package com.example.supero.euquem;

import android.app.ProgressDialog;
import android.app.AlertDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.EditText;
import android.content.DialogInterface;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.io.FileNotFoundException;
import java.net.UnknownHostException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import com.google.gson.Gson;
import java.util.List;

/**
 * Created by Augusto Henrique da Conceição on 07/06/2017.
 */

public class CorreiosAPI{

    private MainActivity mainActivity;
    private ProgressDialog load;
    private AlertDialog alertDialog;


    public CorreiosAPI(MainActivity mainActivity){
        this.mainActivity = mainActivity;
    }

    final private String urlService = "http://correiosapi.apphb.com/cep/";

    public void chamarAsyncTaskConsultaCEP(){
        TarefaDownload download = new TarefaDownload();
        download.execute(urlService + mainActivity.cepEditText.getText().toString());
    }

    private class TarefaDownload extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute(){
            load = ProgressDialog.show(mainActivity, "Por favor, aguarde.", "Buscando CEP...");
            alertDialog = new AlertDialog.Builder(mainActivity, android.R.style.Theme_Material_Dialog_Alert).create();
            alertDialog.setTitle("Atenção!");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
        }

        @Override
        protected String doInBackground(String... params) {
            String json = null;
            try {
                URL url = new URL(params[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                urlConnection.setReadTimeout(5000);
                urlConnection.setConnectTimeout(5000);

                InputStream stream = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
                StringBuilder builder = new StringBuilder();

                String inputString;
                while ((inputString = bufferedReader.readLine()) != null) {
                    builder.append(inputString);
                }

                json = builder.toString();
                urlConnection.disconnect();
            } catch (UnknownHostException e) {
                alertDialog.setMessage("O serviço dos Correios não está disponível ou você não está conectado à internet.\n\nHost Error");
            } catch (SocketTimeoutException e) {
                alertDialog.setMessage("O serviço dos Correios não está disponível ou você não está conectado à internet.\n\nHost Timeout");
            } catch (FileNotFoundException e){
                alertDialog.setMessage("O CEP " + mainActivity.cepEditText.getText() + " é inválido.");
            } catch (Exception e) {
                alertDialog.setMessage("O serviço dos Correios não está disponível ou você não está conectado à internet.\n\nUnknow");
            }
            return json;
        }

        @Override
        protected void onPostExecute(String jsonText){
            Log.i("CEP", "Retorno CEP: " + jsonText);
            if(jsonText!=null) {

                Gson gson = new Gson();
                CEPWrapper response = gson.fromJson(jsonText, CEPWrapper.class);
                mainActivity.logradouroEditText.setText(response.logradouro);
                mainActivity.bairroEditText.setText(response.bairro);
                mainActivity.cidadeEditText.setText(response.cidade);
                mainActivity.estadoEditText.setText(response.estado);
                mainActivity.numeroEditText.requestFocus();
                mainActivity.cepValido = true;
                mainActivity.isFormularioValido();

            }else{
                alertDialog.show();
                mainActivity.logradouroEditText.setText(null);
                mainActivity.bairroEditText.setText(null);
                mainActivity.cidadeEditText.setText(null);
                mainActivity.estadoEditText.setText(null);
                mainActivity.cepEditText.setText(null);
                mainActivity.cepEditText.requestFocus();
                mainActivity.cepValido = false;
                mainActivity.isFormularioValido();
            }
            load.dismiss();
        }
    }
}
// {"cep":"89056101","tipoDeLogradouro":"Rua","logradouro":"Fritz Koegler","bairro":"Fortaleza","cidade":"Blumenau","estado":"SC"}
class CEPWrapper {
    public String cep;
    public String tipoDeLogradouro;
    public String logradouro;
    public String bairro;
    public String cidade;
    public String estado;


    public static CEPWrapper fromJson(String s) {
        return new Gson().fromJson(s, CEPWrapper.class);
    }
    public String toString() {
        return new Gson().toJson(this);
    }
}