/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifsp.controle.dao;

import br.ifsp.modelo.venda.Pagamento;
import br.ifsp.modelo.venda.Venda;
import br.ifsp.utils.QueryBetweenInt;
import br.ifsp.utils.QueryReadPagamento;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Thon
 */
public class DaoPagamento {
    
    public boolean create(Pagamento aux){
        
        ConnectionFactory<Pagamento, Integer> con = new ConnectionFactory<>();
        String sql = "INSERT INTO PAGAMENTO (DATAPAG, VENDA, VALOR, ATIVO) VALUES (?, ?, ?, TRUE)";
        return con.executeNonQuery(sql, DaoPagamento::createProtection, aux);
    }
    
    public Pagamento read(QueryReadPagamento load){
        ConnectionFactory<Pagamento, QueryReadPagamento> con = new ConnectionFactory<>();
        String sql = "SELECT * FROM PAGAMENTO WHERE DATAPAG=? AND VENDA=?";
        List<Pagamento> lista = con.executeQuery(sql, DaoPagamento::readProtection, load, DaoPagamento::getDto, new Pagamento());
        return (lista.isEmpty())? null: lista.get(0);
    }
    
    public List<Pagamento> readAll(int venda){
        ConnectionFactory<Pagamento, Integer> con = new ConnectionFactory<>();
        String sql = "SELECT * FROM PAGAMENTO WHERE VENDA=?";
        return con.executeQuery(sql, DaoPagamento::readAllProtection, venda, DaoPagamento::getDto, new Pagamento());
    }
    
    public List<Pagamento> readAll(QueryBetweenInt intervalo){
        ConnectionFactory<Pagamento, QueryBetweenInt> con = new ConnectionFactory<>();
        String sql = "SELECT * FROM PAGAMENTO WHERE (DATAPAG BETWEEN ? AND ?) ";
        return con.executeQuery(sql, DaoPagamento::readAllProtection, intervalo, DaoPagamento::getDto, new Pagamento());
    }
    
    public boolean update(Pagamento aux){
        
        ConnectionFactory<Pagamento, Integer> con = new ConnectionFactory<>();
        String sql = "UPDATE PAGAMENTO SET DATAPAG=?, VALOR=?, ATIVO=? WHERE DATAPAG=? AND VENDA=?";
        return con.executeNonQuery(sql, DaoPagamento::updateProtection, aux);
    }
    
    public boolean delete(Pagamento aux){
        
        ConnectionFactory<Pagamento, Integer> con = new ConnectionFactory<>();
        String sql = "DELETE FROM PAGAMENTO WHERE DATAPAG=? AND VENDA=?";
        return con.executeNonQuery(sql, DaoPagamento::deleteProtection, aux);
    }
    
    public static PreparedStatement createProtection(PreparedStatement query, Pagamento aux){
        
        try{
            query.setDate(1, new Date(aux.getData().getTime()));
            query.setInt(2, aux.getOrigem().getId());
            query.setDouble(3, aux.getValor());            
        }catch(SQLException e){
            System.out.println("Erro em DaoPagamento. " + e);
        }        
        return query;
    }
    
    public static PreparedStatement readProtection(PreparedStatement query, QueryReadPagamento aux){
        
        try{
            query.setDate(1, new Date(aux.data.getTime()));
            query.setInt(2, aux.venda.getId());                     
        }catch(SQLException e){
            System.out.println("Erro em DaoPagamento. " + e);
        }        
        return query;
    }
    
    public static PreparedStatement readAllProtection(PreparedStatement query, int venda){
        
        try{
            query.setInt(1, venda);
                           
        }catch(SQLException e){
            System.out.println("Erro em DaoPagamento. " + e);
        }        
        return query;
    }
    
    public static PreparedStatement readAllProtection(PreparedStatement query, QueryBetweenInt intervalo){
        
        try{
            query.setDate(1, new Date(intervalo.inicio.getTime()));
            query.setDate(2, new Date(intervalo.fim.getTime()));           
        }catch(SQLException e){
            System.out.println("Erro em DaoPagamento. " + e);
        }        
        return query;
    }
    
    public static PreparedStatement updateProtection(PreparedStatement query, Pagamento aux){
        
        try{
            query.setDate(1, new Date(aux.getData().getTime()));            
            query.setDouble(2, aux.getValor());            
            query.setBoolean(3, aux.isStatus());
            query.setDate(4, new Date(aux.getData().getTime()));
            query.setInt(5, aux.getOrigem().getId());            
        }catch(SQLException e){
            System.out.println("Erro em DaoPagamento. " + e);
        }        
        return query;
    }
    
    public static PreparedStatement deleteProtection(PreparedStatement query, Pagamento aux){
        
        try{
            query.setDate(1, new Date(aux.getData().getTime()));
            query.setInt(2, aux.getOrigem().getId());                     
        }catch(SQLException e){
            System.out.println("Erro em DaoPagamento. " + e);
        }        
        return query;
    }
    
    public static Pagamento getDto(ResultSet rs, Pagamento aux){
        
        aux = new Pagamento();
        DaoVenda daoVenda = new DaoVenda();
        try{
            aux.setData(rs.getDate("DATAPAG"));
            aux.setOrigem(daoVenda.read(rs.getInt("VENDA")));
            aux.setValor(rs.getDouble("VALOR"));
            aux.setStatus(rs.getBoolean("ATIVO"));
        }catch(SQLException e){
            System.out.println("Erro em DaoPagamento, método venda. " + e);
        }
        return aux;
    }
}
