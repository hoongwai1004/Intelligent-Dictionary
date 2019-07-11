package com.cat300.ty_ch.intelligentdictionary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FavoriteDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME  = "favorite.db";
    private static final String TABLE_NAME = "favorite";
    private static final String COL_1 = "ID";
    private static final String COL_2 = "SEARCHWORD";

    public FavoriteDBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, SEARCHWORD TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }

    public boolean insertData(String data){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, data);
        long result = sqLiteDatabase.insert(TABLE_NAME, null,contentValues);
        sqLiteDatabase.close();

        if (result == -1)
            return false;
        else
            return true;
    }

    public Integer deleteData(String data){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        int i = sqLiteDatabase.delete(TABLE_NAME, "SEARCHWORD=?", new String[]{data});
        return i;
    }

    public Cursor getAllData(){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor res = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return res;
    }
}
