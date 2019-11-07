package com.example.redes;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class conexion {
    public void IniciarSecion(String url, final Context context, final Class clas, final String[] valores, final String[] key , final String[] mensajes) {
        RequestQueue rq= Volley.newRequestQueue(context);
        //utilizamos el stringrequest donde mandamos todos los datos como el url el metodo
        StringRequest jrq = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        //si la respuesta viene vacia es que el usuario no se encuentra
                        if(response.equals("[]")){
                            Toast.makeText(context, mensajes[0] + response, Toast.LENGTH_LONG).show();
                        }else {
                            //si trae datos es que si existe
                            Toast.makeText(context, mensajes[1]+ response, Toast.LENGTH_LONG).show();
                            Intent Acer = new Intent(context, clas);
                            context.startActivity(Acer);

                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        //en caso que haya error de conexion
                        Toast.makeText(context,mensajes[2]+ error.toString(), Toast.LENGTH_LONG).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams()
            {
                //pasamos los parametros que iran en el metodo de post
                encrip eso = new encrip();//agregamos un obj de la clase encrip
                //String User = eso.MD5(txtUser.getText().toString());//encriptamos el usuario y contraseña
                //String Pass = eso.MD5(txtPwd.getText().toString());
                Map<String, String> params = new HashMap<String, String>();
                for (int i=0;i<valores.length;i++){
                    params.put(key[i], valores[i]);//agregamos los datos
                }
                //params.put("user", valores[0]);//agregamos los datos
                //params.put("pwd", valores[1]);
                return params;//retornamos los parametros
            }
        };
        rq.add(jrq);
    }
}
