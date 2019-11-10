package com.example.redes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class registro extends AppCompatActivity {
    // Variables
    EditText Pass1, Pass2, Folio, User;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        //instanciamos los datos
        Pass1=findViewById(R.id.txtPass1);
        Pass2=findViewById(R.id.txtPass2);
        Folio=findViewById(R.id.txtFolio);
        User=findViewById(R.id.txtUser);
    }
    public void RegistrarNuevoUsuario (View view){
        if (!User.getText().toString().equals("") && !Folio.getText().toString().equals("") && !Pass1.getText().toString().equals("") && !Pass2.getText().toString().equals("")){
            if (Pass1.getText().toString().equals(Pass2.getText().toString())){
                mandarRegistro();
            }else{
                Toast.makeText(this,"Las contraseñas no concuerdan", Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(this,"Compruebe sus datos", Toast.LENGTH_LONG).show();
        }
    }

    public void Cancelar (View view){
        Intent Inicio = new Intent(this, MainActivity.class);
        startActivity(Inicio);
    }
    private void mandarRegistro(){
        encrip eso = new encrip();
        String[] val=new String[3];
        val[0]=Folio.getText().toString();//AL IGUAL QUE SE GUARDA EL VALOR QUE SE MANDAR EN UN ARREGLO
        val[1]=eso.MD5(User.getText().toString());
        val[2]=eso.MD5(Pass1.getText().toString());
        String[] key=new String[3];
        key[0]="Folio";//GUARDAMOS EL KEY DE CADA VALOR A MANDAR EN FORMA DE ARREGLO
        key[1]="User";
        key[2]="Pass";//GUARDAMOS EL KEY DE CADA VALOR A MANDAR EN FORMA DE ARREGLO
        String[] mensajes=new String[3];//MANDAREMOS LOS MENSAJES A IMPRIMIR EN FORMA DE ARREGLE
        mensajes[0]="LISTO";
        mensajes[1]="LISTO";
        mensajes[2]="ERROR EN LA CONEXION";
        conexion con=new conexion();//CREAMOS UN OBJETO DE LA CLASE CONEXION
        //MANDAMOS TOd0 AL METODO CONEXION
        con.Conexion(getString(R.string.url)+"registro_cliente.php",this,MainActivity.class,val,key,mensajes);
    }
}
