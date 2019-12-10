package br.ifsp.controle.dao;

import br.ifsp.modelo.infoprod.RegistroProduto;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


public class DaoRegistroProduto implements IDao<RegistroProduto> {

    @Override
    public boolean create(RegistroProduto aux)  {

            ConnectionFactory<RegistroProduto, Integer> conn = new ConnectionFactory<>();
            String sql = "INSERT INTO REGISTROPRODUTO (CODIGO, NOME, DESCRICAO, IMAGEM, ATIVO, "
                    + "CATEGORIA) VALUES(UPPER(?), UPPER(?), UPPER(?), UPPER(?), UPPER(?), UPPER(?))";
            return conn.executeNonQuery(sql, DaoRegistroProduto::createProtection, aux);
    }

    @Override
    public RegistroProduto read(int id) {
        ConnectionFactory<RegistroProduto, Integer> conn = new ConnectionFactory<>();
        String sql = "SELECT * FROM REGISTROPRODUTO WHERE ID=?";
        List<RegistroProduto> lista = conn.executeQuery(sql, DaoRegistroProduto::readProtection, id, DaoRegistroProduto::getDto, new RegistroProduto());
        return (lista.isEmpty())? null: lista.get(0);
    }

    
    public RegistroProduto read(String str) {
        ConnectionFactory<RegistroProduto, String> conn = new ConnectionFactory<>();
        String sql = "SELECT * FROM REGISTROPRODUTO WHERE NOME LIKE UPPER(?)";
        List<RegistroProduto> lista = conn.executeQuery(sql, DaoRegistroProduto::readProtection, str, DaoRegistroProduto::getDto, new RegistroProduto());
        return (lista.isEmpty())? null: lista.get(0);
    }


    @Override
    public List<RegistroProduto> readAll(boolean status, String str) {
        ConnectionFactory<RegistroProduto, String> conn = new ConnectionFactory<>();
        String sql="";
        if(status)
            sql = "SELECT * FROM REGISTROPRODUTO WHERE ATIVO=TRUE AND (NOME LIKE UPPER(?) OR CODIGO LIKE UPPER(?) OR ID LIKE ?)";
        else
            sql = "SELECT * FROM REGISTROPRODUTO WHERE ATIVO=FALSE AND (NOME LIKE UPPER(?) OR CODIGO LIKE UPPER(?) OR ID LIKE ?)";
        return conn.executeQuery(sql, DaoRegistroProduto::readAllProtection, str, DaoRegistroProduto::getDto, new RegistroProduto());
    }

    @Override
    public List<RegistroProduto> readAll(String str) {
        ConnectionFactory<RegistroProduto, String> conn = new ConnectionFactory<>();
        String sql="SELECT * FROM REGISTROPRODUTO WHERE NOME LIKE UPPER(?) OR CODIGO LIKE UPPER(?) OR ID=?";
        return conn.executeQuery(sql, DaoRegistroProduto::readAllProtection, str, DaoRegistroProduto::getDto, new RegistroProduto());
    }

    @Override
    public boolean update(RegistroProduto aux) {
        ConnectionFactory<RegistroProduto, Integer> conn = new ConnectionFactory<>();
        String sql = "UPDATE REGISTROPRODUTO SET ATIVO=TRUE, NOME=UPPER(?), CODIGO=UPPER(?), DESCRICAO=UPPER(?), IMAGEM=?,  CATEGORIA=? WHERE ID=?";
        return conn.executeNonQuery(sql, DaoRegistroProduto::updateProtection, aux);
    }

    @Override
    public boolean delete(int id) {
        ConnectionFactory<Integer, Integer> conn = new ConnectionFactory<>();
        String sql = "UPDATE REGISTROPRODUTO SET ATIVO=FALSE WHERE ID=?";
        return conn.executeNonQuery(sql, DaoRegistroProduto::deleteProtection, id);
    }
    
    public boolean desativaPorCategoria(int categoria){
        ConnectionFactory<Integer, Integer> banco =  new ConnectionFactory<>();
        String sql = "UPDATE REGISTROPRODUTO SET ATIVO=FALSE WHERE CATEGORIA=?";
        return banco.executeNonQuery(sql, DaoRegistroProduto::desativaPorCategoriaProtection, categoria);
    }
    
    public static PreparedStatement createProtection(PreparedStatement query, RegistroProduto aux){

        try{
            query.setString(1, aux.getCodigo());
            query.setString(2, aux.getNome());
            query.setString(3, aux.getDescricao());
            query.setString(4, aux.getImagem());
            query.setBoolean(5, aux.isStatus());
            query.setInt(6, aux.getCategoria().getId());
        }catch(SQLException e){
            System.out.println("Erro, em createProtection, DaoRegistroProduto " + e);
        }
        return query;
    }

    public static PreparedStatement readProtection(PreparedStatement query, int id){

        try{
            query.setInt(1, id);
        }catch(SQLException e){
            System.out.println("Erro, em createProtection, DaoRegistroProduto " + e);
        }
        return query;
    }

    public static PreparedStatement readProtection(PreparedStatement query, String str){

        try{
            query.setString(1, str);
        }catch(SQLException e){
            System.out.println("Erro, em createProtection, DaoRegistroProduto " + e);
        }
        return query;
    }

    public static PreparedStatement readAllProtection(PreparedStatement query, String str){
        try{
            query.setString(1, str + "%");
            query.setString(2, str + "%");            
            query.setString(3, str);
        }catch(SQLException e){
            System.out.println("Erro, em createProtection, DaoRegistroProduto " + e);
        }
        return query;
    }

    public static PreparedStatement updateProtection(PreparedStatement query, RegistroProduto aux){
        try{
            query.setString(1, aux.getNome());
            query.setString(2, aux.getCodigo());
            query.setString(3, aux.getDescricao());
            query.setString(4, aux.getImagem());
            query.setInt(5, aux.getCategoria().getId());
            query.setInt(6, aux.getId());
         
        }catch(SQLException e){
            System.out.println("Erro em updateProtection, RegistroProduto. " + e);
        }
        return query;
    }

    public static PreparedStatement deleteProtection(PreparedStatement query, int id){
        try{
            query.setInt(1, id);
        }catch(SQLException e){
            System.out.println("Erro em updateProtection, RegistroProduto. " + e);
        }
        return query;
    }
    
    public static PreparedStatement desativaPorCategoriaProtection(PreparedStatement query, int categoria){
        try{
            query.setInt(1, categoria);
        }catch(SQLException e){
            System.out.println("Erro em updateProtection, RegistroProduto. " + e);
        }
        return query;
    }

    public static RegistroProduto getDto(ResultSet rs, RegistroProduto aux){

        try {
            DaoCategoria con_categoria = new DaoCategoria();
            int id_categoria = rs.getInt("CATEGORIA");
            aux = new RegistroProduto();
            aux.setId(rs.getInt("ID"));
            aux.setNome(rs.getString("NOME"));
            aux.setCodigo(rs.getString("CODIGO"));
            aux.setDescricao(rs.getString("DESCRICAO"));
            aux.setStatus(rs.getBoolean("ATIVO"));
            aux.setCategoria(con_categoria.read(id_categoria));
            aux.setImagem(rs.getString("IMAGEM"));
            return aux;
        }catch (SQLException e){
            System.out.println("Erro, getDto RegistroProduto. " + e);
        }
        return null;
    }

}
