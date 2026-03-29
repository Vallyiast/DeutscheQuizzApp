package com.example.deutschquiz.activity;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.deutschquiz.R;
import com.example.deutschquiz.database.ScoreDataBase;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ListFragment extends Fragment {

    Button main;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_list, container, false);

        ScoreDataBase scores = new ScoreDataBase(requireContext(), requireActivity().getIntent().getStringExtra("destination"));

        main = view.findViewById(R.id.main_menu);
        main.setOnClickListener(v -> requireActivity().finish());

        ListView maListe = view.findViewById(R.id.maListe);

        // Données à afficher
        List<String[]> data = new ArrayList<>();

        try {
            AssetManager am = requireContext().getAssets();
            InputStream is = am.open( requireActivity().getIntent().getStringExtra("destination")+".txt");

            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String ligne;
            while ((ligne = reader.readLine()) != null) {
                String[] l=ligne.split(";");
                String[] item = {l[0],l[1],String.valueOf(scores.getScore(l[0]))};
                data.add(item);
            }
            reader.close();
        } catch (IOException e) {
            Log.d("ListFragment","verbes list not found");
        }


        ArrayAdapter<String[]> adapter = new ArrayAdapter<>(requireContext(), R.layout.list_item, R.id.textLeft, data) {


            @NonNull
            @Override
            public View getView(int position, View convertView, @NonNull ViewGroup parent) {
                if (convertView == null) {
                    LayoutInflater inflater = LayoutInflater.from(getContext());
                    convertView = inflater.inflate(R.layout.list_item, parent, false);
                }

                String[] item = data.get(position);

                TextView left = convertView.findViewById(R.id.textLeft);
                TextView center = convertView.findViewById(R.id.textCenter);

                left.setText(item[0]);
                center.setText(item[1]);

                return convertView;
            }
        };

        maListe.setAdapter(adapter);
        return view;
    }
}
