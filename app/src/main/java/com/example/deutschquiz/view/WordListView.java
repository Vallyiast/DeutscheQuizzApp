package com.example.deutschquiz.view;

import androidx.lifecycle.ViewModel;

import com.example.deutschquiz.WordRepository;
import com.example.deutschquiz.model.Word;

import java.util.List;

public class WordListView extends ViewModel {
    public List<Word> getTranslations() {
        return WordRepository.getWordList();
    }
}
