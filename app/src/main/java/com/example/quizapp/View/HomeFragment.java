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
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.quizapp.Model.Entity.User;
import com.example.quizapp.Model.Helper.FirebaseQuestion;
import com.example.quizapp.Model.Helper.FirebaseUsers;
import com.example.quizapp.R;
import com.example.quizapp.ViewModel.CategoryAdapter;
import com.example.quizapp.databinding.FragmentHomeBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    private ArrayList<String> categoryList;
    private ArrayList<String> categoryListByUser;
    private CategoryAdapter categoryAdapter;
    private CategoryAdapter categoryByUserAdapter;
    public HomeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fragment_home, null , false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FirebaseUsers.getInstance().getUserByID(FirebaseUsers.getInstance().getIdUserCurrent(), new FirebaseUsers.UserCallback() {
            @Override
            public void onUserReceived(User user) {
                binding.tvRank.setText(String.valueOf(user.getPrev_score()));
                binding.tvCountQuestion.setText(String.valueOf(user.getScore()));
            }
        });

        categoryList = new ArrayList<>();
        categoryListByUser = new ArrayList<>();

        binding.rvCategory.setHasFixedSize(true);
        binding.rvCategory.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.rvCategoryUser.setHasFixedSize(true);
        binding.rvCategoryUser.setLayoutManager(new LinearLayoutManager(requireActivity(),LinearLayoutManager.HORIZONTAL, false));


        categoryAdapter = new CategoryAdapter(categoryList);
        binding.rvCategory.setAdapter(categoryAdapter);

        categoryByUserAdapter = new CategoryAdapter(categoryListByUser);
        binding.rvCategoryUser.setAdapter(categoryByUserAdapter);

        binding.imgUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainerView, new SettingsFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });
        FirebaseQuestion.getInstance().getCategoryByUser(new FirebaseQuestion.CategoryCallback() {
            @Override
            public void onQuestionCategory(List<String> categories) {
                categoryListByUser.addAll(categories);
                categoryByUserAdapter.notifyDataSetChanged();
            }
        });
        FirebaseQuestion.getInstance().getQuestion("questions").addOnCompleteListener(new OnCompleteListener<List<String>>() {
            @Override
            public void onComplete(@NonNull Task<List<String>> task) {
                if (task.isSuccessful()) {
                    categoryList.addAll(task.getResult());
                    categoryAdapter.notifyDataSetChanged();
                    binding.btnAddCategory.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            Bundle bundle = new Bundle();
                            bundle.putStringArrayList("categoryList", categoryList);

                            ChooseCategoryFragment chooseCategoryFragment = new ChooseCategoryFragment();
                            chooseCategoryFragment.setArguments(bundle);
                            getActivity().getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.fragmentContainerView, chooseCategoryFragment)
                                    .addToBackStack(null)
                                    .commit();
                        }
                    });
                }
                else{
                    Exception ex = task.getException();
                }
            }
        });
    }
}