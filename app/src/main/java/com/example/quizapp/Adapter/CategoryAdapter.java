package com.example.quizapp.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizapp.Model.Question;
import com.example.quizapp.R;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {

    private List<Question> allitem;
    private OnItemClickListener listener;

    public CategoryAdapter(List<Question> allitem) {
        this.allitem = allitem;
    }

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
<<<<<<< HEAD
        Question question = list.get(position);
        holder.cate.setText(question.getCategory());
=======
        Question question = allitem.get(position);
        holder.categoryName.setText(question.getCategory());
>>>>>>> eb743f309e4d11ad34ff3f457406c7c504452d1b
    }

    @Override
    public int getItemCount() {
        return allitem.size();
    }

<<<<<<< HEAD
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView cate;
=======
    public interface OnItemClickListener {
        void onItemClick(String category);
    }
>>>>>>> eb743f309e4d11ad34ff3f457406c7c504452d1b

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView categoryName;

        MyViewHolder(View itemView) {
            super(itemView);
<<<<<<< HEAD
            cate = itemView.findViewById(R.id.tv_name_category);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Navigation.findNavController(v).navigate(R.id.playGameFragment);

=======
            categoryName = itemView.findViewById(R.id.tv_name_category);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        String category = allitem.get(getAdapterPosition()).getCategory();
                        listener.onItemClick(category); // Call onItemClick method of listener
                    }
>>>>>>> eb743f309e4d11ad34ff3f457406c7c504452d1b
                }
            });
        }
    }
}
