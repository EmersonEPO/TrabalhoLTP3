/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifnmg.trabalho.classes;

/**
 *
 * @author emerson
 */
public class Cliente extends Pessoa {
    private int Id;
    
    //----
    
    public Cliente(){
        Id = 0;
    }
    
    //----
    
    public int getId(){
        return Id;
    }
    
    public void setId(int Id){
        this.Id = Id;
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
        final Cliente other = (Cliente) obj;
        if (this.Id != other.Id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Cliente{" + "Id=" + Id + '}';
    }
    
    
    
    
    
    
    
    
    
    
    
    
}
