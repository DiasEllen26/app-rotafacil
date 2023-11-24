package br.com.alfaumuarama.rotafacil.models;

import android.content.ContentValues;

import java.util.HashMap;

public class Usuario {
    public int id;
    public String email;
    public String senha;

    //Converter os dados da classe em ContentValues (padrao do SQLite)
    public ContentValues toDados() {
        ContentValues dados = new ContentValues();

        if (id > 0)
            dados.put("id", id);
        dados.put("email", email);
        dados.put("senha", senha);

        return dados;
    }

    //Converter os dados da classe em HashMap<> (padrao do ListView)
    public HashMap<String,String> toMap() {
        HashMap<String,String> dados = new HashMap<>();

        dados.put("id", String.valueOf(id));
        dados.put("email", email);
        dados.put("senha", senha);

        return dados;
    }
}