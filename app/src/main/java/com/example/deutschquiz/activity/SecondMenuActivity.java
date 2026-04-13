package com.example.deutschquiz.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.deutschquiz.R;

public class SecondMenuActivity  extends AppCompatActivity {

    Button buttonGame, buttonList, buttonMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_menu);

        buttonGame = findViewById(R.id.Game);
        buttonList = findViewById(R.id.List);
        buttonMenu = findViewById(R.id.Menu);

        // Affiche le premier fragment au lancement
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragment_container_view, MenuFragment.class, null)
                    .commit();
        }


        buttonList.setOnClickListener(v -> {
            resetButtonColor();
            switchToFragment(new WordListFragment());
            v.setBackgroundColor(Color.parseColor("#3B3B3B"));
        });
        buttonGame.setOnClickListener(v -> {
            resetButtonColor();
            switchToFragment(new MatchFragment());
            v.setBackgroundColor(Color.parseColor("#3B3B3B"));
        });

        buttonMenu.setOnClickListener(v -> finish());
    }

    private void resetButtonColor() {
        buttonList.setBackgroundColor(Color.parseColor("#1B1B1B"));
        buttonGame.setBackgroundColor(Color.parseColor("#1B1B1B"));
        buttonMenu.setBackgroundColor(Color.parseColor("#1B1B1B"));
    }

    private void switchToFragment(Fragment fragment) {
        Fragment current = getSupportFragmentManager().findFragmentById(R.id.fragment_container_view);

        if (current != null && current.getClass() == fragment.getClass()) {
            resetButtonColor();
            getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out)
                    .replace(R.id.fragment_container_view,new MenuFragment())
                    .addToBackStack(null)
                    .commit();
        } else {
            getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out)
                    .replace(R.id.fragment_container_view,fragment)
                    .addToBackStack(null)
                    .commit();
        }
    }
}
