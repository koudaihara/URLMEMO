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

import com.example.quickmemo.Entity.ItemData;
import com.example.quickmemo.Entity.ItemManegementListItem;

import java.util.ArrayList;

public class ItemShareActivity extends AppCompatActivity {

    ListView choicewordlist = null;
    DbAccess dbAccess = null;
    //データベースヘルパー宣言
    DatabaseHelper helper = null;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.itemshere);

        dbAccess = new DbAccess();
        helper = new DatabaseHelper(this);
        final Intent shareintent = new Intent();
        choicewordlist = findViewById(R.id.choiceItemList);

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
                TextView itemUrlTextView = ((TextView) ((LinearLayout) view).findViewById(R.id.ItemUrl));
                String itemUrl = itemUrlTextView.getText().toString();

                if(itemUrl.equals("")){

                    Toast.makeText(ItemShareActivity.this, "URLが存在しません", Toast.LENGTH_LONG).show();

                }else {
                    shareintent.setAction(Intent.ACTION_SEND);
                    shareintent.setType("text/plain");
                    shareintent.putExtra(Intent.EXTRA_TEXT, itemUrl);
                    startActivity(shareintent);
                }
            }
        });

    }




    public void btnBack_onClick(View v) {
        finish();
    }
}



