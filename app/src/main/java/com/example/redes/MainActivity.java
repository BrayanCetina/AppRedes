package com.example.redes;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    TextView txtUser,txtPwd;
    RequestQueue rq;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtUser=findViewById(R.id.txtUser);
        txtPwd=findViewById(R.id.txtPwd);
        rq= Volley.newRequestQueue(this);
    }

    public void Registrar (View view){
        Intent registrar = new Intent(this, registro.class);
        startActivity(registrar);
    }
    public void Acceder (View view){
        //Intent Acer = new Intent(this, Main2Activity.class);
        //startActivity(Acer);
        IniciarSecion("https://evidential-tubing.000webhostapp.com/usuario_cliente.php");
    }

    private void IniciarSecion(String url) {
        //encrip eso=new encrip();
        //String user = eso.MD5(txtUser.getText().toString());
        //String pass = eso.MD5(txtPwd.getText().toString());
        //String url="https://evidential-tubing.000webhostapp.com/usuario_cliente.php?user="+user+"&pwd="+pass;
        /*Map<String, String> params = new HashMap();
        params.put("user", txtUser.getText().toString());
        params.put("pwd", txtPwd.getText().toString());
        JSONObject obj=new JSONObject(params);
        try {
            obj.put("user",txtUser.getText().toString());
            obj.put("pwd",txtPwd.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jrq = new JsonObjectRequest(Request.Method.POST,url, obj,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(MainActivity.this, "se encontro", Toast.LENGTH_SHORT).show();
                        Intent Acer = new Intent(MainActivity.this, Main2Activity.class);
                        startActivity(Acer);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "no se encontro",Toast.LENGTH_LONG).show();
                    }
                });*/
        StringRequest jrq = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user", txtUser.getText().toString());
                params.put("pwd", txtPwd.getText().toString());
                return params;
            }
        };
        rq.add(jrq);
    }
}