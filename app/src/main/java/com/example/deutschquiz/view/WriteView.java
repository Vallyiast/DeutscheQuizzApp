package com.example.deutschquiz.view;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.deutschquiz.WordRepository;
import com.example.deutschquiz.model.Word;
import com.example.deutschquiz.utils.CommonUses;
import com.example.deutschquiz.database.ScoreDataBase;
import com.example.deutschquiz.database.WikDictionary;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class WriteView extends ViewModel {

    List<Word> dictionnary = new ArrayList<>();

    int questionIndex = 0;
    private ScoreDataBase scores;
    private List<Word> usedWordsList;

    WikDictionary repo;


    private final MutableLiveData<String> word = new MutableLiveData<>();
    private final MutableLiveData<List<String>> translations = new MutableLiveData<>();
    private final Executor executor = Executors.newSingleThreadExecutor();

    public void init(Context context) {
        if (repo != null) return;
        repo = new WikDictionary(context);
        scores = new ScoreDataBase(context);
        dictionnary = WordRepository.getWordList();
        usedWordsList = CommonUses.extractionDictionnaire(scores, dictionnary); //List des index utiles du dictionnary
    }

    public void getNextWord() {
        Word germanWord = CommonUses.indexSuivant(scores, usedWordsList);
        loadTranslations(germanWord.getWordString());
    }

    public LiveData<String> getWord() {
        return word;
    }
    public LiveData<List<String>> getTranslations() {
        return translations;
    }
    public void loadTranslations(String germanWord) {
        executor.execute(() -> {
            word.postValue(germanWord);
            List<String> result = repo.getTranslations(germanWord);
            translations.postValue(result);
        });
    }

}
