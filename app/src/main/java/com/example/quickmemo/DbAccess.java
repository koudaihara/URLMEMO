package com.example.quickmemo;

import android.content.ClipData;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;

public class DbAccess {

    /**
     * メソッド名　：　selectItemdata
     * 概要　：　updatedialogでSaveボタンを押下時に実行される
     * 　　　　　　・DBアクセスし、ItemDataを取得する
     *
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public ArrayList<String> selectItemData(DatabaseHelper helper, String table, String column, String selection, String[] selectionArgs) throws SQLException{

        //Adapter初期化
        ArrayList<String> data = new ArrayList<>();


        //データベースから登録済みのアイテムを取得し、メイン画面に表示する
        try (SQLiteDatabase db = helper.getWritableDatabase();
             //db.query(取得するテーブル名,取得するカラム名,条件,条件に設定する値)
             Cursor cs = db.query(table, new String[]{column}, selection, selectionArgs, null, null, null, null)) {

            while (cs.moveToNext()) {
                data.add(cs.getString(cs.getColumnIndex(column)));
            }
        }
        //data返却
        return data;
    }

    /**
     * メソッド名　：　selectItemAllData
     * 概要　：　ItemDataを全件取得する
     *
     */

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public ArrayList<ItemData> selectItemAllData(DatabaseHelper helper) throws SQLException{

        //Adapter初期化
        ArrayList<ItemData> itemDataArrayList = new ArrayList<>();
        String sql = "SELECT * FROM ItemData LEFT OUTER JOIN CategoryData ON ItemData.CategoryName = CategoryData.CategoryName";


        //データベースから登録済みのアイテムを取得し、メイン画面に表示する
        try (SQLiteDatabase db = helper.getWritableDatabase();

             //db.query(取得するテーブル名,取得するカラム名,条件,条件に設定する値)
             //Cursor cs = db.query("CategoryData", null, "CategoryName = ", null, null, null, null, null)) {
            Cursor cs = db.rawQuery(sql,null)){

            while (cs.moveToNext()) {
                ItemData itemData = new ItemData();
                itemData.setItemName(cs.getString(0));
                itemData.setItemUrl(cs.getString(1));
                itemData.setCategoryName(cs.getString(2));
                itemData.setCategoryColor(cs.getString(4));
                itemDataArrayList.add(itemData);
            }
        }
        return itemDataArrayList;
    }

    /**
     * メソッド名　：　updatedata
     * 概要　：　updatedialogでSaveボタンを押下時に実行される
     * 　　　　　　・DBアクセスし、ItemDataを更新する
     *
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void updateItemData(DatabaseHelper helper, String upItemName, String upItemUrl , String categoryName ,String where, String data)throws SQLException {
        try (SQLiteDatabase db = helper.getWritableDatabase()) {
            ContentValues cv = new ContentValues();
            cv.put("ItemName", upItemName);
            cv.put("ItemUrl", upItemUrl);
            cv.put("CategoryName",categoryName);
            String[] params = {data};
            //db.update(更新するテーブル,更新する情報,条件,条件に渡す値)
            db.update("ItemData", cv, where, params);

        }
    }

    /**
     * メソッド名　：　insertdata
     * 概要　：　updatedialogでSaveボタンを押下時に実行される
     * 　　　　　　・DBアクセスし、ItemDataを更新する
     *
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void insertItemData(DatabaseHelper helper, String insertItemName, String insertItemUrl,String insertCategoryName)throws SQLException {
        try (SQLiteDatabase db = helper.getWritableDatabase()) {
            ContentValues cv = new ContentValues();
            cv.put("ItemName", insertItemName);
            cv.put("ItemUrl", insertItemUrl);
            cv.put("CategoryName", insertCategoryName);
            //db.insert("books", null, cv);
            db.insertWithOnConflict("ItemData", null, cv, SQLiteDatabase.CONFLICT_NONE);

        }
    }


    /**
     * メソッド名　：　deletedata
     * 概要　：　updatedialogでSaveボタンを押下時に実行される
     * 　　　　　　・DBアクセスし、ItemDataを更新する
     *
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void deleteItemData(DatabaseHelper helper, String updateItemName) throws  SQLException{
        try (SQLiteDatabase db = helper.getWritableDatabase()) {
            String[] params = {updateItemName};
            db.delete("ItemData", "ItemName = ?", params);

        }
    }

    /**
     * メソッド名　：　selectCategoryAllData
     * 概要　：　CategoryDataを全件取得する
     *
     */

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public ArrayList<CategoryData> selectCategoryAllData(DatabaseHelper helper) throws SQLException{

        //Adapter初期化
        ArrayList<CategoryData> categoryDataArrayList = new ArrayList<>();


        //データベースから登録済みのアイテムを取得し、メイン画面に表示する
        try (SQLiteDatabase db = helper.getWritableDatabase();
             //db.query(取得するテーブル名,取得するカラム名,条件,条件に設定する値)
             Cursor cs = db.query("CategoryData", new String[]{"CategoryName","CategoryColor"}, null, null, null, null, null, null)) {

            while (cs.moveToNext()) {
                CategoryData categoryData = new CategoryData();
                categoryData.setCategoryName(cs.getString(0));
                categoryData.setCategoryColor(cs.getString(1));
                categoryDataArrayList.add(categoryData);
            }
        }
        return categoryDataArrayList;
    }

    /**
     * メソッド名　：　insertCategoryData
     * 概要　：　updatedialogでSaveボタンを押下時に実行される
     * 　　　　　　・DBアクセスし、ItemDataを更新する
     *
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void insertCategoryData(DatabaseHelper helper,String categoryName,String categoryColor) throws SQLException{
        try (SQLiteDatabase db = helper.getWritableDatabase()) {
            ContentValues cv = new ContentValues();
            cv.put("CategoryName", categoryName);
            cv.put("CategoryColor",categoryColor);
            //db.insert("books", null, cv);
            db.insertWithOnConflict("CategoryData", null, cv, SQLiteDatabase.CONFLICT_NONE);

        }
    }


    /**
     * メソッド名　：　updateCategorydata
     * 概要　：　updatedialogでSaveボタンを押下時に実行される
     * 　　　　　　・DBアクセスし、ItemDataを更新する
     *
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void updateCategoryData(DatabaseHelper helper,String updateCategoryName,String updateCategoryColor,String where,String beforeCategoryname) throws SQLException{
        try (SQLiteDatabase db = helper.getWritableDatabase()) {
            ContentValues cv = new ContentValues();
            cv.put("CategoryName", updateCategoryName);
            cv.put("CategoryColor", updateCategoryColor);
            String[] params = {beforeCategoryname};
            //db.update(更新するテーブル,更新する情報,条件,条件に渡す値)
            db.update("CategoryData", cv, where, params);

        }
    }

    /**
     * メソッド名　：　deleteCategorydata
     * 概要　：　updatedialogでSaveボタンを押下時に実行される
     * 　　　　　　・DBアクセスし、ItemDataを更新する
     *
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void deleteCategoryData(DatabaseHelper helper, String deleteCategoryName)throws SQLException {
        try (SQLiteDatabase db = helper.getWritableDatabase()) {
            String[] params = {deleteCategoryName};
            db.delete("CategoryData", "CategoryName = ?", params);

        }
    }

}
