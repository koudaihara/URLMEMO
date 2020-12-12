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

import com.example.quickmemo.Entity.CategoryData;

import java.util.ArrayList;
import java.util.Objects;

public class ItemInsertDialogFragment extends DialogFragment {

    //MainActivityから呼び出されたのか、SubActivityからも呼び出されたのかを判断する為のフラグを定義
    //SubActivity⇒ture  MainActivty ⇒false
    boolean activityFlg = false;

    //アクティビティから取得したフラグを格納
    public ItemInsertDialogFragment(boolean ActivityFlg) {

        activityFlg = ActivityFlg;


    }

    /**
     * メソッド名　：　onCreateDialog
     * 概要　：　onCreateメソッド、onSaveメソッドから呼び出され実行される
     * 　　　　　　・ADDダイアログを生成する
     *
     *
     * @param savedInstanceState
     * @return
     */



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final EditText NewItemName = new EditText(getActivity());
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

        //ItemUpdateActivityから呼び出された場合は、共有されたUrlをダイアログに設定
        if(activityFlg) {

            //SubActivityから選択したリストのitemNameとインテントされたURLを取得
            Bundle args = Objects.requireNonNull(getArguments());
            final String newItemUrl = args.getString("NewItemUrl");

            //updatedialogのEdittextに初期値を設定
            diarogItemUrl.setText(newItemUrl);
        }

        try{
            ArrayList<CategoryData> categoryAllData = dbAccess.selectCategoryAllData(helper);
            ArrayList<String> categoryNameAllData = new ArrayList<String>();
            for(CategoryData categoryData : categoryAllData) {

                String categoryName = categoryData.getCategoryName();
                categoryNameAllData.add(categoryName);
            }
            ArrayAdapter dialogcategorySpinnerAdapter = new ArrayAdapter<String>(getActivity(), R.layout.support_simple_spinner_dropdown_item, categoryNameAllData);
            //setAdapterを呼び出し、スピナーにアダプターをセットする
            dialogCategoryName.setAdapter(dialogcategorySpinnerAdapter);
        }catch (
                SQLException e){
            Toast.makeText(getActivity(), "An error occurred!!", Toast.LENGTH_LONG).show();
        }



        //builderにdialogに設定する情報をセット
        builder.setTitle("NewItem")
                .setView(layout)
                .setPositiveButton("Save",null)
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) { }
                        }
                );
        //AlartDialog生成
        AlertDialog dialog = builder.create();

        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                        //ダイアログに入力されているItemname、ItemUrl、CategoryNameを取得
                        String insertItemName = dialogItemName.getText().toString();
                        String insertItemUrl = diarogItemUrl.getText().toString();
                        String insertCatagoryName = dialogCategoryName.getSelectedItem().toString();
                        if(insertItemName.length() != 0){
                            try {
                                ///onInserteメソッド呼び出し
                                dbAccess.insertItemData(helper,insertItemName,insertItemUrl,insertCatagoryName);
                                Toast.makeText(getActivity(), "Item added", Toast.LENGTH_LONG)
                                        .show();
                                startActivity(intent);
                            }catch (SQLException e){
                                Toast.makeText(getActivity(), String.format("%s is Already exists!!", insertItemName),Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            Toast.makeText(getActivity(), "Please enter ItemName!!", Toast.LENGTH_LONG)
                                    .show();
                        }



            }




        });

        return dialog;


    }
}
