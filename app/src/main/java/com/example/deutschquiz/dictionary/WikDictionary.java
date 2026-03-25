package com.example.deutschquiz.dictionary;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class WikDictionary extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "de-en.sqlite3";

    public WikDictionary(Context context) {
        super(context, DATABASE_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {}

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}



    public List<String> getTranslations(String word) {
        SQLiteDatabase dbase = this.getReadableDatabase();

        Log.d(this.getClass().getName(),"Database word: " +word);

        String TABLE_NAME = "simple_translation";
        Cursor cursor = dbase.query(
                TABLE_NAME,
                new String[]{"trans_list"},
                "written_rep = ?",
                new String[]{word},
                null,  null, null, "1"
        );

        if (!cursor.moveToFirst()) {
            return Collections.emptyList();
        }
        String translations = cursor.getString(0);
        cursor.close();

        Log.d(this.getClass().getName(),"Database queried for "+word+" : " +translations);

        return parseTranslations(translations);
    }

    private List<String> parseTranslations(String raw) {
        String[] parts = raw.split("\\|");
        List<String> result = new ArrayList<>();

        for (String p : parts) {
            String clean = p.trim();
            if (!clean.isEmpty()) {
                result.add(clean);
            }
        }

        return result;
    }

}

