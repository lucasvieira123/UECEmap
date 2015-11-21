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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;
import java.util.List;

public class PesquisaActivity extends AppCompatActivity {
    private static final int URL_LOADER = 0;
    private MyDatabaseHelper db = new MyDatabaseHelper(this);
    private LugarDAOSQLLite lugarDAO = new LugarDAOSQLLite(db);
    private MyAdapter adapter;

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
        final List<Lugar> lugarList = lugarDAO.findByNome(query);
        ListView lv = (ListView) findViewById(R.id.listPesq);
        Log.d("alert","Query: "+query);
        if(lugarList.size() == 0)
            mostrarToast("Busca não encontrou nenhum resultado.");
        else{
            Log.d("alert","primeiro resultado: "+lugarList.get(0).getNome());
            adapter = new MyAdapter(this,lugarList);
            if(adapter == null)
                Log.d("alert","adapter nulo");
            else if(lv == null)
                Log.d("alert","lv nulo");
            lv.setAdapter(adapter);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        marcarMapa(lugarList.get(position).getLongitude(),lugarList.get(position).getLatitude());
                }
            });
        }
    }

    public void mostrarToast(String mensagem){
        Toast toast = Toast.makeText(this, mensagem, Toast.LENGTH_SHORT);
        toast.show();
    }

    public void marcarMapa(Double longi, Double lat){
        Bundle b = new Bundle();
        b.putDouble("long",longi);
        b.putDouble("lat", lat);
        Intent i = new Intent(this,MapsActivity.class);
        i.putExtras(b);
        startActivity(i);

    }

}
