package com.example.deutschquiz.activity;

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

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.deutschquiz.R;
import com.example.deutschquiz.utils.UsedColors;
import com.example.deutschquiz.view.MatchView;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class MatchFragment extends Fragment {

    private MatchView viewModel;
    private MaterialButton lastSelectedButton = null;
    private boolean isLastSelectedButtonQuestion;
    Button menuButton, next;
    LinearLayout leftCol, rightCol;
    List<MaterialButton> questionsButtons;
    List<MaterialButton> answersButtons;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(MatchView.class);
        viewModel.init(requireContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_match, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {

        menuButton = view.findViewById(R.id.main_menu);
        leftCol = view.findViewById(R.id.leftColumn);
        rightCol = view.findViewById(R.id.rightColumn);
        next = view.findViewById(R.id.next_button);

        menuButton.setOnClickListener(v -> requireActivity().finish());

        addButtonsToLayout();

        viewModel.getQuestionList().observe(getViewLifecycleOwner(), questionList -> {
            for (int i = 0; i< viewModel.getNbResponses(); i++) {
                MaterialButton question_button = questionsButtons.get(i);
                question_button.setBackgroundColor(Color.rgb(27, 27, 27));
                question_button.setText(questionList.get(i));
            }
        });

        viewModel.getAnswerList().observe(getViewLifecycleOwner(), answerList -> {
            for (int i = 0; i< viewModel.getNbResponses(); i++) {
                MaterialButton answer_button = answersButtons.get(i);
                if (answerList.size()>i) {
                    answer_button.setVisibility(View.VISIBLE);
                    answer_button.setBackgroundColor(Color.rgb(27, 27, 27));
                    answer_button.setText(answerList.get(i));
                } else {
                    answer_button.setVisibility(View.INVISIBLE);
                }
            }
        });

        next.setOnClickListener(v -> updateQuestion());
        updateQuestion();
    }

    private void addButtonsToLayout() {
        this.questionsButtons = new ArrayList<>();
        this.answersButtons = new ArrayList<>();
        this.leftCol.removeAllViews();
        this.rightCol.removeAllViews();
        for (int i = 0; i< viewModel.getNbResponses(); i++) {
            Context themedContext = new ContextThemeWrapper(
                    requireContext(),
                    R.style.PrimaryButton
            );

            MaterialButton question = new MaterialButton( themedContext);
            MaterialButton answer = new MaterialButton(themedContext );

            question.setOnClickListener(v -> onButtonClick(v,true));
            answer.setOnClickListener(v -> onButtonClick(v,false));

            leftCol.addView(question);
            this.questionsButtons.add(question);
            this.rightCol.addView(answer);
            this.answersButtons.add(answer);
        }
    }

    private void onButtonClick(View view, boolean is_question_button) {

        MaterialButton current_button = (MaterialButton) view;

        if (lastSelectedButton == null || (isLastSelectedButtonQuestion == is_question_button) ) {
            current_button.setStrokeColor(ColorStateList.valueOf(UsedColors.light_gray));
            lastSelectedButton = current_button;
            isLastSelectedButtonQuestion = is_question_button;
        } else {
            if (is_question_button ? viewModel.isTranslationRight(current_button.getText(),lastSelectedButton.getText()) : viewModel.isTranslationRight(lastSelectedButton.getText(),current_button.getText())) {
                current_button.setTextColor(UsedColors.dark_color_Win);
                lastSelectedButton.setTextColor(UsedColors.dark_color_Win);
                current_button.setStrokeColor(ColorStateList.valueOf(UsedColors.dark_color_Win));
                lastSelectedButton.setStrokeColor(ColorStateList.valueOf(UsedColors.dark_color_Win));
                current_button.setClickable(false);
                lastSelectedButton.setClickable(false);
            } else {
                current_button.setTextColor(UsedColors.dark_color_Loose);
                lastSelectedButton.setTextColor(UsedColors.dark_color_Loose);
            }
            lastSelectedButton = null;
        }
    }

    private void updateQuestion() {
        viewModel.nextWords();
        resetLayout();
    }

    private void resetLayout() {
        for (MaterialButton button: questionsButtons) {
            resetButtonLayout(button);
        }
        for (MaterialButton button: answersButtons) {
            resetButtonLayout(button);
        }
    }

    private void resetButtonLayout(MaterialButton button) {
        button.setClickable(true);
        button.setTextColor(UsedColors.text_color);
        button.setStrokeColor(ColorStateList.valueOf(UsedColors.border_color));
    }

}