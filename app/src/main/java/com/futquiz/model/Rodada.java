package com.futquiz.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/*
 * Classe abstrata que representa a Rodada do jogo
 * @author Larissa Palhares
 */
public abstract class Rodada {
    protected Set<Integer> idsSorteados = new HashSet<>();
    protected int meta;
    protected List<Integer> multiplicadores;
    protected int pontosAcumulados;

    public Rodada(int meta) {
        this.meta = meta;
        this.pontosAcumulados = 0;
        this.multiplicadores = new ArrayList<>();
    }
    
    public abstract void iniciarRodada();

    public abstract void exibirMultiplicacao();

    public boolean metaAlcancada() {
        return pontosAcumulados >= meta;
    }

    public int getPontosAcumulados() {
        return pontosAcumulados;
    }

    public List<Integer> getMultiplicadores() {
        return multiplicadores;
    }

    
    public Quarterback sortearQuarterback(List<Quarterback> quarterbacks) {
        if (idsSorteados.size() >= 10) {
            return null;
        }

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
}
