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
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class QuizView extends ViewModel implements IQuiz {
    private final MutableLiveData<String> questionLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<String>> answers = new MutableLiveData<>();
    private Word questionWord;
    final int nbResponses = CommonUses.nbButtonsQuizActivity;
    boolean answerToGerman = true;
    List<Word> germanWordsList = new ArrayList<>();
    List<Word> usedWordsList = new ArrayList<>();
    boolean firstTimeAnswering_FLAG;

    Random random = new Random();

    public void init(Context context) {
        germanWordsList = WordRepository.getWordList();
        usedWordsList = WordRepository.getWordListWithScore(-5); //List des index utiles du dictionnary
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

        questionWord = usedWordsList.get(random.nextInt(usedWordsList.size()));

        if (questionWord.getTranslation().isEmpty() || (!CommonUses.includeTransparentWords && questionWord.translationIsTransparent())) {
            nextWord();
        } else {
            List<Word> responses = getRandomWordsExcept(questionWord, nbResponses - 1);
            List<String> answerList = new ArrayList<>();

            if (answerToGerman) {
                questionLiveData.postValue(CommonUses.formatTranslations(questionWord.getTranslation()));

                answerList.add(questionWord.getWordString());
                answerList.addAll(responses.stream().map(Word::getWordString).collect(Collectors.toList()));
            } else {
                questionLiveData.postValue(questionWord.getPrettyWordString());

                answerList.add(questionWord.getTranslation().get(0));
                for (Word word : responses) {
                    if (!word.getTranslation().isEmpty()) answerList.add(word.getTranslation().get(0));
                }
            }
            Collections.shuffle(answerList);
            answers.postValue(answerList);
        }
    }
    public LiveData<String> getQuestion() {
        return questionLiveData;
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
            if (!word.equals(germanWord) && word.getClass() == germanWord.getClass()) randomWords.add(word);
        }
        return randomWords;
    }
    public boolean isACorrectResponse(String word) {
        return (answerToGerman ? questionWord.getWordString():questionWord.getTranslation().get(0)).equalsIgnoreCase(word);
    }
    public void modifyWordScore(String answer) {
        if (firstTimeAnswering_FLAG) {
            if (isACorrectResponse(answer)) {
                WordRepository.changeWordScore(questionWord,-1);
            } else {
                WordRepository.changeWordScore(questionWord,1);
                 // TODO : Update the score of the answer
            }
            firstTimeAnswering_FLAG = false;
        }
    }
}
