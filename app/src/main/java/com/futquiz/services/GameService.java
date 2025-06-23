package com.futquiz.services;

import com.futquiz.auxiliares.GerenciadorArquivos;
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
 * @author Gustavo Henrique
 */
public class GameService {
    private final List<Jogada> historico = new ArrayList<>();
    private Rodada rodada;
    private List<Quarterback> quarterbacks;
    private boolean jogoTerminado = false;
    private int ultimaMeta;
    private String ultimoModo;
    private String ultimoTipoRodada;
    private static GameService instancia;

    /**
     * Construtor para implementar o padrão Singleton
     * Garante que apenas uma instância do GameService seja criada
     * 
     * @return Instância única do GameService
     */
    private GameService() {
    }

    public static GameService getInstance() {
        if (instancia == null) {
            instancia = new GameService();
        }
        return instancia;
    }


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
     * @throws ModoPontuacaoInvalidoException         se o modo de pontuação fornecido for
     *                                                inválido
     * @throws TipoRodadaInvalidoException            se o tipo de rodada fornecido for inválido
     */
    public void iniciarJogo(int meta, String modo, String exibeEstatisticas)
            throws NaoFoiPossivelCarregarArquivoException,
            ModoPontuacaoInvalidoException,
            TipoRodadaInvalidoException {

        quarterbacks = GerenciadorArquivos.carregarDados("/dados.csv");
        this.historico.clear();
        rodada = RodadaFactory.criarRodada(meta, modo, exibeEstatisticas);

        rodada.iniciarRodada();

        ultimaMeta = meta;
        ultimoModo = modo;
        ultimoTipoRodada = exibeEstatisticas;
    }

    /**
     * Reinicia o jogo, limpando rodada e histórico
     */
    public void reiniciarJogoMesmasConfigs() throws NaoFoiPossivelCarregarArquivoException, ModoPontuacaoInvalidoException, TipoRodadaInvalidoException {
        iniciarJogo(this.ultimaMeta, this.ultimoModo, this.ultimoTipoRodada);
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
        rodada.getMultiplicadores().remove(multiplicador);

        if (jogoAcabou()) {
            salvarResumoRodada();
            jogoTerminado = true;
        }

        return pontos;
    }

    /**
     * Verifica se o jogador venceu a rodada
     *
     * @return true se o jogador venceu, false caso contrário
     */
    public boolean jogadorVenceu() {
        return rodada.jogadorVenceu();
    }


    /**
     * Verifica se o jogador perdeu a rodada
     *
     * @return true se o jogador perdeu, false caso contrário
     */
    public boolean jogadorPerdeu() {
        return rodada.jogadorPerdeu();
    }


    /**
     * Verifica se o jogo acabou
     *
     * @return true se o jogo acabou, false caso contrário
     */
    public boolean jogoAcabou() {
        return rodada.jogadorPerdeu() || rodada.jogadorVenceu();
    }



    /**
     * Confere se as estatísticas devem ser exibidas
     *
     * @return true se as estatísticas devem ser exibidas, false caso não
     */
    public boolean deveExibirEstatistica() {
        return rodada.getExibeEstatisticas();
    }

    /**
     * Retorna a lista de multiplicadores disponíveis
     *
     * @return Lista de multiplicadores disponíveis
     */
    public List<Multiplicador> getMultiplicadoresDisponiveis() {
        return rodada.getMultiplicadores();
    }


    /**
     * Retorna a rodada atual
     *
     * @return A rodada atual
     */
    public Rodada getRodada() {
        return rodada;
    }

    /**
     * Retorna uma mensagem de resultado da rodada, informando se o jogador
     * superou a meta ou se faltaram pontos para alcançá-la.
     * 
     * @return Mensagem de resultado da rodada
     */
    public String getMensagemResultado() {
        if (jogadorVenceu()) {
            return "Você superou a meta por " + (rodada.getPontosAcumulados() - rodada.getMeta()) + " pontos.";
        } else if (jogadorPerdeu()) {
            return "Faltaram " + (rodada.getMeta() - rodada.getPontosAcumulados()) + " pontos para superar a meta.";
        }
        return "";
    }

    /**
     * Retorna as metas disponíveis para o jogo
     * 
     * @return  Lista de metas disponíveis
     */
    public List<Integer> obterMetasDisponiveis() {
        return MultiplicadorFactory.getMetasDisponiveis();
    }


    /**
     * Chama o metodo que grava o resumo da rodada no arquivo CSV
     */
    public void salvarResumoRodada() {
        List<String> listaDetalhes = new ArrayList<>();
        for (Jogada jogada : historico) {
            listaDetalhes.add(jogada.getMultiplicador().getValor() + "x - "
                    + jogada.getQuarterback().getNome()
                    + " (" + jogada.getPontosGerados() + ")");
        }


        int pontos = rodada.getPontosAcumulados();


        GerenciadorArquivos.gravarResumo(
                this.ultimoTipoRodada,
                this.ultimoModo,
                this.ultimaMeta,
                pontos,
                listaDetalhes
        );
    }

}


