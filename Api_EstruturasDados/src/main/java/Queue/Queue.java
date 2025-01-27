package Queue;

import Exceptions.EmptyCollectionException;
import Interfaces.QueueADT;

public abstract class Queue<T> implements QueueADT<T> {

    /**
     * Contador que mantém o número de elementos na Queue
     */
    protected int count;

    /**
     * Construtor padrão que inicializa o contador da Queue.
     */
    public Queue() {
        this.count = 0;
    }

    /**
     * Adiciona um elemento ao final da Queue (enqueue).
     *
     * @param element O elemento a ser adicionado na Queue.
     */
    @Override
    public abstract void enqueue(T element);

    /**
     * Remove o elemento da frente da Queue (dequeue).
     * Se a Queue estiver vazia, uma exceção é lançada.
     *
     * @return O elemento removido da frente da Queue.
     * @throws EmptyCollectionException Se a Queue estiver vazia.
     */
    @Override
    public abstract T dequeue() throws EmptyCollectionException;

    /**
     * Retorna o elemento da frente da Queue, sem removê-lo (first).
     * Se a Queue estiver vazia, uma exceção é lançada.
     *
     * @return O elemento da frente da Queue.
     * @throws EmptyCollectionException Se a Queue estiver vazia.
     */
    @Override
    public abstract T first() throws EmptyCollectionException;

    /**
     * Verifica se a Queue está vazia.
     *
     * @return true se a Queue estiver vazia, caso contrário false.
     */
    @Override
    public boolean isEmpty() {
        boolean empty = false;

        if (this.count == 0) {
            empty = true;
        }

        return empty;
    }

    /**
     * Retorna o número de elementos na Queue.
     *
     * @return O número de elementos na Queue.
     */
    @Override
    public int size() {
        return this.count;
    }

    /**
     * Retorna uma representação em string de todos os elementos da Queue.
     *
     * @return A representação em string dos elementos na Queue.
     */
    @Override
    public abstract String toString();
}
