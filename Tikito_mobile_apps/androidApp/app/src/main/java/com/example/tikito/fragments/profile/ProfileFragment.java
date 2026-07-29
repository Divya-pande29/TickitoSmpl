package com.example.tikito.fragments.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.tikito.R;
import com.google.android.material.card.MaterialCardView;

public class ProfileFragment extends Fragment {

    private ImageButton btnLogout;


    private MaterialCardView cardCurrentBookings;
    private MaterialCardView cardHistory;
    private MaterialCardView cardUpdateProfile;

    private TextView textUserName;

    public ProfileFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        initViews(view);

        setListeners();

        return view;
    }

    private void initViews(View view) {

        btnLogout = view.findViewById(R.id.btnLogout);

        textUserName = view.findViewById(R.id.textUserName);

        cardCurrentBookings = view.findViewById(R.id.cardCurrentBookings);
        cardHistory = view.findViewById(R.id.cardHistory);
        cardUpdateProfile = view.findViewById(R.id.cardUpdateProfile);

    }

    private void setListeners() {


        cardCurrentBookings.setOnClickListener(v ->
                Toast.makeText(getContext(), "Current Bookings", Toast.LENGTH_SHORT).show());

        cardHistory.setOnClickListener(v ->
                Toast.makeText(getContext(), "Booking History", Toast.LENGTH_SHORT).show());

        cardUpdateProfile.setOnClickListener(v ->
                Toast.makeText(getContext(), "Update Profile", Toast.LENGTH_SHORT).show());

        btnLogout.setOnClickListener(v ->
                Toast.makeText(getContext(), "Logout", Toast.LENGTH_SHORT).show());

    }

}