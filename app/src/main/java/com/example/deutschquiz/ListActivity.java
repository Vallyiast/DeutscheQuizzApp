package com.example.deutschquiz;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    Button main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        ScoreDataBase scores = new ScoreDataBase(this, getIntent().getStringExtra("destination"));

        main = findViewById(R.id.main_menu);
        main.setOnClickListener(v -> finish());

        ListView maListe = findViewById(R.id.maListe);

        // Données à afficher
        List<String[]> data = new ArrayList<>();

        try {
            AssetManager am = getAssets();
            InputStream is = am.open(getIntent().getStringExtra("destination") + ".txt");

            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String ligne;
            while ((ligne = reader.readLine()) != null) {
                String[] l=ligne.split(";");
                String[] item = {l[0],l[1],String.valueOf(scores.getScore(l[0]))};
                data.add(item);
            }
            reader.close();
        } catch (IOException e) {
            LinearLayout conteneur = findViewById(R.id.conteneur);
            TextView t = new TextView(this);
            t.setText(e.toString());
            conteneur.addView(t);
        }


        ArrayAdapter<String[]> adapter = new ArrayAdapter<>(this, R.layout.list_item, R.id.textLeft, data) {
            @NonNull
            @Override
            public View getView(int position, View convertView, @NonNull ViewGroup parent) {
                if (convertView == null) {
                    LayoutInflater inflater = LayoutInflater.from(getContext());
                    convertView = inflater.inflate(R.layout.list_item, parent, false);
                }

                String[] item = data.get(position);

                TextView left = convertView.findViewById(R.id.textLeft);
                TextView center = convertView.findViewById(R.id.textCenter);
                TextView right = convertView.findViewById(R.id.textRight);

                left.setText(item[0]);
                center.setText(item[1]);
                right.setText(item[2]);

                return convertView;
            }
        };

        maListe.setAdapter(adapter);
    }
}
