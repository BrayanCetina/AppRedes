package com.example.redes;

import android.view.ViewGroup;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.os.AsyncTask;
import android.widget.TextView;
import java.io.InputStream;

public class Adaptador extends BaseAdapter {
    private static LayoutInflater inflater = null;
    Context contexto;
    String[] ids;
    String[] img;
    String[] titulo;
    String[] contenido;
    Bitmap bitmap;

    public Adaptador(Context contexto, String[] ids, String[] img, String[] titulo, String[] contenido) {
        this.contexto = contexto;
        this.ids = ids;
        this.img = img;
        this.titulo = titulo;
        this.contenido = contenido;
        inflater = (LayoutInflater) contexto.getSystemService(contexto.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return img.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        final View vista = inflater.inflate(R.layout.item,null);
        TextView Titulo = (TextView) vista.findViewById(R.id.tvTitulo);
        TextView Contenido = (TextView) vista.findViewById(R.id.tvContenido);
        ImageView IMGURL = (ImageView) vista.findViewById(R.id.imfFoto);


        Titulo.setText(titulo[i]);
        Contenido.setText(contenido[i]);
        new GetImageFromURL(IMGURL).execute(img[i]);

        IMGURL.setTag(i);
        //Precionar la foto
        IMGURL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentl = new Intent(contexto,VisorImagen.class);
                intentl.putExtra("IMG",img[i]);
                contexto.startActivity(intentl);
            }
        });
        return vista;
    }

    public class GetImageFromURL extends AsyncTask<String, Void, Bitmap> {

        ImageView imgView;

        public GetImageFromURL(ImageView imgv) {
            this.imgView = imgv;
        }

        @Override
        protected Bitmap doInBackground(String... url) {
            String urldisplay = url[0];
            bitmap = null;

            try {
                InputStream ist = new java.net.URL(urldisplay).openStream();
                bitmap = BitmapFactory.decodeStream(ist);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {

            super.onPostExecute(bitmap);
            imgView.setImageBitmap(bitmap);
        }
    }

}
