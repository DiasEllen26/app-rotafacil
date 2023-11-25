package br.com.alfaumuarama.rotafacil;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


import java.util.ArrayList;
import java.util.HashMap;

import br.com.alfaumuarama.rotafacil.datasource.TbUsuario;
import br.com.alfaumuarama.rotafacil.models.Usuario;

public class MainActivity extends AppCompatActivity {


    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(!usuarioEstaLogado()){
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }

    }

    private boolean usuarioEstaLogado(){
        TbUsuario tbUsuario = new TbUsuario(MainActivity.this);

        Usuario usuario = tbUsuario.buscarLocal();

        if(usuario.id == null) return false;

        return true;
    }
};

