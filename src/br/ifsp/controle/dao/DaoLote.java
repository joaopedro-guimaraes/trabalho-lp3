package br.ifsp.controle.dao;

import br.ifsp.modelo.infoprod.Lote;
import br.ifsp.utils.QueryBetweenInt;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DaoLote implements IDao<Lote> {

    @Override
    public boolean create(Lote aux) {
        ConnectionFactory<Lote, Integer> conn = new ConnectionFactory<>();
        String sql="INSERT INTO LOTE (FORNECEDOR, DATALOTE, VALOR, LUCRO, ATIVO) VALUES (?, ?, ?, ?, TRUE)";
        return conn.executeNonQuery(sql, DaoLote::createProtection, aux);
    }
    
    @Override
    public Lote read(int id) {
        ConnectionFactory<Lote, Integer> conn = new ConnectionFactory<>();
        String sql="SELECT * FROM LOTE WHERE ID=?";
        List<Lote>lista = conn.executeQuery(sql, DaoLote::readProtection, id, DaoLote::getDto, new Lote());
        return (lista.isEmpty())? null: lista.get(0);   
    }
    @Override
    public List<Lote> readAll(boolean status, String str) {
        ConnectionFactory<Lote, String> conn = new ConnectionFactory<>();
        String sql= (status)?"SELECT * FROM LOTE WHERE ATIVO=TRUE AND ID LIKE ?" : "SELECT * FROM LOTE WHERE ATIVO=FALSE AND ID LIKE ?";
        return conn.executeQuery(sql, DaoLote::readAllProtection, str, DaoLote::getDto, new Lote());        
    }  
    
    public List<Lote> readAll(QueryBetweenInt dadosPesquisa ) {
        ConnectionFactory<Lote, QueryBetweenInt> conn = new ConnectionFactory<>();
        String sql="SELECT * FROM LOTE WHERE ID=? AND (DATALOTE BETWEEN ? AND ?)";
        return conn.executeQuery(sql, DaoLote::readAllProtection, dadosPesquisa, DaoLote::getDto, new Lote());        
    }  
    
    @Override
    public List<Lote> readAll(String str) {
       ConnectionFactory<Lote, String> conn = new ConnectionFactory<>();
        String sql="SELECT * FROM LOTE WHERE ID LIKE ?";
        return conn.executeQuery(sql, DaoLote::readAllProtection, str, DaoLote::getDto, new Lote());
    }
    
    @Override
    public boolean update(Lote aux) {
        ConnectionFactory<Lote, Integer> conn = new ConnectionFactory<>();
        String sql="UPDATE LOTE SET FORNECEDOR=?, DATALOTE=?, VALOR=?, LUCRO=?, ATIVO=TRUE WHERE ID=?";
        return conn.executeNonQuery(sql, DaoLote::updateProtection, aux);
    }   

    @Override
    public boolean delete(int id) {
        ConnectionFactory<Integer, Integer> conn = new ConnectionFactory<>();
        String sql="UPDATE LOTE SET ATIVO=FALSE WHERE ID=?";
        return conn.executeNonQuery(sql, DaoLote::deleteProtection, id);
    }
    
    public static PreparedStatement createProtection(PreparedStatement query, Lote aux){
        
        try{            
            query.setInt(1, aux.getOrigem().getId());
            query.setDate(2, new Date(aux.getData().getTime()));
            query.setDouble(3, aux.getValor());
            query.setDouble(4, aux.getLucroPerct());
            
        }catch(SQLException e){
            System.out.println("Erro, create protection, DaoLote. " + e);
        }
        return query;
    }
    
    public static PreparedStatement readProtection(PreparedStatement query, int id){        
        try{            
            query.setInt(1, id);            
        }catch(SQLException e){
            System.out.println("Erro, read protection, DaoLote. " + e);
        }
        return query;
    }       
    
    public static PreparedStatement readAllProtection(PreparedStatement query, QueryBetweenInt aux){        
        try{            
            query.setInt(1, aux.id);
            query.setDate(2, new Date(aux.inicio.getTime()));  
            query.setDate(3, new Date(aux.fim.getTime()));
        }catch(SQLException e){
            System.out.println("Erro, readAll protection, DaoLote. " + e);
        }
        return query;
    }
    
    public static PreparedStatement readAllProtection(PreparedStatement query, String str){        
        try{            
            query.setString(1, str + "%");            
        }catch(SQLException e){
            System.out.println("Erro, readAll protection, DaoLote. " + e);
        }
        return query;
    } 
    
    public static PreparedStatement updateProtection(PreparedStatement query, Lote aux){
        
        try{            
            query.setInt(1, aux.getOrigem().getId());
            query.setDate(2, new Date(aux.getData().getTime()));
            query.setDouble(3, aux.getValor());
            query.setDouble(4, aux.getLucroPerct());
            query.setInt(5, aux.getId());
            
        }catch(SQLException e){
            System.out.println("Erro, create protection, DaoLote. " + e);
        }
        return query;
    }
    
    public static PreparedStatement deleteProtection(PreparedStatement query, int id){
        
        try{            
            query.setInt(1, id);            
        }catch(SQLException e){
            System.out.println("Erro, create protection, DaoLote. " + e);
        }
        return query;
    }
    
    public static Lote getDto(ResultSet rs, Lote aux){
        
        try{
            aux = new Lote();
            DaoFornecedor daoFornecedor = new DaoFornecedor();
            aux.setId(rs.getInt("ID"));
            aux.setOrigem(daoFornecedor.read(rs.getInt("FORNECEDOR")));
            aux.setData(rs.getDate("DATALOTE"));
            aux.setValor(rs.getDouble("VALOR"));
            aux.setLucroPerct(rs.getDouble("LUCRO"));
            aux.setStatus(rs.getBoolean("ATIVO"));
            return aux;
        }catch(SQLException e){
            System.out.println("Erro em getDto, DaoLote. " + e);
        }
        return null;
    }
    
}
