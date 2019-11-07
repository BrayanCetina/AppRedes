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
import com.example.redes.encrip;

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
        encrip eso = new encrip();
        String[] val=new String[2];
        val[0]=eso.MD5(txtUser.getText().toString());
        val[1]=eso.MD5(txtPwd.getText().toString());
        String[] key=new String[2];
        key[0]="user";
        key[1]="pwd";
        String[] mensajes=new String[2];
        mensajes[0]="Usuario o Contraseña incorrecto";
        mensajes[1]="SE PUDO";
        mensajes[2]="ERROR EN LA CONEXION";
        conexion con=new conexion();
        con.IniciarSecion(getString(R.string.url)+"usuario_cliente.php",this,Main2Activity.class,val,key,mensajes);
        //al dar click se manda al metodo iniciarsesion pasando el url
        //IniciarSecion(getString(R.string.url));
    }

    private void IniciarSecion(String url) {
        //utilizamos el stringrequest donde mandamos todos los datos como el url el metodo
        StringRequest jrq = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        //si la respuesta viene vacia es que el usuario no se encuentra
                        if(response.equals("[]")){
                            Toast.makeText(getApplicationContext(), "Usuario o Contraseña incorrecto" + response, Toast.LENGTH_LONG).show();
                        }else {
                            //si trae datos es que si existe
                            Toast.makeText(MainActivity.this, "SE PUDO" + response, Toast.LENGTH_LONG).show();
                            Intent Acer = new Intent(MainActivity.this, Main2Activity.class);
                            startActivity(Acer);
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        //en caso que haya error de conexion
                        Toast.makeText(getApplicationContext(),"ERROR DE CONEXION"+ error.toString(), Toast.LENGTH_LONG).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams()
            {
                //pasamos los parametros que iran en el metodo de post
                encrip eso = new encrip();//agregamos un obj de la clase encrip
                String User = eso.MD5(txtUser.getText().toString());//encriptamos el usuario y contraseña
                String Pass = eso.MD5(txtPwd.getText().toString());
                Map<String, String> params = new HashMap<String, String>();
                params.put("user", User);//agregamos los datos
                params.put("pwd", Pass);
                return params;//retornamos los parametros
            }
        };
        rq.add(jrq);
    }
}