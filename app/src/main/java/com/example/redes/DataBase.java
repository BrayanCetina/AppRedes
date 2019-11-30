package com.example.redes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBase extends SQLiteOpenHelper {

    public DataBase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql="CREATE TABLE prueba(id integer primary key,idcliente integer,user varchar,pass varchar,nombre text,apellido text,correo text)";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists prueba");
        sqLiteDatabase.execSQL("CREATE TABLE prueba(id integer primary key,idcliente integer,user varchar,pass varchar,nombre text,apellido text,correo text)");
    }
}
