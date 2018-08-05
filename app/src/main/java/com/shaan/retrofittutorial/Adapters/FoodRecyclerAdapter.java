package com.shaan.retrofittutorial.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.shaan.retrofittutorial.Model.Food;
import com.shaan.retrofittutorial.R;

import java.util.List;

public class FoodRecyclerAdapter extends RecyclerView.Adapter<FoodRecyclerAdapter.MyViewHolder> {

    List<Food> foods;
    Context context;

    public FoodRecyclerAdapter(Context context, List<Food> foods) {
        this.context = context;
        this.foods = foods;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.raw_food_item, parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tvName.setText(foods.get(position).getName());
        holder.tvCalories.setText("Calories : " + foods.get(position).getCalories());
        Glide.with(context)
                .load(foods.get(position).getImagePath())
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return foods.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvName, tvCalories;
        ImageView imageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvCalories = itemView.findViewById(R.id.tvCalories);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}
