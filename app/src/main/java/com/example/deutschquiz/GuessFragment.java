package com.example.deutschquiz;

import static android.view.View.INVISIBLE;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GuessFragment extends Fragment {


    Button main_menu_button, forget_button, recall_button;
    MaterialCardView frame;
    TextView word, nb_remaining_words;
    List<String[]> dictionnary = new ArrayList<>();
    List<Integer> dictionnary_index_list = new ArrayList<>();
    int current_index;
    int FLAG_language;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_guess, container, false);

        main_menu_button = view.findViewById(R.id.main_menu);
        frame = view.findViewById(R.id.cadre);
        word = view.findViewById(R.id.word);
        forget_button = view.findViewById(R.id.forget);
        recall_button = view.findViewById(R.id.recall);
        nb_remaining_words = view.findViewById(R.id.scoreText);

        dictionnary = CommonUses.getThemeList(requireContext().getAssets(),requireActivity().getIntent().getStringExtra("destination"));
        dictionnary_index_list = IntStream.range(1, dictionnary.size()).boxed().collect(Collectors.toList());
        Collections.shuffle(dictionnary_index_list);
        main_menu_button.setOnClickListener(v -> requireActivity().finish());

        update_qustion();
        forget_button.setOnClickListener(v -> {
            dictionnary_index_list.add(current_index);
            frame.animate().rotationBy(360f).setDuration(300).start();
            update_qustion();
        });
        recall_button.setOnClickListener(v -> {
            frame.animate().rotationBy(-360f).setDuration(300).start();
            update_qustion();
        });

        return view;
    }

    private void update_qustion() {
        nb_remaining_words.setText(String.format(Locale.getDefault(),"%d",dictionnary_index_list.size()));
        FLAG_language = 1;
        if (dictionnary_index_list.isEmpty()) {
            frame.setVisibility(INVISIBLE);
        } else {
            current_index = dictionnary_index_list.get(0);
            dictionnary_index_list.remove(0);
            word.setText(dictionnary.get(current_index)[FLAG_language]);

            frame.setOnClickListener(v -> {
                FLAG_language = 1 - FLAG_language;
                frame.animate().rotationYBy(360f).setDuration(300).start();
                word.setText(dictionnary.get(current_index)[FLAG_language]);
            });
        }
    }
}
