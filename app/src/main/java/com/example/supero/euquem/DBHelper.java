package com.example.supero.euquem;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Augusto Henrique da Conceição on 15/06/2017.
 */
public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "EuQuem.db";
    public static final String PERFIL_TABLE_NAME = "perfil";
    public static final String PERFIL_COLUMN_ID = "id";
    public static final String PERFIL_COLUMN_NOME = "nome";
    public static final String PERFIL_COLUMN_EMAIL = "email";
    public static final String PERFIL_COLUMN_TELEFONE = "telefone";
    public static final String PERFIL_COLUMN_CEP = "cep";
    public static final String PERFIL_COLUMN_NUMERO = "numero";
    public static final String PERFIL_COLUMN_COMPLEMENTO = "complemento";
    public static final String PERFIL_COLUMN_ESTADO = "estado";
    public static final String PERFIL_COLUMN_CIDADE = "cidade";
    public static final String PERFIL_COLUMN_LOGRADOURO = "logradouro";
    public static final String PERFIL_COLUMN_BAIRRO = "bairro";

    private HashMap hp;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table perfil " +
                        "(id integer primary key, nome text, email text, telefone text, cep integer, numero integer, complemento text, " +
                        "estado text, cidade text, logradouro text, bairro text)"
        );

        ContentValues contentValues = new ContentValues();
        contentValues.put("nome", "");
        contentValues.put("email", "");
        contentValues.put("telefone", "");
        contentValues.put("cep", 0);
        contentValues.put("numero", 0);
        contentValues.put("complemento", "");
        contentValues.put("estado", "");
        contentValues.put("cidade", "");
        contentValues.put("logradouro", "");
        contentValues.put("bairro", "");
        db.insert("perfil", null, contentValues);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS perfil");
        onCreate(db);
    }

    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from perfil where id="+id+"", null );
        return res;
    }

    public boolean updatePerfil(Perfil perfil) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("nome", perfil.nome);
        contentValues.put("email", perfil.email);
        contentValues.put("telefone", perfil.telefone);
        contentValues.put("cep", perfil.cep);
        contentValues.put("numero", perfil.numero);
        contentValues.put("complemento", perfil.complemento);
        contentValues.put("estado", perfil.estado);
        contentValues.put("cidade", perfil.cidade);
        contentValues.put("logradouro", perfil.logradouro);
        contentValues.put("bairro", perfil.bairro);
        db.update("perfil", contentValues, "id = ? ", new String[] { Integer.toString(perfil.id) } );
        return true;
    }
}