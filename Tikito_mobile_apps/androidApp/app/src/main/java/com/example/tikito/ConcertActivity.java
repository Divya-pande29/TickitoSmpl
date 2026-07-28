package com.example.tikito;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ConcertActivity extends AppCompatActivity {
    private ImageView btnBack, btnSearch;
    private Button btnPop, btnRock, btnEdm, btnClassical;
    private LinearLayout concertArijit, concertSunburn, concertColdplay, concertRahman, artistAmit, artistSanam;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_concert);
        btnBack = findViewById(R.id.btnBack);
        btnSearch = findViewById(R.id.btnSearch);

        btnPop = findViewById(R.id.btnPop);
        btnRock = findViewById(R.id.btnRock);
        btnEdm = findViewById(R.id.btnEdm);
        btnClassical = findViewById(R.id.btnClassical);

        concertArijit = findViewById(R.id.concertArijit);
        concertSunburn = findViewById(R.id.concertSunburn);
        concertColdplay = findViewById(R.id.concertColdplay);
        concertRahman = findViewById(R.id.concertRahman);
        artistAmit = findViewById(R.id.artistAmit);
        artistSanam = findViewById(R.id.artistSanam);

        // --- CLICK LISTENERS ---

        // Back Button
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }

        // Search Button
        if (btnSearch != null) {
            btnSearch.setOnClickListener(v ->
                    Toast.makeText(this, "Search Concerts Clicked", Toast.LENGTH_SHORT).show()
            );
        }

        // Category Buttons
        if (btnPop != null) {
            btnPop.setOnClickListener(v ->
                    Toast.makeText(this, "Filter: Pop", Toast.LENGTH_SHORT).show()
            );
        }

        if (btnRock != null) {
            btnRock.setOnClickListener(v ->
                    Toast.makeText(this, "Filter: Rock", Toast.LENGTH_SHORT).show()
            );
        }

        if (btnEdm != null) {
            btnEdm.setOnClickListener(v ->
                    Toast.makeText(this, "Filter: EDM", Toast.LENGTH_SHORT).show()
            );
        }

        if (btnClassical != null) {
            btnClassical.setOnClickListener(v ->
                    Toast.makeText(this, "Filter: Classical", Toast.LENGTH_SHORT).show()
            );
        }

        // Concert Clicks
        if (concertArijit != null) {
            concertArijit.setOnClickListener(v ->
                    Toast.makeText(this, "Arijit Singh Live Clicked", Toast.LENGTH_SHORT).show()
            );
        }

        if (concertSunburn != null) {
            concertSunburn.setOnClickListener(v ->
                    Toast.makeText(this, "Sunburn Arena Clicked", Toast.LENGTH_SHORT).show()
            );
        }

        if (concertColdplay != null) {
            concertColdplay.setOnClickListener(v ->
                    Toast.makeText(this, "Coldplay India Tour Clicked", Toast.LENGTH_SHORT).show()
            );
        }

        if (concertRahman != null) {
            concertRahman.setOnClickListener(v ->
                    Toast.makeText(this, "AR Rahman Live Clicked", Toast.LENGTH_SHORT).show()
            );
        }

        if (artistAmit != null) {
            artistAmit.setOnClickListener(v ->
                    Toast.makeText(this, "Amit Trivedi Clicked", Toast.LENGTH_SHORT).show()
            );
        }

        if (artistSanam != null) {
            artistSanam.setOnClickListener(v ->
                    Toast.makeText(this, "Sanam Band Clicked", Toast.LENGTH_SHORT).show()
            );
        }
    }
}


