package Stacks;

import Exceptions.EmptyCollectionException;
import Interfaces.StackADT;
import Nodes.LinearNode;

public class LinkedStack<T> implements StackADT<T> {

    private int top;

    private LinearNode<T> head;

    public LinkedStack() {
        this.top = 0;
        this.head = null;
    }

    @Override
    public void push(T element) {
        LinearNode<T> newNod = new LinearNode<>(element);

        newNod.setNext(this.head);

        this.head = newNod;
        this.top++;
    }

    @Override
    public T pop() throws EmptyCollectionException {
        if (this.top == 0) {
            throw new EmptyCollectionException("A Stack está vazia, logo não é possível remover!");
        }

        T temp_element = this.head.getElement();

        if (this.top == 1) {
            this.head = null;
        } else {
            this.head = this.head.getNext();
        }

        this.top--;
        return temp_element;
    }

    @Override
    public T peek() throws EmptyCollectionException {
        if (this.top == 0) {
            throw new EmptyCollectionException("A Stack está vazia, logo não dá para consultar o ultimo elemento!");
        }

        return this.head.getElement();
    }

    @Override
    public boolean isEmpty() {
        boolean empty = false;

        if (this.top == 0) {
            empty = true;
        }

        return empty;
    }

    @Override
    public int size() {
        return this.top;
    }

    @Override
    public String toString() {
        String temp = "";
        LinearNode<T> tempLinked = this.head;

        for (int i = 0; i < this.top; i++) {
            temp = temp + tempLinked.getElement().toString() + " ";
            tempLinked = tempLinked.getNext();
        }

        return temp;
    }
}
