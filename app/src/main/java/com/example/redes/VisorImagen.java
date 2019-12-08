package com.example.redes;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import java.io.InputStream;

public class VisorImagen extends AppCompatActivity {
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.viewimagen);
        ImageView img = (ImageView) findViewById(R.id.ivImagenCompleta);

        Intent intent = getIntent();
        Bundle b = intent.getExtras();


        if(b!=null) {
            new GetImageFromURL(img).execute(b.getString("IMG"));
        }
    }


    public class GetImageFromURL extends AsyncTask<String,Void, Bitmap>
    {

        ImageView imgView;
        public GetImageFromURL(ImageView imgv)
        {
            this.imgView=imgv;
        }
        @Override
        protected Bitmap doInBackground(String... url) {
            String urldisplay=url[0];
            bitmap=null;

            try{

                InputStream ist=new java.net.URL(urldisplay).openStream();
                bitmap= BitmapFactory.decodeStream(ist);
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }

            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap){

            super.onPostExecute(bitmap);
            imgView.setImageBitmap(bitmap);
        }
    }
}
