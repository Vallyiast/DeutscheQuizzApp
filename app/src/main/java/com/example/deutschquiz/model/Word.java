package com.example.deutschquiz.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class Word {
    private final String word;
    private final List<String> translation = new ArrayList<>();

    public Word(String word){
        this.word = word;
    }
    public String getWordString() {
        return word;
    }

    public String getPrettyWordString() {
        return word;
    }

    public List<String> getTranslation() {
        return translation;
    }
    public void addTranslation(String translation) {
        this.translation.add(translation);
    }
    public void addTranslations(List<String> translations) {
        this.translation.addAll(translations);
    }

    public boolean translationIsTransparent() {
        for (String str:translation) {
            if (word.equalsIgnoreCase(str)) return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Word)) return false;
        Word word1 = (Word) o;
        return Objects.equals(word, word1.word);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(word);
    }

}
