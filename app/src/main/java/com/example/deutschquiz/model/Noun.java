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

    @Override
    public String getPrettyWordString() {
        if (gender.equals(Gender.FEMININ)) {
            return "die "+super.getPrettyWordString();
        } else if (gender.equals(Gender.NEUTRAL)) {
            return "das "+super.getPrettyWordString();
        } else {
            return "der "+super.getPrettyWordString();
        }
    }

    public String getPlural() {
        return "die "+super.getWordString()+plural.split("-")[1];
    }
}
