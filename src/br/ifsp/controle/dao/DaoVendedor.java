package br.ifsp.controle.dao;

import br.ifsp.modelo.Pessoa.Contato;
import br.ifsp.modelo.Pessoa.Endereco;
import br.ifsp.modelo.Pessoa.Vendedor;
import br.ifsp.utils.CarregaLogin;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DaoVendedor implements IDao<Vendedor>{

	@Override
    public boolean create(Vendedor aux) {
        ConnectionFactory<Vendedor, Integer> con = new ConnectionFactory<>();
        String sql = "INSERT INTO FUNCIONARIO (NOME, CPF, RG, NASCIMENTO, SEXO, ATIVO, CEP, ESTADO,"
                + "CIDADE, BAIRRO, RUA, NUMERO, COMPLEMENTO, TELCELULAR, TELRESIDENCIAL, EMAIL,"
                + "LOGIN, SENHA, CARGO) VALUES (UPPER(?), UPPER(?), UPPER(?), UPPER(?), UPPER(?),"
                + "TRUE, UPPER(?), UPPER(?), UPPER(?), UPPER(?), UPPER(?), UPPER(?), UPPER(?), "
                + "UPPER(?), UPPER(?), UPPER(?), UPPER(?), UPPER(?), UPPER(?))";
        return con.executeNonQuery(sql, DaoVendedor::createProtection, aux);
    }
    
    @Override
    public Vendedor read(int id) {
        ConnectionFactory<Vendedor, Integer> con = new ConnectionFactory<>();
        String sql = "SELECT * FROM FUNCIONARIO WHERE ID=?";
        List<Vendedor> lista = con.executeQuery(sql, DaoVendedor::readProtection, id, DaoVendedor::getDto, new Vendedor());
        return (lista.isEmpty())? null: lista.get(0);    
    }
    
    public Vendedor read(String str) {
        ConnectionFactory<Vendedor, String> con = new ConnectionFactory<>();
        String sql = "SELECT * FROM FUNCIONARIO WHERE CPF LIKE UPPER(?)";
        List<Vendedor> lista = con.executeQuery(sql, DaoVendedor::readProtection, str, DaoVendedor::getDto, new Vendedor());
        return (lista.isEmpty())? null: lista.get(0);    
    }
    
    @Override
    public List<Vendedor> readAll(boolean status, String str) {
        ConnectionFactory<Vendedor, String> con = new ConnectionFactory<>();
        String sql ="";
        if(status)
            sql = "SELECT * FROM FUNCIONARIO WHERE ATIVO=TRUE AND (ID=? OR CPF LIKE UPPER(?) OR RG LIKE UPPER(?) OR NOME LIKE UPPER(?))";
        else
            sql = "SELECT * FROM FUNCIONARIO WHERE ATIVO=FALSE AND (ID=? OR CPF LIKE UPPER(?) OR RG LIKE UPPER(?) OR NOME LIKE UPPER(?))";
        return con.executeQuery(sql, DaoVendedor::readAllProtection, str, DaoVendedor::getDto, new Vendedor());
    }

    @Override
    public List<Vendedor> readAll(String str) {
        ConnectionFactory<Vendedor, String> con = new ConnectionFactory<>();
        String sql = "SELECT * FROM FUNCIONARIO WHERE ID=? OR CPF LIKE UPPER(?) OR RG LIKE UPPER(?) OR NOME LIKE UPPER(?)";
        return con.executeQuery(sql, DaoVendedor::readAllProtection, str, DaoVendedor::getDto, new Vendedor());
        
    }
    
    @Override
    public boolean update(Vendedor aux) {
        ConnectionFactory<Vendedor, Integer> con = new ConnectionFactory<>();
        String sql = "UPDATE FUNCIONARIO SET NOME = UPPER(?), CPF = UPPER(?), RG = UPPER(?), "
                + "NASCIMENTO = UPPER(?), SEXO = UPPER(?), ATIVO = TRUE,"
                + " CEP = UPPER(?), ESTADO = UPPER(?), CIDADE = UPPER(?), BAIRRO = UPPER(?),"
                + " RUA = UPPER(?), NUMERO = UPPER(?), COMPLEMENTO = UPPER(?), TELCELULAR = UPPER(?),"
                + " TELRESIDENCIAL = UPPER(?), EMAIL = UPPER(?) WHERE ID = UPPER(?)";
        return con.executeNonQuery(sql, DaoVendedor::updateProtection, aux);
    }
    
    @Override
    public boolean delete(int id) {
        ConnectionFactory<Integer, Integer> con = new ConnectionFactory<>();
        String sql = "UPDATE FUNCIONARIO SET ATIVO = FALSE WHERE ID=?";               
        return con.executeNonQuery(sql, DaoVendedor::deleteProtection, id);
    }   
    
    public Vendedor login(CarregaLogin keys){
     
        Vendedor aux = new Vendedor();
        ConnectionFactory<Vendedor, CarregaLogin> con = new ConnectionFactory<>();
        String sql="SELECT * FROM FUNCIONARIO WHERE ATIVO=TRUE AND LOGIN LIKE ? AND SENHA LIKE ?";
        List<Vendedor> lista = con.executeQuery(sql, DaoVendedor::loginProtection, keys , DaoVendedor::getDto, new Vendedor());
        return (lista.isEmpty())? null: lista.get(0);
    }
    
    public Integer ContFuncionario(){
        ConnectionFactory<Integer, Integer> banco = new ConnectionFactory<>();
        String sql = "SELECT COUNT(ID) FROM FUNCIONARIO";
        List<Integer> lista = banco.executeQuery(sql, DaoVendedor::CountProtection, 0, DaoVendedor::getDtoCount, 0);
        return lista.get(0);
    }
    
    public static PreparedStatement CountProtection(PreparedStatement query, Integer teste){
        return query;
    }
    
    public static Integer getDtoCount(ResultSet rs, Integer aux){
            try {
                aux = rs.getInt("COUNT(ID)");
            } catch (SQLException ex) {
                System.out.println("Erro no método count");
            }
        return aux;
    }
    
    public static PreparedStatement loginProtection(PreparedStatement query, CarregaLogin keys){
        try{
            query.setString(1, keys.login);
            System.out.println("teste:" + keys.login);
            query.setString(2, keys.senha);
            System.out.println("teste:" + keys.senha);
        }catch(SQLException e){
            System.out.println("Erro em loginProtection, DaoVendedor. " + e);
        }        
        return query;
    }
    public static PreparedStatement createProtection(PreparedStatement query, Vendedor aux){
        Date data = (aux.getNascimento()!= null)? new Date(aux.getNascimento().getTime()): null;
        try{
            query.setString(1, aux.getNome());
            query.setString(2, aux.getCpf());
            query.setString(3, aux.getRg());
            query.setDate(4, data);
            query.setBoolean(5, aux.isSexo());            
            query.setString(6, aux.getEndereco().getCep());
            query.setString(7, aux.getEndereco().getEstado());
            query.setString(8, aux.getEndereco().getCidade());
            query.setString(9, aux.getEndereco().getBairro());            
            query.setString(10, aux.getEndereco().getRua());
            query.setString(11, aux.getEndereco().getNumero());
            query.setString(12, aux.getEndereco().getComplemento());
            query.setString(13, aux.getContatos().getNumeroCelular());
            query.setString(14, aux.getContatos().getNumeroResidencial());
            query.setString(15, aux.getContatos().getEmail());
            query.setString(16, aux.getLogin());
            query.setString(17, aux.getSenha());
            query.setBoolean(18, aux.isCargo());
            return query;
        }catch(SQLException e){
            System.out.println("Erro em createProtection DaoVendedor. " + e);
        }        
        return null;
    }        
    
    public static PreparedStatement readProtection(PreparedStatement query, int id){        
        try{
            query.setInt(1, id);
        }catch(SQLException e){
            System.out.println("Erro em read, DaoVendedor. " + e);
        }        
        return query;
    }
    
    public static PreparedStatement readProtection(PreparedStatement query, String cpf){        
        try{
            query.setString(1, cpf);
        }catch(SQLException e){
            System.out.println("Erro em read, DaoVendedor. " + e);
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
            System.out.println("Erro readAllProtection DaoVendedor." + e);
        }
        return query;
    }    

    public static PreparedStatement updateProtection(PreparedStatement query, Vendedor aux){
        Date data = (aux.getNascimento()!= null)? new Date(aux.getNascimento().getTime()): null;
        try{
            query.setString(1, aux.getNome());
            query.setString(2, aux.getCpf());
            query.setString(3, aux.getRg());
            query.setDate(4, data);
            query.setBoolean(5, aux.isSexo());            
            query.setString(6, aux.getEndereco().getCep());
            query.setString(7, aux.getEndereco().getEstado());
            query.setString(8, aux.getEndereco().getCidade());
            query.setString(9, aux.getEndereco().getBairro());            
            query.setString(10, aux.getEndereco().getRua());
            query.setString(11, aux.getEndereco().getNumero());
            query.setString(12, aux.getEndereco().getComplemento());
            query.setString(13, aux.getContatos().getNumeroCelular());
            query.setString(14, aux.getContatos().getNumeroResidencial());
            query.setString(15, aux.getContatos().getEmail());
            query.setInt(16, aux.getId());
            return query;
        }catch(SQLException e){
            System.out.println("Erro em updateProtection DaoVendedor. " + e);
        }        
        return null;
    }    
    
    public static PreparedStatement deleteProtection(PreparedStatement query, int id){
        try{           
            query.setInt(1, id);            
        }catch(SQLException e){
            System.out.println("Erro deleteProtection DaoVendedor." + e);
        }
        return query;
    }
        
    public static Vendedor getDto(ResultSet rs, Vendedor aux){
               
        try{          
            aux = new Vendedor();
            
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
            aux.setStatus(rs.getBoolean("ATIVO"));
            aux.setCargo(rs.getBoolean("CARGO"));
            return aux;
        }catch(SQLException e){
            System.out.println("Erro getDto em DaoVendedor. " + e);
        }
        return null;
    }
}
