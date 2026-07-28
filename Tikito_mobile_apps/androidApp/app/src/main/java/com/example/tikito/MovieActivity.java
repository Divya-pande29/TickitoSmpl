package com.example.tikito;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MovieActivity extends AppCompatActivity {
    private ImageView btnBack, btnSearch;
    private EditText etSearch;
    private Button btnAction, btnComedy, btnRomance, btnHorror;
    private LinearLayout movieEvilDead, movieOdyssey, movieMirzapur, movieAvengers, movieJanNeta, movieTeraYaar;
    private TextView tvTitleEvilDead, tvTitleOdyssey, tvTitleMirzapur, tvTitleAvengers, tvTitleJanNeta, tvTitleTeraYaar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        btnBack = findViewById(R.id.btnBack);
        btnSearch = findViewById(R.id.btnSearch);

        btnAction = findViewById(R.id.btnAction);
        btnComedy = findViewById(R.id.btnComedy);
        btnRomance = findViewById(R.id.btnRomance);
        btnHorror = findViewById(R.id.btnHorror);

        movieEvilDead = findViewById(R.id.movieEvilDead);
        movieOdyssey = findViewById(R.id.movieOdyssey);
        movieMirzapur = findViewById(R.id.movieMirzapur);
        movieAvengers = findViewById(R.id.movieAvengers);
        movieJanNeta = findViewById(R.id.movieJanNeta);
        movieTeraYaar = findViewById(R.id.movieTeraYaar);

        tvTitleEvilDead = findViewById(R.id.tvTitleEvilDead);
        tvTitleOdyssey = findViewById(R.id.tvTitleOdyssey);
        tvTitleMirzapur = findViewById(R.id.tvTitleMirzapur);
        tvTitleAvengers = findViewById(R.id.tvTitleAvengers);
        tvTitleJanNeta = findViewById(R.id.tvTitleJanNeta);
        tvTitleTeraYaar = findViewById(R.id.tvTitleTeraYaar);

        tvTitleEvilDead = findViewById(R.id.tvTitleEvilDead);
        tvTitleOdyssey = findViewById(R.id.tvTitleOdyssey);
        tvTitleMirzapur = findViewById(R.id.tvTitleMirzapur);
        tvTitleAvengers = findViewById(R.id.tvTitleAvengers);
        tvTitleJanNeta = findViewById(R.id.tvTitleJanNeta);
        tvTitleTeraYaar = findViewById(R.id.tvTitleTeraYaar);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }

        // --- REAL-TIME SEARCH (As you type) ---
        if (etSearch != null) {
            etSearch.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    performSearch(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {}
            });

            // Handle Keyboard Search/Enter Button Press
            etSearch.setOnEditorActionListener((v, actionId, event) -> {
                if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE) {
                    performSearch(etSearch.getText().toString());
                    return true;
                }
                return false;
            });
        }

        // Search Icon Click
        if (btnSearch != null) {
            btnSearch.setOnClickListener(v -> {
                if (etSearch != null) {
                    performSearch(etSearch.getText().toString());
                }
            });
        }

        // --- CATEGORY BUTTONS ---
        if (btnAction != null) btnAction.setOnClickListener(v -> performSearch("Evil"));
        if (btnComedy != null) btnComedy.setOnClickListener(v -> performSearch("Tera"));
        if (btnRomance != null) btnRomance.setOnClickListener(v -> performSearch("Jan"));
        if (btnHorror != null) btnHorror.setOnClickListener(v -> performSearch("Odyssey"));

        // Movie Card Clicks
        if (movieEvilDead != null) movieEvilDead.setOnClickListener(v -> Toast.makeText(this, "Evil Dead Burn Selected", Toast.LENGTH_SHORT).show());
        if (movieOdyssey != null) movieOdyssey.setOnClickListener(v -> Toast.makeText(this, "The Odyssey Selected", Toast.LENGTH_SHORT).show());
        if (movieMirzapur != null) movieMirzapur.setOnClickListener(v -> Toast.makeText(this, "Mirzapur Selected", Toast.LENGTH_SHORT).show());
        if (movieAvengers != null) movieAvengers.setOnClickListener(v -> Toast.makeText(this, "Avengers Selected", Toast.LENGTH_SHORT).show());
        if (movieJanNeta != null) movieJanNeta.setOnClickListener(v -> Toast.makeText(this, "Jan Neta Selected", Toast.LENGTH_SHORT).show());
        if (movieTeraYaar != null) movieTeraYaar.setOnClickListener(v -> Toast.makeText(this, "Tera Yaar Hoon Main Selected", Toast.LENGTH_SHORT).show());
    }

    // Main Filtering Function
    private void performSearch(String query) {
        String cleanQuery = query.toLowerCase().trim();

        filterSingleMovie(movieEvilDead, tvTitleEvilDead, cleanQuery);
        filterSingleMovie(movieOdyssey, tvTitleOdyssey, cleanQuery);
        filterSingleMovie(movieMirzapur, tvTitleMirzapur, cleanQuery);
        filterSingleMovie(movieAvengers, tvTitleAvengers, cleanQuery);
        filterSingleMovie(movieJanNeta, tvTitleJanNeta, cleanQuery);
        filterSingleMovie(movieTeraYaar, tvTitleTeraYaar, cleanQuery);
    }

    private void filterSingleMovie(LinearLayout card, TextView title, String query) {
        if (card != null && title != null) {
            if (query.isEmpty()) {
                card.setVisibility(View.VISIBLE);
            } else {
                String movieName = title.getText().toString().toLowerCase();
                if (movieName.contains(query)) {
                    card.setVisibility(View.VISIBLE);
                } else {
                    card.setVisibility(View.GONE);
                }
            }
        }
    }
}