package com.example.deutschquiz;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class MenuActivity extends AppCompatActivity {
    Button buttonQuiz, buttonList, buttonGuess, buttonWrite, buttonMatch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_fragment);

        buttonGuess = findViewById(R.id.Guess);
        buttonQuiz = findViewById(R.id.Quiz);
        buttonList = findViewById(R.id.List);
        buttonWrite = findViewById(R.id.Write);
        buttonMatch = findViewById(R.id.Match);

        // Affiche le premier fragment au lancement
        if (savedInstanceState == null) {
            buttonGuess.setBackgroundColor(Color.parseColor("#1B1B1B"));
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragment_container_view, GuessFragment.class, null)
                    .commit();
        }

        // Gestion de la bannière
        buttonMatch.setOnClickListener(v ->  {
            switchToFragment(new MatchFragment());
            v.setBackgroundColor(Color.parseColor("#1B1B1B"));
        });

        buttonQuiz.setOnClickListener(v ->  {
            switchToFragment(new QuizFragment());
            v.setBackgroundColor(Color.parseColor("#1B1B1B"));
        });

        buttonGuess.setOnClickListener(v -> {
            switchToFragment(new GuessFragment());
            v.setBackgroundColor(Color.parseColor("#1B1B1B"));
        });
        buttonList.setOnClickListener(v -> {
            switchToFragment(new ListFragment());
                v.setBackgroundColor(Color.parseColor("#1B1B1B"));
        });
        buttonWrite.setOnClickListener(v -> {
            switchToFragment(new WriteFragment());
            v.setBackgroundColor(Color.parseColor("#1B1B1B"));
        });
    }

    private void switchToFragment(Fragment fragment) {

        buttonGuess.setBackgroundColor(Color.parseColor("#1B1B1B"));
        buttonQuiz.setBackgroundColor(Color.parseColor("#1B1B1B"));
        buttonList.setBackgroundColor(Color.parseColor("#1B1B1B"));
        buttonMatch.setBackgroundColor(Color.parseColor("#1B1B1B"));
        buttonWrite.setBackgroundColor(Color.parseColor("#1B1B1B"));

        getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out)
                .replace(R.id.fragment_container_view,fragment)
                .addToBackStack(null)
                .commit();
    }
}
