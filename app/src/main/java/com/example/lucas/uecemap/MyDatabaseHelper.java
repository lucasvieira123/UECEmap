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
public class MyDatabaseHelper extends SQLiteOpenHelper{

    private static final String TAB_NOME = "lugares";
    private static final String COL_NOME = "nome";
    private static final String COL_LAT = "latitude";
    private static final String COL_LONG = "longitude";
    private static final String COL_DESC = "descricao";
    private static final String COL_ID = "id";
    private static final String COL_CONT = "contato";
    private static final String COL_FOTO = "foto";
    private static final String CRIAR_TABELA = "CREATE TABLE IF NOT EXISTS lugares (id INTEGER PRIMARY KEY AUTOINCREMENT, nome TEXT NOT NULL, latitude FLOAT NOT NULL, longitude FLOAT NOT NULL, descricao TEXT, contato INTEGER NOT NULL, foto BLOB);";

    MyDatabaseHelper(Context context) {
        super(context, "ueceMap", null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CRIAR_TABELA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Deleta BD
        db.execSQL("DROP TABLE IF EXISTS "+TAB_NOME);
        // Cria tabela novamente
        onCreate(db);
    }

    private static final int DATABASE_VERSION = 12;

}
