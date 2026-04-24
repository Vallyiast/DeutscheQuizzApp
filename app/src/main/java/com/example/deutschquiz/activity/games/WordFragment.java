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
import com.example.deutschquiz.model.Word;
import com.example.deutschquiz.utils.CommonUses;

public class WordFragment extends Fragment {

    TextView wordText, translationsText, isTransparentText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_word, container, false);
    }
    public void setWord(Word word) {
        wordText.setText(word.getWordString());
        translationsText.setText(CommonUses.formatTranslations(word.getTranslation()));
        isTransparentText.setText(word.translationIsTransparent()?"transparent":"");
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        wordText = view.findViewById(R.id.word);
        translationsText = view.findViewById(R.id.translations);
        isTransparentText = view.findViewById(R.id.istransparent);

    }
}
