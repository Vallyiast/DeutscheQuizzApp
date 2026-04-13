package com.example.deutschquiz.providers;

import android.content.res.AssetManager;
import android.util.Log;

import com.example.deutschquiz.model.Adjective;
import com.example.deutschquiz.model.Adverb;
import com.example.deutschquiz.model.AutomaticConjugator;
import com.example.deutschquiz.model.Noun;
import com.example.deutschquiz.model.Verb;
import com.example.deutschquiz.model.Word;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CSVProvider implements WordProvider{
    private final AssetManager assetManager;
    private final String filename;
    private final Class<? extends Word> wordClass;


    public CSVProvider(AssetManager assetManager, String filename, Class<? extends Word> wordClass) {
        this.assetManager = assetManager;
        this.filename = filename;
        this.wordClass = wordClass;
    }
    @Override
    public List<Word> provideWithWords() {
        List<Word> dictionnaire = new ArrayList<>();
        try {
            InputStream is = assetManager.open(filename + ".txt");

            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = reader.readLine()) != null) {
                dictionnaire.add(obtainWordFromFileLine(line));
            }
            reader.close();
        } catch (IOException e) {
            Log.d("CSVProvider",filename+" list not found");
        }
        return dictionnaire;
    }

    private Word obtainWordFromFileLine(String line) {
        String[] splitLine = line.split(";");
        if (wordClass == Noun.class) {
            return new Noun(splitLine[0], null, "");
        } else if (wordClass == Verb.class) {
            return new Verb(splitLine[0],null,new AutomaticConjugator(splitLine[0]));
        } else if (wordClass == Adverb.class) {
            return new Adverb(splitLine[0]);
        } else if (wordClass == Adjective.class) {
            return new Adjective(splitLine[0]);
        } else {
            return new Word(splitLine[0]) {};
        }
    }


}
