package com.example.lucas.uecemap;

import java.util.ArrayList;
import java.util.List;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;


public class MyAdapter extends ArrayAdapter<Lugar> {

    public MyAdapter(Context context, List<Lugar> lugarList) {

        super(context, R.layout.item, lugarList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Lugar lugar = getItem(position);

        if(convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item, parent, false);

        TextView tvNome = (TextView) convertView.findViewById(R.id.item_title);
        TextView tvDescricao = (TextView) convertView.findViewById(R.id.item_content);

        tvNome.setText(lugar.getNome());
        tvDescricao.setText(lugar.getDescricao());


        return convertView;
    }
}