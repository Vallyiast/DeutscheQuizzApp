package com.example.deutschquiz;

import com.example.deutschquiz.model.Word;
import com.example.deutschquiz.providers.WordProvider;

import java.util.List;

public class WordRepository {

    private static List<Word> wordList;

    public static void setWordList(WordProvider provider) {
        wordList = provider.provideWithWords();
    }

    public static List<Word> getWordList() {
        return wordList;
    }
}
