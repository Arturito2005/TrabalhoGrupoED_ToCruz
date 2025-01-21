package Interfaces.Jogo;

import Personagens.Inimigo;

/**
 * Interface que define as interações realizadas por um inimigo em cada divisão do jogo.
 *
 * @author Artur Pinto
 * Nº mecanográfico: 8230138
 * @author Francisco Oliveira
 * Nº mecanografico: 8230148
 *
 * @version 1.0
 */
public interface IteracoesInimigo {
    /**
     * Permite ao inimigo recebido como paramêtro de atacar.
     *
     * @param inimigo o objeto {@link Inimigo} que será o inimigo a atacar o To Crruz.
     */
    public void attackInimigo(Inimigo inimigo);
}