package com.example.deutschquiz;

import android.content.res.AssetManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CommonUses {


    public static List<String[]> getThemeList(AssetManager assetManager, String theme) {
        List<String[]> dictionnaire = new ArrayList<>();
        try {
            InputStream is = assetManager.open(theme + ".txt");

            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String ligne;
            while ((ligne = reader.readLine()) != null) {
                dictionnaire.add(ligne.split(";",-1));
            }
            reader.close();
        } catch (IOException e) {
            Log.d("CommonUses",theme+" list not found");
        }
        return dictionnaire;
    }

}
