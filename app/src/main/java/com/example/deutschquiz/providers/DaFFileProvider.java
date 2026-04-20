package com.example.deutschquiz.providers;

import android.content.res.AssetManager;
import android.util.Log;

import com.example.deutschquiz.model.Gender;
import com.example.deutschquiz.model.Noun;
import com.example.deutschquiz.model.Word;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class DaFFileProvider implements WordProvider{

    private final AssetManager assetManager;
    private final String filename;
    public DaFFileProvider(AssetManager assetManager, String filename) {
        this.assetManager = assetManager;
        this.filename = filename;
    }

    @Override
    public List<Word> provideWithWords() {
        List<Word> dictionnaire = new ArrayList<>();
        try {
            InputStream is = assetManager.open("DaF/"+filename);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = reader.readLine()) != null) {
                dictionnaire.add(obtainWordFromFileLine(line));
            }
            reader.close();
        } catch (IOException e) {
            Log.d("Provider",e.getMessage());
            Log.d("Provider",filename+" list not found");
        }
        return dictionnaire;
    }

    private Word obtainWordFromFileLine(String line) {
        String[] splittedLine = line.split(",");
        String[] splitStart = splittedLine[0].split(" ");
        String plural = splittedLine.length>1 ? splittedLine[1].trim() : "(Nur Singular)";
        if (splitStart[0].equalsIgnoreCase("der")) {
            return new Noun(splitStart[1], Gender.MASCULIN,plural);
        } else if (splitStart[0].equalsIgnoreCase("das")) {
            return new Noun(splitStart[1], Gender.NEUTRAL,plural);
        } else if (splitStart[0].equalsIgnoreCase("die")) {
            return new Noun(splitStart[1], Gender.FEMININ,plural);
        } else {
            return new Word(splitStart[0]) {};
        }
    }
}
