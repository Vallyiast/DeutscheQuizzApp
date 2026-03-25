package com.example.deutschquiz.view;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.deutschquiz.CommonUses;
import com.example.deutschquiz.dictionary.WikDictionary;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GuessView extends ViewModel {

    private final MutableLiveData<String> word = new MutableLiveData<>();
    private final MutableLiveData<List<String>> translations = new MutableLiveData<>();

    List<String[]> dictionnary = new ArrayList<>();
    List<Integer> dictionnaryIndexList = new ArrayList<>();

    int currentIndex;
    private final Executor executor = Executors.newSingleThreadExecutor();
    private WikDictionary repo;

    public void init(Context context, String wordsFileName) {

        if (repo != null) return;
        repo = new WikDictionary(context);

        dictionnary = CommonUses.getThemeList(context.getAssets(),wordsFileName);
        dictionnaryIndexList = IntStream.range(1, dictionnary.size()).boxed().collect(Collectors.toList());
        Collections.shuffle(dictionnaryIndexList);
    }

    public int nbOfRemainingWords() {
        return dictionnaryIndexList.size();
    }
    public boolean dictionnaryIsEmpty() {
        return dictionnaryIndexList.isEmpty();
    }

    public String getNextWord() {
        currentIndex = dictionnaryIndexList.remove(0);
        String germanWord = dictionnary.get(currentIndex)[0];
        loadTranslations(germanWord);
        return germanWord;
    }

    public LiveData<String> getWord() {
        return word;
    }
    public LiveData<List<String>> getTranslations() {
        return translations;
    }
    public void loadTranslations(String germanWord) {
        executor.execute(() -> {
            List<String> result = repo.getTranslations(germanWord);
            translations.postValue(result);
            word.postValue(germanWord);
        });
    }

    public void forgotWord() {
        dictionnaryIndexList.add(currentIndex);
    }
}
