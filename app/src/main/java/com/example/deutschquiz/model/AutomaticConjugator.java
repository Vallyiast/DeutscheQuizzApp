package com.example.deutschquiz.model;

public class AutomaticConjugator implements Conjugator{

    private String[] present;
    private String[] past;
    private String perfekt;

    public AutomaticConjugator(String word) {
        String radical = word.substring(0,word.length()-2);

        this.present = new String[] { radical+"e",radical+"st", radical+"t",word,radical+"t",word };
        this.past =  new String[] { radical+"te",radical+"test", radical+"tet",radical+"ten",radical+"tet",radical+"ten" };
        this.perfekt = "ge"+word;
    }

    public void setPresent(String[] present) {
        this.present = present;
    }

    public void setPast(String[] past) {
        this.past = past;
    }

    public void setPerfekt(String perfekt) {
        this.perfekt = perfekt;
    }

    @Override
    public String[] getPresent() {
        return present;
    }

    @Override
    public String[] getPast() {
        return past;
    }

    @Override
    public String getPerfekt() {
        return perfekt;
    }
}
