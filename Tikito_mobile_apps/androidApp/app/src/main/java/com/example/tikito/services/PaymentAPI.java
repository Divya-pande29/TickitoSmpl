package com.example.tikito.services;

import com.example.tikito.entities.ApiResponse;
import com.example.tikito.entities.CreateOrderRequest;
import com.example.tikito.entities.CreateOrderResponse;
import com.example.tikito.entities.VerifyPaymentRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface PaymentAPI {

    @POST("tikito/payment/create-order")
    Call<ApiResponse<CreateOrderResponse>> createOrder(
            @Body CreateOrderRequest request
    );

    @POST("tikito/payment/verify")
    Call<ApiResponse<String>> verifyPayment(
            @Body VerifyPaymentRequest request
    );

}