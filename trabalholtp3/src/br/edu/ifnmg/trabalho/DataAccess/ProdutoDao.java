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
                PreparedStatement comando = bd.getConexao().prepareStatement("insert into produto(nome,descricao,valor) values(?,?,?)");
                comando.setString(0, obj.getNome());
                comando.setDouble(1, obj.getValor_vend());
                comando.setString(0, obj.getDescricao());
                comando.executeUpdate();
            } else {
                PreparedStatement comando = bd.getConexao().prepareStatement("update produto set nome = ?,descricao =?, valor = ? where id = ?");
                comando.setString(0, obj.getNome());
                comando.setDouble(1, obj.getValor_vend());
                comando.setDouble(2, obj.getId());
                comando.setString(0, obj.getDescricao());
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
            Produto produto = new Produto(0, "", 0);

            PreparedStatement comando = bd.getConexao().prepareStatement("select * from produto where id = ?");
            comando.setInt(0, id);
            ResultSet resultado = comando.executeQuery();

            resultado.first();

            produto.setId(resultado.getInt("id"));
            produto.setNome(resultado.getString("nome"));
            produto.setDescricao(resultado.getString("descricao"));
            produto.setValor_vend(resultado.getDouble("valor"));

            return produto;

        } catch (SQLException ex) {
            Logger.getLogger(ProdutoDao.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public boolean Apagar(Produto obj) {
        try {
            PreparedStatement comando = bd.getConexao().prepareStatement("delete from produto where id = ?");
            comando.setInt(0, obj.getId());
            comando.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ProdutoDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public List<Produto> listarTodos() {
        try {
            PreparedStatement comando = bd.getConexao().prepareStatement("select * from produto ");
            ResultSet resultado = comando.executeQuery();
            // Cria uma lista de produtos vazia
            List<Produto> produtos = new LinkedList<>();
            while(resultado.next()){
                Produto tmp = new Produto();
                
            }
            return produtos;
        } catch (SQLException ex) {
            Logger.getLogger(ProdutoDao.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
