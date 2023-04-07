package com.example.quizapp.View;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizapp.Model.Entity.Question;
import com.example.quizapp.Model.Entity.User;
import com.example.quizapp.Model.Helper.FirebaseUtils;
import com.example.quizapp.R;
import com.example.quizapp.ViewModel.CategoryAdapter;
import com.example.quizapp.ViewModel.MySharedPreferences;
import com.example.quizapp.databinding.FragmentHomeBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    private ArrayList<String> categoryList;
    private CategoryAdapter categoryAdapter;
    public HomeFragment() {
        // Required empty public constructor
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
        FirebaseUtils.getInstance().getUserByID(FirebaseUtils.getInstance().getIDCurrentUser()).addOnCompleteListener(new OnCompleteListener<User>() {
            @Override
            public void onComplete(@NonNull Task<User> task) {
                if (task.isSuccessful()) {
                    User user = task.getResult();
                }
                else {
                    Exception ex = task.getException();
                }
            }
        });

        //Log.d("DEBUG", MySharedPreferences.getInstance(getContext()).getString("idUser"));

        categoryList= new ArrayList<>();

        binding.rvCategory.setHasFixedSize(true);
        binding.rvCategory.setLayoutManager(new LinearLayoutManager(getActivity()));

        categoryAdapter = new CategoryAdapter(categoryList);
        binding.rvCategory.setAdapter(categoryAdapter);

        binding.imgUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.settingsFragment);
            }
        });
        FirebaseUtils.getInstance().getQuestion("questions").addOnCompleteListener(new OnCompleteListener<List<String>>() {
            @Override
            public void onComplete(@NonNull Task<List<String>> task) {
                if (task.isSuccessful()) {
                    categoryList.addAll(task.getResult());

                    categoryAdapter.notifyDataSetChanged();
                }
                else{
                    Exception ex = task.getException();
                }
            }
        });
        binding.imgUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.settingsFragment);
            }
        });
    }
}