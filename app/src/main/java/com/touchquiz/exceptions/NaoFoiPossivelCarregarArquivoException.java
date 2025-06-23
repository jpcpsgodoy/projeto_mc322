package com.touchquiz.exceptions;

/**
 * Exceção lançada quando não é possível carregar um arquivo.
 * 
 * @author João Pedro
 */
public class NaoFoiPossivelCarregarArquivoException extends Exception {

    /**
     * Construtor da exceção.
     * 
     * @param mensagem Mensagem de erro a ser exibida.
     */
    public NaoFoiPossivelCarregarArquivoException(String mensagem) {
        super(mensagem);
    }
}