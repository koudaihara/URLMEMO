package com.example.quickmemo;

import com.example.quickmemo.Entity.CategoryData;
import com.example.quickmemo.Entity.ItemData;
import com.example.quickmemo.Entity.ItemManegementListItem;

import java.util.ArrayList;
import java.util.Random;

public class DataConverter {

    /**
     *
     *ItemDataからItemManegementListItem型に変換
     *
     */
    public ArrayList<ItemManegementListItem> ItemManegementListItemConverter(ArrayList<ItemData> itemdata){

        ArrayList<ItemManegementListItem> itemManegementListItems = new ArrayList<ItemManegementListItem>();

        for(int i = 0 ; i < itemdata.size() ; i++){

            ItemManegementListItem itemManegementListItem = new ItemManegementListItem();
            itemManegementListItem.setId((new Random()).nextLong());
            itemManegementListItem.setItemName(itemdata.get(i).getItemName());
            itemManegementListItem.setItemUrl(itemdata.get(i).getItemUrl());
            itemManegementListItem.setCategoryName(itemdata.get(i).getCategoryName());
            itemManegementListItem.setCategoryColor(itemdata.get(i).getCategoryColor());
            itemManegementListItems.add(itemManegementListItem);

        }

        return itemManegementListItems;

    }
    /**
     *
     * ラジオボタンID（リソースID）からカテゴリーカラー（DB値）に変換
     *
     */
    public String radioIdtoCategoryColorConverter(int radioId){

        String categoryColor = null;

        switch (radioId) {
            case R.id.Yellow:
                categoryColor = "1";
                break;
            case R.id.Orange:
                categoryColor = "2";
                break;
            case R.id.Pink:
                categoryColor = "3";
                break;
            case R.id.Red:
                categoryColor = "4";
                break;
            case R.id.Green:
                categoryColor = "5";
                break;
            case R.id.Blue:
                categoryColor = "6";
                break;
            case R.id.Purple:
                categoryColor = "7";
                break;
            case R.id.Silver:
                categoryColor = "8";
                break;
            case R.id.Black:
                categoryColor = "9";
                break;

        }

        return categoryColor;

    }

    /**
     *
     * カテゴリーカラー（DB値）からラジオボタンID（リソースID）に変換ｎ
     *
     */
    public int categoryColortoRadioButtonIdConverter(String categoryColor){

        int radioId = 0;

        switch (categoryColor) {
            case "1":
                radioId = R.id.Yellow;
                break;
            case "2":
                radioId = R.id.Orange;
                break;
            case "3":
                radioId = R.id.Pink;
                break;
            case "4":
                radioId = R.id.Red;
                break;
            case "5":
                radioId = R.id.Green;
                break;
            case "6":
                radioId = R.id.Blue;
                break;
            case "7":
                radioId = R.id.Purple;
                break;
            case "8":
                radioId = R.id.Silver;
                break;
            case "9":
                radioId = R.id.Black;
                break;

        }

        return radioId;

    }

    /**
     * カテゴリーアップデートダイアログのスピナー初期表示用にcategoryNameAllDataのインデックスを入れ替える
     * 引数で与えられたselectCategoryNameに合致するCategoryDataを先頭にする
     */

    public ArrayList<String> categoryDataIndexChange(ArrayList<CategoryData> categoryAllData, String selectCategoryName){

        ArrayList<CategoryData> spinnercategoryNameData = new ArrayList<CategoryData>();
        ArrayList<String> spinnerCategoryName = new ArrayList<String>();

        for(CategoryData categoryData:categoryAllData){

            String categoryName = categoryData.getCategoryName();

            if(categoryName.equals(selectCategoryName)){

                spinnercategoryNameData.add(0,categoryData);

            }else{

                spinnercategoryNameData.add(categoryData);
            }

        }

        for(CategoryData categoryData : spinnercategoryNameData){

            String categoryName = categoryData.getCategoryName();
            spinnerCategoryName.add(categoryName);

        }

        return spinnerCategoryName;
    }


}
