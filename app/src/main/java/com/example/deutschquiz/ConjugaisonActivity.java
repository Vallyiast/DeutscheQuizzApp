package com.example.deutschquiz;

import android.graphics.PointF;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;
import java.util.Random;

public class ConjugaisonActivity extends AppCompatActivity {
    private View lastSelected = null;
    private LinesView linesView;

    Button menu_button;
    LinearLayout leftCol, rightCol;

    Random rand = new Random();

    private List<String[]> dictionnaire;

    private final String[] pronoms = {"ich","du","er,sie","wir","ihr","Sie"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conjugaison);

        menu_button = findViewById(R.id.main_menu);
        menu_button.setOnClickListener(v -> finish());

        leftCol = findViewById(R.id.leftColumn);
        rightCol = findViewById(R.id.rightColumn);
        linesView = findViewById(R.id.linesView);

        dictionnaire = CommonUses.getThemeList(this, "verbes");

        updateQuestion();

    }

    private void updateQuestion() {
        int index = rand.nextInt(dictionnaire.size());
        String[] conjugaisons = conjugueurListPresent(index);
        for (int i = 0; i<6; i++) {
            RadioButton rbg = new RadioButton(this);
            rbg.setText(pronoms[i]);
            rbg.setOnClickListener(v -> lastSelected = v);
            leftCol.addView(rbg);

            RadioButton rbd = new RadioButton(this);
            rbd.setText(conjugaisons[i]);
            rbd.setOnClickListener(v -> {
                if (lastSelected != null) {
                    PointF start = getCenter(lastSelected);
                    PointF end = getCenter(v);
                    linesView.addLine(start, end);
                    lastSelected = null;
                }
            });
            rightCol.addView(rbd);
        }
    }

    private PointF getCenter(View v) {
        int[] location = new int[2];
        v.getLocationOnScreen(location);
        float x = location[0] + v.getWidth()/2f;
        float y = location[1] + v.getHeight()/2f;
        return new PointF(x,y);
    }

    private String[] conjugueurListPresent(int index_dictionnaire) {
        String[] result = new String[6];
        for (int i = 0; i<6;i++) {
            String str = dictionnaire.get(index_dictionnaire)[2+i];
            if (str.isBlank()) {
                result[i] = conjugueurPresent(dictionnaire.get(index_dictionnaire)[0],i+1);
            }
            else {
                result[i] = str;
            }
        }
        return result;
    }

    private String conjugueurPresent(String infinitif, int index_pronom) {
        /* Conjuge les verbes donnés à l'infinitif selon le pronom personnel indexé par index_pronom (1-6)
         */
        String radical = infinitif.substring(0,infinitif.length()-2).toLowerCase();
        switch (index_pronom) {
            case 1:
                return radical.concat("e");
            case 2:
                return radical.concat("st");
            case 3:
            case 5:
                return radical.concat("t");
            default:
                return infinitif.toLowerCase();
        }
    }
}