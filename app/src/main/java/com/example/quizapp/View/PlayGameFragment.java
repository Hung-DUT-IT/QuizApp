package com.example.quizapp.View;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

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


public class PlayGameFragment extends Fragment {
    List<Question> questions;
    private Question currentQuestion;
    private int currentIndex = 1;
    private int score = 0;
    private TextView questionTextView;
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
            loadNextQuestion();
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_play_game, container, false);
        // Initialize the UI elements
        questionTextView = view.findViewById(R.id.question_text_view);
        option1Button = view.findViewById(R.id.option_button_1);
        option2Button = view.findViewById(R.id.option_button_2);
        option3Button = view.findViewById(R.id.option_button_3);
        option4Button = view.findViewById(R.id.option_button_4);
        // Set the click listeners for the answer buttons
        option1Button.setOnClickListener(view2 -> checkAnswer(1));
        option2Button.setOnClickListener(view2-> checkAnswer(2));
        option3Button.setOnClickListener(view2 -> checkAnswer(3));
        option4Button.setOnClickListener(view2 -> checkAnswer(4));
        return view;
    }
    private void loadNextQuestion() {
        // Move to the next question
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
//        option1Button.setText(currentQuestion.getOption1());
//        option2Button.setText(currentQuestion.getOption2());
//        option3Button.setText(currentQuestion.getOption3());
//        option4Button.setText(currentQuestion.getOption4());
    }
    private void checkAnswer(int optionNumber) {
        // Check if the selected option is correct
        if (1 == optionNumber) {
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
}