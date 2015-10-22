package com.example.lucas.uecemap;

/**
 * Created by stack on 22/10/15.
 */
public class LugarORM {

    static final String TAB_NOME = "lugares";
    static final String COL_NOME = "nome";
    static final String COL_LAT = "latitude";
    static final String COL_LONG = "longitude";
    static final String COL_DESC = "descricao";
    private static final String COL_ID = "id";
    static final String CRIAR_TABELA = "CREATE TABLE IF NOT EXISTS lugares (nome TEXT NOT NULL, latitude FLOAT NOT NULL, longitude FLOAT NOT NULL, descricao TEXT);";

}
