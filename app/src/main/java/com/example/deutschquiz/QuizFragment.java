package com.example.deutschquiz;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class QuizFragment extends Fragment {
    private static final Random rand = new Random();
    LinearLayout button_container;
    TextView question_textview;
    Button next, variation, main;
    List<MaterialButton> answers_buttons = new ArrayList<>();
    SeekBar seekBar;

    int questionIndex = 0;
    int nbReponses = 3;
    byte variation_lang_DetoFr = 0;
    List<String[]> dictionnary = new ArrayList<>();
    List<Integer> list_index_utile = new ArrayList<>();
    public ScoreDataBase scores;
    boolean first_FLAG;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_qcm, container, false);

        scores = new ScoreDataBase(requireContext(),requireActivity().getIntent().getStringExtra("destination"));

        dictionnary = CommonUses.getThemeList(requireContext().getAssets(),requireActivity().getIntent().getStringExtra("destination"));
        list_index_utile = CommonUses.extractionDictionnaire(scores,dictionnary); //List des index utiles du dictionnaire

        main = view.findViewById(R.id.main_menu);
        button_container  = view.findViewById(R.id.conteneur_reponses);
        question_textview = view.findViewById(R.id.question_text);
        next = view.findViewById(R.id.next_button);
        variation = view.findViewById(R.id.variation_button);

        seekBar = view.findViewById(R.id.seekBar);
        seekBar.setProgress(nbReponses-1);
        addButtons();

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                nbReponses = progress+1;
                addButtons();
                updateQuestion();
            }
            @Override public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        main.setOnClickListener(v -> requireActivity().finish());
        variation.setOnClickListener(v -> {
            if (variation_lang_DetoFr==0) {
                variation_lang_DetoFr = 1;
                variation.setText("De->Fr");
            } else {
                variation_lang_DetoFr = 0;
                variation.setText("Fr->De");
            }
        });


        updateQuestion();
        next.setOnClickListener(v -> updateQuestion());
        return view;
    }

    /**
     *   Met à jour les bouttons du LinearLayout conteneur lors du changement du nombre de bouttons
     */
    private void addButtons() {
        this.answers_buttons = new ArrayList<>();
        this.button_container.removeAllViews();
        for (int i = 0;i<nbReponses;i++) {
            Context themedContext = new ContextThemeWrapper(
                    requireContext(),
                    R.style.ButtonCharacterDynamic
            );

            MaterialButton button = new MaterialButton(themedContext);
            this.button_container.addView(button);
            this.answers_buttons.add(button);
        }
    }


    /**
     * Met à jour les questions lors du pasage à la question suivante
     */
    private void updateQuestion() {
        question_textview.setText("");

        next.setVisibility(View.INVISIBLE);
        first_FLAG = true;

        questionIndex = CommonUses.indexSuivant(scores, dictionnary, list_index_utile);
        List<String> answers = new ArrayList<>();
        int correct_answer_index = rand.nextInt(nbReponses);

        answers.add(dictionnary.get(questionIndex)[variation_lang_DetoFr]);
        for (int i = 1; i < nbReponses; i++) {
            answers.add(dictionnary.get(CommonUses.aleatoireExclu(dictionnary.size(), questionIndex))[variation_lang_DetoFr]);
        }
        question_textview.setText(dictionnary.get(questionIndex)[1 - variation_lang_DetoFr]);

        for (int i = 0; i<nbReponses; i++) {
            Button answer_button = answers_buttons.get(i);
            answer_button.setBackgroundColor(Color.rgb(27, 27, 27));
            answer_button.setText(answers.get((correct_answer_index+i)%nbReponses));
            int finalI = i;
            answer_button.setOnClickListener(v -> onAnswerClick(v, correct_answer_index, finalI,  answer_button, answers) );
        }
    }


    private void onAnswerClick(View v, int r, int i, Button answer_button, List<String> answers) {
        next.setVisibility(View.VISIBLE);

        String target_answer = dictionnary.get(questionIndex)[0];
        if ((r+ i)%nbReponses==0) {
            if (first_FLAG) {
                scores.saveScore(target_answer,scores.getScore(target_answer)-1);
            }
            answer_button.setBackgroundColor(UsedColors.dark_color_Win);
        } else {
            if (first_FLAG) {
                scores.saveScore(target_answer,scores.getScore(target_answer)+1);
                String obtained_answer = answers.get((r+i)%nbReponses);
                scores.saveScore(obtained_answer,scores.getScore(obtained_answer)+1);
            }
            answer_button.setBackgroundColor(UsedColors.dark_color_Loose);
        }
        first_FLAG = false;
    }
}