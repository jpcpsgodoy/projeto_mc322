package com.futquiz.auxiliares;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import com.futquiz.model.*;
import com.futquiz.exceptions.*;

/**
 * Classe responsável por ler dados de um arquivo CSV e carregar objetos
 * Quarterback.
 * 
 * @author João Pedro
 */
public class leitorDeCSV {

    /**
     * Construtor privado para evitar instâncias da classe.
     */
    private leitorDeCSV() {
    }

    /**
     * Método para carregar dados de um arquivo CSV e retornar uma lista de
     * quarterbacks
     * 
     * @param caminho Caminho do arquivo CSV a ser lido. O caminho deve ser relativo
     * ao classpath.
     * @return Lista de objetos Quarterback carregados a partir do arquivo CSV.
     */
    public static List<Quarterback> carregarDados(String caminho) throws NaoFoiPossivelCarregarArquivoException {
        List<Quarterback> quarterbacks = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(leitorDeCSV.class.getResourceAsStream(caminho)))) {
            String linha = br.readLine();
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(",");
                if (dados.length == 5) {
                    int id = Integer.parseInt(dados[0].trim());
                    String nome = dados[1].trim();
                    int tdsPasse = Integer.parseInt(dados[2]);
                    int tdsCorridos = Integer.parseInt(dados[3]);
                    int tdsTotal = Integer.parseInt(dados[4].trim());

                    Quarterback qb = new Quarterback(id, nome, tdsPasse, tdsCorridos, tdsTotal);
                    quarterbacks.add(qb);
                }
            }
        } catch (Exception e) {
            throw new NaoFoiPossivelCarregarArquivoException("Não foi possível carregar o arquivo: " + caminho);
        }

        return quarterbacks;
    }

    /**
     * Grava o resumo da rodada em um arquivo CSV.
     *
     * @param tipoRodada O tipo de rodada (Normal ou Desafio).
     * @param tipoPontuacao O tipo de pontuação (TD_PASSE ou TD_TOTAL).
     * @param meta A meta de touchdowns para alcançar.
     * @param pontos Os pontos acumulados na rodada.
     * @param detalhes Disposição dos qbs e multiplicadores.
     */
    public static void gravarResumo(String tipoRodada, String tipoPontuacao, int meta, int pontos, String detalhes) {
        File arquivo = new File("historico_jogos.csv");
        try (FileWriter fw = new FileWriter(arquivo, true);
             PrintWriter pw = new PrintWriter(fw)) {

            if (arquivo.length() == 0) {
                pw.println("\"Tipo de Rodada\",\"Tipo de Pontuacao\",\"Meta\",\"Pontos\",\"Detalhes\"");
            }

            pw.printf("\"%s\",\"%s\",%d,%d,\"%s\"%n", tipoRodada, tipoPontuacao, meta, pontos, detalhes);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}