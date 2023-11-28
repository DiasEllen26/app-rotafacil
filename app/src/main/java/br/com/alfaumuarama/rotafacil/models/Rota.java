package br.com.alfaumuarama.rotafacil.models;




import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;

public class Rota {
    public String id;
    public String descricao;
    public Date chegada;
    public Date saida;
    public String localPartida;
    public String destino;

    public HashMap<String,String> toMap() {
        HashMap<String,String> dados = new HashMap<>();

        dados.put("id", id);
        dados.put("descricao", descricao);
        dados.put("chegada", String.valueOf(chegada));
        dados.put("saida", String.valueOf(saida));
        dados.put("localPartida", localPartida);
        dados.put("destino", destino);

        return dados;
    }
}
