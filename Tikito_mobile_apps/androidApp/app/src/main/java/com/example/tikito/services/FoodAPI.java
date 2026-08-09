package com.example.tikito.services;

import com.example.tikito.entities.ApiResponse;
import com.example.tikito.entities.Food;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface FoodAPI {

    @GET("tikito/foods")
    Call<ApiResponse<List<Food>>> getAllFoods();

    @GET("tikito/foods/available")
    Call<ApiResponse<List<Food>>> getAvailableFoods();

    @GET("tikito/foods/{foodId}")
    Call<ApiResponse<Food>> getFoodById(
            @Path("foodId") long foodId
    );

    @GET("tikito/foods/name/{foodName}")
    Call<ApiResponse<List<Food>>> searchFoodByName(
            @Path("foodName") String foodName
    );

}