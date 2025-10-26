package com.example.deutschquiz;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class WriteActivity extends AppCompatActivity {
    Random rand = new Random();
    final List<Character> voyelles = Arrays.asList('a', 'e', 'i', 'o', 'u', 'ä', 'ü', 'ï', 'ö', 'ë');
    final List<Character> consonnes = Arrays.asList('b','c','d','f','g','h','j','k','l','m','n','p','q','r','s','t','v','w','x','y','z','ß');

    int index_charactere_reponse = 0;
    boolean FLAG_voyelle = true; //Flag supplémentaire utilisé lorsque la longueur du mot écrit dépasse celle de la traduction (ie le mot est faux)
    String reponse;
    List<String[]> dictionnaire = new ArrayList<>();
    TextView textview_question, textview_reponse;
    Button buttonSubmit, next, main, traduction;
    LinearLayout lettres_container;

    List<Button> character_buttons = new ArrayList<>();
    int questionIndex = 0;
    private ScoreDataBase scores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        scores = new ScoreDataBase(this,getIntent().getStringExtra("destination"));

        main = findViewById(R.id.main_menu);
        textview_question = findViewById(R.id.question);
        textview_reponse = findViewById(R.id.reponse);
        buttonSubmit = findViewById(R.id.buttonSubmit);
        next = findViewById(R.id.next_button);
        traduction = findViewById(R.id.traduction);
        lettres_container = findViewById(R.id.lettres_container);

        character_buttons.add(findViewById(R.id.lettre1));
        character_buttons.add(findViewById(R.id.lettre2));
        character_buttons.add(findViewById(R.id.lettre3));
        character_buttons.add(findViewById(R.id.lettre4));
        character_buttons.add(findViewById(R.id.lettre5));
        character_buttons.add(findViewById(R.id.lettre6));

        dictionnaire = CommonUses.getThemeList(this,getIntent().getStringExtra("destination"));

        main.setOnClickListener(v -> finish());

        updateQuestion();
        next.setOnClickListener(v -> {

            textview_reponse.setText("");
            updateQuestion();
        });
    }

    /**
     * Give a new word to translate
     */
    private void updateQuestion() {
        lettres_container.setVisibility(View.VISIBLE);
        buttonSubmit.setVisibility(View.VISIBLE);
        traduction.setVisibility(View.VISIBLE);

        buttonSubmit.setAlpha(1.0f);
        traduction.setAlpha(1.0f);

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

        questionIndex = QCMActivity.indexSuivant(scores, dictionnaire);
        textview_reponse.setText("");
        textview_reponse.setTextColor(Color.WHITE);
        textview_question.setText(dictionnaire.get(questionIndex)[1]);

        index_charactere_reponse = 0;
        reponse = dictionnaire.get(questionIndex)[0].toLowerCase();

        updateCharacters();


        buttonSubmit.setOnClickListener( v-> {
            String userInput = textview_reponse.getText().toString().toLowerCase();
            checkAnswer(userInput.equals(reponse.toLowerCase()));
        });
        traduction.setOnClickListener(v -> {
            textview_reponse.setText(reponse);
            traduction.animate().alpha(0.0f).setDuration(500)
                    .withEndAction(() -> traduction.setVisibility(View.INVISIBLE))
                    .start();
            buttonSubmit.animate().alpha(0.0f).setDuration(500)
                    .withEndAction(() -> traduction.setVisibility(View.INVISIBLE))
                    .start();
            lettres_container.setVisibility(View.INVISIBLE);
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
            int finalI = i;
            character_buttons.get(i).setOnClickListener(v -> {
                textview_reponse.setText(String.format("%s%s",textview_reponse.getText(),((Button) v).getText()));
                index_charactere_reponse++;
                updateCharacters();
            });
        }
    }

    private void checkAnswer(boolean isTrue) {
        if (isTrue) {
            textview_reponse.setTextColor(MainActivity.couleurW);
            traduction.animate()
                    .alpha(0.0f).setDuration(500)
                    .withEndAction(() -> traduction.setVisibility(View.INVISIBLE))
                    .start();
            buttonSubmit.animate()
                    .alpha(0.0f).setDuration(500)
                    .withEndAction(() -> traduction.setVisibility(View.INVISIBLE))
                    .start();
            lettres_container.setVisibility(View.INVISIBLE);
        } else {
            textview_reponse.setTextColor(MainActivity.couleurL);
        }
    }
}