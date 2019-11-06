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


public class SesionFragment extends Fragment {




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
        btnSesion.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                iniciarSesion();
            }
        });
        return vista;

    }


    public void iniciarSesion(){
        encrip eso=new encrip();
        String user = eso.MD5(txtUser.getText().toString());
        String pass = eso.MD5(txtPwd.getText().toString());
        String url="https://evidential-tubing.000webhostapp.com/usuario_cliente.php";
        StringRequest jrq =new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.isEmpty()){
                    Intent menu=new Intent(getContext(),Main2Activity.class);
                    startActivity(menu);

                }else{
                    Toast.makeText(getContext(), "correo o password incorrecto", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(),"No se pudo conectar"+error.toString(),Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros=new HashMap<>();
                parametros.put("user",txtUser.getText().toString());
                parametros.put("pwd",txtUser.getText().toString());
                return parametros;
            }
        };
        RequestQueue requestQueue=Volley.newRequestQueue(getContext());
        requestQueue.add(jrq);
    }


}
