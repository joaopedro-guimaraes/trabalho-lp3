/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifsp.teste;

import br.ifsp.controle.dao.DaoCliente;
import br.ifsp.controle.dao.DaoPagamento;
import br.ifsp.controle.dao.DaoProdutoVendido;
import br.ifsp.controle.dao.DaoVenda;
import br.ifsp.controle.dao.DaoVendedor;
import br.ifsp.modelo.Pessoa.Cliente;
import br.ifsp.modelo.Pessoa.Vendedor;
import br.ifsp.modelo.infoprod.ProdutoVendido;
import br.ifsp.modelo.venda.Pagamento;
import br.ifsp.modelo.venda.Venda;
import br.ifsp.utils.CarregaListas;
import br.ifsp.utils.QueryBetweenInt;
import br.ifsp.utils.QueryBetweenString;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Thon
 */
public class TesteDaoVenda {
    
    public static void main(String[] args) {
        
        DaoVendedor daoVendedor = new DaoVendedor();
        DaoCliente daoCliente = new DaoCliente();
        //Venda aux = new Venda();        
        /*aux.setComprador(daoCliente.read(1));
        aux.setFuncionario(daoVendedor.read(3));
        aux.setData(new Date());*/
        QueryBetweenInt query = new QueryBetweenInt();
               
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        try {
           query.inicio = formato.parse("01/01/2018");
           query.fim = formato.parse("06/11/2018");
        } catch (ParseException ex) {
            Logger.getLogger(TesteDaoVenda.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        DaoVenda banco = new DaoVenda();
        List<Venda> lista = banco.readAll(query);
        //aux.setDesconto(50);       
        
        
        /*for(Venda aux:lista)
            CarregaListas.carregaPagamentosEProdutos(aux); */          
        Vendedor teste = new Vendedor();
        for(Venda aux:lista){
        System.out.println("Id: " + aux.getId());
        System.out.println("Comprador: " + aux.getComprador().getNome());
        System.out.println("Vendedor: " + aux.getFuncionario().getNome());
        System.out.println("Data da venda: " + aux.getData());
        System.out.println("Valor total: " + aux.getTotal());
        if(aux.itens != null)
        for(ProdutoVendido pv: aux.itens){
        System.out.println("Produto Vendido: " + pv.getProduto().getRegistro().getNome());
        System.out.println("Quantia: "+ pv.getQuantidade());        
        }
        if(aux.pagamentos != null)
        for(Pagamento pg: aux.pagamentos){
        System.out.println("Data Vencimento: " + pg.getData());
        System.out.println("Valor: " + pg.getValor());     
        System.out.println("Pago: " + pg.isStatus());
        }
        teste = aux.getFuncionario();
        System.out.println("Vendedor");
        System.out.println("Id: " + teste.getId());
        System.out.println("Nome: " + teste.getNome());
        System.out.println("Cpf: " + teste.getCpf());
        System.out.println("rg: " + teste.getRg());
        System.out.println("sexo: " + ((teste.isSexo())? "Masculino" : "Feminino"));
        System.out.println("nascimento: " + teste.getNascimento());
        System.out.println("Cargo: " + ((teste.isCargo())?"Admin" : "Funcionário"));
        System.out.println("cep: " + teste.getEndereco().getCep());
        System.out.println("estado: " + teste.getEndereco().getEstado());
        System.out.println("cidade: " + teste.getEndereco().getCidade());
        System.out.println("bairro: " + teste.getEndereco().getBairro());
        System.out.println("rua: " + teste.getEndereco().getRua());
        System.out.println("numero: " + teste.getEndereco().getNumero());
        System.out.println("complemento: " + teste.getEndereco().getComplemento());
        System.out.println("celular: " + teste.getContatos().getNumeroCelular());
        System.out.println("telefone: " + teste.getContatos().getNumeroResidencial());
        System.out.println("email: " + teste.getContatos().getEmail());
        }    
    }   
    
}
