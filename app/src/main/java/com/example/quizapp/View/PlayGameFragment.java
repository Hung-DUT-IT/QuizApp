package com.example.quizapp.View;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quizapp.Model.Question;
import com.example.quizapp.R;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;


public class PlayGameFragment extends Fragment {
    List<Question> questions;
    private Question currentQuestion;
    private CountDownTimer countDownTimer;
    private long timeLeftInMillis;
    private int currentIndex = 1;
    private int score = 0;
    private TextView questionTextView;
    private TextView timerTextView;
    private TextView countQuestionTextView;
    private Button option1Button;
    private Button option2Button;
    private Button option3Button;
    private Button option4Button;
    public PlayGameFragment() {
//        questions = list;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            questions = (List<Question>) getArguments().getSerializable("questions");
            Log.d("TAG", "onCreate: " + questions.size() + "");
            //loadNextQuestion();
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_play_game, container, false);
        // Initialize the UI elements
        countQuestionTextView = view.findViewById(R.id.tv_count_question);
        timerTextView = view.findViewById(R.id.tv_countdown_timer);
        questionTextView = view.findViewById(R.id.question_text_view);
        option1Button = view.findViewById(R.id.option_button_1);
        option2Button = view.findViewById(R.id.option_button_2);
        option3Button = view.findViewById(R.id.option_button_3);
        option4Button = view.findViewById(R.id.option_button_4);
        // Set the click listeners for the answer buttons
        option1Button.setOnClickListener(view2 -> checkAnswer(option1Button.getText().toString()));
        option2Button.setOnClickListener(view2-> checkAnswer(option2Button.getText().toString()));
        option3Button.setOnClickListener(view2 -> checkAnswer(option3Button.getText().toString()));
        option4Button.setOnClickListener(view2 -> checkAnswer(option4Button.getText().toString()));
        loadNextQuestion();
        startCountDown();

        return view;
    }
    private void loadNextQuestion() {

        // Move to the next question
        countQuestionTextView.setText(String.valueOf(currentIndex));

        currentIndex++;
        // Check if there are no more questions
        if (currentIndex >= questions.size()) {
            // Show the score
            Toast.makeText(getActivity(), "Your score is " + score, Toast.LENGTH_SHORT).show();
            // Return to the previous fragment
            requireActivity().getSupportFragmentManager().popBackStack();
            return;
        }
        // Get the current question
        currentQuestion = questions.get(currentIndex);
        // Set the question text
        questionTextView.setText(currentQuestion.getQuestion());
        // Set the answer options
        Log.d("ABC",currentQuestion.getIncorrect_answers().size()+"");


        option1Button.setText(currentQuestion.getIncorrect_answers().get(0));
        option2Button.setText(currentQuestion.getIncorrect_answers().get(1));
        option3Button.setText(currentQuestion.getIncorrect_answers().get(2));
        option4Button.setText(currentQuestion.getCorrect_answer());
    }
    private void checkAnswer(String optionNumber) {
        // Check if the selected option is correct
        if (currentQuestion.getCorrect_answer().equals(optionNumber)) {
            // Increase the score
            score++;

            // Load the next question
            loadNextQuestion();
        } else {
            // Show the score
            Toast.makeText(getActivity(), "Your score is " + score, Toast.LENGTH_SHORT).show();
            // Return to the previous fragment
            requireActivity().getSupportFragmentManager().popBackStack();
        }
    }
    private void startCountDown() {
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

    private void updateCountDownText() {
        int minutes = (int) (timeLeftInMillis / 1000) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;

        String timeFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

        timerTextView.setText(timeFormatted);

        if (timeLeftInMillis < 10000) {
            timerTextView.setTextColor(Color.RED);
        } else {
            timerTextView.setTextColor(Color.BLUE);
        }
    }
    private void finishQuiz() {
        Log.d("Score",score + "");
        countDownTimer.cancel();

        // Create a bundle to pass the score to the GameCompletedFragment
        Bundle bundle = new Bundle();
        bundle.putInt("score", score);

        // Create a new instance of GameCompletedFragment
        GameCompletedFragment gameCompletedFragment = new GameCompletedFragment();
        gameCompletedFragment.setArguments(bundle);

        // Replace the current fragment with GameCompletedFragment
        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView, gameCompletedFragment)
                .addToBackStack(null)
    }
}