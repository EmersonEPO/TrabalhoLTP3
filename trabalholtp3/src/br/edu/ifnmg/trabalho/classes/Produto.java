/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifnmg.trabalho.classes;

import java.util.Objects;
import javax.swing.JTextField;

/**
 *
 * @author emerson
 */
public class Produto {
    private int Id;
    private String Nome;
    private double Valor_comp;
    private double Valor_vend;
    private String Descricao;
    private int Estoque;
   
    //----
    
    public Produto(){
        Id = 0;
        Nome = "vazio";
        Valor_comp = 0;
        Valor_vend = 0;
        Descricao = "vazio";
        Estoque = 0;
    }

    //Construtor para a Edição
    public Produto(int i, String nom) {
        this.Id = i;
        this.Nome = nom;
    }

    //----

    public int getId() {
        return Id;
    }

    public void setId(int Id) throws ErroValidacaoException {
        if(Id >= 0){
            this.Id = Id;
        }else{
            throw new ErroValidacaoException("Id menor que zero!");
        }
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String Nome) throws ErroValidacaoException {
        if((Nome.length() >= 3) || (Nome.length() <= 250)){
            this.Nome = Nome;
        }else{
            throw new ErroValidacaoException("Tamanho do nome é invalido!");
        }
    }

    public double getValor_comp() {
        return Valor_comp;
    }

    public void setValor_comp(double Valor_comp) throws ErroValidacaoException {
        if(Valor_comp > 0){
            this.Valor_comp = Valor_comp;
        }else{
            throw new ErroValidacaoException("Valor de compra não pode ser menor que zero!");       
        }
    }

    public double getValor_vend() {
        return Valor_vend;
    }

    public void setValor_vend(double Valor_vend) throws ErroValidacaoException {
        if(Valor_vend > 0){
            this.Valor_vend = Valor_vend;
        }else{
            throw new ErroValidacaoException("Valor de venda não pode ser menor que zero!");
        }
    }

    public String getDescricao() {
        return Descricao;
    }

    public void setDescricao(String Descricao) throws ErroValidacaoException {
        if((Descricao.length()>= 0)||(Descricao.length() <= 255)){
            this.Descricao = Descricao;
        }else{
            throw new ErroValidacaoException("Tamanho invalido para descrição!");
        }
    }

    public int getEstoque() {
        return Estoque;
    }

    public void setEstoque(int Estoque) throws ErroValidacaoException {
        if(Estoque >= 0){
            this.Estoque = Estoque;
        }else{
            throw new ErroValidacaoException("Valor de estoque não pode ser zero!");
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Produto other = (Produto) obj;
        if (this.Id != other.Id) {
            return false;
        }
        if (!Objects.equals(this.Nome, other.Nome)) {
            return false;
        }
        if (Double.doubleToLongBits(this.Valor_comp) != Double.doubleToLongBits(other.Valor_comp)) {
            return false;
        }
        if (Double.doubleToLongBits(this.Valor_vend) != Double.doubleToLongBits(other.Valor_vend)) {
            return false;
        }
        if (!Objects.equals(this.Descricao, other.Descricao)) {
            return false;
        }
        if (this.Estoque != other.Estoque) {
            return false;
        }
        return true;
    }

    

    @Override
    public String toString() {
        return "Produto{" + "Id=" + Id + ", Nome=" + Nome + ", Valor_comp=" + Valor_comp + ", Valor_vend=" + Valor_vend + ", Descricao=" + Descricao + ", Estoque=" + Estoque + '}';
    }    

    public void getNome(String text) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

  

}
