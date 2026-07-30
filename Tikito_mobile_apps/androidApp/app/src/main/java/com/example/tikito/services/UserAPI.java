package com.example.tikito.services;

import com.example.tikito.entities.ApiResponse;
import com.example.tikito.entities.LoginRequest;
import com.example.tikito.entities.LoginResponse;
import com.example.tikito.entities.UserDto;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserAPI {

    @POST("tikito/auth/login")
    Call<ApiResponse<LoginResponse>> login(@Body LoginRequest request);

    @POST("tikito/user/register")
    Call<ApiResponse<UserDto>> register(@Body UserDto userDto);

}

