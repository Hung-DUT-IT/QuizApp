package com.example.quizapp.ViewModel;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizapp.Model.Entity.Question;
import com.example.quizapp.Model.Helper.FirebaseQuestion;
import com.example.quizapp.R;
import com.example.quizapp.View.CreateOrJoinFragment;
import com.example.quizapp.View.GameCompletedFragment;
import com.example.quizapp.View.PlayGameFragment;
import com.example.quizapp.databinding.CategoryItemRowBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;


import java.io.Serializable;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {
    private final List<String> questions;
    private Context mContext;
    public CategoryAdapter(List<String> questions, Context context) {
        this.questions = questions;
        this.mContext = context;
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
        switch (category)
        {
            case "Science":
                holder.binding.imgIconCategory.setImageResource(R.drawable.science);
                break;
            case "Film & TV":
                holder.binding.imgIconCategory.setImageResource(R.drawable.film);
                break;
            case "Society & Culture":
                holder.binding.imgIconCategory.setImageResource(R.drawable.society);
                break;
            case "Food & Drink":
                holder.binding.imgIconCategory.setImageResource(R.drawable.food);
                break;
            case "History":
                holder.binding.imgIconCategory.setImageResource(R.drawable.history);
                break;
            case "General Knowledge":
                holder.binding.imgIconCategory.setImageResource(R.drawable.generaknowledge);
                break;
            case "Sport & Leisure":
                holder.binding.imgIconCategory.setImageResource(R.drawable.sport);
                break;
            case "Geography":
                holder.binding.imgIconCategory.setImageResource(R.drawable.geography);
                break;
            case "Music":
                holder.binding.imgIconCategory.setImageResource(R.drawable.music);
                break;
            default:
                holder.binding.imgIconCategory.setImageResource(R.drawable.art);
                break;
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseQuestion.getInstance().getQuestionsByCategory("questions",category).addOnCompleteListener(new OnCompleteListener<List<Question>>() {
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

                                    Bundle bundle = new Bundle();
                                    bundle.putSerializable("questions", (Serializable) questions);
                                    bundle.putString("play", "alone" );

                                    PlayGameFragment playGameFragment = new PlayGameFragment();
                                    playGameFragment.setArguments(bundle);
                                    ((AppCompatActivity) mContext).getSupportFragmentManager().beginTransaction()
                                            .replace(R.id.fragmentContainerView, playGameFragment)
                                            .addToBackStack(null)
                                            .commit();
                                }
                            });
                            btnPlayFriends.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    dialog.dismiss();

                                    Bundle bundle = new Bundle();
                                    bundle.putSerializable("questions", (Serializable) questions);

                                    CreateOrJoinFragment createOrJoinFragment = new CreateOrJoinFragment();
                                    createOrJoinFragment.setArguments(bundle);
                                    ((AppCompatActivity) mContext).getSupportFragmentManager().beginTransaction()
                                            .replace(R.id.fragmentContainerView, createOrJoinFragment)
                                            .addToBackStack(null)
                                            .commit();
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