package com.example.proyectofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_profesor_curso_detalle);

//        try {
//            obtenerDetalleCurso();
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
    }

//    private void obtenerDetalleCurso() throws JSONException {
//        Intent myIntent = getIntent();
//        String id_curso = myIntent.getStringExtra("id_curso");
//
//        ArrayList<String> detalleCurso = new ArrayList<>();
//        String token = MyUtils.obtenerToken(this);
//
//        JSONObject data = new JSONObject();
//        data.put("token", token);
//        data.put("id_curso", id_curso);
//        final String requestBody = data.toString();
//
//        StringRequest stringRequest = new StringRequest(
//                Request.Method.POST,
//                Login.API_URL + "profesor/curso",
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        try {
//                            JSONArray jsonResponse = new JSONArray(response);
//                            for(int i = 0; i < jsonResponse.length(); i++){
//                                detalleCurso.add(jsonResponse.getString(i));
//                            }
//
//                            System.out.println(detalleCurso);
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Log.e("LOG_VOLLEY", error.toString());
//                    }
//                }) {
//            @Override
//            public String getBodyContentType() {
//                return "application/json; charset=utf-8";
//            }
//
//            @Override
//            public byte[] getBody() throws AuthFailureError {
//                try {
//                    return requestBody == null ? null : requestBody.getBytes("utf-8");
//                } catch (UnsupportedEncodingException uee) {
//                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
//                    return null;
//                }
//            }
//        };
//
//        mQueue.add(stringRequest);
//    }
}