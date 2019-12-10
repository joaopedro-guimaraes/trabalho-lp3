/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifsp.teste;

import br.ifsp.controle.dao.DaoFornecedor;
import br.ifsp.modelo.Pessoa.Contato;
import br.ifsp.modelo.infoprod.Fornecedor;
import java.util.List;

/**
 *
 * @author joao
 */
public class TesteDaoFornecedor {
    
    public static void main(String args[]) {
        
        Fornecedor aux = new Fornecedor();
        DaoFornecedor banco = new DaoFornecedor();
        
        // Create
        
        aux.setNome("americanas");
        aux.setCnpj("2222");
        
        Contato contato = new Contato();
        contato.setDono(aux);
        contato.setEmail("mericanas@email");
        contato.setNumeroCelular("8888-8888");
        contato.setNumeroResidencial("9999-9999");
        
        aux.setContato(contato);
        
        System.out.println(banco.create(aux));
        
        // 
       
        //read All
        /*List<Fornecedor> lista = banco.readAll(false, "");
        
        for(Fornecedor f:lista)
            System.out.println(f.getId() + "\n" + f.getNome() + "\n" + f.getCnpj() + "\n" + f.getContato().getEmail() + "\n" + f.isStatus());
       
        
        //System.out.println(banco.update(banco.read(1)));
        
        /*if(aux == null)
            System.out.println("Objeto não encontrado");
        else {
            System.out.println(aux.getId() + "\n" + aux.getNome() + "\n" + aux.getCnpj() + "\n" + aux.getContato().getEmail());
        }*/
         
    }
}
