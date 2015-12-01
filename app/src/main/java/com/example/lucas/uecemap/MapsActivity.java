package com.example.lucas.uecemap;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity {

    private GoogleMap mMap;
    private MyDatabaseHelper db = new MyDatabaseHelper(this);
    private LugarDAOSQLLite lugarDAO = new LugarDAOSQLLite(db);
    private ArrayList<Marker> listMarker;
    private List<Lugar> lugarList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Intent i = new Intent(this,LugarInfo.class);
                Bundle b = new Bundle();
                b.putString("nome",lugarList.get(0).getNome());
                i.putExtras(b);
                startActivity(i);
                return false;
            }
        });

        // Associate searchable configuration with the SearchView

    }





    public void searchClick(View v){
        Intent i = new Intent(this,PesquisaActivity.class);
        startActivity(i);
    }


    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
        Intent i = getIntent();
        Bundle b = i.getExtras();
        if(b!=null){
            lugarList = lugarDAO.findByNome(b.getString("nome"));
            adicionarMarcadores(lugarList);
        }
    }




    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                //setUpMap();
                //onMapReady(mMap);

                mMap.setMyLocationEnabled(true);
                mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(-3.785914, -38.552517)));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(16));
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            }
        }
    }




    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */



    private void adicionarMarcadores(List<Lugar> listLugar){
        listMarker = new ArrayList<>();
        for(Lugar l: listLugar){
            MarkerOptions mark;
            mark = new MarkerOptions();
            mark.position(new LatLng(l.getLatitude(), l.getLongitude()));
            mark.title(l.getNome());
            mark.snippet(l.getDescricao() + "\nContato: " + l.getContato());
            listMarker.add(mMap.addMarker(mark));
        }



    }


    private void setUpMap() {
        //preencherBD(db);
        ArrayList<Lugar> lugarList = lugarDAO.obterTodosOsLugares();
        for(int i=0;i<lugarList.size();i++){
            MarkerOptions mark = new MarkerOptions();
            mark.position(new LatLng(lugarList.get(i).getLatitude(), lugarList.get(i).getLongitude()));
            mark.title(lugarList.get(i).getNome());
            mark.snippet(lugarList.get(i).getDescricao());
            Marker m = mMap.addMarker(mark);
            m.setVisible(false);
            listMarker.add(m);
        }

    }
    //função a ser chamada somente uma vez a cada versao do BDhit
    private void preencherBD(){


        lugarDAO.addLugar(new Lugar("UECE", "Bem-vindo à UECE", -3.785914, -38.552517,12345678));
        lugarDAO.addLugar(new Lugar("Reitoria", "Reitoria da UECE", -3.785882, -38.552594,12345678));
        lugarDAO.addLugar(new Lugar("MACC/MPCOMP", "Prédio de pesquisa e mestrado em computação", -3.787052, -38.552691,12345678));
        lugarDAO.addLugar(new Lugar("Bloco P", "Bloco da Computação/Matemática/Psicologia", -3.789726, -38.553227,12345678));
        lugarDAO.addLugar(new Lugar("R.U.", "Restaurante Universitário", -3.790486, -38.553262,12345678));
    }


}
