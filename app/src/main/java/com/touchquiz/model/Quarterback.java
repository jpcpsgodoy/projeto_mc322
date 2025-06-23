package com.touchquiz.model;

/**
 * Classe que representa um Quarterback no jogo.
 * O jogador possui um ID, nome, total de touchdowns passados, corridos e total.
 * @author Larissa Palhares
 */
public class Quarterback {
    private int id;
    private String nome;
    private int tdsPasse;
    private int tdsCorridos;
    private int tdsTotal;

    /**
     * Construtor da classe Quarterback.
     * 
     * @param id ID único do quarterback.
     * 
     * @param nome Nome do quarterback.
     * 
     * @param tdsPasse Total de touchdowns passados.
     * 
     * @param tdsCorridos Total de touchdowns corridos.
     * 
     * @param tdsTotal Total de touchdowns
     */
    public Quarterback(int id, String nome, int tdsPasse, int tdsCorridos, int tdsTotal) {
        this.id = id;
        this.nome = nome;
        this.tdsPasse = tdsPasse;
        this.tdsCorridos = tdsCorridos;
        this.tdsTotal = tdsTotal;
    }

    /*
     * Método getter para o ID do quarterback.
     * 
     * @return ID do quarterback.
     */
    public int getId() {
        return id;
    }

    /*
     * Método getter para o nome do quarterback.
     * 
     * @return Nome do quarterback.
     */
    public String getNome() {
        return nome;
    }

    /*
     * Método getter para o total de touchdowns passados do quarterback.
     * 
     * @return Total de touchdowns passados do quarterback.
     */
    public int getTdsPasse() {
        return tdsPasse;
    }

    /*
     * Método getter para o total de touchdowns corridos do quarterback.
     * 
     * @return Total de touchdowns corridos do quarterback.
     */
    public int getTdsCorridos() {
        return tdsCorridos;
    }

    /*
     * Método getter para o total de touchdowns do quarterback.
     * 
     * @return Total de touchdowns do quarterback.
     */
    public int getTdsTotal() {
        return tdsTotal;
    }

    /*
     * Método que retorna as estatísticas do quarterback em formato de String.
     * @return String representando o quarterback.
     */
    @Override
    public String toString() {
        return "Nome: " + nome +
                "\nTouchdowns passados: " + tdsPasse +
                "\nTouchdowns corridos: " + tdsCorridos +
                "\nTouchdowns totais: " + tdsTotal;
    }
}
