package com.example.quizapp.Model.Helper;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.quizapp.Model.Entity.Room;
import com.example.quizapp.Model.Entity.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class FirebaseUsers {
    private static FirebaseUsers instance;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;

    private FirebaseUsers() {
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
    }

    public static synchronized FirebaseUsers getInstance() {
        if (instance == null) {
            instance = new FirebaseUsers();
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
    public void addUser(String Uid, User user){
        mDatabase.getReference("Users").child(Uid).setValue(user);
    }
    public com.google.firebase.auth.FirebaseUser getCurrentUser() {
        return mAuth.getCurrentUser();
    }
    public void getUserByID(String id, final FirebaseUsers.UserCallback callback)
    {
        Query query = mDatabase.getReference("Users").child(id);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    User user = snapshot.getValue(User.class);
                    callback.onUserReceived(user);
                }
                else{
                    callback.onUserReceived(new User());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void setScore(String Uid, int score){
        mDatabase.getReference("Users").child(Uid).child("score").setValue(score);
    }
    public interface UserCallback {
        void onUserReceived(User user);
    }
    public void setPass(String Uid, String pass){
        mDatabase.getReference("Users").child(Uid).child("pass").setValue(pass);
    }

    public void changePass(String newPass, OnCompleteListener<Void> listener){
        mAuth.getCurrentUser().updatePassword(newPass).addOnCompleteListener(listener);
    }

    public void setUpdate(String Uid, String name){
        mDatabase.getReference("Users").child(Uid).child("name").setValue(name);
    }

    public void resetPassword(String email, OnCompleteListener<Void> listener){
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(listener);
    }

}
