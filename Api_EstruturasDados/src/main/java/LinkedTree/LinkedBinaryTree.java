package LinkedTree;

import Exceptions.ElementNotFoundException;
import Exceptions.EmptyCollectionException;
import Interfaces.BinaryTreeADT;
import LinkedList.LinearLinkedUnorderedList;
import Queue.LinkedQueue;

import java.util.Iterator;

/**
 * Representa uma árvore binária ligada que implementa a interface {@link BinaryTreeADT}.
 *
 * @param <T> o tipo dos elementos armazenados na árvore binária.
 * @author Artur Pinto
 * Nº mecanográfico: 8230138
 * @author Francisco Oliveira
 * Nº mecanográfico: 8230148
 * @version 1.0
 */
public abstract class LinkedBinaryTree<T> implements BinaryTreeADT<T> {

    /**
     * Contador de elementos na árvore binária.
     */
    protected int count;

    /**
     * Raiz da árvore binária.
     */
    protected BinaryTreeNode<T> root;

    /**
     * Construtor padrão que cria uma árvore binária vazia.
     */
    public LinkedBinaryTree() {
        this.count = 0;
        this.root = null;
    }

    /**
     * Construtor que cria uma árvore binária com um único elemento.
     *
     * @param element o elemento a ser armazenado na raiz da árvore.
     */
    public LinkedBinaryTree(T element) {
        this.count = 0;
        this.root = new BinaryTreeNode<>(element);
    }

    /**
     * Retorna o elemento armazenado na raiz da árvore.
     *
     * @return o elemento da raiz.
     */
    @Override
    public T getRoot() {
        return this.root.element;
    }

    /**
     * Verifica se a árvore está vazia.
     *
     * @return {@code true} se a árvore estiver vazia, {@code false} caso contrário.
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
     * @return o número de elementos na árvore.
     */
    @Override
    public int size() {
        return this.count;
    }

    /**
     * Verifica se um elemento está presente na árvore.
     *
     * @param targetElement o elemento a ser procurado.
     * @return {@code true} se o elemento for encontrado, {@code false} caso contrário.
     */
    @Override
    public boolean contains(T targetElement) {
        T target = null;
        boolean contains = false;

        try {
            target = find(targetElement);
        } catch (ElementNotFoundException | EmptyCollectionException ex) {
            System.out.println(ex.getMessage());
        }

        if (target != null) {
            contains = true;
        }

        return contains;
    }

    /**
     * Metodo auxiliar que pesquisa recursivamente um elemento na árvore.
     *
     * @param targetElement o elemento a ser procurado.
     * @param next          o nó atual a ser verificado.
     * @return o nó que contém o elemento ou {@code null} se o elemento não for encontrado.
     */
    private BinaryTreeNode<T> findAgain(T targetElement, BinaryTreeNode<T> next) {
        if (next == null) {
            return null;
        }

        if (next.element.equals(targetElement)) {
            return next;
        }

        BinaryTreeNode<T> temp = findAgain(targetElement, next.left);

        if (temp == null) {
            temp = findAgain(targetElement, next.right);
        }

        return temp;
    }

    /**
     * Procura um elemento na árvore binária.
     *
     * @param targetElement o elemento a ser procurado.
     * @return o elemento encontrado.
     * @throws ElementNotFoundException se o elemento não for encontrado.
     * @throws EmptyCollectionException se a árvore estiver vazia.
     */
    @Override
    public T find(T targetElement) throws ElementNotFoundException, EmptyCollectionException {
        if (this.count == 0) {
            throw new EmptyCollectionException("A lista está vazia");
        }

        BinaryTreeNode<T> current = findAgain(targetElement, this.root);

        if (current == null) {
            throw new ElementNotFoundException("O target introduzido não existe na arvore");
        }

        return current.element;
    }

    /**
     * Realiza uma travessia in-order (em ordem) na árvore e armazena os elementos
     * em uma lista.
     *
     * @param node     o nó atual a ser visitado.
     * @param tempList a lista onde os elementos serão armazenados.
     */
    protected void inorder(BinaryTreeNode<T> node, LinearLinkedUnorderedList<T> tempList) {
        if (node != null) {
            inorder(node.left, tempList);
            tempList.addToRear(node.element);
            inorder(node.right, tempList);
        }
    }

    /**
     * Retorna um iterador para a travessia in-order da árvore.
     *
     * @return um iterador para a travessia in-order.
     */
    @Override
    public Iterator<T> iteratorInOrder() {
        LinearLinkedUnorderedList<T> tempList = new LinearLinkedUnorderedList<>();
        inorder(root, tempList);

        return tempList.iterator();
    }

    /**
     * Realiza uma travessia pre-order (pré-ordem) na árvore e armazena os elementos
     * em uma lista.
     *
     * @param node     o nó atual a ser visitado.
     * @param tempList a lista onde os elementos serão armazenados.
     */
    protected void preOrder(BinaryTreeNode<T> node, LinearLinkedUnorderedList<T> tempList) {
        if (node != null) {
            tempList.addToRear(node.element);
            preOrder(node.left, tempList);
            preOrder(node.right, tempList);
        }
    }

    /**
     * Retorna um iterador para a travessia pre-order da árvore.
     *
     * @return um iterador para a travessia pre-order.
     */
    @Override
    public Iterator<T> iteratorPreOrder() {
        LinearLinkedUnorderedList<T> tempList = new LinearLinkedUnorderedList<>();
        preOrder(root, tempList);

        return tempList.iterator();
    }

    /**
     * Realiza uma travessia post-order (pós-ordem) na árvore e armazena os elementos
     * em uma lista.
     *
     * @param node     o nó atual a ser visitado.
     * @param tempList a lista onde os elementos serão armazenados.
     */
    protected void posOrder(BinaryTreeNode<T> node, LinearLinkedUnorderedList<T> tempList) {
        if (node != null) {
            posOrder(node.left, tempList);
            posOrder(node.right, tempList);
            tempList.addToRear(node.element);
        }
    }

    /**
     * Retorna um iterador para a travessia post-order da árvore.
     *
     * @return um iterador para a travessia post-order.
     */
    @Override
    public Iterator<T> iteratorPostOrder() {
        LinearLinkedUnorderedList<T> tempList = new LinearLinkedUnorderedList<>();
        preOrder(root, tempList);

        return tempList.iterator();
    }

    /**
     * Realiza uma travessia level-order (nível por nível) na árvore e armazena os
     * elementos em uma lista.
     *
     * @param node     o nó atual a ser visitado.
     * @param tempList a lista onde os elementos serão armazenados.
     */
    protected void levelOrder(BinaryTreeNode<T> node, LinearLinkedUnorderedList<T> tempList) {
        LinkedQueue<BinaryTreeNode<T>> nodeQueue = new LinkedQueue<BinaryTreeNode<T>>();

        if (node != null) {
            nodeQueue.enqueue(node);

            while (!nodeQueue.isEmpty()) {
                try {
                    BinaryTreeNode<T> current = nodeQueue.dequeue();
                    if (current != null) {
                        tempList.addToRear(current.element);

                        if (current.left != null) {
                            nodeQueue.enqueue(current.left);
                        }

                        if (current.right != null) {
                            nodeQueue.enqueue(current.right);
                        }
                    }
                } catch (EmptyCollectionException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }
    }

    /**
     * Retorna um iterador para a travessia level-order da árvore.
     *
     * @return um iterador para a travessia level-order.
     */
    @Override
    public Iterator<T> iteratorLevelOrder() {
        LinearLinkedUnorderedList<T> results = new LinearLinkedUnorderedList<>();
        levelOrder(this.root, results);

        return results.iterator();
    }

    /**
     * Retorna uma representação em string da árvore, realizando uma travessia
     * level-order.
     *
     * @return uma string representando os elementos da árvore.
     */
    @Override
    public String toString() {
        String temp = "";
        Iterator<T> itr = this.iteratorLevelOrder();

        while (itr.hasNext()) {
            temp += itr.next() + " ";
        }

        return temp;
    }
}
