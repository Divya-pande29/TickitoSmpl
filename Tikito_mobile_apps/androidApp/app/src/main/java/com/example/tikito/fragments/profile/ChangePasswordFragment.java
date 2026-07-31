package com.example.tikito.fragments.profile;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.tikito.R;
import com.example.tikito.entities.ApiResponse;
import com.example.tikito.entities.UserDto;
import com.example.tikito.utils.API;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordFragment extends Fragment {

    private ShapeableImageView imgProfile;

    private TextInputEditText editOldPassword;
    private TextInputEditText editNewPassword;
    private TextInputEditText editConfirmPassword;

    private MaterialButton btnSave;

    public ChangePasswordFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_change_password,
                container,
                false);

        imgProfile = view.findViewById(R.id.imgProfile);

        editOldPassword = view.findViewById(R.id.editOldPassword);
        editNewPassword = view.findViewById(R.id.editNewPassword);
        editConfirmPassword = view.findViewById(R.id.editConfirmPassword);

        btnSave = view.findViewById(R.id.btnSave);

        btnSave.setOnClickListener(v -> changePassword());

        return view;
    }

    private void changePassword() {

        String oldPassword =
                editOldPassword.getText().toString().trim();

        String newPassword =
                editNewPassword.getText().toString().trim();

        String confirmPassword =
                editConfirmPassword.getText().toString().trim();

        if (TextUtils.isEmpty(oldPassword)
                || TextUtils.isEmpty(newPassword)
                || TextUtils.isEmpty(confirmPassword)) {

            Toast.makeText(getContext(),
                    "Please fill all fields",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        System.out.println("Old Password: [" + oldPassword + "]");
        System.out.println("New Password: [" + newPassword + "]");
        System.out.println("Confirm Password: [" + confirmPassword + "]");



        Log.d("PASSWORD_DEBUG", "Old: [" + oldPassword + "]");
        Log.d("PASSWORD_DEBUG", "New: [" + newPassword + "]");
        Log.d("PASSWORD_DEBUG", "Confirm: [" + confirmPassword + "]");

        if (!newPassword.contentEquals(confirmPassword)) {

            Toast.makeText(
                    getContext(),
                    "New=[" + newPassword + "] Confirm=[" + confirmPassword + "]",
                    Toast.LENGTH_LONG
            ).show();

            return;
        }

        UserDto dto = new UserDto();

        dto.setOldPassword(oldPassword);
        dto.setNewPassword(newPassword);

        btnSave.setEnabled(false);

        API.getApi(requireContext())
                .getUserAPI()
                .changePassword(dto)
                .enqueue(new Callback<ApiResponse<String>>() {

                    @Override
                    public void onResponse(Call<ApiResponse<String>> call,
                                           Response<ApiResponse<String>> response) {

                        btnSave.setEnabled(true);

                        if (response.isSuccessful()
                                && response.body() != null
                                && "success".equalsIgnoreCase(response.body().getStatus())) {

                            Toast.makeText(getContext(),
                                    "Password changed successfully",
                                    Toast.LENGTH_SHORT).show();

                            editOldPassword.setText("");
                            editNewPassword.setText("");
                            editConfirmPassword.setText("");
                            requireActivity()
                                    .getSupportFragmentManager()
                                    .popBackStack();

                        } else {

                            if (response.errorBody() != null) {
                                try {
                                    Toast.makeText(getContext(),
                                            response.errorBody().string(),
                                            Toast.LENGTH_LONG).show();
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            } else {
                                Toast.makeText(getContext(),
                                        "Failed to change password",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ApiResponse<String>> call,
                                          Throwable t) {

                        btnSave.setEnabled(true);

                        Toast.makeText(getContext(),
                                t.getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                });
    }
}