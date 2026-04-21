 package com.example.deutschquiz;

import com.example.deutschquiz.database.WikDictionary;
import com.example.deutschquiz.model.Word;
import com.example.deutschquiz.providers.WordProvider;

import java.util.ArrayList;
import java.util.List;

 public class WordRepository {

    private static List<Word> words;

    public static void setWordList(WordProvider provider, WikDictionary repo) {
        List<Word> wordList = provider.provideWithWords();

        List<Word> toRemove = new ArrayList<>();
        for (Word word : wordList) {
            List<String> result = repo.getTranslations(word.getWordString());
            if (!result.isEmpty()) {
                word.addTranslations(result);
            } else {
                toRemove.add(word);
            }
        }
        wordList.removeAll(toRemove);
        words = wordList;

    }

    public static List<Word> getWordList() {
        return words;
    }
}
