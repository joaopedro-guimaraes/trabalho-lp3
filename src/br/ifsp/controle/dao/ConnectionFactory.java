package br.ifsp.controle.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
/*T se refere a classe do objeto que será buscada no banco, isto é, as classes do modelo. E será 
útilizado para tipar a função getDto e o objeto que ela recebe para inserir os dados do resultSet*/
/*E se refere ao tipo de dado enviado para configurar uma proteção sql e será utilizado para tipar
o objeto recebido que conterá os dados para serem verificados*/


public class ConnectionFactory<T, E> {
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/PANDACUB";
    private static final String USER = "root";
    private static final String PASSWD = "";    
             
    /*Construtor padrão, Daos enviam suas strings sqls e funções necessárias para execução */
    private Connection getConnection(){
        try {
            Class.forName(DRIVER);
            return DriverManager.getConnection(URL, USER, PASSWD);
        } catch (SQLException ex) {
            throw new RuntimeException("Erro na conexão em tempo de execução", ex);
        } catch (ClassNotFoundException e) {
            System.out.println("Erro ao tentar criar conexão." + e);
        }
        return null;
    }
    /*Recebe uma string sql, recebe uma função que respeite os padrões de IDaoProtectionFunction e
    atribui o tipo de dado E para as operações flexíveis dela. Recebe um tipo de dado que tem de atender
    as restrições da classe que E foi declarado.
    Recebe uma função que busca os dados no banco através de um resultSet, atribui a ela o tipo de dado
    T para realizar suas operações flexíveis e passa como parametro um tipo de dado T para esta preencher
    e retornar.
     */
    public List<T> executeQuery(String sql, IDaoProtectionFunction<E> functionProtection, E auxProtection, IDaoGetFunction<T> functionGetDto, T auxGet) {
       
        try{
            Connection con = getConnection();
            
            PreparedStatement query = con.prepareStatement(sql);
            query = functionProtection.execute(query, auxProtection);        
            ResultSet rs = query.executeQuery();
            List<T> lista = new ArrayList<T>();
            while(rs.next()){
                lista.add(functionGetDto.executeGet(rs, auxGet));
            }
            rs.close();
            query.close();
            con.close();
            return lista;
        }catch(SQLException e){
            System.out.println("Erro executeQuery " + e);
        }
        return null;
    }
    public boolean executeNonQuery(String sql, IDaoProtectionFunction<T> function, T aux) {
        
        try{
            Connection con = getConnection();
            PreparedStatement query = con.prepareStatement(sql);
            query = function.execute(query, aux);        
            query.execute(); 
            query.close();
            con.close();
            return true;
        }catch(SQLException e){
            System.out.println("Erro executeQuery " + e);
        }
        return false;        
    }
}