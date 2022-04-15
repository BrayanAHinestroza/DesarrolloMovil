package com.example.proyectofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.example.proyectofinal.ui.HomeEstudiante;
import com.example.proyectofinal.ui.HomeProfesor;
import com.example.proyectofinal.ui.Login;
import com.example.proyectofinal.ui.adapter.CustomNotifyProfesorAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class HomeProfesorCursoDetalle extends AppCompatActivity {
    private RequestQueue mQueue;
    private TextView tv_home_profesor_curso_detalle_nombre,
            tv_home_profesor_curso_detalle_grupo,
            tv_home_profesor_curso_detalle_fechaI,
            tv_home_profesor_curso_detalle_fechaF,
            tv_home_profesor_curso_detalle_fechaIP,
            tv_home_profesor_curso_detalle_fechaFP,
            tv_home_profesor_curso_detalle_descripcion,
            tv_home_profesor_curso_detalle_horario,
            tv_home_profesor_curso_detalle_profesor,
            tv_home_profesor_curso_detalle_nroEstudiantes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_profesor_curso_detalle);

        tv_home_profesor_curso_detalle_nombre = (TextView) findViewById(R.id.tv_home_profesor_curso_detalle_nombre);
        tv_home_profesor_curso_detalle_grupo = (TextView)findViewById(R.id.tv_home_profesor_curso_detalle_grupo);
        tv_home_profesor_curso_detalle_fechaI =(TextView) findViewById(R.id.tv_home_profesor_curso_detalle_fechaI);
        tv_home_profesor_curso_detalle_fechaF = (TextView)findViewById(R.id.tv_home_profesor_curso_detalle_fechaF);
        tv_home_profesor_curso_detalle_fechaIP = (TextView)findViewById(R.id.tv_home_profesor_curso_detalle_fechaIP);
        tv_home_profesor_curso_detalle_fechaFP = (TextView)findViewById(R.id.tv_home_profesor_curso_detalle_fechaFP);
        tv_home_profesor_curso_detalle_descripcion =(TextView) findViewById(R.id.tv_home_profesor_curso_detalle_descripcion);
        tv_home_profesor_curso_detalle_horario =(TextView) findViewById(R.id.tv_home_profesor_curso_detalle_horario);
        tv_home_profesor_curso_detalle_profesor = (TextView)findViewById(R.id.tv_home_profesor_curso_detalle_profesor);
        tv_home_profesor_curso_detalle_nroEstudiantes =(TextView) findViewById(R.id.tv_home_profesor_curso_detalle_nroEstudiantes);

        try {
            obtenerDetalleCurso();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void obtenerDetalleCurso() throws JSONException {
        Intent myIntent = getIntent();
        String id_curso = myIntent.getStringExtra("id_curso");

        ArrayList<String> detalleCurso = new ArrayList<>();
        String token = MyUtils.obtenerToken(this);

        JSONObject data = new JSONObject();
        data.put("token", token);
        data.put("id_curso", id_curso);
        final String requestBody = data.toString();

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                MyUtils.API_URL + "profesor/clase",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);

//                            for(int i = 0; i < jsonResponse.length(); i++){
//                                detalleCurso.add(jsonResponse.getString(i));
//                            }

                            System.out.println("HERE ->>>>>>>>>>>>>>>>>>>>>>>>>>");
//                            System.out.println(detalleCurso.get(0));
                            System.out.println(jsonResponse);

                            tv_home_profesor_curso_detalle_nombre.setText(jsonResponse.getString("nombre"));
                            tv_home_profesor_curso_detalle_grupo.setText(jsonResponse.getString("id_curso"));
                            tv_home_profesor_curso_detalle_fechaI.setText(jsonResponse.getString("fecha_inicio").substring(0,10));
                            tv_home_profesor_curso_detalle_fechaF.setText(jsonResponse.getString("fecha_fin").substring(0,10));
                            tv_home_profesor_curso_detalle_fechaIP.setText(jsonResponse.getString("fecha_inicio_parciales").substring(0,10));
                            tv_home_profesor_curso_detalle_fechaFP.setText(jsonResponse.getString("fecha_fin_parciales").substring(0,10));
                            tv_home_profesor_curso_detalle_descripcion.setText(jsonResponse.getString("descripcion"));
                            tv_home_profesor_curso_detalle_horario.setText(jsonResponse.getString("horarios"));
                            tv_home_profesor_curso_detalle_profesor.setText(jsonResponse.getString("nombre_profesor"));
                            tv_home_profesor_curso_detalle_nroEstudiantes.setText(jsonResponse.getString("nro_usuarios"));

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
}