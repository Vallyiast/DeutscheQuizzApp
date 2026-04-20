package com.example.deutschquiz.activity.navigation;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.deutschquiz.R;
import com.example.deutschquiz.activity.games.WordListFragment;
import com.example.deutschquiz.activity.games.MatchFragment;

public class SecondMenuActivity  extends AppCompatActivity {

    Button buttonGame, buttonList, buttonMenu;
    ImageButton buttonParameters;
    private Fragment activeFragment;
    private final MenuFragment menuFragment = new MenuFragment();
    private final MatchFragment matchFragment = new MatchFragment();
    private final WordListFragment wordListFragment = new WordListFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_menu);

        buttonGame = findViewById(R.id.Game);
        buttonList = findViewById(R.id.List);
        buttonMenu = findViewById(R.id.Menu);
        buttonParameters = findViewById(R.id.cog);

        if (savedInstanceState == null) {
            activeFragment = menuFragment;

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container_view, menuFragment, "MENU")
                    .add(R.id.fragment_container_view, matchFragment, "MATCH").hide(matchFragment)
                    .add(R.id.fragment_container_view, wordListFragment, "LIST").hide(wordListFragment)
                    .commit();
        }


        buttonList.setOnClickListener(v -> {
            resetButtonColor();
            switchToFragment(wordListFragment);
            v.setBackgroundColor(Color.parseColor("#3B3B3B"));
        });
        buttonGame.setOnClickListener(v -> {
            resetButtonColor();
            switchToFragment(matchFragment);
            v.setBackgroundColor(Color.parseColor("#3B3B3B"));
        });

        buttonParameters.setOnClickListener(v -> {
            Intent intent = new Intent(SecondMenuActivity.this, ParametersActivity.class);
            startActivity(intent);
        });

        buttonMenu.setOnClickListener(v -> {
            resetButtonColor();
            switchToFragment(menuFragment);
            v.setBackgroundColor(Color.parseColor("#3B3B3B"));
        });
    }

    private void resetButtonColor() {
        buttonList.setBackgroundColor(Color.parseColor("#1B1B1B"));
        buttonGame.setBackgroundColor(Color.parseColor("#1B1B1B"));
        buttonMenu.setBackgroundColor(Color.parseColor("#1B1B1B"));
    }

    private void switchToFragment(Fragment fragment) {
        if (!(fragment == activeFragment)) {
            getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out)
                    .hide(activeFragment)
                    .show(fragment)
                    .commit();
            activeFragment = fragment;
        }
    }
}
