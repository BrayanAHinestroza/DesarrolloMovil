package com.example.proyectofinal.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Login extends AppCompatActivity {
    private EditText edtUsername, edtPassword;
    private RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtUsername = (EditText) findViewById(R.id.edt_username);
        edtPassword = (EditText) findViewById(R.id.edt_password);
        mQueue = Volley.newRequestQueue(this);
    }

    public void onIniciarSesion(View view){
        String username = edtUsername.getText().toString();
        String password = edtPassword.getText().toString();

        JSONObject response = validarDatos(username, password);

        try {
            if (response.getBoolean("status")){
                iniciarSesion(username, password);
            }else{
                Toast.makeText(Login.this,"ERROR: " + response.getString("message").toString(),Toast.LENGTH_LONG).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void iniciarSesion(String username, String password) throws JSONException {
        JSONObject data = new JSONObject();

        data.put("username", username);
        data.put("password", password);
        final String requestBody = data.toString();

        StringRequest stringRequest = new StringRequest(
            Request.Method.POST,
                MyUtils.API_URL + "Login",
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);

                        if (jsonResponse.getInt("code") == 1) {
                            String rol = jsonResponse.getString("roles_id").toString();
                            String token = jsonResponse.getString("token").toString();

                            ContentValues registro = new ContentValues();
                            registro.put("rol", rol);
                            registro.put("token", token);
                            MyUtils.guardarEnBaseDeDatos(Login.this, registro);

                            if (rol.equals("2")){
                                Intent intent = new Intent(Login.this, HomeEstudiante.class);
                                startActivity(intent);
                            }else if (rol.equals("3")){
                                Intent intent = new Intent(Login.this, HomeProfesor.class);
                                startActivity(intent);
                            }
                            Toast.makeText(Login.this,"BIENVENIDO: " + username,Toast.LENGTH_LONG).show();
                        }

                        if (jsonResponse.getInt("code") == 2){
                            Toast.makeText(Login.this,"ERROR: " + jsonResponse.getString("message").toString(),Toast.LENGTH_LONG).show();
                        }

                        if (jsonResponse.getInt("code") == 3){
                            Toast.makeText(Login.this,"ERROR: " + jsonResponse.getString("message").toString(),Toast.LENGTH_LONG).show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("LOG_VOLLEY", error.toString());
                    Toast.makeText(Login.this,error.toString(),Toast.LENGTH_LONG).show();
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

        mQueue.add(stringRequest);
    }

    public JSONObject validarDatos(String username, String password){
        JSONObject response = new JSONObject();

        if (username.equals("") || password.equals("")) {
            try {
                response.put("status", false);
                response.put("message","Los campos username y password son obligatorios");
                return response;
            }catch (Exception ex){
                System.out.println(ex);
            }
        }

        if (!password.equals("")){
            /*
             * ^ represents starting character of the string.
             * (?=.*[0-9]) represents a digit must occur at least once.
             * (?=.*[a-z]) represents a lower case alphabet must occur at least once.
             * (?=.*[A-Z]) represents an upper case alphabet that must occur at least once.
             * (?=\\S+$) white spaces don’t allowed in the entire string.
             * .{8, 20} represents at least 8 characters and at most 20 characters.
             * $ represents the end of the string.
             */
            String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,20}$";
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(password);

            try {
                if (!m.matches()){
                    response.put("status", false);
                    response.put("message","La contraseña no es valida");
                    return response;
                }
            }catch (Exception ex){
                System.out.println(ex);
            }
        }

        if (!username.equals("")){
            /*
             * ^ represents starting character of the string.
             * ([0-9]) represents a digit.
             * ([a-z]) represents a lower case alphabet.
             * ([A-Z]) represents an upper case alphabet.
             * (?=\\S+$) white spaces don’t allowed in the entire string.
             * .{3, 20} represents at least 3 characters and at most 20 characters.
             * $ represents the end of the string.
             */
            String regex = "^[a-zA-Z0-9].{2,19}$";
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(username);

            try {
                if (!m.matches()){
                    response.put("status", false);
                    response.put("message","El username no es valida");
                    return response;
                }
            }catch (Exception ex){
                System.out.println(ex);
            }
        }

        try {
            response.put("status", true);
        }catch (Exception ex){
            System.out.println(ex);
        }
        return response;
    }
}