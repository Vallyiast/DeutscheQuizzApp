package com.example.deutschquiz.model;

public class Noun extends Word {
    private final Gender gender;
    private final String plural;
    public Noun(String word, Gender gender, String plural) {
        super(word);
        this.gender = gender;
        this.plural = plural;
    }
    public Gender getGender() {
        return gender;
    }

    public String getPlural() {
        return plural;
    }
}
