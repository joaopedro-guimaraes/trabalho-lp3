/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifsp.teste;

import br.ifsp.controle.dao.DaoCliente;
import br.ifsp.controle.dao.DaoVendedor;
import br.ifsp.modelo.Pessoa.Contato;
import br.ifsp.modelo.Pessoa.Endereco;
import br.ifsp.modelo.Pessoa.Vendedor;
import br.ifsp.utils.CarregaLogin;
import br.ifsp.utils.Criptografia;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Thon
 */
public class TesteVendedor {
    
    
    public static void main(String[] args) {
        Criptografia util = new Criptografia();
        Contato cont = new Contato();
        Endereco end = new Endereco();
        Vendedor aux = new Vendedor();    
        DaoVendedor banco = new DaoVendedor();
        /*cont.setNumeroCelular("16997175476");
        cont.setNumeroResidencial("1633788174");
        cont.setEmail("EnokiMilGrau@hotmail.com");
        end.setCep("455454454");
        end.setEstado("SP");
        end.setCidade("Analandia");
        end.setBairro("Santa Felícia");
        end.setRua("Alexandrina");
        end.setNumero("8785");
        end.setComplemento("");
        aux.setContatos(cont);
        aux.setEndereco(end);
        aux.setNome("Enoki Brocador");
        aux.setCpf("66666666");
        aux.setRg("54654654");
        aux.setNascimento(new Date());
        aux.setSexo(true);        
        aux.setStatus(true);
        aux.setCargo(true);
         
        try {
            aux.setLogin(util.criptografa("123456"));
            aux.setSenha(util.criptografa("654321"));
        } catch (NullPointerException ex) {
            Logger.getLogger(TesteVendedor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(TesteVendedor.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(banco.create(aux));*/
        //CarregaLogin keys = new CarregaLogin();
        /*try {
            keys.login = util.criptografa("123456");
            keys.senha = util.criptografa("654321");
        } catch (NullPointerException ex) {
            Logger.getLogger(TesteVendedor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(TesteVendedor.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        //List<Vendedor> lista = banco.readAll("");
        /*aux = banco.readAll(true,"3").get(0); 
        if(aux != null){
        System.out.println("Id: " + aux.getId());
        System.out.println("Nome: " + aux.getNome());
        System.out.println("Cpf: " + aux.getCpf());
        System.out.println("rg: " + aux.getRg());
        System.out.println("sexo: " + ((aux.isSexo())? "Masculino" : "Feminino"));
        System.out.println("nascimento: " + aux.getNascimento());        
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
        }*/
        System.out.println(banco.ContFuncionario());
    }
        
}
