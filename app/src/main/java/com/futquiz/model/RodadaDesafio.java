package com.futquiz.model;

import java.util.List;
import java.util.Random;

/**
 * Classe que representa uma rodada de desafio no jogo FutQuiz
 * 
 * @author João Pedro
 * @author Larissa Palhares
 */
public class RodadaDesafio extends Rodada {

    /**
     * Construtor da classe RodadaDesafio
     *
     * @param meta a meta de pontos da rodada
     * @param modoPontuacao modo de pontuação da rodada (TD_PASSE ou TD_TOTAL)
     */
    public RodadaDesafio(int meta, ModoPontuacao modoPontuacao) {
        super(meta, modoPontuacao);
        this.exibeEstatisticas = false;
    }

    /**
     * Método que inicia a rodada de desafio, criando os multiplicadores
     * e aumentando a meta em 10% para ficar mais desafiador
     */
    @Override
    public void iniciarRodada() {
        this.multiplicadores = MultiplicadorFactory.criarMultiplicadores(meta);
        this.meta = (int) (meta * 1.1);
    }

    /**
     * Método que sorteia um quarterback da lista de quarterbacks
     * para a rodada de desafio. Ele escolhe aleatoriamente um quarterback
     * entre os 50 melhores, garantindo que não seja repetido.
     *
     * @param quarterbacks Lista de quarterbacks disponíveis
     * @return Quarterback sorteado
     */
    @Override
    public Quarterback sortearQuarterback(List<Quarterback> quarterbacks) {
        List<Quarterback> top50 = quarterbacks.subList(4, Math.min(50, quarterbacks.size()));
        Random rand = new Random();
        Quarterback sorteado;
        do {
            sorteado = top50.get(rand.nextInt(top50.size()));
        } while (idsSorteados.contains(sorteado.getId()));
        return sorteado;
    }
}
