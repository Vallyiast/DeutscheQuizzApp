package com.example.deutschquiz.activity.games;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.deutschquiz.R;
import com.example.deutschquiz.utils.UsedColors;
import com.example.deutschquiz.view.QuizView;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class QuizFragment extends Fragment {
    private QuizView viewModel;
    LinearLayout buttonContainer;
    TextView questionTextview;
    Button next, variation, main;
    List<MaterialButton> answersButtons = new ArrayList<>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(QuizView.class);
        viewModel.init(requireContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_qcm, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {

        main = view.findViewById(R.id.main_menu);
        buttonContainer = view.findViewById(R.id.conteneur_reponses);
        questionTextview = view.findViewById(R.id.question_text);
        next = view.findViewById(R.id.next_button);
        variation = view.findViewById(R.id.variation_button);

        addButtons();

        main.setOnClickListener(v -> requireActivity().finish());
        variation.setOnClickListener(v -> {
            viewModel.changeTranslationDirection();
            variation.setText(viewModel.currentTranslationDirection());
        });

        viewModel.getAnswerList().observe(getViewLifecycleOwner(), answerList -> {
            for (int i = 0; i< answerList.size(); i++) {
                MaterialButton answer_button = answersButtons.get(i);
                answer_button.setBackgroundColor(Color.rgb(27, 27, 27));
                answer_button.setText(answerList.get(i));
                answer_button.setTextColor(Color.parseColor("#FFFFFF"));
                answer_button.setOnClickListener(v -> onAnswerClick(answer_button));
            }
        });

        viewModel.getQuestion().observe(getViewLifecycleOwner(), word -> questionTextview.setText(word));


        next.setOnClickListener(v -> updateQuestion());
        updateQuestion();
    }

    /**
     *   Met à jour les bouttons du LinearLayout conteneur lors du changement du nombre de bouttons
     */
    private void addButtons() {
        this.answersButtons = new ArrayList<>();
        this.buttonContainer.removeAllViews();
        for (int i = 0; i< viewModel.getNbResponses(); i++) {
            Context themedContext = new ContextThemeWrapper(
                    requireContext(),
                    R.style.PrimaryButton
            );

            MaterialButton button = new MaterialButton(themedContext);
            this.buttonContainer.addView(button);
            this.answersButtons.add(button);
        }
    }

    /**
     * Met à jour les questions lors du pasage à la question suivante
     */
    private void updateQuestion() {
        viewModel.nextWord();
        next.setVisibility(View.INVISIBLE);
    }

    private void onAnswerClick(MaterialButton answerButton) {
        next.setVisibility(View.VISIBLE);
        if (viewModel.isACorrectResponse(answerButton.getText().toString())) {
            answerButton.setBackgroundColor(UsedColors.dark_color_Win);
        } else {
            answerButton.setBackgroundColor(UsedColors.dark_color_Loose);
        }
        viewModel.modifyWordScore(answerButton.getText().toString());
    }
}