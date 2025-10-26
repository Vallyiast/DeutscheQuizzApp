package com.example.deutschquiz;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    public static String[] types = {"verbes","noms","adverbes","adjectifs","cours_gluck"};

    public static final int couleurBR = Color.rgb(131,105,83);
    public static final int couleurB = Color.rgb(61, 55, 41);
    public static final int couleurW = Color.rgb(0, 150, 0);
    public static final int couleurL = Color.rgb(150, 0, 0);


    Button button_verbes, button_adjectifs, button_noms, button_adverbes, button_mots;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button_verbes = findViewById(R.id.Verbes);
        button_adjectifs = findViewById(R.id.Adjectifs);
        button_noms = findViewById(R.id.Noms);
        button_adverbes = findViewById(R.id.Adverbes);
        button_mots = findViewById(R.id.Mots);
        image = findViewById(R.id.declinaisons);

        image.setImageResource(R.drawable.declinaisons);

        button_verbes.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MenuVerbesActivity.class);
            intent.putExtra("destination", "verbes");
            startActivity(intent);
        });
        button_adjectifs.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MenuActivity.class);
            intent.putExtra("destination", "adjectifs");
            startActivity(intent);
        });
        button_noms.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MenuActivity.class);
            intent.putExtra("destination", "noms");
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
