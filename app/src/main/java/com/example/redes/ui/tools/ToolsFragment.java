package com.example.redes.ui.tools;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.redes.DataBase;
import com.example.redes.Main2Activity;
import com.example.redes.R;
import com.example.redes.clientes;
import com.example.redes.conexion;
import com.example.redes.encrip;
import com.example.redes.registro;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ToolsFragment extends Fragment {
    TextView txtNom, txtApe, txtCorreo,txtpass1,txtpass2;
    ProgressDialog progreso;
    RequestQueue request;
    String nombre,apellido,id,password,usuario,txtpasac,txtpasnew;
    Button boton;
    JsonObjectRequest jsonObjectRequest;
    encrip eso=new encrip();

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LayoutInflater lf = getActivity().getLayoutInflater();
        View vista=lf.inflate(R.layout.fragment_tools,container,false);
        txtNom= vista.findViewById(R.id.txtNom);
        txtApe= vista.findViewById(R.id.txtApe);
        txtCorreo=vista.findViewById(R.id.txtCorreo);
        txtpass1= vista.findViewById(R.id.txtPass1);
        txtpass2= vista.findViewById(R.id.txtPass2);
        request= Volley.newRequestQueue(getContext());
        cargar();
        boton =vista.findViewById(R.id.buttonaPerfilAceptar);
        boton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                txtpasac=eso.MD5(txtpass1.getText().toString());
                txtpasnew=eso.MD5(txtpass2.getText().toString());
                //Toast.makeText(getContext(),"lo tienes", Toast.LENGTH_LONG).show();
                //brayan valida que los campos a editar usuario o nombre esten llenos y sean correctos para actualizar
                if(txtpasac.equals(password)){
                    //Toast.makeText(getContext(),"contraseña correcta", Toast.LENGTH_LONG).show();
                    if(txtCorreo.getText().toString().equals("")) {
                        Toast.makeText(getContext(),"no deje vacio el campo de usuario", Toast.LENGTH_LONG).show();
                    }
                    if(txtCorreo.getText().toString()!=usuario && txtpasnew.equals("")){
                        //por si solo cambia el nombre de ususario
                        DataBase baseDatosAdmin = new DataBase(getContext(), "prueba",null,1);
                        SQLiteDatabase bd=baseDatosAdmin.getWritableDatabase();
                        usuario=eso.MD5(txtCorreo.getText().toString());
                        Toast.makeText(getContext(),"se va a actualizar el user", Toast.LENGTH_LONG).show();
                        Conexion("https://evidential-tubing.000webhostapp.com/actualizar.php");
                        bd.execSQL("UPDATE prueba SET user='"+txtCorreo.getText().toString()+"', pass='' WHERE idcliente='"+id+"'");
                        Toast.makeText(getContext(),"se actualizo el user", Toast.LENGTH_LONG).show();
                        Intent registrar = new Intent(getContext(), Main2Activity.class);
                        startActivity(registrar);
                    }
                    if(txtCorreo.getText().toString()!="" && txtpasnew!=""){
                        DataBase baseDatosAdmin = new DataBase(getContext(), "prueba",null,1);
                        SQLiteDatabase bd=baseDatosAdmin.getWritableDatabase();
                        usuario=eso.MD5(txtCorreo.getText().toString());
                        password=eso.MD5(txtpass2.getText().toString());
                        Conexion( "https://evidential-tubing.000webhostapp.com/actualizar.php");
                        bd.execSQL("UPDATE prueba SET user='"+txtCorreo.getText().toString()+"', pass='"+password+"' WHERE idcliente='"+id+"'");
                        Toast.makeText(getContext(),"se actualizo la contra", Toast.LENGTH_LONG).show();
                        Intent registrar = new Intent(getContext(), Main2Activity.class);
                        startActivity(registrar);
                    }
                }else{
                    Toast.makeText(getContext(),"ingrese su contraseña actual para editar su usuario o contraseña", Toast.LENGTH_LONG).show();
                }
            }
        });
        return vista;

    }
    private  void cargar(){
        DataBase baseDatosAdmin = new DataBase(getContext(), "prueba",null,1);
        SQLiteDatabase bd=baseDatosAdmin.getWritableDatabase();

        Cursor tabla= bd.rawQuery("SELECT * FROM prueba",null);
        tabla.moveToFirst();
        //1.id 2.nombre 3.apellido 4.usuario 5.password 6.correo
        id=tabla.getString(1);
        txtNom.setText(""+tabla.getString(4));
        nombre=tabla.getString(4);
        txtApe.setText(""+tabla.getString(5));
        apellido=tabla.getString(5);
        txtCorreo.setText(""+tabla.getString(2));
        usuario=tabla.getString(2);
        password=tabla.getString(3);
        do{
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
                    public void onResponse(String response) {
                        //si la respuesta viene vacia es que el usuario no se encuentra
                        Toast.makeText(getContext(),"se actualizo", Toast.LENGTH_LONG).show();
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
                params.put("id",id);//agregamos los datos
                params.put("User",usuario);//agregamos los datos
                params.put("Pass",password);//agregamos los datos

                return params;//retornamos los parametros
            }
        };
        rq.add(jrq);
    }
}