package com.example.deutschquiz.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.util.Log;

public class ScoreDataBase extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "scores.db";
    private static final int DATABASE_VERSION = 3;
    private final String TABLE_NAME;
    private final SQLiteDatabase dbase;

    public ScoreDataBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        TABLE_NAME = "scores";
        dbase = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (word TEXT PRIMARY KEY, score INTEGER DEFAULT 0)";
            db.execSQL(CREATE_TABLE);
        } catch (SQLiteException e) {
            Log.d("DataBase",e.toString());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {
                String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (word TEXT PRIMARY KEY, score INTEGER DEFAULT 0)";
                db.execSQL(CREATE_TABLE);
            } catch (SQLiteException e) {
                Log.d("DataBase",e.toString());
            }
    }

    public void saveScore(String word, int score) {
        ContentValues values = new ContentValues();
        values.put("word",word);
        values.put("score",score);
        dbase.insertWithOnConflict(TABLE_NAME,null,values,SQLiteDatabase.CONFLICT_REPLACE);
    }

    public void changeScore(String mot, int scoreChange) {
        dbase.execSQL(
                "INSERT INTO "+TABLE_NAME+" (word, score) VALUES (?, ?) " +
                        "ON CONFLICT(word) DO UPDATE SET score = score + ?",
                new Object[]{mot, scoreChange, scoreChange}
        );
    }

    public int getScore(String word) {
        Cursor cursor = dbase.query(
                TABLE_NAME,
                new String[]{"score"},
                "word=?", new String[]{word},
                null,  null, null
                );
        int score = 0;
        if (cursor.moveToFirst()) {score = cursor.getInt(0);}
        cursor.close();
        return score;
    }

}
