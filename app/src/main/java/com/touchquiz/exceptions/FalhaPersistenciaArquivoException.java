package com.touchquiz.exceptions;

/**
 * Exceção que representa uma falha na persistência de um arquivo.
 */
public class FalhaPersistenciaArquivoException extends RuntimeException {
    /**
     * Construtor da classe FalhaPersistenciaArquivoException.
     *
     * @param message A mensagem de erro associada ao erro.
     */
    public FalhaPersistenciaArquivoException(String message) {
        super(message);
    }
}
