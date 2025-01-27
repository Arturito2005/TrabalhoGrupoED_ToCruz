package Interfaces;

import Exceptions.ElementNotFoundException;
import Exceptions.EmptyCollectionException;

/**
 * Esta interface define a ADT (Tipo Abstrato de Dados) para uma árvore de pesquisa binária (BST).
 *
 * @param <T> o tipo de elementos armazenados na árvore de pesquisa binária
 * @author Artur Pinto
 * Nº mecanográfico: 8230138
 * @author Francisco Oliveira
 * Nº mecanográfico: 8230148
 * @version 1.0
 */
public interface BinarySearchTreeADT<T> extends BinaryTreeADT<T> {

    /**
     * Adiciona o elemento especificado na localização apropriada desta árvore.
     *
     * @param element o elemento a ser adicionado nesta árvore
     */
    public void addElement(T element);

    /**
     * Remove e retorna o elemento especificado desta árvore.
     *
     * @param targetElement o elemento a ser removido desta árvore
     * @return o elemento removido desta árvore
     */
    public T removeElement(T targetElement) throws EmptyCollectionException, ElementNotFoundException;

    /**
     * Remove todas as ocorrências do elemento especificado desta árvore.
     *
     * @param targetElement o elemento cujas todas as instâncias serão removidas da árvore
     */
    public void removeAllOccurences(T targetElement);

    /**
     * Remove e retorna o menor elemento desta árvore.
     *
     * @return o menor elemento desta árvore
     */
    public T removeMin();

    /**
     * Remove e retorna o maior elemento desta árvore.
     *
     * @return o maior elemento desta árvore
     */
    public T removeMax();

    /**
     * Retorna uma referência para o menor elemento desta árvore.
     *
     * @return uma referência para o menor elemento desta árvore
     */
    public T findMin() throws EmptyCollectionException;

    /**
     * Retorna uma referência para o maior elemento desta árvore.
     *
     * @return uma referência para o maior elemento desta árvore
     */
    public T findMax() throws EmptyCollectionException;
}
