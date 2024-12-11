package Interfaces;

import Exceptions.ElementNotFoundException;
import Exceptions.EmptyCollectionException;

import java.util.Iterator;

/**
 * Esta interface define o ADT para uma lista genérica.
 * <p>
 * Esta interface estende a interface {@code Iterable<T>}, permitindo que a lista
 * seja iterável.
 *
 * @param <T> o tipo dos elementos armazenados nesta lista
 * @author Artur Pinto
 * Nº mecanográfico: 8230138
 * @author Francisco Oliveira
 * Nº mecanográfico: 8230148
 * @version 1.0
 */
public interface ListADT<T> extends Iterable<T> {

    /**
     * Remove e retorna o primeiro elemento desta lista.
     *
     * @return o primeiro elemento desta lista
     * @throws EmptyCollectionException se a lista estiver vazia
     */
    public T removeFirst() throws EmptyCollectionException;

    /**
     * Remove e retorna o último elemento desta lista.
     *
     * @return o último elemento desta lista
     * @throws EmptyCollectionException se a lista estiver vazia
     */
    public T removeLast() throws EmptyCollectionException;

    /**
     * Remove e retorna o elemento especificado desta lista.
     *
     * @param element o elemento a ser removido da lista
     * @return o elemento removido
     * @throws EmptyCollectionException se a lista estiver vazia
     * @throws ElementNotFoundException se o elemento não for encontrado
     */
    public T remove(T element) throws EmptyCollectionException, ElementNotFoundException;

    /**
     * Retorna uma referência para o primeiro elemento desta lista.
     *
     * @return uma referência para o primeiro elemento desta lista
     */
    public T first();

    /**
     * Retorna uma referência para o último elemento desta lista.
     *
     * @return uma referência para o último elemento desta lista
     */
    public T last();

    /**
     * Retorna true se esta lista contiver o elemento alvo especificado.
     *
     * @param target o elemento que está sendo buscado na lista
     * @return true se a lista contiver este elemento
     */
    public boolean contains(T target);

    /**
     * Retorna true se esta lista não contiver elementos.
     *
     * @return true se esta lista não contiver elementos
     */
    public boolean isEmpty();

    /**
     * Retorna o número de elementos nesta lista.
     *
     * @return a representação inteira do número de elementos nesta lista
     */
    public int size();

    /**
     * Retorna um iterador para os elementos desta lista.
     *
     * @return um iterador sobre os elementos desta lista
     */
    @Override
    public Iterator<T> iterator();

    /**
     * Retorna uma representação em string desta lista.
     *
     * @return uma representação em string desta lista
     */
    @Override
    public String toString();
}
