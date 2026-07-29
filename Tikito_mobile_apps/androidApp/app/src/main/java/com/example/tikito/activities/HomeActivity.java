package com.example.tikito.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tikito.R;
import com.example.tikito.utils.SessionManager;

public class HomeActivity extends AppCompatActivity {

    Button btn, btnLogout  ;
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
    }
}