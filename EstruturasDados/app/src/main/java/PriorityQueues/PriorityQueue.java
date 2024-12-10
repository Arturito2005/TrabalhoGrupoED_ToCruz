package PriorityQueues;

import ArrayHeaps.ArrayHeap;
import Exceptions.EmptyCollectionException;

/**
 * A classe PriorityQueue demonstra uma fila de prioridade utilizando uma Heap.
 *
 * @param <T> o tipo do elemento armazenado na fila de prioridade
 *
 * @author Artur Pinto
 * Nº mecanográfico: 8230138
 * @author Francisco Oliveira
 * Nº mecanográfico: 8230148
 * @version 1.0
 */
public class PriorityQueue<T> extends ArrayHeap<PriorityQueueNode<T>> {

    /**
     * Cria uma fila de prioridade vazia.
     */
    public PriorityQueue() {
        super();
    }

    /**
     * Adiciona o elemento dado à fila de prioridade.
     *
     * @param object o elemento a ser adicionado à fila de prioridade
     * @param priority a prioridade inteira do elemento a ser adicionado
     */
    public void addElement(T object, int priority) {
        PriorityQueueNode<T> node = new PriorityQueueNode<>(object, priority);
        super.addElement(node);
    }

    /**
     * Remove o próximo elemento de maior prioridade da fila de prioridade e
     * retorna uma referência a ele.
     *
     * @return uma referência ao próximo elemento de maior prioridade na fila
     * @throws EmptyCollectionException caso a fila esteja vazia
     */
    public T removeNext() throws EmptyCollectionException {
        if (this.count == 0) {
            throw new EmptyCollectionException("Não é possível remover, porque a heap está vazia");
        }

        PriorityQueueNode<T> temp = (PriorityQueueNode<T>) super.removeMin();
        return temp.getElement();
    }
}
