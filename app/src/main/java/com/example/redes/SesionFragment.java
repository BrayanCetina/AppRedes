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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.example.redes.encrip;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;


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
        encrip eso=new encrip();
        String user = eso.MD5(txtUser.getText().toString());
        String pass = eso.MD5(txtPwd.getText().toString());
        String url="https://evidential-tubing.000webhostapp.com/usuario_cliente.php";
        //jrq =new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        JSONObject request = new JSONObject();
        try
        {
            request.put("user", txtUser.getText().toString());
            request.put("pwd", txtPwd.getText().toString());
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        jrq =new JsonObjectRequest(Request.Method.POST, url,request, this                       , this);
        rq.add(jrq);
    }
}
