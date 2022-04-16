package com.example.proyectofinal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.proyectofinal.ui.Login;
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
    EditText etSearchByCodigoListadoE;
    ProgressBar progressBarListadoEstudiantes;
    Button btn_home_profesor_curso_estudiantes_novedades, btn_home_profesor_curso_estudiantes_all;
    private RecyclerView estudiantesRecyclerView;
    private HomeProfesorCursoEstudiantesAdapter.RecyclerViewClickListener listener;
    public ArrayList<String> estudiantes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_profesor_curso_estudiantes);

        tvCurso = (TextView) findViewById(R.id.tvNumeroCurso);
        etSearchByCodigoListadoE = (EditText) findViewById(R.id.etSearchByCodigoListadoE);
        progressBarListadoEstudiantes = (ProgressBar) findViewById(R.id.progressBarListadoEstudiantes);
        btn_home_profesor_curso_estudiantes_all = (Button)findViewById(R.id.btn_home_profesor_curso_estudiantes_all);
        btn_home_profesor_curso_estudiantes_novedades = (Button)findViewById(R.id.btn_home_profesor_curso_estudiantes_novedades);
        progressBarListadoEstudiantes.setVisibility(View.GONE);
        estudiantesRecyclerView = findViewById(R.id.recyclerViewHomeProfesorCursoEstudiantes);
        estudiantesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        try {
            JSONObject data = new JSONObject();
            Intent myIntent = getIntent();
            String id_curso = myIntent.getStringExtra("id_curso");
            String nombre_curso = myIntent.getStringExtra("nombre_curso");
            data.put("id_curso", id_curso);
            tvCurso.setText(nombre_curso.toUpperCase() +" - #" + id_curso);

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

    public void onSearchByCodigo(View view){
        estudiantesRecyclerView.setVisibility(View.INVISIBLE);
        progressBarListadoEstudiantes.setVisibility(View.VISIBLE);

        try {
            JSONObject data = new JSONObject();
            Intent myIntent = getIntent();
            String id_curso = myIntent.getStringExtra("id_curso");
            String nombre_curso = myIntent.getStringExtra("nombre_curso");
            data.put("id_curso", id_curso);
            data.put("filtro_codigo", etSearchByCodigoListadoE.getText().toString());
            tvCurso.setText(nombre_curso.toUpperCase() +" - #" + id_curso);

            final String requestBody = data.toString();
            StringRequest stringRequest = new StringRequest(
                    Request.Method.POST,
                    MyUtils.API_URL + "clases/estudiantes",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                estudiantes.clear();
                                JSONArray jsonResponse = new JSONArray(response);
                                for(int i = 0; i < jsonResponse.length(); i++){
                                    estudiantes.add(jsonResponse.getString(i));
                                }
                                setOnClickListener();
                                estudiantesRecyclerView.setAdapter(new HomeProfesorCursoEstudiantesAdapter(estudiantes, listener));
                            } catch (JSONException e) {
                                Toast.makeText(HomeProfesorCursoEstudiantes.this,e.getMessage(),Toast.LENGTH_LONG).show();
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
        } finally {
            estudiantesRecyclerView.setVisibility(View.VISIBLE);
            progressBarListadoEstudiantes.setVisibility(View.INVISIBLE);
        }
    }

    public void onChangeToNovedades(View view){
        btn_home_profesor_curso_estudiantes_novedades.setVisibility(View.VISIBLE);
        btn_home_profesor_curso_estudiantes_novedades.setEnabled(true);
        btn_home_profesor_curso_estudiantes_all.setVisibility(View.GONE);
        btn_home_profesor_curso_estudiantes_all.setEnabled(false);
    }

    public void onChangeToEstudiantes(View view){
        btn_home_profesor_curso_estudiantes_all.setVisibility(View.VISIBLE);
        btn_home_profesor_curso_estudiantes_all.setEnabled(true);
        btn_home_profesor_curso_estudiantes_novedades.setVisibility(View.GONE);
        btn_home_profesor_curso_estudiantes_novedades.setEnabled(false);
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