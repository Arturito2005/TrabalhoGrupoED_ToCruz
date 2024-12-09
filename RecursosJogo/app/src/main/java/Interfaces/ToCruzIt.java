package Interfaces;

import Exceptions.AllLifeException;
import Exceptions.EmptyCollectionException;
import Exceptions.UsedColectedItemException;
import Exceptions.WrongTypeItemException;
import Items.ItemCura;

/**
 * Interface que define todas as iterações que o ToCruz pode ter com qualquer item presentes no edificio.
 *
 * @author Artur
 * @version 1.0
 */
public interface ToCruzIt {

    public boolean mochilaIsFull();

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
