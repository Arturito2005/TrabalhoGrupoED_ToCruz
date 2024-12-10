package DoubleLinkedList;

import Exceptions.ElementNotFoundException;
import Exceptions.EmptyCollectionException;
import Interfaces.ListADT;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Esta classe representa uma lista duplamente encadeada genérica. Ela implementa
 * as operações básicas de uma lista ordenada, como inserção, remoção, e iteração.
 * A lista mantém uma referência para o primeiro (head) e o último (tail) nó da lista.
 *
 * @param <T> o tipo dos elementos armazenados na lista.
 * @author Artur Pinto
 * Nº mecanográfico: 8230138
 * @author Francisco Oliveira
 * Nº mecanográfico: 8230148
 * @version 1.0
 */
public abstract class DoublyLinkedList<T> implements ListADT<T> {

    /**
     * Contador de modificações da lista, usado para controle de concorrência
     */
    protected int modCount;

    /**
     * Número de elementos na lista
     */
    protected int count;

    /**
     * Referências para o primeiro e último nó da lista
     */
    protected DoubleNode<T> head, tail;

    /**
     * Constrói uma lista duplamente encadeada vazia.
     */
    public DoublyLinkedList() {
        this.count = 0;
        this.modCount = 0;
        this.head = null;
        this.tail = null;
    }


    /**
     * Remove e retorna o primeiro elemento da lista.
     *
     * @return o primeiro elemento da lista.
     * @throws EmptyCollectionException se a lista estiver vazia.
     */
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

    /**
     * Remove e retorna o último elemento da lista.
     *
     * @return o último elemento da lista.
     * @throws EmptyCollectionException se a lista estiver vazia.
     */
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

    /**
     * Remove o elemento especificado da lista.
     *
     * @param element o elemento a ser removido.
     * @return o elemento removido.
     * @throws EmptyCollectionException se a lista estiver vazia.
     * @throws ElementNotFoundException se o elemento não for encontrado na lista.
     */
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

    /**
     * Retorna o primeiro elemento da lista.
     *
     * @return o primeiro elemento da lista.
     */
    @Override
    public T first() {
        return this.tail.getElement();
    }

    /**
     * Retorna o último elemento da lista.
     *
     * @return o último elemento da lista.
     */
    @Override
    public T last() {
        return this.head.getElement();
    }

    /**
     * Verifica se o elemento especificado está presente na lista.
     *
     * @param target o elemento a ser verificado.
     * @return true se o elemento estiver na lista, false caso contrário.
     */
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

    /**
     * Verifica se a lista está vazia.
     *
     * @return true se a lista estiver vazia, false caso contrário.
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
     * Retorna o número de elementos na lista.
     *
     * @return o número de elementos na lista.
     */
    @Override
    public int size() {
        return this.count;
    }

    /**
     * Retorna um iterador para a lista.
     *
     * @return um iterador para a lista.
     */
    @Override
    public Iterator<T> iterator() {
        return new MyIterator<>();
    }

    /**
     * Retorna uma representação em string da lista.
     *
     * @return a representação em string da lista.
     */
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


    /**
     * Classe interna que implementa o iterador para a lista duplamente encadeada.
     */
    private class MyIterator<E> implements Iterator<E> {

        /**
         * Variável que mantém a referência do nó atual durante a iteração.
         */
        private DoubleNode<T> current;

        /**
         * Contador de modificações esperado para o controle de concorrência.
         * Usado para verificar se a lista foi modificada enquanto o iterador estava em uso.
         */
        private int exceptedModCount;

        /**
         * Flag que indica se o próximo elemento pode ser removido com segurança.
         * Este valor é definido como true após a chamada do método next().
         */
        private boolean isOkToRemove;

        /**
         * O elemento que pode ser removido.
         * Este elemento é armazenado após a chamada do método next() para ser removido posteriormente.
         */
        private E elementOkToRemove;

        /**
         * Constrói o iterador para a lista.
         */
        private MyIterator() {
            this.current = head;
            this.exceptedModCount = modCount;
            this.isOkToRemove = false;
            this.elementOkToRemove = null;
        }

        /**
         * Verifica se há um próximo elemento.
         *
         * @return true se houver um próximo elemento, false caso contrário.
         */
        @Override
        public boolean hasNext() {
            return (current != null);
        }

        /**
         * Retorna o próximo elemento da lista.
         *
         * @return o próximo elemento.
         * @throws ConcurrentModificationException se a lista foi modificada enquanto o iterador estava em uso.
         * @throws NoSuchElementException se não houver mais elementos.
         */
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

        /**
         * Remove o último elemento retornado pelo iterador.
         *
         * @throws ConcurrentModificationException se a lista foi modificada enquanto o iterador estava em uso.
         * @throws IllegalStateException se o método next() não foi chamado antes de remove().
         */
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
