package com.example.tikito.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tikito.R;
import com.example.tikito.entities.Food;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {

    private final List<Food> foodList;

    public FoodAdapter(List<Food> foodList) {
        this.foodList = foodList;
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adaptor_food, parent, false);

        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {

        Food food = foodList.get(position);

        holder.txtFoodName.setText(food.getFoodName());
        holder.txtDescription.setText(food.getDescription());
        holder.txtPrice.setText("₹" + food.getPrice());

        Glide.with(holder.itemView.getContext())
                .load(food.getImageUrl())
                .placeholder(R.drawable.food_placeholder)
                .error(R.drawable.food_placeholder)
                .into(holder.imgFood);

        holder.txtQuantity.setText(String.valueOf(food.getQuantity()));

        holder.btnPlus.setOnClickListener(v -> {

            food.setQuantity(food.getQuantity() + 1);

            holder.txtQuantity.setText(
                    String.valueOf(food.getQuantity()));

        });

        holder.btnMinus.setOnClickListener(v -> {

            if (food.getQuantity() > 0) {

                food.setQuantity(food.getQuantity() - 1);

                holder.txtQuantity.setText(
                        String.valueOf(food.getQuantity()));
            }

        });
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    static class FoodViewHolder extends RecyclerView.ViewHolder {

        ImageView imgFood;
        TextView txtFoodName;
        TextView txtDescription;
        TextView txtPrice;
        ImageButton btnMinus;
        ImageButton btnPlus;
        TextView txtQuantity;

        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);

            imgFood = itemView.findViewById(R.id.imgFood);
            txtFoodName = itemView.findViewById(R.id.txtFoodName);
            txtDescription = itemView.findViewById(R.id.txtDescription);
            txtPrice = itemView.findViewById(R.id.txtPrice);

            btnMinus = itemView.findViewById(R.id.btnMinus);
            btnPlus = itemView.findViewById(R.id.btnPlus);
            txtQuantity = itemView.findViewById(R.id.txtQuantity);
        }
    }

    public List<Food> getSelectedFoods() {
        List<Food> selected = new ArrayList<>();

        for (Food food : foodList) {
            if (food.getQuantity() > 0) {
                selected.add(food);
            }
        }

        return selected;
    }
}