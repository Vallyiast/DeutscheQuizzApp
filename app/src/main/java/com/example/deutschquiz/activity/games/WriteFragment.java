package com.example.deutschquiz.activity.games;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.deutschquiz.utils.CommonUses;
import com.example.deutschquiz.R;
import com.example.deutschquiz.utils.UsedColors;
import com.example.deutschquiz.view.WriteView;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class WriteFragment extends Fragment {

    private WriteView viewModel;
    final Random rand = new Random();
    static final List<Character> vowels = Arrays.asList('a', 'e', 'i', 'o', 'u', 'ä', 'ü', 'ï', 'ö', 'ë');
    static final List<Character> consonants = Arrays.asList('b','c','d','f','g','h','j','k','l','m','n','p','q','r','s','t','v','w','x','y','z','ß');
    int indexCharactereReponse = 0;
    boolean FLAG_voyelle = true; //Flag supplémentaire utilisé lorsque la longueur du mot écrit dépasse celle de la traduction (ie le mot est faux)

    TextView textviewQuestion, textviewReponse;
    Button next, main, evaluate;
    MaterialCardView lettersContainer, cadre;
    final List<Button> characterButtons = new ArrayList<>();
    String currentGermanWord;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(WriteView.class);
        viewModel.init(requireContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_write, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {

        main = view.findViewById(R.id.main_menu);
        textviewQuestion = view.findViewById(R.id.question);
        textviewReponse = view.findViewById(R.id.reponse);
        next = view.findViewById(R.id.next_button);
        evaluate = view.findViewById(R.id.evaluate);
        lettersContainer = view.findViewById(R.id.lettres_container);
        cadre = view.findViewById(R.id.cadre);

        characterButtons.add(view.findViewById(R.id.lettre1));
        characterButtons.add(view.findViewById(R.id.lettre2));
        characterButtons.add(view.findViewById(R.id.lettre3));
        characterButtons.add(view.findViewById(R.id.lettre4));
        characterButtons.add(view.findViewById(R.id.lettre5));
        characterButtons.add(view.findViewById(R.id.lettre6));

        main.setOnClickListener(v -> requireActivity().finish());

        viewModel.getTranslations().observe(getViewLifecycleOwner(), translations -> textviewQuestion.setText(CommonUses.formatTranslations(translations)));

        viewModel.getWord().observe(getViewLifecycleOwner(), germanWord -> {
            currentGermanWord = germanWord.toLowerCase();
            updateCharacters();
        });

        next.setOnClickListener(v -> {
            textviewReponse.setText("");
            updateQuestion();
        });

        evaluate.setOnClickListener(v -> {
            next.setVisibility(View.VISIBLE);
            checkAnswer(viewModel.evaluate(textviewReponse.getText().toString()));
        });
        updateQuestion();
    }

    /**
     * Give a new word to translate
     */
    private void updateQuestion() {
        lettersContainer.setVisibility(View.VISIBLE);
        evaluate.setVisibility(View.VISIBLE);
        next.setVisibility(View.INVISIBLE);
        evaluate.setAlpha(1.0f);

        lettersContainer.animate()
                .alpha(0.0f)
                .setDuration(0)
                .withEndAction(() -> {
                    // Une fois le fade-out terminé
                    lettersContainer.animate()
                            .alpha(1.0f)
                            .setDuration(500)
                            .start();
                })
                .start();

        viewModel.getNextWord();
        textviewReponse.setText("");
        textviewReponse.setTextColor(Color.WHITE);

        cadre.setCardBackgroundColor(UsedColors.background_color);
        cadre.setStrokeColor(UsedColors.border_color);

        indexCharactereReponse = 0;
    }

    /**
     * Update the buttons containing letters
     */
    private void updateCharacters() {
        int index_correct_char = rand.nextInt(6);

        if (currentGermanWord.length()> indexCharactereReponse) {
            if (vowels.contains(currentGermanWord.charAt(indexCharactereReponse))) {
                fillButtonsLetters(vowels);
            } else {
                fillButtonsLetters(consonants);
            }
            characterButtons.get(index_correct_char).setText(String.valueOf(currentGermanWord.charAt(indexCharactereReponse)));
        } else {

            if (FLAG_voyelle) {
                fillButtonsLetters(vowels);
                FLAG_voyelle = false;
            } else {
                fillButtonsLetters(consonants);
                FLAG_voyelle = true;
            }

        }
    }

    /**
     * Method filling with the letter list giving the dedicated buttons
     * @param lettres List of letters that will fill the buttons
     */
    private void fillButtonsLetters(List<Character> lettres) {
        List<Character> copieLettres = new ArrayList<>(lettres);
        Collections.shuffle(copieLettres);

        for (int i = 0; i< characterButtons.size(); i++) {
            characterButtons.get(i).setText(copieLettres.get(i).toString());
            characterButtons.get(i).setOnClickListener(v -> {
                textviewReponse.setText(String.format("%s%s", textviewReponse.getText(),((Button) v).getText()));
                indexCharactereReponse++;
                updateCharacters();
            });
        }
    }

    private void checkAnswer(boolean isTrue) {
        if (isTrue) {
            textviewReponse.setTextColor(UsedColors.light_color_Win);
            cadre.setCardBackgroundColor(UsedColors.dark_color_Win);
            cadre.setStrokeColor(UsedColors.light_color_Win);

        } else {
            textviewReponse.setTextColor(UsedColors.light_color_Loose);
            cadre.setCardBackgroundColor(UsedColors.dark_color_Loose);
            cadre.setStrokeColor(UsedColors.light_color_Loose);
        }
        textviewReponse.setText(currentGermanWord);
        evaluate.animate()
                .alpha(0.0f).setDuration(500)
                .withEndAction(() -> evaluate.setVisibility(View.INVISIBLE))
                .start();
        lettersContainer.setVisibility(View.INVISIBLE);
    }
}