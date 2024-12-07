package ArrayList;

import Exceptions.ElementNotFoundException;
import Exceptions.EmptyCollectionException;
import Interfaces.ArrayListADT;
import Interfaces.ListADT;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public abstract class ArrayList<T> implements ListADT<T>, ArrayListADT<T> {

    private static final int CAPACITY_DEFAULT = 100;

    protected int modCount;

    protected int count;

    protected T[] list;

    public ArrayList() {
        this.modCount = 0;
        this.count = 0;
        this.list = (T[]) (new Object[CAPACITY_DEFAULT]);
    }

    public ArrayList(int capacity) {
        this.modCount = 0;
        this.count = 0;

        if (capacity > 0) {
            this.list = (T[]) (new Object[capacity]);
        } else {
            this.list = (T[]) (new Object[CAPACITY_DEFAULT]);
        }
    }

    protected void expandArray() {
        T[] temp = (T[]) (new Object[(this.list.length * 2)]);

        System.arraycopy(this.list, 0, temp, 0, this.list.length);

        this.list = temp;
    }

    @Override
    public T removeFirst() throws EmptyCollectionException {
        if (this.count == 0) {
            throw new EmptyCollectionException("A lista está vazia!");
        }

        T elementRemove = this.list[0];
        this.list[0] = null;

        for (int i = 0; i < this.count; i++) {
            this.list[i] = this.list[i + 1];
        }

        this.list[this.count - 1] = null;
        this.count--;
        this.modCount++;
        return elementRemove;
    }

    @Override
    public T removeLast() throws EmptyCollectionException {
        if (this.count == 0) {
            throw new EmptyCollectionException("A lista está vazia!");
        }

        this.count--;
        T elementRemove = this.list[this.count];
        this.list[this.count] = null;
        this.modCount++;

        return elementRemove;
    }

    @Override
    public T remove(T element) throws EmptyCollectionException {
        if (this.count == 0) {
            throw new EmptyCollectionException("A lista está vazia");
        }

        if (!contains(element)) {
            throw new ElementNotFoundException("O elemento introduzido não existe");
        }

        int pos = -1;

        do {
            pos++;
        } while (!this.list[pos].equals(element));

        T elementRem = this.list[pos];
        this.list[pos] = null;

        for (int i = pos; i < this.count; i++) {
            this.list[i] = this.list[i + 1];
        }

        this.list[this.count - 1] = null;
        this.count--;
        this.modCount++;
        return elementRem;
    }

    @Override
    public T first() {
        return this.list[0];
    }

    @Override
    public T last() {
        return this.list[this.count - 1];
    }

    @Override
    public boolean contains(T target) {
        boolean targetFound = false;

        for (T currentEl : this.list) {
            if (target.equals(currentEl)) {
                targetFound = true;
            }
        }

        return targetFound;
    }

    @Override
    public T find(int pos) throws ArrayIndexOutOfBoundsException, NullPointerException {
        if(pos > this.count - 1 || pos < 0) {
            throw new ArrayIndexOutOfBoundsException("Posição que não existe no array");
        }

        if(this.list[pos] == null) {
            throw new NullPointerException("A posição corresponde a uma posição nula");
        }

        return this.list[pos];
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

        for (int i = 0; i < this.count; i++) {
            temp = temp + this.list[i] + " ";
        }

        return temp;
    }

    private class MyIterator<E> implements Iterator<E> {

        private int current;

        private int exceptedModCount;

        private boolean isOkToRemove;

        private MyIterator() {
            this.current = 0;
            this.exceptedModCount = modCount;
            this.isOkToRemove = false;
        }

        @Override
        public boolean hasNext() {
            return (this.current != size());
        }

        @Override
        public E next() {
            if (this.exceptedModCount != modCount) {
                throw new ConcurrentModificationException();
            }

            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            this.isOkToRemove = true;
            this.current++;
            return (E) list[current - 1];
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
                ArrayList.this.remove(list[current - 1]);
            } catch (EmptyCollectionException ex) {
                System.out.print(ex.getMessage());
            }

            this.exceptedModCount++;
            this.isOkToRemove = false;
            this.current--;
        }
    }
}
