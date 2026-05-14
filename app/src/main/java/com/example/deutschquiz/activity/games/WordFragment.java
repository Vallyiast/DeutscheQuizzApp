package com.example.deutschquiz.activity.games;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.deutschquiz.R;
import com.example.deutschquiz.WordRepository;
import com.example.deutschquiz.model.Word;
import com.example.deutschquiz.utils.LevenshteinDistance;


public class WordFragment extends Fragment {
    TextView wordText, translationsText, isTransparentText, scoreText;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_word, container, false);
    }
    public void setWord(Word word) {
        wordText.setText(word.getWordString());
        translationsText.setText(formatTranslationsWithDistance(word));
        isTransparentText.setText(word.translationIsTransparent()?"transparent":"");
        scoreText.setText((String.valueOf(WordRepository.getWordScore(word))));
    }

    private String formatTranslationsWithDistance(Word word) {
        if (word.getTranslation().isEmpty()) return "?";

        String text = word.getTranslation().get(0)+" "+ LevenshteinDistance.iterative(word.getWordString().toLowerCase(),word.getTranslation().get(0).toLowerCase());
        if (word.getTranslation().size() > 1) text += ",\n" + word.getTranslation().get(1)+" "+ LevenshteinDistance.iterative(word.getWordString().toLowerCase(),word.getTranslation().get(1).toLowerCase());
        if (word.getTranslation().size() > 2) text += ",\n" + word.getTranslation().get(2)+" "+ LevenshteinDistance.iterative(word.getWordString().toLowerCase(),word.getTranslation().get(2).toLowerCase());
        return text;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        wordText = view.findViewById(R.id.word);
        translationsText = view.findViewById(R.id.translations);
        isTransparentText = view.findViewById(R.id.istransparent);
        scoreText = view.findViewById(R.id.score);

    }
}
