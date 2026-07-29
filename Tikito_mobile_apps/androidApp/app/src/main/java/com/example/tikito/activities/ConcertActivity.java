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

public class ConcertActivity extends AppCompatActivity {


    private ImageView btnBack;
    private TextView tvTitle;
    private EditText etSearch;

    // Now Showing
    private ImageView imgPosterEvilDead;
    private TextView tvTitleEvilDead, tvRatingEvilDead;

    private ImageView imgPosterOdyssey;
    private TextView tvTitleOdyssey, tvRatingOdyssey;

    // Upcoming Concerts
    private ImageView imgPosterMirzapur;
    private TextView tvTitleMirzapur, tvRatingMirzapur;

    private ImageView imgPosterAvengers;
    private TextView tvTitleAvengers, tvRatingAvengers;

    // Popular Concerts
    private ImageView imgPosterJanNeta;
    private TextView tvTitleJanNeta, tvRatingJanNeta;

    private ImageView imgPosterTeraYaar;
    private TextView tvTitleTeraYaar, tvRatingTeraYaar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);


        initViews();

        tvTitle.setText("Concerts");
        etSearch.setHint("Search Concerts...");

        btnBack.setOnClickListener(v -> finish());

        loadConcertData();
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

    private void loadConcertData() {

        // ================= LIVE NOW =================

        imgPosterEvilDead.setImageResource(R.drawable.back);   // Replace with arijit_live
        tvTitleEvilDead.setText("Arijit Singh Live");
        tvRatingEvilDead.setText("Mumbai • 7:00 PM");

        imgPosterOdyssey.setImageResource(R.drawable.back);    // Replace with diljit_live
        tvTitleOdyssey.setText("Diljit Dosanjh");
        tvRatingOdyssey.setText("Pune • 8:00 PM");

        // ================= UPCOMING =================

        imgPosterMirzapur.setImageResource(R.drawable.back);   // Replace with ar_rahman
        tvTitleMirzapur.setText("A. R. Rahman Live");
        tvRatingMirzapur.setText("15 Aug 2026");

        imgPosterAvengers.setImageResource(R.drawable.back);   // Replace with honey_singh
        tvTitleAvengers.setText("Yo Yo Honey Singh");
        tvRatingAvengers.setText("28 Sep 2026");

        // ================= POPULAR =================

        imgPosterJanNeta.setImageResource(R.drawable.back);    // Replace with shreya
        tvTitleJanNeta.setText("Shreya Ghoshal");
        tvRatingJanNeta.setText("★ 4.9 (25K)");

        imgPosterTeraYaar.setImageResource(R.drawable.back);   // Replace with sonu
        tvTitleTeraYaar.setText("Sonu Nigam Live");
        tvRatingTeraYaar.setText("★ 4.8 (20K)");
    }
}