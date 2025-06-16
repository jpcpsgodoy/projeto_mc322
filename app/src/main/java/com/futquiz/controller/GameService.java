package com.futquiz.controller;

import java.util.List;

import com.futquiz.auxiliares.*;
import com.futquiz.exceptions.*;
import com.futquiz.model.*;

/**
 * Serviço responsável por gerenciar a lógica do jogo
 */
public class GameService {
    private Rodada rodada;
    private List<Quarterback> quarterbacks;

    /**
     * Inicia o jogo com os parâmetros fornecidos, carregando os dados dos
     * quarterbacks
     * a partir de um arquivo CSV.
     * 
     * @param meta              Meta de touchdowns a ser alcançada no jogo
     * @param modo              Modo de pontuação a ser utilizado no jogo (TD_PASSE
     *                          ou TD_TOTAL)
     * @param exibeEstatisticas Indica se as estatísticas devem ser exibidas durante
     *                          o jogo
     * @throws NaoFoiPossivelCarregarArquivoException se ocorrer um erro ao carregar
     *                                                o arquivo CSV
     */
    public void iniciarJogo(int meta, ModoPontuacao modo, boolean exibeEstatisticas)
            throws NaoFoiPossivelCarregarArquivoException {
        quarterbacks = leitorDeCSV.carregarDados("/dados.csv");

        rodada = exibeEstatisticas ? new RodadaNormal(meta, modo) : new RodadaDesafio(meta, modo);

        rodada.iniciarRodada();
    }

    /**
     * Sorteia um quarterback da lista de quarterbacks disponíveis e o registra
     * como usado na rodada atual.
     * 
     * @return O quarterback sorteado
     */
    public Quarterback sortearQuarterback() {
        Quarterback qb = rodada.sortearQuarterback(quarterbacks);
        rodada.registrarQuarterbackUsado(qb);
        return qb;
    }

    /**
     * Aplica um multiplicador à pontuação do quarterback
     * e atualiza a pontuação acumulada da rodada.
     * 
     * @param qb            Quarterback cuja pontuação será multiplicada
     * @param multiplicador Multiplicador a ser aplicado à pontuação do quarterback
     * @return A pontuação resultante após a aplicação do multiplicador
     */
    public int aplicarMultiplicador(Quarterback qb, Multiplicador multiplicador) {
        int pontos = multiplicador.aplicar(rodada.getPontuacaoQB(qb));
        rodada.adicionarPontos(pontos);
        rodada.getMultiplicadores().remove(multiplicador);
        return pontos;
    }
}
