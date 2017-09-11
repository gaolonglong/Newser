package com.gaolonglong.app.newser.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Admin on 2017/4/23.
 */

public class ZhihuDbHelper extends SQLiteOpenHelper {
    private Context context;
    private static final String NEWS_TABLE = "create table News("
            + "id integer primary key autoincrement,"
            + "news_id integer,"
            + "img_url text,"
            + "title text)";

    public ZhihuDbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(NEWS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
