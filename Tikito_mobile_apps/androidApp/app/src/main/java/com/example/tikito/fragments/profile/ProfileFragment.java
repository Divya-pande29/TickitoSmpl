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
import com.example.tikito.activities.LoginActivity;
import com.example.tikito.entities.ApiResponse;
import com.example.tikito.entities.UserDto;
import com.example.tikito.utils.API;
import com.example.tikito.utils.SessionManager;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.imageview.ShapeableImageView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {

    private ImageButton btnLogout;
    private MaterialCardView cardCurrentBookings;
    private MaterialCardView cardHistory;
    private MaterialCardView cardUpdateProfile;

    private ShapeableImageView imgProfile;
    private TextView textUserName;

    private SessionManager sessionManager;

    public ProfileFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        sessionManager = new SessionManager(requireContext());

        initViews(view);

        API.getApi(requireContext())
                .getUserAPI()
                .getProfile()
                .enqueue(new Callback<ApiResponse<UserDto>>() {
                    @Override
                    public void onResponse(Call<ApiResponse<UserDto>> call,
                                           Response<ApiResponse<UserDto>> response) {


                        if (response.isSuccessful()
                                && response.body() != null
                                && response.body().getData() != null) {
                            UserDto user = response.body().getData();


                            // First Name
                            textUserName.setText("Hello, " + user.getFirstName() + "!");


                        }
                    }

                    @Override
                    public void onFailure(Call<ApiResponse<UserDto>> call,
                                          Throwable t) {
                        t.printStackTrace();
                    }
                });


        setListeners();

        return view;
    }

    private void initViews(View view) {

        btnLogout = view.findViewById(R.id.btnLogout);

        textUserName = view.findViewById(R.id.textUserName);

        cardCurrentBookings = view.findViewById(R.id.cardCurrentBookings);
        cardHistory = view.findViewById(R.id.cardHistory);
        cardUpdateProfile = view.findViewById(R.id.cardUpdateProfile);
        imgProfile = view.findViewById(R.id.imgProfile);

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

        cardUpdateProfile.setOnClickListener(v -> {

            requireActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, new ChangePasswordFragment())
                    .addToBackStack(null)
                    .commit();

        });
        btnLogout.setOnClickListener(v -> {

            sessionManager.logout();

            Toast.makeText(requireContext(),
                    "Logged out successfully",
                    Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(requireActivity(), LoginActivity.class);

            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

            startActivity(intent);

            requireActivity().finish();

        });
    }

}