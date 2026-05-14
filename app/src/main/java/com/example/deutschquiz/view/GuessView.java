package com.example.deutschquiz.view;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.deutschquiz.model.Word;
import com.example.deutschquiz.WordRepository;
import com.example.deutschquiz.utils.CommonUses;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GuessView extends ViewModel {

    private final MutableLiveData<String> word = new MutableLiveData<>();
    private final MutableLiveData<List<String>> translations = new MutableLiveData<>();
    Word germanWord;
    List<Word> dictionnary = new ArrayList<>();
    List<Integer> dictionnaryIndexList = new ArrayList<>();

    int currentIndex;

    public void init() {
        dictionnary = WordRepository.getWordListWithScore(-10);
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
        if (germanWord!=null) WordRepository.changeWordScore(germanWord,-1);
        currentIndex = dictionnaryIndexList.remove(0);
        germanWord = dictionnary.get(currentIndex);
        if (!CommonUses.includeTransparentWords && germanWord.translationIsTransparent()) getNextWord();
        else {
            translations.postValue(germanWord.getTranslation());
            word.postValue(germanWord.getPrettyWordString());
        }
    }

    public LiveData<String> getWord() {
        return word;
    }
    public LiveData<List<String>> getTranslations() {
        return translations;
    }
    public void forgotWord() {
        WordRepository.changeWordScore(germanWord,1);
        dictionnaryIndexList.add(currentIndex);
    }

    public int getWordAssociatedColor() {
        return germanWord.hashCode();
    }
}
