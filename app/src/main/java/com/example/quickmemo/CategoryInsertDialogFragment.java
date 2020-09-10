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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;

public class CategoryInsertDialogFragment extends DialogFragment {

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
        final Intent intent = new Intent(getContext(), CategoryManegementActivity.class);

        //updatedialogのLayoutを取得
        LinearLayout layout = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.categorydialog,null);

        //updatedialogのEdittext,RadioGroupを取得
        final EditText dialogCategoryName = (EditText) layout.findViewById(R.id.dialogcategoryname);
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


        //RadioGroupの初期値Blackに対して、選択時のxmlを適用
        dialogCategoryColorgroup.check(R.id.Black);
        RadioButton radio = dialogCategoryColorgroup.findViewById(R.id.Black);
        radio.setBackgroundResource(R.drawable.black);


        //builderにdialogに設定する情報をセット
        builder.setTitle("NewCategory")
                .setView(layout)
                .setPositiveButton("Save",null)
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) { }
                        }
                );
        //AlartDialog生成し表示
        AlertDialog dialog = builder.create();
        dialog.show();

        //Saveボタン押下時のnullチェック
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                //ダイアログに入力されているCategotyNameを取得
                String insertCategoryName = dialogCategoryName.getText().toString();
                //ダイアログに入力されているCategoryColorのIdを取得
                int categoryColorId = dialogCategoryColorgroup.getCheckedRadioButtonId();
                String insertCategoryColor = new DataConverter().radioIdtoCategoryColorConverter(categoryColorId);

                // String insertCategoryColor = dialogCategoryColor.getSelectedItem().toString();
                if(insertCategoryName.length() != 0){
                    try {
                        ///onInserteメソッド呼び出し
                        dbAccess.insertCategoryData(helper,insertCategoryName,insertCategoryColor);
                        Toast.makeText(getActivity(), "Category added", Toast.LENGTH_LONG)
                                .show();
                        startActivity(intent);
                    }catch (SQLException e){
                        Toast.makeText(getActivity(), String.format("%s is Already exists!!", insertCategoryName),Toast.LENGTH_LONG).show();
                    }
                }else {
                    Toast.makeText(getActivity(), "Please enter CategoryName!!", Toast.LENGTH_LONG)
                            .show();
                }
            }
        });

        return dialog;

    }
}
