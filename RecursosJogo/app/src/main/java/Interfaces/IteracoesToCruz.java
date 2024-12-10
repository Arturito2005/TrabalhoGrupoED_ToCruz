package Interfaces;

import Personagens.Inimigo;

/**
 * Interface que define as interações realizadas pelo To Cruz.
 *
 * @author Artur Pinto
 * Nº mecanográfico: 8230138
 * @author Francisco Oliveria
 * Nº mecanografico: 8230148
 *
 * @version 1.0
 */
public interface IteracoesToCruz {

    /**
     * Permite ao To Cruz coletar o Alvo da missão.
     */
    public void ToCruzGetAlvo();

    /**
     * Permite o To Cruz atacar todos os inimigos da divisão.
     */
    public void attackToCruz(StackADT<Inimigo> dead_inimigo);

    /**
     * Permite ao To Cruz utilziar o item da divisão
     */
    public void usarItemDivisao();

}
