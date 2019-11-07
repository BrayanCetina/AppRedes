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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtUser=findViewById(R.id.txtUser);
        txtPwd=findViewById(R.id.txtPwd);
    }

    public void Registrar (View view){
        Intent registrar = new Intent(this, registro.class);
        startActivity(registrar);
    }
    public void Acceder (View view){
        mandar();
    }
    private void mandar(){
        encrip eso = new encrip();
        String[] val=new String[2];
        val[0]=eso.MD5(txtUser.getText().toString());//ENCRIPTAMOS LOS VALORES QUE EL USUARIO AGREGE
        val[1]=eso.MD5(txtPwd.getText().toString());//AL IGUAL QUE SE GUARDA EL VALOR QUE SE MANDAR EN UN ARREGLO
        String[] key=new String[2];
        key[0]="user";//GUARDAMOS EL KEY DE CADA VALOR A MANDAR EN FORMA DE ARREGLO
        key[1]="pwd";
        String[] mensajes=new String[3];//MANDAREMOS LOS MENSAJES A IMPRIMIR EN FORMA DE ARREGLE
        mensajes[0]="Usuario o Contraseña incorrecto";
        mensajes[1]="BIENVENIDO";
        mensajes[2]="ERROR EN LA CONEXION";
        conexion con=new conexion();//CREAMOS UN OBJETO DE LA CLASE CONEXION
        //MANDAMOS TOd0 AL METODO CONEXION
        con.Conexion(getString(R.string.url)+"usuario_cliente.php",this,Main2Activity.class,val,key,mensajes);
    }
}