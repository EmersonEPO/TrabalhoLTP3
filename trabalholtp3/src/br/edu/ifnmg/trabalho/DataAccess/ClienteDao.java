/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifnmg.trabalho.DataAccess;

import br.edu.ifnmg.trabalho.classes.Cliente;
import br.edu.ifnmg.trabalho.classes.Email;
import br.edu.ifnmg.trabalho.classes.Endereco;
import br.edu.ifnmg.trabalho.classes.ErroValidacaoException;
import br.edu.ifnmg.trabalho.classes.Telefone;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import sun.security.util.Length;

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
    
    //Função para formartar data 00/00/0000 para 00000-00-00
    
   
    public boolean Salvar(Cliente obj) throws SQLException {
        try {
            if (obj.getId() == 0) {
                PreparedStatement comando = bd.getConexao().prepareStatement("insert into pessoas(nome,cpf,rg,data_nasc) values(?,?,?,?)");
                comando.setString(1, obj.getNome());
                comando.setInt(2, obj.getCpf());
                comando.setInt(3, obj.getRg());
                
                //converter data
                java.sql.Date dataBd = new java.sql.Date(obj.getData().getTime());
                
                comando.setDate(4,dataBd);
                
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
                //Conveter Data
                java.sql.Date dataBd = new java.sql.Date(obj.getData().getTime());
                
                comando.setDate(4,dataBd);
                
                comando.executeUpdate();
            }
           
            //Salvando Telefone
            for (Telefone tel : obj.getTelefones()) {
                if (tel.getId() == 0) {
                    PreparedStatement comando = bd.getConexao().prepareStatement("insert into telefones(pessoa,num) values(?,?)");
                    
                    comando.setInt(1, ChaveEstrangeira(obj.getCpf()));
                    comando.setInt(2, tel.getNum());
                    comando.executeUpdate();
   
                } else {
                    PreparedStatement comando = bd.getConexao().prepareStatement("update telefones set num = ? where id = ?"); 
                    comando.setInt(1, tel.getNum());
                    comando.setInt(2, ChaveEstrangeira(obj.getCpf()));
                    comando.executeUpdate();
                }
       
            }
            
           
            //Salvando Email
            for (Email em : obj.getEmails()) {
                if (em.getId() == 0) {
                    PreparedStatement comando = bd.getConexao().prepareStatement("insert into emails(pessoa,email) values(?,?)");
                    
                    comando.setInt(1, ChaveEstrangeira(obj.getCpf()));
                    comando.setString(2, em.getEmail_nome());
                    comando.executeUpdate();
   
                } else {
                    PreparedStatement comando = bd.getConexao().prepareStatement("update emails set email = ? where id =?"); 
                    comando.setString(1, em.getEmail_nome());
                    comando.setInt(2, ChaveEstrangeira(obj.getCpf()));
                    comando.executeUpdate();
                }
       
            }
            
            //Salvando Endereços
            for (Endereco en : obj.getEnderecos()) {
                if (en.getId() == 0) {
                    PreparedStatement comando = bd.getConexao().prepareStatement("insert into enderecos(pessoa,rua,num,bairro) values(?,?,?,?)");
                    
                    comando.setInt(1, ChaveEstrangeira(obj.getCpf()));
                    comando.setString(2, en.getRua());
                    comando.setInt(3, en.getNum());
                    comando.setString(4, en.getBairro());
                    comando.executeUpdate();
   
                } else {
                    PreparedStatement comando = bd.getConexao().prepareStatement("update emails set rua =?, num =?, bairro =?  where id =?"); 
                    comando.setString(1, en.getRua());
                    comando.setInt(2, en.getNum());
                    comando.setString(3, en.getBairro());
                    comando.setInt(4, ChaveEstrangeira(obj.getCpf()));
                    comando.executeUpdate();
                }
       
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

            PreparedStatement comando = bd.getConexao().prepareStatement("select * from pessoas where id = ?");
            comando.setInt(1, id);
            ResultSet resultado = comando.executeQuery();

            resultado.first();

            clien.setId(resultado.getInt("id"));
            clien.setNome(resultado.getString("nome"));
            clien.setCpf(resultado.getInt("cpf"));
            clien.setRg(resultado.getInt("rg"));
            clien.setDataRetorno(resultado.getString("data_nasc"));
            
  

            resultado.first();
            
            

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
    
    public List<Cliente> listarTodos() throws ErroValidacaoException, ParseException {
        try {
            PreparedStatement comando = bd.getConexao().prepareStatement("select * from pessoas");
            ResultSet resultado = comando.executeQuery();
            // Cria uma lista de pagamentos vazia
            List<Cliente> clien = new LinkedList<>();
            while(resultado.next()){
              // Inicializa um objeto de Cliente vazio
                Cliente tmp = new Cliente();
                // Pega os valores do retorno da consulta e coloca no objeto
                tmp.setId(resultado.getInt("id"));
                tmp.setNome(resultado.getString("nome"));
                tmp.setCpf(resultado.getInt("cpf"));
                tmp.setRg(resultado.getInt("rg"));
                tmp.setDataRetorno(resultado.getString("data_nasc"));
                
               
                
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
            
            String sql = "select * from pessoas";
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
            // Cria uma lista de pessoas vazia
            List<Cliente> clien = new LinkedList<>();
            while (resultado.next()) {
                // Inicializa um objeto de pagamento vazio
                Cliente tmp = new Cliente();
                // Pega os valores do retorno da consulta e coloca no objeto
                tmp.setId(resultado.getInt("id"));
                tmp.setNome(resultado.getString("nome"));
                tmp.setCpf(resultado.getInt("cpf"));
                tmp.setRg(resultado.getInt("rg"));
                tmp.setDataRetorno(resultado.getString("data_nasc"));
                
     
                // Pega o objeto e coloca na lista
                clien.add(tmp);
            }
            return clien;
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDao.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
}

