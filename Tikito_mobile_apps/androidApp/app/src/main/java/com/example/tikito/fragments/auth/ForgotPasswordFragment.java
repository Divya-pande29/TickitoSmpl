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


public class ForgotPasswordFragment extends Fragment {

    private TextInputEditText editEmail;
    private MaterialButton btnResetPassword;
    private TextView textLogin;

    public ForgotPasswordFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_forgot_password, container, false);

        editEmail = view.findViewById(R.id.editEmail);
        btnResetPassword = view.findViewById(R.id.btnResetPassword);
        textLogin = view.findViewById(R.id.textLogin);

        btnResetPassword.setOnClickListener(v -> {

            String email = editEmail.getText().toString().trim();

            if (TextUtils.isEmpty(email)) {
                editEmail.setError("Enter Email");
                return;
            }

            Toast.makeText(getContext(),
                    "Reset Password API will be called here",
                    Toast.LENGTH_SHORT).show();
        });

        textLogin.setOnClickListener(v ->
                requireActivity().onBackPressed());

        return view;
    }
}