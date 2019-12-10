package br.ifsp.controle.dao;

import br.ifsp.modelo.infoprod.Produto;
import br.ifsp.modelo.infoprod.ProdutoVendido;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DaoProdutoVendido {

    public boolean create(ProdutoVendido aux){
        ConnectionFactory<ProdutoVendido, Integer> con = new ConnectionFactory<>();
        String sql = "INSERT INTO PRODUTOVENDIDO(VENDA, PRODUTO, QUANTIDADE) VALUES (?, ?, ?)";
        return con.executeNonQuery(sql, DaoProdutoVendido::createProtection, aux);        
    }
    
    public ProdutoVendido read(ProdutoVendido aux){
        ConnectionFactory<ProdutoVendido, ProdutoVendido> con = new ConnectionFactory<>();
        String sql = "SELECT * FROM PRODUTOVENDIDO WHERE VENDA=? AND PRODUTO=?";
        List<ProdutoVendido> lista = con.executeQuery(sql, DaoProdutoVendido::deleteProtection, aux, DaoProdutoVendido::getDto, new ProdutoVendido());
        return (lista.isEmpty())?null:lista.get(0);
    }
    
    public List<ProdutoVendido> readAll(int venda){
        ConnectionFactory<ProdutoVendido, Integer> con = new ConnectionFactory<>();
        String sql = "SELECT * FROM PRODUTOVENDIDO WHERE VENDA=?";
        List<ProdutoVendido> lista = con.executeQuery(sql, DaoProdutoVendido::readAllProtection, venda, DaoProdutoVendido::getDto, new ProdutoVendido());
        return (lista.isEmpty())?null: lista;
    }
    
    public List<ProdutoVendido> readAll(Produto aux){
        ConnectionFactory<ProdutoVendido, Integer> con = new ConnectionFactory<>();
        String sql = "SELECT * FROM PRODUTOVENDIDO WHERE PRODUTO=?";
        List<ProdutoVendido> lista = con.executeQuery(sql, DaoProdutoVendido::readAllProtection, aux.getId(), DaoProdutoVendido::getDto, new ProdutoVendido());
        return (lista.isEmpty())?null: lista;
    }
    
    public boolean update(ProdutoVendido aux){
        ConnectionFactory<ProdutoVendido, Integer> con = new ConnectionFactory<>();
        String sql = "UPDATE PRODUTOVENDIDO SET QUANTIDADE=? WHERE VENDA=? AND PRODUTO=?";
        return con.executeNonQuery(sql, DaoProdutoVendido::updateProtection, aux);        
    }
    
    public boolean delete(ProdutoVendido aux){
        ConnectionFactory<ProdutoVendido, Integer> con = new ConnectionFactory<>();
        String sql = "DELETE FROM PRODUTOVENDIDO WHERE VENDA=? AND PRODUTO=?";
        return con.executeNonQuery(sql, DaoProdutoVendido::deleteProtection, aux);        
    }
        
    public static PreparedStatement createProtection(PreparedStatement query, ProdutoVendido aux){
        
        try{
            query.setInt(1, aux.getVendaOrigem().getId());
            query.setInt(2, aux.getProduto().getId());
            query.setInt(3, aux.getQuantidade());
            
        }catch(SQLException e){
            System.out.println("Erro em createProtection, DaoProdutoVendido. " + e);
        }        
        return query;
    }
        
    public static PreparedStatement readAllProtection(PreparedStatement query, int id){
        
        try{
            query.setInt(1, id);            
        }catch(SQLException e){
            System.out.println("Erro em createProtection, DaoProdutoVendido. " + e);
        }        
        return query;
    }
    
    public static PreparedStatement updateProtection(PreparedStatement query, ProdutoVendido aux){
        
        try{
            query.setInt(1, aux.getQuantidade());
            query.setInt(2, aux.getVendaOrigem().getId());
            query.setInt(3, aux.getProduto().getId());   
        }catch(SQLException e){
            System.out.println("Erro em createProtection, DaoProdutoVendido. " + e);
        }        
        return query;
    }
    
    public static PreparedStatement deleteProtection(PreparedStatement query, ProdutoVendido aux){
        
        try{            
            query.setInt(1, aux.getVendaOrigem().getId());
            query.setInt(2, aux.getProduto().getId());   
        }catch(SQLException e){
            System.out.println("Erro em deleteProtection, DaoProdutoVendido. " + e);
        }        
        return query;
    }
    
    public static ProdutoVendido getDto(ResultSet rs, ProdutoVendido aux){       
        
        DaoProduto daoProduto = new DaoProduto();
        DaoVenda daoVenda = new DaoVenda();        
        aux = new ProdutoVendido();        
        try{
            aux.setVendaOrigem(daoVenda.read(rs.getInt("VENDA")));
            aux.setProduto(daoProduto.read(rs.getInt("PRODUTO")));
            aux.setQuantidade(rs.getInt("QUANTIDADE"));                        
        }catch(SQLException e){
            System.out.println("Erro em getDto, DaoVenda. " + e);
        }        
        return aux;
    }
}
