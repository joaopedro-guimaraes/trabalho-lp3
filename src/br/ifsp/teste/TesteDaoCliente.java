/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifsp.teste;

import br.ifsp.controle.dao.DaoCliente;
import br.ifsp.modelo.Pessoa.Cliente;
import br.ifsp.modelo.Pessoa.Contato;
import br.ifsp.modelo.Pessoa.Endereco;
import java.util.Date;

/**
 *
 * @author Thon
 */
public class TesteDaoCliente {
    
    public static void main(String[] args) {
        
        //Contato cont = new Contato();
        //Endereco end = new Endereco();
        Cliente aux = new Cliente();       
       /* cont.setNumeroCelular("16997175476");
        cont.setNumeroResidencial("1633788174");
        cont.setEmail("thon_ataide@hotmail.com");
        end.setCep("455454454");
        end.setEstado("SP");
        end.setCidade("San xarles");
        end.setBairro("PQ Fehr");
        end.setRua("Alexandrina");
        end.setNumero("2456");
        end.setComplemento("");
        aux.setContatos(cont);
        aux.setEndereco(end);
        aux.setNome("Thon Ataide");
        aux.setCpf("8888888");
        aux.setRg("564987");
        aux.setNascimento(new Date());
        aux.setSexo(true);
        aux.setCredito(0);
        aux.setStatus(true);*/
        DaoCliente banco = new DaoCliente(); 
        aux = banco.read(4);    
        //aux.setNome("Carlão");
        //banco.create(aux);
        System.out.println("Id: " + aux.getId());
        System.out.println("Nome: " + aux.getNome());
        System.out.println("Cpf: " + aux.getCpf());
        System.out.println("rg: " + aux.getRg());
        System.out.println("sexo: " + ((aux.isSexo())? "Masculino" : "Feminino"));
        System.out.println("nascimento: " + aux.getNascimento());
        System.out.println("crédito: " + aux.getCredito());
        System.out.println("cep: " + aux.getEndereco().getCep());
        System.out.println("estado: " + aux.getEndereco().getEstado());
        System.out.println("cidade: " + aux.getEndereco().getCidade());
        System.out.println("bairro: " + aux.getEndereco().getBairro());
        System.out.println("rua: " + aux.getEndereco().getRua());
        System.out.println("numero: " + aux.getEndereco().getNumero());
        System.out.println("complemento: " + aux.getEndereco().getComplemento());
        System.out.println("celular: " + aux.getContatos().getNumeroCelular());
        System.out.println("telefone: " + aux.getContatos().getNumeroResidencial());
        System.out.println("email: " + aux.getContatos().getEmail());
              
        
    }
}
