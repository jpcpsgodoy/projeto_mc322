package com.futquiz.model;

/**
 * Classe que representa uma rodada normal no jogo FutQuiz
 * @author Larissa Palhares
 */
public class RodadaNormal extends Rodada {
    
    /*
     * Construtor da classe RodadaNormal
     */
    public RodadaNormal(int meta, ModoPontuacao modoPontuacao) {
        super(meta, modoPontuacao);
        this.exibeEstatisticas = true;
    }

    /**
     * Método que inicia a rodada normal, criando os multiplicadores
     */
    @Override
    public void iniciarRodada() {
        this.multiplicadores = MultiplicadorFactory.criarMultiplicadores(meta);
    }

}
