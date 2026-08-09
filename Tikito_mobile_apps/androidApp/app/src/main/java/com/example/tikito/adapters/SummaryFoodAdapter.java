package com.example.tikito.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tikito.R;
import com.example.tikito.entities.Food;

import java.util.List;

public class SummaryFoodAdapter extends RecyclerView.Adapter<SummaryFoodAdapter.ViewHolder> {

    private final List<Food> foodList;

    public SummaryFoodAdapter(List<Food> foodList) {
        this.foodList = foodList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adaptor_summary, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Food food = foodList.get(position);

        holder.txtFoodName.setText(food.getFoodName());

        holder.txtQuantity.setText("Qty : " + food.getQuantity());

        holder.txtPrice.setText("₹" + (food.getPrice() * food.getQuantity()));

        Glide.with(holder.itemView.getContext())
                .load(food.getImageUrl())
                .placeholder(R.drawable.food_placeholder)
                .error(R.drawable.food_placeholder)
                .into(holder.imgFood);
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgFood;
        TextView txtFoodName;
        TextView txtQuantity;
        TextView txtPrice;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgFood = itemView.findViewById(R.id.imgFood);
            txtFoodName = itemView.findViewById(R.id.txtFoodName);
            txtQuantity = itemView.findViewById(R.id.txtQuantity);
            txtPrice = itemView.findViewById(R.id.txtPrice);
        }
    }
}