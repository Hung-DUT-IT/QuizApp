package com.example.quizapp.View;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.quizapp.Model.Question;
import com.example.quizapp.R;
import com.example.quizapp.ViewModel.PagerAdapterCreatingQuiz;
import com.example.quizapp.databinding.FragmentPagerCreatingQuizBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PagerCreatingQuizFragment extends Fragment implements View.OnClickListener {


    private FragmentPagerCreatingQuizBinding binding;
    private static List<Question> questionSet;
    public static List<Question> getQuestionSet() {
        return questionSet;
    }
    public String category;
    public static Bundle savedInstanceStates[];
    public FirebaseDatabase database;
    protected DatabaseReference mDatabase;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bd= getArguments();
        if (bd != null) {
            questionSet= new ArrayList<Question>();
            category= bd.getString("category");
            savedInstanceStates=new Bundle[10];
            database= FirebaseDatabase.getInstance();
            mDatabase=database.getReference();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPagerCreatingQuizBinding.inflate(inflater, container , false);

        View view= binding.getRoot();
        Log.d("test", "onCreateView cuar thang FragmentPager ");
        List<CreateQuizFragment> fragmentList = new ArrayList<>();
        for(int i=1;i<=10;i++)
        {
            fragmentList.add(CreateQuizFragment.newInstance(i));
        }
        PagerAdapterCreatingQuiz adapter = new PagerAdapterCreatingQuiz(requireActivity(), fragmentList);
        binding.viewpager.setAdapter(adapter);
        binding.btnUploadQuestions.setOnClickListener(this);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onClick(View view) {
        PagerAdapterCreatingQuiz adapterTemp = (PagerAdapterCreatingQuiz) binding.viewpager.getAdapter();
        for (CreateQuizFragment fragmentTemp : adapterTemp.fragmentList) //duyệt tất cả fragment creatquiz của ViewPager để thêm vào tập câu hỏi
        {
            String a,b,c,d;
            int checked;
            String myquestion;
            if(!fragmentTemp.isAdded())
            {
                int questionnumber=fragmentTemp.getArguments().getInt("question_number");
                Log.d("test", questionnumber+"");
                int index=questionnumber-1;
                Bundle bd=savedInstanceStates[index];
                if(bd==null)
                {
                    continue;
                }
                a=bd.getString("answerA");
                b=bd.getString("answerB");
                c=bd.getString("answerC");
                d=bd.getString("answerD");
                checked=bd.getInt("checked");
                myquestion=bd.getString("question");
            }
            else {
                myquestion = fragmentTemp.binding.editQuestion.getText().toString();
                Log.d("test", "lấy được câu hỏi ");
                a = fragmentTemp.binding.tvAnswerA.getText().toString();
                b = fragmentTemp.binding.tvAnswerB.getText().toString();
                c = fragmentTemp.binding.tvAnswerC.getText().toString();
                d = fragmentTemp.binding.tvAnswerD.getText().toString();
                checked = fragmentTemp.checked;

            }
            Question question = new Question();
            ArrayList<String> tempList = new ArrayList<>();
            Collections.addAll(tempList, a, b, c, d);
            ArrayList<String> tempListWronganswers = new ArrayList<>();
            for (int i = 0; i < tempList.size(); i++) {
                if (checked == i) {
                    question.setCorrect_answer(tempList.get(i));
                } else {
                    tempListWronganswers.add(tempList.get(i));
                }
            }
            question.setCategory(this.category);
            question.setQuestion(myquestion);
            question.setType("Multiple Choice");
            question.setDifficulty("Not Know");
            question.setIncorrect_answers(tempListWronganswers);
            questionSet.add(question);

        }
        Log.d("test", "tập câu hỏi: " +"\n" + questionSet.toString());
        mDatabase.child("questionByUser").push().setValue(questionSet)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("test", "onSuccess: ");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });
        Navigation.findNavController(view).popBackStack(R.id.chooseCategoryFragment, false);

    }
 
}