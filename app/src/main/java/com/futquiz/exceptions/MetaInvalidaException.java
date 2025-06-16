package com.futquiz.exceptions;

/*
 * Exceção lançada quando a meta é inválida.
 * @author Larissa Palhares
 */
public class MetaInvalidaException extends Exception {
    public MetaInvalidaException(String mensagem) {
        super(mensagem);
    }
}
