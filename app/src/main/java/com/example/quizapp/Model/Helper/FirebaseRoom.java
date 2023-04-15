package com.example.quizapp.Model.Helper;

import androidx.annotation.NonNull;

import com.example.quizapp.Model.Entity.Room;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class FirebaseRoom {
    private static FirebaseRoom instance;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;

    private FirebaseRoom() {
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
    }

    public static synchronized FirebaseRoom getInstance() {
        if (instance == null) {
            instance = new FirebaseRoom();
        }
        return instance;
    }
    public void addRoom(String roomCode, String name, String ownerId){
        Room room = new Room(roomCode, name, ownerId);
        mDatabase.getReference("Rooms").child(roomCode).setValue(room);
    }

    public void joinRoom(String roomCode, String playerId, final FirebaseRoom.AllowCallback callback){
        mDatabase.getReference("Rooms").child(roomCode).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    if (Boolean.FALSE.equals(snapshot.child("start").getValue(Boolean.class))) {

                        Query query = mDatabase.getReference("Rooms").child(roomCode).child("idPlayers").orderByValue().equalTo(playerId);
                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists()) {

                                } else {
                                    mDatabase.getReference("Rooms").child(roomCode).child("idPlayers").child(playerId).setValue(0);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                        callback.onRoomAllow(true);
                    }
                    else{
                        callback.onRoomAllow(false);
                    }
                }
                else
                {
                    callback.onRoomAllow(false);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void startGame(String roomCode){
        mDatabase.getReference("Rooms").child(roomCode).child("start").setValue(true);
    }
    public Task<Boolean> checkStartGame(String roomCode){
        TaskCompletionSource<Boolean> tcs = new TaskCompletionSource<>();
        mDatabase.getReference("Rooms").child(roomCode).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    if (Boolean.TRUE.equals(snapshot.child("start").getValue(Boolean.class))) {
                        tcs.setResult(true);

                    }
                }
                else
                {
                    tcs.setResult(false);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return tcs.getTask();
    }

    public void getRoomById(String roomCode, final FirebaseRoom.RoomCallback callback) {
        Query query = mDatabase.getReference("Rooms").child(roomCode);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Room room = snapshot.getValue(Room.class);
                    callback.onRoomReceived(room);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void setScore(String roomCode, String playerId, int score){
        mDatabase.getReference("Rooms").child(roomCode).child("idPlayers").child(playerId).setValue(score);
    }
    public interface RoomCallback {
        void onRoomReceived(Room room);
    }
    public interface AllowCallback {
        void onRoomAllow(Boolean allow);
    }
}
