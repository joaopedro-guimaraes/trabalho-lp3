package br.ifsp.controle.dao;

import br.ifsp.modelo.Pessoa.Contato;
import java.util.List;
import br.ifsp.modelo.infoprod.Fornecedor;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DaoFornecedor implements IDao<Fornecedor> {

    @Override
    public boolean create(Fornecedor aux) {

            ConnectionFactory<Fornecedor, Integer> conn = new ConnectionFactory<>();
            String sql = "INSERT INTO FORNECEDOR(NOME, CNPJ, TELCELULAR, TELRESIDENCIAL, EMAIL, ATIVO) VALUES(UPPER(?), UPPER(?), UPPER(?), UPPER(?), UPPER(?), TRUE);";
            return conn.executeNonQuery(sql, DaoFornecedor::createProtection, aux);
    }

    @Override
    public Fornecedor read(int id) {

        ConnectionFactory<Fornecedor, Integer> conn = new ConnectionFactory<>();
        String sql = "SELECT * FROM FORNECEDOR WHERE ID = ?";
        List<Fornecedor> lista = conn.executeQuery(sql, DaoFornecedor::readProtection, id, DaoFornecedor::getDto, new Fornecedor());
        return (lista.isEmpty())? null: lista.get(0);
    }
    
    public Fornecedor read(String str) {
        ConnectionFactory<Fornecedor, String> conn = new ConnectionFactory<>();
        String sql = "SELECT * FROM FORNECEDOR WHERE NOME LIKE UPPER(?)";
        List<Fornecedor> lista = conn.executeQuery(sql, DaoFornecedor::readProtection, str, DaoFornecedor::getDto, new Fornecedor());
        return (lista.isEmpty())? null: lista.get(0); //se lista vazia retorna null, senão retorna primeiro elemento
    }

    @Override
    public List<Fornecedor> readAll(boolean status, String str) {

        ConnectionFactory<Fornecedor, String> conn = new ConnectionFactory<>();
        String sql = "";
        if(status)
            sql = "SELECT * FROM FORNECEDOR WHERE ATIVO=TRUE AND (NOME LIKE UPPER(?) OR CNPJ LIKE UPPER(?) OR ID=?)";
        else
            sql = "SELECT * FROM FORNECEDOR WHERE ATIVO=FALSE AND (NOME LIKE UPPER(?) OR CNPJ LIKE UPPER(?) OR ID=?)";
        return conn.executeQuery(sql, DaoFornecedor::readaAllProtection, str, DaoFornecedor::getDto, new Fornecedor());
    }

    @Override
    public List<Fornecedor> readAll(String str) {

        ConnectionFactory<Fornecedor, String> conn = new ConnectionFactory<>();
        String sql = "SELECT * FROM FORNECEDOR WHERE NOME LIKE UPPER(?) OR CNPJ LIKE UPPER(?) OR ID=?";
        return conn.executeQuery(sql, DaoFornecedor::readaAllProtection, str, DaoFornecedor::getDto, new Fornecedor());
    }

    @Override
    public boolean update(Fornecedor aux) {
        ConnectionFactory<Fornecedor, Integer> conn = new ConnectionFactory<>();
        String sql = "UPDATE FORNECEDOR SET ATIVO=TRUE, NOME=UPPER(?), CNPJ=UPPER(?), TELCELULAR=UPPER(?), TELRESIDENCIAL=(?), EMAIL=UPPER(?) WHERE ID=?";
        return conn.executeNonQuery(sql, DaoFornecedor::updateProtection, aux);
    }

    @Override
    public boolean delete(int id) {
        ConnectionFactory<Integer, Integer> conn = new ConnectionFactory<>();
        String sql = "UPDATE FORNECEDOR SET ATIVO=FALSE WHERE ID=?";
        return conn.executeNonQuery(sql, DaoFornecedor::deleteProtection, id);
    }

    public static PreparedStatement createProtection(PreparedStatement query, Fornecedor aux){

        try{
            query.setString(1, aux.getNome());
            query.setString(2, aux.getCnpj());
            query.setString(3, aux.getContato().getNumeroCelular());
            query.setString(4, aux.getContato().getNumeroResidencial());
            query.setString(5, aux.getContato().getEmail());
        }catch(SQLException e){
            System.out.println("Erro em createProtection Forncedor" + e);
        }
        return query;
    }

    public static PreparedStatement readProtection(PreparedStatement query, int id){

        try{
            query.setInt(1, id);
        }catch(SQLException e){
            System.out.println("Erro em readProtection Fornecedor" + e);
        }
        return query;
    }

    public static PreparedStatement readProtection(PreparedStatement query, String str){

        try{
            query.setString(1, str);
        }catch(SQLException e){
            System.out.println("Erro em readProtection Fornecedor" + e);
        }
        return query;
    }

    public static PreparedStatement readaAllProtection(PreparedStatement query, String str){

        try{
            query.setString(1, str + "%");
            query.setString(2, str + "%");
            query.setString(3, str);
        }catch(SQLException e){
            System.out.println("Erro em readAllProtection Fornecedor" + e);
        }
        return query;
    }

    public static PreparedStatement updateProtection(PreparedStatement query, Fornecedor aux){

        try{
            query.setString(1, aux.getNome());
            query.setString(2, aux.getCnpj());
            query.setString(3, aux.getContato().getNumeroCelular());
            query.setString(4, aux.getContato().getNumeroResidencial());
            query.setString(5, aux.getContato().getEmail());
            query.setInt(6, aux.getId());
        }catch(SQLException e){

        }
        return query;
    }

    public static PreparedStatement deleteProtection(PreparedStatement query, int id){
        try{
            query.setInt(1,id);

        }catch(SQLException e){

        }
        return query;
    }
    
    private static Fornecedor getDto(ResultSet rs, Fornecedor aux) {

        aux = new Fornecedor();
        Contato contato = new Contato();
        
        try {
            
            aux.setId(rs.getInt("ID"));
            aux.setNome(rs.getString("NOME"));
            aux.setCnpj(rs.getString("CNPJ"));
            aux.setStatus(rs.getBoolean("ATIVO"));
            
            contato.setDono(aux);
            contato.setEmail(rs.getString("EMAIL"));
            contato.setNumeroCelular(rs.getString("TELCELULAR"));
            contato.setNumeroResidencial(rs.getString("TELRESIDENCIAL"));
            
            aux.setContato(contato);
            return aux;
        } catch (SQLException e) {
            System.out.println("Erro ao tentar ler categoria" + e);
        }
        return null;
    }
}
