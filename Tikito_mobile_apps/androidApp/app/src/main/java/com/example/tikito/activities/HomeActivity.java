package com.example.tikito.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.example.tikito.R;
import com.example.tikito.utils.SessionManager;

public class HomeActivity extends AppCompatActivity {

    Button btn, btnLogout  ;
import com.example.tikito.fragments.HomeFragment;
import com.example.tikito.fragments.ReviewFragment;
import com.example.tikito.fragments.profile.ProfileFragment;

public class HomeActivity extends AppCompatActivity {

    LinearLayout navHome, navProfile, navReview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btn = findViewById(R.id.book);
        btnLogout = findViewById(R.id.btnLogout);

        btn.setOnClickListener(v ->
        {
            Intent intent = new Intent(this, BookSeatActivity.class);
            startActivity(intent);
        });

        btnLogout.setOnClickListener(view -> {

            // Clear saved session
            SessionManager sessionManager = new SessionManager(HomeActivity.this);
            sessionManager.logout();

            // Go back to Login screen
            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

            finish();

        });


        navHome = findViewById(R.id.navHome);
        navProfile = findViewById(R.id.navProfile);
        navReview = findViewById(R.id.navReview);


        // Default fragment
        loadFragment(new HomeFragment());


        navHome.setOnClickListener(v -> {
            loadFragment(new HomeFragment());
        });


        navProfile.setOnClickListener(v -> {
            loadFragment(new ProfileFragment());
        });


        navReview.setOnClickListener(v -> {
            loadFragment(new ReviewFragment());
        });

    }


    private void loadFragment(Fragment fragment){

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit();


    }
}
