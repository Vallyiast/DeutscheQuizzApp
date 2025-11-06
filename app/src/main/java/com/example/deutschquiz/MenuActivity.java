package com.example.deutschquiz;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class MenuActivity extends AppCompatActivity {

    Button boutonQuiz, boutonList, boutonGuess, boutonWrite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_fragment);

        boutonGuess = findViewById(R.id.Guess);
        boutonQuiz = findViewById(R.id.Quiz);
        boutonList = findViewById(R.id.Liste);
        boutonWrite = findViewById(R.id.Write);

        // Affiche le premier fragment au lancement
        if (savedInstanceState == null) {

            boutonGuess.setBackgroundColor(Color.parseColor("#3d3729"));
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragment_container_view, GuessFragment.class, null)
                    .commit();
        }

        // Gestion de la bannière
        boutonQuiz.setOnClickListener(v ->  {
            switchToFragment(new QuizFragment());
            v.setBackgroundColor(Color.parseColor("#3d3729"));
        });
        boutonGuess.setOnClickListener(v -> {
            switchToFragment(new GuessFragment());
            v.setBackgroundColor(Color.parseColor("#3d3729"));
        });
        boutonList.setOnClickListener(v -> {
            switchToFragment(new ListFragment());
                v.setBackgroundColor(Color.parseColor("#3d3729"));
        });
        boutonWrite.setOnClickListener(v -> {
            switchToFragment(new WriteFragment());
            v.setBackgroundColor(Color.parseColor("#3d3729"));
        });
    }

    private void switchToFragment(Fragment fragment) {

        boutonGuess.setBackgroundColor(Color.parseColor("#ffd03c"));
        boutonWrite.setBackgroundColor(Color.parseColor("#ffd03c"));
        boutonList.setBackgroundColor(Color.parseColor("#ffd03c"));
        boutonQuiz.setBackgroundColor(Color.parseColor("#ffd03c"));

        getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out)
                .replace(R.id.fragment_container_view,fragment)
                .addToBackStack(null)
                .commit();
    }
}
