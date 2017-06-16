package com.example.supero.euquem;

import android.database.Cursor;
import android.util.Log;

/**
 * Created by Augusto Henrique da Conceição on 15/06/2017.
 */

public class Perfil {

    private DBHelper dbConn;

    public int id;
    public String nome;
    public String email;
    public String telefone;
    public Integer cep;
    public Integer numero;
    public String complemento;
    public String estado;
    public String cidade;
    public String logradouro;
    public String bairro;

    public Perfil(DBHelper dbConn){
        id = 0;
        nome = "";
        email = "";
        telefone = "";
        cep = 0;
        numero = 0;
        complemento = "";
        estado = "";
        cidade = "";
        logradouro = "";
        bairro = "";
        this.dbConn = dbConn;
    }

    public void loadPerfil(){
        Cursor rs = dbConn.getData(id);
        rs.moveToFirst();

        id = rs.getInt(rs.getColumnIndex(DBHelper.PERFIL_COLUMN_ID));
        nome = rs.getString(rs.getColumnIndex(DBHelper.PERFIL_COLUMN_NOME));
        Log.i("Perfil", "Nome: " + nome);
        email = rs.getString(rs.getColumnIndex(DBHelper.PERFIL_COLUMN_EMAIL));
        Log.i("Perfil", "Email: " + email);
        telefone = rs.getString(rs.getColumnIndex(DBHelper.PERFIL_COLUMN_TELEFONE));
        Log.i("Perfil", "Telefone: " + telefone);
        cep = rs.getInt(rs.getColumnIndex(DBHelper.PERFIL_COLUMN_CEP));
        numero = rs.getInt(rs.getColumnIndex(DBHelper.PERFIL_COLUMN_NUMERO));
        complemento = rs.getString(rs.getColumnIndex(DBHelper.PERFIL_COLUMN_COMPLEMENTO));
        estado = rs.getString(rs.getColumnIndex(DBHelper.PERFIL_COLUMN_ESTADO));
        cidade = rs.getString(rs.getColumnIndex(DBHelper.PERFIL_COLUMN_CIDADE));
        logradouro = rs.getString(rs.getColumnIndex(DBHelper.PERFIL_COLUMN_LOGRADOURO));
        bairro = rs.getString(rs.getColumnIndex(DBHelper.PERFIL_COLUMN_BAIRRO));

        if (!rs.isClosed())  {
            rs.close();
        }
    }

    public void savePerfil(){
        Log.i("Perfil", "Nome: " + nome);
        Log.i("Perfil", "Email: " + email);
        Log.i("Perfil", "Telefone: " + telefone);
        dbConn.updatePerfil(this);
    }

}
