package com.futquiz.controller;

import com.futquiz.auxiliares.leitorDeCSV;
import com.futquiz.exceptions.ModoPontuacaoInvalidoException;
import com.futquiz.exceptions.NaoFoiPossivelCarregarArquivoException;
import com.futquiz.exceptions.TipoRodadaInvalidoException;
import com.futquiz.model.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Serviço responsável por gerenciar a lógica do jogo
 *
 * @author Larissa Palhares
 * @author João Pedro
 */
public class GameService {
    private Rodada rodada;
    private List<Quarterback> quarterbacks;
    private List<Jogada> historico = new ArrayList<>();

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
     * @throws ModoPontuacaoInvalidoException se o modo de pontuação fornecido for
     *                                        inválido
     * @throws TipoRodadaInvalidoException se o tipo de rodada fornecido for inválido 
     */
    public void iniciarJogo(int meta, String modo, String exibeEstatisticas)
            throws NaoFoiPossivelCarregarArquivoException,
                   ModoPontuacaoInvalidoException,
                   TipoRodadaInvalidoException {
        quarterbacks = leitorDeCSV.carregarDados("/dados.csv");

        rodada = RodadaFactory.criarRodada(meta, modo, exibeEstatisticas);

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
        historico.add(new Jogada(qb, multiplicador, pontos));
        return pontos;
    }

    /**
     * Confere se a meta de touchdowns foi alcançada
     * @return true se a meta foi alcançada, false caso não
     */
    public boolean metaAtingida() {
        return rodada.metaAlcancada();
    }

    /**
     * Confere se as estatísticas devem ser exibidas
     * @return true se as estatísticas devem ser exibidas, false caso não
     */
    public boolean deveExibirEstatistica() {
        return rodada.getExibeEstatisticas();
    }

    /**
     * Retorna a lista de multiplicadores disponíveis
     * @return Lista de multiplicadores disponíveis
     */
    public List<Multiplicador> getMultiplicadoresDisponiveis() {
        return rodada.getMultiplicadores();
    }

    /**
     * Retorna a pontuação acumulada na rodada
     * @return Pontos acumulados na rodada
     */
    public int getPontosAcumulados() {
        return rodada.getPontosAcumulados();
    }

    /**
     * Retorna a rodada atual
     *
     * @return A rodada atual
     */
    public Rodada getRodada() {
        return rodada;
    }
}
