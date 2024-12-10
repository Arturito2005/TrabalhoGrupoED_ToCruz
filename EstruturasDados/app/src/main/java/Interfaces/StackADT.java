package Interfaces;

import Exceptions.EmptyCollectionException;

/**
 * Esta interface define o ADT (Tipo Abstrato de Dados) para uma pilha genérica.
 *
 * @param <T> o tipo dos elementos armazenados nesta pilha
 * @author Artur Pinto
 * Nº mecanográfico: 8230138
 * @author Francisco Oliveira
 * Nº mecanográfico: 8230148
 * @version 1.0
 */
public interface StackADT<T> {

    /**
     * Adiciona um elemento ao topo desta pilha.
     *
     * @param element o elemento a ser empilhado
     */
    public void push(T element);

    /**
     * Remove e retorna o elemento do topo desta pilha.
     *
     * @return T o elemento removido do topo da pilha
     * @throws EmptyCollectionException
     */
    public T pop() throws EmptyCollectionException;

    /**
     * Retorna, sem remover, o elemento do topo desta pilha.
     *
     * @return T o elemento no topo da pilha
     * @throws EmptyCollectionException
     */
    public T peek() throws EmptyCollectionException;

    /**
     * Retorna true se esta pilha não contiver elementos.
     *
     * @return boolean se a pilha está vazia ou não
     */
    public boolean isEmpty();

    /**
     * Retorna o número de elementos nesta pilha.
     *
     * @return int o número de elementos nesta pilha
     */
    public int size();

    /**
     * Retorna uma representação em string desta pilha.
     *
     * @return String a representação em string desta pilha
     */
    @Override
    public String toString();
}
