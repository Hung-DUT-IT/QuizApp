package com.example.quizapp.View;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class HomeFragment extends Fragment {
    private DatabaseReference database;
    private CategoryAdapter categoryAdapter;
    private ArrayList<Question> allitem;
    private Set<String> categorySet;
    private MainActivity mainActivity;
    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mainActivity = (MainActivity) getActivity();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        RecyclerView rclCategoryList = view.findViewById(R.id.rv_category);
        rclCategoryList.setHasFixedSize(true);
        rclCategoryList.setLayoutManager(new LinearLayoutManager(getActivity()));

        database = FirebaseDatabase.getInstance().getReference("questions");
        allitem = new ArrayList<>();
        categorySet = new HashSet<>();
        categoryAdapter = new CategoryAdapter(getActivity(), allitem, new CategoryAdapter.CategoryClickListener() {
            @Override
            public void onCategoryClick(Question question) {
                String category = question.getCategory();
                getQuestionsByCategory(category);
            }
        });
        rclCategoryList.setAdapter(categoryAdapter);

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

        return view;
    }
    public void getQuestionsByCategory(String category) {
        // Truy vấn danh sách câu hỏi theo category
        database.orderByChild("category").equalTo(category).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Question> questions = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Question question = snapshot.getValue(Question.class);
                    questions.add(question);
                }
                // Hiển thị danh sách câu hỏi trong một hộp thoại
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Questions for " + category);
                builder.setCancelable(true);
                StringBuilder sb = new StringBuilder();
                for (Question question : questions) {
                    sb.append("\n\n`").append("-> "+question.getQuestion());
                }
                builder.setMessage(sb.toString());
                builder.show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

}