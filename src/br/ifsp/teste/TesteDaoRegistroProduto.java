/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifsp.teste;

import br.ifsp.controle.dao.DaoCategoria;
import br.ifsp.controle.dao.DaoRegistroProduto;
import br.ifsp.modelo.infoprod.RegistroProduto;
import java.util.List;

/**
 *
 * @author Thon
 */
public class TesteDaoRegistroProduto {
    
    public static void main(String[] args) {
        DaoCategoria daoCat = new DaoCategoria();
        RegistroProduto aux = new RegistroProduto();
        aux.setNome("Regata Nike");
        aux.setDescricao("Preta, tamanho M.");
        aux.setCodigo("56487");
        aux.setStatus(true);
        aux.setCategoria(daoCat.read(1));
        DaoRegistroProduto banco = new DaoRegistroProduto();
        banco.create(aux);
       // System.out.println(banco.delete(1));
        //aux = banco.read(1);
        //banco.update(aux);
       // banco.create(aux);
        //banco.delete(3);
        /*List<RegistroProduto> lista = banco.readAll("calças");
        for(RegistroProduto aux :lista)
        System.out.println("Id: " + aux.getId() + "Nome: " + aux.getNome() + "Código: " + 
                aux.getCodigo() + "Descrição: " + aux.getDescricao() + "Status: " + aux.isStatus() + 
                "Categoria: " + aux.getCategoria().getId() + " " + aux.getCategoria().getNome());*/
    }
}
