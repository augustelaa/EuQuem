package com.example.supero.euquem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class CadastradoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrado);
    }

    public void voltar(View view){
        Intent intent = new Intent(CadastradoActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
