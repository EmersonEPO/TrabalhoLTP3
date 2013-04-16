/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifnmg.trabalho.classes;

import java.util.Objects;

/**
 *
 * @author emerson
 */
public class Produto {
    private int Id;
    private String Nome;
    private float Valor_comp;
    private float Valor_vend;
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

    public Produto(int i, String escova_de_Dentes, double d) {
        throw new UnsupportedOperationException("Not yet implemented");
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

    public float getValor_comp() {
        return Valor_comp;
    }

    public void setValor_comp(float Valor_comp) throws ErroValidacaoException {
        if(Valor_comp > 0){
            this.Valor_comp = Valor_comp;
        }else{
            throw new ErroValidacaoException("Valor de compra não pode ser menor que zero!");       
        }
    }

    public float getValor_vend() {
        return Valor_vend;
    }

    public void setValor_vend(float Valor_vend) throws ErroValidacaoException {
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
        if (Float.floatToIntBits(this.Valor_comp) != Float.floatToIntBits(other.Valor_comp)) {
            return false;
        }
        if (Float.floatToIntBits(this.Valor_vend) != Float.floatToIntBits(other.Valor_vend)) {
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
    
    
}
