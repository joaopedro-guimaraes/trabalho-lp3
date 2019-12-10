package br.ifsp.controle.dao;

import br.ifsp.modelo.Pessoa.Cliente;
import br.ifsp.modelo.Pessoa.Contato;
import br.ifsp.modelo.Pessoa.Endereco;
import br.ifsp.modelo.Pessoa.Vendedor;
import java.util.List;

import br.ifsp.modelo.venda.Venda;
import br.ifsp.utils.QueryBetweenInt;
import br.ifsp.utils.QueryBetweenString;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DaoVenda  {
    
    public boolean create(Venda aux) {
        ConnectionFactory<Venda, Integer> con = new ConnectionFactory<>();
        String sql="INSERT INTO VENDA (FUNCIONARIO, CLIENTE, DATAVENDA, DESCONTO) VALUES (?, ?, ?, ?)";
        return con.executeNonQuery(sql, DaoVenda::createProtection, aux);
    }
    
    public Venda read(int id) {
        ConnectionFactory<Venda, Integer> con = new ConnectionFactory<>();
        String sql=" SELECT V.ID, V.DATAVENDA, V.DESCONTO, F.ID, F.NOME, F.CPF, F.RG, F.NASCIMENTO, "
                + "F.SEXO, F.ATIVO, F.CEP, F.ESTADO, F.CIDADE, F.BAIRRO, F.RUA, F.NUMERO, "
                + "F.COMPLEMENTO, F.TELCELULAR, F.TELRESIDENCIAL, F.EMAIL, F.CARGO, C.ID, C.NOME,"
                + " C.CPF, C.RG, C.CREDITO, C.NASCIMENTO, C.SEXO, C.ATIVO, C.CEP, C.ESTADO, C.CIDADE,"
                + " C.BAIRRO, C.RUA, C.NUMERO, C.COMPLEMENTO, C.TELCELULAR, C.TELRESIDENCIAL, C.EMAIL"
                +       " FROM " 
                + "VENDA V LEFT JOIN FUNCIONARIO F ON V.FUNCIONARIO=F.ID" 
                +       " LEFT JOIN " 
                +"CLIENTE C ON C.ID=V.CLIENTE WHERE V.ID=?;";
        List<Venda> lista = con.executeQuery(sql, DaoVenda::readProtection, id, DaoVenda::getDto, new Venda());
        return (lista.isEmpty())? null : lista.get(0);
    }
    public List<Venda> readAll(QueryBetweenInt dados) {
        ConnectionFactory<Venda, QueryBetweenInt> con = new ConnectionFactory<>();
        String sql=" SELECT V.ID, V.DATAVENDA, V.DESCONTO, F.ID, F.NOME, F.CPF, F.RG, F.NASCIMENTO, "
                + "F.SEXO, F.ATIVO, F.CEP, F.ESTADO, F.CIDADE, F.BAIRRO, F.RUA, F.NUMERO, "
                + "F.COMPLEMENTO, F.TELCELULAR, F.TELRESIDENCIAL, F.EMAIL, F.CARGO, C.ID, C.NOME,"
                + " C.CPF, C.RG, C.CREDITO, C.NASCIMENTO, C.SEXO, C.ATIVO, C.CEP, C.ESTADO, C.CIDADE,"
                + " C.BAIRRO, C.RUA, C.NUMERO, C.COMPLEMENTO, C.TELCELULAR, C.TELRESIDENCIAL, C.EMAIL"
                +       " FROM " 
                + "VENDA V LEFT JOIN FUNCIONARIO F ON V.FUNCIONARIO=F.ID" 
                +       " LEFT JOIN " 
                +"CLIENTE C ON C.ID=V.CLIENTE WHERE (DATAVENDA BETWEEN ? AND ?)";
        List<Venda> lista = con.executeQuery(sql,DaoVenda::readAllProtectionEmpty , dados, DaoVenda::getDto, new Venda());
        return (lista.isEmpty())? null : lista;
    }
    
    public List<Venda> readAllFuncionario(QueryBetweenString dados) {
        ConnectionFactory<Venda, QueryBetweenString> con = new ConnectionFactory<>();
        String sql=" SELECT V.ID, V.DATAVENDA, V.DESCONTO, F.ID, F.NOME, F.CPF, F.RG, F.NASCIMENTO, "
                + "F.SEXO, F.ATIVO, F.CEP, F.ESTADO, F.CIDADE, F.BAIRRO, F.RUA, F.NUMERO, "
                + "F.COMPLEMENTO, F.TELCELULAR, F.TELRESIDENCIAL, F.EMAIL, F.CARGO, C.ID, C.NOME,"
                + " C.CPF, C.RG, C.CREDITO, C.NASCIMENTO, C.SEXO, C.ATIVO, C.CEP, C.ESTADO, C.CIDADE,"
                + " C.BAIRRO, C.RUA, C.NUMERO, C.COMPLEMENTO, C.TELCELULAR, C.TELRESIDENCIAL, C.EMAIL"
                +       " FROM " 
                + "VENDA V LEFT JOIN FUNCIONARIO F ON V.FUNCIONARIO=F.ID" 
                +       " LEFT JOIN " 
                +"CLIENTE C ON C.ID=V.CLIENTE "
                + "WHERE F.NOME LIKE ? AND F.ID=V.FUNCIONARIO AND (DATAVENDA BETWEEN ? AND ?)";
        List<Venda> lista = con.executeQuery(sql,DaoVenda::readAllProtection , dados, DaoVenda::getDto, new Venda());
        return (lista.isEmpty())? null : lista;
    }
    
    public List<Venda> readAllCliente(QueryBetweenString dados) {
        ConnectionFactory<Venda, QueryBetweenString> con = new ConnectionFactory<>();
        String sql=" SELECT V.ID, V.DATAVENDA, V.DESCONTO, F.ID, F.NOME, F.CPF, F.RG, F.NASCIMENTO, "
                + "F.SEXO, F.ATIVO, F.CEP, F.ESTADO, F.CIDADE, F.BAIRRO, F.RUA, F.NUMERO, "
                + "F.COMPLEMENTO, F.TELCELULAR, F.TELRESIDENCIAL, F.EMAIL, F.CARGO, C.ID, C.NOME,"
                + " C.CPF, C.RG, C.CREDITO, C.NASCIMENTO, C.SEXO, C.ATIVO, C.CEP, C.ESTADO, C.CIDADE,"
                + " C.BAIRRO, C.RUA, C.NUMERO, C.COMPLEMENTO, C.TELCELULAR, C.TELRESIDENCIAL, C.EMAIL"
                +       " FROM " 
                + "VENDA V LEFT JOIN FUNCIONARIO F ON V.FUNCIONARIO=F.ID" 
                +       " LEFT JOIN " 
                +"CLIENTE C ON C.ID=V.CLIENTE "
                + "WHERE C.NOME LIKE ? AND C.ID=V.CLIENTE AND (DATAVENDA BETWEEN ? AND ?)";
        List<Venda> lista = con.executeQuery(sql,DaoVenda::readAllProtection , dados, DaoVenda::getDto, new Venda());
        
        return (lista.isEmpty())? null : lista;  
    }
    
    public boolean update(Venda aux) {
        ConnectionFactory<Venda, Integer> con = new ConnectionFactory<>();
        String sql="UPDATE VENDA SET DESCONTO=? WHERE ID=?";
        return con.executeNonQuery(sql, DaoVenda::updateProtection, aux);
    }
    
    public boolean delete(int id) {
        ConnectionFactory<Integer, Integer> con = new ConnectionFactory<>();
        String sql="DELETE FROM VENDA WHERE ID=?";
        return con.executeNonQuery(sql, DaoVenda::deleteProtection, id);        
    }
    
    public int retornaUltimaVenda(){
        ConnectionFactory<Integer, Integer> banco = new ConnectionFactory<>();
        String sql = "SELECT MAX(ID) FROM VENDA";
        return banco.executeQuery(sql, DaoVenda::maxProtection, 0, DaoVenda::maxGetDto, 0).get(0);
    }
    
    public static PreparedStatement maxProtection(PreparedStatement query, int aux){            
      
        return query;
    }
    
    public static PreparedStatement createProtection(PreparedStatement query, Venda aux){            
      try{
          query.setInt(1, aux.getFuncionario().getId());
          query.setInt(2, aux.getComprador().getId());
          query.setDate(3, new Date(aux.getData().getTime()));
          query.setDouble(4, aux.getDesconto());
      }catch(SQLException e){
          System.out.println("Erro em createProtection, DaoVenda. " + e);
      }
        return query;
    }
    
    public static PreparedStatement readProtection(PreparedStatement query, int id){            
      try{
          query.setInt(1, id);          
      }catch(SQLException e){
          System.out.println("Erro em readProtection, DaoVenda. " + e);
      }
        return query;
    }
    public static PreparedStatement readAllProtectionEmpty(PreparedStatement query, QueryBetweenInt dados){            
      try{          
          query.setDate(1, new Date(dados.inicio.getTime()));
          query.setDate(2, new Date(dados.fim.getTime()));
      }catch(SQLException e){
          System.out.println("Erro em readAllProtection, DaoVenda. " + e);
      }
        return query;
    }
    public static PreparedStatement readAllProtection(PreparedStatement query, QueryBetweenString dados){            
      try{
          query.setString(1, dados.str + "%");  
          query.setDate(2, new Date(dados.inicio.getTime()));
          query.setDate(3, new Date(dados.fim.getTime()));
      }catch(SQLException e){
          System.out.println("Erro em readAllProtection, DaoVenda. " + e);
      }
        return query;
    }
    public static PreparedStatement updateProtection(PreparedStatement query, Venda aux){            
      try{
          query.setDouble(1, aux.getDesconto());
          query.setInt(2, aux.getId());
      }catch(SQLException e){
          System.out.println("Erro em deleteProtection, DaoVenda. " + e);
      }
        return query;
    }
    public static PreparedStatement deleteProtection(PreparedStatement query, int id){            
      try{
          query.setInt(1, id);  
      }catch(SQLException e){
          System.out.println("Erro em deleteProtection, DaoVenda. " + e);
      }
        return query;
    }  
       
    public static Integer maxGetDto(ResultSet rs, Integer i){
        try{
            i = rs.getInt("MAX(ID)");
        }catch(SQLException e){
            System.out.println("Erro  daoVenda maxgetDto" + e);
        }
        return i;
    }
    public static Venda getDto(ResultSet rs, Venda aux){
        
        Cliente cliente = new Cliente();
        Vendedor vendedor = new Vendedor();
        Contato cont = new Contato();
        Endereco end = new Endereco();   
        aux = new Venda();        
        try{         
            //carrega dados da tabela funcionário
            cont.setNumeroCelular(rs.getString("F.TELCELULAR"));
            cont.setNumeroResidencial(rs.getString("F.TELRESIDENCIAL"));
            cont.setEmail(rs.getString("F.EMAIL"));
                          
            end.setCep(rs.getString("F.CEP"));
            end.setEstado(rs.getString("F.ESTADO"));
            end.setCidade(rs.getString("F.CIDADE"));
            end.setBairro(rs.getString("F.BAIRRO"));
            end.setRua(rs.getString("F.RUA"));
            end.setNumero(rs.getString("F.NUMERO"));
            end.setComplemento(rs.getString("F.COMPLEMENTO"));
            
            vendedor.setContatos(cont);
            vendedor.setEndereco(end);
            
            vendedor.setId(rs.getInt("F.ID"));
            vendedor.setNome(rs.getString("F.NOME"));
            vendedor.setCpf(rs.getString("F.CPF"));
            vendedor.setRg(rs.getString("F.RG"));
            vendedor.setNascimento(rs.getDate("F.NASCIMENTO"));
            vendedor.setSexo(Boolean.getBoolean("F.SEXO"));            
            vendedor.setStatus(rs.getBoolean("F.ATIVO"));
            vendedor.setCargo(rs.getBoolean("F.CARGO"));
            
            //carrega dados da tabela cliente
            cont = new Contato();
            end = new Endereco();
            cont.setNumeroCelular(rs.getString("C.TELCELULAR"));
            cont.setNumeroResidencial(rs.getString("C.TELRESIDENCIAL"));
            cont.setEmail(rs.getString("C.EMAIL"));
                             
            end.setCep(rs.getString("C.CEP"));
            end.setEstado(rs.getString("C.ESTADO"));
            end.setCidade(rs.getString("C.CIDADE"));
            end.setBairro(rs.getString("C.BAIRRO"));
            end.setRua(rs.getString("C.RUA"));
            end.setNumero(rs.getString("C.NUMERO"));
            end.setComplemento(rs.getString("C.COMPLEMENTO"));
            
            cliente.setContatos(cont);
            cliente.setEndereco(end);
            
            cliente.setId(rs.getInt("C.ID"));
            cliente.setNome(rs.getString("C.NOME"));
            cliente.setCpf(rs.getString("C.CPF"));
            cliente.setRg(rs.getString("C.RG"));
            cliente.setNascimento(rs.getDate("C.NASCIMENTO"));
            cliente.setSexo(Boolean.getBoolean("C.SEXO"));
            cliente.setCredito(rs.getDouble("C.CREDITO"));
            cliente.setStatus(rs.getBoolean("C.ATIVO"));
            
            //carrega dados da tabela venda
            aux.setId(rs.getInt("ID"));
            aux.setComprador(cliente);
            aux.setFuncionario(vendedor);
            aux.setData(rs.getDate("DATAVENDA"));
            aux.setDesconto(rs.getDouble("DESCONTO"));            
        }catch(SQLException e){
            System.out.println("Erro em getDto, DaoVenda. " + e);
        }        
        return aux;
    }
}
