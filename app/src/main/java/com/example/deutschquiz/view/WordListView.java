package com.example.deutschquiz.view;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.deutschquiz.database.WikDictionary;
import com.example.deutschquiz.model.Word;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class WordListView extends ViewModel {

    private final MutableLiveData<List<Word>> words = new MutableLiveData<>();
    private final Executor executor = Executors.newSingleThreadExecutor();
    private WikDictionary repo;

    public void init(Context context) {
        if (repo != null) return;
        repo = new WikDictionary(context);
    }
    public LiveData<List<Word>> getTranslations() {
        return words;
    }
    public void loadTranslations(List<Word> germanWords) {
        executor.execute(() -> {
            for (Word word : germanWords) {
                List<String> result = repo.getTranslations(word.getWordString());
                word.addTranslations(result);
                words.postValue(germanWords);
            }
        });
    }

}
