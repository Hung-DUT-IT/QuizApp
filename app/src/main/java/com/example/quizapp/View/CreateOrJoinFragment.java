package com.example.quizapp.View;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.quizapp.Model.Entity.Question;
import com.example.quizapp.Model.Helper.FirebaseRoom;
import com.example.quizapp.Model.Helper.FirebaseUsers;
import com.example.quizapp.R;
import com.example.quizapp.databinding.FragmentCreateOrJoinBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.Serializable;
import java.util.List;
import java.util.Random;

public class CreateOrJoinFragment extends Fragment {
    private FragmentCreateOrJoinBinding binding;
    private List<Question> questions;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            questions = (List<Question>) getArguments().getSerializable("questions");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fragment_create_or_join, null , false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.btnCreateRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mDialog = new AlertDialog.Builder(view.getContext());
                LayoutInflater inflater = LayoutInflater.from(view.getContext());
                View mView = inflater.inflate(R.layout.add_room, null);

                mDialog.setView(mView);

                AlertDialog dialog = mDialog.create();
                dialog.setCancelable(true);

                Button btnJoin = mView.findViewById(R.id.btn_add_room);
                EditText nameRoom = mView.findViewById(R.id.edit_name_room);
                btnJoin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String roomCode = generateRoomCode();
                        String name = nameRoom.getText().toString();
                        String ownerId = FirebaseUsers.getInstance().getIdUserCurrent();

                        if(!name.isEmpty()){
                            FirebaseRoom.getInstance().addRoom(roomCode, name, ownerId);

                            Bundle bundle = new Bundle();
                            bundle.putString("roomCode", roomCode);
                            bundle.putSerializable("questions", (Serializable) questions);

                            RoomFragment roomFragment = new RoomFragment();
                            roomFragment.setArguments(bundle);
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, roomFragment)
                                    .addToBackStack(null)
                                    .commit();

                            dialog.dismiss();
                        }
                        else{
                            Toast.makeText(getContext(), "Please enter your name room", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                dialog.show();
            }
        });

        binding.btnJoinRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mDialog = new AlertDialog.Builder(view.getContext());
                LayoutInflater inflater = LayoutInflater.from(view.getContext());
                View mView = inflater.inflate(R.layout.add_room, null);

                mDialog.setView(mView);

                AlertDialog dialog = mDialog.create();
                dialog.setCancelable(true);

                Button btnJoin = mView.findViewById(R.id.btn_add_room);
                EditText nameRoom = mView.findViewById(R.id.edit_name_room);
                btnJoin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String roomCode = nameRoom.getText().toString();
                        String playerId = FirebaseUsers.getInstance().getIdUserCurrent();
                        if(!roomCode.isEmpty())
                        {
                            FirebaseRoom.getInstance().joinRoom(roomCode, playerId, new FirebaseRoom.AllowCallback() {
                                @Override
                                public void onRoomAllow(Boolean allow) {
                                    if (allow){
                                        Bundle bundle = new Bundle();
                                        bundle.putString("roomCode", roomCode);
                                        bundle.putSerializable("questions", (Serializable) questions);

                                        RoomFragment roomFragment = new RoomFragment();
                                        roomFragment.setArguments(bundle);
                                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, roomFragment)
                                                .addToBackStack(null)
                                                .commit();
                                        dialog.dismiss();
                                    }
                                    else{
                                        Toast.makeText(getContext(),"khong ton tai", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                        else{
                            Toast.makeText(getContext(), "Please enter your code room", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                dialog.show();
            }
        });
    }
    private String generateRoomCode() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        int length = 6;
        StringBuilder codeBuilder = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            codeBuilder.append(characters.charAt(random.nextInt(characters.length())));
        }
        return codeBuilder.toString();
    }
}