package com.example.deutschquiz.model;

public class Verb extends Word {
    private final String perfektAuxiliary;
    private final String[] present;
    private final String[] past;
    private final String perfekt;

    public Verb(String word, String perfektAuxiliary, Conjugator conjugator) {
        super(word);
        String radical = word.substring(0,word.length()-2);

        this.perfektAuxiliary = perfektAuxiliary;
        this.present = conjugator.getPresent();
        this.past =  conjugator.getPast();
        this.perfekt = conjugator.getPerfekt();
    }

    public String getPerfekt() {
        return perfekt;
    }

    public String getPerfektAuxiliary() {
        return perfektAuxiliary;
    }

    public String[] getPresent() {
        return present;
    }

    public String[] getPast() {
        return past;
    }
}

