package com.example.quizapp.ViewModel;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizapp.Model.Entity.Question;
import com.example.quizapp.Model.Helper.FirebaseUtils;
import com.example.quizapp.R;
import com.example.quizapp.databinding.CategoryItemRowBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletionException;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {

    private List<Question> questions;
    FirebaseUtils firebaseUtils = FirebaseUtils.getInstance();
    public CategoryAdapter(List<Question> questions) {
        this.questions = questions;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CategoryItemRowBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.category_item_row, parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Question question = questions.get(position);
        holder.binding.tvNameCategory.setText(question.getCategory());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String category = question.getCategory();
                try {
                    getQuestionsByCategory(category).addOnCompleteListener(new OnCompleteListener<List<Question>>() {
                        @Override
                        public void onComplete(@NonNull Task<List<Question>> task) {
                            if (task.isSuccessful()) {
                                List<Question> questions = task.getResult();
                                Bundle args = new Bundle();
                                args.putSerializable("questions", (Serializable) questions);
                                Navigation.findNavController(v).navigate(R.id.playGameFragment, args);
                            } else {
                                Exception ex = task.getException();
                            }
                        }
                    });
                }catch (CompletionException e){
                    Log.d("ERROR", e.toString());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        public CategoryItemRowBinding binding;
        MyViewHolder(CategoryItemRowBinding itemBinding) {
            super(itemBinding.getRoot());
            this.binding = itemBinding;
        }
    }
    public Task<List<Question>> getQuestionsByCategory(String category) {

        TaskCompletionSource<List<Question>> tcs = new TaskCompletionSource<>();
        ArrayList<Question> questions = new ArrayList<>();

        FirebaseDatabase.getInstance().getReference("questions").orderByChild("category").equalTo(category).addListenerForSingleValueEvent(new ValueEventListener() {
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