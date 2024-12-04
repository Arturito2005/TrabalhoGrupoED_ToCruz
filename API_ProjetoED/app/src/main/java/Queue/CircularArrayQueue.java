package Queue;

import Interfaces.QueueADT;
import Exceptions.EmptyCollectionException;

public class CircularArrayQueue<T> implements QueueADT<T> {

    private static final int DEFAULT_CAPACITY = 30;

    private int count;

    private int front;

    private int rear;

    private T[] array;

    public CircularArrayQueue(int capacity) {
        this.count = 0;
        this.front = 0;
        this.rear = 0;

        if (capacity == 0) {
            this.array = (T[]) (new Object[1]);
        } else {
            this.array = (T[]) (new Object[capacity]);
        }
    }

    public CircularArrayQueue() {
        this.count = 0;
        this.front = 0;
        this.rear = 0;
        this.array = (T[]) (new Object[DEFAULT_CAPACITY]);
    }

    private void expandArray() {
        T[] temp = (T[]) (new Object[(this.array.length * 2)]);

        System.arraycopy(this.array, 0, temp, 0, this.array.length);

        this.rear = this.array.length;
        this.array = temp;
    }

    @Override
    public void enqueue(T element) {
        if (this.array.length == this.count) {
            expandArray();
        }

        this.array[this.rear] = element;
        this.rear = (this.rear + 1) % this.array.length;
        this.count++;
    }

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

    @Override
    public T first() throws EmptyCollectionException {
        if (this.count == 0) {
            throw new EmptyCollectionException("A ArrayQueue está vazia");
        }

        return this.array[this.front];
    }

    @Override
    public boolean isEmpty() {
        boolean empty = false;

        if (this.count == 0) {
            empty = true;
        }

        return empty;
    }

    @Override
    public int size() {
        return this.count;
    }

    @Override
    public String toString() {
        String temp = "";

        for (T ele : this.array) {
            if (ele != null) {
                temp = temp + ele;
            }
        }

        return temp;
    }
}
