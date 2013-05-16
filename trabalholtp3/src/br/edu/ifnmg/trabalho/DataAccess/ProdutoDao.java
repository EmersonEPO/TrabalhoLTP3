/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifnmg.trabalho.DataAccess;

import br.edu.ifnmg.trabalho.classes.ErroValidacaoException;
import br.edu.ifnmg.trabalho.classes.Produto;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author aluno
 */
public class ProdutoDao {
    private Bd bd;
    
    public ProdutoDao(){
        bd = new Bd();
    }
    
    public boolean Salvar(Produto obj) {
        try {
            if (obj.getId() == 0) {
                PreparedStatement comando = bd.getConexao().prepareStatement("insert into produtos(nome,valor_comp,valor_unit,descricao,estoque,status) values(?,?,?,?,?,?)");
                comando.setString(1, obj.getNome());
                comando.setDouble(2, obj.getValor_comp());
                comando.setDouble(3, obj.getValor_vend());
                comando.setString(4, obj.getDescricao());
                comando.setInt(5, obj.getEstoque());
                comando.setInt(6, 1);
                comando.executeUpdate();
            } else {
                PreparedStatement comando = bd.getConexao().prepareStatement("update produtos set nome=?,valor_comp=?,valor_unit=?,descricao=?,estoque=? where id = ? and status=1");
                comando.setString(1, obj.getNome());
                comando.setDouble(2, obj.getValor_comp());
                comando.setDouble(3, obj.getValor_vend());
                comando.setString(4, obj.getDescricao());
                comando.setInt(5, obj.getEstoque());
                comando.setInt(6, obj.getId());
                comando.executeUpdate();
            }
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ProdutoDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
     public Produto Abrir(int id) throws ErroValidacaoException {
        try {
            Produto produto = new Produto(0, "");

            PreparedStatement comando = bd.getConexao().prepareStatement("select * from produtos where id=? and status=1");
            comando.setInt(1, id);
            ResultSet resultado = comando.executeQuery();

            resultado.first();

            produto.setId(resultado.getInt("id"));
            produto.setNome(resultado.getString("nome"));
            produto.setDescricao(resultado.getString("descricao"));
            produto.setValor_comp(resultado.getDouble("valor_comp"));
            produto.setValor_vend(resultado.getDouble("valor_unit"));
            produto.setEstoque(resultado.getInt("estoque"));

            return produto;

        } catch (SQLException ex) {
            Logger.getLogger(ProdutoDao.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public boolean Apagar(Produto obj) {
        try {
            PreparedStatement comando = bd.getConexao().prepareStatement("update produtos set status=0 where id=?");
            comando.setInt(1, obj.getId());
            comando.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ProdutoDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public List<Produto> listarTodos() throws ErroValidacaoException {
        try {
            PreparedStatement comando = bd.getConexao().prepareStatement(""
                    + "select * from produtos where status=1 order by id");
            ResultSet resultado = comando.executeQuery();
            // Cria uma lista de produtos vazia
            List<Produto> produtos = new LinkedList<>();
            while(resultado.next()){
              // Inicializa um objeto de produto vazio
                Produto tmp = new Produto();
                // Pega os valores do retorno da consulta e coloca no objeto
                tmp.setId(resultado.getInt("id"));
                tmp.setNome(resultado.getString("nome"));
                tmp.setValor_comp(resultado.getDouble("valor_comp"));
                tmp.setValor_vend(resultado.getDouble("valor_unit"));
                tmp.setDescricao(resultado.getString("descricao"));
                tmp.setEstoque(resultado.getInt("estoque"));
                
                // Pega o objeto e coloca na lista
                produtos.add(tmp);
                
            }
            return produtos;
        } catch (SQLException ex) {
            System.out.printf("Erro ao abrir produtos LOCAL: PRODUTODAO-LISTAR TODOS");
            return null;
        }
    }
    
    public List<Produto> buscar(Produto filtro) throws ErroValidacaoException {
        try {
            
            String sql = "select * from produtos ";
            String where = "";
            
            if(filtro.getNome().length() > 0){
                where = "nome like '%"+filtro.getNome()+"%'";
                where = where + " and status = 1 ";
            }
            
           
            
            if(where.length() > 0){
                sql = sql + " where " + where;
            }
            
            if(where.length() == 0){
                where = where + " status = 1 order by id";
                sql = sql + " where " + where;
            }
            
            Statement comando = bd.getConexao().createStatement();
            
            ResultSet resultado = comando.executeQuery(sql);
            // Cria uma lista de produtos vazia
            List<Produto> produtos = new LinkedList<>();
            while (resultado.next()) {
                // Inicializa um objeto de produto vazio
                Produto tmp = new Produto();
                // Pega os valores do retorno da consulta e coloca no objeto
                tmp.setId(resultado.getInt("id"));
                tmp.setNome(resultado.getString("nome"));
                tmp.setValor_comp(resultado.getDouble("valor_comp"));
                tmp.setValor_vend(resultado.getDouble("valor_unit"));
                tmp.setDescricao(resultado.getString("descricao"));
                tmp.setEstoque(resultado.getInt("estoque"));
                // Pega o objeto e coloca na lista
                produtos.add(tmp);
            }
            return produtos;
        } catch (SQLException ex) {
            Logger.getLogger(ProdutoDao.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
