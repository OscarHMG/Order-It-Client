package com.oscarhmg.orderit.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * Created by OscarHMG on 09/11/2017.
 */

public class DataBase extends SQLiteAssetHelper {
    private final static String DB_NAME ="OrderIt-DB.db";
    private final static int DB_VERSION = 1;

    public DataBase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }
}
