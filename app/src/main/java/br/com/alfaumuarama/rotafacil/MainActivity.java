package br.com.alfaumuarama.rotafacil;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import br.com.alfaumuarama.rotafacil.datasource.BancoDadosCloud;
import br.com.alfaumuarama.rotafacil.datasource.TbUsuario;
import br.com.alfaumuarama.rotafacil.models.Rota;
import br.com.alfaumuarama.rotafacil.models.Usuario;

public class MainActivity extends ListActivity {


    FirebaseFirestore db =  BancoDadosCloud.getFirestoreInstance();

    ArrayList<Rota> rotasList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(!usuarioEstaLogado()){
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            return;
        }

        atualizarLista();
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Rota rota = rotasList.get(position);

        Intent tela = new Intent(MainActivity.this, DetalhesActivity.class );
        Bundle params = new Bundle();
        params.putString("destino", rota.destino);
        params.putString("saida", rota.saida.toString());
        params.putString("localPartida", rota.localPartida);
        params.putString("chegada", rota.chegada.toString());
        params.putString("descricao", rota.descricao);

        tela.putExtras(params);

        startActivity(tela);
    }

    private boolean usuarioEstaLogado(){
        TbUsuario tbUsuario = new TbUsuario(MainActivity.this);

        Usuario usuario = tbUsuario.buscarLocal();

        if(usuario.id == null) return false;

        return true;
    }
    private void atualizarLista() {

        db.collection("rota")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    rotasList = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Rota rota = document.toObject(Rota.class);
                        rotasList.add(rota);
                    }
                    ListAdapter adapter = new SimpleAdapter(
                            MainActivity.this, //Local onde esta a ListView (Nesta classe)
                            listaToMap(rotasList), //Lista com os dados em formato HashMap
                            R.layout.listview_modelo, //layout com o modelo de cada item (celula)
                            new String[] { "localPartida", "destino" }, //as colunas com os dados
                            new int[] { R.id.txtPartida, R.id.txtDestino } //cada campo que receberao os dados
                    );

                    setListAdapter(adapter);

                } else {

                }
            }
        });


    }

    private ArrayList<HashMap<String,String>> listaToMap(ArrayList<Rota> lista) {
        ArrayList<HashMap<String,String>> listaRetorno = new ArrayList<>();

        for (Rota rota: lista) {
            listaRetorno.add(rota.toMap());
        }

        return listaRetorno;
    }
};

