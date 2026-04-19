package com.example.deutschquiz.activity.navigation;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.deutschquiz.R;

public class GeneralActivity extends AppCompatActivity {

    LinearLayout commons, chapter12, chapter13, chapter14,chapter15,chapter16;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general);

        commons = findViewById(R.id.commons);
        chapter12 = findViewById(R.id.chapter12);
        chapter13 = findViewById(R.id.chapter13);
        chapter14 = findViewById(R.id.chapter14);
        chapter15 = findViewById(R.id.chapter15);
        chapter16 = findViewById(R.id.chapter16);

        commons.setOnClickListener(v -> {
            Intent intent = new Intent(GeneralActivity.this, MainActivity.class);
            startActivity(intent);
        });

        chapter12.setOnClickListener(v -> {
            Intent intent = new Intent(GeneralActivity.this, DaFMenu.class);
            intent.putExtra("chapter", "12");
            startActivity(intent);
        });
        chapter13.setOnClickListener(v -> {
            Intent intent = new Intent(GeneralActivity.this, DaFMenu.class);
            intent.putExtra("chapter", "13");
            startActivity(intent);
        });
        chapter14.setOnClickListener(v -> {
            Intent intent = new Intent(GeneralActivity.this, DaFMenu.class);
            intent.putExtra("chapter", "14");
            startActivity(intent);
        });
        chapter15.setOnClickListener(v -> {
            Intent intent = new Intent(GeneralActivity.this, DaFMenu.class);
            intent.putExtra("chapter", "15");
            startActivity(intent);
        });
        chapter16.setOnClickListener(v -> {
            Intent intent = new Intent(GeneralActivity.this, DaFMenu.class);
            intent.putExtra("chapter", "16");
            startActivity(intent);
        });
    }
}
