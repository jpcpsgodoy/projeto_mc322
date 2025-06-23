package com.touchquiz.model;

/**
 * Classe que representa uma jogada no jogo FutQuiz.
 * Cada jogada é composta por um quarterback, um multiplicador e os pontos gerados.
 * 
 * @author Larissa Palhares
 */
public class Jogada {
    private final Quarterback quarterback;
    private final Multiplicador multiplicador;
    private final int pontosGerados;

    /**
     * Construtor da classe Jogada
     * 
     * @param quarterback Quarterback escolhido para a jogada
     * @param multiplicador Multiplicador escolhido para a jogada
     * @param pontosGerados Pontos gerados pela atribuição
     */
    public Jogada(Quarterback quarterback, Multiplicador multiplicador, int pontosGerados) {
        this.quarterback = quarterback;
        this.multiplicador = multiplicador;
        this.pontosGerados = pontosGerados;
    }

    /*
     * Metodo getter para acessar o quarterback da jogada
     */
    public Quarterback getQuarterback() {
        return quarterback;
    }

    /*
     * Metodo getter para acessar o multiplicador da jogada
     */
    public Multiplicador getMultiplicador() {
        return multiplicador;
    }

    /*
     * Metodo getter para acessar os pontos gerados pela jogada
     */
    public int getPontosGerados() {
        return pontosGerados;
    }
}