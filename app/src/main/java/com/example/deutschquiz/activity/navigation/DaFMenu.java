package com.example.deutschquiz.activity.navigation;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.deutschquiz.R;
import com.example.deutschquiz.WordRepository;
import com.example.deutschquiz.providers.DaFFileProvider;
import com.google.android.material.button.MaterialButton;

public class DaFMenu extends AppCompatActivity {
    MaterialButton menu, button_a, button_b, button_c;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.daf_menu);

        menu = findViewById(R.id.menu);
        button_a = findViewById(R.id.A);
        button_b = findViewById(R.id.B);
        button_c = findViewById(R.id.C);

        menu.setOnClickListener(v -> finish());

        button_a.setOnClickListener(v -> {
            Intent intent = new Intent(DaFMenu.this, SecondMenuActivity.class);
            WordRepository.setWordList(new DaFFileProvider(getAssets(), getIntent().getStringExtra("chapter") + "A"));
            startActivity(intent);
        });
        button_b.setOnClickListener(v -> {
            Intent intent = new Intent(DaFMenu.this, SecondMenuActivity.class);
            WordRepository.setWordList(new DaFFileProvider(getAssets(), getIntent().getStringExtra("chapter") + "B"));
            startActivity(intent);
        });
        button_c.setOnClickListener(v -> {
            Intent intent = new Intent(DaFMenu.this, SecondMenuActivity.class);
            WordRepository.setWordList(new DaFFileProvider(getAssets(), getIntent().getStringExtra("chapter") + "C"));
            startActivity(intent);
        });


    }
}
