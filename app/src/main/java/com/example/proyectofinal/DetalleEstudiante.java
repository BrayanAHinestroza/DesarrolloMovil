package com.example.proyectofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class DetalleEstudiante extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_estudiante);

        Intent myIntent = getIntent();
        String id_estudiante = myIntent.getStringExtra("id_estudiante");

        System.out.println("HEREEEEE ->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println(id_estudiante);
    }
}