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

    public Cliente(int i, String nom) throws ErroValidacaoException {
        this.Id = i;
        this.setNome(nom);
        
    }
   
    //----
    
    public int getId(){
        return Id;
    }
    

    public void setId(int Id) throws ErroValidacaoException{
        if(Id >= 0){
            this.Id = Id;
        }else{
            throw new ErroValidacaoException("Id não pode ser menor que zero!");
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
