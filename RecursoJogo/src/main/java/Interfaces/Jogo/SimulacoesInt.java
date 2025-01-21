package Interfaces.Jogo;

import Missoes.Simulacoes;

/**
 * Interface que contém 3 modos de jogo da missão.
 *
 * @author Artur Pinto
 * Nº mecanográfico: 8230138
 * @author Francisco Oliveira
 * Nº mecanografico: 8230148
 * @version 1.0
 */
public interface SimulacoesInt {

    /**
     * Ativa o modo manual da missão.
     *
     * @return a simulação feita pelo jogador
     */
    public Simulacoes modojogoManual();

    /**
     * Ativa o modo automático da missão.
     *
     *
     */
    public void modojogoAutomatico();

    /**
     * Ativa o modo "jogo automático" da missão.
     *
     * @return a simulação feita pelo jogador
     */
    public Simulacoes jogoAutomatico();
}
