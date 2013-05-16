/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifnmg.trabalho.DataAccess;

import br.edu.ifnmg.trabalho.classes.ErroValidacaoException;
import br.edu.ifnmg.trabalho.classes.Pagamento;
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
public class PagamentoDao {
    private Bd bd;
    
    public PagamentoDao(){
        bd = new Bd();
    }
    
    public boolean Salvar(Pagamento obj) {
        try {
            if (obj.getId() == 0) {
                PreparedStatement comando = bd.getConexao().prepareStatement("insert into pagamentos(nome,juros,status) values(?,?,?)");
                comando.setString(1, obj.getNome());
                comando.setDouble(2, obj.getJuros());
                comando.setInt(3,1);
              
                comando.executeUpdate();
            } else {
                PreparedStatement comando = bd.getConexao().prepareStatement("update pagamentos set nome=?,juros=? where id=? and status=1");
                comando.setString(1, obj.getNome());
                comando.setDouble(2, obj.getJuros());
                comando.setInt(3, obj.getId());
                comando.executeUpdate();
            }
            return true;
        } catch (SQLException ex) {
            System.out.printf("Erro ao salvar pagamento LOCAL: PAGAMENTODAO-SALVAR");
            return false;
        }
    }
    
     public Pagamento Abrir(int id) throws ErroValidacaoException {
        try {
            Pagamento paga = new Pagamento(0, "");

            PreparedStatement comando = bd.getConexao().prepareStatement("select * from pagamentos where id=? and status=1");
            comando.setInt(1, id);
            ResultSet resultado = comando.executeQuery();

            resultado.first();

            paga.setId(resultado.getInt("id"));
            paga.setNome(resultado.getString("nome"));
            paga.setJuros(resultado.getDouble("juros"));
        
            return paga;

        } catch (SQLException ex) {
            System.out.printf("Erro ao abrir pagamento LOCAL: PAGAMENTODAO-ABRIR");
            return null;
        }
    }
    
    public boolean Apagar(Pagamento obj) {
        try {
            PreparedStatement comando = bd.getConexao().prepareStatement("update pagamentos set status=0 where id=?");
            comando.setInt(1, obj.getId());
            comando.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.out.printf("Erro ao destivar pagamento LOCAL: PAGAMENTODAO-APAGAR");
            return false;
        }
    }
    
    public List<Pagamento> listarTodos() throws ErroValidacaoException {
        try {
            PreparedStatement comando = bd.getConexao().prepareStatement("select * from pagamentos where status=1");
            ResultSet resultado = comando.executeQuery();
            // Cria uma lista de pagamentos vazia
            List<Pagamento> paga = new LinkedList<>();
            while(resultado.next()){
              // Inicializa um objeto de pagamentos vazio
                Pagamento tmp = new Pagamento();
                // Pega os valores do retorno da consulta e coloca no objeto
                tmp.setId(resultado.getInt("id"));
                tmp.setNome(resultado.getString("nome"));
                tmp.setJuros(resultado.getDouble("juros"));
                
                
                // Pega o objeto e coloca na lista
                paga.add(tmp);
                
            }
            return paga;
        } catch (SQLException ex) {
            System.out.printf("Erro ao listar todos pagamentos LOCAL: PAGAMENTODAO-LISTAR TODOS");
            return null;
        }
    }
    
    public List<Pagamento> buscar(Pagamento filtro) throws ErroValidacaoException {
        try {
            
            String sql = "select * from pagamentos";
            String where = "";
            
            if(filtro.getNome().length() > 0){
                where = "nome like '%"+filtro.getNome()+"%'";
                where = where + " and status = 1 ";
            }
    
            if(where.length() > 0){
                sql = sql + " where " + where;
            }
            
            if(where.length() == 0){
                where = where + " status = 1 ";
                sql = sql + " where " + where;
            }
          
            
            Statement comando = bd.getConexao().createStatement();
            
            ResultSet resultado = comando.executeQuery(sql);
            // Cria uma lista de pagamento vazia
            List<Pagamento> paga = new LinkedList<>();
            while (resultado.next()) {
                // Inicializa um objeto de pagamento vazio
                Pagamento tmp = new Pagamento();
                // Pega os valores do retorno da consulta e coloca no objeto
                tmp.setId(resultado.getInt("id"));
                tmp.setNome(resultado.getString("nome"));
                tmp.setJuros(resultado.getDouble("juros"));
     
                // Pega o objeto e coloca na lista
                paga.add(tmp);
            }
            return paga;
        } catch (SQLException ex) {
            System.out.printf("Erro ao filtrar pagamentos LOCAL: PAGAMENTODAO-BUSCAR");
            return null;
        }
    }
}

