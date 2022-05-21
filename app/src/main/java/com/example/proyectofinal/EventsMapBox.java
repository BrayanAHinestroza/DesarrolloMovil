package com.example.proyectofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import com.mapbox.maps.MapView;
import com.mapbox.maps.Style;


public class EventsMapBox extends AppCompatActivity {
    MapView mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_map_box);


        Intent myIntent = getIntent();
        String latitud = myIntent.getStringExtra("lat");
        String longitud = myIntent.getStringExtra("long");

        Uri gmmIntentUri = Uri.parse("google.streetview:cbll="+latitud+","+longitud);


        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);

        mapIntent.setPackage("com.google.android.apps.maps");

        startActivity(mapIntent);

        mapView = (MapView) findViewById(R.id.mapView);
        mapView.getMapboxMap().loadStyleUri(Style.MAPBOX_STREETS);

    }
}