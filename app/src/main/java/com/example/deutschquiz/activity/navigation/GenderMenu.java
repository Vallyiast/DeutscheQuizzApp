package com.example.deutschquiz.activity.navigation;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.deutschquiz.R;
import com.example.deutschquiz.activity.games.QuizFragment;

public class GenderMenu extends AppCompatActivity {

    Button buttonQuiz;
    private Fragment activeFragment;
    private final QuizFragment quizFragment = new QuizFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = new Bundle();
        args.putString("activity_quiz", "gender");
        quizFragment.setArguments(args);


        setContentView(R.layout.gender_menu);

        buttonQuiz = findViewById(R.id.Quiz);

        if (savedInstanceState == null) {
            activeFragment = quizFragment;

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container_view, quizFragment, "Quiz")
                    .commit();
        }


        buttonQuiz.setOnClickListener(v -> {
            resetButtonColor();
            switchToFragment(quizFragment);
            v.setBackgroundColor(Color.parseColor("#3B3B3B"));
        });

    }

    private void resetButtonColor() {
        buttonQuiz.setBackgroundColor(Color.parseColor("#1B1B1B"));
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
