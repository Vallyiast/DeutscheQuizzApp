package com.example.deutschquiz.activity.navigation;

import android.os.Bundle;
import android.widget.Button;
import android.widget.SeekBar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.deutschquiz.R;
import com.example.deutschquiz.utils.CommonUses;
import com.google.android.material.materialswitch.MaterialSwitch;

public class ParametersActivity extends AppCompatActivity {

    Button mainMenuButton, fr, en;
    SeekBar matchSeekBar, quizSeekBar, writeSeekBar;
    MaterialSwitch materialSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parameters);

        mainMenuButton = findViewById(R.id.main_menu);
        fr = findViewById(R.id.fr);
        en = findViewById(R.id.en);

        matchSeekBar = findViewById(R.id.seekBarMatch);
        matchSeekBar.setProgress(CommonUses.nbButtonsMatchActivity);

        quizSeekBar = findViewById(R.id.seekBarQuiz);
        quizSeekBar.setProgress(CommonUses.nbButtonsQuizActivity);


        writeSeekBar = findViewById(R.id.seekBarLetters);
        writeSeekBar.setProgress(CommonUses.nbButtonsWritingActivity);

        materialSwitch = findViewById(R.id.switch1);
        materialSwitch.setActivated(CommonUses.includeTransparentWords);

        quizSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                CommonUses.nbButtonsQuizActivity = progress;
            }
            @Override public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        matchSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                CommonUses.nbButtonsMatchActivity = progress;
            }
            @Override public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        writeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                CommonUses.nbButtonsWritingActivity = progress;
            }
            @Override public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        materialSwitch.setOnClickListener(v -> CommonUses.includeTransparentWords = !CommonUses.includeTransparentWords);

        en.setOnClickListener(v -> CommonUses.translationLanguage = "en");

        fr.setOnClickListener(v -> CommonUses.translationLanguage = "fr");

        mainMenuButton.setOnClickListener(v -> finish());
    }
}
