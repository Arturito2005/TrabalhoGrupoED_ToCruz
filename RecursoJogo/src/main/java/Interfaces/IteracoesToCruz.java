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
     * Permite que o personagem ToCruz ataque todos os inimigos presentes na divisão.
     * Os inimigos mortos durante o ataque são adicionados à lista de inimigos mortos.
     *
     * @param dead_inimigo A lista de inimigos mortos durante o ataque, que será atualizada
     *                     com os inimigos que foram derrotados por ToCruz.
     */
    public void attackToCruz(UnorderedListADT<Inimigo> dead_inimigo);

    /**
     * Permite ao To Cruz utilziar o item da divisão
     */
    public void usarItemDivisao();

}
