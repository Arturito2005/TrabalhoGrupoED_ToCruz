package LinkedList;

import Exceptions.ElementNotFoundException;
import Exceptions.EmptyCollectionException;
import Interfaces.ListADT;
import Nodes.LinearNode;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Representa uma lista ligada linear genérica. Esta classe implementa a interface {@link ListADT},
 * fornecendo métodos para manipulação de elementos em uma lista ligada (adicionar, remover, acessar, etc.).
 *
 * @param <T> o tipo de dado armazenado na lista
 * @author Artur Pinto
 * Nº mecanográfico: 8230138
 * @author Francisco Oliveira
 * Nº mecanográfico: 8230148
 * @version 1.0
 */
public abstract class LinearLinkedList<T> implements ListADT<T> {

    /**
     * Contador de modificações realizadas na lista. Usado para verificar modificações concorrentes.
     */
    protected int modCount;

    /**
     * Contador de elementos na lista.
     */
    protected int count;

    /**
     * Referência para o primeiro nó da lista (cabeça).
     */
    protected LinearNode<T> head, tail;

    /**
     * Constrói uma lista vazia.
     */
    public LinearLinkedList() {
        this.count = 0;
        this.modCount = 0;
        this.head = null;
        this.tail = this.head;
    }

    /**
     * Remove o único elemento da lista quando a lista contém apenas um elemento.
     */
    private void removeOneElement() {
        this.head = null;
        this.tail = null;
    }

    /**
     * Remove o primeiro elemento da lista.
     *
     * @return o elemento removido
     * @throws EmptyCollectionException se a lista estiver vazia
     */
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

    /**
     * Remove o último elemento da lista.
     *
     * @return o elemento removido
     * @throws EmptyCollectionException se a lista estiver vazia
     */
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

    /**
     * Remove o primeiro elemento correspondente ao valor fornecido.
     *
     * @param element o elemento a ser removido
     * @return o elemento removido
     * @throws EmptyCollectionException se a lista estiver vazia
     * @throws ElementNotFoundException se o elemento não for encontrado na lista
     */
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

    /**
     * Retorna o primeiro elemento da lista.
     *
     * @return o primeiro elemento da lista
     */
    @Override
    public T first() {
        return this.head.getElement();
    }

    /**
     * Retorna o último elemento da lista.
     *
     * @return o último elemento da lista
     */
    @Override
    public T last() {
        return this.tail.getElement();
    }

    /**
     * Verifica se o elemento alvo existe na lista.
     *
     * @param target o elemento a ser verificado
     * @return {@code true} se o elemento estiver presente na lista, {@code false} caso contrário
     */
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

    /**
     * Verifica se a lista está vazia.
     *
     * @return {@code true} se a lista estiver vazia, {@code false} caso contrário
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
     * @return o número de elementos na lista
     */
    @Override
    public int size() {
        return this.count;
    }

    /**
     * Retorna um iterador para percorrer a lista.
     *
     * @return um iterador para percorrer a lista
     */
    @Override
    public Iterator<T> iterator() {
        return new MyIterator<>();
    }

    /**
     * Retorna uma representação em String da lista.
     *
     * @return uma String representando a lista
     */
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

    /**
     * Implementação interna do iterador para a lista.
     */
    private class MyIterator<E> implements Iterator<E> {

        /**
         * Representa o iterador para a lista ligada linear.
         * Mantém o estado atual do iterador, incluindo o nó atual, o contador de modificações esperadas,
         * o estado de remoção e o elemento atualmente disponível para remoção.
         *
         * @param <E> o tipo do elemento retornado pelo iterador
         */
        private LinearNode<T> current;

        /**
         * Contador de modificações realizadas na lista. Usado para verificar modificações concorrentes.
         * Caso a lista seja modificada enquanto o iterador está em uso, será lançada uma exceção de modificação concorrente.
         */
        private int exceptedModCount;

        /**
         * Flag que indica se a remoção do elemento atual é permitida.
         * A remoção só é permitida após a chamada do método {@link #next()}.
         */
        private boolean isOkToRemove;

        /**
         * O elemento que foi obtido por meio do iterador e que pode ser removido.
         * Armazena o último elemento acessado por {@link #next()} para possibilitar a remoção.
         */
        private E elementOkToRemove;

        /**
         * Constrói um iterador inicializando o nó atual e o contador de modificações.
         * Também define a flag de remoção e o elemento removível para os valores padrão.
         */
        private MyIterator() {
            this.current = head;
            this.exceptedModCount = modCount;
            this.isOkToRemove = false;
            this.elementOkToRemove = null;
        }

        /**
         * Verifica se há um próximo elemento na lista.
         *
         * @return {@code true} se houver um próximo elemento, {@code false} caso contrário
         */
        @Override
        public boolean hasNext() {
            return (current != null);
        }

        /**
         * Retorna o próximo elemento da lista.
         *
         * @return o próximo elemento da lista
         * @throws ConcurrentModificationException se a lista foi modificada enquanto o iterador está em uso
         * @throws NoSuchElementException          se não houver mais elementos
         */
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

        /**
         * Remove o último elemento retornado pelo iterador.
         *
         * @throws ConcurrentModificationException se a lista foi modificada enquanto o iterador está em uso
         * @throws IllegalStateException           se o iterador não estiver em um estado válido para remoção
         */
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
