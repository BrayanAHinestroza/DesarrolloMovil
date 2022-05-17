package com.example.proyectofinal.ui.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.proyectofinal.MyUtils;
import com.example.proyectofinal.QR_Scanner;
import com.example.proyectofinal.R;
import com.example.proyectofinal.ui.HomeProfesorAddNotify;
import com.example.proyectofinal.ui.adapter.CustomEventosProfesorAdapter;
import com.example.proyectofinal.ui.adapter.CustomNotifyProfesorAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class EventosProfesorFragment extends Fragment {
    private RequestQueue mQueue;
    private Button btnHomeProfesorReadQR;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.home_profesor_eventos_fragment,container,false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewHomeProfesorEventos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        btnHomeProfesorReadQR = (Button) view.findViewById(R.id.btn_home_profesor_read_qr);

        btnHomeProfesorReadQR.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), QR_Scanner.class);
                startActivity(intent);
            }
        });

        try {
            ArrayList<String> eventos = new ArrayList<>();
            String token = MyUtils.obtenerToken(getContext());
            JSONObject data = new JSONObject();
            data.put("token", token);

            final String requestBody = data.toString();

            StringRequest stringRequest = new StringRequest(
                    Request.Method.POST,
                    MyUtils.API_URL + "clases/eventos",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONArray jsonResponse = new JSONArray(response);
                                for(int i = 0; i < jsonResponse.length(); i++){
                                    eventos.add(jsonResponse.getString(i));
                                }

                                recyclerView.setAdapter(new CustomEventosProfesorAdapter(eventos));

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
            mQueue = Volley.newRequestQueue(getContext());
            mQueue.add(stringRequest);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return view;
    }
}
