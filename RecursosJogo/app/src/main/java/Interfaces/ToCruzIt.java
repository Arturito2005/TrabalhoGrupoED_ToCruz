package Interfaces;

import Exceptions.AllLifeException;
import Exceptions.EmptyCollectionException;
import Exceptions.UsedColectedItemException;
import Exceptions.WrongTypeItemException;
import Items.ItemCura;

/**
 * Interface que define todas as iterações que o ToCruz pode ter com qualquer item presentes no edificio.
 *
 * @author Artur Pinto
 * Nº mecanográfico: 8230138
 *
 * @author Francisco Oliveria
 * Nº mecanografico: 8230148
 *
 * @version 1.0
 */
public interface ToCruzIt {

    /**
     * Verifica se a mochila do To Cruz está cheia.
     *
     * @return {@code true} se a mochila estiver cheia; {@code false} caso contrário.
     */
    public boolean mochilaIsFull();

    /**
     * Verifica se há um kit de cura na mochila do To Cruz.
     *
     * @return {@code true} se houver um kit de cura na mochila; {@code false} caso contrário.
     */
    public boolean mochilaTemKit();
    /**
     * Método para armazenar um item de cura na mochila do ToCruz.
     *
     * @param item O item de cura que será armazenado.
     * @return Uma mensagem indicando o sucesso ou falha ao guardar o item.
     * @throws NullPointerException Se o item fornecido for nulo.
     * @throws WrongTypeItemException        Se o item fornecido não for do tipo kit de vida.
     */
    public ItemCura guardarKit(ItemCura item) throws NullPointerException, WrongTypeItemException, AllLifeException, UsedColectedItemException;

    /**
     * Método para usar um kit de cura armazenados na mochila, o metodo retorna o ItemUsado
     *
     * @return O kit usado
     */
    public ItemCura usarKit() throws EmptyCollectionException;

    /**
     * Método para usar os item de cura que não estão na mochila do To Cruz, ou seja, para o To Cruz utilziar
     * o item na sala que o coletou sem guardar.
     *
     * @param item O item de cura que será usado.
     * @return Uma mensagem indicando o sucesso ou falha ao usar o item.
     * @throws NullPointerException Se o item fornecido for nulo.
     */
    public ItemCura usarItem(ItemCura item) throws NullPointerException, UsedColectedItemException;
}
