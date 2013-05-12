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
public class Pagamento {
    private int Id;
    private String Nome;
    private double Juros;
    
    //----
    
    public Pagamento(){
        Id = 0;
        Nome = "vazio";
        Juros = 0;
        
    }

    public Pagamento(int i, String nom) {
        this.Id = i;
        this.Nome = nom;
    }
    
    //----

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String Nome) {
        this.Nome = Nome;
    }

    public double getJuros() {
        return Juros;
    }

    public void setJuros(double Juros) {
        this.Juros = Juros;
    }
    
    //----

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + this.Id;
        hash = 47 * hash + Objects.hashCode(this.Nome);
        hash = 47 * hash + (int) (Double.doubleToLongBits(this.Juros) ^ (Double.doubleToLongBits(this.Juros) >>> 32));
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
        final Pagamento other = (Pagamento) obj;
        if (this.Id != other.Id) {
            return false;
        }
        if (!Objects.equals(this.Nome, other.Nome)) {
            return false;
        }
        if (Double.doubleToLongBits(this.Juros) != Double.doubleToLongBits(other.Juros)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Pagamento{" + "Id=" + Id + ", Nome=" + Nome + ", Juros=" + Juros + '}';
    }
    
   
}
