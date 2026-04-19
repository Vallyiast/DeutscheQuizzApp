package com.example.deutschquiz.activity.navigation;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.deutschquiz.R;
import com.example.deutschquiz.WordRepository;
import com.example.deutschquiz.model.Adjective;
import com.example.deutschquiz.model.Adverb;
import com.example.deutschquiz.model.Verb;
import com.example.deutschquiz.providers.CSVProvider;

public class MainActivity extends AppCompatActivity {

    public static final String[] types = {"verbes","noms","adverbes","adjectifs","cours_gluck"};


    Button button_verbes, button_adjectifs, button_adverbes, button_mots, parameters_button;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button_verbes = findViewById(R.id.Verbes);
        button_adjectifs = findViewById(R.id.Adjectifs);
        button_adverbes = findViewById(R.id.Adverbes);
        button_mots = findViewById(R.id.Mots);
        parameters_button = findViewById(R.id.Parameters);
        image = findViewById(R.id.declinaisons);

        image.setImageResource(R.drawable.declinaisons);


        parameters_button.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ParametersActivity.class);
            startActivity(intent);
        });

        button_verbes.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SecondMenuActivity.class);
            WordRepository.setWordList(new CSVProvider(getAssets(), "verbes", Verb.class));
            startActivity(intent);
        });
        button_adjectifs.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SecondMenuActivity.class);
            WordRepository.setWordList(new CSVProvider(getAssets(), "adjectifs", Adjective.class));
            startActivity(intent);
        });
        button_adverbes.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SecondMenuActivity.class);
            WordRepository.setWordList(new CSVProvider(getAssets(), "adverbes", Adverb.class));
            startActivity(intent);
        });
        button_mots.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SecondMenuActivity.class);
            WordRepository.setWordList(new CSVProvider(getAssets(), "cours_gluck", null));
            startActivity(intent);
        });
    }
}
