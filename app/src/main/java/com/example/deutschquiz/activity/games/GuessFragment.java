package com.example.deutschquiz.activity.games;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.deutschquiz.utils.CommonUses;
import com.example.deutschquiz.R;
import com.example.deutschquiz.view.GuessView;
import com.google.android.material.card.MaterialCardView;

public class GuessFragment extends Fragment {

    private GuessView viewModel;
    private boolean isGerman = false;

    Button mainMenuButton, forgetButton, recallButton;
    MaterialCardView frame;
    TextView word, nbRemainingWords;

    private String currentGermanWord;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(GuessView.class);
        viewModel.init();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_guess, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        mainMenuButton = view.findViewById(R.id.main_menu);
        frame = view.findViewById(R.id.cadre);
        word = view.findViewById(R.id.word);
        forgetButton = view.findViewById(R.id.forget);
        recallButton = view.findViewById(R.id.recall);
        nbRemainingWords = view.findViewById(R.id.scoreText);

        mainMenuButton.setOnClickListener(v -> requireActivity().finish());

        // OBSERVE ONLY ONCE
        viewModel.getTranslations().observe(getViewLifecycleOwner(), translations -> {
            if (!isGerman) {
                word.setText(CommonUses.formatTranslations(translations));
            }
        });

        viewModel.getWord().observe(getViewLifecycleOwner(), germanWord -> {
            currentGermanWord = germanWord;
            if (isGerman) {
                word.setText(germanWord);
            }
        });

        forgetButton.setOnClickListener(v -> {
            viewModel.forgotWord();
            updateQuestion();
        });
        recallButton.setOnClickListener(v -> updateQuestion());
        frame.setOnClickListener(v -> flipCard());

        updateQuestion();
    }

    private void updateQuestion() {
        nbRemainingWords.setText(String.valueOf(viewModel.nbOfRemainingWords()));

        if (viewModel.dictionnaryIsEmpty()) {
            frame.setVisibility(View.INVISIBLE);
        } else {
            isGerman = false;
            viewModel.getNextWord();
        }
    }

    private void flipCard() {
        isGerman = !isGerman;

        frame.animate()
                .rotationYBy(360f)
                .setDuration(150)
                .withEndAction(() -> {
                    if (isGerman) word.setText(currentGermanWord);
                    else word.setText(CommonUses.formatTranslations(viewModel.getTranslations().getValue()));
                })
                .start();
    }


}
