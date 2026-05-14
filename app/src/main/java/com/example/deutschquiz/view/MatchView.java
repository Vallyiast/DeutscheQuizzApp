package com.example.deutschquiz.view;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.deutschquiz.WordRepository;
import com.example.deutschquiz.model.Word;
import com.example.deutschquiz.utils.CommonUses;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MatchView extends ViewModel {

    private final MutableLiveData<List<String>> questionsData = new MutableLiveData<>();
    private final MutableLiveData<List<String>> answersData = new MutableLiveData<>();
    final Map<String,Word> wordMap = new HashMap<>();
    private List<Word> dictionary;
    int nbResponses;


    public void init() {
        nbResponses = CommonUses.nbButtonsMatchActivity;

        dictionary = WordRepository.getWordListWithScore(-10);
        nbResponses = Math.min(nbResponses, dictionary.size());
    }

    public void nextWords() {
        List<Word> questions = getQuestions(nbResponses);
        questionsData.postValue(questions.stream().map(Word::getPrettyWordString).collect(Collectors.toList()));

        List<String> answers = new ArrayList<>();
        for (Word word: questions) {
            answers.add(word.getTranslation().get(0));
            wordMap.put(word.getPrettyWordString(), word);
        }
        Collections.shuffle(answers);
        answersData.postValue(answers);
    }

    public LiveData<List<String>> getQuestionList() {
        return questionsData;
    }
    public LiveData<List<String>> getAnswerList() {
        return answersData;
    }

    public boolean isTranslationRight(CharSequence germanWord, CharSequence translatedWord) {
        if (wordMap.containsKey((String) germanWord)) return wordMap.get((String) germanWord).getTranslation().get(0).equalsIgnoreCase((String) translatedWord);
        else return false;
    }

    public int getNbResponses() {
        return nbResponses;
    }

    private List<Word> getQuestions(int nbResponses) {
        List<Word> wordListTemp = new ArrayList<>(dictionary);
        Collections.shuffle(wordListTemp);
        List<Word> result = new ArrayList<>();
        while(result.size()<nbResponses && !wordListTemp.isEmpty()) {
            Word word = wordListTemp.remove(0);
            if (!word.translationIsTransparent() || CommonUses.includeTransparentWords) result.add(word);
        }
        return result;
    }
}
