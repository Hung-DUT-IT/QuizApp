package com.example.quizapp.View;

import static android.content.ContentValues.TAG;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.quizapp.Adapter.CategoryAdapter;
import com.example.quizapp.MainActivity;
import com.example.quizapp.Model.Question;
import com.example.quizapp.R;
import com.example.quizapp.databinding.FragmentHomeBinding;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    private DatabaseReference database;
    private CategoryAdapter categoryAdapter;
    private ArrayList<Question> allitem;
    private Set<String> categorySet;
    private MainActivity mainActivity;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mainActivity = (MainActivity) getActivity();
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView rclCategoryList = binding.rvCategory;
        rclCategoryList.setHasFixedSize(true);
        rclCategoryList.setLayoutManager(new LinearLayoutManager(getActivity()));

        database = FirebaseDatabase.getInstance().getReference("questions");
        allitem = new ArrayList<>();
        categorySet = new HashSet<>();
        categoryAdapter = new CategoryAdapter(allitem);
        rclCategoryList.setAdapter(categoryAdapter);
        // Thiết lập listener cho adapter
        categoryAdapter.setListener(new CategoryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String category) {
                // Xử lý sự kiện click
                Log.d(TAG, "Category clicked: " + category);
                // Chuyển tới trang khác
                // ...
            }
        });
        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Question question = snapshot.getValue(Question.class);
                    if (!categorySet.contains(question.getCategory())) {
                        allitem.add(question);
                        categorySet.add(question.getCategory());
                    }
                }
                categoryAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
    public Task<List<Question>> getQuestionsByCategory(String category) {

        TaskCompletionSource<List<Question>> tcs = new TaskCompletionSource<>();
        ArrayList<Question> questions = new ArrayList<>();

        database.orderByChild("category").equalTo(category).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot questionSnapshot : dataSnapshot.getChildren()) {
                    Question question = questionSnapshot.getValue(Question.class);
                    questions.add(question);
                }

                // Resolve the task with the list of questions
                tcs.setResult(questions);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Reject the task with the database error
                tcs.setException(databaseError.toException());
            }
        });

        return tcs.getTask();
    }
}