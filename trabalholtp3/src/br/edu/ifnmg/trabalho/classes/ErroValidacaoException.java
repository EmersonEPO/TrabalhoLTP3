/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifnmg.trabalho.classes;

/**
 *
 * @author aluno
 */
public class ErroValidacaoException extends Exception {
    
    public ErroValidacaoException(String msg){
        super("Id menor que zero!");
    }
    
}
