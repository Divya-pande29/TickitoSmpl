package com.example.tikito.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.tikito.R;
import com.example.tikito.entities.ApiResponse;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.example.tikito.entities.UserDto;
import com.example.tikito.utils.API;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResetPasswordActivity extends AppCompatActivity {

    private TextInputEditText editOtp;
    private TextInputEditText editNewPassword;
    private TextInputEditText editConfirmPassword;
    private MaterialButton btnReset;
    private TextView textLogin;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);


        email = getIntent().getStringExtra("email");

        initViews();
        initListeners();
    }

    private void initViews() {

        editOtp = findViewById(R.id.editOtp);

        editNewPassword = findViewById(R.id.editNewPassword);

        editConfirmPassword = findViewById(R.id.editConfirmPassword);

        btnReset = findViewById(R.id.btnReset);

        textLogin = findViewById(R.id.textLogin);
    }

    private void initListeners() {

        btnReset.setOnClickListener(v -> {

            if (validateInputs()) {
                resetPassword();
            }

        });

        textLogin.setOnClickListener(v -> finish());
    }

    private boolean validateInputs() {

        String otp =
                editOtp.getText().toString().trim();

        String password =
                editNewPassword.getText().toString().trim();

        String confirm =
                editConfirmPassword.getText().toString().trim();

        if (TextUtils.isEmpty(otp)) {
            editOtp.setError("Enter OTP");
            editOtp.requestFocus();
            return false;
        }

        if (otp.length() != 6) {
            editOtp.setError("OTP must be 6 digits");
            editOtp.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(password)) {
            editNewPassword.setError("Enter password");
            editNewPassword.requestFocus();
            return false;
        }

        if (password.length() < 4) {
            editNewPassword.setError("Minimum 4 characters");
            editNewPassword.requestFocus();
            return false;
        }

        if (!password.equals(confirm)) {
            editConfirmPassword.setError("Passwords don't match");
            editConfirmPassword.requestFocus();
            return false;
        }

        return true;
    }

    private void resetPassword() {

        UserDto dto = new UserDto();

        dto.setEmail(email);

        dto.setOtp(
                Integer.parseInt(
                        editOtp.getText().toString()));

        dto.setNewPassword(
                editNewPassword.getText().toString().trim());

        API.getApi(this)
                .getUserAPI()
                .resetPassword(dto)
                .enqueue(new Callback<ApiResponse<String>>() {

                    @Override
                    public void onResponse(
                            Call<ApiResponse<String>> call,
                            Response<ApiResponse<String>> response) {

                        if (response.isSuccessful()
                                && response.body() != null
                                && "success".equalsIgnoreCase(
                                response.body().getStatus())) {

                            Toast.makeText(
                                    ResetPasswordActivity.this,
                                    response.body().getData(),
                                    Toast.LENGTH_SHORT
                            ).show();

                            Intent intent =
                                    new Intent(
                                            ResetPasswordActivity.this,
                                            LoginActivity.class);

                            intent.addFlags(
                                    Intent.FLAG_ACTIVITY_CLEAR_TOP);

                            startActivity(intent);

                            finish();

                        } else {

                            Toast.makeText(
                                    ResetPasswordActivity.this,
                                    "Password reset failed",
                                    Toast.LENGTH_SHORT
                            ).show();
                        }
                    }

                    @Override
                    public void onFailure(
                            Call<ApiResponse<String>> call,
                            Throwable t) {

                        Toast.makeText(
                                ResetPasswordActivity.this,
                                "Network Error : " + t.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                });
    }
}