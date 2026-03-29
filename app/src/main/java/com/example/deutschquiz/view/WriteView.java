package com.example.deutschquiz.view;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.deutschquiz.utils.CommonUses;
import com.example.deutschquiz.database.ScoreDataBase;
import com.example.deutschquiz.database.WikDictionary;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class WriteView extends ViewModel {


    Random rand = new Random();
    final List<Character> vowels = Arrays.asList('a', 'e', 'i', 'o', 'u', 'ä', 'ü', 'ï', 'ö', 'ë');
    final List<Character> consonants = Arrays.asList('b','c','d','f','g','h','j','k','l','m','n','p','q','r','s','t','v','w','x','y','z','ß');
    int indexCharactereReponse = 0;
    boolean FLAG_voyelle = true; //Flag supplémentaire utilisé lorsque la longueur du mot écrit dépasse celle de la traduction (ie le mot est faux)
    List<String[]> dictionnary = new ArrayList<>();

    int questionIndex = 0;
    private ScoreDataBase scores;
    private List<Integer> listIndex;

    WikDictionary repo;


    private final MutableLiveData<String> word = new MutableLiveData<>();
    private final MutableLiveData<List<String>> translations = new MutableLiveData<>();
    private final Executor executor = Executors.newSingleThreadExecutor();

    public void init(Context context, String wordsFileName) {
        if (repo != null) return;
        repo = new WikDictionary(context);
        scores = new ScoreDataBase(context,wordsFileName);
        dictionnary = CommonUses.getThemeList(context.getAssets(),wordsFileName);
        listIndex = CommonUses.extractionDictionnaire(scores, dictionnary); //List des index utiles du dictionnary
    }

    public void getNextWord() {
        questionIndex = CommonUses.indexSuivant(scores, dictionnary, listIndex);
        String germanWord = dictionnary.get(questionIndex)[0];
        loadTranslations(germanWord);
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
