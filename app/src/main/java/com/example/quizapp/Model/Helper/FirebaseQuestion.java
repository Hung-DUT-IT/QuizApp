package com.example.quizapp.Model.Helper;

import androidx.annotation.NonNull;

import com.example.quizapp.Model.Entity.Question;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FirebaseQuestion {
    private static FirebaseQuestion instance;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;

    private FirebaseQuestion() {
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
    }

    public static synchronized FirebaseQuestion getInstance() {
        if (instance == null) {
            instance = new FirebaseQuestion();
        }
        return instance;
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
    public void addQuestion(String category, String position, Question question){
        mDatabase.getReference("QuestionByUser").child(category).child(position).setValue(question);
    }
    public void getCategoryByUser(FirebaseQuestion.CategoryCallback categoryCallback){
        mDatabase.getReference("QuestionByUser").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if( snapshot.exists()){
                    List<String> categoryList = new ArrayList<>();
                    for (DataSnapshot categorySnapshot : snapshot.getChildren()) {
                        String category = categorySnapshot.getKey();
                        categoryList.add(category);
                    }
                    categoryCallback.onQuestionCategory(categoryList);
                }
                else{
                    categoryCallback.onQuestionCategory(new ArrayList<>());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public interface CategoryCallback {
        void onQuestionCategory(List<String> categories);
    }
}
