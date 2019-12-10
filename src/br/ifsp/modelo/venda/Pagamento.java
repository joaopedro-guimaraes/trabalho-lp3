package br.ifsp.modelo.venda;

import java.util.Date;

public class Pagamento {

    private Date data;
    private Venda origem;
    private double valor;    
    private boolean status;

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Venda getOrigem() {
        return origem;
    }

    public void setOrigem(Venda origem) {
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

    
}
