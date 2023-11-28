package br.com.alfaumuarama.rotafacil;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import br.com.alfaumuarama.rotafacil.datasource.TbUsuario;
import br.com.alfaumuarama.rotafacil.models.Usuario;

public class CadastroActivity  extends AppCompatActivity {
    EditText edtCpf, edtEmail, edtEndereco, edtLogin, edtNome, edtSenha, edtTelefone;

    Button btnCadastrar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        // Inicializar os EditTexts
        edtCpf = findViewById(R.id.edtCpf);
        edtEmail = findViewById(R.id.edtEmailCadatro);
        edtEndereco = findViewById(R.id.edtEndereco);
        edtLogin = findViewById(R.id.edtLogin);
        edtNome = findViewById(R.id.edtNome);
        edtSenha = findViewById(R.id.edtSenhaCadastro);
        edtTelefone = findViewById(R.id.edtTelefone);

        btnCadastrar = findViewById(R.id.btnCadastrar);

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cadastrar();
            }
        });

    }


    private void cadastrar() {
        // Recuperar dados dos campos
        String cpf = edtCpf.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();
        String endereco = edtEndereco.getText().toString().trim();
        String login = edtLogin.getText().toString().trim();
        String nome = edtNome.getText().toString().trim();
        String senha = edtSenha.getText().toString().trim();
        String telefoneStr = edtTelefone.getText().toString().trim();

        if (TextUtils.isEmpty(cpf) || TextUtils.isEmpty(email) || TextUtils.isEmpty(endereco) ||
                TextUtils.isEmpty(login) || TextUtils.isEmpty(nome) || TextUtils.isEmpty(senha) ||
                TextUtils.isEmpty(telefoneStr)) {
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
        } else {
            // Todos os campos foram preenchidos, continuar com a lógica de cadastro
            Long telefone = Long.parseLong(telefoneStr);
            TbUsuario tbUsuario = new TbUsuario(CadastroActivity.this);

            Usuario user = new Usuario();
            user.cpf = cpf;
            user.email = email;
            user.endereco = endereco;
            user.login = login;
            user.nome = nome;
            user.senha = TbUsuario.calcularHashSHA256(senha);
            user.telefone = telefone;

            tbUsuario.cadastrar(user, new TbUsuario.CadastroListener() {
                @Override
                public void onCadastroSucesso() {
                    Intent tela = new Intent(CadastroActivity.this, LoginActivity.class);
                    startActivity(tela);
                }

                @Override
                public void onCadastroFalha(String mensagemErro) {
                    ShowMensagem(mensagemErro);
                }
            });


        }

    }
    private void ShowMensagem(String texto) {
        AlertDialog.Builder alerta = new AlertDialog.Builder(CadastroActivity.this);
        alerta.setTitle("Atenção"); //Adicionando o titulo da mensagem
        alerta.setMessage(texto); //Adicionando o texto da mensagem
        alerta.setNeutralButton("OK", null); //Adicionando botao de OK
        alerta.show(); //Exibindo a mensagem na tela
    }
}
