package com.example.proyectofinal.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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
import com.example.proyectofinal.MyUtils;
import com.example.proyectofinal.R;
import com.example.proyectofinal.ui.adapter.LinkedHashMapAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HomeProfesorAddNotify extends AppCompatActivity {
    private EditText tipo_novedad, comentarios_novedad;
    private TextView key_spinner_selected;
    private Button btn_registrar;
    private Spinner spinner;
    private RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_profesor_notify_add);

        try {
            cargarDatosSpinner();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        tipo_novedad = (EditText) findViewById(R.id.edt_tipo_novedad);
        comentarios_novedad = (EditText) findViewById(R.id.edt_obs_novedad);
        btn_registrar = (Button) findViewById(R.id.btn_registrar_novedad);
        spinner = (Spinner) findViewById(R.id.spinner_clase);
        key_spinner_selected = (TextView) findViewById(R.id.key_spinner_selected);
    }

    private void cargarDatosSpinner() throws JSONException {
        String token = MyUtils.obtenerToken(this);
        JSONObject data = new JSONObject();
        data.put("token", token);

        final String requestBody = data.toString();

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                MyUtils.API_URL + "profesor/clases",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            ArrayList<String> cursos = new ArrayList<>();
                            LinkedHashMap<String, String> mapData = new LinkedHashMap<String, String>();

                            JSONArray jsonResponse = new JSONArray(response);
                            for(int i = 0; i < jsonResponse.length(); i++){
                                JSONObject temp = new JSONObject(jsonResponse.getString(i));
                                mapData.put(temp.getString("id_curso"), temp.getString("nombre"));
                            }

                            LinkedHashMapAdapter<String, String> adapter =
                                    new LinkedHashMapAdapter<String, String>(
                                            HomeProfesorAddNotify.this, android.R.layout.simple_spinner_item, mapData);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                            spinner.setAdapter(adapter);
                            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                    Map.Entry<String, String> item = (Map.Entry<String, String>) spinner.getSelectedItem();
                                    String key = item.getKey();

                                    key_spinner_selected.setText(key);
                                }
                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {

                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("LOG_VOLLEY", error.toString());
                        Toast.makeText(HomeProfesorAddNotify.this,error.toString(),Toast.LENGTH_LONG).show();
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
        String tipoNovedad = tipo_novedad.getText().toString();
        String comentariosNovedad = comentarios_novedad.getText().toString();
        String claseNovedad = key_spinner_selected.getText().toString();
        try {
            String token = MyUtils.obtenerToken(this);
            JSONObject data = new JSONObject();
            data.put("tipoNovedad",tipoNovedad.toUpperCase());
            data.put("comentariosNovedad",comentariosNovedad);
            data.put("claseNovedad",claseNovedad);
            data.put("token",token);

            final String requestBody = data.toString();

            StringRequest stringRequest = new StringRequest(
                    Request.Method.POST,
                    MyUtils.API_URL + "profesor/novedades_clase/add",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Intent intent = new Intent(HomeProfesorAddNotify.this, HomeProfesor.class);
                            startActivity(intent);                            
                            Toast.makeText(HomeProfesorAddNotify.this,"CREADO CORRECTAMENTE",Toast.LENGTH_LONG).show();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("LOG_VOLLEY", error.toString());
                            Toast.makeText(HomeProfesorAddNotify.this,"No se encontro ningún curso asociado",Toast.LENGTH_LONG).show();
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