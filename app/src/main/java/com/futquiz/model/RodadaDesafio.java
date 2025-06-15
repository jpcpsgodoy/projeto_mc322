package com.futquiz.model;

import java.util.List;
import java.util.Random;

/**
 * Classe que representa uma rodada de desafio no jogo FutQuiz
 * 
 * @author João Pedro
 */
public class RodadaDesafio extends Rodada {

    public RodadaDesafio(int meta, ModoPontuacao modoPontuacao) {
        super(meta, modoPontuacao);
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
     * Método que indica se a rodada exibe estatísticas dos quarterbacks
     */
    @Override
    public boolean exibeEstatistica() {
        return false;
    }

    @Override
    public Quarterback sortearQuarterback(List<Quarterback> quarterbacks) {
        List<Quarterback> top50 = quarterbacks.subList(0, Math.min(50, quarterbacks.size()));
        Random rand = new Random();
        Quarterback sorteado;
        do {
            sorteado = top50.get(rand.nextInt(top50.size()));
        } while (idsSorteados.contains(sorteado.getId()));
        return sorteado;
    }
}
