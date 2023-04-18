package com.example.quizapp.View;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.quizapp.Model.Entity.Question;
import com.example.quizapp.Model.Helper.FirebaseQuestion;
import com.example.quizapp.Model.Helper.FirebaseUsers;
import com.example.quizapp.R;
import com.example.quizapp.ViewModel.PagerAdapter;
import com.example.quizapp.databinding.FragmentCreateQuestionBinding;
import com.example.quizapp.databinding.FragmentPagerCreateQuestionBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PagerCreateQuestionFragment extends Fragment {
    private FragmentPagerCreateQuestionBinding binding;
    private List<Fragment> fragmentList;
    private PagerAdapter pagerAdapter;
    private String category;
    public static HashMap<Integer, Question> questionList = new HashMap<>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            category = getArguments().getString("category");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fragment_pager_create_question, container , false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fragmentList = new ArrayList<>();

        for(int i = 0; i <= 10; i++)
        {
            fragmentList.add(new CreateQuestionFragment(i));
        }
        pagerAdapter = new PagerAdapter(requireActivity(), fragmentList);
        binding.viewpager.setAdapter(pagerAdapter);

        binding.btnUploadQuestions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (Map.Entry<Integer, Question> entry : questionList.entrySet()) {
                    int position =entry.getKey();
                    Question question = entry.getValue();
                    question.setCategory(category);
                    FirebaseQuestion.getInstance().addQuestion(category, String.valueOf(position), question);

                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragmentContainerView, new HomeFragment())
                            .addToBackStack(    null)
                            .commit();
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
}