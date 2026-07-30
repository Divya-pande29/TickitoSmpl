package com.example.tikito.activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.TextView;
import android.widget.Toast;

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

public class RegisterActivity extends AppCompatActivity {

    private TextInputEditText editFirstName;
    private TextInputEditText editLastName;
    private TextInputEditText editEmail;
    private TextInputEditText editPhone;
    private TextInputEditText editPassword;

    private MaterialButton btnRegister;
    private TextView textLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initViews();
        initListeners();
    }

    private void initViews() {

        editFirstName = findViewById(R.id.editFirstName);
        editLastName = findViewById(R.id.editLastName);
        editEmail = findViewById(R.id.editEmail);
        editPhone = findViewById(R.id.editPhone);
        editPassword = findViewById(R.id.editPassword);

        btnRegister = findViewById(R.id.btnRegister);
        textLogin = findViewById(R.id.textLogin);
    }

    private void initListeners() {

        btnRegister.setOnClickListener(v -> {

            if (validateInputs()) {
                register();
            }

        });

        textLogin.setOnClickListener(v -> finish());
    }

    private boolean validateInputs() {

        String firstName = editFirstName.getText().toString().trim();
        String lastName = editLastName.getText().toString().trim();
        String email = editEmail.getText().toString().trim();
        String phone = editPhone.getText().toString().trim();
        String password = editPassword.getText().toString().trim();

        if (TextUtils.isEmpty(firstName)) {
            editFirstName.setError("Enter first name");
            editFirstName.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(lastName)) {
            editLastName.setError("Enter last name");
            editLastName.requestFocus();
            return false;
        }

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

        if (TextUtils.isEmpty(phone)) {
            editPhone.setError("Enter phone number");
            editPhone.requestFocus();
            return false;
        }

        if (phone.length() != 10) {
            editPhone.setError("Enter valid phone number");
            editPhone.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(password)) {
            editPassword.setError("Enter password");
            editPassword.requestFocus();
            return false;
        }

        if (password.length() < 4) {
            editPassword.setError("Password must be at least 4 characters");
            editPassword.requestFocus();
            return false;
        }

        return true;
    }

    private void register() {

        UserDto user = new UserDto(
                editFirstName.getText().toString().trim(),
                editLastName.getText().toString().trim(),
                editEmail.getText().toString().trim(),
                editPassword.getText().toString().trim(),
                editPhone.getText().toString().trim()
        );

        API.getApi()
                .getUserAPI()
                .register(user)
                .enqueue(new Callback<ApiResponse<UserDto>>() {

                    @Override
                    public void onResponse(Call<ApiResponse<UserDto>> call,
                                           Response<ApiResponse<UserDto>> response) {

                        if (response.isSuccessful()
                                && response.body() != null
                                && "success".equalsIgnoreCase(response.body().getStatus())) {

                            Toast.makeText(RegisterActivity.this,
                                    "Registration Successful",
                                    Toast.LENGTH_SHORT).show();

                            finish();

                        } else {

                            Toast.makeText(RegisterActivity.this,
                                    "Registration Failed",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ApiResponse<UserDto>> call,
                                          Throwable t) {

                        Toast.makeText(RegisterActivity.this,
                                "Network Error : " + t.getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                });
    }
}