package com.touchquiz.exceptions;

/***
 * Exceção lançada quando o tipo de rodada é inválido.
 * 
 * @author Larissa Palhares
 */
public class TipoRodadaInvalidoException extends Exception {
    
    /**
     * Construtor da exceção.
     * 
     * @param mensagem Mensagem de erro a ser exibida.
     */
    public TipoRodadaInvalidoException(String mensagem) {
        super(mensagem);
    }
}
