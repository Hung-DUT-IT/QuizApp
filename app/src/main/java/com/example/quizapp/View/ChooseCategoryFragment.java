package com.example.quizapp.View;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.quizapp.Model.Entity.Question;
import com.example.quizapp.R;
import com.example.quizapp.ViewModel.CategoryAdapter;
import com.example.quizapp.databinding.FragmentChangePassBinding;
import com.example.quizapp.databinding.FragmentChooseCategoryBinding;

import java.util.List;

public class ChooseCategoryFragment extends Fragment {
    private FragmentChooseCategoryBinding binding;
    private List<String> categoryList;
    private CategoryAdapter categoryAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            categoryList = getArguments().getStringArrayList("categoryList");
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fragment_choose_category, null , false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        binding.rvCategory.setHasFixedSize(true);
        binding.rvCategory.setLayoutManager(new LinearLayoutManager(getActivity()));
        categoryAdapter = new CategoryAdapter(categoryList);
        binding.rvCategory.setAdapter(categoryAdapter);

        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String category = binding.editNameCategory.getText().toString();
                if (!category.isEmpty()){
                    Bundle bundle = new Bundle();
                    bundle.putString("category", category);
                    PagerCreateQuestionFragment pagerCreateQuestionFragment = new PagerCreateQuestionFragment();
                    pagerCreateQuestionFragment.setArguments(bundle);
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragmentContainerView, pagerCreateQuestionFragment)
                            .addToBackStack(null)
                            .commit();
                }
                else{
                    Toast.makeText(getContext(), "Please enter your Category", Toast.LENGTH_SHORT).show();
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