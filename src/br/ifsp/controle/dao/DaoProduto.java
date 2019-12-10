package br.ifsp.controle.dao;

import br.ifsp.modelo.infoprod.Lote;
import br.ifsp.modelo.infoprod.Produto;
import br.ifsp.utils.QueryLoteRegistroEmProduto;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DaoProduto implements IDao<Produto>{

    @Override
    public boolean create(Produto aux) {
         ConnectionFactory<Produto, Integer> conn = new ConnectionFactory<>();
         String sql="INSERT INTO PRODUTO (REGISTRO, LOTE, VALOR, QUANTIDADEINICIAL, "
                 + "QUANTIDADEATUAL, ATIVO) VALUES (UPPER(?), UPPER(?), UPPER(?), UPPER(?), UPPER(?), TRUE)";         
         return conn.executeNonQuery(sql, DaoProduto::createProtection, aux);
    }
    
    @Override
    public Produto read(int id) {
        ConnectionFactory<Produto, Integer> conn = new ConnectionFactory<>();
        String sql= "SELECT * FROM PRODUTO WHERE ID = ? ";   
        List<Produto> lista = conn.executeQuery(sql, DaoProduto::readProtection, id, DaoProduto::getDto, new Produto());
        return (lista.isEmpty())? null: lista.get(0);
    }
    
    @Override
    public List<Produto> readAll(String str) {
        ConnectionFactory<Produto, String> conn = new ConnectionFactory<>();
        String sql= "SELECT * FROM PRODUTO PROD, REGISTROPRODUTO REGI WHERE (REGI.NOME LIKE UPPER(?) OR "
                + "REGI.CODIGO LIKE UPPER(?) OR PROD.ID=?) AND PROD.REGISTRO = REGI.ID";   
        List<Produto> lista = conn.executeQuery(sql, DaoProduto::readAllProtection, str, DaoProduto::getDto, new Produto());
        return lista;
    }
    
    @Override
    public List<Produto> readAll(boolean status, String str) {
        ConnectionFactory<Produto, String> conn = new ConnectionFactory<>();
        String sql="";
        if(status){
            sql= "SELECT * FROM PRODUTO PROD, REGISTROPRODUTO REGI WHERE PROD.ATIVO=TRUE AND (REGI.NOME LIKE UPPER(?) OR REGI.CODIGO LIKE UPPER(?) OR PROD.ID=?) AND PROD.REGISTRO = REGI.ID";   
        }else{
            sql= "SELECT * FROM PRODUTO PROD, REGISTROPRODUTO REGI WHERE PROD.ATIVO=FALSE AND (REGI.NOME LIKE UPPER(?) OR REGI.CODIGO LIKE UPPER(?) OR PROD.ID=?) AND PROD.REGISTRO = REGI.ID";
        }
            
        List<Produto> lista = conn.executeQuery(sql, DaoProduto::readAllProtection, str, DaoProduto::getDto, new Produto());
        return lista;
    }
    
    @Override
    public boolean update(Produto aux) {
        ConnectionFactory<Produto, Integer> conn = new ConnectionFactory<>();
         String sql="UPDATE PRODUTO SET REGISTRO=?, LOTE=?, VALOR=?, QUANTIDADEINICIAL=?, "
                 + "QUANTIDADEATUAL=?, ATIVO=TRUE WHERE ID=?";         
         return conn.executeNonQuery(sql, DaoProduto::updateProtection, aux);
    }
    
    @Override
    public boolean delete(int id) {
        ConnectionFactory<Integer, Integer> conn = new ConnectionFactory<>();
         String sql="UPDATE PRODUTO SET ATIVO=FALSE WHERE ID=?";         
         return conn.executeNonQuery(sql, DaoProduto::readProtection, id);  
    }
    
    public boolean desativaPorLote(int lote){
        ConnectionFactory<Integer, Integer> banco = new ConnectionFactory<>();
        String sql = "UPDATE PRODUTO SET ATIVO=FALSE WHERE LOTE=?";
        return banco.executeNonQuery(sql, DaoProduto::desativaPorLoteProtection, lote);
    }
    
    public Produto read(QueryLoteRegistroEmProduto loteRegistro){
        ConnectionFactory<Produto, QueryLoteRegistroEmProduto> banco = new ConnectionFactory<>();
        String sql = "SELECT * FROM PRODUTO WHERE LOTE=? AND REGISTRO=?";
        List<Produto> lista = banco.executeQuery(sql, DaoProduto::readPorLoteRegistroProtection, loteRegistro, DaoProduto::getDto, new Produto());
        return (lista.isEmpty())? null: lista.get(0);
    }
    
    public static PreparedStatement createProtection(PreparedStatement query, Produto aux){
        
        try{
            query.setInt(1, aux.getRegistro().getId());
            query.setInt(2, aux.getOrigem().getId());
            query.setDouble(3, aux.getValor());
            query.setInt(4, aux.getQuantidade());
            query.setInt(5, aux.getQuantidadeatual());
        }catch(SQLException e){
            System.out.println("Erro em DaoProduto, createProtection. " + e);
        }
        return query;
    }
    
    public static PreparedStatement readProtection(PreparedStatement query, int id){        
        try{
            query.setInt(1, id);            
        }catch(SQLException e){
            System.out.println("Erro em DaoProduto, readProtection. " + e);
        }
        return query;
    }

    public static PreparedStatement readAllProtection(PreparedStatement query, String str){        
        try{
            query.setString(1, str + "%");
            query.setString(2, str + "%");
            query.setString(3, str);
        }catch(SQLException e){
            System.out.println("Erro em DaoProduto, readAllProtection. " + e);
        }
        return query;
    }    

    public static PreparedStatement updateProtection(PreparedStatement query, Produto aux){
        
        try{
            query.setInt(1, aux.getRegistro().getId());
            query.setInt(2, aux.getOrigem().getId());
            query.setDouble(3, aux.getValor());
            query.setInt(4, aux.getQuantidade());
            query.setInt(5, aux.getQuantidadeatual());
            query.setInt(6, aux.getId());
        }catch(SQLException e){
            System.out.println("Erro em DaoProduto, updateProtection. " + e);
        }
        return query;
    }
    
    public static PreparedStatement desativaPorLoteProtection(PreparedStatement query, int lote){
        try{
            query.setInt(1, lote);            
        }catch(SQLException e){
            System.out.println("Erro em DaoProduto, desativa por loteProtection");
        }
        return query;
    }
    
    public static PreparedStatement readPorLoteRegistroProtection(PreparedStatement query, QueryLoteRegistroEmProduto loteRegistro){
        try{
            query.setInt(1, loteRegistro.lote);
            query.setInt(2, loteRegistro.registro);
        }catch(SQLException e){
            System.out.println("Erro em DaoProduto, desativa por loteProtection");
        }
        return query;
    }
    public static Produto getDto(ResultSet rs, Produto aux){
        
        try{
            DaoRegistroProduto daoReg = new DaoRegistroProduto();
            DaoLote daoLote = new DaoLote();
            aux = new Produto();
            
            aux.setOrigem(daoLote.read(rs.getInt("LOTE")));
            aux.setRegistro(daoReg.read(rs.getInt("REGISTRO")));
            aux.setId(rs.getInt("ID"));
            aux.setQuantidade(rs.getInt("QUANTIDADEINICIAL"));
            aux.setQuantidadeatual(rs.getInt("QUANTIDADEATUAL"));
            aux.setValor(rs.getDouble("VALOR"));
            aux.setStatus(rs.getBoolean("ATIVO"));
            
        }catch(SQLException e){
            
        }
        
        
        return aux;
    }
    
}
