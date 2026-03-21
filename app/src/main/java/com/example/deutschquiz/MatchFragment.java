package com.example.deutschquiz;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MatchFragment extends Fragment {
    private MaterialButton last_selected_button = null;
    private boolean is_last_selected_button_question;
    Button menu_button, next;
    LinearLayout left_col, right_col;
    Random rand = new Random();
    private List<String[]> dictionnary;

    List<MaterialButton> questions_buttons;
    List<MaterialButton> answers_buttons;
    int nb_responses;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_match, container, false);

        menu_button = view.findViewById(R.id.main_menu);
        left_col = view.findViewById(R.id.leftColumn);
        right_col = view.findViewById(R.id.rightColumn);
        next = view.findViewById(R.id.next_button);

        nb_responses = 7;

        menu_button.setOnClickListener(v -> requireActivity().finish());

        dictionnary = CommonUses.getThemeList(requireContext().getAssets(),requireActivity().getIntent().getStringExtra("destination"));
        nb_responses = Math.min(nb_responses,dictionnary.size());
        addButtonsToLayout();

        updateQuestion();

        next.setOnClickListener(v -> updateQuestion());
        return view;
    }

    private void addButtonsToLayout() {
        this.questions_buttons = new ArrayList<>();
        this.answers_buttons = new ArrayList<>();
        this.left_col.removeAllViews();
        this.right_col.removeAllViews();
        for (int i = 0;i<nb_responses;i++) {
            Context themedContext = new ContextThemeWrapper(
                    requireContext(),
                    R.style.ButtonCharacterDynamic
            );

            MaterialButton question = new MaterialButton( themedContext);
            MaterialButton answer = new MaterialButton(themedContext );

            question.setOnClickListener(v -> onButtonClick(v,true));
            answer.setOnClickListener(v -> onButtonClick(v,false));

            left_col.addView(question);
            this.questions_buttons.add(question);
            this.right_col.addView(answer);
            this.answers_buttons.add(answer);
        }
    }

    private void onButtonClick(View view, boolean is_question_button) {

        MaterialButton current_button = (MaterialButton) view;

        if (last_selected_button == null || (is_last_selected_button_question == is_question_button) ) {
            current_button.setStrokeColor(ColorStateList.valueOf(UsedColors.light_gray));
            last_selected_button = current_button;
            is_last_selected_button_question = is_question_button;
        } else {
            if (last_selected_button.getText() == getTranslation(current_button.getText(), is_question_button)) {
                current_button.setTextColor(UsedColors.dark_color_Win);
                last_selected_button.setTextColor(UsedColors.dark_color_Win);
                current_button.setStrokeColor(ColorStateList.valueOf(UsedColors.dark_color_Win));
                last_selected_button.setStrokeColor(ColorStateList.valueOf(UsedColors.dark_color_Win));
                current_button.setClickable(false);
                last_selected_button.setClickable(false);
            } else {
                current_button.setTextColor(UsedColors.dark_color_Loose);
                last_selected_button.setTextColor(UsedColors.dark_color_Loose);
            }
            last_selected_button = null;
        }
    }
    private CharSequence getTranslation(CharSequence text, boolean is_question) {
        if (is_question) {
            for (String[] strings : dictionnary) {
                if (strings[0].contentEquals(text)) {
                    return strings[1];
                }
            }
        } else {
            for (String[] strings : dictionnary) {
                if (strings[1].contentEquals(text)) {
                    return strings[0];
                }
            }
        }
        throw new RuntimeException("No translation found for :"+text);
    }

    private void updateQuestion() {
        List<Integer> shuffled_list = getShuffledList(nb_responses);
        List<String[]> questions = getQuestions(nb_responses);
        resetLayout();


        for (int i = 0; i<nb_responses; i++) {
            MaterialButton question_button = questions_buttons.get(i);
            MaterialButton answer_button = answers_buttons.get(shuffled_list.get(i));

            String[] stringtext = questions.get(i);

            question_button.setText(stringtext[0]);
            answer_button.setText(stringtext[1]);
        }
    }

    private void resetLayout() {
        for (MaterialButton button: questions_buttons) {
            resetButtonLayout(button);
        }
        for (MaterialButton button: answers_buttons) {
            resetButtonLayout(button);
        }
    }

    private void resetButtonLayout(MaterialButton button) {
        button.setClickable(true);
        button.setTextColor(UsedColors.text_color);
        button.setStrokeColor(ColorStateList.valueOf(UsedColors.border_color));
    }


    /**
     * Return shuffled list of integers between 0 and nb_max-1
     * @param nb_max number of integers in the list
     * @return List<Integer>
     */
    private List<Integer> getShuffledList(int nb_max) {
        List<Integer> shuffled_list = new ArrayList<>();
        for (int i = 0; i < nb_max; i++) {
            shuffled_list.add(i);
        }
        Collections.shuffle(shuffled_list);
        return shuffled_list;
    }

    private List<String[]> getQuestions(int nbResponses) {
        int rand_index = rand.nextInt(Math.max(0,dictionnary.size()-nbResponses));
        return dictionnary.subList(rand_index,rand_index+nbResponses);
    }


}