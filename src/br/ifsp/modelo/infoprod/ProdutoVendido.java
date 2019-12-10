package br.ifsp.modelo.infoprod;

import br.ifsp.modelo.venda.Venda;

public class ProdutoVendido {

    private Venda vendaOrigem;
    private Produto produto;
    private int quantidade;
    private String nomeProduto;
    private int idProduto;

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public Venda getVendaOrigem() {
        return vendaOrigem;
    }

    public void setVendaOrigem(Venda vendaOrigem) {
        this.vendaOrigem = vendaOrigem;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public int getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
    }

    
}
