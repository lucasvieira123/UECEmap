package com.example.lucas.uecemap;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static com.example.lucas.uecemap.LugarORM.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by stack on 22/10/15.
 */
public class LugarDAOSQLLite {

    private final SQLiteOpenHelper mSQLLiteDatabase;

    public LugarDAOSQLLite(SQLiteOpenHelper sqlLiteDatabase) {
        this.mSQLLiteDatabase = sqlLiteDatabase;
    }

    public Lugar getById(String id) {
        return null;
    }

    public List<Lugar> findByNomeOrDescricao(String busca) {
        List<Lugar> aux = findByNome(busca);
        aux.addAll(findByDescricao(busca));
        return aux;
    }

    private List<Lugar> findByColumn(String column, String value) {

        SQLiteDatabase db = mSQLLiteDatabase.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TAB_NOME + " WHERE " + column + " like '%'||?||'%'", new String[] {value});
        List<Lugar> aux = new ArrayList<>();
        try {
            converterCursorParaObjetos(cursor, aux);
        } finally {
            cursor.close();
        }
        return aux;
    }

    public List<Lugar> findByNome(String nome) {
        return findByColumn(COL_NOME, nome);
    }

    private void converterCursorParaObjetos(Cursor cursor, List<Lugar> aux) {
        Lugar lugar = null;
       if(cursor.moveToFirst()){
          do {

               lugar = new Lugar(Integer.parseInt(cursor.getString(0)),cursor.getString(1),cursor.getString(4), Double.parseDouble(cursor.getString(2)),Double.parseDouble(cursor.getString(3)), Integer.parseInt(cursor.getString(5)), cursor.getString(6).getBytes());
               aux.add(lugar);
           } while (cursor.moveToNext());
       }

    }



    public List<Lugar> findByDescricao(String descricao) {
        return findByColumn(COL_DESC,descricao);
    }

   /* public Lugar obterLugar(String nome) {
        SQLiteDatabase db = mSQLLiteDatabase.getReadableDatabase();
        Cursor cursor = db.query(TAB_NOME,new String[]{COL_NOME,COL_LAT,COL_LONG,COL_DESC},COL_NOME+"=?",new String[]{nome.toUpperCase()},null,null,null,null);
        Lugar lugar = null;
        if(cursor!=null && cursor.moveToFirst()){
            lugar = new Lugar(cursor.getString(0),cursor.getString(3),Double.parseDouble(cursor.getString(1)),Double.parseDouble(cursor.getString(2)));
            cursor.close();
        }

        return lugar;
    }*/

    public ArrayList<Lugar> obterTodosOsLugares(){
        ArrayList<Lugar> lugarList = new ArrayList<Lugar>();

        SQLiteDatabase db = mSQLLiteDatabase.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+TAB_NOME,null);

        if(cursor.moveToFirst()){
            do{
                Lugar lugar = new Lugar();
                lugar.setId(Integer.parseInt(cursor.getString(0)));
                lugar.setNome(cursor.getString(1));
                lugar.setLatitude(Double.parseDouble(cursor.getString(2)));
                lugar.setLongitude(Double.parseDouble(cursor.getString(3)));
                lugar.setDescricao(cursor.getString(4));
                lugar.setContato(Integer.parseInt(cursor.getString(5)));
                lugar.setFoto(cursor.getString(6).getBytes());
                lugarList.add(lugar);

            }while(cursor.moveToNext());
        }
        return lugarList;
    }

    public void addLugar(Lugar lugar) {
        SQLiteDatabase db = mSQLLiteDatabase.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COL_NOME,lugar.getNome().toUpperCase());
        cv.put(COL_LAT,lugar.getLatitude());
        cv.put(COL_LONG,lugar.getLongitude());
        cv.put(COL_DESC,lugar.getDescricao());
        cv.put(COL_CONT, lugar.getContato());
        cv.put(COL_FOTO,lugar.getFoto());



        db.insert(TAB_NOME, null, cv);


        db.close();
    }

    public boolean contemRegistro(){
        SQLiteDatabase db = mSQLLiteDatabase.getReadableDatabase();
        long numOfEntries = DatabaseUtils.queryNumEntries(db, LugarORM.TAB_NOME);

        if(numOfEntries == 0l) {
            // Tabela vazia, preencha com seus dados iniciais
            return false;
        } else {
            return true;
        }
    }


}
