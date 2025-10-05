package com.example.deutschquiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.util.Log;

public class ScoreDataBase extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "scores.db";
    private static final int DATABASE_VERSION = 2;
    private final String actual_type;
    private final String TABLE_NAME;


    private final SQLiteDatabase dbase;

    public ScoreDataBase(Context context, String actual_type) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.actual_type = actual_type;
        TABLE_NAME = "score_"+actual_type;
        dbase = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        for (String type:MainActivity.types) {
            try {
                String CREATE_TABLE = "CREATE TABLE " + "score_" + type + " (" + type + " TEXT PRIMARY KEY, " + "score" + " INTEGER DEFAULT 0)";
                db.execSQL(CREATE_TABLE);
            } catch (SQLiteException e) {
                Log.d("DataBase",e.toString());
            }
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        for (String type:MainActivity.types) {
            try {
                String CREATE_TABLE = "CREATE TABLE " + "score_" + type + " (" + type + " TEXT PRIMARY KEY, " + "score" + " INTEGER DEFAULT 0)";
                db.execSQL(CREATE_TABLE);
            } catch (SQLiteException e) {
                Log.d("DataBase",e.toString());
            }
        }
    }


    public void saveScore(String mot, int score) {
        ContentValues values = new ContentValues();
        values.put(actual_type,mot);
        values.put("score",score);
        dbase.insertWithOnConflict(TABLE_NAME,null,values,SQLiteDatabase.CONFLICT_REPLACE);
    }

    public int getScore(String mot) {
        Cursor cursor = dbase.query(
                TABLE_NAME,
                new String[]{"score"},
                actual_type + "=?",
                new String[]{mot},
                null,  null, null
                );
        int score = 0;
        if (cursor.moveToFirst()) {score = cursor.getInt(0);}
        cursor.close();
        return score;
    }

}
