package com.example.deutschquiz.view;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.deutschquiz.WordRepository;
import com.example.deutschquiz.database.ScoreDataBase;
import com.example.deutschquiz.model.Word;
import com.example.deutschquiz.utils.CommonUses;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class QuizView extends ViewModel {

    private final MutableLiveData<String> question = new MutableLiveData<>();
    private final MutableLiveData<List<String>> answers = new MutableLiveData<>();
    private String response;
    private Word questionString;
    final int nbResponses = CommonUses.nbButtonsQuizActivity;
    boolean answerToGerman = true;
    List<Word> germanWordsList = new ArrayList<>();
    List<Word> usedWordsList = new ArrayList<>();
    public ScoreDataBase scores;
    boolean firstTimeAnswering_FLAG;


    public void init(Context context) {
        scores = new ScoreDataBase(context);
        germanWordsList = WordRepository.getWordList();
        usedWordsList = CommonUses.extractionDictionnaire(scores,germanWordsList); //List des index utiles du dictionnary
    }

    public int getNbResponses() {
        return nbResponses;
    }

    public void changeTranslationDirection() {
        answerToGerman = !answerToGerman;
    }

    public String currentTranslationDirection() {
        if (answerToGerman) {
            return "to DE";
        } else {
            return "from DE";
        }
    }
    public void nextWord() {
        firstTimeAnswering_FLAG = true;
        Word germanWord = CommonUses.indexSuivant(scores, usedWordsList);

        List<Word> randomWordsList = getRandomWordsExcept(germanWord,nbResponses-1);
        List<String> answerList = new ArrayList<>();

        if (germanWord.getTranslation().isEmpty()) {
            nextWord();
        } else {
            if (answerToGerman) {
                question.postValue(CommonUses.formatTranslations(germanWord.getTranslation()));
                response = germanWord.getWordString();

                answerList.add(germanWord.getWordString());
                answerList.addAll(randomWordsList.stream().map(Word::getWordString).collect(Collectors.toList()));
            } else {
                question.postValue(germanWord.getWordString());
                response = germanWord.getTranslation().get(0);

                answerList.add(germanWord.getTranslation().get(0));
                for (Word word : randomWordsList) {
                    if (!word.getTranslation().isEmpty()) answerList.add(word.getTranslation().get(0));
                }
            }
            Collections.shuffle(answerList);
            answers.postValue(answerList);
        }
    }
    public LiveData<String> getQuestion() {
        return question;
    }
    public LiveData<List<String>> getAnswerList() {
        return answers;
    }


    private List<Word> getRandomWordsExcept(Word germanWord, int nbOfWords) {
        List<Word> randomWords = new ArrayList<>();

        List<Integer> dictionnaryIndexList = IntStream.range(1, germanWordsList.size()).boxed().collect(Collectors.toList());
        Collections.shuffle(dictionnaryIndexList);

        while (randomWords.size() != nbOfWords && !dictionnaryIndexList.isEmpty()) {
            Word word = germanWordsList.get(dictionnaryIndexList.remove(0));
            if (!word.equals(germanWord)) randomWords.add(word);
        }
        return randomWords;
    }

    public boolean isACorrectResponse(String word) {
        return response.equalsIgnoreCase(word);
    }

    public void modifyWordScore(String answer) {
        if (firstTimeAnswering_FLAG) {
            if (isACorrectResponse(answer)) {
                if (answerToGerman) {
                    scores.changeScore(response,-1);
                } else {
                    scores.changeScore(questionString.getWordString(),-1);
                }
            } else {
                if (answerToGerman) {
                    scores.changeScore(response,-1);
                    scores.changeScore(answer,-1);
                } else {
                    scores.changeScore(questionString.getWordString(),-1);
                    // TODO : Update the score when answers are not in german
                }

            }
            firstTimeAnswering_FLAG = false;
        }
    }


}
