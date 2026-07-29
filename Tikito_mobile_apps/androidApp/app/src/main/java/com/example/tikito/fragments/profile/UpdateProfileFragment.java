package com.example.tikito.fragments.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.tikito.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textfield.TextInputEditText;

public class UpdateProfileFragment extends Fragment {

    private ShapeableImageView imgProfile;
    private MaterialButton btnChangePicture;
    private MaterialButton btnSave;
    private TextInputEditText editPassword;

    public UpdateProfileFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_update_profile, container, false);

        imgProfile = view.findViewById(R.id.imgProfile);
        btnChangePicture = view.findViewById(R.id.btnChangePicture);
        btnSave = view.findViewById(R.id.btnSave);
        editPassword = view.findViewById(R.id.editPassword);

        btnChangePicture.setOnClickListener(v ->
                Toast.makeText(getContext(), "Select Profile Picture", Toast.LENGTH_SHORT).show());

        btnSave.setOnClickListener(v ->
                Toast.makeText(getContext(), "Save Changes", Toast.LENGTH_SHORT).show());

        return view;
    }
}