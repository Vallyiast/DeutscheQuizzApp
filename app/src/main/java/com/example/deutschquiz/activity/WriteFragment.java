package com.example.deutschquiz.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.deutschquiz.CommonUses;
import com.example.deutschquiz.R;
import com.example.deutschquiz.ScoreDataBase;
import com.example.deutschquiz.UsedColors;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class WriteFragment extends Fragment {
    Random rand = new Random();
    final List<Character> voyelles = Arrays.asList('a', 'e', 'i', 'o', 'u', 'ä', 'ü', 'ï', 'ö', 'ë');
    final List<Character> consonnes = Arrays.asList('b','c','d','f','g','h','j','k','l','m','n','p','q','r','s','t','v','w','x','y','z','ß');
    int index_charactere_reponse = 0;
    boolean FLAG_voyelle = true; //Flag supplémentaire utilisé lorsque la longueur du mot écrit dépasse celle de la traduction (ie le mot est faux)
    String reponse;
    List<String[]> dictionnaire = new ArrayList<>();
    TextView textview_question, textview_reponse;
    Button next, main, evaluate;
    MaterialCardView lettres_container, cadre;

    List<Button> character_buttons = new ArrayList<>();
    int questionIndex = 0;
    private ScoreDataBase scores;
    private List<Integer> list_index;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_write, container, false);

        scores = new ScoreDataBase(getContext(),requireActivity().getIntent().getStringExtra("destination"));
        dictionnaire = CommonUses.getThemeList(requireContext().getAssets(),requireActivity().getIntent().getStringExtra("destination"));
        list_index = CommonUses.extractionDictionnaire(scores,dictionnaire); //List des index utiles du dictionnaire

        main = view.findViewById(R.id.main_menu);
        textview_question = view.findViewById(R.id.question);
        textview_reponse = view.findViewById(R.id.reponse);
        next = view.findViewById(R.id.next_button);
        evaluate = view.findViewById(R.id.evaluate);
        lettres_container = view.findViewById(R.id.lettres_container);
        cadre = view.findViewById(R.id.cadre);

        character_buttons.add(view.findViewById(R.id.lettre1));
        character_buttons.add(view.findViewById(R.id.lettre2));
        character_buttons.add(view.findViewById(R.id.lettre3));
        character_buttons.add(view.findViewById(R.id.lettre4));
        character_buttons.add(view.findViewById(R.id.lettre5));
        character_buttons.add(view.findViewById(R.id.lettre6));


        main.setOnClickListener(v -> requireActivity().finish());

        updateQuestion();
        next.setOnClickListener(v -> {

            textview_reponse.setText("");
            updateQuestion();
        });
        return view;
    }

    /**
     * Give a new word to translate
     */
    private void updateQuestion() {
        lettres_container.setVisibility(View.VISIBLE);
        evaluate.setVisibility(View.VISIBLE);
        next.setVisibility(View.INVISIBLE);
        evaluate.setAlpha(1.0f);

        lettres_container.animate()
                .alpha(0.0f)
                .setDuration(0)
                .withEndAction(() -> {
                    // Une fois le fade-out terminé
                    lettres_container.animate()
                            .alpha(1.0f)
                            .setDuration(500)
                            .start();
                })
                .start();

        questionIndex = CommonUses.indexSuivant(scores, dictionnaire, list_index);
        textview_reponse.setText("");
        textview_reponse.setTextColor(Color.WHITE);
        textview_question.setText(dictionnaire.get(questionIndex)[1]);
        cadre.setBackgroundColor(UsedColors.background_color);
        cadre.setStrokeColor(UsedColors.border_color);

        index_charactere_reponse = 0;
        reponse = dictionnaire.get(questionIndex)[0].toLowerCase();

        updateCharacters();

        evaluate.setOnClickListener(v -> {

            next.setVisibility(View.VISIBLE);
            checkAnswer(reponse.equals(textview_reponse.getText().toString().toLowerCase()));

        });
    }

    /**
     * Update the buttons containing letters
     */
    private void updateCharacters() {
        int index_correct_char = rand.nextInt(6);

        if (reponse.length()>index_charactere_reponse) {
            if (voyelles.contains(reponse.charAt(index_charactere_reponse))) {
                remplirLettresBouttons(voyelles);
            } else {
                remplirLettresBouttons(consonnes);
            }
            character_buttons.get(index_correct_char).setText(String.valueOf(reponse.charAt(index_charactere_reponse)));
        } else {

            if (FLAG_voyelle) {
                remplirLettresBouttons(voyelles);
                FLAG_voyelle = false;
            } else {
                remplirLettresBouttons(consonnes);
                FLAG_voyelle = true;
            }

        }
    }


    /**
     * Method filling with the letter list giving the dedicated buttons
     * @param lettres List of letters that will fill the buttons
     */
    private void remplirLettresBouttons(List<Character> lettres) {
        List<Character> copieLettres = new ArrayList<>(lettres);
        Collections.shuffle(copieLettres);

        for (int i = 0; i<character_buttons.size();i++) {
            character_buttons.get(i).setText(copieLettres.get(i).toString());
            character_buttons.get(i).setOnClickListener(v -> {
                textview_reponse.setText(String.format("%s%s",textview_reponse.getText(),((Button) v).getText()));
                index_charactere_reponse++;
                updateCharacters();
            });
        }
    }

    private void checkAnswer(boolean isTrue) {
        if (isTrue) {
            textview_reponse.setTextColor(UsedColors.light_color_Win);
            cadre.setBackgroundColor(UsedColors.dark_color_Win);
            cadre.setStrokeColor(UsedColors.light_color_Win);

            evaluate.animate()
                    .alpha(0.0f).setDuration(500)
                    .withEndAction(() -> evaluate.setVisibility(View.INVISIBLE))
                    .start();
            lettres_container.setVisibility(View.INVISIBLE);
        } else {
            textview_reponse.setTextColor(UsedColors.light_color_Loose);
            cadre.setBackgroundColor(UsedColors.dark_color_Loose);
            cadre.setStrokeColor(UsedColors.light_color_Loose);
        }
    }
}