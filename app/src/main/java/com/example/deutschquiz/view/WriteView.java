package com.example.deutschquiz.view;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.deutschquiz.WordRepository;
import com.example.deutschquiz.model.Word;
import com.example.deutschquiz.utils.CommonUses;
import com.example.deutschquiz.database.ScoreDataBase;

import java.util.List;

public class WriteView extends ViewModel {

    private ScoreDataBase scores;
    private List<Word> usedWordsList;
    private final MutableLiveData<String> word = new MutableLiveData<>();
    private final MutableLiveData<List<String>> translations = new MutableLiveData<>();

    public void init(Context context) {
        scores = new ScoreDataBase(context);
        List<Word> words = WordRepository.getWordList();
        usedWordsList = CommonUses.extractionDictionnaire(scores, words); //List des index utiles du dictionnary
    }

    public void getNextWord() {
        Word germanWord = CommonUses.indexSuivant(scores, usedWordsList);

        word.postValue(germanWord.getWordString());
        translations.postValue(germanWord.getTranslation());
    }

    public LiveData<String> getWord() {
        return word;
    }
    public LiveData<List<String>> getTranslations() {
        return translations;
    }


}
