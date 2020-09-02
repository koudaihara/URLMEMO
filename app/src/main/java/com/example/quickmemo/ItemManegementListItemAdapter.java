package com.example.quickmemo;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ItemManegementListItemAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<ItemManegementListItem> data;
    private int resource;

    ItemManegementListItemAdapter(Context context,
                                  ArrayList<ItemManegementListItem> data, int resource) {
        this.context = context;
        this.data = data;
        this.resource = resource;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return data.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Activity activity = (Activity) context;
        ItemManegementListItem itemManegementListItem = (ItemManegementListItem) getItem(position);
        if (convertView == null) {
            convertView = activity.getLayoutInflater().inflate(resource,parent, false);
            //convertView = convertView.inflate(resource,parent,null);
        }
        ((TextView)convertView.findViewById(R.id.ItemName)).setText(itemManegementListItem.getItemName());
        ((TextView)convertView.findViewById(R.id.ItemUrl)).setText(itemManegementListItem.getItemUrl());
        ((TextView)convertView.findViewById(R.id.CategoryName)).setText(itemManegementListItem.getCategoryName());
        int resouceId = Integer.parseInt(itemManegementListItem.getCategoryColor());
        switch (resouceId) {
            case R.id.Black:
                convertView.findViewById(R.id.CategoryColor).setBackgroundColor(Color.rgb(0, 0, 0));
                break;
            case R.id.Blue:
                convertView.findViewById(R.id.CategoryColor).setBackgroundColor(Color.rgb(0, 0, 255));
                break;
            case R.id.Green:
                convertView.findViewById(R.id.CategoryColor).setBackgroundColor(Color.rgb(0, 128, 0));
                break;
            case R.id.Orange:
                convertView.findViewById(R.id.CategoryColor).setBackgroundColor(Color.rgb(255, 165, 0));
                break;
            case R.id.Purple:
                convertView.findViewById(R.id.CategoryColor).setBackgroundColor(Color.rgb(128, 0, 128));
                break;
            case R.id.Red:
                convertView.findViewById(R.id.CategoryColor).setBackgroundColor(Color.rgb(255, 0, 0));
                break;
            case R.id.Silver:
                convertView.findViewById(R.id.CategoryColor).setBackgroundColor(Color.rgb(192, 192, 192));
                break;
            case R.id.Yellow:
                convertView.findViewById(R.id.CategoryColor).setBackgroundColor(Color.rgb(255, 255, 0));
                break;
            case R.id.Pink:
                convertView.findViewById(R.id.CategoryColor).setBackgroundColor(Color.rgb(255, 192, 203));
                break;

        }
        return convertView;
    }
}
