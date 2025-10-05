package com.example.deutschquiz;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class WriteActivity extends AppCompatActivity {

    List<String[]> dictionnaire = new ArrayList<>();
    TextView question;
    EditText editTextUserInput;
    Button buttonSubmit, next, main, traduction;
    TextView textViewResult;
    int questionIndex = 0;
    private ScoreDataBase scores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        scores = new ScoreDataBase(this,getIntent().getStringExtra("destination"));

        main = findViewById(R.id.main_menu);
        question = findViewById(R.id.question);
        editTextUserInput = findViewById(R.id.editTextUserInput);
        buttonSubmit = findViewById(R.id.buttonSubmit);
        textViewResult = findViewById(R.id.textViewResult);
        next = findViewById(R.id.next_button);
        traduction = findViewById(R.id.traduction);

        try {
            AssetManager am = getAssets();
            InputStream is = am.open(getIntent().getStringExtra("destination") + ".txt");

            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String ligne;
            while ((ligne = reader.readLine()) != null) {
                dictionnaire.add(ligne.split(";"));
            }
            reader.close();
        } catch (IOException e) {
            LinearLayout conteneur = findViewById(R.id.conteneur);
            TextView t = new TextView(this);
            t.setText(e.toString());
            conteneur.addView(t);
        }

        main.setOnClickListener(v -> finish());

        updateQuestion();
        next.setOnClickListener(v -> {
            editTextUserInput.setText("");
            updateQuestion();
        });
    }

    private void updateQuestion() {
        questionIndex = QCMActivity.indexSuivant(scores, dictionnaire);
        question.setText(dictionnaire.get(questionIndex)[1]);
        buttonSubmit.setOnClickListener( v-> {
            String userInput = editTextUserInput.getText().toString().toLowerCase();
            checkAnswer(userInput.equals(dictionnaire.get(questionIndex)[0].toLowerCase()));
        });

        traduction.setOnClickListener(v -> editTextUserInput.setText(dictionnaire.get(questionIndex)[0]));
    }

    private void checkAnswer(boolean isTrue) {
        if (isTrue) {
            textViewResult.setText("✅ Bonne réponse !");
            textViewResult.setTextColor(MainActivity.couleurW);
        } else {
            textViewResult.setText("❌ Mauvaise réponse.");
            textViewResult.setTextColor(MainActivity.couleurL);
        }
    }
}