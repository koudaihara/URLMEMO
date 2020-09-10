package com.example.quickmemo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.SQLException;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;
import java.util.Objects;

public class ItemUpdateDialogFragment extends DialogFragment {

    /**
     * メソッド名　：　onCreateDialog
     * 概要　：　onCreateメソッド、onSaveメソッドから呼び出され実行される
     * 　　　　　　・ADDダイアログを生成する
     *
     *
     * @param savedInstanceState
     * @return
     */

    //MainActivityから呼び出されたのか、SubActivityからも呼び出されたのかを判断する為のフラグを定義
    //SubActivity⇒false  MainActivty ⇒ture
    boolean activityFlg = false;

    //アクティビティから取得したフラグを格納
    public ItemUpdateDialogFragment(boolean ActivityFlg) {

        activityFlg = ActivityFlg;


    }



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final DbAccess dbAccess = new DbAccess();
        final DatabaseHelper helper = new DatabaseHelper(getActivity());
        final Intent intent = new Intent(getContext(), ItemManegementActivity.class);

        //updatedialogのLayoutを取得
        LinearLayout layout = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.itemdialog,null);

        //updatedialogのEdittextを取得
        final EditText dialogItemName = (EditText) layout.findViewById(R.id.dialogitemname);
        final EditText diarogItemUrl = (EditText) layout.findViewById(R.id.dialogitemurl);
        final Spinner dialogCategoryName = (Spinner) layout.findViewById(R.id.dialogcategoryname);

        try{

            ArrayList<CategoryData> categoryAllData = dbAccess.selectCategoryAllData(helper);
            ArrayList<String> categoryNameAllData = new ArrayList<String>();
            for(CategoryData categoryData : categoryAllData){

                String categoryName = categoryData.getCategoryName();
                categoryNameAllData.add(categoryName);

            }
            ArrayAdapter dialogcategorySpinnerAdapter = new ArrayAdapter<String>(getActivity(), R.layout.support_simple_spinner_dropdown_item, categoryNameAllData);
            //setAdapterを呼び出し、スピナーにアダプターをセットする
            dialogCategoryName.setAdapter(dialogcategorySpinnerAdapter);
        }catch (
                SQLException e){
            Toast.makeText(getActivity(), "An error occurred!!", Toast.LENGTH_LONG)
                    .show();
        }



        //ItemmanegementActivityから選択したリストのitemNameとインテントされたURLを取得
        Bundle args = Objects.requireNonNull(getArguments());
        final String beforeItemName = args.getString("beforeItemName");
        final String beforeItemUrl = args.getString("beforeItemUrl");

        //updatedialogのEdittextに初期値を設定
        dialogItemName.setText(beforeItemName);
        diarogItemUrl.setText(beforeItemUrl);


        //builderにdialogに設定する情報をセット
        builder.setTitle("UpdateItem")
                .setView(layout)
                .setPositiveButton("Save",null)
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }
                );

                //MainActivtyから呼び出された場合は、「Deleteボタン」を表示
                if (activityFlg){

                    builder.setNeutralButton("Delete",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                    //ダイアログに入力されているItemnameを取得
                                    String deleteitemname = dialogItemName.getText().toString();
                                try {
                                    //ondeleteメソッド呼び出し
                                    dbAccess.deleteItemData(helper,deleteitemname);
                                    //ダイアログに入力されているItemnameを取得
                                    Toast.makeText(getActivity(),"アイテムを削除しました",Toast.LENGTH_SHORT).show();
                                }catch (SQLException e){
                                    Toast.makeText(getActivity(), "An error occurred!!", Toast.LENGTH_LONG)
                                            .show();
                                }
                                    //MainActivityに遷移
                                    startActivity(intent);
                                }
                            }
                    );

                }
        //AlartDialog生成
         AlertDialog dialog = builder.create();

        dialog.show();

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                //ダイアログに入力されているItemnameを取得
                String insertItemName = dialogItemName.getText().toString();
                //ダイアログに入力されているItemurlを取得
                String insertItemUrl = diarogItemUrl.getText().toString();
                if(insertItemName.length() != 0){
                    //ダイアログに入力されているItemname,ItemUrl,CategoruNameを取得
                    String updateitemname = dialogItemName.getText().toString();
                    String updateitemurl = diarogItemUrl.getText().toString();
                    String categoryName = dialogCategoryName.getSelectedItem().toString();

                    try {
                        ///onUpdateメソッド呼び出し
                        dbAccess.updateItemData(helper,updateitemname,updateitemurl,categoryName,"ItemName = ?",beforeItemName);
                        Toast.makeText(getActivity(), "Item Updated", Toast.LENGTH_SHORT)
                                .show();
                    }catch (SQLException e){
                        Toast.makeText(getActivity(), "An error occurred!!", Toast.LENGTH_LONG)
                                .show();
                    }
                    startActivity(intent);
                }else {
                    Toast.makeText(getActivity(), "Please enter ItemName!!", Toast.LENGTH_LONG)
                            .show();
                }



            }




        });

        return dialog;


    }
}
