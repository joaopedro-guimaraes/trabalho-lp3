package br.ifsp.controle.dao;

import java.util.List;

import br.ifsp.modelo.Pessoa.Cliente;
import br.ifsp.modelo.Pessoa.Contato;
import br.ifsp.modelo.Pessoa.Endereco;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DaoCliente implements IDao<Cliente>{

    @Override
    public boolean create(Cliente aux) {
        ConnectionFactory<Cliente, Integer> con = new ConnectionFactory<>();
        String sql = "INSERT INTO CLIENTE (NOME, CPF, RG, NASCIMENTO, SEXO, CREDITO, ATIVO, CEP, ESTADO,"
                + "CIDADE, BAIRRO, RUA, NUMERO, COMPLEMENTO, TELCELULAR, TELRESIDENCIAL, EMAIL) VALUES"
                + "(UPPER(?), UPPER(?), UPPER(?), UPPER(?), UPPER(?), UPPER(?), TRUE, UPPER(?), UPPER(?),"
                + "UPPER(?), UPPER(?), UPPER(?), UPPER(?), UPPER(?), UPPER(?), UPPER(?), UPPER(?))";
        return con.executeNonQuery(sql, DaoCliente::createProtection, aux);
    }
     
    @Override
    public Cliente read(int id) {
        ConnectionFactory<Cliente, Integer> con = new ConnectionFactory<>();
        String sql = "SELECT * FROM CLIENTE WHERE ID=?";
        List<Cliente> lista = con.executeQuery(sql, DaoCliente::readProtection, id, DaoCliente::getDto, new Cliente());
        return (lista.isEmpty())? null: lista.get(0);    
    }
    
    public Cliente read(String str) {
        ConnectionFactory<Cliente, String> con = new ConnectionFactory<>();
        String sql = "SELECT * FROM CLIENTE WHERE CPF LIKE UPPER(?)";
        List<Cliente> lista = con.executeQuery(sql, DaoCliente::readProtection, str, DaoCliente::getDto, new Cliente());
        return (lista.isEmpty())? null: lista.get(0);    
    }
    
    @Override
    public List<Cliente> readAll(boolean status, String str) {
        ConnectionFactory<Cliente, String> con = new ConnectionFactory<>();
        String sql ="";
        if(status)
            sql = "SELECT * FROM CLIENTE WHERE ATIVO=TRUE AND (ID=? OR CPF LIKE UPPER(?) OR RG LIKE UPPER(?) OR NOME LIKE UPPER(?))";
        else
            sql = "SELECT * FROM CLIENTE WHERE ATIVO=FALSE AND (ID=? OR CPF LIKE UPPER(?) OR RG LIKE UPPER(?) OR NOME LIKE UPPER(?))";
        return con.executeQuery(sql, DaoCliente::readAllProtection, str, DaoCliente::getDto, new Cliente());
    }

    @Override
    public List<Cliente> readAll(String str) {
        ConnectionFactory<Cliente, String> con = new ConnectionFactory<>();
        String sql = "SELECT * FROM CLIENTE WHERE ID=? OR CPF LIKE UPPER(?) OR RG LIKE UPPER(?) OR NOME LIKE UPPER(?)";
        return con.executeQuery(sql, DaoCliente::readAllProtection, str, DaoCliente::getDto, new Cliente());
        
    }
    
    @Override
    public boolean update(Cliente aux) {
        ConnectionFactory<Cliente, Integer> con = new ConnectionFactory<>();
        String sql = "UPDATE CLIENTE SET NOME = UPPER(?), CPF = UPPER(?), RG = UPPER(?), "
                + "NASCIMENTO = UPPER(?), SEXO = UPPER(?), CREDITO = UPPER(?), ATIVO = TRUE,"
                + " CEP = UPPER(?), ESTADO = UPPER(?), CIDADE = UPPER(?), BAIRRO = UPPER(?),"
                + " RUA = UPPER(?), NUMERO = UPPER(?), COMPLEMENTO = UPPER(?), TELCELULAR = UPPER(?),"
                + " TELRESIDENCIAL = UPPER(?), EMAIL = UPPER(?) WHERE ID = UPPER(?)";
        return con.executeNonQuery(sql, DaoCliente::updateProtection, aux);
    }
    
    @Override
    public boolean delete(int id) {
        ConnectionFactory<Integer, Integer> con = new ConnectionFactory<>();
        String sql = "UPDATE CLIENTE SET ATIVO = FALSE WHERE ID=?";               
        return con.executeNonQuery(sql, DaoCliente::deleteProtection, id);
    }   
    
    public static PreparedStatement createProtection(PreparedStatement query, Cliente aux){
        Date data = (aux.getNascimento()!= null)? new Date(aux.getNascimento().getTime()): null;
        try{
            query.setString(1, aux.getNome());
            query.setString(2, aux.getCpf());
            query.setString(3, aux.getRg());
            query.setDate(4, data);
            query.setBoolean(5, aux.isSexo());
            query.setDouble(6, aux.getCredito());
            query.setString(7, aux.getEndereco().getCep());
            query.setString(8, aux.getEndereco().getEstado());
            query.setString(9, aux.getEndereco().getCidade());
            query.setString(10, aux.getEndereco().getBairro());            
            query.setString(11, aux.getEndereco().getRua());
            query.setString(12, aux.getEndereco().getNumero());
            query.setString(13, aux.getEndereco().getComplemento());
            query.setString(14, aux.getContatos().getNumeroCelular());
            query.setString(15, aux.getContatos().getNumeroResidencial());
            query.setString(16, aux.getContatos().getEmail());
            return query;
        }catch(SQLException e){
            System.out.println("Erro em createProtection DaoCliente. " + e);
        }        
        return null;
    }        
    
    public static PreparedStatement readProtection(PreparedStatement query, int id){        
        try{
            query.setInt(1, id);
        }catch(SQLException e){
            System.out.println("Erro em read, Dao Cliente. " + e);
        }        
        return query;
    }
    
    public static PreparedStatement readProtection(PreparedStatement query, String cpf){        
        try{
            query.setString(1, cpf);
        }catch(SQLException e){
            System.out.println("Erro em read, Dao Cliente. " + e);
        }        
        return query;
    }
    
    public static PreparedStatement readAllProtection(PreparedStatement query, String str){
        try{           
            query.setString(1, str);
            query.setString(2, str + "%");
            query.setString(3, str + "%");
            query.setString(4, str + "%");
        }catch(SQLException e){
            System.out.println("Erro readAllProtection DaoCliente." + e);
        }
        return query;
    }    

    public static PreparedStatement updateProtection(PreparedStatement query, Cliente aux){
        Date data = (aux.getNascimento()!= null)? new Date(aux.getNascimento().getTime()): null;
        try{
            query.setString(1, aux.getNome());
            query.setString(2, aux.getCpf());
            query.setString(3, aux.getRg());
            query.setDate(4, data);
            query.setBoolean(5, aux.isSexo());
            query.setDouble(6, aux.getCredito());
            query.setString(7, aux.getEndereco().getCep());
            query.setString(8, aux.getEndereco().getEstado());
            query.setString(9, aux.getEndereco().getCidade());
            query.setString(10, aux.getEndereco().getBairro());            
            query.setString(11, aux.getEndereco().getRua());
            query.setString(12, aux.getEndereco().getNumero());
            query.setString(13, aux.getEndereco().getComplemento());
            query.setString(14, aux.getContatos().getNumeroCelular());
            query.setString(15, aux.getContatos().getNumeroResidencial());
            query.setString(16, aux.getContatos().getEmail());
            query.setInt(17, aux.getId());
            return query;
        }catch(SQLException e){
            System.out.println("Erro em updateProtection DaoCliente. " + e);
        }        
        return null;
    }    
    
    public static PreparedStatement deleteProtection(PreparedStatement query, int id){
        try{           
            query.setInt(1, id);            
        }catch(SQLException e){
            System.out.println("Erro deleteProtection DaoCliente." + e);
        }
        return query;
    }
    
    public static Cliente getDto(ResultSet rs, Cliente aux){
               
        try{          
            aux = new Cliente();
            
            Contato cont = new Contato();
            cont.setNumeroCelular(rs.getString("TELCELULAR"));
            cont.setNumeroResidencial(rs.getString("TELRESIDENCIAL"));
            cont.setEmail(rs.getString("EMAIL"));
            
            Endereco end = new Endereco();     
            end.setCep(rs.getString("CEP"));
            end.setEstado(rs.getString("ESTADO"));
            end.setCidade(rs.getString("CIDADE"));
            end.setBairro(rs.getString("BAIRRO"));
            end.setRua(rs.getString("RUA"));
            end.setNumero(rs.getString("NUMERO"));
            end.setComplemento(rs.getString("COMPLEMENTO"));
            
            aux.setContatos(cont);
            aux.setEndereco(end);
            
            aux.setId(rs.getInt("ID"));
            aux.setNome(rs.getString("NOME"));
            aux.setCpf(rs.getString("CPF"));
            aux.setRg(rs.getString("RG"));
            aux.setNascimento(rs.getDate("NASCIMENTO"));
            aux.setSexo(rs.getBoolean("SEXO"));
            aux.setCredito(rs.getDouble("CREDITO"));
            aux.setStatus(rs.getBoolean("ATIVO"));
            
            return aux;
        }catch(SQLException e){
            System.out.println("Erro getDto em DaoCliente. " + e);
        }
        return null;
    }
}
