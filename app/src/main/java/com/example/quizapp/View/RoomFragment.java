package com.example.quizapp.View;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.quizapp.Model.Entity.Question;
import com.example.quizapp.Model.Entity.Room;
import com.example.quizapp.Model.Entity.User;
import com.example.quizapp.Model.Helper.FirebaseRoom;
import com.example.quizapp.Model.Helper.FirebaseUsers;
import com.example.quizapp.R;
import com.example.quizapp.ViewModel.UserAdapter;
import com.example.quizapp.databinding.FragmentRoomBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RoomFragment extends Fragment {
    private FragmentRoomBinding binding;
    private UserAdapter userAdapter;
    private ArrayList<User> users;
    private String roomCode;
    private List<Question> questions;
    private Collection<String> idUsers;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            roomCode = getArguments().getString("roomCode");
            questions = (List<Question>) getArguments().getSerializable("questions");
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fragment_room, null , false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        users = new ArrayList<>();

        idUsers = new ArrayList<>();

        binding.rvUser.setHasFixedSize(true);
        binding.rvUser.setLayoutManager(new LinearLayoutManager(requireActivity(),LinearLayoutManager.VERTICAL, false));

        userAdapter = new UserAdapter(users);
        binding.rvUser.setAdapter(userAdapter);
        FirebaseRoom.getInstance().getRoomById(roomCode, new FirebaseRoom.RoomCallback() {
            @Override
            public void onRoomReceived(Room room) {
                binding.tvCountUser.setText(String.valueOf(room.getIdPlayers().size()));
                binding.tvGameCode.setText(room.getRoomCode());
                binding.tvNameRoom.setText(room.getName());

                idUsers = room.getIdPlayers().keySet() ;
                for (String id : idUsers) {
                    FirebaseUsers.getInstance().getUserByID(id, new FirebaseUsers.UserCallback() {
                        @Override
                        public void onUserReceived(User user) {
                            users.add(user);
                            userAdapter.notifyDataSetChanged();
                        }
                    });
                }
                binding.btnStartGame.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FirebaseRoom.getInstance().startGame(roomCode);
                    }
                });
            }
        });
        FirebaseRoom.getInstance().checkStartGame(roomCode).addOnCompleteListener(new OnCompleteListener<Boolean>() {
            @Override
            public void onComplete(@NonNull Task<Boolean> task) {
                if (task.isSuccessful()) {
                    Boolean started = task.getResult();
                    if (started){
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("questions", (Serializable) questions);
                        bundle.putString("play", "friends");
                        bundle.putString("roomCode", roomCode);

                        PlayGameFragment playGameFragment = new PlayGameFragment();
                        playGameFragment.setArguments(bundle);
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragmentContainerView, playGameFragment)
                                .addToBackStack(null)
                                .commit();

                    }
                }
                else {
                    Exception ex = task.getException();
                }
            }
        });
    }
}