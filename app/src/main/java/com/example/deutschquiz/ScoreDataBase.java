package com.example.deutschquiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;

import androidx.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;

public class ScoreDataBase extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "scores.db";
    private static final int DATABASE_VERSION = 1;
    private final String type;
    private final String TABLE_NAME;


    private final SQLiteDatabase dbase;

    public ScoreDataBase(Context context, String type) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.type = type;
        TABLE_NAME = "score_"+type;
        dbase = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" + type + " TEXT PRIMARY KEY, " + "score" + " INTEGER DEFAULT 0)";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }


    public void saveScore(String mot, int score) {
        ContentValues values = new ContentValues();
        values.put(type,mot);
        values.put("score",score);
        dbase.insertWithOnConflict(TABLE_NAME,null,values,SQLiteDatabase.CONFLICT_REPLACE);
    }

    public int getScore(String mot) {
        Cursor cursor = dbase.query(
                TABLE_NAME,
                new String[]{"score"},
                type + "=?",
                new String[]{mot},
                null,  null, null
                );
        int score = 0;
        if (cursor.moveToFirst()) {score = cursor.getInt(0);}
        cursor.close();
        return score;
    }

    public Map<String,Integer> getAllScores() {
        Map<String,Integer> scores = new HashMap<>();
        Cursor cursor = dbase.query(TABLE_NAME,null,null,null,null,null,null);
        while (cursor.moveToNext()) {
            String mot = cursor.getString(cursor.getColumnIndexOrThrow(type));
            int score = cursor.getInt(cursor.getColumnIndexOrThrow("score"));
            scores.put(mot,score);
        }
        cursor.close();
        return scores;
    }
}
