/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifsp.teste;

import br.ifsp.controle.dao.DaoLote;
import br.ifsp.controle.dao.DaoProduto;
import br.ifsp.controle.dao.DaoRegistroProduto;
import br.ifsp.modelo.infoprod.Produto;
import java.util.List;

/**
 *
 * @author Thon
 */
public class TesteProduto {
   
    public static void main(String[] args) {
        
        DaoRegistroProduto daoReg = new DaoRegistroProduto();
        DaoLote daoLote = new DaoLote();
        DaoProduto banco = new DaoProduto();
        Produto aux = new Produto();
        /*aux.setOrigem(daoLote.read(1));
        aux.setRegistro(daoReg.read(1));
        aux.setQuantidade(5);
        aux.setQuantidadeatual(5);
        aux.setStatus(true);
        aux.setValor(5);
        System.out.println(banco.create(aux));*/
        aux = banco.read(1);
        if(aux != null){            
            System.out.println("Id: " + aux.getId());
            System.out.println("Registro: " + aux.getRegistro().getNome());
            System.out.println("Lote: " + aux.getOrigem().getId());
            System.out.println("Quantidade inicial: " + aux.getQuantidade());
            System.out.println("Quantidade Atual: " + aux.getQuantidadeatual());
            System.out.println("Valor: " + aux.getValor());
            System.out.println("Status: " + aux.isStatus());
        }
        
        banco.update(aux);
        aux = banco.read(1);
        System.out.println("Id: " + aux.getId());
        System.out.println("Registro: " + aux.getRegistro().getNome());
        System.out.println("Lote: " + aux.getOrigem().getId());
        System.out.println("Quantidade inicial: " + aux.getQuantidade());
        System.out.println("Quantidade Atual: " + aux.getQuantidadeatual());
        System.out.println("Valor: " + aux.getValor());
        System.out.println("Status: " + aux.isStatus());
    }
}
