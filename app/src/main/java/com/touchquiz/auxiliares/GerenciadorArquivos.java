package com.touchquiz.auxiliares;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.touchquiz.model.*;
import com.touchquiz.exceptions.*;

/**
 * Classe responsável por ler dados de um arquivo CSV e carregar objetos
 * Quarterback.
 * 
 * @author João Pedro
 */
public class GerenciadorArquivos {

    /**
     * Construtor privado para evitar instâncias da classe.
     */
    private GerenciadorArquivos() {
    }

    /**
     * Método para carregar dados de um arquivo CSV e retornar uma lista de
     * quarterbacks
     * 
     * @param caminho Caminho do arquivo CSV a ser lido. O caminho deve ser relativo
     *                ao classpath.
     * @return Lista de objetos Quarterback carregados a partir do arquivo CSV.
     */
    public static List<Quarterback> carregarDados(String caminho) throws NaoFoiPossivelCarregarArquivoException {
        List<Quarterback> quarterbacks = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(GerenciadorArquivos.class.getResourceAsStream(caminho)))) {
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
    public static void gravarResumo(String tipoRodada, String tipoPontuacao, int meta, int pontos, List<String> detalhes) {
        File arquivo = new File("historico_jogos.csv");
        try (FileWriter fw = new FileWriter(arquivo, true);
             PrintWriter pw = new PrintWriter(fw)) {


            if (arquivo.length() == 0) {
                pw.print("\"Tipo de Rodada\",\"Tipo de Pontuacao\",\"Meta\",\"Pontos\"");
                for (int i = 0; i < detalhes.size(); i++) {
                    pw.print(",\"Detalhe" + (i + 1) + "\"");
                }
                pw.println();
            }


            pw.printf("\"%s\",\"%s\",%d,%d", tipoRodada, tipoPontuacao, meta, pontos);
            for (String detalhe : detalhes) {
                pw.printf(",\"%s\"", detalhe);
            }
            pw.println();


        } catch (IOException e) {
            throw new FalhaPersistenciaArquivoException("Não foi possível gravar o resumo do jogo no arquivo.");
        }
    }

    /**
     * Carrega o histórico de jogos a partir de um arquivo CSV.
     *
     * @return Uma lista de arrays de strings contendo os dados do histórico de jogos.
     */
    public static List<String[]> carregarHistoricoJogos() {
        List<String[]> historico = new ArrayList<>();
        File arquivo = new File("historico_jogos.csv");

        if (!arquivo.exists()) return historico;
        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String linha = br.readLine(); //ignora cabeçalho
            while ((linha = br.readLine()) != null) {
                String[] campos = linha.replace("\"", "").split(",");
                if (campos.length >= 5) {
                    String tipoRodada = campos[0];
                    String tipoPontuacao = campos[1];
                    String meta = campos[2];
                    String pontos = campos[3];
                    List<String> listaDetalhes = new ArrayList<>();
                    listaDetalhes.addAll(Arrays.asList(campos).subList(4, campos.length));
                    String detalhes = String.join("\n", listaDetalhes);
                    String[] arrFinal = { tipoRodada, tipoPontuacao, meta, pontos, detalhes };
                    historico.add(arrFinal);
                }
            }
        } catch (IOException e) {
            throw new FalhaPersistenciaArquivoException("Não foi possível carregar o histórico de jogos.");
        }

        return historico;
    }

}
