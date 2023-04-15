package com.example.quizapp.View;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.quizapp.Model.Entity.Question;
import com.example.quizapp.Model.Helper.FirebaseRoom;
import com.example.quizapp.Model.Helper.FirebaseUsers;
import com.example.quizapp.R;
import com.example.quizapp.databinding.FragmentPlayGameBinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class PlayGameFragment extends Fragment {
    private FragmentPlayGameBinding binding;
    private ArrayList<Button> buttons;
    private List<Question> questions;
    private Question currentQuestion;
    private CountDownTimer countDownTimer;
    private long timeLeftInMillis = 30000;
    private int currentIndex = 1;
    private int score = 0;
    private String play;
    private String roomCode;
    public PlayGameFragment() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            questions = (List<Question>) getArguments().getSerializable("questions");
            play = getArguments().getString("play");
            roomCode = getArguments().getString("roomCode");
        }
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fragment_play_game, container , false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loadNextQuestion();
        countDownTimer = new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                timeLeftInMillis = 0;
                updateCountDownText();
                finishQuiz();
            }
        }.start();

    }

    private void loadNextQuestion() {
        binding.tvCountQuestion.setText(String.format("%s/%d", String.valueOf(score), questions.size()));
        currentIndex++;
        if (currentIndex >= questions.size()) {
            finishQuiz();
        }
        else{
            currentQuestion = questions.get(currentIndex);

            binding.tvQuestion.setText(currentQuestion.getQuestion());

            List<String> answers = currentQuestion.getIncorrect_answers();
            answers.add(currentQuestion.getCorrect_answer());
            Collections.shuffle(answers);

            buttons = new ArrayList<>();
            buttons.add(binding.btnQuestionA);
            buttons.add(binding.btnQuestionB);
            buttons.add(binding.btnQuestionC);
            buttons.add(binding.btnQuestionD);
            int position = 0;
            for (Button button : buttons) {
                button.setText(answers.get(position++));
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        setColorButtonOneClick(buttons,button);
                        checkAnswer(button.getText().toString());
                    }
                });
            }
        }
    }
    private void setColorButtonOneClick(ArrayList<Button> buttons, Button id){
        for (Button button : buttons) {
            button.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#CBDCE6")));
        }
        id.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#e78230")));
    }
    private void checkAnswer(String answer){
        if (currentQuestion.getCorrect_answer().equals(answer)) {
            score++;
        } else {
            score = 0;
        }
        loadNextQuestion();
    }
    private void finishQuiz() {
        countDownTimer.cancel();


        Bundle bundle = new Bundle();
        bundle.putInt("score", score);
        bundle.putString("play", play);
        bundle.putString("roomCode", roomCode);
        GameCompletedFragment gameCompletedFragment = new GameCompletedFragment();
        gameCompletedFragment.setArguments(bundle);
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView, gameCompletedFragment)
                .addToBackStack(null)
                .commit();


        if(play.equals("alone")){
            FirebaseUsers.getInstance().setScore(FirebaseUsers.getInstance().getIdUserCurrent(), score);
        }
        else{
            FirebaseRoom.getInstance().setScore(roomCode, FirebaseUsers.getInstance().getIdUserCurrent(), score);
        }
    }
    private void updateCountDownText() {
        int minutes = (int) (timeLeftInMillis / 1000) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;

        String timeFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

        binding.tvCountdownTimer.setText(timeFormatted);

        if (timeLeftInMillis < 10000) {
            binding.tvCountdownTimer.setTextColor(Color.RED);
        } else {
            binding.tvCountdownTimer.setTextColor(Color.BLUE);
        }
    }
}