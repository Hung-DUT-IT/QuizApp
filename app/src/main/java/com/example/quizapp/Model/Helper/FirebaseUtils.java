package com.example.quizapp.Model.Helper;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.quizapp.Model.Entity.Question;
import com.example.quizapp.Model.Entity.Room;
import com.example.quizapp.Model.Entity.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FirebaseUtils {

    private static FirebaseUtils instance;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;

    private FirebaseUtils() {
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
    }

    public static FirebaseUtils getInstance() {
        if (instance == null) {
            instance = new FirebaseUtils();
        }
        return instance;
    }

    public void signIn(String email, String password, OnCompleteListener<AuthResult> listener) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(listener);
    }
    public String  getIdUserCurrent(){
         return mAuth.getCurrentUser().getUid();
    }
    public void signOut() {
        mAuth.signOut();
    }

    public void signUp(String email, String password, OnCompleteListener<AuthResult> listener){
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(listener);
    }
    public DatabaseReference getDataReference(String path) {
        return mDatabase.getReference(path);
    }
    public FirebaseUser getCurrentUser() {
        return mAuth.getCurrentUser();
    }
    public String getIDCurrentUser(){
        return mAuth.getCurrentUser().getUid();
    }
    public Task<User> getUserByID(String id){
        TaskCompletionSource<User> tcs = new TaskCompletionSource<>();
        User user = new User();
        mDatabase.getReference("users").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                tcs.setResult(snapshot.getValue(User.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                tcs.setException(error.toException());
            }
        });
        return tcs.getTask();
    }

    public Task<List<String>> getQuestion(String path) {
        TaskCompletionSource<List<String>> tcs = new TaskCompletionSource<>();
        ArrayList<String> categories = new ArrayList<>();
        mDatabase.getReference(path).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot questionSnapshot : snapshot.getChildren()) {
                    // Extract the topic information from each question
                    String topic = questionSnapshot.child("category").getValue(String.class);
                    if (!categories.contains(topic)) {
                        categories.add(topic);
                    }
                }
                tcs.setResult(categories);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                tcs.setException(error.toException());
            }
        });
        return tcs.getTask();
    }
    public Task<List<Question>> getQuestionsByCategory(String path, String category) {
        TaskCompletionSource<List<Question>> tcs = new TaskCompletionSource<>();
        ArrayList<Question> questions = new ArrayList<>();
        mDatabase.getReference(path).orderByChild("category").equalTo(category).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot questionSnapshot : snapshot.getChildren()) {
                    Question question = questionSnapshot.getValue(Question.class);
                    questions.add(question);
                }
                tcs.setResult(questions);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                tcs.setException(error.toException());
            }
        });
        return tcs.getTask();
    }
    private void AddUserToRoom(String path, Room newRoom){
        mDatabase.getReference(path).child(newRoom.getId()).setValue(newRoom);
    }
}
