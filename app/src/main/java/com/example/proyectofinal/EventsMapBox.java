package com.example.proyectofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class EventsMapBox extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_map_box);

        Intent myIntent = getIntent();
        String latitud = myIntent.getStringExtra("lat");
        String longitud = myIntent.getStringExtra("long");




    }
}