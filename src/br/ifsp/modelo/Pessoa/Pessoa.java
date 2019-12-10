package br.ifsp.modelo.Pessoa;

import java.util.Date;

public abstract class Pessoa {
    
    private int id;
    private String nome;
    private String cpf;
    private String rg;
    private Date nascimento;
    private Endereco endereco;
    private boolean sexo;
    private Contato contatos;
    private boolean status;

    public Pessoa() {

        status=true;
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

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public Date getNascimento() {
        return nascimento;
    }

    public void setNascimento(Date nascimento) {
        this.nascimento = nascimento;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public boolean isSexo() {
        return sexo;
    }

    public void setSexo(boolean sexo) {
        this.sexo = sexo;
    }

    public Contato getContatos() {
        return contatos;
    }

    public void setContatos(Contato contatos) {
        this.contatos = contatos;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
