package com.example.lucas.uecemap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by stack on 20/10/15.
 */
public class DatabaseHelper extends SQLiteOpenHelper{

    private static final String TAB_NOME = "lugares";
    private static final String COL_NOME = "nome";
    private static final String COL_LAT = "latitude";
    private static final String COL_LONG = "longitude";
    private static final String COL_DESC = "descricao";
    private static final String CRIAR_TABELA = "CREATE TABLE IF NOT EXISTS lugares (nome TEXT NOT NULL, latitude FLOAT NOT NULL, longitude FLOAT NOT NULL, descricao TEXT);";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CRIAR_TABELA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Deleta BD
        db.execSQL("DROP TABLE IF EXISTS"+TAB_NOME);
        // Cria tabela novamente
        onCreate(db);
    }

    private static final int DATABASE_VERSION = 3;

    DatabaseHelper (Context context) {
        super(context, "ueceMap", null, DATABASE_VERSION);
    }

    public Lugar obterLugar (String nome){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TAB_NOME,new String[]{COL_NOME,COL_LAT,COL_LONG,COL_DESC},COL_NOME+"=?",new String[]{nome},null,null,null,null);
        if(cursor!=null) cursor.moveToFirst();
        Lugar lugar = new Lugar(cursor.getString(0),cursor.getString(3),Double.parseDouble(cursor.getString(1)),Double.parseDouble(cursor.getString(2)));
        return lugar;
    }

    public ArrayList<Lugar> obterTodosOsLugares(){
        ArrayList<Lugar> lugarList = new ArrayList<Lugar>();


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+TAB_NOME,null);

        if(cursor.moveToFirst()){
            do{
                Lugar lugar = new Lugar();
                lugar.setNome(cursor.getString(0));
                lugar.setLatitude(Double.parseDouble(cursor.getString(1)));
                lugar.setLongitude(Double.parseDouble(cursor.getString(2)));
                lugar.setDescricao(cursor.getString(3));
                lugarList.add(lugar);
            }while(cursor.moveToNext());
        }
        return lugarList;
    }

    public void addLugar (Lugar lugar){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COL_NOME,lugar.getNome());
        cv.put(COL_LAT,lugar.getLatitude());
        cv.put(COL_LONG,lugar.getLongitude());
        cv.put(COL_DESC,lugar.getDescricao());

        db.insert(TAB_NOME, null, cv);
        db.close();
    }


}
