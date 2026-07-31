package com.example.tikito.fragments.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.tikito.R;
import com.example.tikito.activities.BookSeatActivity;
import com.example.tikito.activities.HomeActivity;
import com.example.tikito.activities.TicketHistoryActivity;
import com.example.tikito.activities.UpcomingTicketsActivity;
import com.google.android.material.card.MaterialCardView;

public class ProfileFragment extends Fragment {

    private ImageButton btnLogout;


    private MaterialCardView cardCurrentBookings;
    private MaterialCardView cardHistory;
    private MaterialCardView cardUpdateProfile;

    private TextView textUserName;

    Button booking;

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

        booking = view.findViewById(R.id.booking);
        btnLogout = view.findViewById(R.id.btnLogout);

        textUserName = view.findViewById(R.id.textUserName);

        cardCurrentBookings = view.findViewById(R.id.cardCurrentBookings);
        cardHistory = view.findViewById(R.id.cardHistory);
        cardUpdateProfile = view.findViewById(R.id.cardUpdateProfile);

    }

    private void setListeners() {


        cardCurrentBookings.setOnClickListener(v ->
        {
            Intent intent = new Intent(requireContext(), UpcomingTicketsActivity.class);
            startActivity(intent);
        });

        cardHistory.setOnClickListener(v ->
        {
            Intent intent = new Intent(requireContext(), TicketHistoryActivity.class);
            startActivity(intent);
        });

        cardUpdateProfile.setOnClickListener(v ->
                Toast.makeText(getContext(), "Update Profile", Toast.LENGTH_SHORT).show());

        btnLogout.setOnClickListener(v ->
                Toast.makeText(getContext(), "Logout", Toast.LENGTH_SHORT).show());

        booking.setOnClickListener(v ->
        {
            Intent intent = new Intent(requireContext(), BookSeatActivity.class);
            startActivity(intent);
        });

    }

}