 package com.example.deutschquiz;

import com.example.deutschquiz.database.ScoreDataBase;
import com.example.deutschquiz.database.WikDictionary;
import com.example.deutschquiz.model.Noun;
import com.example.deutschquiz.model.Word;
import com.example.deutschquiz.providers.WordProvider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

 public class WordRepository {

    private static Map<Word,Integer> words;
    private static ScoreDataBase scores;

    public static void setWordList(WordProvider provider, WikDictionary repo, ScoreDataBase scoresDB) {
        scores = scoresDB;
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
        words = new HashMap<>();

        for (Word word : wordList) {
            words.put(word, scores.getScore(word.getWordString()));
        }

    }
    public static void changeWordScore(Word word,int score) {
        scores.changeScore(word.getWordString(),score);
    }

    public static List<Word> getWordList() {
        return List.copyOf(words.keySet());
    }

    public static List<Word> getWordListWithScore(int minimumScore) {
        return words.entrySet().stream()
                .filter(entry -> entry.getValue() >= minimumScore)
                .map(Map.Entry::getKey).collect(Collectors.toList());
    }

    public static List<Noun> getNounList() {
        return WordRepository.getWordList().stream().filter(word -> word.getClass()==Noun.class).map(word -> (Noun)word).collect(Collectors.toList());
    }

     public static int getWordScore(Word word) {
        return words.get(word);
     }
 }
