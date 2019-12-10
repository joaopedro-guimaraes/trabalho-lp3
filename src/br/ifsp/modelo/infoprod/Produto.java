package br.ifsp.modelo.infoprod;

public class Produto {

    private int id;
    private String nome;
    private String nomeFornecedor;
    private RegistroProduto registro;
    private Lote origem;
    private double valor;
    private int quantidade;
    private int quantidadeatual;
    private boolean status;

    public Produto(){
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

    public String getNomeFornecedor() {
        return nomeFornecedor;
    }

    public void setNomeFornecedor(String nomeFornecedor) {
        this.nomeFornecedor = nomeFornecedor;
    }
    
    
    public RegistroProduto getRegistro() {
        return registro;
    }

    public void setRegistro(RegistroProduto registro) {
        this.registro = registro;
    }

    public Lote getOrigem() {
        return origem;
    }

    public void setOrigem(Lote origem) {
        this.origem = origem;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public int getQuantidadeatual() {
        return quantidadeatual;
    }

    public void setQuantidadeatual(int quantidadeatual) {
        this.quantidadeatual = quantidadeatual;
    }

}
