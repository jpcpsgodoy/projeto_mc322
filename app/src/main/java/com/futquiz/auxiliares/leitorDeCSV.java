package com.futquiz.auxiliares;

import java.io.BufferedReader;
import java.io.InputStreamReader;
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
     *                ao classpath.
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
}
