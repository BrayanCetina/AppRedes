package com.example.redes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class Adaptador extends BaseAdapter {
    private ArrayList<Entidad> listItems;
    private Context context;

    public Adaptador(ArrayList<Entidad> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @Override
    public int getCount() {
        return listItems.size();
    }

    @Override
    public Object getItem(int i) {
        return listItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        Entidad Item = (Entidad) getItem(i);
        view = LayoutInflater.from(context).inflate(R.layout.item,null);
        ImageView imgFoto = view.findViewById(R.id.imfFoto);
        TextView tvTitulo = view.findViewById(R.id.tvTitulo);
        TextView tvContenido = view.findViewById(R.id.tvContenido);

        imgFoto.setImageResource(Item.getImgFoto());
        tvTitulo.setText((Item.getTitulo()));
        tvContenido.setText(Item.getContenido());

        return view;
    }
}
