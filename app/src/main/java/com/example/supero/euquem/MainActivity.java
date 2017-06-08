package com.example.supero.euquem;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.os.Bundle;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    public EditText cepEditText;
    public EditText logradouroEditText;
    public EditText numeroEditText;
    public EditText complementoEditText;
    public EditText bairroEditText;
    public EditText cidadeEditText;
    public EditText estadoEditText;

    private CorreiosAPI correiosAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Instanciando os inputs */
        cepEditText = (EditText) findViewById(R.id.cep);
        logradouroEditText = (EditText) findViewById(R.id.logradouro);
        numeroEditText = (EditText) findViewById(R.id.numero);
        complementoEditText = (EditText) findViewById(R.id.complemento);
        bairroEditText = (EditText) findViewById(R.id.bairro);
        cidadeEditText = (EditText) findViewById(R.id.cidade);
        estadoEditText = (EditText) findViewById(R.id.estado);
        /* Iniciando os watchs */
        new CEPWatcher();

        correiosAPI = new CorreiosAPI(this);
    }

    class CEPWatcher {

        public CEPWatcher(){
            cepEditText.addTextChangedListener(cepWatcher);
        }

        public final TextWatcher cepWatcher = new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            public void afterTextChanged(Editable s) {
                if (s.length() == 8) {
                    Log.i("CEP", "Consultando CEP: " + s.toString());
                    correiosAPI.chamarAsyncTaskConsultaCEP();
                }
            }
        };
    }
}