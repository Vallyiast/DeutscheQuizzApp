package com.example.deutschquiz.view;

import android.content.Context;

import androidx.lifecycle.LiveData;

import java.util.List;

public interface IQuiz {
    void init(Context context);

    void changeTranslationDirection();

    LiveData<List<String>> getAnswerList();

    LiveData<String> getQuestion();

    void nextWord();

    boolean isACorrectResponse(String response);

    void modifyWordScore(String response);
    int getNbResponses();
    String currentTranslationDirection();
}
