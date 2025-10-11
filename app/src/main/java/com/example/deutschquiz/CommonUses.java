package com.example.deutschquiz;

import android.content.res.AssetManager;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CommonUses {


    public static List<String[]> getThemeList(AppCompatActivity context, String theme) {
        List<String[]> dictionnaire = new ArrayList<>();
        try {
            AssetManager am = context.getAssets();
            InputStream is = am.open(theme + ".txt");

            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String ligne;
            while ((ligne = reader.readLine()) != null) {
                dictionnaire.add(ligne.split(";"));
            }
            reader.close();
        } catch (IOException e) {
            Log.d("CommonUses",theme+" list not found");
        }
        return dictionnaire;
    }

}
