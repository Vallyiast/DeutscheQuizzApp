package com.example.deutschquiz.view;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.deutschquiz.WordRepository;
import com.example.deutschquiz.database.WikDictionary;
import com.example.deutschquiz.model.Word;
import com.example.deutschquiz.utils.CommonUses;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class MatchView extends ViewModel {

    private final MutableLiveData<List<String>> questionsData = new MutableLiveData<>();
    private final MutableLiveData<List<String>> answersData = new MutableLiveData<>();

    final Map<String,String> wordMap = new HashMap<>();
    final Random rand = new Random();
    private List<Word> dictionary;
    int nbResponses;

    private final Executor executor = Executors.newSingleThreadExecutor();
    private WikDictionary repo;

    public void init(Context context) {
        if (repo != null) return;
        repo = new WikDictionary(context);
        nbResponses = CommonUses.nbButtonsMatchActivity;

        dictionary = WordRepository.getWordList();
        nbResponses = Math.min(nbResponses, dictionary.size());
    }

    public void nextWords() {
        List<String> questions = getQuestions(nbResponses);
        loadTranslations(questions);
    }

    public LiveData<List<String>> getQuestionList() {
        return questionsData;
    }
    public LiveData<List<String>> getAnswerList() {
        return answersData;
    }
    public void loadTranslations(List<String> questions) {
        executor.execute(() -> {
            List<String> toRemove = new ArrayList<>();
            List<String> answers = new ArrayList<>();
            for (String word: questions) {
                List<String> translation = repo.getTranslations(word);
                if (!translation.isEmpty()) {
                    answers.add(translation.get(0));
                    wordMap.put(word, translation.get(0));
                } else {
                    toRemove.add(word);
                }
            }
            questions.removeAll(toRemove);
            Collections.shuffle(answers);
            answersData.postValue(answers);
            questionsData.postValue(questions);
        });
    }

    public boolean isTranslationRight(CharSequence germanWord, CharSequence translatedWord) {
        if (wordMap.containsKey(germanWord)) return wordMap.get((String) germanWord).equalsIgnoreCase((String) translatedWord);
        else return false;
    }

    public int getNbResponses() {
        return nbResponses;
    }

    private List<String> getQuestions(int nbResponses) {
        int rand_index = rand.nextInt(Math.max(0, dictionary.size()-nbResponses));
        return dictionary.subList(rand_index,rand_index+nbResponses).stream().map(Word::getWordString).collect(Collectors.toList());
    }
}
