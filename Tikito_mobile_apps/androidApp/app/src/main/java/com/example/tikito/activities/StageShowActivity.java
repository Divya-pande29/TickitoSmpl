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

public class StageShowActivity extends AppCompatActivity {

    private ImageView btnBack;
    private TextView tvTitle;
    private EditText etSearch;

    // Now Showing
    private ImageView imgPosterEvilDead;
    private TextView tvTitleEvilDead, tvRatingEvilDead;

    private ImageView imgPosterOdyssey;
    private TextView tvTitleOdyssey, tvRatingOdyssey;

    // Upcoming Shows
    private ImageView imgPosterMirzapur;
    private TextView tvTitleMirzapur, tvRatingMirzapur;

    private ImageView imgPosterAvengers;
    private TextView tvTitleAvengers, tvRatingAvengers;

    // Popular Shows
    private ImageView imgPosterJanNeta;
    private TextView tvTitleJanNeta, tvRatingJanNeta;

    private ImageView imgPosterTeraYaar;
    private TextView tvTitleTeraYaar, tvRatingTeraYaar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        initViews();

        tvTitle.setText("Stage Shows");
        etSearch.setHint("Search Stage Shows...");

        btnBack.setOnClickListener(v -> finish());

        loadStageShowData();
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

    private void loadStageShowData() {

        // ================= NOW SHOWING =================

        imgPosterEvilDead.setImageResource(R.drawable.back); // Replace with sangeet_manapman
        tvTitleEvilDead.setText("Sangeet Manapman");
        tvRatingEvilDead.setText("Today • 7:00 PM");

        imgPosterOdyssey.setImageResource(R.drawable.back); // Replace with chala_hawa
        tvTitleOdyssey.setText("Chala Hawa Yeu Dya");
        tvRatingOdyssey.setText("Today • 8:30 PM");

        // ================= UPCOMING SHOWS =================

        imgPosterMirzapur.setImageResource(R.drawable.back); // Replace with hasya_katta
        tvTitleMirzapur.setText("Hasya Katta");
        tvRatingMirzapur.setText("15 Aug 2026");

        imgPosterAvengers.setImageResource(R.drawable.back); // Replace with drama_night
        tvTitleAvengers.setText("Drama Night");
        tvRatingAvengers.setText("25 Aug 2026");

        // ================= POPULAR SHOWS =================

        imgPosterJanNeta.setImageResource(R.drawable.back); // Replace with jau_bai_gavat
        tvTitleJanNeta.setText("Jau Bai Gavat");
        tvRatingJanNeta.setText("★ 4.9 (12K)");

        imgPosterTeraYaar.setImageResource(R.drawable.back); // Replace with comedy_live
        tvTitleTeraYaar.setText("Comedy Live Show");
        tvRatingTeraYaar.setText("★ 4.8 (10K)");
    }
}
