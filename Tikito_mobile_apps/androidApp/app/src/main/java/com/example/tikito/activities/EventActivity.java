package com.example.tikito.activities;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tikito.R;

public class EventActivity extends AppCompatActivity {

    private ImageView btnBack;
    private TextView tvTitle;
    private EditText etSearch;

    // Now Showing
    private ImageView imgPosterEvilDead;
    private TextView tvTitleEvilDead, tvRatingEvilDead;

    private ImageView imgPosterOdyssey;
    private TextView tvTitleOdyssey, tvRatingOdyssey;

    // Upcoming Events
    private ImageView imgPosterMirzapur;
    private TextView tvTitleMirzapur, tvRatingMirzapur;

    private ImageView imgPosterAvengers;
    private TextView tvTitleAvengers, tvRatingAvengers;

    // Popular Events
    private ImageView imgPosterJanNeta;
    private TextView tvTitleJanNeta, tvRatingJanNeta;

    private ImageView imgPosterTeraYaar;
    private TextView tvTitleTeraYaar, tvRatingTeraYaar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);


        initViews();

        tvTitle.setText("Events");
        etSearch.setHint("Search Events...");

        btnBack.setOnClickListener(v -> finish());

        loadEventData();
    }

    private void initViews() {

        btnBack = findViewById(R.id.btnBack);
        tvTitle = findViewById(R.id.tvTitle);
        etSearch = findViewById(R.id.etSearch);

        // Now Showing
        imgPosterEvilDead = findViewById(R.id.imgPosterEvilDead);
        tvTitleEvilDead = findViewById(R.id.tvTitleEvilDead);
        tvRatingEvilDead = findViewById(R.id.tvRatingEvilDead);

        imgPosterOdyssey = findViewById(R.id.imgPosterOdyssey);
        tvTitleOdyssey = findViewById(R.id.tvTitleOdyssey);
        tvRatingOdyssey = findViewById(R.id.tvRatingOdyssey);

        // Upcoming
        imgPosterMirzapur = findViewById(R.id.imgPosterMirzapur);
        tvTitleMirzapur = findViewById(R.id.tvTitleMirzapur);
        tvRatingMirzapur = findViewById(R.id.tvRatingMirzapur);

        imgPosterAvengers = findViewById(R.id.imgPosterAvengers);
        tvTitleAvengers = findViewById(R.id.tvTitleAvengers);
        tvRatingAvengers = findViewById(R.id.tvRatingAvengers);

        // Popular
        imgPosterJanNeta = findViewById(R.id.imgPosterJanNeta);
        tvTitleJanNeta = findViewById(R.id.tvTitleJanNeta);
        tvRatingJanNeta = findViewById(R.id.tvRatingJanNeta);

        imgPosterTeraYaar = findViewById(R.id.imgPosterTeraYaar);
        tvTitleTeraYaar = findViewById(R.id.tvTitleTeraYaar);
        tvRatingTeraYaar = findViewById(R.id.tvRatingTeraYaar);
    }

    private void loadEventData() {

        // ================= TRENDING EVENTS =================

        imgPosterEvilDead.setImageResource(R.drawable.back); // Replace with tech_fest
        tvTitleEvilDead.setText("Tech Fest 2026");
        tvRatingEvilDead.setText("Pune • Today");

        imgPosterOdyssey.setImageResource(R.drawable.back); // Replace with startup_summit
        tvTitleOdyssey.setText("Startup Summit");
        tvRatingOdyssey.setText("Mumbai • 10:00 AM");

        // ================= UPCOMING EVENTS =================

        imgPosterMirzapur.setImageResource(R.drawable.back); // Replace with food_festival
        tvTitleMirzapur.setText("Food Festival");
        tvRatingMirzapur.setText("12 Aug 2026");

        imgPosterAvengers.setImageResource(R.drawable.back); // Replace with auto_expo
        tvTitleAvengers.setText("Auto Expo");
        tvRatingAvengers.setText("25 Aug 2026");

        // ================= POPULAR EVENTS =================

        imgPosterJanNeta.setImageResource(R.drawable.back); // Replace with comic_con
        tvTitleJanNeta.setText("Comic Con");
        tvRatingJanNeta.setText("★ 4.9 (18K)");

        imgPosterTeraYaar.setImageResource(R.drawable.back); // Replace with book_fair
        tvTitleTeraYaar.setText("Book Fair");
        tvRatingTeraYaar.setText("★ 4.8 (15K)");
    }
}

