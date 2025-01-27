package Queue;

import Exceptions.EmptyCollectionException;
import Interfaces.QueueADT;

/**
 * Implementação de uma circular array queue circular utilizando um array.
 * A classe implementa a interface QueueADT e utiliza um array para armazenar os elementos de forma circular.
 * Quando o array atinge sua capacidade máxima, ele é redimensionado.
 *
 * @param <T> Tipo genérico dos elementos armazenados na circular array queue.
 * @author Artur Pinto
 * Nº mecanográfico: 8230138
 * @author Francisco Oliveira
 * Nº mecanográfico: 8230148
 * @version 1.0
 */
public class CircularArrayQueue<T> extends Queue<T> {

    /**
     * Capacidade padrão do array
     */
    private static final int DEFAULT_CAPACITY = 30;

    /**
     * Índice do elemento da frente da circular array queue
     */
    private int front;

    /**
     * Índice do elemento da traseira da circular array queue
     */
    private int rear;

    /**
     * Array utilizado para armazenar os elementos da circular array queue
     */
    private T[] array;

    /**
     * Construtor padrão que inicializa a circular array queue com uma capacidade padrão.
     *
     * @param capacity capacidade do array
     */
    public CircularArrayQueue(int capacity) {
        super();
        this.front = 0;
        this.rear = 0;

        if (capacity == 0) {
            this.array = (T[]) (new Object[1]);
        } else {
            this.array = (T[]) (new Object[capacity]);
        }
    }

    /**
     * Construtor para inicializar a circular array queue com uma capacidade específica.
     */
    public CircularArrayQueue() {
        super();
        this.front = 0;
        this.rear = 0;
        this.array = (T[]) (new Object[DEFAULT_CAPACITY]);
    }

    /**
     * Expande o array de elementos, dobrando sua capacidade e realocando os elementos
     * na nova posição, mantendo a ordem e a circularidade.
     */
    private void expandArray() {
        T[] temp = (T[]) (new Object[(this.array.length * 2)]);

        System.arraycopy(this.array, 0, temp, 0, this.array.length);

        this.rear = this.array.length;
        this.array = temp;
    }

    /**
     * Adiciona um elemento à traseira da circular array queue (enqueue).
     * Caso a circular array queue esteja cheia, o array é expandido.
     *
     * @param element O elemento a ser adicionado na circular array queue.
     */
    @Override
    public void enqueue(T element) {
        if (this.array.length == this.count) {
            expandArray();
        }

        this.array[this.rear] = element;
        this.rear = (this.rear + 1) % this.array.length;
        this.count++;
    }

    /**
     * Remove e retorna o elemento da frente da circular array queue (dequeue).
     * Se a circular array queue estiver vazia, uma exceção é lançada.
     *
     * @return O elemento removido da frente da circular array queue.
     * @throws EmptyCollectionException Se a circular array queue estiver vazia.
     */
    @Override
    public T dequeue() throws EmptyCollectionException {
        if (this.count == 0) {
            throw new EmptyCollectionException("A ArrayQueue está vazia");
        }

        T element = this.array[this.front];

        this.array[this.front] = null;
        this.front = (this.front + 1) % array.length;
        this.count--;

        return element;
    }

    /**
     * Retorna o elemento da frente da circular array queue, sem removê-lo (first).
     * Se a circular array queue estiver vazia, uma exceção é lançada.
     *
     * @return O elemento da frente da circular array queue.
     * @throws EmptyCollectionException Se a circular array queue estiver vazia.
     */
    @Override
    public T first() throws EmptyCollectionException {
        if (this.count == 0) {
            throw new EmptyCollectionException("A ArrayQueue está vazia");
        }

        return this.array[this.front];
    }

    /**
     * Retorna uma representação em string de todos os elementos da circular array queue.
     *
     * @return A representação em string dos elementos na circular array queue.
     */
    @Override
    public String toString() {
        String temp = "";

        for (T ele : this.array) {
            if (ele != null) {
                temp += ele;
            }
        }

        return temp;
    }
}
