package com.example.redes.ui.slideshow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.redes.R;
import com.github.barteksc.pdfviewer.PDFView;

public class SlideshowFragment extends Fragment {

    PDFView pdfView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_slideshow,container,false);
        pdfView = (PDFView) root.findViewById(R.id.pdfView);
        pdfView.fromAsset("prueba.pdf").load(); //mostramos el pdf
        return root;
    }
}