package com.futquiz.model;

/**
 * Classe que representa um multiplicador de estatísticas
 * 
 * @author João Pedro
 */
public class Multiplicador {
    private final int valor;

    /**
     * Construtor da classe Multiplicador
     * 
     * @param valor o valor do multiplicador
     */
    public Multiplicador(int valor) {
        this.valor = valor;
    }

    /**
     * Aplica o multiplicador a uma estatística
     * 
     * @param estatistica a estatística a ser multiplicada
     * @return o resultado da multiplicação
     */
    public int aplicar(int estatistica) {
        return estatistica * valor;
    }

    /**
     * Retorna o valor do multiplicador
     * 
     * @return o valor do multiplicador
     */
    public int getValor() {
        return valor;
    }
}
