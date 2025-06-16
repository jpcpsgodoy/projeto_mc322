package com.futquiz.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Fábrica de multiplicadores que cria instâncias de Multiplicador
 * 
 * @author João Pedro
 */
public class MultiplicadorFactory {

    /**
     * Cria uma lista de multiplicadores com base na meta fornecida
     * 
     * @param meta a meta para a qual os multiplicadores serão criados (2000, 3500
     *             ou 5000)
     * @return uma lista de multiplicadores correspondentes à meta
     */
public static List<Multiplicador> criarMultiplicadores(int meta) {
    List<Multiplicador> multiplicadores = new ArrayList<>();

    if (meta <= 2000) {
        for (int i = 0; i < 3; i++) multiplicadores.add(new Multiplicador(1));
        for (int i = 0; i < 4; i++) multiplicadores.add(new Multiplicador(2));
        multiplicadores.add(new Multiplicador(3));
    } else if (meta <= 3500) {
        for (int i = 0; i < 2; i++) multiplicadores.add(new Multiplicador(1));
        for (int i = 0; i < 3; i++) multiplicadores.add(new Multiplicador(2));
        for (int i = 0; i < 3; i++) multiplicadores.add(new Multiplicador(3));
    } else if (meta <= 5000) {
        for (int i = 0; i < 2; i++) multiplicadores.add(new Multiplicador(1));
        for (int i = 0; i < 2; i++) multiplicadores.add(new Multiplicador(2));
        for (int i = 0; i < 2; i++) multiplicadores.add(new Multiplicador(3));
        for (int i = 0; i < 2; i++) multiplicadores.add(new Multiplicador(4));
    }

    return multiplicadores;
    }
}
