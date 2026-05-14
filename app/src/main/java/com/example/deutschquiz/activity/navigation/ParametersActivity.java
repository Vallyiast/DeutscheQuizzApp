package com.example.deutschquiz.activity.navigation;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.widget.Button;
import android.widget.SeekBar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.deutschquiz.R;
import com.example.deutschquiz.utils.CommonUses;
import com.example.deutschquiz.utils.UsedColors;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.materialswitch.MaterialSwitch;

import java.util.HashMap;
import java.util.Map;

public class ParametersActivity extends AppCompatActivity {

    MaterialButton mainMenuButton, fr, en;
    SeekBar matchSeekBar, quizSeekBar, writeSeekBar;
    MaterialSwitch materialSwitch;
    Map<String, MaterialButton> languageButtons = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parameters);

        mainMenuButton = findViewById(R.id.main_menu);
        fr = findViewById(R.id.fr);
        en = findViewById(R.id.en);
        languageButtons.put("en",en);
        languageButtons.put("fr",fr);
        languageButtons.get(CommonUses.translationLanguage).setBackgroundColor(UsedColors.light_gray);

        matchSeekBar = findViewById(R.id.seekBarMatch);
        matchSeekBar.setProgress(CommonUses.nbButtonsMatchActivity);

        quizSeekBar = findViewById(R.id.seekBarQuiz);
        quizSeekBar.setProgress(CommonUses.nbButtonsQuizActivity);


        writeSeekBar = findViewById(R.id.seekBarLetters);
        writeSeekBar.setProgress(CommonUses.nbButtonsWritingActivity);

        materialSwitch = findViewById(R.id.switch1);
        materialSwitch.setChecked(CommonUses.includeTransparentWords);

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

        en.setOnClickListener(v -> {
            CommonUses.translationLanguage = "en";
            languageButtons.values().forEach(b->b.setBackgroundColor(UsedColors.background_color));
            en.setBackgroundColor(UsedColors.light_gray);
        });

        fr.setOnClickListener(v -> {
            CommonUses.translationLanguage = "fr";
            languageButtons.values().forEach(b->b.setBackgroundColor(UsedColors.background_color));
            fr.setBackgroundColor(UsedColors.light_gray);
        });

        mainMenuButton.setOnClickListener(v -> finish());
    }




}
