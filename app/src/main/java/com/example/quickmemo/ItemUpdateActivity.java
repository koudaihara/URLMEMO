package com.example.quickmemo;

import android.content.Intent;
import android.database.SQLException;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.example.quickmemo.Entity.CategoryData;
import com.example.quickmemo.Entity.ItemData;
import com.example.quickmemo.Entity.ItemManegementListItem;

import java.util.ArrayList;

public class ItemUpdateActivity extends AppCompatActivity {

    ListView choicewordlist = null;
    DbAccess dbAccess = null;
    //データベースヘルパー宣言
    DatabaseHelper helper = null;

    String intentItemUrl = null;
    String[] intentAllCategoryName = null;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.itemupdate);

        dbAccess = new DbAccess();
        helper = new DatabaseHelper(this);
        choicewordlist = findViewById(R.id.choiceItemList);

        Intent subIntent = getIntent();
        intentItemUrl = subIntent.getStringExtra("intentItemUrl");
        //intentAllCategoryName = subIntent.getStringArrayExtra("intentAllCategoryName");
        //Toast.makeText(this, String.format("こんにちは、%sさん！", intentItemUrl),Toast.LENGTH_SHORT).show();
        try{
            //selectdataを呼び出し、ItemDataが全件格納されたArrayListを取得
            ArrayList<ItemData> allItemData = dbAccess.selectItemAllData(helper);
            ArrayList<ItemManegementListItem> itemManegementListItems = new ArrayList<ItemManegementListItem>();
            itemManegementListItems = new DataConverter().ItemManegementListItemConverter(allItemData);
            ItemManegementListItemAdapter itemListAdapter = new ItemManegementListItemAdapter(this,itemManegementListItems, R.layout.itemmanegementlistitem);
            //setAdapterを呼び出し、リストビューにアダプターをセットする
            choicewordlist.setAdapter(itemListAdapter);
        }catch (
                SQLException e){
            Toast.makeText(this, "An error occurred!!", Toast.LENGTH_LONG)
                    .show();
        }



        //リストビュー押下時のリスナーを登録
        choicewordlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //選択されたリストビューの文字列を取得
                TextView itemNameTextView = ((TextView) ((LinearLayout) view).findViewById(R.id.ItemName));
                String itemName = itemNameTextView.getText().toString();
                TextView categoryNameTextView = ((TextView) ((LinearLayout) view).findViewById(R.id.CategoryName));
                String categoryName = categoryNameTextView.getText().toString();
                //選択されたリストビューのitemname文字列を取得
                Bundle arg = new Bundle();
                arg.putString("beforeItemName",itemName);
                arg.putString("beforeItemUrl",intentItemUrl);
                arg.putString("beforeCategoryName",categoryName);
                ArrayList<CategoryData> categoryAllData = dbAccess.selectCategoryAllData(helper);
                ArrayList<String> spinnerCategoryAllData = new DataConverter().categoryDataIndexChange(categoryAllData,categoryName);
                String[] spinnerCategoryNameAllData = spinnerCategoryAllData.toArray(new String[0]);
                arg.putStringArray("categoryNameAllData",spinnerCategoryNameAllData);


                //ダイアログ「AddDialogFragment」を生成
                DialogFragment dialog = new ItemUpdateDialogFragment(true);
                dialog.setArguments(arg);
                //ダイアログ「「AddDialogFragment」を表示
                dialog.show(getSupportFragmentManager(), "");

            }
        });

    }



    /**
     * メソッド名　：　onClick
     * 概要　：　ADDボタン押下時に実行される
     * 　　　　　　・ADDダイアログの生成
     */
    public void onClick(View view) {
        //選択されたリストビューのitemname文字列を取得
        Bundle arg = new Bundle();
        //インテントから取得したURLをセット
        arg.putString("NewItemUrl", intentItemUrl);
        //AddDialogを生成
        DialogFragment dialog = new ItemInsertDialogFragment(true);
        dialog.setArguments(arg);
        dialog.show(getSupportFragmentManager(), "dialog_button");
    }




    public void btnBack_onClick(View v) {
        finish();
    }
}



