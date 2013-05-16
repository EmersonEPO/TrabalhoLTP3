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
public class Endereco {
    private int Id;
    private String Rua;
    private int Num;
    private String Bairro;
    private String Cidade;
    private String Cep;
    private String Estdo;
    
    //----
    
    public Endereco(){
        Id = 0;
        Rua = "vazio";
        Num = 0;
        Bairro = "vazio";
    }

    public Endereco(int i, String rua, String num, String bairro) {
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
            throw new ErroValidacaoException("Id não pode ser menor que zero");
        }
    }
    
    public String getRua() {
        return Rua;
    }

    public void setRua(String Rua) {
        this.Rua = Rua;
    }

    public int getNum() {
        return Num;
    }

    public String getCidade() {
        return Cidade;
    }

    public void setCidade(String Cidade) {
        this.Cidade = Cidade;
    }

    public String getCep() {
        return Cep;
    }

    public void setCep(String Cep) {
        this.Cep = Cep;
    }

    public String getEstdo() {
        return Estdo;
    }

    public void setEstdo(String Estdo) {
        this.Estdo = Estdo;
    }

    
    public void setNum(int Num) throws ErroValidacaoException {
        if(Num >= 0){
        this.Num = Num;
        }else{
            throw new ErroValidacaoException("Numero não pode ser menor que zero");
        }
    }

    public String getBairro() {
        return Bairro;
    }

    public void setBairro(String Bairro) {
        this.Bairro = Bairro;
    }

    //----

    @Override
    public int hashCode() {
        int hash = 5;
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
        final Endereco other = (Endereco) obj;
        if (this.Id != other.Id) {
            return false;
        }
        if (!Objects.equals(this.Rua, other.Rua)) {
            return false;
        }
        if (this.Num != other.Num) {
            return false;
        }
        if (!Objects.equals(this.Bairro, other.Bairro)) {
            return false;
        }
        if (!Objects.equals(this.Cidade, other.Cidade)) {
            return false;
        }
        if (!Objects.equals(this.Cep, other.Cep)) {
            return false;
        }
        if (!Objects.equals(this.Estdo, other.Estdo)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Endereco{" + "Id=" + Id + ", Rua=" + Rua + ", Num=" + Num + ", Bairro=" + Bairro + ", Cidade=" + Cidade + ", Cep=" + Cep + ", Estdo=" + Estdo + '}';
    }

   
    
    
   

   
    
    
    
    
    
  
}
