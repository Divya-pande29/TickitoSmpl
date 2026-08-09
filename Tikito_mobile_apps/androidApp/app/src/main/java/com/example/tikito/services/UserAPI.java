package com.example.tikito.services;

import com.example.tikito.entities.ApiResponse;
import com.example.tikito.entities.LoginRequest;
import com.example.tikito.entities.LoginResponse;
import com.example.tikito.entities.UserDto;

import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;

public interface UserAPI {

    @POST("tikito/auth/login")
    Call<ApiResponse<LoginResponse>> login(@Body LoginRequest request);

    @POST("tikito/user/register")
    Call<ApiResponse<UserDto>> register(@Body UserDto userDto);

    @POST("tikito/user/forgot-password")
    Call<ApiResponse<String>> forgotPassword(@Body UserDto dto);

    @GET("tikito/user/profile")
    Call<ApiResponse<UserDto>> getProfile();

    @PUT("tikito/user/password")
    Call<ApiResponse<String>> changePassword(@Body UserDto userDto);

    @POST("tikito/user/reset-password")
    Call<ApiResponse<String>> resetPassword(@Body UserDto dto);

}