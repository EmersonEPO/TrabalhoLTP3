/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifnmg.trabalho.DataAccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author aluno
 */
public class Bd { 
    private Connection Conexao;
    
    //----
    
    public Connection getConexao(){
        return Conexao;
    }
    
    public void setConexao(Connection Conexao){
        this.Conexao = Conexao;
    }
    
    public Bd(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            //Lembre-se! No IFNMG a senha é aluno, em casa devo colocar a minha senha!
            Conexao = (Connection) DriverManager.getConnection("jdbc:mysql://127.0.0.1/trabalho","root","epo123");
        
        }catch(ClassNotFoundException ex){
            Logger.getLogger(Bd.class.getName()).log(Level.SEVERE, null, ex); 
        }catch(SQLException ex){
             Logger.getLogger(Bd.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
