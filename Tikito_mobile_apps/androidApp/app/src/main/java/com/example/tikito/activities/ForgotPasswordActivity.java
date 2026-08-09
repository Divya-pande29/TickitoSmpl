package com.example.tikito.activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tikito.R;
import com.example.tikito.entities.ApiResponse;
import com.example.tikito.entities.UserDto;
import com.example.tikito.utils.API;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordActivity extends AppCompatActivity {

    private TextInputEditText editEmail;

    private MaterialButton btnSendOtp;
    private TextView textLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        initViews();
        initListeners();
    }

    private void initViews() {

        editEmail = findViewById(R.id.editEmail);

        btnSendOtp = findViewById(R.id.btnSendOtp);
        textLogin = findViewById(R.id.textLogin);
    }

    private void initListeners() {

        btnSendOtp.setOnClickListener(v -> {

            if (validateInputs()) {
                sendOtp();
            }

        });

        textLogin.setOnClickListener(v -> finish());
    }

    private boolean validateInputs() {

        String email = editEmail.getText().toString().trim();


        if (TextUtils.isEmpty(email)) {
            editEmail.setError("Enter email");
            editEmail.requestFocus();
            return false;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editEmail.setError("Enter valid email");
            editEmail.requestFocus();
            return false;
        }

        return true;
    }

    private void sendOtp() {

        UserDto dto = new UserDto();

        dto.setEmail(editEmail.getText().toString().trim());

        API.getApi(this)
                .getUserAPI()
                .forgotPassword(dto)
                .enqueue(new Callback<ApiResponse<String>>() {

                    @Override
                    public void onResponse(Call<ApiResponse<String>> call,
                                           Response<ApiResponse<String>> response) {

                        if (response.isSuccessful()
                                && response.body() != null
                                && "success".equalsIgnoreCase(response.body().getStatus())) {

                            Toast.makeText(
                                    ForgotPasswordActivity.this,
                                    response.body().getData(),
                                    Toast.LENGTH_SHORT
                            ).show();

                            Intent intent = new Intent(
                                    ForgotPasswordActivity.this,
                                    ResetPasswordActivity.class);

                            intent.putExtra(
                                    "email",
                                    dto.getEmail());

                            startActivity(intent);

                            finish();
                        } else {

                            Toast.makeText(
                                    ForgotPasswordActivity.this,
                                    "Failed to send OTP",
                                    Toast.LENGTH_SHORT
                            ).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ApiResponse<String>> call,
                                          Throwable t) {

                        Toast.makeText(
                                ForgotPasswordActivity.this,
                                "Network Error : " + t.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                });
    }
}