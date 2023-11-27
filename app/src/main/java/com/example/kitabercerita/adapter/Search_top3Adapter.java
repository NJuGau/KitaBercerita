package com.example.kitabercerita.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.kitabercerita.R;

import java.util.ArrayList;

public class Search_top3Adapter extends BaseAdapter {

    private Context context;
    private ArrayList<String> dataList;
    private ArrayList<String> dataList1;
    private ArrayList<String> dataList2;

    public Search_top3Adapter(Context context, ArrayList<String> dataList, ArrayList<String> dataList1, ArrayList<String> dataList2) {
        this.context = context;
        this.dataList = dataList;
        this.dataList1 = dataList1;
        this.dataList2 = dataList2;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_search, parent, false);
        }

        TextView textView = convertView.findViewById(R.id.textView);
        TextView textView1 = convertView.findViewById(R.id.textView1);
        TextView textView2 = convertView.findViewById(R.id.textView2);

        textView.setText(dataList.get(position));
        textView1.setText(dataList1.get(position));
        textView2.setText(dataList2.get(position));


        return convertView;
    }
}



