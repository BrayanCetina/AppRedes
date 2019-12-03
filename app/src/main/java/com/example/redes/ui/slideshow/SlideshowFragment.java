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
import com.github.barteksc.pdfviewer.PDFView;

import java.io.File;

public class SlideshowFragment extends Fragment {

    PDFView pdfView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_slideshow,container,false);
        pdfView = (PDFView) root.findViewById(R.id.pdfView);
        pdfView.fromAsset("prueba.pdf").load();
        return root;

    }
}