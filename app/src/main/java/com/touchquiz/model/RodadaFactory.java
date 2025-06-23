package com.touchquiz.model;

import com.touchquiz.exceptions.*;
/**
 * Fábrica de rodadas que cria instâncias de Rodada
 * @author Larissa Palhares
 */
public class RodadaFactory {

    /**
     * Cria uma nova instância de Rodada com base nos parâmetros fornecidos
     * @param meta              Meta de touchdowns a ser alcançada na rodada
     * @param modoSelecionado  String que representa o modo de pontuação selecionado
     * @param tipoSelecionado String que representa o tipo de rodada selecionado
     * @return Instância de Rodada criada com base nos parâmetros fornecidos
     * @throws ModoPontuacaoInvalidoException se o modo de pontuação selecionado for inválido
     * @throws TipoRodadaInvalidoException se o tipo de rodada selecionado for inválido
     */
    public static Rodada criarRodada(int meta, String modoSelecionado, String tipoSelecionado)
                                    throws ModoPontuacaoInvalidoException, TipoRodadaInvalidoException {
        ModoPontuacao modo = validarModoPontuacao(modoSelecionado);
        boolean exibeEstatisticas = validarTipoRodada(tipoSelecionado);

        return exibeEstatisticas ? new RodadaNormal(meta, modo)
                                 : new RodadaDesafio(meta, modo);
    }

    /**
     * Valida o modo de pontuação selecionado e retorna o modo correspondente
     * @param modoSelecionado String que representa o modo de pontuação selecionado
     * @return ModoPontuacao correspondente ao modo de pontuação selecionado
     * @throws ModoPontuacaoInvalidoException se o modo de pontuação selecionado for inválido
     */
    private static ModoPontuacao validarModoPontuacao(String modoSelecionado) 
                                                     throws ModoPontuacaoInvalidoException {
        if (modoSelecionado.equals("Touchdowns passados")) {
            return ModoPontuacao.TD_PASSE;
        } else if (modoSelecionado.equals("Touchdowns totais")) {
            return ModoPontuacao.TD_TOTAL;
        } else {
            throw new ModoPontuacaoInvalidoException("Escolha um modo de pontuação entre touchdowns passados ou touchdowns totais.");
        }
    }

    /**
     * Valida o tipo de rodada selecionado e retorna o boolean correspondente
     * @param tipoRodada String que representa o tipo de rodada selecionado
     * @return boolean correspondente ao tipo de rodada selecionado
     * @throws TipoRodadaInvalidoException se o tipo de rodada selecionado for inválido
     */
    private static boolean validarTipoRodada(String tipoRodada) 
                                            throws TipoRodadaInvalidoException {
        if (tipoRodada.equals("Normal")) {
            return true;
        } else if (tipoRodada.equals("Desafio")) {
            return false;
        } else {
            throw new TipoRodadaInvalidoException("Escolha um tipo de rodada válido: Normal ou Desafio.");
        }
    }
}