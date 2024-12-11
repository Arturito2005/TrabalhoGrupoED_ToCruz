package Interfaces;

import Exceptions.EmptyCollectionException;

/**
 * Esta interface define o ADT para uma fila.
 *
 * @param <T> o tipo dos elementos armazenados na fila
 * @author Artur Pinto
 * Nº mecanográfico: 8230138
 * @author Francisco Oliveira
 * Nº mecanográfico: 8230148
 * @version 1.0
 */
public interface QueueADT<T> {

    /**
     * Adiciona um elemento à parte traseira desta fila.
     *
     * @param element o elemento a ser adicionado à parte traseira desta fila
     */
    public void enqueue(T element);

    /**
     * Remove e retorna o elemento da frente desta fila.
     *
     * @return o elemento da frente desta fila
     * @throws EmptyCollectionException
     */
    public T dequeue() throws EmptyCollectionException;

    /**
     * Retorna, sem remover, o elemento da frente desta fila.
     *
     * @return o primeiro elemento nesta fila
     * @throws EmptyCollectionException
     */
    public T first() throws EmptyCollectionException;

    /**
     * Retorna true se esta fila não contiver elementos.
     *
     * @return true se esta fila estiver vazia
     */
    public boolean isEmpty();

    /**
     * Retorna o número de elementos nesta fila.
     *
     * @return a representação inteira do tamanho desta fila
     */
    public int size();

    /**
     * Retorna uma representação em string desta fila.
     *
     * @return a representação em string desta fila
     */
    @Override
    public String toString();
}
