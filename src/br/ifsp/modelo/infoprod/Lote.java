package br.ifsp.modelo.infoprod;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Lote {

    private int id;
    private Date data;
    private Fornecedor origem;
    private double valor;
    private double lucroPerct;
    public List<Produto> itens = new ArrayList<Produto>();
    private boolean status;
    private String nomeFornecedor;

    public Lote(){
        this.status = true;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Fornecedor getOrigem() {
        return origem;
    }

    public void setOrigem(Fornecedor origem) {
        this.origem = origem;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public double getLucroPerct() {
        return lucroPerct;
    }

    public void setLucroPerct(double lucroPerct) {
        this.lucroPerct = lucroPerct;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getNomeFornecedor() {
        return nomeFornecedor;
    }

    public void setNomeFornecedor(String nomeFornecedor) {
        this.nomeFornecedor = nomeFornecedor;
    }

    @Override
    public String toString() {
        return  origem.getNome() + " " + data;
    }

    
}
