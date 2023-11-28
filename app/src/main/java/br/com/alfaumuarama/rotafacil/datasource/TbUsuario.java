package br.com.alfaumuarama.rotafacil.datasource;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import br.com.alfaumuarama.rotafacil.models.Usuario;

public class TbUsuario {

    FirebaseFirestore db;

    public  TbUsuario(Context context){
        BancoDados.getInstance().abrirBanco(context);
        String sql = "CREATE TABLE IF NOT EXISTS usuario (" +
                "  id TEXT PRIMARY KEY," +
                "cpf TEXT," +
                "email TEXT," +
                "endereco TEXT," +
                "login TEXT," +
                "nome TEXT," +
                "senha TEXT," +
                "telefone INTEGER" +
                ")";
        BancoDados.getInstance().executarSQL(sql);
        db = BancoDadosCloud.getFirestoreInstance();
    }
    public void salvarLocal(Usuario usuario){
        BancoDados.getInstance().getDb().insert("usuario", null, usuario.toDados());
    }
    public Usuario buscarLocal(){
        Cursor cursor = BancoDados.getInstance().getDb().query(
                "usuario",
                new String[] {"id", "cpf", "email", "endereco", "login", "nome", "senha", "telefone"},
                null,
                null,
                null,
                null,
                "nome",
                null
        );

        return retornaLista(cursor);
    }
    private Usuario retornaLista(Cursor cursor) {
        Usuario usuario = new Usuario();
        //verificando se existem dados no cursos retornado pelo SELECT
        if (cursor.getCount() > 0) {
            cursor.moveToFirst(); //seta o cursor na primeira posicao da lista

            //pega o indice da coluna retornado pelo SELECT
            int indiceCampoId = cursor.getColumnIndex("id");
            int indiceCampoCpf = cursor.getColumnIndex("cpf");
            int indiceCampoEmail = cursor.getColumnIndex("email");
            int indiceCampoEndereco = cursor.getColumnIndex("endereco");
            int indiceCampoLogin = cursor.getColumnIndex("login");
            int indiceCampoNome = cursor.getColumnIndex("nome");
            int indiceCampoSenha = cursor.getColumnIndex("senha");
            int indiceCampoTelefone = cursor.getColumnIndex("telefone");


            usuario.id = cursor.getString(indiceCampoId);
            usuario.cpf = cursor.getString(indiceCampoCpf);
            usuario.email = cursor.getString(indiceCampoEmail);
            usuario.endereco = cursor.getString(indiceCampoEndereco);
            usuario.login = cursor.getString(indiceCampoLogin);
            usuario.nome = cursor.getString(indiceCampoNome);
            usuario.senha = cursor.getString(indiceCampoSenha);
            usuario.telefone = cursor.getLong(indiceCampoTelefone);

        }

        return usuario;
    }

    public interface LoginCallback {
        void onSuccess(Usuario usuario);
        void onFailure(Exception e);
    }


    public void login(String login, String senha, LoginCallback callback)  {
        Usuario usuario = new Usuario();
        Log.d("Succes", " *********************** ");
        Log.d("Succes", login);
        Log.d("Succes", senha);
        db.collection("usuario")
                .whereEqualTo("login", login)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful() && task.getResult() != null && !task.getResult().isEmpty()) {
                            DocumentSnapshot document = task.getResult().getDocuments().get(0);
                            String senhaArmazenada = document.getString("senha");
                            Log.d("Succes", "Senha armazenada");
                            Log.d("Succes", senhaArmazenada);

                            String senhaHashFornecida = calcularHashSHA256(senha);

                            Log.d("Succes", "Senha fornecida");
                            Log.d("Succes", senhaHashFornecida);

                            if (senhaHashFornecida != null && senhaHashFornecida.equals(senhaArmazenada)) {
                                Log.d("Succes", "Esta tudo certo");
                              usuario.id = document.getId();
                              usuario.login = document.getString("login");
                              usuario.nome = document.getString("nome");
                              usuario.cpf = document.getString("cpf");
                              usuario.email = document.getString("email");
                              usuario.telefone = document.getLong("telefone");
                              usuario.endereco = document.getString("endereco");

                              callback.onSuccess(usuario);
                            }else {
                                callback.onFailure(new Exception("Senha incorreta"));
                            }
                        }else {
                            callback.onFailure(new Exception("Usuário não encontrado ou erro na consulta"));
                        }
                    }
                });


    }

    public interface CadastroListener {
        void onCadastroSucesso();
        void onCadastroFalha(String mensagemErro);
    }

    public void cadastrar(Usuario usuario, CadastroListener listener){
        boolean status = false;
        db.collection("usuario").add(usuario).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Log.d("Succes","Sucesso ao cadastrar");
                listener.onCadastroSucesso();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
               Log.w("Erro","Falha ao cadastrar");
                listener.onCadastroFalha("Falha ao cadastar" + e.getMessage());
            }
        });

    }


    public static String calcularHashSHA256(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest(input.getBytes());

            // Converte bytes para uma representação hexadecimal
            StringBuilder hexStringBuilder = new StringBuilder();
            for (byte b : hashBytes) {
                hexStringBuilder.append(String.format("%02x", b));
            }

            return hexStringBuilder.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }


}
