package com.example.tikito.activities;

import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.tikito.R;
import com.example.tikito.fragments.home.EventListFragment;
import com.example.tikito.fragments.profile.ProfileFragment;

public class HomeActivity extends AppCompatActivity {


    LinearLayout navHome, navProfile, navReview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        navHome = findViewById(R.id.navHome);
        navProfile = findViewById(R.id.navProfile);


        // Default fragment
        loadFragment(new EventListFragment());


        navHome.setOnClickListener(v -> {
            loadFragment(new EventListFragment());
        });


        navProfile.setOnClickListener(v -> {
            loadFragment(new ProfileFragment());
        });


    }


    private void loadFragment(Fragment fragment){

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit();


    }
}
