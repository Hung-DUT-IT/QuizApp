package com.example.quizapp.View;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.quizapp.Model.Question;
import com.example.quizapp.R;
import com.example.quizapp.databinding.FragmentCreateQuizBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateQuizFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateQuizFragment extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static  String  QUESTION_NUMBER  = "question_number";
    private static  String ARG_PARAM2 = "param2";


    // TODO: Rename and change types of parameters
    int questionnumber;

    public Question question;

    public FragmentCreateQuizBinding binding;
    public int checked=0;
    public CreateQuizFragment() {

    }


    public static CreateQuizFragment newInstance(int questionumber) {
        CreateQuizFragment fragment = new CreateQuizFragment();
        Bundle args = new Bundle();
        args.putInt(QUESTION_NUMBER, questionumber);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            questionnumber = getArguments().getInt(QUESTION_NUMBER);
        }
        Log.d("Test", "onCreate fragment  " + questionnumber);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_create_quiz, container, false);
        return view;

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding=FragmentCreateQuizBinding.bind(view);
        binding.questionNumberTv.setText("Question "+questionnumber);

        binding.imgAddAnswerA.setOnClickListener(this);
        binding.imgAddAnswerB.setOnClickListener(this);
        binding.imgAddAnswerC.setOnClickListener(this);
        binding.imgAddAnswerD.setOnClickListener(this);
        binding.radioBtnA.setOnClickListener(this::onRadiosClick);
        binding.radioBtnB.setOnClickListener(this::onRadiosClick);
        binding.radioBtnC.setOnClickListener(this::onRadiosClick);
        binding.radioBtnD.setOnClickListener(this::onRadiosClick);
        if (savedInstanceState != null) {
            binding.tvAnswerA.setText(savedInstanceState.getString("answerA"));
            binding.tvAnswerB.setText(savedInstanceState.getString("answerB"));
            binding.tvAnswerC.setText(savedInstanceState.getString("answerC"));
            binding.tvAnswerD.setText(savedInstanceState.getString("answerD"));
            binding.radioBtnA.setChecked(false);
            switch (savedInstanceState.getInt("checked"))
            {
                case 0:
                    break;
                case 1:
                    binding.radioBtnB.setChecked(true);
                    break;
                case 2:
                    binding.radioBtnC.setChecked(true);
                    break;
                case 3:
                    binding.radioBtnD.setChecked(true);
                    break;

            }
        }
    }
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("checked",checked);
        outState.putString("answerA",binding.tvAnswerA.getText().toString());
        outState.putString("answerB",binding.tvAnswerB.getText().toString());
        outState.putString("answerC",binding.tvAnswerC.getText().toString());
        outState.putString("answerD",binding.tvAnswerD.getText().toString());
        outState.putString("question",binding.editQuestion.getText().toString());
        PagerCreatingQuizFragment.savedInstanceStates[questionnumber-1]=outState;

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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_add_answer_a:
                    showDiaglog("A");
                break;
            case R.id.img_add_answer_b:
                showDiaglog("B");
                break;
            case R.id.img_add_answer_c:
                showDiaglog("C");
                break;
            default:
                showDiaglog("D");
                break;
        }
    }
    public void showDiaglog(String answerr)
    {
        EditText answer = new EditText(getContext());
        new AlertDialog.Builder(getContext())
                .setTitle("Answer: "+answerr)
                .setView(answer)
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @SuppressLint("SuspiciousIndentation")
                    public void onClick(DialogInterface dialog, int whichButton) {
                        if(answerr.equals(""))
                        {
                            Toast.makeText(getContext(),"Vui lòng không bỏ trống", Toast.LENGTH_SHORT).show();
                        }
                        else
                        switch (answerr) {
                            case "A":
                                binding.tvAnswerA.setText( answer.getText().toString());
                                break;
                            case "B":
                                binding.tvAnswerB.setText( answer.getText().toString());
                                break;
                            case "C":
                                binding.tvAnswerC.setText( answer.getText().toString());
                                break;
                            default:
                                binding.tvAnswerD.setText( answer.getText().toString());
                                break;
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                })
                .show();
    }
}
