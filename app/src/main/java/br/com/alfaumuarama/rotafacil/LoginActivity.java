package br.com.alfaumuarama.rotafacil;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {


    EditText edtEmail, edtSenha;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtEmail = findViewById(R.id.edtEmail);
        edtSenha = findViewById(R.id.edtSenha);
        btnLogin = findViewById(R.id.btnLogin);

        Intent dadosRecebido = getIntent();

        if (caminhoRecebido != null) {

            Bundle params = caminhoRecebido.getExtras();

            if (params != null) {
                // idAluno = params.getInt("Id");
                edtEmail.setText(params.getString("Email"));
                edtSenha.setText(params.getString("Senha"));

                //direcionar para a tela principal
            }
        }

    }