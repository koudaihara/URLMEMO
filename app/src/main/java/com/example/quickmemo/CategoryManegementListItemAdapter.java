package com.example.quickmemo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.quickmemo.Entity.CategoryManegementListItem;

import java.util.ArrayList;

public class CategoryManegementListItemAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<CategoryManegementListItem> data;
    private int resource;

    CategoryManegementListItemAdapter(Context context,
                                      ArrayList<CategoryManegementListItem> data, int resource) {
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

    @SuppressLint("ResourceType")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Activity activity = (Activity) context;
        CategoryManegementListItem categoryManegementListItem = (CategoryManegementListItem) getItem(position);
        if (convertView == null) {
            convertView = activity.getLayoutInflater()
                    .inflate(resource, null);
        }
        ((TextView)convertView.findViewById(R.id.CategoryName)).setText(categoryManegementListItem.getCategoryName());
        int resouceId = Integer.parseInt(categoryManegementListItem.getCategoryColor());
        switch (resouceId) {

            case 1:
                convertView.findViewById(R.id.CategoryColor).setBackgroundColor(Color.rgb(255, 255, 0));
                break;
            case 2:
                convertView.findViewById(R.id.CategoryColor).setBackgroundColor(Color.rgb(255, 165, 0));
                break;
            case 3:
                convertView.findViewById(R.id.CategoryColor).setBackgroundColor(Color.rgb(255, 192, 203));
                break;
            case 4:
                convertView.findViewById(R.id.CategoryColor).setBackgroundColor(Color.rgb(255, 0, 0));
                break;
            case 5:
                convertView.findViewById(R.id.CategoryColor).setBackgroundColor(Color.rgb(0, 128, 0));
                break;
            case 6:
                convertView.findViewById(R.id.CategoryColor).setBackgroundColor(Color.rgb(0, 0, 255));
                break;
            case 7:
                convertView.findViewById(R.id.CategoryColor).setBackgroundColor(Color.rgb(128, 0, 128));
                break;
            case 8:
                convertView.findViewById(R.id.CategoryColor).setBackgroundColor(Color.rgb(192, 192, 192));
                break;
            case 9:
                convertView.findViewById(R.id.CategoryColor).setBackgroundColor(Color.rgb(0, 0, 0));
                break;

        }
        return convertView;
    }
}
