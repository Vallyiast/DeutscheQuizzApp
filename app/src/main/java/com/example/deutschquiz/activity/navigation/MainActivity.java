package com.example.deutschquiz.activity.navigation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.deutschquiz.R;
import com.example.deutschquiz.WordRepository;
import com.example.deutschquiz.database.WikDictionary;
import com.example.deutschquiz.model.Adjective;
import com.example.deutschquiz.model.Adverb;
import com.example.deutschquiz.model.Verb;
import com.example.deutschquiz.providers.CSVProvider;
import com.example.deutschquiz.providers.WordProvider;

public class MainActivity extends AppCompatActivity {

    Button button_verbes, button_adjectifs, button_adverbes, button_mots;
    ImageView image;


    ImageView loading;
    View load_overlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button_verbes = findViewById(R.id.Verbes);
        button_adjectifs = findViewById(R.id.Adjectifs);
        button_adverbes = findViewById(R.id.Adverbes);
        button_mots = findViewById(R.id.Mots);
        image = findViewById(R.id.declinaisons);

        loading = findViewById(R.id.loading);
        load_overlay = findViewById(R.id.loading_overlay);
        image.setImageResource(R.drawable.declinaisons);


        WikDictionary repo = new WikDictionary(this);

        button_verbes.setOnClickListener(v -> queryAndLaunchActivity(new CSVProvider(getAssets(), "verbes", Verb.class), repo));
        button_adjectifs.setOnClickListener(v -> queryAndLaunchActivity(new CSVProvider(getAssets(), "adjectifs", Adjective.class), repo));
        button_adverbes.setOnClickListener(v -> queryAndLaunchActivity(new CSVProvider(getAssets(), "adverbes", Adverb.class), repo));
        button_mots.setOnClickListener(v -> queryAndLaunchActivity(new CSVProvider(getAssets(), "cours_gluck", null), repo));

    }

    private void queryAndLaunchActivity(WordProvider provider, WikDictionary repo) {

        loading.setVisibility(View.VISIBLE);
        load_overlay.setVisibility(View.VISIBLE);
        findViewById(R.id.activity).setEnabled(false);

        Intent intent = new Intent(MainActivity.this, SecondMenuActivity.class);

        new Thread(() -> {
            WordRepository.setWordList(provider, repo);

            runOnUiThread(() -> {
                loading.setVisibility(View.INVISIBLE);
                load_overlay.setVisibility(View.INVISIBLE);
                findViewById(R.id.activity).setEnabled(true);
                startActivity(intent);
            });
        }).start();
    }
}
