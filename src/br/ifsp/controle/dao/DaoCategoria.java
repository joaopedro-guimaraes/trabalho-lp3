package br.ifsp.controle.dao;

import br.ifsp.modelo.infoprod.Categoria;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DaoCategoria implements IDao<Categoria>{

    public boolean create(Categoria aux){
        if (aux == null)
            return false;
        ConnectionFactory<Categoria, Integer> conn = new ConnectionFactory<>();
        String sql = "INSERT INTO CATEGORIA(NOME, ATIVO) VALUES (UPPER(?), TRUE)";
        return  conn.executeNonQuery(sql, DaoCategoria::createProtection, aux);
    }                   

    public Categoria read(int id) {        
        ConnectionFactory<Categoria, Integer> conn = new ConnectionFactory();
        String sql = "SELECT * FROM CATEGORIA WHERE ID=?";
        List <Categoria> lista = conn.executeQuery(sql, DaoCategoria::readProtection, id, DaoCategoria::getDto, new Categoria());
        return (lista.isEmpty())? null : lista.get(0);
    }  
    
    public Categoria read(String nome) {
        
        ConnectionFactory<Categoria, String> conn = new ConnectionFactory();
        String sql = "SELECT * FROM CATEGORIA WHERE NOME LIKE UPPER(?)";
        List <Categoria> lista = conn.executeQuery(sql, DaoCategoria::readProtection, nome, DaoCategoria::getDto, new Categoria());
        return (lista.isEmpty())? null : lista.get(0);
    }  
    
    public List<Categoria> readAll(boolean status, String str) {
        
        ConnectionFactory<Categoria, String> conn = new ConnectionFactory<>();
        String sql = "";
        if(status)
            sql = "SELECT * FROM CATEGORIA WHERE ATIVO = TRUE AND (NOME LIKE UPPER(?) OR ID=?)";
        else
            sql = "SELECT * FROM CATEGORIA WHERE ATIVO = FALSE AND (NOME LIKE UPPER(?) OR ID=?)";
        
        return conn.executeQuery(sql, DaoCategoria::readAllProtection, str, DaoCategoria::getDto, new Categoria());
    }

    @Override
    public List<Categoria> readAll(String str) {

        ConnectionFactory<Categoria, String> conn = new ConnectionFactory<>();
        String sql = "SELECT * FROM CATEGORIA WHERE NOME LIKE UPPER(?) OR ID = ?";
        return conn.executeQuery(sql, DaoCategoria::readAllProtection, str, DaoCategoria::getDto, new Categoria());
    }
    
    public boolean update(Categoria aux) {        

        if (aux == null)
            return false;            
        ConnectionFactory<Categoria, Categoria> conn = new ConnectionFactory<>();
        String sql = "UPDATE CATEGORIA SET NOME=UPPER(?), ATIVO=TRUE  WHERE ID = ?";
        return conn.executeNonQuery(sql, DaoCategoria::updateProtection, aux);
    }
    
    public boolean delete(int id) {        
                    
        ConnectionFactory<Integer, Integer> conn = new ConnectionFactory<>();
        String sql = "UPDATE CATEGORIA SET ATIVO=FALSE WHERE ID=?";
        return conn.executeNonQuery(sql, DaoCategoria::deleteProtection, id);        
    }
    
    public static PreparedStatement createProtection(PreparedStatement query, Categoria aux){        
        try{
            query.setString(1, aux.getNome()); 
        }catch(SQLException e ){
            System.out.println("Erro, create protection categoria" + e);
        }
        return query;
    }
    public static PreparedStatement readProtection(PreparedStatement query, int id){
        try{
            query.setInt(1, id);
        }catch(SQLException e){
            System.out.println("Erro" + e);
        }
        return query;
    }
     public static PreparedStatement readProtection(PreparedStatement query, String nome){
        try{
            query.setString(1, nome);
        }catch(SQLException e){
            System.out.println("Erro" + e);
        }
        return query;
    }
     
     public static PreparedStatement readAllProtection(PreparedStatement query, String atributo){
        try{
            query.setString(1, "%" + atributo + "%");
            query.setString(2, atributo);
        }catch(SQLException e){
            System.out.println("Erro" + e);
        }
        return query;
    }
     public static PreparedStatement updateProtection(PreparedStatement query, Categoria aux){
        try{
            query.setString(1, aux.getNome());
            query.setInt(2, aux.getId());            
        }catch(SQLException e){
            System.out.println("Erro" + e);
        }
        return query;
    }
    public static PreparedStatement deleteProtection(PreparedStatement query, int id){        
        try{
            query.setInt(1, id); 
        }catch(SQLException e ){
            System.out.println("Erro, create protection categoria" + e);
        }
        return query;
    }
    
    public static Categoria getDto(ResultSet rs, Categoria aux ) {
        
        try {
            aux = new Categoria();
            aux.setId(rs.getInt("id"));
            aux.setNome(rs.getString("nome"));
            aux.setStatus(rs.getBoolean("ativo"));
            return aux;
        } catch (SQLException e) {
            System.out.println("Erro ao tentar ler categoria" + e);
        }
        return null;
    }
}
