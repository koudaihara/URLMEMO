package com.example.quickmemo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.os.Build;


import java.util.SplittableRandom;

public class DatabaseHelper extends SQLiteOpenHelper {

    static final private String DBNAME = "sample.sqlite";
    static final private int VERSION = 1;

    //コンストラクター
    public DatabaseHelper(Context context){
        super(context,DBNAME,null, VERSION);
    }

    public void onOpen(SQLiteDatabase db){
        super.onOpen(db);
    }


    @SuppressLint("SQLiteString")
    @Override
    public void onCreate(SQLiteDatabase db) {

        //DB作成
    //db.execSQL("CREATE TABLE ItemData (" + "ItemName TEXT PRIMARY KEY, ItemUrl TEXT,FOREIGN KEY (CategoryName)REFERENCES CategoryData(CategoryName))");
        db.execSQL("CREATE TABLE ItemData (" + "ItemName TEXT PRIMARY KEY, ItemUrl TEXT , CategoryName TEXT)");
        db.execSQL("CREATE TABLE CategoryData (" + "CategoryName TEXT PRIMARY KEY , CategoryColor TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS asmstime");
        onCreate(db);
    }
}
