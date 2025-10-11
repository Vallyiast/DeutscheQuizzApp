package com.example.deutschquiz;

import static android.view.View.INVISIBLE;

import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class GuessActivity extends AppCompatActivity {

    Button main, forget_button,recall_button,boutton_mot;
    LinearLayout cadre;
    TextView title;
    List<String[]> dictionnaire = new ArrayList<>();
    List<Integer> indexList = new ArrayList<>();
    int indexCourant;

    int changement_mot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess);

        main = findViewById(R.id.main_menu);
        title = findViewById(R.id.title);
        cadre = findViewById(R.id.cadre);
        boutton_mot = findViewById(R.id.word);
        forget_button = findViewById(R.id.forget);
        recall_button = findViewById(R.id.recall);

        dictionnaire = CommonUses.getThemeList(this,getIntent().getStringExtra("destination"));
        indexList = java.util.stream.IntStream.range(1, dictionnaire.size()).boxed().collect(java.util.stream.Collectors.toList());
        Collections.shuffle(indexList);
        main.setOnClickListener(v -> finish());

        updateQuestion();
        forget_button.setOnClickListener(v -> {
            indexList.add(indexCourant);
            updateQuestion();
        });
        recall_button.setOnClickListener(v -> {
            updateQuestion();
        });
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
                cadre.animate().rotationBy(360f).setDuration(300).start();
                boutton_mot.setText(dictionnaire.get(indexCourant)[changement_mot]);
            });
        }
    }
}
