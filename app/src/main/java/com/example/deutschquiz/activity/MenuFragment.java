package com.example.deutschquiz.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.deutschquiz.R;

public class MenuFragment extends Fragment {
    Button buttonQuiz, buttonGuess, buttonWrite, buttonMatch;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_menu_fragment, container, false);


        buttonGuess = view.findViewById(R.id.Guess);
        buttonQuiz = view.findViewById(R.id.Quiz);
        buttonWrite = view.findViewById(R.id.Write);
        buttonMatch = view.findViewById(R.id.Match);

        // Affiche le premier fragment au lancement
        if (savedInstanceState == null) {
            buttonGuess.setBackgroundColor(Color.parseColor("#1B1B1B"));
            getChildFragmentManager().beginTransaction()
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

        buttonWrite.setOnClickListener(v -> {
            switchToFragment(new WriteFragment());
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
                .replace(R.id.fragment_container_view,fragment)
                .addToBackStack(null)
                .commit();
    }
}
