package com.touchquiz.exceptions;

/**
 * Exceçao lançada quando o modo de pontuação é inválido.
 * 
 * @author Larissa Palhares
 */
public class ModoPontuacaoInvalidoException extends Exception {

    /**
     * Construtor da exceção.
     *
     * @param mensagem Mensagem de erro a ser exibida.
     */
    public ModoPontuacaoInvalidoException(String mensagem) {
        super(mensagem);
    }
    
}
