package com.example.quickmemo;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.database.SQLException;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class CategoryManegementActivity extends AppCompatActivity {

    /**
     *
     * フィールド変数宣言
     *
     */
    DatabaseHelper helper = null;
    DbAccess dbAccess = null;
    String categoryname = "CategoryName";
    String categoryColor = "CategoryColor";
    ListView categoryList = null;
    Intent itemManegementIntent = null;


    /**
     * メソッド名　：　onCreate
     * 概要　：　MainActivity生成時に実行される
     * 　　　　　　・ListViewの生成
     * 　　　　　　　・リスナー定義
     * 　　　　　　・ADDダイアログの生成
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.categorymanegement);


        /**
         * 変数の初期化
         */
        dbAccess = new DbAccess();
        categoryList = findViewById(R.id.categoryList);
        helper = new DatabaseHelper(this);
        CategoryManegementListItemAdapter categoryManegementListItemAdapter = null;
        ArrayList<CategoryData> categoryDataArrayList= null;

        //DB削除
        //deleteDatabase("sample.sqlite");




        /**
         *  リストビューに長押し時の編集イベントを追加
         */

        categoryList.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    public boolean onItemLongClick(AdapterView<?> av,
                                                   View view, int position, long id) {

                        //選択されたリストビューのCategoryName文字列を取得
                        Bundle arg = new Bundle();
                        TextView categoryNameTextView = (view.findViewById(R.id.CategoryName));
                        String beforeCategoryName = categoryNameTextView.getText().toString();
                        arg.putString("beforeCategoryName",beforeCategoryName);
                        arg.putString("beforeCategoryColor",dbAccess.selectData(helper,"CategoryData", categoryColor,"CategoryName = ?",new String[]{beforeCategoryName}).get(0));
                        //ダイアログ「CategoryInsertDialogFragment」を表示
                        DialogFragment dialog = new CategoryUpdateDialogFragment();
                        dialog.setArguments(arg);
                        dialog.show(getSupportFragmentManager(), "");
                        return true;
                    }
                }
        );

        try{
            //selectdataを呼び出し、Categorydata全件格納されたArrayListを取得
            categoryDataArrayList = dbAccess.selectCategoryAllData(helper);

            //Adapterに渡すCategotuManegementListItemのリストを作成
            ArrayList<CategoryManegementListItem> categoryManegementListItemList = new ArrayList<CategoryManegementListItem>();
            for(int i = 0 ; i < categoryDataArrayList.size() ; i++){

                CategoryManegementListItem categoryManegementListItem = new CategoryManegementListItem();
                categoryManegementListItem.setId((new Random()).nextLong());
                categoryManegementListItem.setCategoryName(categoryDataArrayList.get(i).getCategoryName());
                categoryManegementListItem.setCategoryColor(categoryDataArrayList.get(i).getCategoryColor());
                categoryManegementListItemList.add(categoryManegementListItem);
            }


            categoryManegementListItemAdapter = new CategoryManegementListItemAdapter(this, categoryManegementListItemList, R.layout.categorymanegementlistitem);
            //setAdapterを呼び出し、リストビューにアダプターをセットする
            categoryList.setAdapter(categoryManegementListItemAdapter);
        }catch (
                SQLException e){
            Toast.makeText(this, "An error occurred!!", Toast.LENGTH_LONG)
                    .show();
        }
    }

    /**
     * メソッド名　：　onClick
     * 概要　：　ADDボタン押下時に実行される
     * 　　　　　　・ADDダイアログの生成
     */
    public void onClick(View view) {

        DialogFragment dialog = new CategoryInsertDialogFragment();
        dialog.show(getSupportFragmentManager(), "dialog_button");

    }

    //メニュー定義ファイルをもとにオプションメニューを表示
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.option_menu_category,menu);
        return true;
    }
    //メニューを選択時の制御
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.categorybutun:
                itemManegementIntent = new Intent(this, ItemManegementActivity.class);
                startActivity(itemManegementIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * キーボードの戻るボタン押下時の処理制御
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onBackPressed(){

    }


    /**
     * メソッド名　：　serchClear
     * 概要　：　戻るボタン押下時に実行される
     * 　　　　　　・全CategoryをCategoryListに表示する
     *        ・検索テキストボックスに入力されている文字列を削除する
     */

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void serchClear(){
        //ワイルドカードでItemName全件をDBから取得し、表示する
        String searchItemName = "%";
        ArrayList<String> searchItemData = dbAccess.selectData(helper,"CategoryData",categoryname,"CategoryName like ?",new String[]{searchItemName});
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, searchItemData);
        categoryList.setAdapter(adapter);
    }

    /**
     * メソッド名　：　moveItemManegementActivity
     * 概要　：　Categoryボタン押下時に実行される
     * 　　　　　　・Category画面に遷移する
     *
     */
    public void moveItemManegementActivity(View view){
        //ItemManegementActivityに遷移する
        itemManegementIntent = new Intent(this,ItemManegementActivity.class);
        startActivity(itemManegementIntent);
        itemManegementIntent = null;
    }




}

