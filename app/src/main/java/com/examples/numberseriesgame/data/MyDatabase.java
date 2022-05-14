package com.examples.numberseriesgame.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.examples.numberseriesgame.Game;

import java.util.ArrayList;

public class MyDatabase extends SQLiteOpenHelper {
    private static final String DATA_BASE_NAME = "DbGames";
    private static final int VERSION_DB = 2;
    private static final String TABLE_NAME = "Games";
    private static final String COLUMN_SCORE = "Score";
    private static final String COLUMN_FULL_NAME = "FullName";
    private static final String COLUMN_DATE = "Date";
    public static MyDatabase INSTANCE;

    public MyDatabase(@Nullable Context context) {
        super(context, DATA_BASE_NAME, null, VERSION_DB);
    }

    public static MyDatabase getINSTANCE(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new MyDatabase(context);
        }
        return INSTANCE;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME + " ( " + COLUMN_SCORE + " INTEGER, " + COLUMN_FULL_NAME + " TEXT ,"+ COLUMN_DATE + " TEXT )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME + "");
        onCreate(sqLiteDatabase);
    }

    public boolean insertGame(Game game) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_SCORE, game.getScore());
        contentValues.put(COLUMN_FULL_NAME, game.getFullName());
        contentValues.put(COLUMN_DATE, game.getGameDate());
        long result = database.insert(TABLE_NAME, null, contentValues);
        return result != -1;
    }

    public ArrayList<Game> getAllGames() {
        ArrayList<Game> listGames = new ArrayList<>();
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            int indexScore = cursor.getColumnIndex(COLUMN_SCORE);
            int indexFullName = cursor.getColumnIndex(COLUMN_FULL_NAME);
            int indexDate = cursor.getColumnIndex(COLUMN_DATE);
            do {
                int score = cursor.getInt(indexScore);
                String fullName = cursor.getString(indexFullName);
                String date = cursor.getString(indexDate);
                listGames.add(new Game(score, fullName, date));
            } while (cursor.moveToNext());
        }
        return listGames;
    }

    public boolean deleteAllGAmes() {
        SQLiteDatabase database = getWritableDatabase();
        int result = database.delete(TABLE_NAME, null, null);
        return result > 0;
    }
}
