package com.example.redes.ui.gallery;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
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
import android.text.format.DateFormat;
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

    TextView fecha;
    String  dia;
    String mes="",año="",diapre="";
    String idcliente;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        LayoutInflater lf = getActivity().getLayoutInflater();
        View root = lf.inflate(R.layout.fragment_gallery, container, false);
        //direccionamos las variables
        fecha=root.findViewById(R.id.fecha);
        //creamos un calendar para obtener la fecha actual
        Calendar fecha=Calendar.getInstance();
        año=String.valueOf(fecha.get(Calendar.YEAR));//año
        mes=String.valueOf(fecha.get(Calendar.MONTH)+1);//importante sumarle 1 al mes porque empieza desde 0
        diapre=String.valueOf(fecha.get(Calendar.DAY_OF_MONTH));//dia del mes
        consultar();//consultamos el id del cliente para mandarlo y obtener su fecha de corte
        Conexion(getString(R.string.url)+"fecha.php");//mandamos al metodo consultar para obtener los datos del cliente

                // Inflate the layout for this fragment
        return root;
    }
    public void consultar(){
        //se crea la BD local para recibir datos
        DataBase baseDatosAdmin = new DataBase(getContext(), "bdPrueba",null,1);
        SQLiteDatabase bd=baseDatosAdmin.getWritableDatabase();
        //Se hace la consulta de la BD
        Cursor tabla= bd.rawQuery("SELECT * FROM prueba",null);
        tabla.moveToFirst();
        idcliente=tabla.getString(1);//obtenemos su id
        tabla.close();
        bd.close();
    }
    public void Conexion(String url) {
        TextView a;
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
                            //se le quitan [] para que se pueda transaformar a un JSONObject
                            response=response.replace("[","");
                            response=response.replace("]","");
                                Toast.makeText(getContext(), "BIEN", Toast.LENGTH_LONG).show();
                            JSONObject OBJETO = null;
                            try {
                                OBJETO = new JSONObject(response);//pasamos los datos al jsonobject
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            String diacli = "";
                            try {
                                dia = OBJETO.getString("0");//extraemos el dia de corte
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            //se comprueba si el dia actual es mayor al dia de corte se pasa al siguinete mes
                            //la fecha de pago
                            if(Integer.parseInt(diapre)>Integer.parseInt(dia)){
                                //y si el mes es doce se tiene que pasar a 1(enero) y sumar 1 al año
                                if(Integer.parseInt(mes)==12){
                                    mes="1";
                                    año=(Integer.parseInt(año)+1)+"";
                                }else {
                                    //de lo contrario solo se le suma al mes
                                    mes = (Integer.parseInt(mes) + 1) + "";
                                }
                            }
                            //si faltan 5 dias para la fecha de corte se muestra un alert
                            if(Integer.parseInt(diapre)>=2 && Integer.parseInt(diapre)<7) {
                                int res=Integer.parseInt(dia)-Integer.parseInt(diapre);
                                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(getContext());
                                dialogo1.setTitle("Importante");
                                dialogo1.setMessage("faltan " + res + " para la fecha de corte");
                                dialogo1.setCancelable(false);
                                dialogo1.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialogo1, int id) {

                                    }
                                });
                                dialogo1.show();
                            }
                            //mostramos la fecha de proximo pago
                            fecha.setText(dia+"/"+mes+"/"+año);
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