package com.example.proyectofinal.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.proyectofinal.R;

public class HomeEstudiante extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_estudiante);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}