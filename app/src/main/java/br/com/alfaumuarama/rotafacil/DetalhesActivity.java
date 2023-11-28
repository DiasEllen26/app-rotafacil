package br.com.alfaumuarama.rotafacil;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DetalhesActivity extends AppCompatActivity {
    TextView txtChegada, txtLocalPartida, txtDestino, txtSaida, txtDescricao ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actovoty_detalhes);

        txtChegada = findViewById(R.id.txtChegadaDetalhes);
        txtLocalPartida = findViewById(R.id.txtLocalPartidaDetalhes);
        txtDestino = findViewById(R.id.txtDestinoDetalhes);
        txtSaida = findViewById(R.id.txtSaidaDetalhes);
        txtDescricao = findViewById(R.id.txtDescricaoDetalhes);

        Intent caminhoRecebido = getIntent();

        if (caminhoRecebido != null) {

            Bundle parametros = caminhoRecebido.getExtras();

            if (parametros != null) {
                txtChegada.setText("Chegada: " + formatarHoraMinuto(parametros.getString("chegada")));
                txtLocalPartida.setText(parametros.getString("localPartida"));
                txtDestino.setText(parametros.getString("destino"));
                txtSaida.setText("Saida: " +formatarHoraMinuto(parametros.getString("saida")));
                txtDescricao.setText(parametros.getString("descricao"));

            }
        }

    }
    private String formatarHoraMinuto(String dataString) {
        try {
            System.out.println(" *********************** ");
            System.out.println(dataString);
            SimpleDateFormat inputFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss 'GMT'Z yyyy", Locale.US);
            Date data = inputFormat.parse(dataString);

            System.out.println("Formatado"+ data.toString());

            if (data != null) {
                SimpleDateFormat horaMinutoFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
                System.out.println(horaMinutoFormat.format(data));
                return horaMinutoFormat.format(data);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "";
    }
}
