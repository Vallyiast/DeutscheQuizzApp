package com.example.deutschquiz.view;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.deutschquiz.WordRepository;
import com.example.deutschquiz.model.Word;
import com.example.deutschquiz.utils.CommonUses;
import com.example.deutschquiz.database.ScoreDataBase;
import com.example.deutschquiz.utils.LevenshteinDistance;

import java.util.List;

public class WriteView extends ViewModel {

    private ScoreDataBase scores;
    private List<Word> words;
    private final MutableLiveData<String> word = new MutableLiveData<>();
    private final MutableLiveData<List<String>> translations = new MutableLiveData<>();
    Word germanWord;

    public void init(Context context) {
        scores = new ScoreDataBase(context);
        words = WordRepository.getWordListWithScore(-20);
    }

    public void getNextWord() {
        germanWord = CommonUses.indexSuivant(scores, words);

        word.postValue(germanWord.getWordString());
        translations.postValue(germanWord.getTranslation());
    }

    public LiveData<String> getWord() {
        return word;
    }
    public LiveData<List<String>> getTranslations() {
        return translations;
    }

    public boolean evaluate(String answer) {
        boolean isValid = LevenshteinDistance.iterative(germanWord.getWordString().toLowerCase(),answer.toLowerCase())<=1;
        if (isValid) WordRepository.changeWordScore(germanWord,-2);
        else WordRepository.changeWordScore(germanWord,2);
        return isValid;
    }

}
