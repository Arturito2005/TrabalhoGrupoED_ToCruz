package Queue;

import Interfaces.QueueADT;
import Exceptions.EmptyCollectionException;
import Nodes.LinearNode;

public class LinkedQueue<T> implements QueueADT<T> {

    private LinearNode<T> front;

    private LinearNode<T> rear;

    private int count;

    public LinkedQueue() {
        this.front = null;
        this.rear = null;
        this.count = 0;
    }

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

    @Override
    public T first() throws EmptyCollectionException {
        if (this.count == 0) {
            throw new EmptyCollectionException("A queue está vazia");
        }

        return this.front.getElement();
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
        LinearNode<T> current = this.front;

        while (current != null) {
            temp = temp + current.getElement().toString();
            current = current.getNext();
        }

        return temp;
    }
}
