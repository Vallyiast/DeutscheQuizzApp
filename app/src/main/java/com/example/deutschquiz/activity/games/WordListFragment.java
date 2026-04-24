package com.example.deutschquiz.activity.games;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.deutschquiz.R;
import com.example.deutschquiz.model.Word;
import com.example.deutschquiz.view.WordListView;

import java.util.List;

public class WordListFragment extends Fragment {

    private WordListView viewModel;
    private final WordFragment wordFragment = new WordFragment();
    Button main;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(WordListView.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        main = view.findViewById(R.id.main_menu);
        main.setOnClickListener(v -> requireActivity().finish());

        View container = view.findViewById(R.id.word_view);
        ListView listView = view.findViewById(R.id.maListe);

        getChildFragmentManager().beginTransaction()
                .add(R.id.word_view, wordFragment, "WORD").hide(wordFragment)
                .commit();

        List<Word> data = viewModel.getTranslations();

        ArrayAdapter<Word> adapter = new ArrayAdapter<>(requireContext(), R.layout.list_item, R.id.textLeft, data) {

            @NonNull
            @Override
            public View getView(int position, View convertView, @NonNull ViewGroup parent) {
                if (convertView == null) {
                    LayoutInflater inflater = LayoutInflater.from(getContext());
                    convertView = inflater.inflate(R.layout.list_item, parent, false);
                }

                TextView left = convertView.findViewById(R.id.textLeft);
                TextView center = convertView.findViewById(R.id.textCenter);

                Word word = data.get(position);
                left.setText(word.getPrettyWordString());
                List<String> translations = word.getTranslation();

                if ((!translations.isEmpty())) {
                    center.setText(translations.get(0));
                } else {
                    center.setText("Loading...");
                }

                convertView.setOnClickListener(v -> {
                            container.setVisibility(View.VISIBLE);
                            wordFragment.setWord(word);
                            getChildFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out)
                                    .show(wordFragment)
                                    .commit();
                        }
                );

                return convertView;
            }
        };

        listView.setAdapter(adapter);
    }
}
