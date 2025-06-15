package com.futquiz.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/*
 * Classe abstrata que representa a Rodada do jogo
 * @author Larissa Palhares
 * @author João Pedro
 */
public abstract class Rodada {
    protected Set<Integer> idsSorteados = new HashSet<>();
    protected int meta;
    protected List<Multiplicador> multiplicadores;
    protected int pontosAcumulados;
    protected ModoPontuacao modoPontuacao;

    public Rodada(int meta, ModoPontuacao modoPontuacao) {
        this.meta = meta;
        this.modoPontuacao = modoPontuacao;
        this.pontosAcumulados = 0;
        this.multiplicadores = new ArrayList<>();
    }

    public abstract void iniciarRodada();

    public abstract boolean exibeEstatistica();

    public boolean metaAlcancada() {
        return pontosAcumulados >= meta;
    }

    public int getPontosAcumulados() {
        return pontosAcumulados;
    }

    public int getMeta() {
        return meta;
    }

    public void adicionarPontos(int pontos) {
        this.pontosAcumulados += pontos;
    }

    public List<Multiplicador> getMultiplicadores() {
        return multiplicadores;
    }

    public Quarterback sortearQuarterback(List<Quarterback> quarterbacks) {
        Random rand = new Random();
        Quarterback sorteado;

        do {
            sorteado = quarterbacks.get(rand.nextInt(quarterbacks.size()));
        } while (idsSorteados.contains(sorteado.getId()));

        return sorteado;
    }

    public void registrarQuarterbackUsado(Quarterback quarterback) {
        idsSorteados.add(quarterback.getId());
    }

    public int getPontuacaoQB(Quarterback quarterback) {
        return switch (modoPontuacao) {
            case TD_PASSE -> quarterback.getTdsPasse();
            case TD_TOTAL -> quarterback.getTdsTotal();
        };
    }
}
