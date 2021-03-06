package com.example.supero.euquem;

import android.os.Bundle;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.widget.Button;
import android.view.View;
import android.widget.EditText;
import android.database.Cursor;

/**
 * Created by Augusto Henrique da Conceição on 07/06/2017.
 */
public class MainActivity extends AppCompatActivity {

    private CorreiosAPI correiosAPI;
    private Perfil perfil;
    private DBHelper dbConn;

    protected EditText cepEditText;
    protected EditText emailEditText;
    protected EditText nomeEditText;
    protected EditText telefoneEditText;
    protected Button cadastrarButton;
    protected EditText logradouroEditText;
    protected EditText numeroEditText;
    protected EditText complementoEditText;
    protected EditText bairroEditText;
    protected EditText cidadeEditText;
    protected EditText estadoEditText;

    public boolean cepValido;
    public boolean emailValido;
    public boolean nomeValido;
    public boolean telefoneValido;
    public boolean numeroValido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Instanciando os inputs */
        cepEditText = (EditText) findViewById(R.id.cep);
        emailEditText = (EditText) findViewById(R.id.email);
        nomeEditText = (EditText) findViewById(R.id.nome);
        telefoneEditText = (EditText) findViewById(R.id.telefone);
        cadastrarButton = (Button) findViewById(R.id.cadastrar);
        logradouroEditText = (EditText) findViewById(R.id.logradouro);
        numeroEditText = (EditText) findViewById(R.id.numero);
        complementoEditText = (EditText) findViewById(R.id.complemento);
        bairroEditText = (EditText) findViewById(R.id.bairro);
        cidadeEditText = (EditText) findViewById(R.id.cidade);
        estadoEditText = (EditText) findViewById(R.id.estado);
        /* Iniciando os watchs */
        new TheWatcher();

        correiosAPI = new CorreiosAPI(this);
        dbConn = new DBHelper(this);
        perfil = new Perfil(dbConn);
        perfil.id = 1;
        perfil.loadPerfil();

        nomeEditText.setText(perfil.nome);
        emailEditText.setText(perfil.email);
        telefoneEditText.setText(perfil.telefone);
        if(perfil.cep != 0) {
            cepEditText.setText(perfil.cep.toString());
        }else{
            cepEditText.setText("");
        }
        if(perfil.numero != 0) {
            numeroEditText.setText(perfil.numero.toString());
        }else{
            numeroEditText.setText("");
        }
        complementoEditText.setText(perfil.complemento);
        estadoEditText.setText(perfil.estado);
        cidadeEditText.setText(perfil.cidade);
        logradouroEditText.setText(perfil.logradouro);
        bairroEditText.setText(perfil.bairro);
    }

    public void isFormularioValido(){
        if(nomeValido && telefoneValido && cepValido && numeroValido && emailValido) {
            cadastrarButton.setEnabled(true);
        }else{
            cadastrarButton.setEnabled(false);
        }
    }

    // Chamado ao clicar o botão de cadastrar
    public void cadastrar(View view){
        perfil.id = 1;
        perfil.nome = nomeEditText.getText().toString();
        perfil.email = emailEditText.getText().toString();
        perfil.telefone = telefoneEditText.getText().toString();
        perfil.cep = Integer.parseInt(cepEditText.getText().toString());
        perfil.numero = Integer.parseInt(numeroEditText.getText().toString());
        perfil.complemento = complementoEditText.getText().toString();
        perfil.estado = estadoEditText.getText().toString();
        perfil.cidade = cidadeEditText.getText().toString();
        perfil.logradouro = logradouroEditText.getText().toString();
        perfil.bairro = bairroEditText.getText().toString();
        perfil.savePerfil();

        Intent intent = new Intent(MainActivity.this, CadastradoActivity.class);
        startActivity(intent);
    }

    class TheWatcher {

        public TheWatcher(){
            cepEditText.addTextChangedListener(cepWatcher);
            emailEditText.addTextChangedListener(emailWatcher);
            nomeEditText.addTextChangedListener(nomeWatcher);
            telefoneEditText.addTextChangedListener(telefoneWatcher);
            numeroEditText.addTextChangedListener(numeroWatcher);

            cepValido = false;
        }

        public final boolean isValidEmail(CharSequence target) {
            if (target == null) {
                return false;
            } else {
                return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
            }
        }

        public final boolean isValidPhone(CharSequence target) {
            if (target == null) {
                return false;
            } else {
                return Patterns.PHONE.matcher(target).matches();
            }
        }

        public final TextWatcher cepWatcher = new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            public void afterTextChanged(Editable s) {
                if (cepEditText.getText().length() == 8) {
                    Log.i("CEP", "Consultando CEP: " + cepEditText.getText().toString());
                    correiosAPI.chamarAsyncTaskConsultaCEP();
                }else{
                    cepValido = false;
                }

                isFormularioValido();
            }
        };

        public final TextWatcher emailWatcher = new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            public void afterTextChanged(Editable s) {
                if (emailEditText.getText().length() > 6) {
                    Log.i("E-mail", "Consultando e-mail: " + emailEditText.getText().toString());
                    emailValido = isValidEmail(emailEditText.getText());
                }

                isFormularioValido();
            }
        };

        public final TextWatcher nomeWatcher = new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            public void afterTextChanged(Editable s) {
                if (nomeEditText.getText().length() >= 2) {
                    nomeValido = true;
                }else{
                    nomeValido = false;
                }

                isFormularioValido();
            }
        };

        public final TextWatcher telefoneWatcher = new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            public void afterTextChanged(Editable s) {
                if (telefoneEditText.getText().length() >= 8) {
                    Log.i("Telefone", "Consultando telefone: " + telefoneEditText.getText().toString());
                    telefoneValido = isValidPhone(telefoneEditText.getText());
                }else{
                    telefoneValido = false;
                }

                isFormularioValido();
            }
        };

        public final TextWatcher numeroWatcher = new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            public void afterTextChanged(Editable s) {
                if (numeroEditText.getText().length() > 0) {
                    numeroValido = true;
                }else{
                    numeroValido = false;
                }

                isFormularioValido();
            }
        };
    }
}