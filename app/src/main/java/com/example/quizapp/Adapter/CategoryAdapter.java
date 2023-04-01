package com.example.quizapp.Adapter;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizapp.Model.Question;
import com.example.quizapp.R;
import com.example.quizapp.View.PlayGameFragment;
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

    private List<Question> allitem;

    public CategoryAdapter(List<Question> allitem) {
        this.allitem = allitem;
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Question question = allitem.get(position);
        holder.categoryName.setText(question.getCategory());
    }

    @Override
    public int getItemCount() {
        return allitem.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView categoryName;

        MyViewHolder(View itemView) {
            super(itemView);
            categoryName = itemView.findViewById(R.id.tv_name_category);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String category = allitem.get(getAdapterPosition()).getCategory();
                    try {
                        getQuestionsByCategory(category)
                                .addOnCompleteListener(task -> {
                                    if (task.isSuccessful()) {
                                        List<Question> questions = task.getResult();
                                        Bundle args = new Bundle();
                                        args.putSerializable("questions", (Serializable) questions);
                                        Navigation.findNavController(v).navigate(R.id.playGameFragment, args);
                                    } else {
                                        Exception ex = task.getException();
                                        // TODO: Handle
                                    }
                                });

                    }catch (CompletionException e){
                        Log.d("ERROR", e.toString());
                    }
                }
            });
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