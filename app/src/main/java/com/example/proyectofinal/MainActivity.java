package com.example.proyectofinal;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_ProyectoFinal);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "proyecto_final", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();

        Cursor fila = db.rawQuery("select * from auth", null);

        if(fila.moveToFirst()){
            if (fila.getString(1).equals("2")){
                Intent intent = new Intent(this, HomeEstudiante.class);
                startActivity(intent);
            }else if (fila.getString(1).equals("3")){
                Intent intent = new Intent(this, HomeProfesor.class);
                startActivity(intent);
            }else{
                Intent intent = new Intent(this, Login.class);
                startActivity(intent);
            }
        }else{
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
        }
    }
}