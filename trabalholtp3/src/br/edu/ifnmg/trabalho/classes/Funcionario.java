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
public class Funcionario extends Pessoa{
    private int Id;
    private String Usuario;
    private String Senha;
    
    //----
    
    public Funcionario(){
        Id = 0;
        Usuario = "";
        Senha = "";
    }
    
    //Metodo Principal
    public static void main(String args[]){
    
    }
    
    //----
    
     public int getId(){
        return Id;
    }
    
    public void setId(int Id){
        this.Id = Id;
    }
    
    public String getUsuario() {
        return Usuario;
    }

    public void setUsuario(String Usuario) {
        this.Usuario = Usuario;  
    }

    public String getSenha() {
        return Senha;
    }

    public void setSenha(String Senha) {
        this.Senha = Senha;
    }

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
        final Funcionario other = (Funcionario) obj;
        if (this.Id != other.Id) {
            return false;
        }
        if (!Objects.equals(this.Usuario, other.Usuario)) {
            return false;
        }
        if (!Objects.equals(this.Senha, other.Senha)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Funcionario{" + "Id=" + Id + ", Usuario=" + Usuario + ", Senha=" + Senha + '}';
    }
    
    
}
