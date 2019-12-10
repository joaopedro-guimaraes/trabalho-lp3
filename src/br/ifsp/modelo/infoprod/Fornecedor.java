package br.ifsp.modelo.infoprod;

import br.ifsp.modelo.Pessoa.Contato;

public class Fornecedor {
    
    private int id;
    private String nome;
    private String cnpj;
    private Contato contato;    
    private boolean status;

    public Fornecedor(){
        this.status = true;
        this.contato = null;
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

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public Contato getContato() {
        return contato;
    }

    public void setContato(Contato contato) {
        this.contato = contato;
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
