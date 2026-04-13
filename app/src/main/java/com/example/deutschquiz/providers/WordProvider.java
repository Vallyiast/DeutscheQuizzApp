package com.example.deutschquiz.providers;

import com.example.deutschquiz.model.Word;

import java.util.List;

public interface WordProvider {
    List<Word> provideWithWords();
}
