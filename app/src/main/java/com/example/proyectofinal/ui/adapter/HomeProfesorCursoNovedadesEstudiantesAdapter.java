package com.example.proyectofinal.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectofinal.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HomeProfesorCursoNovedadesEstudiantesAdapter extends RecyclerView.Adapter<HomeProfesorCursoNovedadesEstudiantesAdapter.ViewHolder> {
    private ArrayList<String> list;
    private RecyclerViewClickListener listener;

    public HomeProfesorCursoNovedadesEstudiantesAdapter(ArrayList<String> list,  RecyclerViewClickListener listener){
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_home_profesor_curso_novedades_estudiantes, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeProfesorCursoNovedadesEstudiantesAdapter.ViewHolder holder, int position) {
        try {
            JSONObject data = new JSONObject(list.get(position));
            String nombreUser = data.getString("nombre") + " " + data.getString("apellido");
            String username = data.getString("codigo") + " - " + nombreUser;
            holder.tv_profesor_home_curso_novedad_estudiante_titulo.setText(data.getString("tipo_novedad"));
            holder.tv_profesor_home_curso_novedad_estudiante_nombre.setText(username);
            holder.tv_profesor_home_curso_novedad_id_novedad.setText(data.getString("id_novedad"));
            holder.tv_profesor_home_curso_novedad_estudiante_fecha.setText(data.getString("fecha_novedad").substring(0,10));
            holder.tv_profesor_home_curso_novedad_estudiante_desc.setText(data.getString("observaciones"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface RecyclerViewClickListener{
        void onClick(View v, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tv_profesor_home_curso_novedad_estudiante_titulo,
                tv_profesor_home_curso_novedad_estudiante_nombre,
                tv_profesor_home_curso_novedad_estudiante_fecha,
                tv_profesor_home_curso_novedad_estudiante_desc,
                tv_profesor_home_curso_novedad_id_novedad;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            tv_profesor_home_curso_novedad_estudiante_titulo=itemView.findViewById(R.id.tv_profesor_home_curso_novedad_estudiante_titulo);
            tv_profesor_home_curso_novedad_estudiante_nombre=itemView.findViewById(R.id.tv_profesor_home_curso_novedad_estudiante_nombre);
            tv_profesor_home_curso_novedad_estudiante_fecha=itemView.findViewById(R.id.tv_profesor_home_curso_novedad_estudiante_fecha);
            tv_profesor_home_curso_novedad_estudiante_desc=itemView.findViewById(R.id.tv_profesor_home_curso_novedad_estudiante_desc);
            tv_profesor_home_curso_novedad_id_novedad=itemView.findViewById(R.id.tv_profesor_home_curso_novedad_id_novedad);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onClick(view, getBindingAdapterPosition());
        }
    }
}