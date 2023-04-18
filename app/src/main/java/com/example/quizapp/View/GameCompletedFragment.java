package com.example.quizapp.View;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.quizapp.Model.Helper.FirebaseRoom;
import com.example.quizapp.Model.Helper.FirebaseUsers;
import com.example.quizapp.R;
import com.example.quizapp.databinding.FragmentGameCompletedBinding;

import java.io.Serializable;


public class GameCompletedFragment extends Fragment {
    private FragmentGameCompletedBinding binding;
    private String play;
    private int score ;
    private String roomCode;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            play = getArguments().getString("play");
            score = getArguments().getInt("score");
            roomCode = getArguments().getString("roomCode");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fragment_game_completed, container , false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.tvSumCountQuestion.setText(String.format("Score: %d", score));
        binding.tvConclusion.setText(String.format("You try 50 questions within three minutes and answer %d questions correctly", score));

        binding.imgBackHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(play.equals("alone")){
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragmentContainerView, new HomeFragment())
                            .addToBackStack(null)
                            .commit();
                }
                else{

                    Bundle bundle = new Bundle();
                    bundle.putString("roomCode", roomCode);

                    LeaderBoardFragment leaderBoardFragment = new LeaderBoardFragment();
                    leaderBoardFragment.setArguments(bundle);
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragmentContainerView, leaderBoardFragment)
                            .addToBackStack(null)
                            .commit();
                }
            }
        });
    }
}