package com.example.proyectofinal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.proyectofinal.ui.adapter.CustomNotifyProfesorAdapter;
import com.example.proyectofinal.ui.adapter.HomeProfesorCursoEstudiantesAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class HomeProfesorCursoEstudiantes extends AppCompatActivity {
    private RequestQueue mQueue;
    TextView tvCurso;
    private RecyclerView estudiantesRecyclerView;
    private HomeProfesorCursoEstudiantesAdapter.RecyclerViewClickListener listener;
    public ArrayList<String> estudiantes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_profesor_curso_estudiantes);

        tvCurso = (TextView) findViewById(R.id.tvNumeroCurso);
        estudiantesRecyclerView = findViewById(R.id.recyclerViewHomeProfesorCursoEstudiantes);
        estudiantesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        try {
            JSONObject data = new JSONObject();
            Intent myIntent = getIntent();
            String id_curso = myIntent.getStringExtra("id_curso");
            data.put("id_curso", id_curso);
            tvCurso.setText("DESARROLLO MOVIL - #" + id_curso);

            final String requestBody = data.toString();
            StringRequest stringRequest = new StringRequest(
                    Request.Method.POST,
                    MyUtils.API_URL + "clases/estudiantes",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONArray jsonResponse = new JSONArray(response);
                                for(int i = 0; i < jsonResponse.length(); i++){
                                    estudiantes.add(jsonResponse.getString(i));
                                }
                                setOnClickListener();
                                estudiantesRecyclerView.setAdapter(new HomeProfesorCursoEstudiantesAdapter(estudiantes, listener));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("LOG_VOLLEY", error.toString());
                        }
                    }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return requestBody == null ? null : requestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                        return null;
                    }
                }
            };
            mQueue = Volley.newRequestQueue(this);
            mQueue.add(stringRequest);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setOnClickListener() {
        listener = new HomeProfesorCursoEstudiantesAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                Intent intent = new Intent(getApplicationContext(), DetalleEstudiante.class);
                intent.putExtra("id_estudiante",estudiantes.get(position));
                startActivity(intent);
            }
        };
    }
}