/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifsp.teste;

import br.ifsp.controle.dao.DaoCategoria;
import br.ifsp.modelo.infoprod.Categoria;
import java.util.List;

/**
 *
 * @author Thon
 */
public class TesteDaoCategoria {
    
    public static void main(String args[]){
        
        DaoCategoria banco = new DaoCategoria();
        
        Categoria aux = banco.read(1);
        
        if(aux == null)
            System.out.println("lista = nulo");
        else
            System.out.println("Id:" + aux.getId() + " nome: " + aux.getNome());
        
    }
}
