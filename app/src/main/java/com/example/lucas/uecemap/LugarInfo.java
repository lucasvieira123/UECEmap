package com.example.lucas.uecemap;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

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
        final List<Lugar> lugarList = lugarDAO.findByNome(b.getString("nome"));
        TextView text = (TextView) findViewById(R.id.textNome);
        text.setText(lugarList.get(0).getNome());
        text = (TextView) findViewById(R.id.textDesc);
        text.setText(lugarList.get(0).getDescricao());
        text = (TextView) findViewById(R.id.textCont);
        text.setText(lugarList.get(0).getContato());
        text.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                String numero = "tel:"+ lugarList.get(0).getContato();
                startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(numero)));
                return false;
            }
        });

    }



}
