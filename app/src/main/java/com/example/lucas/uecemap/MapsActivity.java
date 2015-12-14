package com.example.lucas.uecemap;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends AppCompatActivity {

    private GoogleMap mMap;
    private MyDatabaseHelper db = new MyDatabaseHelper(this);
    private LugarDAOSQLLite lugarDAO = new LugarDAOSQLLite(db);
    private ArrayList<Marker> listMarker;
    private Bundle b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();
        lugarDAO.fillEmptyDB();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.title_activity_maps);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_map, menu);
        android.support.v7.widget.AppCompatButton button = (android.support.v7.widget.AppCompatButton) MenuItemCompat.getActionView(menu.findItem(R.id.buttonSearch));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchClick(v);
            }
        });
        return false;
    }
    @Override
    public void onNewIntent(Intent intent){
        super.onNewIntent(intent);
        setPlace(intent);
    }
    public void showInfo (int id){
        Intent i = new Intent(this,LugarInfo.class);
        Bundle b = new Bundle();
        b.putInt("id", id);
        i.putExtras(b);
        startActivity(i);
    }
    public void searchClick(View v){
        Intent i = new Intent(this,PesquisaActivity.class);
        startActivity(i);
    }
    public void setPlace(Intent i){
        b = i.getExtras();
        if (b != null) {
           addMark(b.getDouble("lat"),b.getDouble("long"));
        }else Log.d("alert","b é null");
    }
    private void setUpMapIfNeeded() {
        if (mMap == null) {
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            if (mMap != null) {
                mMap.setMyLocationEnabled(true);
                mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(-3.788221, -38.552883)));
                mMap.animateCamera(CameraUpdateFactory.zoomTo((float) 16.4));
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        showInfo(b.getInt("id"));
                        return false;
                    }
                });
            }
        }
    }
    private void addMark(double lat, double longi){
        mMap.clear();
        MarkerOptions mark;
        mark = new MarkerOptions();
        mark.position(new LatLng(lat, longi));
        mMap.addMarker(mark);
    }
    private void addMarkers(List<Lugar> listLugar){
        mMap.clear();
        listMarker = new ArrayList<>();
        for(Lugar l: listLugar){
            MarkerOptions mark;
            mark = new MarkerOptions();
            mark.position(new LatLng(l.getLatitude(), l.getLongitude()));
            listMarker.add(mMap.addMarker(mark));
        }
    }
}