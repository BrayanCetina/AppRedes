package com.example.redes.ui.send;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.redes.DataBase;
import com.example.redes.Main2Activity;
import com.example.redes.R;

import java.io.File;

public class SendFragment extends Fragment {

    Button boton;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        LayoutInflater lf = getActivity().getLayoutInflater();
        View vista=lf.inflate(R.layout.fragment_tools,container,false);
        //boton para descargar pdf
        boton =vista.findViewById(R.id.button5descagar);
        boton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mostrarPDF("prueba.pdf",getContext());
            }
        });
        // Inflate the layout for this fragment
        return vista;

    }
    //metodo para mostrar pdf
    public void mostrarPDF(String nombPdf, Context context) {
        Toast.makeText(context, "Visualizando documento", Toast.LENGTH_LONG).show();

        // Así va correctamente la dirección
        String dir = Environment.getExternalStorageDirectory()+ "/assets/" + nombPdf;
        //Toast.makeText(getContext(),dir, Toast.LENGTH_LONG).show();
        File arch = new File(dir);
        //se visualiza el pdf mediante una aplicacion externa
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(arch), "application/pdf");
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(context, "No existe una aplicación para abrir el PDF", Toast.LENGTH_SHORT).show();
        }
    }
}