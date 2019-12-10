package br.ifsp.modelo.Pessoa;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.ifsp.modelo.venda.Venda;

public class Cliente extends Pessoa {
	
    private double credito;

    public double getCredito() {
        return credito;
    }

    public void setCredito(double credito) {
        this.credito = credito;
    }
        
    public List<Venda> Compras(Date inicio, Date fim){
		
		List<Venda> compras = new ArrayList<Venda>();
		return compras;
    }

}
