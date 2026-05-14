package com.example.deutschquiz.activity.navigation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.deutschquiz.R;
import com.example.deutschquiz.WordRepository;
import com.example.deutschquiz.database.ScoreDataBase;
import com.example.deutschquiz.database.WikDictionary;
import com.example.deutschquiz.providers.DaFFileProvider;
import com.example.deutschquiz.providers.WordProvider;
import com.google.android.material.button.MaterialButton;

public class DaFMenu extends AppCompatActivity {
    MaterialButton menu, button_a, button_b, button_c, button_gender_a, button_gender_b, button_gender_c;
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
        button_gender_a = findViewById(R.id.A_gender);
        button_gender_b = findViewById(R.id.B_gender);
        button_gender_c = findViewById(R.id.C_gender);
        loading = findViewById(R.id.loading);
        load_overlay = findViewById(R.id.loading_overlay);

        menu.setOnClickListener(v -> finish());

        button_a.setOnClickListener(v -> queryAndLaunchActivity(new DaFFileProvider(getAssets(), getIntent().getStringExtra("chapter") + "A"), repo,SecondMenuActivity.class));
        button_b.setOnClickListener(v -> queryAndLaunchActivity(new DaFFileProvider(getAssets(), getIntent().getStringExtra("chapter") + "B"), repo,SecondMenuActivity.class));
        button_c.setOnClickListener(v -> queryAndLaunchActivity(new DaFFileProvider(getAssets(), getIntent().getStringExtra("chapter") + "C"), repo,SecondMenuActivity.class));

        button_gender_a.setOnClickListener(v -> queryAndLaunchActivity(new DaFFileProvider(getAssets(), getIntent().getStringExtra("chapter") + "A"), repo, GenderMenu.class));
        button_gender_b.setOnClickListener(v -> queryAndLaunchActivity(new DaFFileProvider(getAssets(), getIntent().getStringExtra("chapter") + "B"), repo, GenderMenu.class));
        button_gender_c.setOnClickListener(v -> queryAndLaunchActivity(new DaFFileProvider(getAssets(), getIntent().getStringExtra("chapter") + "C"), repo, GenderMenu.class));
    }

    private void queryAndLaunchActivity(WordProvider provider,WikDictionary repo, Class<?> menuClass) {

        loading.setVisibility(View.VISIBLE);
        load_overlay.setVisibility(View.VISIBLE);
        findViewById(R.id.activity).setEnabled(false);

        Intent intent = new Intent(DaFMenu.this, menuClass);

        new Thread(() -> {
            WordRepository.setWordList(provider, repo,new ScoreDataBase(this));

            runOnUiThread(() -> {
                loading.setVisibility(View.INVISIBLE);
                load_overlay.setVisibility(View.INVISIBLE);
                findViewById(R.id.activity).setEnabled(true);
                startActivity(intent);
            });
        }).start();
    }
}
