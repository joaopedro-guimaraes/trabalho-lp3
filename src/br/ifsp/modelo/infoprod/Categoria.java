package br.ifsp.modelo.infoprod;

import java.util.ArrayList;
import java.util.List;

public class Categoria {

    private int id;
    private String nome;
    private boolean status;
    public List<RegistroProduto> produtos = new ArrayList<RegistroProduto>();

    public Categoria() {

       status = true;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }   

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return nome;
    }
    
    

}
