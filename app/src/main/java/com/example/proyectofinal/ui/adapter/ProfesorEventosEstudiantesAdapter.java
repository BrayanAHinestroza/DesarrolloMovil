package com.example.proyectofinal.ui.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectofinal.ProfesorEventosEstudiantes;
import com.example.proyectofinal.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ProfesorEventosEstudiantesAdapter extends RecyclerView.Adapter<ProfesorEventosEstudiantesAdapter.MyViewHolder> {
    ArrayList<String> list;

    public ProfesorEventosEstudiantesAdapter(ArrayList<String> list){
        this.list = list;
    }

    @NonNull
    @Override
    public ProfesorEventosEstudiantesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProfesorEventosEstudiantesAdapter.MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_profesor_eventos_estudiantes, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        try {
            JSONObject data = new JSONObject(list.get(position));
            holder.tv_profesor_home_eventos_estudiante_nombre.setText(data.getString("nombre_completo"));
            holder.tv_profesor_home_eventos_estudiante_email.setText(data.getString("email"));
            holder.tv_profesor_home_eventos_estudiante_programa.setText(data.getString("nombre_programa"));
            holder.tv_profesor_home_eventos_estudiante_fecha_confirm.setText(data.getString("fecha_confirmacion").substring(0,10));
            holder.tv_profesor_home_eventos_estudiante_rol.setText(data.getString("nombre"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tv_profesor_home_eventos_estudiante_nombre, tv_profesor_home_eventos_estudiante_email, tv_profesor_home_eventos_estudiante_programa, tv_profesor_home_eventos_estudiante_fecha_confirm, tv_profesor_home_eventos_estudiante_rol;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_profesor_home_eventos_estudiante_nombre=itemView.findViewById(R.id.tv_profesor_home_eventos_estudiante_nombre);
            tv_profesor_home_eventos_estudiante_email=itemView.findViewById(R.id.tv_profesor_home_eventos_estudiante_email);
            tv_profesor_home_eventos_estudiante_programa = itemView.findViewById(R.id.tv_profesor_home_eventos_estudiante_programa);
            tv_profesor_home_eventos_estudiante_fecha_confirm = itemView.findViewById(R.id.tv_profesor_home_eventos_estudiante_fecha_confirm);
            tv_profesor_home_eventos_estudiante_rol = itemView.findViewById(R.id.tv_profesor_home_eventos_estudiante_rol);
        }
    }
}
