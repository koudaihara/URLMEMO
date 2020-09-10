package com.example.quickmemo;

import java.util.ArrayList;
import java.util.Random;

public class DataConverter {

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


}
