package com.example.proyectofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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
import com.example.proyectofinal.ui.HomeProfesor;
import com.example.proyectofinal.ui.HomeProfesorAddNotify;
import com.example.proyectofinal.ui.adapter.HomeProfesorCursoEstudiantesAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class DetalleEstudiante extends AppCompatActivity {
    private RequestQueue mQueue;
    private TextView tvId, tvNombre, tvUsername, tvCodigo, tvTipoDocumento, tvNumeroDocumento, tvCorreoElectronico, tvFacultad, tvPrograma;
    private EditText etTipoNovedad, etObservacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_estudiante);
        tvNombre = (TextView) findViewById(R.id.detalle_estudiante_nombre);
        tvUsername = (TextView) findViewById(R.id.detalle_estudiante_username);
        tvCodigo = (TextView) findViewById(R.id.tv_detalle_estudiante_codigo);
        tvTipoDocumento = (TextView) findViewById(R.id.tv_detalle_estudiante_tipoDocumento);
        tvNumeroDocumento = (TextView) findViewById(R.id.tv_detalle_estudiante_numeroDocumento);
        tvCorreoElectronico = (TextView) findViewById(R.id.tv_detalle_estudiante_correo);
        tvFacultad = (TextView) findViewById(R.id.tv_detalle_estudiante_facultad);
        tvPrograma = (TextView) findViewById(R.id.tv_detalle_estudiante_programa);
        tvId = (TextView) findViewById(R.id.tv_detalle_estudiante_id);
        etTipoNovedad = (EditText) findViewById(R.id.edt_detalle_estudiante_tipoNovedad);
        etObservacion = (EditText) findViewById(R.id.edt_detalle_estudiante_obsNovedad);

        Intent myIntent = getIntent();
        String id_estudiante = myIntent.getStringExtra("id_estudiante");
        System.out.println(id_estudiante);

        try {
            getDataEstudiante(id_estudiante);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void getDataEstudiante(String datos) throws JSONException {
        String token = MyUtils.obtenerToken(this);

        JSONObject data = new JSONObject();
        data.put("token", token);
        data.put("data", datos);

        final String requestBody = data.toString();

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                MyUtils.API_URL + "clases/estudiante",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            String nombreEstudiante = jsonResponse.getString("nombre") + " " + jsonResponse.getString("apellido");
                            tvId.setText(jsonResponse.getString("id_usuario"));
                            tvNombre.setText(nombreEstudiante);
                            tvUsername.setText(jsonResponse.getString("username"));
                            tvCodigo.setText(jsonResponse.getString("codigo"));
                            tvTipoDocumento.setText(jsonResponse.getString("tipo_documento"));
                            tvNumeroDocumento.setText(jsonResponse.getString("numero_documento"));
                            tvCorreoElectronico.setText(jsonResponse.getString("email"));
                            tvFacultad.setText(jsonResponse.getString("facultad"));
                            tvPrograma.setText(jsonResponse.getString("programa"));
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
    }

    public void onClicRegistrarNovedad(View view){
        String tipoNovedad = etTipoNovedad.getText().toString();
        String comentariosNovedad = etObservacion.getText().toString();
        String idEstudiante = tvId.getText().toString();

        try {
            String token = MyUtils.obtenerToken(this);
            JSONObject data = new JSONObject();
            data.put("tipoNovedad", tipoNovedad.toUpperCase());
            data.put("comentariosNovedad", comentariosNovedad);
            data.put("id_estudiante", idEstudiante);
            data.put("token", token);

            final String requestBody = data.toString();

            StringRequest stringRequest = new StringRequest(
                    Request.Method.POST,
                    MyUtils.API_URL + "profesor/novedades_estudiante/add",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            etTipoNovedad.setText("");
                            etObservacion.setText("");
                            Toast.makeText(DetalleEstudiante.this,"CREADO CORRECTAMENTE",Toast.LENGTH_LONG).show();
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