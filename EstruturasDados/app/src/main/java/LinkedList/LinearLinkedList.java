package LinkedList;

import Exceptions.ElementNotFoundException;
import Exceptions.EmptyCollectionException;
import Interfaces.ListADT;
import Nodes.LinearNode;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public abstract class LinearLinkedList<T> implements ListADT<T> {

    protected int modCount;

    protected int count;

    protected LinearNode<T> head, tail;

    public LinearLinkedList() {
        this.count = 0;
        this.modCount = 0;
        this.head = null;
        this.tail = this.head;
    }

    private void removeOneElement() {
        this.head = null;
        this.tail = null;
    }

    @Override
    public T removeFirst() throws EmptyCollectionException {
        if (this.count == 0) {
            throw new EmptyCollectionException("A lista está vaiza!");
        }

        T elementRem = this.head.getElement();

        if (this.count == 1) {
            removeOneElement();
        } else {
            this.head = this.head.getNext();
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

        T elementRem = this.tail.getElement();

        LinearNode<T> current = this.head;
        LinearNode<T> previous = null;

        while (current.getElement() != this.tail.getElement()) {
            previous = current;
            current = current.getNext();
        }

        if (this.count == 1) {
            removeOneElement();
        } else {
            this.tail = previous;
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

        LinearNode<T> current = this.head;
        LinearNode<T> previous = null;
        T elementRem = null;
        boolean find = false;
        int pos_find = this.count;

        while (current != null && find == false) {
            if (current.getElement().equals(element)) {
                find = true;
                elementRem = element;
            } else {
                previous = current;
                current = current.getNext();
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
            this.head = current.getNext();
        } else if (current.getElement().equals(this.tail.getElement()) && pos_find == 1) {
            previous.setNext(null);
            this.tail = previous;
        } else {
            previous.setNext(current.getNext());
        }

        this.count--;
        this.modCount++;
        return elementRem;
    }

    @Override
    public T first() {
        return this.head.getElement();
    }

    @Override
    public T last() {
        return this.tail.getElement();
    }

    @Override
    public boolean contains(T target) {
        boolean conatin = false;
        LinearNode<T> current = this.head;

        while (current != null && conatin == false) {
            if (current.getElement().equals(target)) {
                conatin = true;
            } else {
                current = current.getNext();
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
        LinearNode<T> current = this.head;
        int i = this.count;

        while (i > 0) {
            temp = temp + current.getElement() + " ";
            current = current.getNext();
            i--;
        }

        return temp;
    }

    private class MyIterator<E> implements Iterator<E> {

        private LinearNode<T> current;

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
                throw new ConcurrentModificationException("Houve alguma modificação na lista!");
            }

            if (!hasNext()) {
                throw new NoSuchElementException("Não existe proximo elemento na lista");
            }

            E element = (E) current.getElement();
            this.elementOkToRemove = element;
            current = current.getNext();

            this.isOkToRemove = true;
            return element;
        }

        @Override
        public void remove() {
            if (this.exceptedModCount != modCount) {
                throw new ConcurrentModificationException("Houve alguma modificação na lista!");
            }

            if (!this.isOkToRemove) {
                throw new IllegalStateException("O pointeiro não aponta para nenhuma posição!");
            }

            try {
                LinearLinkedList.this.remove((T) elementOkToRemove);
            } catch (EmptyCollectionException ex) {
                System.out.print(ex.getMessage());
            }

            this.exceptedModCount++;
            this.isOkToRemove = false;
            this.elementOkToRemove = null;
        }
    }
}
