package com.example.deutschquiz.activity.navigation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.deutschquiz.R;
import com.example.deutschquiz.WordRepository;
import com.example.deutschquiz.database.WikDictionary;
import com.example.deutschquiz.providers.DaFFileProvider;
import com.example.deutschquiz.providers.WordProvider;
import com.google.android.material.button.MaterialButton;

public class DaFMenu extends AppCompatActivity {
    MaterialButton menu, button_a, button_b, button_c;
    ImageView loading;
    View load_overlay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.daf_menu);

        WikDictionary repo = new WikDictionary(this);

        menu = findViewById(R.id.menu);
        button_a = findViewById(R.id.A);
        button_b = findViewById(R.id.B);
        button_c = findViewById(R.id.C);
        loading = findViewById(R.id.loading);
        load_overlay = findViewById(R.id.loading_overlay);

        menu.setOnClickListener(v -> finish());

        button_a.setOnClickListener(v -> queryAndLaunchActivity(new DaFFileProvider(getAssets(), getIntent().getStringExtra("chapter") + "A"), repo));
        button_b.setOnClickListener(v -> queryAndLaunchActivity(new DaFFileProvider(getAssets(), getIntent().getStringExtra("chapter") + "B"), repo));
        button_c.setOnClickListener(v -> queryAndLaunchActivity(new DaFFileProvider(getAssets(), getIntent().getStringExtra("chapter") + "C"), repo));


    }

    private void queryAndLaunchActivity(WordProvider provider,WikDictionary repo) {

        loading.setVisibility(View.VISIBLE);
        load_overlay.setVisibility(View.VISIBLE);
        findViewById(R.id.activity).setEnabled(false);

        Intent intent = new Intent(DaFMenu.this, SecondMenuActivity.class);

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
