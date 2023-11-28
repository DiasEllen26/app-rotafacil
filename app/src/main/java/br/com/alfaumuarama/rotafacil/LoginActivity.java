package br.com.alfaumuarama.rotafacil;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import br.com.alfaumuarama.rotafacil.datasource.TbUsuario;
import br.com.alfaumuarama.rotafacil.models.Usuario;

public class LoginActivity extends AppCompatActivity {

    EditText edtLogin, edtSenha;
    TextView  edtCadastroLogin;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtLogin = findViewById(R.id.edtEmail);
        edtSenha = findViewById(R.id.edtSenha);
        btnLogin = findViewById(R.id.btnLogin);
        edtCadastroLogin = findViewById(R.id.edtCadastroLink);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                efetuarLogin(edtLogin.getText().toString(), edtSenha.getText().toString());
            }
        });

        edtCadastroLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent tela = new Intent(LoginActivity.this, CadastroActivity.class);
                startActivity(tela);
            }
        });

    }

    private void efetuarLogin(String login, String senha){
        TbUsuario tbUsuario = new TbUsuario(LoginActivity.this);


        tbUsuario.login(login, senha, new TbUsuario.LoginCallback() {
            @Override
            public void onSuccess(Usuario usuario) {
                tbUsuario.salvarLocal(usuario);
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }

            @Override
            public void onFailure(Exception e) {
                ShowMensagem(e.getMessage());
            }
        });



    }

    private void ShowMensagem(String texto) {
        AlertDialog.Builder alerta = new AlertDialog.Builder(LoginActivity.this);
        alerta.setTitle("Atenção"); //Adicionando o titulo da mensagem
        alerta.setMessage(texto); //Adicionando o texto da mensagem
        alerta.setNeutralButton("OK", null); //Adicionando botao de OK
        alerta.show(); //Exibindo a mensagem na tela
    }
}