package LinkedTree;

import Exceptions.EmptyCollectionException;
import Interfaces.Heap.HeapADT;

/**
 * A classe Heap implementa uma heap.
 *
 * @param <T> o tipo de dado dos elementos armazenados na heap
 * @author Artur Pinto
 * Nº mecanográfico: 8230138
 * @author Francisco Oliveira
 * Nº mecanográfico: 8230148
 * @version 1.0
 */
public class LinkedHeap<T> extends LinkedBinaryTree<T> implements HeapADT<T> {

    /**
     * Referência para o último nó da heap, utilizado para facilitar a inserção de novos elementos na posição correta.
     */
    public HeapNode<T> lastNode;

    /**
     * Construtor que cria uma heap vazia.
     */
    public LinkedHeap() {
        super();
    }

    /**
     * Adiciona o elemento especificado a esta heap na posição apropriada
     * de acordo com o valor da sua chave. Note que elementos iguais
     * são adicionados à direita.
     *
     * @param obj o elemento a ser adicionado à heap
     */
    @Override
    public void addElement(T obj) {
        HeapNode<T> node = new HeapNode<>(obj);

        if (root == null) {
            root = node;
        } else {
            HeapNode<T> next_parent = getNextParentAdd();

            if (next_parent.left == null) {
                next_parent.left = node;
            } else {
                next_parent.right = node;
            }

            node.parent = next_parent;
        }

        lastNode = node;
        count++;

        if (count > 1) {
            heapifyAdd();
        }
    }

    /**
     * Retorna o nó que será o pai do novo nó a ser adicionado.
     *
     * @return o nó que será o pai do novo nó
     */
    private HeapNode<T> getNextParentAdd() {
        HeapNode<T> result = lastNode;

        while ((result != root) && (result.parent.left != result)) {
            result = result.parent;
        }

        if (result != root) {
            if (result.parent.right == null) {
                result = result.parent;
            } else {
                result = (HeapNode<T>) result.parent.right;

                while (result.left != null) {
                    result = (HeapNode<T>) result.left;
                }
            }
        } else {
            while (result.left != null) {
                result = (HeapNode<T>) result.left;
            }
        }

        return result;
    }

    /**
     * Reorganiza a heap após a adição de um nó.
     */
    private void heapifyAdd() {
        T temp;
        HeapNode<T> next = lastNode;

        temp = next.element;

        while ((next != root) && (((Comparable) temp).compareTo(next.parent.element) < 0)) {
            next.element = next.parent.element;
            next = next.parent;
        }
        next.element = temp;
    }

    /**
     * Remove o elemento com o valor mais baixo da heap e retorna uma
     * referência a ele. Lança uma exceção EmptyCollectionException caso a heap esteja vazia.
     *
     * @return o elemento com o valor mais baixo na heap
     * @throws EmptyCollectionException se a heap estiver vazia
     */
    @Override
    public T removeMin() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("Empty Heap");
        }
        T minElement = root.element;
        if (count == 1) {
            root = null;
            lastNode = null;
        } else {
            HeapNode<T> next_last = getNewLastNode();
            if (lastNode.parent.left == lastNode) {
                lastNode.parent.left = null;
            } else {
                lastNode.parent.right = null;
            }
            root.element = lastNode.element;
            lastNode = next_last;
            heapifyRemove();
        }
        count--;

        return minElement;
    }


    /**
     * Reorganiza a heap após a remoção do elemento raiz.
     */
    private void heapifyRemove() {
        T temp;
        HeapNode<T> node = (HeapNode<T>) root;
        HeapNode<T> left = (HeapNode<T>) node.left;
        HeapNode<T> right = (HeapNode<T>) node.right;
        HeapNode<T> next;

        if ((left == null) && (right == null)) {
            next = null;
        } else if (left == null) {
            next = right;
        } else if (right == null) {
            next = left;
        } else if (((Comparable) left.element).compareTo(right.element) < 0) {
            next = left;
        } else {
            next = right;
        }
        temp = node.element;
        while ((next != null) && (((Comparable) next.element).compareTo(temp) < 0)) {
            node.element = next.element;
            node = next;
            left = (HeapNode<T>) node.left;
            right = (HeapNode<T>) node.right;

            if ((left == null) && (right == null)) {
                next = null;
            } else if (left == null) {
                next = right;
            } else if (right == null) {
                next = left;
            } else if (((Comparable) left.element).compareTo(right.element) < 0) {
                next = left;
            } else {
                next = right;
            }
        }
        node.element = temp;
    }

    /**
     * Retorna o nó que será o novo último nó após uma remoção.
     *
     * @return o nó que será o novo último nó após uma remoção
     */
    private HeapNode<T> getNewLastNode() {
        HeapNode<T> result = lastNode;
        while ((result != root) && (result.parent.left == result)) {
            result = result.parent;
        }

        if (result != root) {
            result = (HeapNode<T>) result.parent.left;
        }
        while (result.right != null) {
            result = (HeapNode<T>) result.right;
        }
        return result;
    }

    /**
     * Retorna o elemento com o valor mais baixo da heap.
     *
     * @return o elemento com o valor mais baixo da heap
     * @throws EmptyCollectionException se a heap estiver vazia
     */
    @Override
    public T findMin() throws EmptyCollectionException {
        if (this.count == 0) {
            throw new EmptyCollectionException("A Heap está vazia!");
        }

        return this.root.element;
    }

}
