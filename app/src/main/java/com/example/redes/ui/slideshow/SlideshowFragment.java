package com.example.redes.ui.slideshow;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.redes.R;

import java.io.File;

public class SlideshowFragment extends Fragment {

    WebView webview;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_tools,container,false);
        mostrarPDF("prueba.pdf",getContext());
        return vista;

    }
    //Procedimiento para mostrar el documento PDF generado
    public void mostrarPDF(String nombPdf, Context context) {
        Toast.makeText(context, "Visualizando documento", Toast.LENGTH_LONG).show();

        // Así va correctamente la dirección
        String dir = Environment.getExternalStorageDirectory()+ "/AppRedes/pdf/" + nombPdf;

        File arch = new File(dir);

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