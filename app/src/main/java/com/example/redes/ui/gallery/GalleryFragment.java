package com.example.redes.ui.gallery;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.redes.DataBase;
import com.example.redes.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class GalleryFragment extends Fragment {
    CalendarView calendario;
    Date presentDate;
    TextView fecha,fecha1;
    String  dia,mes,año;
    String idcliente;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fecha=getActivity().findViewById(R.id.textView7);
        fecha=getActivity().findViewById(R.id.fecha);
        calendario=getActivity().findViewById(R.id.calendarView);
        //Date fecha=new Date();
        //año=String.valueOf(fecha.getYear());
        //mes=String.valueOf(fecha.getMonth());
        consultar();
        Conexion(getString(R.string.url)+"fecha.php");
        //fecha.setText(dia);
                // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_gallery, container, false);
    }
    public void consultar(){
        DataBase baseDatosAdmin = new DataBase(getContext(), "bdPrueba",null,1);
        SQLiteDatabase bd=baseDatosAdmin.getWritableDatabase();

        Cursor tabla= bd.rawQuery("SELECT * FROM prueba",null);
        tabla.moveToFirst();
        //fecha1.setText("");
        idcliente=tabla.getString(1);
        Toast.makeText(getContext(), idcliente , Toast.LENGTH_LONG).show();
        do{
            //fecha1.setText(fecha1.getText()+ "--"+tabla.getString(1));
        }while(tabla.moveToNext());
        tabla.close();
        bd.close();
    }
    public void Conexion(String url) {
        RequestQueue rq= Volley.newRequestQueue(getContext());
        //utilizamos el stringrequest donde mandamos todos los datos como el url el metodo
        StringRequest jrq = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        //si la respuesta viene vacia es que el usuario no se encuentra
                        if(response.equals("[]")){
                            Toast.makeText(getContext(), "fallo" , Toast.LENGTH_LONG).show();
                        }else {
                                //si trae datos es que si existe
                            response=response.replace("[","");
                            response=response.replace("]","");
                                Toast.makeText(getContext(), "BIEN", Toast.LENGTH_LONG).show();
                            JSONObject OBJETO = null;
                            try {
                                OBJETO = new JSONObject(response);
                            } catch (JSONException e) {
                                e.printStackTrace();
                                //Toast.makeText(context, "primer mal", Toast.LENGTH_LONG).show();
                            }
                            String diacli = "";
                            try {
                                dia = OBJETO.getString("0");
                            } catch (JSONException e) {
                                e.printStackTrace();
                                //Toast.makeText(context, "segundo mal"+r, Toast.LENGTH_LONG).show();
                            }
                            Toast.makeText(getContext(), dia , Toast.LENGTH_LONG).show();
                        }

                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        //en caso que haya error de conexion
                        Toast.makeText(getContext(),"FALLO LA CONEXION", Toast.LENGTH_LONG).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams()
            {
                //pasamos los parametros que iran en el metodo de post
                Map<String, String> params = new HashMap<String, String>();
                params.put("idcliente",idcliente);//agregamos los datos

                return params;//retornamos los parametros
            }
        };
        rq.add(jrq);
    }
}