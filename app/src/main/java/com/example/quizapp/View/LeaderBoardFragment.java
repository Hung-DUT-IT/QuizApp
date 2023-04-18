package com.example.quizapp.View;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.quizapp.Model.Entity.Room;
import com.example.quizapp.Model.Entity.User;
import com.example.quizapp.Model.Helper.FirebaseRoom;
import com.example.quizapp.Model.Helper.FirebaseUsers;
import com.example.quizapp.R;
import com.example.quizapp.ViewModel.UserAdapter;
import com.example.quizapp.databinding.FragmentLeaderBoardBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class LeaderBoardFragment extends Fragment {
    private FragmentLeaderBoardBinding binding;
    private String roomCode;
    private Room rooms;
    private List<String> idUsers;
    private ArrayList<User> users;
    private UserAdapter userAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            roomCode = getArguments().getString("roomCode");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fragment_leader_board, container , false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rooms = new Room();
        idUsers = new ArrayList<>();
        users = new ArrayList<>();

        binding.rvUserPlayGame.setHasFixedSize(true);
        binding.rvUserPlayGame.setLayoutManager(new LinearLayoutManager(requireActivity(),LinearLayoutManager.VERTICAL, false));

        userAdapter = new UserAdapter(users);
        binding.rvUserPlayGame.setAdapter(userAdapter);

        FirebaseRoom.getInstance().getRoomById(roomCode, new FirebaseRoom.RoomCallback() {
            @Override
            public void onRoomReceived(Room room) {
                idUsers = sortHashMapByValues(room.getIdPlayers());
                int count = 0;
                for (String id : idUsers) {
                    FirebaseUsers.getInstance().getUserByID(id, new FirebaseUsers.UserCallback() {
                        @Override
                        public void onUserReceived(User user) {
                            users.add(user);

                            if (users.size() == 1){
                                binding.tvUsernameTop1.setText(users.get(0).getName());
                            }
                            else if (users.size() == 2){
                                binding.tvUsernameTop2.setText(users.get(users.size() -1).getName());
                            }
                            else if (users.size() == 3){
                                binding.tvUsernameTop3.setText(users.get(users.size() -1 ).getName());
                            }
                            userAdapter.notifyDataSetChanged();
                        }
                    });
                }
            }
        });
        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainerView, new HomeFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });
    }
    public static List<String> sortHashMapByValues(HashMap<String, Integer> hashMap) {
        List<Map.Entry<String, Integer>> list = new LinkedList<>(hashMap.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });
        List<String> result = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : list) {
            result.add(entry.getKey());
        }
        return result;
    }
}