package com.example.quizapp.View;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.quizapp.Model.Entity.Question;
import com.example.quizapp.Model.Helper.FirebaseRoom;
import com.example.quizapp.Model.Helper.FirebaseUsers;
import com.example.quizapp.R;
import com.example.quizapp.databinding.FragmentCreateQuestionBinding;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class CreateQuestionFragment extends Fragment {

    private FragmentCreateQuestionBinding binding;
    private int checked = 0;
    private int questionNumber;
    private ArrayList<ImageView> imageViews;
    public CreateQuestionFragment(int questionNumber) {
        this.questionNumber = questionNumber;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fragment_create_question, container , false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        HashMap<Integer, Question> questionList = PagerCreateQuestionFragment.questionList;

        List<String> answer = new ArrayList<>();
        answer.add("A");
        answer.add("B");
        answer.add("C");
        answer.add("D");

        imageViews = new ArrayList<>();
        imageViews.add(binding.imgAddAnswerA);
        imageViews.add(binding.imgAddAnswerB);
        imageViews.add(binding.imgAddAnswerC);
        imageViews.add(binding.imgAddAnswerD);

        int position = 0;
        for(ImageView imageView: imageViews){
            String text = answer.get(position++);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showDiaLog(text);
                }
            });
        }
        binding.radioBtnA.setOnClickListener(this::onRadiosClick);
        binding.radioBtnB.setOnClickListener(this::onRadiosClick);
        binding.radioBtnC.setOnClickListener(this::onRadiosClick);
        binding.radioBtnD.setOnClickListener(this::onRadiosClick);

        binding.btnNextQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Question question = new Question();
                question.setQuestion(binding.editQuestion.getText().toString());
                question.setType("Multiple Choice");
                question.setDifficulty("easy");
                ArrayList<String> incorrectAnswers = new ArrayList<>();
                switch (checked)
                {
                    case 0:
                        question.setCorrectAnswer(binding.tvAnswerA.getText().toString());
                        incorrectAnswers.add(binding.tvAnswerD.getText().toString());
                        incorrectAnswers.add(binding.tvAnswerB.getText().toString());
                        incorrectAnswers.add(binding.tvAnswerC.getText().toString());
                        question.setIncorrectAnswers(incorrectAnswers);
                        break;
                    case 1:
                        question.setCorrectAnswer(binding.tvAnswerB.getText().toString());;
                        incorrectAnswers.add(binding.tvAnswerA.getText().toString());
                        incorrectAnswers.add(binding.tvAnswerD.getText().toString());
                        incorrectAnswers.add(binding.tvAnswerC.getText().toString());
                        question.setIncorrectAnswers(incorrectAnswers);
                        break;
                    case 2:
                        question.setCorrectAnswer(binding.tvAnswerC.getText().toString());
                        incorrectAnswers.add(binding.tvAnswerA.getText().toString());
                        incorrectAnswers.add(binding.tvAnswerB.getText().toString());
                        incorrectAnswers.add(binding.tvAnswerD.getText().toString());
                        question.setIncorrectAnswers(incorrectAnswers);
                        break;
                    default:
                        question.setCorrectAnswer(binding.tvAnswerD.getText().toString());
                        incorrectAnswers.add(binding.tvAnswerA.getText().toString());
                        incorrectAnswers.add(binding.tvAnswerB.getText().toString());
                        incorrectAnswers.add(binding.tvAnswerC.getText().toString());
                        question.setIncorrectAnswers(incorrectAnswers);
                        break;
                }
                questionList.put(questionNumber, question);
            }
        });
    }
    private void onRadiosClick(View view) {
        int RadioID=view.getId();
        switch (RadioID)
        {
            case R.id.radio_btn_a:
                checked=0;
                binding.radioBtnB.setChecked(false);
                binding.radioBtnC.setChecked(false);
                binding.radioBtnD.setChecked(false);
                break;
            case R.id.radio_btn_b:
                checked=1;
                binding.radioBtnA.setChecked(false);
                binding.radioBtnC.setChecked(false);
                binding.radioBtnD.setChecked(false);
                break;
            case R.id.radio_btn_c:
                checked=2;
                binding.radioBtnD.setChecked(false);
                binding.radioBtnA.setChecked(false);
                binding.radioBtnB.setChecked(false);

                break;
            default:
                checked=3;
                binding.radioBtnB.setChecked(false);
                binding.radioBtnA.setChecked(false);
                binding.radioBtnC.setChecked(false);
                break;
        }


    }
    public void showDiaLog(String answer)
    {
        androidx.appcompat.app.AlertDialog.Builder mDialog = new androidx.appcompat.app.AlertDialog.Builder(getView().getContext());
        LayoutInflater inflater = LayoutInflater.from(getView().getContext());
        View mView = inflater.inflate(R.layout.add_question, null);

        mDialog.setView(mView);

        androidx.appcompat.app.AlertDialog dialog = mDialog.create();
        dialog.setCancelable(true);

        Button btnAddQuestion = mView.findViewById(R.id.btn_add);
        EditText question = mView.findViewById(R.id.edit_question);
        TextView tittle = mView.findViewById(R.id.tv_tittle);

        tittle.setText(String.format("Answer: %s", answer));
        btnAddQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String questions = question.getText().toString();

                if(!questions.isEmpty()){
                    switch (answer) {
                        case "A":
                            binding.tvAnswerA.setText( questions);
                            break;
                        case "B":
                            binding.tvAnswerB.setText( questions);
                            break;
                        case "C":
                            binding.tvAnswerC.setText( questions);
                            break;
                        default:
                            binding.tvAnswerD.setText( questions);
                            break;
                    }
                }
                else{
                    Toast.makeText(getContext(), "Please enter your question", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}