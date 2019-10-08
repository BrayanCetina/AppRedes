package com.example.redes.ui.home;

import android.os.Bundle;
import android.provider.DocumentsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.redes.Adaptador;
import com.example.redes.Entidad;
import com.example.redes.R;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    private Adaptador adaptador;
    private ListView lvItems;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        lvItems = (ListView) root.findViewById(R.id.lvItems);
        adaptador = new Adaptador(this.GetArrayItems(),getActivity().getApplicationContext());
        lvItems.setAdapter(adaptador);
        return root;
    }
    private ArrayList<Entidad> GetArrayItems(){
        ArrayList<Entidad> listItems = new ArrayList<>();
        listItems.add(new Entidad(R.drawable.mb_1,"Plan Clasico","A solo $299"));
        listItems.add(new Entidad(R.drawable.mb_2,"Plan Mediano","A solo $499"));
        listItems.add(new Entidad(R.drawable.mb_3,"Plan Grande","A solo $599"));
        listItems.add(new Entidad(R.drawable.mb_4,"Plan PYME","A solo $899"));
        listItems.add(new Entidad(R.drawable.mb_5,"Plan Empresarial","A solo $1499"));
        listItems.add(new Entidad(R.drawable.mb_1,"Plan Clasico","A solo $299"));
        listItems.add(new Entidad(R.drawable.mb_2,"Plan Mediano","A solo $499"));
        listItems.add(new Entidad(R.drawable.mb_3,"Plan Grande","A solo $599"));
        listItems.add(new Entidad(R.drawable.mb_4,"Plan PYME","A solo $899"));
        listItems.add(new Entidad(R.drawable.mb_5,"Plan Empresarial","A solo $1499"));
        listItems.add(new Entidad(R.drawable.mb_1,"Plan Clasico","A solo $299"));
        listItems.add(new Entidad(R.drawable.mb_2,"Plan Mediano","A solo $499"));
        listItems.add(new Entidad(R.drawable.mb_3,"Plan Grande","A solo $599"));
        listItems.add(new Entidad(R.drawable.mb_4,"Plan PYME","A solo $899"));
        listItems.add(new Entidad(R.drawable.mb_5,"Plan Empresarial","A solo $1499"));
        return listItems;
    }
}