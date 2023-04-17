package com.example.quizapp.ViewModel;


import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizapp.R;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    ArrayList<String> list;

    CategoryListener categoryListener;
    int previousIndex=-1;
    public CategoryAdapter(ArrayList<String> list, CategoryListener categoryListener)
    {
        this.list=new ArrayList<String>();
        this.list.addAll(list);

        this.categoryListener=categoryListener;

    }

    @NonNull
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_item_row,  parent, false);
        return new ViewHolder(view,this.categoryListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.ViewHolder holder,  int position) {
        holder.textView.setText(list.get(position));

        switch (list.get(position))
        {
            case "Science":
                holder.imgView.setImageResource(R.drawable.science);
                break;
            case "Film & TV":
                holder.imgView.setImageResource(R.drawable.film);
                break;
            case "Society & Culture":
                holder.imgView.setImageResource(R.drawable.society);
                break;
            case "Food & Drink":
                holder.imgView.setImageResource(R.drawable.food);
                break;
            case "History":
                holder.imgView.setImageResource(R.drawable.history);
                break;
            case "General Knowledge":
                holder.imgView.setImageResource(R.drawable.generaknowledge);
                break;
            case "Sport & Leisure":
                holder.imgView.setImageResource(R.drawable.sport);
                break;
            case "Geography":
                holder.imgView.setImageResource(R.drawable.geography);
                break;
            case "Music":
                holder.imgView.setImageResource(R.drawable.music);
                break;
            default:
                holder.imgView.setImageResource(R.drawable.art);
                break;


        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.cardView.setCardBackgroundColor(Color.parseColor("#2E5481"));
                notifyItemChanged(previousIndex);
                previousIndex=position;
                categoryListener.sendDataToFragment(list.get(position));
            }
        });
        holder.cardView.setCardBackgroundColor(Color.parseColor("#30445C"));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;

        public ImageView getImgView() {
            return imgView;
        }

        public void setImgView(ImageView imgView) {
            this.imgView = imgView;
        }

        private ImageView imgView;
        private CardView cardView;


        public ViewHolder(View view, CategoryListener categoryListener) {
            super(view);
            textView = (TextView) view.findViewById(R.id.tv_name_category);
            imgView =(ImageView) view.findViewById(R.id.img_icon_category);
            cardView =(CardView) view.findViewById(R.id.cv_category);

        }

        public TextView getTextView() {
            return textView;
        }



    }
    public interface CategoryListener
    {
        void sendDataToFragment(String category);
    }
}
