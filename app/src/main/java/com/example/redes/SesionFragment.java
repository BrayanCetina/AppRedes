package com.example.redes;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class SesionFragment extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener{



    RequestQueue rq;
    JsonRequest jrq;
    EditText txtUser, txtPwd;
    Button btnSesion;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_sesion, container, false);
        View vista= inflater.inflate(R.layout.fragment_sesion, container, false);
        txtUser = (EditText) vista.findViewById(R.id.txtUser);
        txtPwd = (EditText) vista.findViewById(R.id.txtPwd);
        btnSesion=(Button) vista.findViewById(R.id.btnSesion);
        rq= Volley.newRequestQueue(getContext());
        btnSesion.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                iniciarSesion();
            }
        });
        return vista;

    }



    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getContext(),"No se pudo conectar"+error.toString(),Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        Toast.makeText(getContext(),"Se ha encontrado "+txtUser.toString(),Toast.LENGTH_LONG).show();
        User usuario=new User();
        JSONArray jsonArray=response.optJSONArray("datos");
        JSONObject jsonObject=null;
        try {
            jsonObject=jsonArray.getJSONObject(0);
            usuario.setUser(jsonObject.optString("user"));
            usuario.setUser(jsonObject.optString("pwd"));
            usuario.setUser(jsonObject.optString("names"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Intent menu=new Intent(getContext(),Main2Activity.class);
        startActivity(menu);


    }
    public void iniciarSesion(){
        String url="https://evidential-tubing.000webhostapp.com/usuario_cliente.php?user="+txtUser.getText().toString()+"$pwd="+txtPwd.getText().toString();
        jrq =new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        rq.add(jrq);
    }
}
