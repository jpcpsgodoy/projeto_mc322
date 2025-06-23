package com.touchquiz.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * Classe abstrata que representa a Rodada do jogo
 * @author Larissa Palhares
 * @author João Pedro
 * @author Gustavo Henrique
 */
public abstract class Rodada {
    /**
     * Conjunto de IDs de quarterbacks já sorteados para evitar repetição
     */
    protected Set<Integer> idsSorteados = new HashSet<>();

    /**
     * Indica se as estatísticas devem ser exibidas na rodada
     * A depender do modo da Rodada, pode ser true ou false
     */
    protected boolean exibeEstatisticas;

    /**
     * Meta de pontos que o jogador deve alcançar na rodada
     */
    protected int meta;

    /**
     * Lista de multiplicadores disponíveis na rodada
     */
    protected List<Multiplicador> multiplicadores;

    /**
     * Pontos acumulados pelo jogador na rodada
     */
    protected int pontosAcumulados;

    /**
     * Modo de pontuação da rodada, que pode ser TD_PASSE ou TD_TOTAL
     */
    protected ModoPontuacao modoPontuacao;

    /**
     * Construtor da classe Rodada
     * @param meta a meta de pontos da rodada
     * @param modoPontuacao modo de pontuação da rodada (TD_PASSE ou TD_TOTAL)
     */
    public Rodada(int meta, ModoPontuacao modoPontuacao) {
        this.meta = meta;
        this.modoPontuacao = modoPontuacao;
        this.pontosAcumulados = 0;
        this.multiplicadores = new ArrayList<>();
    }

    /**
     * Método abstrato que deve ser implementado para iniciar a rodada
     */
    public abstract void iniciarRodada();

    /**
     * Método getter que retorna se as estatísticas devem ser exibidas, a depender do modo da Rodada
     * 
     * @return true se as estatísticas devem ser exibidas, false caso contrário
     */
    public boolean getExibeEstatisticas() {
        return exibeEstatisticas;
    }

    /**
     * Método que verifica se a meta foi alcançada
     * 
     * @return true se a meta foi alcançada, false caso contrário
     */
    public boolean metaAlcancada() {
        return pontosAcumulados >= meta;
    }

    /**
     * Método que verifica se o jogador venceu a rodada
     * O jogador vence se não tiver mais multiplicadores e alcancou a meta
     * 
     * @return true se o jogador venceu, false caso contrário
     */
    public boolean jogadorVenceu() {
        return multiplicadores.isEmpty() && metaAlcancada();
    }


    /**
     * Método que verifica se o jogador perdeu a rodada
     * O jogador perde se não tiver mais multiplicadores e não alcancou a meta
     * 
     * @return true se o jogador perdeu, false caso contrário
     */
    public boolean jogadorPerdeu() {
        return multiplicadores.isEmpty() && !metaAlcancada();
    }


    /**
     * Método getter que retorna a pontuação acumulada na rodada
     * 
     * @return a pontuação acumulada na rodada
     */
    public int getPontosAcumulados() {
        return pontosAcumulados;
    }

    /**
     * Método getter que retorna a meta da rodada
     * 
     * @return a meta da rodada
     */
    public int getMeta() {
        return meta;
    }

    /**
     * Método que adiciona pontos ao acumulo de pontos da rodada
     * 
     * @param pontos a quantidade de pontos a ser adicionada
     */
    public void adicionarPontos(int pontos) {
        this.pontosAcumulados += pontos;
    }

    /**
     * Método getter que retorna a lista de multiplicadores disponíveis na rodada
     * 
     * @return a lista de multiplicadores disponíveis na rodada
     */
    public List<Multiplicador> getMultiplicadores() {
        return multiplicadores;
    }

    /**
     * Método que sorteia um Quarterback da lista de quarterbacks disponíveis
     * @param quarterbacks a lista de quarterbacks disponíveis para sorteio
     * 
     * @return o Quarterback sorteado
     */
    public Quarterback sortearQuarterback(List<Quarterback> quarterbacks) {
        Random rand = new Random();
        Quarterback sorteado;

        do {
            sorteado = quarterbacks.get(rand.nextInt(quarterbacks.size()));
        } while (idsSorteados.contains(sorteado.getId()));

        return sorteado;
    }

    /**
     * Método que registra o Quarterback que foi sorteado foi escolhido para não ser sorteado novamente
     * 
     * @param quarterback o Quarterback que foi sorteado
     */
    public void registrarQuarterbackUsado(Quarterback quarterback) {
        idsSorteados.add(quarterback.getId());
    }

    /**
     * Método getter que retorna a pontuação do Quarterback de acordo com o modo de pontuação escolhido
     * @param quarterback o Quarterback que deseja obter a pontuação
     * 
     * @return a pontuação do Quarterback
     */
    public int getPontuacaoQB(Quarterback quarterback) {
        return switch (modoPontuacao) {
            case TD_PASSE -> quarterback.getTdsPasse();
            case TD_TOTAL -> quarterback.getTdsTotal();
        };
    }
}

