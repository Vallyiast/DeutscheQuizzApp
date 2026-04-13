package com.example.deutschquiz.view;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.deutschquiz.model.Word;
import com.example.deutschquiz.WordRepository;
import com.example.deutschquiz.database.WikDictionary;

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

    List<Word> dictionnary = new ArrayList<>();
    List<Integer> dictionnaryIndexList = new ArrayList<>();

    int currentIndex;
    private final Executor executor = Executors.newSingleThreadExecutor();
    private WikDictionary repo;

    public void init(Context context) {
        if (repo != null) return;
        repo = new WikDictionary(context);

        dictionnary = WordRepository.getWordList();
        dictionnaryIndexList = IntStream.range(1, dictionnary.size()).boxed().collect(Collectors.toList());
        Collections.shuffle(dictionnaryIndexList);
    }

    public int nbOfRemainingWords() {
        return dictionnaryIndexList.size();
    }
    public boolean dictionnaryIsEmpty() {
        return dictionnaryIndexList.isEmpty();
    }

    public void getNextWord() {
        currentIndex = dictionnaryIndexList.remove(0);
        Word germanWord = dictionnary.get(currentIndex);
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
            List<String> result = repo.getTranslations(germanWord);
            translations.postValue(result);
            word.postValue(germanWord);
        });
    }

    public void forgotWord() {
        dictionnaryIndexList.add(currentIndex);
    }
}
