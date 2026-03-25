package com.example.deutschquiz.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.deutschquiz.R;

public class MainActivity extends AppCompatActivity {

    public static String[] types = {"verbes","noms","adverbes","adjectifs","cours_gluck"};


    Button button_verbes, button_adjectifs, button_adverbes, button_mots;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button_verbes = findViewById(R.id.Verbes);
        button_adjectifs = findViewById(R.id.Adjectifs);
        button_adverbes = findViewById(R.id.Adverbes);
        button_mots = findViewById(R.id.Mots);
        image = findViewById(R.id.declinaisons);

        image.setImageResource(R.drawable.declinaisons);

        button_verbes.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MenuActivity.class);
            intent.putExtra("destination", "verbes");
            startActivity(intent);
        });
        button_adjectifs.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MenuActivity.class);
            intent.putExtra("destination", "adjectifs");
            startActivity(intent);
        });
        button_adverbes.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MenuActivity.class);
            intent.putExtra("destination", "adverbes");
            startActivity(intent);
        });
        button_mots.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MenuActivity.class);
            intent.putExtra("destination", "cours_gluck");
            startActivity(intent);
        });
    }
}
