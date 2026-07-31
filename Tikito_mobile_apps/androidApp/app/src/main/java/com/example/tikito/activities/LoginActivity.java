package com.example.tikito.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tikito.R;
import com.example.tikito.entities.ApiResponse;
import com.example.tikito.entities.LoginRequest;
import com.example.tikito.entities.LoginResponse;
import com.example.tikito.services.UserAPI;
import com.example.tikito.utils.API;
import com.example.tikito.utils.SessionManager;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText editEmail;
    private TextInputEditText editPassword;

    private MaterialButton btnLogin;
    private MaterialButton btnSignUp;

    private TextView textForgotPassword;

    private CheckBox cbRememberMe;

    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sessionManager = new SessionManager(this);

        if (sessionManager.isLoggedIn()) {
            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
            finish();
            return;
        }

        initViews();
        initListeners();
    }

    private void initViews() {

        editEmail = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassword);

        btnLogin = findViewById(R.id.btnLogin);
        btnSignUp = findViewById(R.id.btnSignUp);

        textForgotPassword = findViewById(R.id.textForgotPassword);

        cbRememberMe = findViewById(R.id.cbRememberMe);
    }

    private void initListeners() {

        btnLogin.setOnClickListener(view -> login());

        btnSignUp.setOnClickListener(view -> {

            Intent intent = new Intent(LoginActivity.this,
                    RegisterActivity.class);

            startActivity(intent);

        });

        textForgotPassword.setOnClickListener(view -> {

            Intent intent = new Intent(
                    LoginActivity.this,
                    ForgotPasswordActivity.class);

            startActivity(intent);

        });

    }

    private boolean validateInputs() {

        String email = editEmail.getText().toString().trim();
        String password = editPassword.getText().toString().trim();

        if (email.isEmpty()) {
            editEmail.setError("Email is required");
            editEmail.requestFocus();
            return false;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editEmail.setError("Enter valid email");
            editEmail.requestFocus();
            return false;
        }

        if (password.isEmpty()) {
            editPassword.setError("Password is required");
            editPassword.requestFocus();
            return false;
        }

        return true;
    }

    private void login() {

        if (!validateInputs())
            return;

        LoginRequest request = new LoginRequest(
                editEmail.getText().toString().trim(),
                editPassword.getText().toString().trim()
        );

        UserAPI userAPI = API.getApi(this).getUserAPI();

        userAPI.login(request).enqueue(new Callback<ApiResponse<LoginResponse>>() {

            @Override
            public void onResponse(Call<ApiResponse<LoginResponse>> call,
                                   Response<ApiResponse<LoginResponse>> response) {

                if (response.isSuccessful()
                        && response.body() != null
                        && "success".equalsIgnoreCase(response.body().getStatus())) {

                    LoginResponse loginResponse = response.body().getData();

                    sessionManager.saveLoginSession(
                            loginResponse.getJwtToken(),
                            loginResponse.getUserId(),
                            loginResponse.getEmail(),
                            loginResponse.getFirstName(),
                            loginResponse.getLastName(),
                            loginResponse.getRole()
                    );

                    Toast.makeText(LoginActivity.this,
                            "Login Successful",
                            Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(LoginActivity.this,
                            HomeActivity.class);

                    startActivity(intent);
                    finish();

                } else {

                    Toast.makeText(LoginActivity.this,
                            "Invalid email or password",
                            Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<ApiResponse<LoginResponse>> call, Throwable t) {
                t.printStackTrace();

                Toast.makeText(
                        LoginActivity.this,
                        "Network Error: " + t.getMessage(),
                        Toast.LENGTH_LONG
                ).show();
            }
        });

    }

}