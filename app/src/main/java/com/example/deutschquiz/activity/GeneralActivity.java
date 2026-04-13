package com.example.deutschquiz.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.deutschquiz.R;

public class GeneralActivity extends AppCompatActivity {

    LinearLayout commons, chapter12, chapter13, chapter14;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general);

        commons = findViewById(R.id.commons);
        chapter12 = findViewById(R.id.chapter12);
        chapter13 = findViewById(R.id.chapter13);
        chapter14 = findViewById(R.id.chapter14);

        commons.setOnClickListener(v -> {
            Intent intent = new Intent(GeneralActivity.this, MainActivity.class);
            startActivity(intent);
        });


    }
}
