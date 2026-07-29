package com.example.tikito.fragments.auth;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tikito.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;


public class RegisterFragment extends Fragment {

    private TextInputEditText editFirstName;
    private TextInputEditText editLastName;
    private TextInputEditText editEmail;
    private TextInputEditText editPhone;
    private TextInputEditText editPassword;

    private MaterialButton btnRegister;
    private TextView textLogin;


    public RegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        initViews(view);
        clickListeners();

        return view;
    }

    private void initViews(View view) {

        editFirstName = view.findViewById(R.id.editFirstName);
        editLastName = view.findViewById(R.id.editLastName);
        editEmail = view.findViewById(R.id.editEmail);
        editPhone = view.findViewById(R.id.editPhone);
        editPassword = view.findViewById(R.id.editPassword);

        btnRegister = view.findViewById(R.id.btnRegister);
        textLogin = view.findViewById(R.id.textLogin);
    }

    private void clickListeners() {

        btnRegister.setOnClickListener(v -> validateInputs());

        textLogin.setOnClickListener(v ->
                requireActivity().onBackPressed());
    }

    private void validateInputs() {

        String firstName = editFirstName.getText().toString().trim();
        String lastName = editLastName.getText().toString().trim();
        String email = editEmail.getText().toString().trim();
        String phone = editPhone.getText().toString().trim();
        String password = editPassword.getText().toString().trim();

        if (TextUtils.isEmpty(firstName)) {
            editFirstName.setError("Enter First Name");
            editFirstName.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(lastName)) {
            editLastName.setError("Enter Last Name");
            editLastName.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(email)) {
            editEmail.setError("Enter Email");
            editEmail.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(phone)) {
            editPhone.setError("Enter Phone Number");
            editPhone.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            editPassword.setError("Enter Password");
            editPassword.requestFocus();
            return;
        }

        Toast.makeText(getContext(),
                "Validation Successful!",
                Toast.LENGTH_SHORT).show();

        // Backend API call will be added here later.
    }
}