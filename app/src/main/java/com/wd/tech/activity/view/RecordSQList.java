package com.wd.tech.activity.view;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 作者：夏洪武
 * 时间：2019/2/23.
 * 邮箱：
 * 说明：
 */
public class RecordSQList extends SQLiteOpenHelper {
    private final static String DB_NAME = "temp.db";
    private final static int DB_VERSION = 1;

    public RecordSQList(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlStr = "CREATE TABLE IF NOT EXISTS records (_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT);";
        db.execSQL(sqlStr);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
