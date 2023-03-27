package com.example.quizapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizapp.Model.Question;
import com.example.quizapp.R;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {

    Context context;
    private CategoryClickListener categoryClickListener;
    ArrayList<Question> list ;

    public interface CategoryClickListener {
        void onCategoryClick(Question question);
    }

    public CategoryAdapter(Context context, ArrayList<Question> list,CategoryClickListener listener) {
        this.context = context;
        this.list = list;
        this.categoryClickListener = listener;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.category_item_row,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Question question = list.get(position);
        holder.cate.setText(question.getCategory());
        holder.cate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryClickListener.onCategoryClick(question);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
            TextView cate;
            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                cate = itemView.findViewById(R.id.tv_name_category);
            }
        }
}