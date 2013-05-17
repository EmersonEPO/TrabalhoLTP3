/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifnmg.trabalho.DataAccess;

import br.edu.ifnmg.trabalho.classes.Email;
import br.edu.ifnmg.trabalho.classes.Endereco;
import br.edu.ifnmg.trabalho.classes.ErroValidacaoException;
import br.edu.ifnmg.trabalho.classes.Telefone;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author emerson
 */
public class ClienteFuncionarioDao {
    private Bd bd;
    
    public ClienteFuncionarioDao(){
        bd = new Bd();
    
    }
    
    public List<Telefone> listarTodosTelefone() throws ErroValidacaoException, ParseException {
        try {
            PreparedStatement comando = bd.getConexao().prepareStatement("select p.id,t.pessoa as pessoa,t.num as num,p.status,c.status,t.status "
                    + " from clientes c "
                    + " inner join pessoas p on (p.id=c.pessoa)"
                    + " inner join telefones t on (p.id=t.pessoa)"
                    + " where p.status=1 and t.status=1 and c.status=1 "
                    + " order by p.id ");
            ResultSet resultado = comando.executeQuery();
            // Cria uma lista de pagamentos vazia
            List<Telefone> t = new LinkedList<>();
            while(resultado.next()){
              // Inicializa um objeto de Cliente vazio
                Telefone tmp = new Telefone();
                // Pega os valores do retorno da consulta e coloca no objeto
                tmp.setId(resultado.getInt("pessoa"));
                tmp.setNum(resultado.getInt("num"));
                
            
                // Pega o objeto e coloca na lista
                t.add(tmp);
                
            }
            return t;
        } catch (SQLException ex) {
            Logger.getLogger(ProdutoDao.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
     public List<Email> listarTodosEmail() throws ErroValidacaoException, ParseException {
        try {
            PreparedStatement comando = bd.getConexao().prepareStatement("select p.id,e.pessoa,e.email,p.status,c.status,e.status "
                    + " from clientes c "
                    + " inner join pessoas p on (p.id=c.pessoa) "
                    + " inner join emails e on (p.id=e.pessoa) "
                    + " where p.status=1 and e.status=1 and c.status=1 "
                    + " order by p.id ");
            ResultSet resultado = comando.executeQuery();
            // Cria uma lista de pagamentos vazia
            List<Email> t = new LinkedList<>();
            while(resultado.next()){
              // Inicializa um objeto de Cliente vazio
                Email tmp = new Email();
                // Pega os valores do retorno da consulta e coloca no objeto
                tmp.setId(resultado.getInt("e.pessoa"));
                tmp.setEmail_nome(resultado.getString("e.email"));
                
            
                // Pega o objeto e coloca na lista
                t.add(tmp);
                
            }
            return t;
        } catch (SQLException ex) {
            Logger.getLogger(ProdutoDao.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
     
    public List<Endereco> listarTodosEndereco() throws ErroValidacaoException, ParseException {
        try {
            PreparedStatement comando = bd.getConexao().prepareStatement("select p.id,e.pessoa,e.rua,e.num,e.bairro,p.status,c.status,e.status "
                    + " from clientes c "
                    + " inner join pessoas p on (p.id=c.pessoa) "
                    + " inner join enderecos e on (p.id=e.pessoa) "
                    + " where p.status=1 and e.status=1 and c.status=1 "
                    + " order by p.id ");
            
            ResultSet resultado = comando.executeQuery();
            // Cria uma lista de pagamentos vazia
            List<Endereco> e = new LinkedList<>();
            while(resultado.next()){
              // Inicializa um objeto de Cliente vazio
                Endereco tmp = new Endereco();
                // Pega os valores do retorno da consulta e coloca no objeto
                tmp.setId(resultado.getInt("e.pessoa"));
                tmp.setRua(resultado.getString("e.rua"));
                tmp.setNum(resultado.getInt("e.num"));
                tmp.setBairro(resultado.getString("e.bairro"));
  
                
                // Pega o objeto e coloca na lista
                e.add(tmp);
                
            }
            return e;
        } catch (SQLException ex) {
            Logger.getLogger(ProdutoDao.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    //============================
    
     public List<Telefone> listarTodosTelefoneFunc() throws ErroValidacaoException, ParseException {
        try {
            PreparedStatement comando = bd.getConexao().prepareStatement("select p.id,t.pessoa as pessoa,t.num as num,p.status,c.status,t.status "
                    + " from funcionarios c "
                    + " inner join pessoas p on (p.id=c.pessoa)"
                    + " inner join telefones t on (p.id=t.pessoa)"
                    + " where p.status=1 and t.status=1 and c.status=1 "
                    + " order by p.id ");
            ResultSet resultado = comando.executeQuery();
            // Cria uma lista de pagamentos vazia
            List<Telefone> t = new LinkedList<>();
            while(resultado.next()){
              // Inicializa um objeto de Cliente vazio
                Telefone tmp = new Telefone();
                // Pega os valores do retorno da consulta e coloca no objeto
                tmp.setId(resultado.getInt("pessoa"));
                tmp.setNum(resultado.getInt("num"));
                
            
                // Pega o objeto e coloca na lista
                t.add(tmp);
                
            }
            return t;
        } catch (SQLException ex) {
            Logger.getLogger(ProdutoDao.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
     public List<Email> listarTodosEmailFunc() throws ErroValidacaoException, ParseException {
        try {
            PreparedStatement comando = bd.getConexao().prepareStatement("select p.id,e.pessoa,e.email,p.status,c.status,e.status "
                    + " from funcionarios c "
                    + " inner join pessoas p on (p.id=c.pessoa) "
                    + " inner join emails e on (p.id=e.pessoa) "
                    + " where p.status=1 and e.status=1 and c.status=1 "
                    + " order by p.id ");
            ResultSet resultado = comando.executeQuery();
            // Cria uma lista de pagamentos vazia
            List<Email> t = new LinkedList<>();
            while(resultado.next()){
              // Inicializa um objeto de Cliente vazio
                Email tmp = new Email();
                // Pega os valores do retorno da consulta e coloca no objeto
                tmp.setId(resultado.getInt("e.pessoa"));
                tmp.setEmail_nome(resultado.getString("e.email"));
                
            
                // Pega o objeto e coloca na lista
                t.add(tmp);
                
            }
            return t;
        } catch (SQLException ex) {
            Logger.getLogger(ProdutoDao.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
     
    public List<Endereco> listarTodosEnderecoFunc() throws ErroValidacaoException, ParseException {
        try {
            PreparedStatement comando = bd.getConexao().prepareStatement("select p.id,e.pessoa,e.rua,e.num,e.bairro,p.status,c.status,e.status "
                    + " from funcionarios c "
                    + " inner join pessoas p on (p.id=c.pessoa) "
                    + " inner join enderecos e on (p.id=e.pessoa) "
                    + " where p.status=1 and e.status=1 and c.status=1 "
                    + " order by p.id ");
            
            ResultSet resultado = comando.executeQuery();
            // Cria uma lista de pagamentos vazia
            List<Endereco> e = new LinkedList<>();
            while(resultado.next()){
              // Inicializa um objeto de Cliente vazio
                Endereco tmp = new Endereco();
                // Pega os valores do retorno da consulta e coloca no objeto
                tmp.setId(resultado.getInt("e.pessoa"));
                tmp.setRua(resultado.getString("e.rua"));
                tmp.setNum(resultado.getInt("e.num"));
                tmp.setBairro(resultado.getString("e.bairro"));
  
                
                // Pega o objeto e coloca na lista
                e.add(tmp);
                
            }
            return e;
        } catch (SQLException ex) {
            Logger.getLogger(ProdutoDao.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
}
