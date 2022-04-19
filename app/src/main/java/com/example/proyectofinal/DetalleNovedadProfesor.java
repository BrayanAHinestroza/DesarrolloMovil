package com.example.proyectofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class DetalleNovedadProfesor extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_novedad_profesor);

        Intent myIntent = getIntent();
        String id_curso = myIntent.getStringExtra("id_curso");

        System.out.println(id_curso);
    }
}