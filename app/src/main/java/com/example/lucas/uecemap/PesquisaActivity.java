package com.example.lucas.uecemap;

import android.app.LoaderManager;
import android.app.SearchManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;
import java.util.List;

public class PesquisaActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final int URL_LOADER = 0;
    MyDatabaseHelper db = new MyDatabaseHelper(this);
    LugarDAOSQLLite lugarDAO = new LugarDAOSQLLite(db);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesquisa);
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView)findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //fazerPesquisa(query);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                fazerPesquisa(newText);
                return false;
            }
        });
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        //faz trata a pesquisa
        handleIntent(intent);

    }

    private void handleIntent(Intent intent){
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            fazerPesquisa(query);


        }
    }

    private void fazerPesquisa(String query){
        getLoaderManager().initLoader(URL_LOADER, null, this);
        List<Lugar> lugarList = lugarDAO.findByNome(query);
        if(lugarList.size() == 0)
            mostrarToast("Busca não encontrou nenhum resultado.");


    }

    public void mostrarToast(String mensagem){
        Toast toast = Toast.makeText(this, mensagem, Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case URL_LOADER:
                // Returns a new CursorLoader
                return new CursorLoader(
                        this,   // Parent activity context
                        Uri.parse("lugar"),        // Table to query
                        new String[]{LugarORM.TAB_NOME,LugarORM.COL_DESC},     // Projection to return
                        null,            // No selection clause
                        null,            // No selection arguments
                        null             // Default sort order
                );
            default:
                // An invalid id was passed in
                return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.changeCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.changeCursor(null);
    }

    ListView list = (ListView) findViewById(R.id.listView);

    SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,R.layout.item,null,new String[]{LugarORM.TAB_NOME,LugarORM.COL_DESC},new int[]{R.id.item_title,R.id.item_content});
    list.setAdapter(adapter);

}
