package Interfaces;

import Missoes.Missao;

/**
 * Interface que define as operações relacionadas para gerir todas as missões
 * importadas pelo o ToCruz.
 *
 * @author Artur Pinto
 * Nº mecanográfico: 8230138
 *
 * @author Francisco Oliveria
 * Nº mecanografico: 8230148
 *
 * @version 1.0
 */
public interface MissoesInt {

    /**
     * Adiciona uma missão ao sistema.
     *
     * @param missao A missão a ser adicionada.
     * @throws NullPointerException Se a missão fornecida for nula.
     */
    public void addMissao(Missao missao) throws NullPointerException;
}