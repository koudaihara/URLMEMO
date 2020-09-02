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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;

import java.util.Objects;

public class CategoryUpdateDialogFragment extends DialogFragment {

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
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final DbAccess dbAccess = new DbAccess();
        final DatabaseHelper helper = new DatabaseHelper(getActivity());
        final Intent intent = new Intent(getContext(), CategoryManegementActivity.class);

        //updatedialogのLayoutを取得
        LinearLayout layout = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.categorydialog,null);
        final RadioGroup dialogCategoryColorgroup = (RadioGroup) layout.findViewById(R.id.categorycolorgroup);
        //RadioGroupにリスナーをセット
        dialogCategoryColorgroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radio = group.findViewById(checkedId);
                CategoryColorRadiogroupListener categoryColorRadiogroupListener = new CategoryColorRadiogroupListener();
                categoryColorRadiogroupListener.backbroundreset(dialogCategoryColorgroup);
                categoryColorRadiogroupListener.categoryColorRadioGroupListenerSet(radio);
            }
        });


        //updatedialogのEdittextを取得
        final EditText dialogCategoryName = (EditText) layout.findViewById(R.id.dialogcategoryname);
        //CategoryManegementActivityから選択したリストのCategoryNameを取得
        Bundle args = Objects.requireNonNull(getArguments());
        final String beforeCategoryName = args.getString("beforeCategoryName");
        final String beforeCategoryColor = args.getString("beforeCategoryColor");

        //updatedialogのEdittext,RadioGroupに初期値を設定
        dialogCategoryName.setText(beforeCategoryName);
        int resoceRadioButtonId = Integer.parseInt(beforeCategoryColor);
        dialogCategoryColorgroup.check(resoceRadioButtonId);
        RadioButton radio = dialogCategoryColorgroup.findViewById(resoceRadioButtonId);
        CategoryColorRadiogroupListener categoryColorRadiogroupListener = new CategoryColorRadiogroupListener();
        categoryColorRadiogroupListener.categoryColorRadioGroupListenerSet(radio);

        //builderにdialogに設定する情報をセット
        builder.setTitle("UpdateCategory")
        .setView(layout)
        .setPositiveButton("Save",null)
        .setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(intent);
                    }
                }
        );
        builder.setNeutralButton("Delete",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        //ダイアログに入力されているCategorynameを取得
                        String deleteCategoryName = dialogCategoryName.getText().toString();
                    try {
                        //ondeleteメソッド呼び出し
                        dbAccess.deleteCategoryData(helper,deleteCategoryName);
                        //ダイアログに入力されているItemnameを取得
                        Toast.makeText(getActivity(),"Category Deleted",Toast.LENGTH_SHORT).show();
                    }catch (SQLException e){
                        Toast.makeText(getActivity(), "An error occurred!!", Toast.LENGTH_LONG)
                                .show();
                    }
                        //MainActivityに遷移
                        startActivity(intent);
                    }
                }
        );

         //AlartDialog生成
         AlertDialog dialog = builder.create();
         dialog.show();

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                //ダイアログに入力されているCategoryNameを取得
                String updateCategoryName = dialogCategoryName.getText().toString();
                if(updateCategoryName.length() != 0){
                    //ダイアログに入力されている更新値を取得
                    String updateCategoryColor = Integer.toString(dialogCategoryColorgroup.getCheckedRadioButtonId());

                    try {
                        ///onUpdateメソッド呼び出し
                        dbAccess.updateCategoryData(helper,updateCategoryName,updateCategoryColor,"CategoryName = ?",beforeCategoryName);
                        Toast.makeText(getActivity(), "Category Updated", Toast.LENGTH_SHORT)
                                .show();
                    }catch (SQLException e){
                        Toast.makeText(getActivity(), String.format("%s is Already exists!!", updateCategoryName),Toast.LENGTH_LONG).show();
                    }
                    startActivity(intent);
                }else {
                    Toast.makeText(getActivity(), "Please enter CategoryName!!", Toast.LENGTH_LONG)
                            .show();
                }

            }

        });

        return dialog;

    }
}
