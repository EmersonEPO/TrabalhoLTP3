/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifnmg.trabalho.DataAccess;

import br.edu.ifnmg.trabalho.classes.Email;
import br.edu.ifnmg.trabalho.classes.Endereco;
import br.edu.ifnmg.trabalho.classes.ErroValidacaoException;
import br.edu.ifnmg.trabalho.classes.Funcionario;
import br.edu.ifnmg.trabalho.classes.Telefone;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    
   
   
    public boolean Salvar(Funcionario obj, int i) throws SQLException {
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
                PreparedStatement comando = bd.getConexao().prepareStatement("update pessoas set nome=?,cpf=?,rg=?,data_nasc=? where id=? and status=1");
                comando.setString(1, obj.getNome());
                comando.setInt(2, obj.getCpf());
                comando.setInt(3, obj.getRg());
                java.sql.Date dataBd = new java.sql.Date(obj.getData().getTime());
                comando.setDate(4,dataBd);
                comando.setInt(5, obj.getId());
                comando.executeUpdate();
                
                PreparedStatement comandofunc = bd.getConexao().prepareStatement("update funcionarios set usuario=?,senha=? where id=? and status=1");
                comandofunc.setString(1, obj.getUsuario());
                comandofunc.setString(2, obj.getSenha());
                comandofunc.setInt(3, obj.getId());
                comandofunc.executeUpdate();
                
            }
           
            if(i==1){
                //Salvando Telefone
                for (Telefone tel : obj.getTelefones()) {
                    if (tel.getId() == 0) {
                        PreparedStatement comando = bd.getConexao().prepareStatement("insert into telefones(pessoa,num,status) values(?,?,?)");

                        comando.setInt(1, ChaveEstrangeira(obj.getCpf()));
                        comando.setInt(2, tel.getNum());
                        comando.setInt(3, 1);
                        comando.executeUpdate();

                    } else {
                        PreparedStatement comando = bd.getConexao().prepareStatement("update telefones set num=? where id=? and status=1"); 
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
                        PreparedStatement comando = bd.getConexao().prepareStatement("insert into enderecos(pessoa,rua,num,bairro,status) values(?,?,?,?,?)");

                        comando.setInt(1, ChaveEstrangeira(obj.getCpf()));
                        comando.setString(2, en.getRua());
                        comando.setInt(3, en.getNum());
                        comando.setString(4, en.getBairro());
                        comando.setInt(5, 1);
                        comando.executeUpdate();

                    } else {
                        PreparedStatement comando = bd.getConexao().prepareStatement("update enderecos set rua=?,num=?,bairro=?  where id=? and status=1"); 
                        comando.setString(1, en.getRua());
                        comando.setInt(2, en.getNum());
                        comando.setString(3, en.getBairro());
                        comando.setInt(4, ChaveEstrangeira(obj.getCpf()));
                        comando.executeUpdate();
                    }

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

            PreparedStatement comando = bd.getConexao().prepareStatement(""
                    + " select p.id,p.nome,p.cpf,p.rg,p.data_nasc,p.status,f.usuario,f.senha "
                    + " from pessoas p "
                    + " inner join funcionarios f on (f.pessoa=p.id) "
                    + " where p.id=? and p.status=1 "
                    + " order by p.id");
            comando.setInt(1, id);
            ResultSet resultado = comando.executeQuery();

            resultado.first();

            func.setId(resultado.getInt("p.id"));
            func.setNome(resultado.getString("p.nome"));
            func.setCpf(resultado.getInt("p.cpf"));
            func.setRg(resultado.getInt("p.rg"));
            func.setDataRetorno(resultado.getString("p.data_nasc"));
            func.setUsuario(resultado.getString("f.usuario"));
            func.setSenha(resultado.getString("f.senha"));
           
            resultado.first();
            
            return func;

        } catch (SQLException ex) {
            Logger.getLogger(ProdutoDao.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public boolean Apagar(Funcionario obj) {
        try {
            PreparedStatement comando = bd.getConexao().prepareStatement("update funcionarios set status=0 where pessoa=? ");
            comando.setInt(1, obj.getId());
            comando.executeUpdate();
            
            PreparedStatement comandoPessoa = bd.getConexao().prepareStatement("update pessoas set status=0 where id=? ");
            comandoPessoa.setInt(1, obj.getId());
            comandoPessoa.executeUpdate();
            
            PreparedStatement comandoTele = bd.getConexao().prepareStatement("update telefones set status=0 where pessoa=? ");
            comandoTele.setInt(1, obj.getId());
            comandoTele.executeUpdate();
            
            PreparedStatement comandoEma = bd.getConexao().prepareStatement("update emails set status=0 where pessoa=? ");
            comandoEma.setInt(1, obj.getId());
            comandoEma.executeUpdate();
            
            PreparedStatement comandoEnd = bd.getConexao().prepareStatement("update enderecos set status=0 where pessoa=? ");
            comandoEnd.setInt(1, obj.getId());
            comandoEnd.executeUpdate();
            
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ProdutoDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public List<Funcionario> listarTodos() throws ErroValidacaoException, ParseException {
        try {
            PreparedStatement comando = bd.getConexao().prepareStatement(""
                    + " select p.id,p.nome,p.cpf,p.rg,p.data_nasc,f.usuario,f.senha,f.status,p.status "
                    + " from pessoas p "
                    + " inner join funcionarios f on (f.pessoa=p.id) "
                    + " where p.status=1 and f.status=1 "
                    + " order by p.id ");
            ResultSet resultado = comando.executeQuery();
            // Cria uma lista de pagamentos vazia
            List<Funcionario> func = new LinkedList<>();
            while(resultado.next()){
              // Inicializa um objeto de Cliente vazio
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
            Logger.getLogger(ProdutoDao.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
  
    public List<Funcionario> buscar(Funcionario filtro) throws ErroValidacaoException {
        try {
            
            String sql = "select p.id,p.nome,p.cpf,p.rg,p.data_nasc,f.usuario,f.senha,f.status,p.status from funcionarios f inner join pessoas p on (p.id=f.pessoa) ";
            String where = "";
            
            if(filtro.getNome().length() > 0){
                where = "p.nome like '%"+filtro.getNome()+"%'";
                where = where + " and p.status=1 order by p.id ";
            }
            
            if(where.length() > 0){
                sql = sql + " where " + where;
            }
            
            if(where.length() == 0){
                where = where + " p.status=1 ";
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

