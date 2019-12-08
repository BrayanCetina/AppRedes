
package com.example.redes.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.redes.Adaptador;
import com.example.redes.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class HomeFragment extends Fragment {
    ListView lista;
    String[] contenido;
    String[] titulo;
    String[] IMGS;
    String[] idas;
    private HomePack homePack;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        homePack = ViewModelProviders.of(this).get(HomePack.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.textViewList);
        lista = (ListView) root.findViewById(R.id.lvItems);
        homePack.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);

            }
        });
        validarUsuario(getString(R.string.url)+"lista.php");
        return root;

    }

    //select usuario con nombre y contraseña en la base de datos
    private void validarUsuario(String URL) {
        //se envian los 3 parametros
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray ja = new JSONArray(response);
                    JSONObject jo = null;
                    idas = new String[ja.length()];
                    IMGS = new String[ja.length()];
                    titulo = new String[ja.length()];
                    contenido = new String[ja.length()];

                    for (int i = 0; i < ja.length(); i++) {
                        jo = ja.getJSONObject(i);
                        idas[i] = jo.getString("id");
                        IMGS[i] = jo.getString("url");
                        titulo[i] = jo.getString("titulo");
                        contenido[i] = jo.getString("contenido");
                    }
                    lista.setAdapter(new Adaptador(getActivity(), idas, IMGS,titulo,contenido));
                } catch (JSONException e) {
                    e.getMessage();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();//captura de rror


            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //obtendra los valres y se los dara a los parametros para ser ingresados en el php
                Map<String, String> parametros = new HashMap<String, String>();
                return parametros;

            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity()); //comunica con php
        requestQueue.add(stringRequest);
    }//fin private
}