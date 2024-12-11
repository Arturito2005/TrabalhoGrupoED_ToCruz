package Interfaces;

import Exceptions.EmptyCollectionException;

/**
 * Esta interface define o ADT (Tipo Abstrato de Dados) para uma stack genérica.
 *
 * @param <T> o tipo dos elementos armazenados nesta stack
 * @author Artur Pinto
 * Nº mecanográfico: 8230138
 * @author Francisco Oliveira
 * Nº mecanográfico: 8230148
 * @version 1.0
 */
public interface StackADT<T> {

    /**
     * Adiciona um elemento ao topo desta stack.
     *
     * @param element o elemento a ser emstackdo
     */
    public void push(T element);

    /**
     * Remove e retorna o elemento do topo desta stack.
     *
     * @return T o elemento removido do topo da stack
     * @throws EmptyCollectionException quando a stack estiver vazia
     */
    public T pop() throws EmptyCollectionException;

    /**
     * Retorna, sem remover, o elemento do topo desta stack.
     *
     * @return T o elemento no topo da stack
     * @throws EmptyCollectionException quando a stack estiver vazia
     */
    public T peek() throws EmptyCollectionException;

    /**
     * Retorna true se esta stack não contiver elementos.
     *
     * @return boolean se a stack está vazia ou não
     */
    public boolean isEmpty();

    /**
     * Retorna o número de elementos nesta stack.
     *
     * @return int o número de elementos nesta stack
     */
    public int size();

    /**
     * Retorna uma representação em string desta stack.
     *
     * @return String a representação em string desta stack
     */
    @Override
    public String toString();
}
