package Interfaces;

import Exceptions.WrongTypeItem;
import Items.ItemCura;

/**
 * Interface que define todas as iterações que o ToCruz pode ter com qualquer item presentes no edificio.
 *
 *
 * @author Artur
 * @version 1.0
 */
public interface InteracoesToCruz {

    /**
     * Método para armazenar um item de cura na mochila do ToCruz.
     *
     * @param item O item de cura que será armazenado.
     * @return Uma mensagem indicando o sucesso ou falha ao guardar o item.
     * @throws NullPointerException Se o item fornecido for nulo.
     * @throws WrongTypeItem Se o item fornecido não for do tipo kit de vida.
     */
    public String guardarKit(ItemCura item) throws NullPointerException, WrongTypeItem;

    /**
     * Método para usar um kit de cura armazenados na mochila
     *
     * @return Uma mensagem indicando o sucesso ou falha ao usar o kit.
     */
    public String usarKit();

    /**
     * Método para usar os item de cura que não estão na mochila do To Cruz, ou seja, para o To Cruz utilziar
     * o item na sala que o coletou sem guardar.
     *
     * @param item O item de cura que será usado.
     * @return Uma mensagem indicando o sucesso ou falha ao usar o item.
     * @throws NullPointerException Se o item fornecido for nulo.
     */
    public String usarItem(ItemCura item) throws NullPointerException;
}
