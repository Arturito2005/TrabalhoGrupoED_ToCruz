package Queue;

import Exceptions.EmptyCollectionException;
import Interfaces.QueueADT;
import Nodes.LinearNode;

/**
 * Implementação de uma Queue (Queue) utilizando uma lista encadeada.
 * A classe implementa a interface QueueADT e usa LinearNode para representar os elementos.
 *
 * @param <T> Tipo genérico dos elementos armazenados na Queue.
 * @author Artur Pinto
 * Nº mecanográfico: 8230138
 * @author Francisco Oliveira
 * Nº mecanográfico: 8230148
 * @version 1.0
 */
public class LinkedQueue<T> implements QueueADT<T> {

    /**
     * Referência para o primeiro elemento (frente) da Queue
     */
    private LinearNode<T> front;

    /**
     * Referência para o último elemento (traseira) da Queue
     */
    private LinearNode<T> rear;

    /**
     * Contador que mantém o número de elementos na Queue
     */
    private int count;

    /**
     * Construtor padrão que inicializa a Queue vazia.
     */
    public LinkedQueue() {
        this.front = null;
        this.rear = null;
        this.count = 0;
    }

    /**
     * Adiciona um elemento ao final da Queue (enqueue).
     *
     * @param element O elemento a ser adicionado na Queue.
     */
    @Override
    public void enqueue(T element) {
        LinearNode<T> newNode = new LinearNode(element);

        if (this.front == null) {
            this.front = newNode;
        } else {
            this.rear.setNext(newNode);
        }

        this.rear = newNode;
        this.count++;
    }

    /**
     * Remove o elemento da frente da Queue (dequeue).
     * Se a Queue estiver vazia, uma exceção é lançada.
     *
     * @return O elemento removido da frente da Queue.
     * @throws EmptyCollectionException Se a Queue estiver vazia.
     */
    @Override
    public T dequeue() throws EmptyCollectionException {
        if (this.count == 0) {
            throw new EmptyCollectionException("A queue está vazia");
        }

        T elementRemoved = this.front.getElement();
        this.front = this.front.getNext();
        this.count--;

        return elementRemoved;
    }

    /**
     * Retorna o elemento da frente da Queue, sem removê-lo (first).
     * Se a Queue estiver vazia, uma exceção é lançada.
     *
     * @return O elemento da frente da Queue.
     * @throws EmptyCollectionException Se a Queue estiver vazia.
     */
    @Override
    public T first() throws EmptyCollectionException {
        if (this.count == 0) {
            throw new EmptyCollectionException("A queue está vazia");
        }

        return this.front.getElement();
    }

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
    public String toString() {
        String temp = "";
        LinearNode<T> current = this.front;

        while (current != null) {
            temp = temp + current.getElement().toString();
            current = current.getNext();
        }

        return temp;
    }
}
