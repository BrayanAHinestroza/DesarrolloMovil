package com.example.proyectofinal.ui.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectofinal.HomeProfesorCursoDetalle;
import com.example.proyectofinal.HomeProfesorCursoEstudiantes;
import com.example.proyectofinal.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CustomClaseProfesorAdapter extends RecyclerView.Adapter<CustomClaseProfesorAdapter.MyViewHolder> {
    ArrayList<String> list;

    public CustomClaseProfesorAdapter(ArrayList<String> list){
        this.list = list;
    }

    @NonNull

    @Override
    public CustomClaseProfesorAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CustomClaseProfesorAdapter.MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_home_profesor_curso, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomClaseProfesorAdapter.MyViewHolder holder, int position) {
        try {
            JSONObject data = new JSONObject(list.get(position));

            holder.tv_profesor_home_curso_title.setText(data.getString("nombre"));
            holder.tv_profesor_home_curso_nrogrupo.setText("Curso #" + data.getString("id_curso"));

            holder.btnHomeProfesorCursoEstudiantes.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(holder.btnHomeProfesorCursoEstudiantes.getContext(), HomeProfesorCursoEstudiantes.class);
                    view.getContext().startActivity(intent);
                }
            });

            holder.btnHomeProfesorCursoDetalle.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(holder.btnHomeProfesorCursoDetalle.getContext(), HomeProfesorCursoDetalle.class);
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
        TextView tv_profesor_home_curso_title, tv_profesor_home_curso_nrogrupo;
        Button btnHomeProfesorCursoEstudiantes, btnHomeProfesorCursoDetalle;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_profesor_home_curso_title=itemView.findViewById(R.id.tv_profesor_home_curso_title);
            tv_profesor_home_curso_nrogrupo=itemView.findViewById(R.id.tv_profesor_home_curso_nrogrupo);
            btnHomeProfesorCursoEstudiantes = itemView.findViewById(R.id.btn_home_profesor_curso_estudiantes);
            btnHomeProfesorCursoDetalle = itemView.findViewById(R.id.btn_home_profesor_curso_detalle);
        }
    }
}
