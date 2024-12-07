package DoubleLinkedList;

import Exceptions.ElementNotFoundException;
import Exceptions.EmptyCollectionException;
import Interfaces.ListADT;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public abstract class DoublyLinkedList<T> implements ListADT<T> {

    protected int modCount;

    protected int count;

    protected DoubleNode<T> head, tail;

    public DoublyLinkedList() {
        this.count = 0;
        this.modCount = 0;
        this.head = null;
        this.tail = null;
    }

    @Override
    public T removeFirst() throws EmptyCollectionException {
        if (this.count == 0) {
            throw new EmptyCollectionException("A lista está vaiza!");
        }

        T elementRem = this.tail.getElement();

        if (this.count == 1) {
            this.head = null;
            this.tail = null;
        } else {
            this.tail = this.tail.getPrevious_ele();
        }

        this.count--;
        this.modCount++;
        return elementRem;
    }

    @Override
    public T removeLast() throws EmptyCollectionException {
        if (this.count == 0) {
            throw new EmptyCollectionException("A lista está vaiza!");
        }

        T elementRem = this.head.getElement();

        if (this.count == 1) {
            this.head = null;
            this.tail = null;
        } else {
            this.head = this.head.getNext_ele();
        }

        this.count--;
        this.modCount++;
        return elementRem;
    }

    @Override
    public T remove(T element) throws EmptyCollectionException, ElementNotFoundException {
        if (this.count == 0) {
            throw new EmptyCollectionException("A lista está vaiza!");
        }

        DoubleNode<T> current = this.head;
        int pos_find = this.count;
        boolean find = false;

        T elementRem = null;
        while (current != null && find == false) {
            if (current.getElement().equals(element)) {
                find = true;
                elementRem = element;
            } else {
                current = current.getNext_ele();
                pos_find--;
            }
        }

        if (find == false) {
            throw new ElementNotFoundException("O elemento introduzido não existe na lista!");
        }

        if (this.count == 1) {
            this.head = null;
            this.tail = null;
        } else if (current.getElement().equals(this.head.getElement()) && pos_find == this.count) {
            this.head = current.getNext_ele();
            this.head.setPrevious_ele(null);
        } else if (current.getElement().equals(this.tail.getElement()) && pos_find == 1) {
            this.tail = current.getPrevious_ele();
            this.tail.setNext_ele(null);
        } else {
            DoubleNode<T> temp_node_pre = current.getPrevious_ele();
            temp_node_pre.setNext_ele(current.getNext_ele());

            DoubleNode<T> temp_node_aft = current.getNext_ele();
            temp_node_aft.setPrevious_ele(current.getPrevious_ele());
        }

        this.count--;
        this.modCount++;
        return elementRem;
    }

    @Override
    public T first() {
        return this.tail.getElement();
    }

    @Override
    public T last() {
        return this.head.getElement();
    }

    @Override
    public boolean contains(T target) {
        boolean conatin = false;
        DoubleNode<T> current = this.head;

        while (current != null && conatin == false) {
            if (current.getElement().equals(target)) {
                conatin = true;
            } else {
                current = current.getNext_ele();
            }
        }

        return conatin;
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
    public Iterator<T> iterator() {
        return new MyIterator<>();
    }

    @Override
    public String toString() {
        String temp = "";
        DoubleNode<T> current = this.head;

        while (current != null) {
            temp = temp + current.getElement() + " ";
            current = current.getNext_ele();
        }

        return temp;
    }

    private class MyIterator<E> implements Iterator<E> {

        private DoubleNode<T> current;

        private int exceptedModCount;

        private boolean isOkToRemove;

        private E elementOkToRemove;

        private MyIterator() {
            this.current = head;
            this.exceptedModCount = modCount;
            this.isOkToRemove = false;
            this.elementOkToRemove = null;
        }

        @Override
        public boolean hasNext() {
            return (current != null);
        }

        @Override
        public E next() {
            if (this.exceptedModCount != modCount) {
                throw new ConcurrentModificationException();
            }

            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            E element = (E) current.getElement();
            this.elementOkToRemove = element;
            current = current.getNext_ele();

            this.isOkToRemove = true;
            return element;
        }

        @Override
        public void remove() {
            if (this.exceptedModCount != modCount) {
                throw new ConcurrentModificationException();
            }

            if (!this.isOkToRemove) {
                throw new IllegalStateException();
            }

            try {
                DoublyLinkedList.this.remove((T) elementOkToRemove);
            } catch (EmptyCollectionException ex) {
                System.out.print(ex.getMessage());
            }

            this.exceptedModCount++;
            this.isOkToRemove = false;
            this.elementOkToRemove = null;
        }
    }
}
