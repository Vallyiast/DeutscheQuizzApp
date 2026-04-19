package com.example.deutschquiz.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.deutschquiz.utils.CommonUses;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class WikDictionary extends SQLiteOpenHelper {

    private static final String dictionaryName = "de-"+ CommonUses.translationLanguage;
    private static final String DB_NAME = dictionaryName+".db";

    private final Context context;
    private final SQLiteDatabase dbase;

    public WikDictionary(Context context) {
        super(context, DB_NAME, null, 1);
        this.context = context;
        dbase = this.getReadableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        executeSqlFile(db, "databases/"+dictionaryName+".sql");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}

    private void executeSqlFile(SQLiteDatabase db, String assetFileName) {
        try {
            InputStream is = context.getAssets().open(assetFileName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder statement = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                statement.append(line);
                if (line.endsWith(";")) {
                    db.execSQL(statement.toString());
                    statement.setLength(0);
                }
            }
            reader.close();
        } catch (Exception e) {
            Log.d(this.getClass().getName(),e.getMessage());
        }
    }

    public List<String> getTranslations(String word) {
        String TABLE_NAME = "simple_translation";
        Cursor cursor = dbase.query(
                TABLE_NAME,
                new String[]{"trans_list"},
                "written_rep = ?",
                new String[]{word},
                null,  null, null, "1"
        );

        if (!cursor.moveToFirst()) {
            cursor.close();
            return Collections.emptyList();
        }
        String translations = cursor.getString(0);
        cursor.close();

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

