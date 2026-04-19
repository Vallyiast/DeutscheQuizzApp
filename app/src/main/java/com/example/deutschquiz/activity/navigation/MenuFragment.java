package com.example.deutschquiz.activity.navigation;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.deutschquiz.R;
import com.example.deutschquiz.activity.games.QuizFragment;
import com.example.deutschquiz.activity.games.WriteFragment;
import com.example.deutschquiz.activity.games.GuessFragment;
import com.example.deutschquiz.activity.games.MatchFragment;

public class MenuFragment extends Fragment {
    Button buttonQuiz, buttonGuess, buttonWrite, buttonMatch;
    private Fragment activeFragment;
    private final MatchFragment matchFragment = new MatchFragment();
    private final QuizFragment quizFragment = new QuizFragment();
    private final GuessFragment guessFragment = new GuessFragment();
    private final WriteFragment writeFragment = new WriteFragment();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_menu_fragment, container, false);

        buttonGuess = view.findViewById(R.id.Guess);
        buttonQuiz = view.findViewById(R.id.Quiz);
        buttonWrite = view.findViewById(R.id.Write);
        buttonMatch = view.findViewById(R.id.Match);


        if (savedInstanceState == null) {
            activeFragment = guessFragment;
            buttonGuess.setBackgroundColor(Color.parseColor("#1B1B1B"));
            getChildFragmentManager().beginTransaction()
                    .add(R.id.fragment_container_view, writeFragment, "WRITE").hide(writeFragment)
                    .add(R.id.fragment_container_view, matchFragment, "MATCH").hide(matchFragment)
                    .add(R.id.fragment_container_view, guessFragment, "GUESS")
                    .add(R.id.fragment_container_view, quizFragment, "QUIZ").hide(quizFragment)
                    .commit();
        }


        buttonMatch.setOnClickListener(v ->  {
            switchToFragment(matchFragment);
            v.setBackgroundColor(Color.parseColor("#1B1B1B"));
        });

        buttonQuiz.setOnClickListener(v ->  {
            switchToFragment(quizFragment);
            v.setBackgroundColor(Color.parseColor("#1B1B1B"));
        });

        buttonGuess.setOnClickListener(v -> {
            switchToFragment(guessFragment);
            v.setBackgroundColor(Color.parseColor("#1B1B1B"));
        });

        buttonWrite.setOnClickListener(v -> {
            switchToFragment(writeFragment);
            v.setBackgroundColor(Color.parseColor("#1B1B1B"));
        });
        return view;
    }

    private void switchToFragment(Fragment fragment) {

        buttonGuess.setBackgroundColor(Color.parseColor("#1B1B1B"));
        buttonQuiz.setBackgroundColor(Color.parseColor("#1B1B1B"));
        buttonMatch.setBackgroundColor(Color.parseColor("#1B1B1B"));
        buttonWrite.setBackgroundColor(Color.parseColor("#1B1B1B"));

        getChildFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out)
                .hide(activeFragment)
                .show(fragment)
                .commit();
        activeFragment = fragment;
    }
}
