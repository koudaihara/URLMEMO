package com.example.quickmemo;

import android.content.Intent;
import android.database.SQLException;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;

public class ItemUpdateActivity extends AppCompatActivity {

    ListView choicewordlist = null;
    DbAccess dbAccess = null;
    //データベースヘルパー宣言
    DatabaseHelper helper = null;

    String intentItemUrl = null;

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
        //Toast.makeText(this, String.format("こんにちは、%sさん！", intentItemUrl),Toast.LENGTH_SHORT).show();
        try{
            //onselectdataを呼び出し、itemNameが全権格納されたArrayListを取得
            ArrayList<String> itemNameAllData = dbAccess.selectItemData(helper,"ItemData","ItemName",null,null);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice, itemNameAllData);
            //setAdapterを呼び出し、リストビューにアダプターをセットする
            choicewordlist.setAdapter(adapter);
        }catch (
        SQLException e){
            Toast.makeText(this, "An error occurred!!", Toast.LENGTH_LONG)
                    .show();
        }



        //リストビュー押下時のリスナーを登録
        choicewordlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //選択されたリストビューのitemname文字列を取得
                Bundle arg = new Bundle();
                arg.putString("beforeItemName",(String)((TextView) view).getText());
                arg.putString("beforeItemUrl", intentItemUrl);
                //ダイアログ「UpdateDialogFragment」を生成
                DialogFragment dialog = new ItemUpdateDialogFragment(false);
                dialog.setArguments(arg);
                //ダイアログ「「UpdateDialogFragment」を表示
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



