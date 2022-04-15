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
import org.w3c.dom.Text;

import java.util.ArrayList;

public class HomeProfesorCursoEstudiantesAdapter extends RecyclerView.Adapter<HomeProfesorCursoEstudiantesAdapter.ViewHolder> {
    private ArrayList<String> list;
    private RecyclerViewClickListener listener;

    public HomeProfesorCursoEstudiantesAdapter(ArrayList<String> list,  RecyclerViewClickListener listener){
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_home_profesor_curso_estudiantes, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeProfesorCursoEstudiantesAdapter.ViewHolder holder, int position) {
        try {
            JSONObject data = new JSONObject(list.get(position));
            String nombreUser = data.getString("nombre") + " " + data.getString("apellido");
            holder.tv_profesor_home_curso_estudiante_nombre.setText(nombreUser);
            holder.tv_profesor_home_curso_estudiante_id.setText(data.getString("id_usuario"));
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
        TextView tv_profesor_home_curso_estudiante_nombre,tv_profesor_home_curso_estudiante_id;
        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            tv_profesor_home_curso_estudiante_nombre=itemView.findViewById(R.id.tv_profesor_home_curso_estudiante_nombre);
            tv_profesor_home_curso_estudiante_id=itemView.findViewById(R.id.tv_profesor_home_curso_estudiante_id);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onClick(view, getBindingAdapterPosition());
        }
    }
}
