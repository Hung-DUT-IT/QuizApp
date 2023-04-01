package com.example.quizapp.Adapter;

import android.content.Context;
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
import com.example.quizapp.View.HomeFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {

    Context context;
    private HomeFragment homeFragment;
    private CategoryClickListener categoryClickListener;
    ArrayList<Question> list;

    public interface CategoryClickListener {
        void onCategoryClick(Question question);
    }

    public CategoryAdapter(Context context, ArrayList<Question> list, CategoryClickListener listener) {
        this.context = context;
        this.list = list;
        this.categoryClickListener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.category_item_row, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Question question = list.get(position);
        holder.cate.setText(question.getCategory());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView cate;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cate = itemView.findViewById(R.id.tv_name_category);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Navigation.findNavController(v).navigate(R.id.playGameFragment);

                }
            });
        }
    }

    public void setHomeFragment(HomeFragment homeFragment) {
        this.homeFragment = homeFragment;
    }
}