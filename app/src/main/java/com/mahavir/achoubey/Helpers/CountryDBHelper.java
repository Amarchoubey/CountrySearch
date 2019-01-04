package com.mahavir.achoubey.Helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CountryDBHelper extends SQLiteOpenHelper {

    //https://github.com/obaro/android-sqlite-sample/blob/master/SqliteExample/app/src/main/java/com/sample/foo/sqliteexample/ExampleDBHelper.java
    public static final String DATABASE_NAME = "ShellCountryDetail.db";
    private static final int DATABASE_VERSION = 1;

    //Table Name
    public static final String COUNTRY_TABLE_NAME = "country";

    public static final String COUNTRY_NAME = "name";
    public static final String COUNTRY_FLAG = "flag";
    public static final String COUNTRY_CAPITAL = "capital";
    public static final String COUNTRY_REGION = "region";
    public static final String COUNTRY_SUB_REGION = "subregion";
    public static final String COUNTRY_CALLING_CODE = "callingcode";
    public static final String COUNTRY_TIME_ZONE = "timezone";
    public static final String COUNTRY_CURRENCY = "currency";
    public static final String COUNTRY_LANGUAGE = "language";

    public CountryDBHelper(Context context) {
        super(context, DATABASE_NAME , null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE " + COUNTRY_TABLE_NAME +
                        "(" + COUNTRY_NAME + " TEXT PRIMARY KEY, " +
                        COUNTRY_FLAG + " TEXT, " +
                        COUNTRY_CAPITAL + " TEXT, " +
                        COUNTRY_REGION + " TEXT, " +
                        COUNTRY_SUB_REGION + " TEXT, " +
                        COUNTRY_CALLING_CODE + " TEXT, " +
                        COUNTRY_TIME_ZONE + " TEXT, " +
                        COUNTRY_CURRENCY + " TEXT, " +
                        COUNTRY_LANGUAGE + " TEXT)"
        );
    }

    public boolean insertCountry(String name, String flag, String capital, String region, String subregion,
                                 String callingcode, String timezone, String currency, String language) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COUNTRY_NAME, name);
        contentValues.put(COUNTRY_FLAG, flag);
        contentValues.put(COUNTRY_CAPITAL, capital);
        contentValues.put(COUNTRY_REGION, region);
        contentValues.put(COUNTRY_SUB_REGION, subregion);
        contentValues.put(COUNTRY_CALLING_CODE, callingcode);
        contentValues.put(COUNTRY_TIME_ZONE, timezone);
        contentValues.put(COUNTRY_CURRENCY, currency);
        contentValues.put(COUNTRY_LANGUAGE, language);

        db.insert(COUNTRY_TABLE_NAME, null, contentValues);
        return true;
    }

    public Cursor getCountry(String searchWord) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "Select * from country where name like " + "'%" + searchWord + "%'";
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + COUNTRY_TABLE_NAME);
        onCreate(db);
    }
}
