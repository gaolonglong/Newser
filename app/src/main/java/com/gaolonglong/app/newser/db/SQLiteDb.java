package com.gaolonglong.app.newser.db;

import android.database.sqlite.SQLiteDatabase;

import com.gaolonglong.app.newser.NewserApp;

/**
 * Created by Admin on 2017/4/23.
 */

public class SQLiteDb {
    private static ZhihuDbHelper dbHelper = new ZhihuDbHelper(NewserApp.instance,"Newss.db",null,1);
    private static SQLiteDatabase sqLiteDb;

    public static SQLiteDatabase getInstance(){
        //初始化收藏数据库
        if (sqLiteDb == null){
            sqLiteDb = dbHelper.getWritableDatabase();
        }
        return sqLiteDb;
    }
}
