package com.example.deutschquiz;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MenuActivity extends AppCompatActivity {
    Button button_qcm, button_write, button_list, button_menu, button_guess;
    TextView name_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        button_qcm = findViewById(R.id.QCM);
        button_write = findViewById(R.id.Write);
        button_list = findViewById(R.id.List);
        button_menu = findViewById(R.id.main_menu);
        name_text = findViewById(R.id.name_text);
        button_guess = findViewById(R.id.Guess);

        name_text.setText(getIntent().getStringExtra("destination"));

        button_menu.setOnClickListener(v -> {
            finish();
        });

        button_qcm.setOnClickListener(v -> {
            Intent intent = new Intent(MenuActivity.this, QCMActivity.class);
            intent.putExtra("destination", getIntent().getStringExtra("destination"));
            startActivity(intent);
        });

        button_write.setOnClickListener(v -> {
            Intent intent = new Intent(MenuActivity.this, WriteActivity.class);
            intent.putExtra("destination", getIntent().getStringExtra("destination"));
            startActivity(intent);
        });

        button_list.setOnClickListener(v -> {
            Intent intent = new Intent(MenuActivity.this, ListActivity.class);
            intent.putExtra("destination", getIntent().getStringExtra("destination"));
            startActivity(intent);
        });

        button_guess.setOnClickListener(v -> {
            Intent intent = new Intent(MenuActivity.this, GuessActivity.class);
            intent.putExtra("destination", getIntent().getStringExtra("destination"));
            startActivity(intent);
        });
    }
}