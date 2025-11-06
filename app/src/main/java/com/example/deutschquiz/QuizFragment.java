package com.example.deutschquiz;

import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class QuizFragment extends Fragment {
    private static final Random rand = new Random();
    LinearLayout conteneur;
    TextView questionText, feedbackText;
    Button next, variation, image_button, main;
    List<Button> answersButtons = new ArrayList<>();
    ImageView image;
    SeekBar seekBar;

    int questionIndex = 0;
    int nbReponses = 3;
    int nb = 0;
    byte varlangDetoFr = 0;
    boolean imagemode = false;

    List<String[]> dictionnaire = new ArrayList<>();


    public ScoreDataBase scores;

    boolean first_FLAG;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_qcm, container, false);

        scores = new ScoreDataBase(requireContext(),requireActivity().getIntent().getStringExtra("destination"));

        dictionnaire = CommonUses.getThemeList(requireContext().getAssets(),requireActivity().getIntent().getStringExtra("destination"));

        main = view.findViewById(R.id.main_menu);
        conteneur  = view.findViewById(R.id.conteneur_reponses);
        questionText = view.findViewById(R.id.question_text);
        feedbackText = view.findViewById(R.id.feedback_text);
        next = view.findViewById(R.id.next_button);
        variation = view.findViewById(R.id.variation_button);
        image_button = view.findViewById(R.id.image_mode_button);
        image = view.findViewById(R.id.monImage);
        image.setVisibility(View.GONE);

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
            if (varlangDetoFr==0) {
                varlangDetoFr = 1;
                variation.setText("De->Fr");
            } else {
                varlangDetoFr = 0;
                variation.setText("Fr->De");
            }
        });
        image_button.setOnClickListener(v -> { // Image mode gestion
            if (!imagemode) {
                imagemode = true;
                image_button.setText("Image mode");
                seekBar.setVisibility(View.GONE);
                image.setVisibility(View.VISIBLE);
                variation.setVisibility(View.INVISIBLE);
                varlangDetoFr = 0;
                nbReponses = 3;
                addButtons();
                updateQuestion();
            } else {
                imagemode = false;
                image_button.setText("To image mode");
                image.setImageResource(0);
                seekBar.setProgress(nbReponses-1);

                seekBar.setVisibility(View.VISIBLE);
                image.setVisibility(View.GONE);
                variation.setVisibility(View.VISIBLE);
                updateQuestion();
            }
        });

        updateQuestion();
        next.setOnClickListener(v -> updateQuestion());
        return view;
    }

    private void addButtons() {
        /*  Met à jour les bouttons du LinearLayout conteneur lors du changement du nombre de bouttons */
        this.answersButtons = new ArrayList<>();
        this.conteneur.removeAllViews();
        for (int i = 0;i<nbReponses;i++) {
            Button button = new Button(new ContextThemeWrapper(requireContext(), R.style.ButtonCommon));
            button.setBackgroundColor(MainActivity.couleurBR);

            this.conteneur.addView(button);
            this.answersButtons.add(button);
        }
    }
    private int aleatoireExclu(int num) {
        int res = rand.nextInt(dictionnaire.size());
        if (res == num) {
            return this.aleatoireExclu(num);
        }
        return res;
    }
    private void checkAnswer(boolean isTrue) {
        if (isTrue) {
            feedbackText.setText("✅ Bonne réponse !");
            feedbackText.setTextColor(MainActivity.couleurW);
        } else {
            feedbackText.setText("❌ Mauvaise réponse.");
            feedbackText.setTextColor(MainActivity.couleurL);
        }
    }
    public static int indexSuivant(ScoreDataBase scoreDB, List<String[]> dico) {
        double longueur = 0;
        for (int i = 0; i<dico.size();i++) {
            longueur += Math.pow(2,scoreDB.getScore(dico.get(i)[0]));
        }
        int index = 0;
        double val = QuizFragment.rand.nextFloat()*longueur;
        double sum = Math.pow(2,scoreDB.getScore(dico.get(0)[0]));
        while (sum < val && index < dico.size() - 1) {
            index++;
            sum += Math.pow(2,scoreDB.getScore(dico.get(index)[0]));
        }
        return index;
    }
    private void updateQuestion() {
        feedbackText.setText("?");
        questionText.setText("");

        first_FLAG = true;
        nb++;
        questionIndex = indexSuivant(scores, dictionnaire);
        List<String> answers = new ArrayList<>();
        int r = rand.nextInt(nbReponses);
        if (imagemode) {
            image.setImageResource(getResources().getIdentifier(dictionnaire.get(questionIndex)[0].toLowerCase(), "drawable", requireContext().getPackageName()));
        }
        answers.add(dictionnaire.get(questionIndex)[varlangDetoFr]);
        for (int i = 1; i < nbReponses; i++) {
            answers.add(dictionnaire.get(this.aleatoireExclu(questionIndex))[varlangDetoFr]);
        }
        questionText.setText(nb + ":" + dictionnaire.get(questionIndex)[1 - varlangDetoFr]);

        for (int i = 0; i<nbReponses; i++) {
            Button answer = answersButtons.get(i);
            answer.setBackgroundColor(MainActivity.couleurBR);
            answer.setText(answers.get((r+i)%nbReponses));

            int finalI = i;
            answer.setOnClickListener(v -> {
                checkAnswer((r+ finalI)%nbReponses==0);
                String target_answer = dictionnaire.get(questionIndex)[0];
                if ((r+ finalI)%nbReponses==0) {
                    if (first_FLAG) {
                        scores.saveScore(target_answer,scores.getScore(target_answer)-1);
                    }
                    answer.setBackgroundColor(MainActivity.couleurW);
                } else {
                    if (first_FLAG) {
                        scores.saveScore(target_answer,scores.getScore(target_answer)+1);
                        String obtained_answer = answers.get((r+finalI)%nbReponses);
                        scores.saveScore(obtained_answer,scores.getScore(obtained_answer)+1);
                    }
                    answer.setBackgroundColor(MainActivity.couleurL);
                }
                first_FLAG = false;
            });
        }
    }
}