package br.com.alfaumuarama.rotafacil.models;

import android.content.ContentValues;

import java.math.BigInteger;
import java.util.HashMap;

public class Usuario {
    public String id;
    public String cpf;
    public String email;
    public String endereco;
    public String login;
    public String nome;
    public String senha;
    public Long telefone;

    //Converter os dados da classe em ContentValues (padrao do SQLite)
    public ContentValues toDados() {
        ContentValues dados = new ContentValues();

        if(!id.isEmpty())
            dados.put("id", id);
        dados.put("cpf", cpf);
        dados.put("email", email);
        dados.put("endereco", endereco);
        dados.put("login", login);
        dados.put("nome", nome);
        dados.put("senha", senha);
        dados.put("telefone", telefone);

        return dados;
    }

    //Converter os dados da classe em HashMap<> (padrao do ListView)
    public HashMap<String,String> toMap() {
        HashMap<String,String> dados = new HashMap<>();

        dados.put("id", id);
        dados.put("cpf", cpf);
        dados.put("email", email);
        dados.put("endereco", endereco);
        dados.put("login", login);
        dados.put("nome", nome);
        dados.put("senha", senha);
        dados.put("telefone", String.valueOf(telefone));

        return dados;
    }
}