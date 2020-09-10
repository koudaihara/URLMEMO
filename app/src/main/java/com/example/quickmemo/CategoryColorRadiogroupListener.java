package com.example.quickmemo;

import android.graphics.Color;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.quickmemo.R;

public class CategoryColorRadiogroupListener {


    public void categoryColorRadioGroupListenerSet(RadioButton radioButton) {

                int radioId = radioButton.getId();
                switch (radioId) {
                    case R.id.Black:
                        radioButton.setBackgroundResource(R.drawable.black);
                        break;
                    case R.id.Blue:
                        radioButton.setBackgroundResource(R.drawable.blue);
                        break;
                    case R.id.Green:
                        radioButton.setBackgroundResource(R.drawable.green);
                        break;
                    case R.id.Orange:
                        radioButton.setBackgroundResource(R.drawable.orange);
                        break;
                    case R.id.Purple:
                        radioButton.setBackgroundResource(R.drawable.purple);
                        break;
                    case R.id.Red:
                        radioButton.setBackgroundResource(R.drawable.red);
                        break;
                    case R.id.Silver:
                        radioButton.setBackgroundResource(R.drawable.silver);
                        break;
                    case R.id.Yellow:
                        radioButton.setBackgroundResource(R.drawable.yellow);
                        break;
                    case R.id.Pink:
                        radioButton.setBackgroundResource(R.drawable.pink);
                        break;

                }

    }

    public void backbroundreset(RadioGroup group) {

        int[] resourceid = {R.id.Black, R.id.Blue, R.id.Green, R.id.Orange, R.id.Pink, R.id.Purple, R.id.Red, R.id.Silver, R.id.Yellow};

        for (int id : resourceid) {

            RadioButton radio = group.findViewById(id);

            switch (id) {
                case R.id.Black:
                    radio.setBackgroundColor(Color.rgb(0, 0, 0));
                    break;
                case R.id.Blue:
                    radio.setBackgroundColor(Color.rgb(0, 0, 255));
                    break;
                case R.id.Green:
                    radio.setBackgroundColor(Color.rgb(0, 128, 0));
                    break;
                case R.id.Orange:
                    radio.setBackgroundColor(Color.rgb(255, 165, 0));
                    break;
                case R.id.Purple:
                    radio.setBackgroundColor(Color.rgb(128, 0, 128));
                    break;
                case R.id.Red:
                    radio.setBackgroundColor(Color.rgb(255, 0, 0));
                    break;
                case R.id.Silver:
                    radio.setBackgroundColor(Color.rgb(192, 192, 192));
                    break;
                case R.id.Yellow:
                    radio.setBackgroundColor(Color.rgb(255, 255, 0));
                    break;
                case R.id.Pink:
                    radio.setBackgroundColor(Color.rgb(255, 192, 203));
                    break;

            }
        }
    }
}