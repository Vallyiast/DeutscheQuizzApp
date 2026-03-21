package com.example.deutschquiz;


import android.content.res.AssetManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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


    /**
     * Extrait de la base de données les indexes utiles du dictionnaire
     * @param db
     * @return dictionnaire d'index utile
     */
    public static List<Integer> extractionDictionnaire(ScoreDataBase db, List<String[]> dictionnaire) {
        List<Integer> dictionnaire_utile = new ArrayList<>();
        for (int i = 0; i<dictionnaire.size();i++) {
            if (db.getScore(dictionnaire.get(i)[0])>-10) {
                dictionnaire_utile.add(i);
            }
        }
        return dictionnaire_utile;
    }


    /**
     * Renvoie l'index du mot suivant tiré au hasard dans le dictionnaire de mot pondéré par les scores de la base de données
     * @param scoreDB score des mots (nombre d'échec moins nombre de réussite à trouver le mot du premier coup
     * @param dico dictionnaire des mots
     * @return index du mot dans le dictionnaire
     */
    public static int indexSuivant(ScoreDataBase scoreDB, List<String[]> dico, List<Integer> list_index) {
        Random rand = new Random();
        double longueur = 0;
        for (int i = 0; i<list_index.size();i++) {
            longueur += Math.pow(2,scoreDB.getScore(dico.get(list_index.get(i))[0]));
        }
        int index = 0;
        double val = rand.nextFloat()*longueur;
        double sum = Math.pow(2,scoreDB.getScore(dico.get(list_index.get(0))[0]));
        while (sum < val && index < dico.size() - 1) {
            index++;
            sum += Math.pow(2,scoreDB.getScore(dico.get(list_index.get(index))[0]));
        }
        return list_index.get(index);
    }

    /**
     * Renvoie un nombre aléatoire dans [0,interval]\{num}
     * @param interval borne superieur de l'intervalle
     * @param num nombre à exclure
     * @return nombre aléatoire
     */
    public static int aleatoireExclu(int interval, int num) {
        Random rand = new Random();
        int res = rand.nextInt(interval-1);
        if (res >= num) {
            return res+1;
        }
        return res;
    }
}
