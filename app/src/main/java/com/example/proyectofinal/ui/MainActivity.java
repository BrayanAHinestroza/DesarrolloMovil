package com.example.proyectofinal.ui;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.example.proyectofinal.R;
import com.example.proyectofinal.io.AdminSQLiteOpenHelper;
import com.example.proyectofinal.ui.HomeEstudiante;
import com.example.proyectofinal.ui.HomeProfesor;
import com.example.proyectofinal.ui.Login;

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
                db.close();
                startActivity(intent);
            }else if (fila.getString(1).equals("3")){
                Intent intent = new Intent(this, HomeProfesor.class);
                db.close();
                startActivity(intent);
            }else{
                Intent intent = new Intent(this, Login.class);
                db.close();
                startActivity(intent);
            }
        }else{
            Intent intent = new Intent(this, Login.class);
            db.close();
            startActivity(intent);
        }
    }
}