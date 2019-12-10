/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifsp.teste;

import br.ifsp.controle.dao.DaoFornecedor;
import br.ifsp.controle.dao.DaoLote;
import br.ifsp.modelo.infoprod.Lote;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Thon
 */
public class TesteDaoLote {
    
    public static void main(String[] args) {
        DaoFornecedor daoFornecedor = new DaoFornecedor();
        Lote aux = new Lote();
        aux.setData(new Date());
        aux.setLucroPerct(0.3);
        aux.setValor(0);
        aux.setStatus(true);
        aux.setOrigem(daoFornecedor.read(1));
        DaoLote banco = new DaoLote();
        banco.create(aux);
        /*aux = banco.read(2);
        aux.setValor(2);
        
        //List<Lote> lista = banco.readAll(true, "2");
        System.out.println(banco.update(aux));
        //for(Lote aux: lista)
        //System.out.println("Id: " + aux.getId() + " Fornecedor: " + aux.getOrigem().getNome() +
        //      " Valor: " + aux.getValor() + " Lucro: " + aux.getLucroPerct() + " Data: " + aux.getData());
        /*else
            System.out.println("vazio");*/
    }
}
