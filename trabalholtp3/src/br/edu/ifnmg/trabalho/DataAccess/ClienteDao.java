/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifnmg.trabalho.DataAccess;

import br.edu.ifnmg.trabalho.classes.Cliente;
import br.edu.ifnmg.trabalho.classes.ErroValidacaoException;
import br.edu.ifnmg.trabalho.classes.Pagamento;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author aluno
 */
public class ClienteDao {
    private Bd bd;
    
    public ClienteDao(){
        bd = new Bd();
    }
    
    //Função para pegar chave estrangeira
    public int ChaveEstrangeira(int Cpf) throws SQLException{
         
         //Aqui estou fazendo uma consulta de pessoa(id) atravez do cpf, se e somente se cpf for igual a obj.getCpf
         PreparedStatement comandoClienteConsulta = bd.getConexao().prepareStatement("select id from pessoas where cpf = ?");
         comandoClienteConsulta.setInt(1,Cpf); 
         ResultSet resultado = comandoClienteConsulta.executeQuery();
         resultado.first();
                
         //Aqui criei uma variavel do tipo inteiro para armazenar o valor da consulta pessoas(id)
         int aux;
         aux = resultado.getInt("id");
               
         return aux;
    }
   
    public boolean Salvar(Cliente obj) {
        try {
            if (obj.getId() == 0) {
                PreparedStatement comando = bd.getConexao().prepareStatement("insert into pessoas(nome,cpf,rg,data_nasc) values(?,?,?,?)");
                comando.setString(1, obj.getNome());
                comando.setInt(2, obj.getCpf());
                comando.setInt(3, obj.getRg());
                comando.setString(4,"19000101");
                
                 //Pimeiramente estou inserindo os dados na tabela pessoa, para atender a obrigatoriedade da chave estrangeira em Clientes(pessoa)
                comando.executeUpdate();
             
                //Aqui estou inserindo a chave estrageira pessoas(id) correspondente ao cpf para inserir na tabela clientes
                PreparedStatement comandoCliente = bd.getConexao().prepareStatement("insert into clientes(pessoa) values(?)");
                comandoCliente.setInt(1, ChaveEstrangeira(obj.getCpf())); 
                
               
               
                //Agora estou inserindo a chave estrangeira pessoas(id) na Tabela cliente(pessoa)
                comandoCliente.executeUpdate();
          
            } else {
                PreparedStatement comando = bd.getConexao().prepareStatement("update pessoas set nome =?,cpf =?,rg =?, data_nasc =? where id = ?");
                comando.setString(1, obj.getNome());
                comando.setInt(2, obj.getCpf());
                comando.setInt(3, obj.getRg());
                comando.setInt(4, obj.getId());
                
                comando.executeUpdate();
            }
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ProdutoDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
     public Cliente Abrir(int id) throws ErroValidacaoException {
        try {
            Cliente clien = new Cliente(0, "");

            PreparedStatement comando = bd.getConexao().prepareStatement("select * from clientes where id = ?");
            comando.setInt(1, id);
            ResultSet resultado = comando.executeQuery();

            resultado.first();

            clien.setId(resultado.getInt("id"));
            clien.setNome(resultado.getString("nome"));
            clien.setCpf(resultado.getInt("cpf"));
            clien.setRg(resultado.getInt("rg"));
            clien.setData(resultado.getDate("data_nasc"));
            
           

            return clien;

        } catch (SQLException ex) {
            Logger.getLogger(ProdutoDao.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public boolean Apagar(Cliente obj) {
        try {
            PreparedStatement comando = bd.getConexao().prepareStatement("delete from clientes where id = ?");
            comando.setInt(1, obj.getId());
            comando.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ProdutoDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public List<Cliente> listarTodos() throws ErroValidacaoException {
        try {
            PreparedStatement comando = bd.getConexao().prepareStatement("select * from pessoas");
            ResultSet resultado = comando.executeQuery();
            // Cria uma lista de pagamentos vazia
            List<Cliente> clien = new LinkedList<>();
            while(resultado.next()){
              // Inicializa um objeto de pagamentos vazio
                Cliente tmp = new Cliente();
                // Pega os valores do retorno da consulta e coloca no objeto
                tmp.setId(resultado.getInt("id"));
                tmp.setNome(resultado.getString("nome"));
                tmp.setCpf(resultado.getInt("cpf"));
                tmp.setRg(resultado.getInt("rg"));
               // tmp.setData(resultado.getDate("data"));
                
                
                // Pega o objeto e coloca na lista
                clien.add(tmp);
                
            }
            return clien;
        } catch (SQLException ex) {
            Logger.getLogger(ProdutoDao.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public List<Cliente> buscar(Cliente filtro) throws ErroValidacaoException {
        try {
            
            String sql = "select * from clientes ";
            String where = "";
            
            if(filtro.getNome().length() > 0){
                where = "nome like '%"+filtro.getNome()+"%'";
            }
            
            if (filtro.getCpf() > 0) {
                if(where.length() > 0) {
                    where = where + " and ";
                }
                where = where + " cpf = " + filtro.getCpf();
            }
            
            if (filtro.getRg() > 0) {
                if(where.length() > 0) {
                    where = where + " and ";
                }
                where = where + " rg = " + filtro.getRg();
            }
            
            if (filtro.getId() > 0) {
                if(where.length() > 0) {
                    where = where + " and ";
                }
                where = where + " id = " + filtro.getId();
            }
            
    
            if(where.length() > 0){
                sql = sql + " where " + where;
            }
            
            Statement comando = bd.getConexao().createStatement();
            
            ResultSet resultado = comando.executeQuery(sql);
            // Cria uma lista de pagamento vazia
            List<Cliente> clien = new LinkedList<>();
            while (resultado.next()) {
                // Inicializa um objeto de pagamento vazio
                Cliente tmp = new Cliente();
                // Pega os valores do retorno da consulta e coloca no objeto
                tmp.setId(resultado.getInt("id"));
                tmp.setNome(resultado.getString("nome"));
                tmp.setCpf(resultado.getInt("cpf"));
                tmp.setRg(resultado.getInt("rg"));
     
                // Pega o objeto e coloca na lista
                clien.add(tmp);
            }
            return clien;
        } catch (SQLException ex) {
            Logger.getLogger(ProdutoDao.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
}

