/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifnmg.trabalho.DataAccess;

import br.edu.ifnmg.trabalho.classes.ErroValidacaoException;
import br.edu.ifnmg.trabalho.classes.Item_venda;
import br.edu.ifnmg.trabalho.classes.Venda;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author emerson
 */
public class VendaDao {

    private Bd bd;

    public VendaDao() {
        bd = new Bd();
    }

    public boolean Salvar(Venda obj) {
        try {
            if (obj.getId() == 0) {
                PreparedStatement comando = bd.getConexao().prepareStatement("insert into vendas(funcionario,cliente,pagamento,data_venda,total,status) values(?,?,?,?,?,?)");
                comando.setInt(1, obj.getAtendente().getId());
                comando.setInt(2,obj.getConsumidor().getId());
                comando.setInt(3, obj.getTipo_paga().getId());
                java.sql.Date data = new java.sql.Date(obj.getData_venda().getTime());
                comando.setDate(4, data);
                comando.setDouble(5, obj.getTotal_final());
                comando.setInt(6, 1);
                comando.executeUpdate();
            } else {
                PreparedStatement comando = bd.getConexao().prepareStatement("update vendas set valorTotal = ?,data = ? where id = ?");
                comando.setDouble(1, obj.getTotal());
                java.sql.Date dt = new java.sql.Date(obj.getData_venda().getTime());
                comando.setDate(2, dt);
                comando.setDouble(2, obj.getId());
                comando.executeUpdate();
            }

            for (Item_venda iv : obj.getItens()) {
                if (iv.isAtivo()) {
                    if (iv.getId() == 0) {
                        PreparedStatement comando = bd.getConexao().prepareStatement("insert into item_venda(venda,produto,valor_item,qtd) values(?,?,?,?)");
                        comando.setInt(1, obj.getId());
                        comando.setInt(2, iv.getProduto().getId());
                        comando.setInt(3, iv.getQtd());
                        comando.executeUpdate();
                    } else {
                        PreparedStatement comando = bd.getConexao().prepareStatement("update itensvenda set produto = ?, quantidade = ? where id = ?");
                        comando.setInt(1, iv.getProduto().getId());
                        comando.setInt(2, iv.getQtd());
                        comando.setInt(3, obj.getId());
                        comando.executeUpdate();
                    }
                } else {
                    PreparedStatement comando = bd.getConexao().prepareStatement("delete from itensvenda where id = ?");
                    comando.setInt(1, obj.getId());
                    comando.executeUpdate();
                }
            }

            return true;
        } catch (SQLException ex) {
            Logger.getLogger(VendaDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public Venda Abrir(int id) throws ErroValidacaoException {
        try {
            Venda venda = new Venda();

            PreparedStatement comando = bd.getConexao().prepareStatement("select * from vendas where id = ?");
            comando.setInt(1, id);
            ResultSet resultado = comando.executeQuery();

            resultado.first();

            venda.setId(resultado.getInt("id"));
            venda.setData_venda(resultado.getDate("data"));
            venda.setTotal(resultado.getDouble("valorTotal"));

            carregaItens(id, venda);

            return venda;

        } catch (SQLException ex) {
            Logger.getLogger(VendaDao.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public boolean Apagar(Venda obj) {
        try {
            PreparedStatement comando2 = bd.getConexao().prepareStatement("delete from itensvenda where venda = ?");
            comando2.setInt(1, obj.getId());
            comando2.executeUpdate();
            
            PreparedStatement comando = bd.getConexao().prepareStatement("delete from vendas where id = ?");
            comando.setInt(1, obj.getId());
            comando.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(VendaDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public List<Venda> listarTodos() throws ErroValidacaoException {
        try {
            PreparedStatement comando = bd.getConexao().prepareStatement("select * from vendas ");
            ResultSet resultado = comando.executeQuery();
           
            List<Venda> vendas = new LinkedList<>();
            while (resultado.next()) {
                
                Venda tmp = new Venda();
                
                tmp.setId(resultado.getInt("id"));
                tmp.setData_venda(resultado.getDate("data"));
                tmp.setTotal(resultado.getDouble("valorTotal"));
               
                
                carregaItens(tmp.getId(), tmp);
                
                vendas.add(tmp);
            }
            return vendas;
        } catch (SQLException ex) {
            Logger.getLogger(VendaDao.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public List<Venda> buscar(Venda filtro) throws ErroValidacaoException {
        try {
            PreparedStatement comando = bd.getConexao().prepareStatement("select * from vendas where valorTotal = ? or data = ? or id = ? ");
            comando.setDouble(1, filtro.getTotal());
            java.sql.Date dt = new java.sql.Date(filtro.getData_venda().getTime());
            comando.setDate(2, dt);
            comando.setInt(3, filtro.getId());
            ResultSet resultado = comando.executeQuery();
            
            List<Venda> vendas = new LinkedList<>();
            while (resultado.next()) {
                
                Venda tmp = new Venda();
                
                tmp.setId(resultado.getInt("id"));
                tmp.setData_venda(resultado.getDate("data"));
                tmp.setTotal(resultado.getDouble("valorTotal"));
                
                
                carregaItens(tmp.getId(), tmp);
                
                vendas.add(tmp);
            }
            return vendas;
        } catch (SQLException ex) {
            Logger.getLogger(VendaDao.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    private void carregaItens(int id, Venda venda) throws SQLException, ErroValidacaoException {
        PreparedStatement comando2 = bd.getConexao().prepareStatement("select * from itensvenda where venda = ?");
        comando2.setInt(1, id);
        ResultSet resultado2 = comando2.executeQuery();

        List<Item_venda> itens = new LinkedList<>();
        ProdutoDao prod = new ProdutoDao();
        while (resultado2.next()) {
            Item_venda tmp = new Item_venda();
            tmp.setId(resultado2.getInt("id"));
            tmp.setQtd(resultado2.getInt("quantidade"));
            tmp.setProduto(prod.Abrir(resultado2.getInt("produto")));
            itens.add(tmp);
        }

        venda.setItens(itens);
    }
    
}