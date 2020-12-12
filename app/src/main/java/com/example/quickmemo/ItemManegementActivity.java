package com.example.quickmemo;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quickmemo.Entity.CategoryData;
import com.example.quickmemo.Entity.ItemData;
import com.example.quickmemo.Entity.ItemManegementListItem;

import java.util.ArrayList;

public class ItemManegementActivity extends AppCompatActivity {

    /**
     *
     * フィールド変数宣言
     *
     */
    DatabaseHelper helper = null;
    DataConverter dataConverter = null;
    DbAccess dbAccess = null;
    String itemName = "ItemName";
    String itemUrl = "ItemUrl";
    String categoryName = "CategoryName";
    ListView itemList = null;
    Spinner serchCategoryNameSpinner = null;
    EditText serchItemNameEditText = null;
    Spinner daialogCategoryNameSpinner = null;
    Intent sentIntent = null;
    Intent getIntent = null;
    Intent updateIntent = null;
    Intent categoryManegement = null;
    Intent itemShare = null;
    ArrayList<CategoryData> categoryAllData = null;
    ArrayList<String> categoryNameAllData = null;


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
        setContentView(R.layout.itemmanegement);
        //DB削除
        //deleteDatabase("sample.sqlite");

        /**
         *
         * 暗黙的インテントによって呼び出された場合は、ItemUpdateActivtyに遷移
         *
         */

        getIntent = getIntent();
        if (getIntent.getStringExtra(Intent.EXTRA_TEXT) != null){

            String intentItemUrl = getIntent.getStringExtra(Intent.EXTRA_TEXT);
            updateIntent = new Intent(this, ItemUpdateActivity.class);
            updateIntent.putExtra("intentItemUrl",intentItemUrl);
            //updateIntent.putExtra("intentAllCategoryName",categoryNameAllData);
            //updateIntent.putExtra("intentItemList",intentItemList);
            startActivity(updateIntent);

        }

        /**
         * 変数の初期化
         */
        dbAccess = new DbAccess();
        itemList = findViewById(R.id.itemList);
        serchCategoryNameSpinner = findViewById(R.id.searchcategoryname);
        serchItemNameEditText = findViewById(R.id.searchitemname);
        daialogCategoryNameSpinner = findViewById(R.id.dialogcategoryname);
        helper = new DatabaseHelper(this);
        ItemManegementListItemAdapter itemListAdapter = null;
        ArrayAdapter<String> categorySpinnerAdapter = null;
        categoryAllData = new ArrayList<CategoryData>();
        categoryNameAllData = new ArrayList<String>();


        /**
         * DBに値がない場合カテゴリーにCategoryName""CategoryColor0を追加
         */
        try{
            //selectdataを呼び出し、  CategoryNameが全件格納されたArrayListを取得
            ArrayList<String> categoryNameAllData = dbAccess.selectData(helper,"CategoryData",categoryName,null,null);
            if(categoryNameAllData.size()==0){

                dbAccess.insertDelaultCategoryData(helper);

            }

        }catch (SQLException e){
            Toast.makeText(this, "An error occurred!!", Toast.LENGTH_LONG).show();
        }


        /**
         * アイテム表示用ListViewにアダプターをセットする
         */
        try{
            //selectdataを呼び出し、ItemDataが全件格納されたArrayListを取得
            ArrayList<ItemData> allItemData = dbAccess.selectItemAllData(helper);
            ArrayList<ItemManegementListItem> itemManegementListItems = new ArrayList<ItemManegementListItem>();
            itemManegementListItems = new DataConverter().ItemManegementListItemConverter(allItemData);
            itemListAdapter = new ItemManegementListItemAdapter(this,itemManegementListItems, R.layout.itemmanegementlistitem);
            //setAdapterを呼び出し、リストビューにアダプターをセットする
            itemList.setAdapter(itemListAdapter);
        }catch (
                SQLException e){
            Toast.makeText(this, "An error occurred!!", Toast.LENGTH_LONG)
                    .show();
        }

        /**
         * 検索用Spinnerにアダプターをセットする
         */
        try{
            //selectdataを呼び出し、  CategoryNameが全件格納されたArrayListを取得
            categoryAllData = dbAccess.selectCategoryAllData(helper);
            for(CategoryData categoryData : categoryAllData){

                String categoryName = categoryData.getCategoryName();
                categoryNameAllData.add(categoryName);

            }
            categoryNameAllData.add(0,"全カテゴリ");
            categorySpinnerAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, categoryNameAllData);
            //setAdapterを呼び出し、スピナーにアダプターをセットする
            serchCategoryNameSpinner.setAdapter(categorySpinnerAdapter);
        }catch (SQLException e){
            Toast.makeText(this, "An error occurred!!", Toast.LENGTH_LONG).show();
        }


        /**
         *  アイテム表示用リストビューに押下の検索イベントを追加
         */
        //itemList.setScrollingCacheEnabled(false);

        itemList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String serchItemUrl = null;
                TextView itemNameTextView = ((TextView) ((LinearLayout) view).findViewById(R.id.ItemName));
                String itemName = itemNameTextView.getText().toString();
                ArrayList<String> itemUrlData = dbAccess.selectData(helper,"ItemData",itemUrl,"ItemName = ?",new String[]{itemName});
                //DBから取得したitemUrlが空文字か判断する
                if(itemUrlData.get(0).equals("")){

                    serchItemUrl = itemName;

                }else{

                    serchItemUrl = itemUrlData.get(0);

                }

                //暗黙的インテントで、検索を実行
                sentIntent = new Intent(Intent.ACTION_WEB_SEARCH);
                sentIntent.putExtra(SearchManager.QUERY, serchItemUrl);
                startActivity(sentIntent);
                sentIntent = null;
            }
        });

        /**
         *  リストビューに長押し時の編集イベントを追加
         */

        itemList.setOnItemLongClickListener(
            new AdapterView.OnItemLongClickListener() {
                public boolean onItemLongClick(AdapterView<?> av,
                                               View view, int position, long id) {
                    //選択されたリストビューの文字列を取得
                    TextView itemNameTextView = ((TextView) ((LinearLayout) view).findViewById(R.id.ItemName));
                    String itemName = itemNameTextView.getText().toString();
                    TextView itemUrlTextView = ((TextView) ((LinearLayout) view).findViewById(R.id.ItemUrl));
                    String itemUrl = itemUrlTextView.getText().toString();
                    TextView categoryNameTextView = ((TextView) ((LinearLayout) view).findViewById(R.id.CategoryName));
                    String categoryName = categoryNameTextView.getText().toString();
                    //選択されたリストビューのitemname文字列を取得
                    Bundle arg = new Bundle();
                    arg.putString("beforeItemName",itemName);
                    arg.putString("beforeItemUrl",itemUrl);
                    arg.putString("beforeCategoryName",categoryName);
                    ArrayList<String> spinnerCategoryAllData = new DataConverter().categoryDataIndexChange(categoryAllData,categoryName);
                    String[] spinnerCategoryNameAllData = spinnerCategoryAllData.toArray(new String[0]);
                    arg.putStringArray("categoryNameAllData",spinnerCategoryNameAllData);
                    //選択されたリストビューのカテゴリーを取得
                    //ダイアログのスピナーを設定


                    //ダイアログ「AddDialogFragment」を生成
                    DialogFragment dialog = new ItemUpdateDialogFragment(true);
                    dialog.setArguments(arg);
                    //ダイアログ「「AddDialogFragment」を表示
                    dialog.show(getSupportFragmentManager(), "");
                    return true;
                }
            }
        );

        // 検索用カテゴリースピナーにリスナーを設定
        serchCategoryNameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            //　アイテムが選択された時
            @Override
            public void onItemSelected(AdapterView<?> parent,
                                       View view, int position, long id) {
 /*               if(serchCategoryNameSpinner.isFocusable() == false){
                    serchCategoryNameSpinner.setFocusable(true);
                }else {
                    onSeach();
                }*/
                onSeach(serchCategoryNameSpinner);

            }

            //　アイテムが選択されなかった
            public void onNothingSelected(AdapterView<?> parent) {
                //
            }
        });
        //serchCategoryNameSpinner.setFocusable(false);

        /**
         *検索用EditTextにイベントを設定
         * キーボードのエンターキーで検索実行
         */
        //キーボード表示を制御するためのオブジェクト
        final InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        serchItemNameEditText.setOnKeyListener(new View.OnKeyListener() {
            //コールバックとしてonKey()メソッドを定義
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //イベントを取得するタイミングには、ボタンが押されてなおかつエンターキーだったときを指定
                if((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)){

                    onSeach(serchItemNameEditText);
                    //キーボードを閉じる
                    inputMethodManager.hideSoftInputFromWindow(serchItemNameEditText.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);

                    return true;
                }
                return false;
            }
        });
    }

    /**
     * キーボードの戻るボタン押下時の処理制御
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onBackPressed(){
        this.serchClear(serchItemNameEditText);
    }

    //メニュー定義ファイルをもとにオプションメニューを表示
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.option_menu,menu);
        return true;
    }

    /**
     *画面遷移ボタン制御
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.sherebutun:
                itemShare = new Intent(this, ItemShareActivity.class);
                startActivity(itemShare);
                break;
            default:
                break;
        }

                return super.onOptionsItemSelected(item);
    }

    /**
     * メソッド名　：　onClick
     * 概要　：　ADDボタン押下時に実行される
     * 　　　　　　・ADDダイアログの生成
     */
    public void onClick(View view) {

        DialogFragment dialog = new ItemInsertDialogFragment(false);
        dialog.show(getSupportFragmentManager(), "dialog_button");

    }

    /**
     * メソッド名　：　onSeach
     * 概要　：　検索ボタン押下時に実行される
     * 　　　　　　・検索条件に合致したItemをItemListに表示する
     */

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void onSeach(View view){

        EditText searchItemNameEdit = findViewById(R.id.searchitemname);
        Spinner searchCategorySpinner = findViewById(R.id.searchcategoryname);
        String searchItemName = "%" + searchItemNameEdit.getText().toString() + "%";
        String searchCategoryName = searchCategorySpinner.getSelectedItem().toString();
        ArrayList<ItemData> searchItemDatas = new ArrayList<ItemData>();
        if(searchCategoryName.equals("全カテゴリ")){

            if(searchItemName.equals("%%")) {
                //カテゴリーが空白、アイテムネームが空白の場合
                searchItemDatas = dbAccess.selectItemAllData(helper);

            }else {
                //カテゴリーが空白、アイテムネームにテキストがある場合
                searchItemDatas = dbAccess.selectItemData(helper,"%%",searchItemName);

            }

        }else {
                //カテゴリーが選択されている場合
                searchItemDatas = dbAccess.selectItemData(helper,searchCategoryName,searchItemName);

        }

        ArrayList<ItemManegementListItem> serchItemManegementListItems = new DataConverter().ItemManegementListItemConverter(searchItemDatas);
        ItemManegementListItemAdapter serchItemListAdapter = new ItemManegementListItemAdapter(this,serchItemManegementListItems, R.layout.itemmanegementlistitem);
        //setAdapterを呼び出し、リストビューにアダプターをセットする
        itemList.setAdapter(serchItemListAdapter);

    }


    /**
     * メソッド名　：　serchClear
     * 概要　：　戻るボタン押下時に実行される
     * 　　　　　　・全ItemをItemListに表示する
     *        ・検索テキストボックスに入力されている文字列を削除する
     */

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void serchClear(View view){
        //検索テキストボックスの入力値を削除する
        serchItemNameEditText.getEditableText().clear();
        serchCategoryNameSpinner.setSelection(0);
        ArrayList<ItemData> itemNameAllData = dbAccess.selectItemAllData(helper);
        ArrayList<ItemManegementListItem> itemManegementListItems = new ArrayList<ItemManegementListItem>();
        itemManegementListItems = new DataConverter().ItemManegementListItemConverter(itemNameAllData);
        ItemManegementListItemAdapter itemListAdapter = new ItemManegementListItemAdapter(this,itemManegementListItems, R.layout.itemmanegementlistitem);
        itemList.setAdapter(itemListAdapter);

    }
    /**
     * メソッド名　：　moveCategory
     * 概要　：　Categoryボタン押下時に実行される
     * 　　　　　　・Category画面に遷移する
     *
     */
    public void moveCategoryManegementActivity(View view){
        //CategoryManegementActivityに遷移する
        //暗黙的インテントで、検索を実行
        itemShare = new Intent(this, CategoryManegementActivity.class);
        startActivity(itemShare);
        itemShare = null;
    }
}

