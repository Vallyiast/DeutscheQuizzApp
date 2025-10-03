package com.example.deutschquiz;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListActivity extends AppCompatActivity {

    Button main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);


        main = findViewById(R.id.main_menu);
        main.setOnClickListener(v -> {
            finish();
        });

        ListView maListe = findViewById(R.id.maListe);

        // Données à afficher
        List<Map<String, String>> data = new ArrayList<>();

        try {
            AssetManager am = getAssets();
            InputStream is = am.open(getIntent().getStringExtra("destination") + ".txt");

            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String ligne;
            while ((ligne = reader.readLine()) != null) {
                String[] l=ligne.split(";");
                Map<String, String> item = new HashMap<>();
                item.put("a", l[0]); // Première colonne
                item.put("b", l[1]); // Deuxième colonne
                data.add(item);
            }
            reader.close();
        } catch (IOException e) {
            LinearLayout conteneur = findViewById(R.id.conteneur);
            TextView t = new TextView(this);
            t.setText(e.toString());
            conteneur.addView(t);
        }


        SimpleAdapter adapter = new SimpleAdapter(
                this,
                data, // ta liste de maps
                android.R.layout.simple_list_item_2, // layout intégré avec text1 + text2
                new String[]{"a", "b"},      // clés définies dans les maps
                new int[]{android.R.id.text1, android.R.id.text2} // où afficher chaque valeur
        );


        maListe.setAdapter(adapter);
    }
}
