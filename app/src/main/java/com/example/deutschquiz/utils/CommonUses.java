package com.example.deutschquiz.utils;


import com.example.deutschquiz.database.ScoreDataBase;
import com.example.deutschquiz.model.Word;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CommonUses {

    public static String translationLanguage = "en";
    public static int nbButtonsMatchActivity = 7;
    public static int nbButtonsQuizActivity = 5;
    public static boolean includeTransparentWords;


    public static String formatTranslations(List<String> translations) {
        if (translations == null || translations.isEmpty()) return "?";

        String text = translations.get(0);
        if (translations.size() > 1) text += ",\n" + translations.get(1);
        if (translations.size() > 2) text += ",\n" + translations.get(2);
        return text;
    }

    /**
     * Extrait de la base de données les indexes utiles du dictionnaire
     * @param db databases
     * @return dictionnaire d'index utile
     */
    public static List<Word> extractionDictionnaire(ScoreDataBase db, List<Word> dictionnaire) {
        List<Word> dictionnaire_utile = new ArrayList<>();
        for (Word word: dictionnaire) {
            if (db.getScore(word.getWordString())>-10) {
                dictionnaire_utile.add(word);
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
    public static Word indexSuivant(ScoreDataBase scoreDB, List<Word> dico) {
        Random rand = new Random();
        double longueur = 0;
        for (Word word : dico) {
            longueur += Math.pow(2,scoreDB.getScore(word.getWordString()));
        }
        int index = 0;
        double val = rand.nextFloat()*longueur;
        double sum = Math.pow(2,scoreDB.getScore(dico.get(index).getWordString()));
        while (sum < val && index < dico.size() - 1) {
            index++;
            sum += Math.pow(2,scoreDB.getScore(dico.get(index).getWordString()));
        }
        return dico.get(index);
    }

}
