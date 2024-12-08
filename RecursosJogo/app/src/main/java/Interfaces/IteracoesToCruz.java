package Interfaces;

import Personagens.Inimigo;
import Stacks.LinkedStack;

import javax.naming.ldap.UnsolicitedNotification;

/**
 * Interface que define as interações realizadas pelo To Cruz.
 *
 * @author Artur
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
