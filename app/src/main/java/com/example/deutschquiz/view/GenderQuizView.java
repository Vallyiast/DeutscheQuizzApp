package com.example.deutschquiz.view;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.deutschquiz.WordRepository;
import com.example.deutschquiz.model.Gender;
import com.example.deutschquiz.model.Noun;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GenderQuizView extends ViewModel implements IQuiz {
    private final MutableLiveData<String> questionLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<String>> answers = new MutableLiveData<>();
    private Noun questionWord;
    List<Noun> germanWordsList = new ArrayList<>();
    List<Integer> dictionnaryIndexList;
    int index = 0;

    public void init(Context context) {
        germanWordsList = WordRepository.getNounList();
        List<String> genderPronouns = List.of("der","die","das");
        answers.postValue(genderPronouns);
        dictionnaryIndexList = IntStream.range(1, germanWordsList.size()).boxed().collect(Collectors.toList());
        Collections.shuffle(dictionnaryIndexList);
    }

    @Override
    public void changeTranslationDirection() {  }

    public void nextWord() {
        if (!(index<germanWordsList.size())) {
            index = 0;
            Collections.shuffle(dictionnaryIndexList);
        }
        index++;
        questionWord = germanWordsList.get(dictionnaryIndexList.get(index));
        questionLiveData.postValue(questionWord.getWordString());

    }
    public LiveData<String> getQuestion() {
        return questionLiveData;
    }
    public LiveData<List<String>> getAnswerList() {
        return answers;
    }
    public boolean isACorrectResponse(String word) {
        switch (word) {
            case "der":
                return Gender.MASCULIN.equals(questionWord.getGender());
            case "die":
                return Gender.FEMININ.equals(questionWord.getGender());
            case "das":
                return Gender.NEUTRAL.equals(questionWord.getGender());
            default:
                return false;
        }
    }

    @Override
    public void modifyWordScore(String response) {  }

    @Override
    public int getNbResponses() {
        return 3;
    }

    @Override
    public String currentTranslationDirection() {
        return "";
    }

}
