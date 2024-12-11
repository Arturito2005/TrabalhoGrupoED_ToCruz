package Interfaces;

import Exceptions.EmptyCollectionException;

/**
 * A interface HeapADT representa a estrutura de dados Heap, que estende uma árvore binária.
 *
 * @param <T> o tipo dos elementos armazenados neste heap, que deve ser comparável para determinar a ordenação.
 * @author Artur Pinto
 * Nº mecanográfico: 8230138
 * @author Francisco Oliveira
 * Nº mecanográfico: 8230148
 * @version 1.0
 */
public interface HeapADT<T> extends BinaryTreeADT<T> {

    /**
     * Adiciona o objeto especificado a este heap.
     *
     * @param obj o elemento a ser adicionado a este heap
     */
    public void addElement(T obj);

    /**
     * Remove o elemento com o menor valor deste heap.
     *
     * @return o elemento com o menor valor deste heap
     * @throws EmptyCollectionException se o heap estiver vazio
     */
    public T removeMin() throws EmptyCollectionException;

    /**
     * Retorna uma referência para o elemento com o menor valor neste heap.
     *
     * @return uma referência para o elemento com o menor valor neste heap
     */
    public T findMin();
}
