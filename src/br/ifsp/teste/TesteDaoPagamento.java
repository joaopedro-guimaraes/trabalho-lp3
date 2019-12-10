/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifsp.teste;

import br.ifsp.controle.dao.DaoPagamento;
import br.ifsp.controle.dao.DaoVenda;
import br.ifsp.modelo.venda.Pagamento;
import java.util.Date;

/**
 *
 * @author Thon
 */
public class TesteDaoPagamento {
    
     public static void main(String[] args) {
        
        DaoVenda daoVenda = new DaoVenda();
        DaoPagamento banco = new DaoPagamento();
        Pagamento aux = new Pagamento();
        
        //aux= banco.readAll(2).get(0);
        aux.setOrigem(daoVenda.read(2));
        aux.setData(new Date());
        aux.setStatus(true);
        aux.setValor(30);
        
         System.out.println(banco.create(aux));
     }
}
