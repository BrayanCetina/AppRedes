package com.example.redes;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class conexion {
    public void Conexion(String url, final Context context, final Class clas, final String[] valores, final String[] key , final String[] mensajes, final boolean logeo) {
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
                            Toast.makeText(context, mensajes[0] , Toast.LENGTH_LONG).show();
                        }else {
                            if(logeo) {
                                response=response.replace("[","");
                                response=response.replace("]","");
                                //Toast.makeText(context, response, Toast.LENGTH_LONG).show();
                                JSONObject OBJETO = null;
                                try {
                                    OBJETO = new JSONObject(response);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    //Toast.makeText(context, "primer mal", Toast.LENGTH_LONG).show();
                                }
                                int id = 0;
                                String user="",pass="",nombre="",apelli="";
                                try {
                                    id = OBJETO.getInt("2");
                                    user = OBJETO.getString("0");
                                    pass = OBJETO.getString("1");
                                    nombre = OBJETO.getString("3");
                                    apelli = OBJETO.getString("4");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    //Toast.makeText(context, "segundo mal"+r, Toast.LENGTH_LONG).show();
                                }
                                DataBase baseDatosAdmin = new DataBase(context, "prueba",null,1);
                                SQLiteDatabase bd=baseDatosAdmin.getWritableDatabase();
                                // generar el registro a guardar
                                ContentValues  registro = new ContentValues();
                                registro.put("idcliente",id);
                                registro.put("user",user);
                                registro.put("pass",pass);
                                registro.put("nombre",nombre);
                                registro.put("apellido",apelli);
                                registro.put("correo","jale.2126@gmail.com");
                                ///insertamos el registro en la Base de Datos
                                bd.insert("prueba",null,registro);
                                bd.close();
                                    //si trae datos es que si existe
                                    //Toast.makeText(context, mensajes[1]+shad.getString("user",""), Toast.LENGTH_LONG).show();
                                    Intent Acer = new Intent(context, clas);
                                    context.startActivity(Acer);
                                try {
                                    conexion.this.finalize();
                                } catch (Throwable throwable) {
                                    throwable.printStackTrace();
                                }
                            }else{
                                //si trae datos es que si existe
                                Toast.makeText(context, mensajes[1], Toast.LENGTH_LONG).show();
                                Intent Acer = new Intent(context, clas);
                                context.startActivity(Acer);
                            }



                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        //en caso que haya error de conexion
                        Toast.makeText(context,mensajes[2], Toast.LENGTH_LONG).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams()
            {
                //pasamos los parametros que iran en el metodo de post
                Map<String, String> params = new HashMap<String, String>();
                for (int i=0;i<valores.length;i++){
                    params.put(key[i], valores[i]);//agregamos los datos
                }
                return params;//retornamos los parametros
            }
        };
        rq.add(jrq);
    }
}
