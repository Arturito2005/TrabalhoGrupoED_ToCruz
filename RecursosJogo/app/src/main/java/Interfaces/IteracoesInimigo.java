package Interfaces;

import Personagens.Inimigo;

/**
 * Interface que define as interações realizadas por um inimigo em cada divisão do jogo.
 *
 * @author Francisco
 * @version 1.0
 */
public interface IteracoesInimigo {
    /**
     * Permite o inimigo recebido como parametro atacar.
     *
     * @param inimigo o objeto {@link Inimigo} que será o inimigo a atacar o To Crruz.
     */
    public void attackInimigo(Inimigo inimigo);
}