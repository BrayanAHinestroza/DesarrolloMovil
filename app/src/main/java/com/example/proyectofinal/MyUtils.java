package com.example.proyectofinal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.proyectofinal.io.AdminSQLiteOpenHelper;

public class MyUtils {

    public static final String API_URL = "http://192.168.1.98:5000/";


    public static void guardarEnBaseDeDatos(Context context, ContentValues registro) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(context, "proyecto_final",null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();

        bd.insert("auth",null, registro);
        bd.close();
    }

    public static String obtenerToken(Context context){
        String token = "";
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(context, "proyecto_final",null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();

        Cursor fila = bd.rawQuery("select * from auth", null);
        if(fila.moveToFirst()){
            token = fila.getString(0);
        }
        bd.close();
        return token;
    }

    public static String obtenerRol(Context context){
        String rol = "";
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(context, "proyecto_final",null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();

        Cursor fila = bd.rawQuery("select * from auth", null);
        if(fila.moveToFirst()){
            rol = fila.getString(1);
        }
        bd.close();
        return rol;
    }

    public static String obtenerUsername(Context context){
        String username = "";
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(context, "proyecto_final",null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();

        Cursor fila = bd.rawQuery("select * from auth", null);
        if(fila.moveToFirst()){
            username = fila.getString(2);
        }
        bd.close();
        return username;
    }
}
