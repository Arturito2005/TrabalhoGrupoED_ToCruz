package Stacks;

import Exceptions.EmptyCollectionException;
import Interfaces.StackADT;

public class ArrayStack<T> implements StackADT<T> {

    private static final int DEFAULT_SIZE = 50;

    private int top;

    private T[] stack;

    public ArrayStack() {
        this.stack = (T[]) (new Object[DEFAULT_SIZE]);
        this.top = 0;
    }

    public ArrayStack(int size_stack) {
        this.stack = (T[]) (new Object[size_stack]);
        this.top = 0;
    }

    private void expandStack() {
        T[] tempStack = (T[]) (new Object[this.top * 2]);

        for (int i = 0; i < top; i++) {
            tempStack[i] = this.stack[i];
        }

        this.stack = tempStack;
    }

    @Override
    public void push(T element) {
        if (this.top == this.stack.length) {
            expandStack();
        }

        this.stack[this.top++] = element;
    }

    @Override
    public T pop() throws EmptyCollectionException {
        if (this.top == 0) {
            throw new EmptyCollectionException("A stack está vazia");
        }

        T oldTop = this.stack[top - 1];

        this.top--;
        this.stack[this.top] = null;

        return oldTop;
    }

    @Override
    public T peek() throws EmptyCollectionException {
        if (this.top == 0) {
            throw new EmptyCollectionException("A Stack está vazia");
        }

        return this.stack[this.top - 1];
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

        for (int i = 0; i < this.top; i++) {
            temp = temp + this.stack[i].toString() + " ";
        }

        return temp;
    }
}
