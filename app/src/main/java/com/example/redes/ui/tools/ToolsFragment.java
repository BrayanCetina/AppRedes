package com.example.redes.ui.tools;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.redes.DataBase;
import com.example.redes.Main2Activity;
import com.example.redes.R;
import com.example.redes.clientes;
import com.example.redes.conexion;
import com.example.redes.encrip;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ToolsFragment extends Fragment implements Response.Listener<JSONObject>,Response.ErrorListener {
    TextView txtNom, txtApe, txtCorreo;
    ProgressDialog progreso;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vista=inflater.inflate(R.layout.fragment_tools,container,false);
        txtNom= vista.findViewById(R.id.txtNom);
        txtApe= vista.findViewById(R.id.txtApe);
        txtCorreo=vista.findViewById(R.id.txtCorreo);
        request= Volley.newRequestQueue(getContext());
        cargar();
        //Folio=findViewById(R.id.txtFolio);
        //User=findViewById(R.id.txtUser);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tools, container, false);

    }
    private  void cargar(){
        DataBase baseDatosAdmin = new DataBase(getContext(), "bdPrueba",null,1);
        SQLiteDatabase bd=baseDatosAdmin.getWritableDatabase();

        Cursor tabla= bd.rawQuery("SELECT * FROM prueba WHERE id=1",null);
        tabla.moveToPosition(0);

        txtNom.setText("1 "+tabla.getString(1));
        txtApe.setText("2 "+tabla.getString(2));
        txtCorreo.setText("3 "+tabla.getString(3));
        do{
            Toast.makeText(getContext(), tabla.getString(1), Toast.LENGTH_LONG).show();
            Toast.makeText(getContext(), tabla.getString(2), Toast.LENGTH_LONG).show();
            Toast.makeText(getContext(), tabla.getString(3), Toast.LENGTH_LONG).show();
            txtNom.setText(txtNom+"--"+tabla.getString(3));
        }while(tabla.moveToNext());
        tabla.close();
        bd.close();

    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getContext(),"Error ",Toast.LENGTH_LONG).show();

    }

    @Override
    public void onResponse(JSONObject response) {
        Toast.makeText(getContext(),"Si entra "+response,Toast.LENGTH_LONG).show();
        clientes cl=new clientes();
        JSONArray json=response.optJSONArray("cliente");
        JSONObject jsonObject=null;
        try {
            jsonObject=json.getJSONObject(0);
            cl.setNombres(jsonObject.optString("nombres"));
            cl.setApellidos(jsonObject.optString("apellidos"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        txtNom.setText((cl.getNombres()));
        txtApe.setText(cl.getApellidos());
        txtCorreo.setText(cl.getCorreo());
    }
}