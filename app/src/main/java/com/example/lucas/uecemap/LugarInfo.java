package com.example.lucas.uecemap;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class LugarInfo extends AppCompatActivity {

    private MyDatabaseHelper db = new MyDatabaseHelper(this);
    private LugarDAOSQLLite lugarDAO = new LugarDAOSQLLite(db);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lugar_info);
        Intent i = getIntent();
        Bundle b = i.getExtras();
        final List<Lugar> lugarList = new ArrayList<>();
        lugarList.add(lugarDAO.getById(Integer.toString(b.getInt("id"))));
        TextView text = (TextView) findViewById(R.id.textNome);
        text.setText(lugarList.get(0).getNome());
        text = (TextView) findViewById(R.id.textDesc);
        text.setText(lugarList.get(0).getDescricao());
        text = (TextView) findViewById(R.id.textCont);
        text.setText(Integer.toString(lugarList.get(0).getContato()));
        text.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                String numero = "tel:"+ lugarList.get(0).getContato();
                startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(numero)));
                return false;
            }
        });
        ImageView img = (ImageView) findViewById(R.id.imageLugar);
        if(lugarList.get(0).getNome().equals("BLOCO P")) img.setImageResource(R.mipmap.bloco_p);
        else if(lugarList.get(0).getNome().equals("R.U.")) img.setImageResource(R.mipmap.ru);
        else if(lugarList.get(0).getNome().equals("UECE")) img.setImageResource(R.mipmap.uece);
        else if(lugarList.get(0).getNome().equals("REITORIA")) img.setImageResource(R.mipmap.reitoria);
        else if(lugarList.get(0).getNome().equals("MACC/MPCOMP")) img.setImageResource(R.mipmap.macc);
    }
}
