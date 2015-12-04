package com.example.lucas.uecemap;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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

    public Lugar getById(String id){
        SQLiteDatabase db = mSQLLiteDatabase.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+TAB_NOME + " WHERE "+ COL_ID + "= ?",new String[]{id});
        List<Lugar> aux = new ArrayList<>();
        try{
            converterCursorParaObjetos(cursor, aux);
        } finally{
            cursor.close();
        }
        return aux.get(0);
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

               lugar = new Lugar(Integer.parseInt(cursor.getString(0)),cursor.getString(1),cursor.getString(4), Double.parseDouble(cursor.getString(2)),Double.parseDouble(cursor.getString(3)), Integer.parseInt(cursor.getString(5)));
               aux.add(lugar);
           } while (cursor.moveToNext());
       }

    }
    public List<Lugar> findByDescricao(String descricao) {
        return findByColumn(COL_DESC,descricao);
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

    public void fillEmptyDB(){
        SQLiteDatabase db = mSQLLiteDatabase.getReadableDatabase();
        long numOfEntries = DatabaseUtils.queryNumEntries(db, LugarORM.TAB_NOME);
        if(numOfEntries == 0l) {
            addLugar(new Lugar("UECE", "Bem-vindo à UECE", -3.785914, -38.552517,12345678));
            addLugar(new Lugar("Reitoria", "Reitoria da UECE", -3.785882, -38.552594,12345678));
            addLugar(new Lugar("MACC/MPCOMP", "Prédio de pesquisa e mestrado em computação", -3.787052, -38.552691,12345678));
            addLugar(new Lugar("Bloco P", "Bloco da Computação/Matemática/Psicologia", -3.789726, -38.553227,12345678));
            addLugar(new Lugar("R.U.", "Restaurante Universitário", -3.790486, -38.553262,12345678));;
        }
    }

}
