
package br.edu.ifnmg.trabalho.classes;

public class ErroValidacaoException extends Exception {
    
    public ErroValidacaoException(String msg){
        super("Ocorreu um erro, consulte o administrador do sistema!");
    }
    
}
