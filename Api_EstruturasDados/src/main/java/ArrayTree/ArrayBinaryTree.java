package ArrayTree;

import ArrayList.ArrayUnorderedList;
import Exceptions.ElementNotFoundException;
import Exceptions.EmptyCollectionException;
import Interfaces.BinaryTreeADT;
import Queue.CircularArrayQueue;

import java.util.Iterator;


/**
 * Uma classe que representa uma árvore binária implementada, usa um array.
 * Fornece vários métodos para manipular e percorrer a árvore binária.
 *
 * @param <T> o tipo de elementos armazenados na árvore.
 * @author Artur Pinto
 * Nº mecanográfico: 8230138
 * @author Francisco Oliveira
 * Nº mecanográfico: 8230148
 * @version 1.0
 */
public abstract class ArrayBinaryTree<T> implements BinaryTreeADT<T> {

    /** Capacidade default para a árvore binária. */
    private static int DEFAULT_CAPACITY = 50;

    /** O número de elementos na árvore. */
    protected int count;

    /** O array que armazena os elementos da árvore. */
    protected T[] tree;

    /**
     * Constrói uma árvore binária vazia com capacidade default.
     */
    public ArrayBinaryTree() {
        this.count = 0;
        this.tree = (T[]) ((new Object[DEFAULT_CAPACITY]));
    }

    /**
     * Constrói uma árvore binária com um elemento raiz.
     *
     * @param element o elemento raiz da árvore binária.
     */
    public ArrayBinaryTree(T element) {
        count = 1;
        tree = (T[]) new Object[DEFAULT_CAPACITY];
        tree[0] = element;
    }

    /**
     * Expande a capacidade do array da árvore quando mais espaço é necessário.
     */
    protected void expandCapacity() {
        T[] temp = (T[]) (new Object[(this.tree.length * 2)]);

        System.arraycopy(this.tree, 0, temp, 0, this.tree.length);

        this.tree = temp;
    }

    /**
     * Retorna o elemento raiz da árvore.
     *
     * @return o elemento raiz.
     */
    @Override
    public T getRoot() {
        return this.tree[0];
    }

    /**
     * Verifica se a árvore está vazia.
     *
     * @return true se a árvore estiver vazia, false caso contrário.
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
     * Retorna o número de elementos na árvore.
     *
     * @return o tamanho da árvore.
     */
    @Override
    public int size() {
        return this.count;
    }

    /**
     * Verifica se a árvore contém o elemento especificado.
     *
     * @param targetElement o elemento a ser verificado.
     * @return true se o elemento for encontrado, false caso contrário.
     */
    @Override
    public boolean contains(T targetElement) {
        boolean contain = false;
        T target = null;

        try {
            target = find(targetElement);
        } catch (ElementNotFoundException | EmptyCollectionException ex) {
            System.out.println(ex.getMessage());
        }

        if (target != null) {
            contain = true;
        }

        return contain;
    }

    /**
     * Encontra e retorna o elemento target na árvore.
     *
     * @param targetElement o elemento a ser encontrado.
     * @return o elemento alvo se encontrado.
     * @throws ElementNotFoundException se o elemento não for encontrado.
     * @throws EmptyCollectionException se a árvore estiver vazia.
     */
    @Override
    public T find(T targetElement) throws ElementNotFoundException, EmptyCollectionException {
        if (this.count == 0) {
            throw new EmptyCollectionException("A lista está vazia");
        }

        boolean found = false;
        T temp = null;

        for (int ct = 0; ct < this.count && !found; ct++) {
            if (targetElement.equals(this.tree[ct])) {
                found = true;
                temp = this.tree[ct];
            }
        }

        if (!found) {
            throw new ElementNotFoundException("binary tree");
        }

        return temp;
    }

    /**
     * Realiza uma travessia em ordem (in-order) da árvore e adiciona os elementos à lista.
     *
     * @param n o índice atual no array da árvore.
     * @param tempList a lista para armazenar os elementos na ordem.
     */
    protected void inOrder(int n, ArrayUnorderedList<T> tempList) {
        if (n < this.tree.length) {
            if (tree[n] != null) {
                inOrder((2 * n) + 1, tempList);
                tempList.addToRear(this.tree[n]);
                inOrder(2 * (n + 1), tempList);
            }
        }
    }

    /**
     * Retorna um iterador para uma travessia em ordem (in-order) da árvore.
     *
     * @return um iterador para a travessia em ordem.
     */
    @Override
    public Iterator<T> iteratorInOrder() {
        ArrayUnorderedList<T> tempList = new ArrayUnorderedList<T>();
        inOrder(0, tempList);

        return tempList.iterator();
    }

    /**
     * Realiza uma travessia em pré-ordem (pre-order) da árvore e adiciona os elementos à lista.
     *
     * @param n o índice atual no array da árvore.
     * @param tempList a lista para armazenar os elementos em pré-ordem.
     */
    protected void preOrder(int n, ArrayUnorderedList<T> tempList) {
        if (n < this.tree.length) {
            if (tree[n] != null) {
                tempList.addToRear(this.tree[n]);
                preOrder((2 * n) + 1, tempList);
                preOrder(2 * (n + 1), tempList);
            }
        }
    }

    /**
     * Retorna um iterador para uma travessia em pré-ordem (pre-order) da árvore.
     *
     * @return um iterador para a travessia em pré-ordem.
     */
    @Override
    public Iterator<T> iteratorPreOrder() {
        ArrayUnorderedList<T> tempList = new ArrayUnorderedList<T>();
        preOrder(0, tempList);

        return tempList.iterator();
    }

    /**
     * Realiza uma travessia em pós-ordem (post-order) da árvore e adiciona os elementos à lista.
     *
     * @param n o índice atual no array da árvore.
     * @param tempList a lista para armazenar os elementos em pós-ordem.
     */
    protected void postOrder(int n, ArrayUnorderedList<T> tempList) {
        if (n < this.tree.length) {
            if (tree[n] != null) {
                postOrder((2 * n) + 1, tempList);
                postOrder(2 * (n + 1), tempList);
                tempList.addToRear(this.tree[n]);
            }
        }
    }

    /**
     * Retorna um iterador para uma travessia em pós-ordem (post-order) da árvore.
     *
     * @return um iterador para a travessia em pós-ordem.
     */
    @Override
    public Iterator<T> iteratorPostOrder() {
        ArrayUnorderedList<T> tempList = new ArrayUnorderedList<T>();
        postOrder(0, tempList);

        return tempList.iterator();
    }

    /**
     * Realiza uma travessia em nível (level-order) da árvore e adiciona os elementos à lista.
     *
     * @param n o índice atual no array da árvore.
     * @param tempList a lista para armazenar os elementos em nível.
     * @param nodes a queue para gerir os nós durante a travessia.
     */
    protected void levelOrder(int n, ArrayUnorderedList<T> tempList, CircularArrayQueue<T> nodes) {
        if (n < this.tree.length) {
            nodes.enqueue(this.tree[n]);

            while (!nodes.isEmpty()) {
                try {
                    T current = nodes.dequeue();
                    if (current != null) {
                        tempList.addToRear(current);

                        if (((2 * n) + 1) < this.tree.length && this.tree[(2 * n) + 1] != null) {
                            nodes.enqueue(this.tree[(2 * n) + 1]);
                        }

                        if ((2 * (n + 1)) < this.tree.length && this.tree[2 * (n + 1)] != null) {
                            nodes.enqueue(this.tree[2 * (n + 1)]);
                        }

                        n++;
                    }
                } catch (EmptyCollectionException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }
    }

    /**
     * Retorna um iterador para uma travessia em nível (level-order) da árvore.
     *
     * @return um iterador para a travessia em nível.
     */
    @Override
    public Iterator<T> iteratorLevelOrder() {
        CircularArrayQueue<T> arrayQueue = new CircularArrayQueue<>();
        ArrayUnorderedList<T> tempList = new ArrayUnorderedList<T>();
        levelOrder(0, tempList, arrayQueue);

        return tempList.iterator();
    }

    /**
     * Retorna uma representação em string da árvore em nível (level-order).
     *
     * @return uma string a representar a árvore em nível.
     */
    @Override
    public String toString() {
        String temp = "";
        Iterator<T> itr = this.iteratorLevelOrder();

        while (itr.hasNext()) {
            temp = temp + itr.next() + " ";
        }

        return temp;
    }
}
