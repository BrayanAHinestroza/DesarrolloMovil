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
import com.example.proyectofinal.ui.adapter.HomeProfesorCursoEstudiantesAdapter;
import com.example.proyectofinal.ui.adapter.ProfesorEventosEstudiantesAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class ProfesorEventosEstudiantes extends AppCompatActivity {
    private RequestQueue mQueue;
    private RecyclerView estudiantesRecyclerView;
    TextView tvCurso, tvTitulo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profesor_eventos_estudiantes);

        tvCurso = (TextView) findViewById(R.id.tvNumeroCurso);
        tvTitulo = (TextView) findViewById(R.id.tv_home_profesor_curso_estudiantes_title);

        estudiantesRecyclerView = findViewById(R.id.recyclerViewHomeProfesorEventosEstudiantes);

        estudiantesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        getData();
    }

    private void getData(){
        try {
            Intent myIntent = getIntent();
            String id_evento_curso = myIntent.getStringExtra("id_evento_curso");

            JSONObject data = new JSONObject();
            data.put("id_evento_curso", id_evento_curso);

            final String requestBody = data.toString();
            StringRequest stringRequest = new StringRequest(
                    Request.Method.POST,
                    MyUtils.API_URL + "clases/eventos/asistentes",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                ArrayList<String> estudiantes = new ArrayList<>();
                                JSONArray jsonResponse = new JSONArray(response);

                                for(int i = 0; i < jsonResponse.length(); i++){
                                    estudiantes.add(jsonResponse.getString(i));
                                }

                                estudiantesRecyclerView.setAdapter(new ProfesorEventosEstudiantesAdapter(estudiantes));
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
}