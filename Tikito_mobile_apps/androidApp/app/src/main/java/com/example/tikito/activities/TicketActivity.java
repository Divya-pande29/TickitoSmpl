package com.example.tikito.activities;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.TextView;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Environment;
import android.widget.Toast;
import android.graphics.Bitmap;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import java.io.File;
import java.io.FileOutputStream;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tikito.R;
import com.example.tikito.adapters.SummaryFoodAdapter;
import com.example.tikito.entities.Food;
import com.google.android.material.button.MaterialButton;

import java.io.OutputStream;
import java.util.ArrayList;

public class TicketActivity extends AppCompatActivity
{
    private TextView txtTicketTotal;
    private TextView txtFoodTotal;
    private TextView txtGrandTotal;
    private TextView txtMovieName;
    private TextView txtVenue;
    private TextView txtDateTime;
    private TextView txtSeats;
    RecyclerView recyclerFoods;
    MaterialButton downloadBtn, gobackBtn;
    private ArrayList<Food> selectedFoods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);
        Toast.makeText(this, "TicketActivity", Toast.LENGTH_LONG).show();

        txtTicketTotal = findViewById(R.id.txtTicketTotal);
        txtFoodTotal = findViewById(R.id.txtFoodTotal);
        txtGrandTotal = findViewById(R.id.txtGrandTotal);
        txtMovieName = findViewById(R.id.txtMovieName);
        txtVenue = findViewById(R.id.txtVenue);
        txtDateTime = findViewById(R.id.txtDateTime);
        txtSeats = findViewById(R.id.txtSeats);
        downloadBtn = findViewById(R.id.btnDownload);
        gobackBtn = findViewById(R.id.btnGoback);
        recyclerFoods = findViewById(R.id.recyclerFoods);

        Intent intent = getIntent();

        String eventName = intent.getStringExtra("eventName");
        String venueName = intent.getStringExtra("venueName");
        String showDate = intent.getStringExtra("showDate");
        String showTime = intent.getStringExtra("showTime");
        double ticketTotal = intent.getDoubleExtra("ticketTotal",0);
        double foodTotal = intent.getDoubleExtra("foodTotal",0);
        double grandTotal = intent.getDoubleExtra("grandTotal",0);
        double ticketPrice = intent.getDoubleExtra("ticketPrice",0);
        ArrayList<Long> seatIds = (ArrayList<Long>) intent.getSerializableExtra("seatIds");
        ArrayList<String> seatNumbers = intent.getStringArrayListExtra("seatNumbers");
        selectedFoods = (ArrayList<Food>) intent.getSerializableExtra("selectedFoods");

        txtMovieName.setText(eventName);
        txtVenue.setText(venueName);
        txtDateTime.setText(showDate + " | " + showTime);
        txtSeats.setText(String.join(", ", seatNumbers));
        txtTicketTotal.setText("₹" + ticketTotal);
        txtFoodTotal.setText("₹" + foodTotal);
        txtGrandTotal.setText("₹" + grandTotal);

        recyclerFoods.setLayoutManager(new LinearLayoutManager(this));
        SummaryFoodAdapter adapter = new SummaryFoodAdapter(selectedFoods);
        recyclerFoods.setAdapter(adapter);

        gobackBtn.setOnClickListener(v ->
        {
            Intent home = new Intent(TicketActivity.this, UpcomingTicketsActivity.class);


            home.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

            startActivity(home);

            finish();
        });

        downloadBtn.setOnClickListener(v ->
        {
            android.util.Log.d("PDF", "Download button clicked");
            generatePdf();
        });

    }

    private void generatePdf() {

        PdfDocument pdfDocument = new PdfDocument();

        Paint paint = new Paint();

        PdfDocument.PageInfo pageInfo =
                new PdfDocument.PageInfo.Builder(595, 842, 1).create();

        PdfDocument.Page page = pdfDocument.startPage(pageInfo);

        Canvas canvas = page.getCanvas();

        int x = 40;
        int y = 60;

        // QR Text
        String qrText =
                "Tikito Movie Ticket\n"
                        + "Movie : " + txtMovieName.getText() + "\n"
                        + "Venue : " + txtVenue.getText() + "\n"
                        + "Date : " + txtDateTime.getText() + "\n"
                        + "Seats : " + txtSeats.getText() + "\n"
                        + "Amount : " + txtGrandTotal.getText() + "\n"
                        + "Status : PAID";

        Bitmap qrBitmap = generateQrCode(qrText);

        paint.setTextSize(24);
        paint.setFakeBoldText(true);

        canvas.drawText("TIKITO MOVIE TICKET", x, y, paint);

        y += 50;

        paint.setTextSize(18);
        paint.setFakeBoldText(false);

        canvas.drawText(
                "Movie : " + txtMovieName.getText(),
                x,
                y,
                paint);

        y += 30;

        canvas.drawText(
                "Venue : " + txtVenue.getText(),
                x,
                y,
                paint);

        y += 30;

        canvas.drawText(
                "Date & Time : " + txtDateTime.getText(),
                x,
                y,
                paint);

        y += 30;

        canvas.drawText(
                "Seats : " + txtSeats.getText(),
                x,
                y,
                paint);

        y += 50;

        paint.setFakeBoldText(true);

        canvas.drawText("Food Items", x, y, paint);

        paint.setFakeBoldText(false);

        y += 30;

        if (selectedFoods != null && !selectedFoods.isEmpty()) {

            for (Food food : selectedFoods) {

                canvas.drawText(
                        food.getFoodName()
                                + " x "
                                + food.getQuantity(),
                        x,
                        y,
                        paint);

                y += 25;
            }

        } else {

            canvas.drawText(
                    "No Food Ordered",
                    x,
                    y,
                    paint);

            y += 25;
        }

        y += 20;

        canvas.drawLine(x, y, 550, y, paint);

        y += 30;

        canvas.drawText(
                "Ticket Total : " + txtTicketTotal.getText(),
                x,
                y,
                paint);

        y += 30;

        canvas.drawText(
                "Food Total : " + txtFoodTotal.getText(),
                x,
                y,
                paint);

        y += 30;

        paint.setFakeBoldText(true);

        canvas.drawText(
                "Grand Total : " + txtGrandTotal.getText(),
                x,
                y,
                paint);

        y += 40;

        canvas.drawText(
                "Status : PAID",
                x,
                y,
                paint);

        if (qrBitmap != null) {

            paint.setFakeBoldText(false);

            canvas.drawText(
                    "Scan Ticket",
                    355,
                    560,
                    paint);

            canvas.drawBitmap(
                    qrBitmap,
                    320,
                    580,
                    null);
        }

        pdfDocument.finishPage(page);

        try {

            ContentValues values = new ContentValues();

            values.put(
                    MediaStore.Downloads.DISPLAY_NAME,
                    "Tikito_Ticket_" + System.currentTimeMillis() + ".pdf");

            values.put(
                    MediaStore.Downloads.MIME_TYPE,
                    "application/pdf");

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {

                values.put(
                        MediaStore.Downloads.RELATIVE_PATH,
                        Environment.DIRECTORY_DOWNLOADS);
            }

            Uri uri = getContentResolver().insert(
                    MediaStore.Downloads.EXTERNAL_CONTENT_URI,
                    values);

            if (uri != null) {

                OutputStream outputStream =
                        getContentResolver().openOutputStream(uri);

                if (outputStream != null) {

                    pdfDocument.writeTo(outputStream);

                    outputStream.flush();
                    outputStream.close();

                    Toast.makeText(
                            this,
                            "Ticket saved to Downloads",
                            Toast.LENGTH_LONG).show();
                }
            }

        } catch (Exception e) {

            e.printStackTrace();

            Toast.makeText(
                    this,
                    "Failed to save PDF",
                    Toast.LENGTH_SHORT).show();

        } finally {

            pdfDocument.close();
        }
    }
    private Bitmap generateQrCode(String text) {

        try {

            QRCodeWriter writer = new QRCodeWriter();

            BitMatrix bitMatrix =
                    writer.encode(
                            text,
                            BarcodeFormat.QR_CODE,
                            220,
                            220);

            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();

            Bitmap bitmap =
                    Bitmap.createBitmap(
                            width,
                            height,
                            Bitmap.Config.RGB_565);

            for (int x = 0; x < width; x++) {

                for (int y = 0; y < height; y++) {

                    bitmap.setPixel(
                            x,
                            y,
                            bitMatrix.get(x, y)
                                    ? android.graphics.Color.BLACK
                                    : android.graphics.Color.WHITE);
                }
            }

            return bitmap;

        } catch (WriterException e) {

            e.printStackTrace();

            return null;
        }
    }
}