package com.example.deutschquiz;

import static android.view.View.INVISIBLE;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GuessFragment extends Fragment {


    Button main, forget_button,recall_button,boutton_mot;
    LinearLayout cadre;
    TextView title;
    List<String[]> dictionnaire = new ArrayList<>();
    List<Integer> indexList = new ArrayList<>();
    int indexCourant;

    int changement_mot;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_guess, container, false);

        main = view.findViewById(R.id.main_menu);
        title = view.findViewById(R.id.title);
        cadre = view.findViewById(R.id.cadre);
        boutton_mot = view.findViewById(R.id.word);
        forget_button = view.findViewById(R.id.forget);
        recall_button = view.findViewById(R.id.recall);

        dictionnaire = CommonUses.getThemeList(requireContext().getAssets(),requireActivity().getIntent().getStringExtra("destination"));
        indexList = java.util.stream.IntStream.range(1, dictionnaire.size()).boxed().collect(java.util.stream.Collectors.toList());
        Collections.shuffle(indexList);
        main.setOnClickListener(v -> requireActivity().finish());

        updateQuestion();
        forget_button.setOnClickListener(v -> {
            indexList.add(indexCourant);
            cadre.animate().rotationBy(360f).setDuration(300).start();

            updateQuestion();
        });
        recall_button.setOnClickListener(v -> {
            cadre.animate().rotationBy(-360f).setDuration(300).start();
            updateQuestion();
        });

        return view;
    }

    private void updateQuestion() {
        changement_mot = 0;
        if (indexList.isEmpty()) {
            title.setText("Pas d'autres mots à trouver !");
            cadre.setVisibility(INVISIBLE);
        } else {
            indexCourant = indexList.get(0);
            indexList.remove(0);
            boutton_mot.setText(dictionnaire.get(indexCourant)[0]);

            boutton_mot.setOnClickListener(v -> {
                changement_mot = 1 - changement_mot;
                cadre.animate().rotationYBy(360f).setDuration(300).start();

                boutton_mot.setText(dictionnaire.get(indexCourant)[changement_mot]);
            });
        }
    }
}
