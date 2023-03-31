package com.example.quizapp.View;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.quizapp.Model.Question;
import com.example.quizapp.R;

import java.util.List;


public class PlayGameFragment extends Fragment {
    private List<Question> questions;

    public PlayGameFragment(List<Question> list) {
        // Required empty public constructor
        questions = list;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Log.d("Test", questions.size() + "");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_play_game, container, false);
    }
}