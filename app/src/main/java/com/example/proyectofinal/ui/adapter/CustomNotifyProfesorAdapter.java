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

public class CustomNotifyProfesorAdapter extends RecyclerView.Adapter<CustomNotifyProfesorAdapter.MyViewHolder> {
    ArrayList<String> list;

    public CustomNotifyProfesorAdapter(ArrayList<String> list){
        this.list = list;
    }

    @NonNull

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_home_profesor_notify, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        try {
            JSONObject data = new JSONObject(list.get(position));

            holder.tv_profesor_home_notify_title.setText(data.getString("tipo_novedad"));
            holder.tv_profesor_home_notify_date.setText(data.getString("fecha_novedad").substring(0,10));
            holder.tv_profesor_home_notify_description.setText(data.getString("observaciones"));
            holder.tv_profesor_home_notify_clase.setText(data.getString("nombre_curso"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tv_profesor_home_notify_title, tv_profesor_home_notify_date, tv_profesor_home_notify_description, tv_profesor_home_notify_clase ;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_profesor_home_notify_title=itemView.findViewById(R.id.tv_profesor_home_notify_title);
            tv_profesor_home_notify_date=itemView.findViewById(R.id.tv_profesor_home_notify_date);
            tv_profesor_home_notify_description=itemView.findViewById(R.id.tv_profesor_home_notify_description);
            tv_profesor_home_notify_clase=itemView.findViewById(R.id.tv_profesor_home_notify_clase);
        }
    }

}

