package com.example.quizapp.ViewModel;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizapp.Model.Entity.Question;
import com.example.quizapp.Model.Helper.FirebaseUtils;
import com.example.quizapp.R;
import com.example.quizapp.databinding.CategoryItemRowBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;


import java.io.Serializable;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {
    private List<String> questions;

    public CategoryAdapter(List<String> questions) {
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
        String category = questions.get(position);
        holder.binding.tvNameCategory.setText(category);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUtils.getInstance().getQuestionsByCategory("questions",category).addOnCompleteListener(new OnCompleteListener<List<Question>>() {
                    @Override
                    public void onComplete(@NonNull Task<List<Question>> task) {
                        if (task.isSuccessful()) {
                            List<Question> questions = task.getResult();

                            AlertDialog.Builder mDialog = new AlertDialog.Builder(v.getContext());
                            LayoutInflater inflater = LayoutInflater.from(v.getContext());
                            View mView = inflater.inflate(R.layout.option_playgame, null);

                            mDialog.setView(mView);

                            AlertDialog dialog = mDialog.create();
                            dialog.setCancelable(true);
                            Button btnPlayAlone =  mView.findViewById(R.id.btn_play_alone);
                            Button btnPlayFriends =  mView.findViewById(R.id.btn_play_friend);
                            btnPlayAlone.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    dialog.dismiss();

                                    Bundle args = new Bundle();
                                    args.putSerializable("questions", (Serializable) questions);
                                    Navigation.findNavController(v).navigate(R.id.playGameFragment, args);
                                }
                            });
                            btnPlayFriends.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    dialog.dismiss();
                                    Bundle args = new Bundle();
                                    args.putSerializable("questions", (Serializable) questions);
                                    Navigation.findNavController(v).navigate(R.id.createOrJoinFragment, args);
                                }
                            });
                            dialog.show();
                        } else {
                            Exception ex = task.getException();
                        }
                    }
                });
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
}