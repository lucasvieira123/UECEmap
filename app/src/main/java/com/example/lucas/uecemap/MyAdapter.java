package com.example.lucas.uecemap;

import java.util.ArrayList;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class MyAdapter extends ArrayAdapter<ModeloTelaInfo> {

    private final Context context;
    private final ArrayList<ModeloTelaInfo> modelsArrayList;

    public MyAdapter(Context context, ArrayList<ModeloTelaInfo> modelsArrayList) {

        super(context, R.layout.item, modelsArrayList);

        this.context = context;
        this.modelsArrayList = modelsArrayList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);


            View rowView = null;
            rowView = inflater.inflate(R.layout.item, parent, false);


            ImageView imgView = (ImageView) rowView.findViewById(R.id.item_icon);
            TextView titleView = (TextView) rowView.findViewById(R.id.item_title);
            TextView contentView = (TextView) rowView.findViewById(R.id.item_content);


            imgView.setImageResource(modelsArrayList.get(position).getIcon());
            titleView.setText(modelsArrayList.get(position).getTitle());
            contentView.setText(modelsArrayList.get(position).getContent());




        return rowView;
    }
}