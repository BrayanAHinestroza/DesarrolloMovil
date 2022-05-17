package com.example.proyectofinal.ui.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectofinal.DetalleNovedadProfesor;
import com.example.proyectofinal.HomeProfesorCursoDetalle;
import com.example.proyectofinal.HomeProfesorCursoEstudiantes;
import com.example.proyectofinal.ProfesorEventosEstudiantes;
import com.example.proyectofinal.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CustomEventosProfesorAdapter extends RecyclerView.Adapter<CustomEventosProfesorAdapter.MyViewHolder> {
    ArrayList<String> list;

    public CustomEventosProfesorAdapter(ArrayList<String> list){
        this.list = list;
    }

    @NonNull
    @Override
    public CustomEventosProfesorAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CustomEventosProfesorAdapter.MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_home_profesor_eventos, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        try {
            JSONObject data = new JSONObject(list.get(position));
            holder.tv_profesor_home_evento_title.setText(data.getString("nombre_evento"));
            holder.tv_profesor_home_evento_fecha.setText(data.getString("fecha").substring(0,10));
            holder.tv_profesor_home_evento_hora.setText(data.getString("hora").substring(0,5));
            holder.tv_profesor_home_evento_descripcion.setText(data.getString("nombre_lugar"));

            holder.btn_home_profesor_evento_estudiantes.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(holder.btn_home_profesor_evento_estudiantes.getContext(), ProfesorEventosEstudiantes.class);
                    try {
                        intent.putExtra("id_evento_curso", data.getString("id_evento_curso"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    view.getContext().startActivity(intent);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tv_profesor_home_evento_title, tv_profesor_home_evento_fecha, tv_profesor_home_evento_hora, tv_profesor_home_evento_descripcion;
        Button btn_home_profesor_evento_estudiantes;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_profesor_home_evento_title=itemView.findViewById(R.id.tv_profesor_home_evento_title);
            tv_profesor_home_evento_fecha=itemView.findViewById(R.id.tv_profesor_home_evento_fecha);
            tv_profesor_home_evento_hora = itemView.findViewById(R.id.tv_profesor_home_evento_hora);
            tv_profesor_home_evento_descripcion = itemView.findViewById(R.id.tv_profesor_home_evento_descripcion);
            btn_home_profesor_evento_estudiantes = itemView.findViewById(R.id.btn_home_profesor_evento_estudiantes);
        }
    }
}