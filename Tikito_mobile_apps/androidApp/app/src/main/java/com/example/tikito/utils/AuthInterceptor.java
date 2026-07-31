package com.example.tikito.utils;

import android.content.Context;
import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthInterceptor implements Interceptor {

    private final SessionManager sessionManager;

    public AuthInterceptor(Context context) {
        sessionManager = new SessionManager(context);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();

        String token = sessionManager.getToken();

        Log.d("JWT_TOKEN", "Token = " + token);

        if (token != null && !token.isEmpty()) {

            request = request.newBuilder()
                    .addHeader("Authorization", "Bearer " + token)
                    .build();

            Log.d("JWT_HEADER",
                    request.header("Authorization"));
        } else {
            Log.d("JWT_HEADER", "Authorization header not added");
        }

        return chain.proceed(request);
    }
}