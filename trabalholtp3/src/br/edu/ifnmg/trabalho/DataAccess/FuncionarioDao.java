/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifnmg.trabalho.DataAccess;

import br.edu.ifnmg.trabalho.classes.Cliente;
import br.edu.ifnmg.trabalho.classes.Email;
import br.edu.ifnmg.trabalho.classes.Endereco;
import br.edu.ifnmg.trabalho.classes.ErroValidacaoException;
import br.edu.ifnmg.trabalho.classes.Funcionario;
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
public class FuncionarioDao {
    private Bd bd;
    
    public FuncionarioDao(){
        bd = new Bd();
    }
    
    //Função para pegar chave estrangeira
    public int ChaveEstrangeira(int Cpf) throws SQLException{
         
         //Aqui estou fazendo uma consulta de pessoa(id) atravez do cpf, se e somente se cpf for igual a obj.getCpf
         PreparedStatement comandoConsulta = bd.getConexao().prepareStatement("select id from pessoas where cpf = ? and status=1");
         comandoConsulta.setInt(1,Cpf); 
         ResultSet resultado = comandoConsulta.executeQuery();
         resultado.first();
                
         //Aqui criei uma variavel do tipo inteiro para armazenar o valor da consulta pessoas(id)
         int aux;
         aux = resultado.getInt("id");
       
         return aux;
    }
    
   
   
    public boolean Salvar(Funcionario obj) throws SQLException {
        try {
            if (obj.getId() == 0) {
                PreparedStatement comando = bd.getConexao().prepareStatement("insert into pessoas(nome,cpf,rg,data_nasc,status) values(?,?,?,?,?)");
                comando.setString(1, obj.getNome());
                comando.setInt(2, obj.getCpf());
                comando.setInt(3, obj.getRg());
                
                //converter data
                java.sql.Date dataBd = new java.sql.Date(obj.getData().getTime());
                
                comando.setDate(4,dataBd);
                comando.setInt(5,1);
                
                //Pimeiramente estou inserindo os dados na tabela pessoa, para atender a obrigatoriedade da chave estrangeira em Clientes(pessoa)
                comando.executeUpdate();
             
                //Aqui estou inserindo a chave estrageira pessoas(id) correspondente ao cpf para inserir na tabela clientes
                PreparedStatement comandofunc = bd.getConexao().prepareStatement("insert into funcionarios(pessoa,usuario,senha,status) values(?,?,?,?)");
                comandofunc.setInt(1, ChaveEstrangeira(obj.getCpf())); 
                comandofunc.setString(2, obj.getUsuario());
                comandofunc.setString(3, obj.getSenha());
                comandofunc.setInt(4, 1);
               
                
                //Agora estou inserindo a chave estrangeira pessoas(id) na Tabela cliente(pessoa)
                comandofunc.executeUpdate();
          
            } else {
                PreparedStatement comando = bd.getConexao().prepareStatement("update pessoas set nome =?,cpf =?,rg =?, data_nasc =? where id = ? and status=1");
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
                    PreparedStatement comando = bd.getConexao().prepareStatement("insert into telefones(pessoa,num,status) values(?,?,?)");
                    
                    comando.setInt(1, ChaveEstrangeira(obj.getCpf()));
                    comando.setInt(2, tel.getNum());
                    comando.setInt(3, 1);
                    comando.executeUpdate();
   
                } else {
                    PreparedStatement comando = bd.getConexao().prepareStatement("update telefones set num = ? where id = ? and status=1"); 
                    comando.setInt(1, tel.getNum());
                    comando.setInt(2, ChaveEstrangeira(obj.getCpf()));
                    comando.executeUpdate();
                }
       
            }
            
           
            //Salvando Email
            for (Email em : obj.getEmails()) {
                if (em.getId() == 0) {
                    PreparedStatement comando = bd.getConexao().prepareStatement("insert into emails(pessoa,email,status) values(?,?,?)");
                    
                    comando.setInt(1, ChaveEstrangeira(obj.getCpf()));
                    comando.setString(2, em.getEmail_nome());
                    comando.setInt(3, 1);
                    comando.executeUpdate();
   
                } else {
                    PreparedStatement comando = bd.getConexao().prepareStatement("update emails set email=? where id=? and status=1"); 
                    comando.setString(1, em.getEmail_nome());
                    comando.setInt(2, ChaveEstrangeira(obj.getCpf()));
                    comando.executeUpdate();
                }
       
            }
            
            //Salvando Endereços
            for (Endereco en : obj.getEnderecos()) {
                if (en.getId() == 0) {
                    PreparedStatement comando = bd.getConexao().prepareStatement("insert into enderecos(pessoa,rua,num,bairro,cidade,cep,estado,status) values(?,?,?,?,?,?,?,?)");
                    
                    comando.setInt(1, ChaveEstrangeira(obj.getCpf()));
                    comando.setString(2, en.getRua());
                    comando.setInt(3, en.getNum());
                    comando.setString(4, en.getBairro());
                    comando.setString(6, en.getCidade());
                    comando.setString(6, en.getCep());
                    comando.setString(7, en.getEstdo());
                    comando.setInt(8, 1);
                    comando.executeUpdate();
   
                } else {
                    PreparedStatement comando = bd.getConexao().prepareStatement("update emails set rua=?,num =?,bairro=?,cidade=?,cep=?,estado=?  where id =? and status=1"); 
                    comando.setString(1, en.getRua());
                    comando.setInt(2, en.getNum());
                    comando.setString(3, en.getBairro());
                    comando.setString(4, en.getCidade());
                    comando.setString(5, en.getCep());
                    comando.setString(6, en.getEstdo());
                    comando.setInt(7, ChaveEstrangeira(obj.getCpf()));
                    comando.executeUpdate();
                }
       
            }
            
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ProdutoDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        
    }
    
     public Funcionario Abrir(int id) throws ErroValidacaoException {
        try {
            Funcionario func = new Funcionario(0, "");

            PreparedStatement comando = bd.getConexao().prepareStatement("select "
                    + "*"
                    + "from pessoas p "
                    + "inner join funcionarios f on (f.pessoa=p.id) "
                    + "where p.id =? "
                    + "order by p.nome");
            comando.setInt(1, id);
            ResultSet resultado = comando.executeQuery();

            resultado.first();

            func.setId(resultado.getInt("id"));
            func.setNome(resultado.getString("nome"));
            func.setCpf(resultado.getInt("cpf"));
            func.setRg(resultado.getInt("rg"));
            func.setDataRetorno(resultado.getString("data_nasc"));
            
  

            resultado.first();
            
            

            return func;

        } catch (SQLException ex) {
            Logger.getLogger(ProdutoDao.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public boolean Apagar(Funcionario obj) {
        try {
            PreparedStatement comando = bd.getConexao().prepareStatement("update funcionarios set status=0 where id = ?");
            comando.setInt(1, obj.getId());
            comando.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ProdutoDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public List<Funcionario> listarTodos() throws ErroValidacaoException, ParseException {
        try {
            PreparedStatement comando = bd.getConexao().prepareStatement("select * from pessoas p inner join funcionarios f on (f.pessoa=p.id) order by p.id");
            ResultSet resultado = comando.executeQuery();
            // Cria uma lista de pagamentos vazia
            List<Funcionario> func = new LinkedList<>();
            while(resultado.next()){
              // Inicializa um objeto de Cliente vazio
                Funcionario tmp = new Funcionario();
                // Pega os valores do retorno da consulta e coloca no objeto
                tmp.setId(resultado.getInt("id"));
                tmp.setNome(resultado.getString("nome"));
                tmp.setCpf(resultado.getInt("cpf"));
                tmp.setRg(resultado.getInt("rg"));
                tmp.setDataRetorno(resultado.getString("data_nasc"));
                tmp.setUsuario(resultado.getString("usuario"));
                tmp.setSenha(resultado.getString("senha"));
                
                // Pega o objeto e coloca na lista
                func.add(tmp);
                
            }
            return func;
        } catch (SQLException ex) {
            Logger.getLogger(ProdutoDao.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
  
    public List<Funcionario> buscar(Funcionario filtro) throws ErroValidacaoException {
        try {
            
            String sql = "select * from funcionarios f inner join pessoas p on (p.id=f.pessoa) ";
            String where = "";
            
            if(filtro.getNome().length() > 0){
                where = "p.nome like '%"+filtro.getNome()+"%'";
                 where = where + " and status = 1 ";
            }
            
            if(where.length() > 0){
                sql = sql + " where " + where;
            }
            
            if(where.length() > 0){
                where = where + " status = 1 ";
                sql = sql + " where " + where;
            }
            
            Statement comando = bd.getConexao().createStatement();
            
            ResultSet resultado = comando.executeQuery(sql);
            // Cria uma lista de pessoas vazia
            List<Funcionario> func = new LinkedList<>();
            while (resultado.next()) {
                // Inicializa um objeto de pagamento vazio
                Funcionario tmp = new Funcionario();
                // Pega os valores do retorno da consulta e coloca no objeto
                tmp.setId(resultado.getInt("p.id"));
                tmp.setNome(resultado.getString("p.nome"));
                tmp.setCpf(resultado.getInt("p.cpf"));
                tmp.setRg(resultado.getInt("p.rg"));
                tmp.setDataRetorno(resultado.getString("p.data_nasc"));
                tmp.setUsuario(resultado.getString("f.usuario"));
                tmp.setSenha(resultado.getString("f.senha"));

                // Pega o objeto e coloca na lista
                func.add(tmp);
            }
            return func;
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDao.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
}

