package com.example.quizapp.Model.Helper;

import com.example.quizapp.Model.Entity.Question;
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

    public void getQuestion(String path, ValueEventListener listener) {
        mDatabase.getReference(path).addListenerForSingleValueEvent(listener);
    }
    public void getQuestionByCategory(String path, String category, ValueEventListener listener) {
        mDatabase.getReference(path).orderByChild("category").equalTo(category).addListenerForSingleValueEvent(listener);
    }
    public Task<List<Question>> getQuestionsByCategory(String path, String category, ValueEventListener listener) {
        TaskCompletionSource<List<Question>> tcs = new TaskCompletionSource<>();
        ArrayList<Question> questions = new ArrayList<>();
        mDatabase.getReference(path).orderByChild("category").equalTo(category).addListenerForSingleValueEvent(listener);
        return tcs.getTask();
    }
}
