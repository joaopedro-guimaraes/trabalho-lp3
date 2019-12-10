/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifsp.teste;

import br.ifsp.controle.dao.DaoProduto;
import br.ifsp.controle.dao.DaoProdutoVendido;
import br.ifsp.controle.dao.DaoVenda;
import br.ifsp.modelo.infoprod.ProdutoVendido;
import java.util.List;

/**
 *
 * @author Thon
 */
public class TesteDaoProdutoVendido {
    
    public static void main(String[] args) {
        
        DaoVenda daoVenda = new DaoVenda();     
                
        DaoProduto daoProduto = new DaoProduto();
      
        ProdutoVendido tool = new ProdutoVendido(); //pesquisa
        tool.setProduto(daoProduto.read(1));
        tool.setVendaOrigem(daoVenda.read(2));
        
        DaoProdutoVendido banco = new DaoProdutoVendido();
        //List<ProdutoVendido> lista = banco.readAll(venda);
        ProdutoVendido aux = new ProdutoVendido();
        
        aux.setProduto(daoProduto.read(1));
        aux.setVendaOrigem(daoVenda.read(2));
        aux.setQuantidade(2);
        
        
        System.out.println(banco.create(aux));
        
        System.out.println(aux.getProduto().getRegistro().getNome());
        System.out.println(aux.getVendaOrigem().getId());
        System.out.println(aux.getQuantidade());
        
        
        
        
    }
}
