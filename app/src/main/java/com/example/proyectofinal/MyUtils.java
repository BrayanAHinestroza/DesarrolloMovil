package com.example.proyectofinal;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class MyUtils {
    public static void guardarEnBaseDeDatos(Context context, ContentValues registro) {
        com.example.proyectofinal.AdminSQLiteOpenHelper admin = new com.example.proyectofinal.AdminSQLiteOpenHelper(context, "proyecto_final",null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();

        bd.insert("auth",null, registro);
//        bd.close();
    }
}
