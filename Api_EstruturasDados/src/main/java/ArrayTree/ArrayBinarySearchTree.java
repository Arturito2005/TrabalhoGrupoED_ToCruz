package ArrayTree;

import ArrayList.ArrayUnorderedList;
import Exceptions.ElementNotFoundException;
import Exceptions.EmptyCollectionException;
import Interfaces.BinarySearchTreeADT;

import java.util.Iterator;

/**
 * Esta classe representa uma implementação de uma árvore binária de busca utilizando um array.
 * A árvore permite a inserção, remoção e busca de elementos de forma ordenada, mantendo a propriedade da árvore binária de busca.
 *
 * @param <T> Tipo dos elementos armazenados na árvore, que deve ser comparável com outros elementos do mesmo tipo.
 * @author Artur Pinto
 * Nº mecanográfico: 8230138
 * @author Francisco Oliveira
 * Nº mecanográfico: 8230148
 * @version 1.0
 */
public class ArrayBinarySearchTree<T> extends ArrayBinaryTree<T> implements BinarySearchTreeADT<T> {

    /** A altura da árvore, calculada com base no número de níveis da árvore. */
    protected int height;

    /** O índice máximo que contém um valor válido na árvore. */
    protected int maxIndex;

    /**
     * Constrói uma árvore binária de busca vazia.
     */
    public ArrayBinarySearchTree() {
        super();
        height = 0;
        maxIndex = -1;
    }

    /**
     * Constrói uma árvore binária de busca com o elemento inicial fornecido.
     *
     * @param element O elemento a ser inserido na árvore.
     */
    public ArrayBinarySearchTree(T element) {
        super(element);
        height = 1;
        maxIndex = 0;
    }

    /**
     * Adiciona um elemento à árvore mantendo a propriedade de ordenação.
     * Se a árvore atingir o limite de capacidade, ela será expandida.
     *
     * @param element O elemento a ser adicionado à árvore.
     */
    @Override
    public void addElement(T element) {
        if (tree.length < maxIndex * 2 + 3) {
            expandCapacity();
        }

        Comparable<T> tempelement = (Comparable<T>) element;
        if (isEmpty()) {
            tree[0] = element;
            maxIndex = 0;
        } else {
            boolean added = false;
            int currentIndex = 0;
            while (!added) {
                if (tempelement.compareTo((tree[currentIndex])) < 0) {
                    if (tree[currentIndex * 2 + 1] == null) {
                        tree[currentIndex * 2 + 1] = element;
                        added = true;
                        if (currentIndex * 2 + 1 > maxIndex) {
                            maxIndex = currentIndex * 2 + 1;
                        }
                    } else {
                        currentIndex = currentIndex * 2 + 1;
                    }
                } else {
                    if (tree[currentIndex * 2 + 2] == null) {
                        tree[currentIndex * 2 + 2] = element;
                        added = true;
                        if (currentIndex * 2 + 2 > maxIndex) {
                            maxIndex = currentIndex * 2 + 2;
                        }
                    } else {
                        currentIndex = currentIndex * 2 + 2;
                    }
                }
            }
        }

        height = (int) (Math.log(maxIndex + 1) / Math.log(2)) + 1;
        count++;
    }

    /**
     * Substitui o nó no índice alvo, ajustando os elementos da árvore conforme necessário.
     *
     * @param targetIndex O índice do nó que será substituído.
     */
    protected void replace(int targetIndex) {
        int currentIndex, oldIndex, newIndex;
        ArrayUnorderedList<Integer> oldlist = new ArrayUnorderedList<Integer>();
        ArrayUnorderedList<Integer> newlist = new ArrayUnorderedList<Integer>();
        ArrayUnorderedList<Integer> templist = new ArrayUnorderedList<Integer>();
        Iterator<Integer> oldIt, newIt;

        if ((targetIndex * 2 + 1 >= tree.length) || (targetIndex * 2 + 2 >= tree.length)) {
            tree[targetIndex] = null;
        } else if ((tree[targetIndex * 2 + 1] == null) && (tree[targetIndex * 2 + 2] == null)) {
            tree[targetIndex] = null;
        } else if ((tree[targetIndex * 2 + 1] != null) && (tree[targetIndex * 2 + 2] == null)) {
            currentIndex = targetIndex * 2 + 1;
            templist.addToRear(currentIndex);
            while (!templist.isEmpty()) {
                try {
                    currentIndex = (templist.removeFirst());
                    newlist.addToRear(currentIndex);
                    if ((currentIndex * 2 + 2) <= (Math.pow(2, height) - 2)) {
                        templist.addToRear(currentIndex * 2 + 1);
                        templist.addToRear(currentIndex * 2 + 2);
                    }
                } catch (EmptyCollectionException ex) {
                    System.out.println(ex.getMessage());
                }
            }

            currentIndex = targetIndex;
            templist.addToRear(currentIndex);
            while (!templist.isEmpty()) {
                try {
                    currentIndex = (templist.removeFirst());
                    oldlist.addToRear(currentIndex);

                    if ((currentIndex * 2 + 2) <= (Math.pow(2, height) - 2)) {
                        templist.addToRear(currentIndex * 2 + 1);
                        templist.addToRear(currentIndex * 2 + 2);
                    }
                } catch (EmptyCollectionException ex) {
                    System.out.println(ex.getMessage());
                }
            }

            oldIt = oldlist.iterator();
            newIt = newlist.iterator();
            while (newIt.hasNext()) {
                oldIndex = oldIt.next();
                newIndex = newIt.next();
                tree[oldIndex] = tree[newIndex];
                tree[newIndex] = null;
            }
        } else if ((tree[targetIndex * 2 + 1] == null) && (tree[targetIndex * 2 + 2] != null)) {
            currentIndex = targetIndex * 2 + 2;
            templist.addToRear(currentIndex);

            while (!templist.isEmpty()) {
                try {
                    currentIndex = (templist.removeFirst());
                    newlist.addToRear(currentIndex);
                    if ((currentIndex * 2 + 2) <= (Math.pow(2, height) - 2)) {
                        templist.addToRear(currentIndex * 2 + 1);
                        templist.addToRear(currentIndex * 2 + 2);
                    }
                } catch (EmptyCollectionException ex) {
                    System.out.println(ex.getMessage());
                }
            }

            currentIndex = targetIndex;
            templist.addToRear(currentIndex);
            while (!templist.isEmpty()) {
                try {
                    currentIndex = (templist.removeFirst());
                    oldlist.addToRear(currentIndex);
                    if ((currentIndex * 2 + 2) <= (Math.pow(2, height) - 2)) {
                        templist.addToRear(currentIndex * 2 + 1);
                        templist.addToRear(currentIndex * 2 + 2);
                    }
                } catch (EmptyCollectionException ex) {
                    System.out.println(ex.getMessage());
                }
            }

            oldIt = oldlist.iterator();
            newIt = newlist.iterator();
            while (newIt.hasNext()) {
                oldIndex = oldIt.next();

                newIndex = newIt.next();
                tree[oldIndex] = tree[newIndex];
                tree[newIndex] = null;
            }
        } else {
            currentIndex = targetIndex * 2 + 2;

            while (tree[currentIndex * 2 + 1] != null) {
                currentIndex = currentIndex * 2 + 1;
            }

            tree[targetIndex] = tree[currentIndex];

            int currentRoot = currentIndex;

            if (tree[currentRoot * 2 + 2] != null) {
                currentIndex = currentRoot * 2 + 2;
                templist.addToRear(currentIndex);
                while (!templist.isEmpty()) {
                    try {
                        currentIndex = (templist.removeFirst());
                        newlist.addToRear(currentIndex);

                        if ((currentIndex * 2 + 2) <= (Math.pow(2, height) - 2)) {
                            templist.addToRear(currentIndex * 2 + 1);
                            templist.addToRear(currentIndex * 2 + 2);
                        }
                    } catch (EmptyCollectionException ex) {
                        System.out.println(ex.getMessage());
                    }
                }

                currentIndex = currentRoot;
                templist.addToRear(currentIndex);
                while (!templist.isEmpty()) {
                    try {
                        currentIndex = (templist.removeFirst());
                        oldlist.addToRear(currentIndex);
                        if ((currentIndex * 2 + 2) <= (Math.pow(2, height) - 2)) {
                            templist.addToRear(currentIndex * 2 + 1);
                            templist.addToRear(currentIndex * 2 + 2);
                        }
                    } catch (EmptyCollectionException ex) {
                        System.out.println(ex.getMessage());
                    }
                }

                oldIt = oldlist.iterator();
                newIt = newlist.iterator();
                while (newIt.hasNext()) {
                    oldIndex = oldIt.next();
                    newIndex = newIt.next();

                    tree[oldIndex] = tree[newIndex];
                    tree[newIndex] = null;
                }
            } else {
                tree[currentRoot] = null;
            }
        }
    }

    /**
     * Encontra o índice de um elemento na árvore.
     *
     * @param tempElement O elemento a ser procurado.
     * @param value O valor para busca.
     * @return O índice do elemento, ou -1 se não encontrado.
     */
    private int findIndex(Comparable<T> tempElement, int value) {
        int pos = -1;

        for (int ct = 0; ct < this.maxIndex; ct++) {
            if (this.tree[ct] != null && tempElement.compareTo(this.tree[ct]) == 0) {
                pos = ct;
            }
        }

        return pos;
    }

    /**
     * Remove um elemento da árvore.
     * A árvore será reorganizada para manter a propriedade de ordenação após a remoção.
     *
     * @param targetElement O elemento a ser removido.
     * @return O elemento removido.
     * @throws ElementNotFoundException Se o elemento não for encontrado na árvore.
     */
    @Override
    public T removeElement(T targetElement) throws ElementNotFoundException {
        if (isEmpty() || targetElement == null) {
            throw new ElementNotFoundException("A lista está vazia");
        }

        Comparable<T> tempElement = (Comparable<T>) targetElement;
        int targetIndex = findIndex(tempElement, 0);

        if (targetIndex == -1) {
            throw new ElementNotFoundException("O elemento a remover não existe na lista");
        }

        T result = tree[targetIndex];
        replace(targetIndex);
        count--;

        int temp = maxIndex;
        maxIndex = -1;

        for (int i = 0; i <= temp; i++) {
            if (tree[i] != null) {
                maxIndex = i;
            }
        }

        height = (int) (Math.log(maxIndex + 1) / Math.log(2)) + 1;

        return result;
    }

    /**
     * Remove todas as ocorrências de um elemento da árvore.
     *
     * @param targetElement O elemento cujas ocorrências serão removidas.
     * @throws ElementNotFoundException Se o elemento não for encontrado.
     */
    @Override
    public void removeAllOccurences(T targetElement) throws ElementNotFoundException {
        try {
            if (removeElement(targetElement) != null) {
                removeAllOccurences(targetElement);
            }
        } catch (ElementNotFoundException ex) {
        }
    }

    /**
     * Remove o elemento mínimo da árvore.
     *
     * @return O elemento mínimo removido.
     * @throws ElementNotFoundException Se a árvore estiver vazia.
     */
    @Override
    public T removeMin() throws ElementNotFoundException {
        return this.removeElement(findMin());
    }

    /**
     * Remove o elemento máximo da árvore.
     *
     * @return O elemento máximo removido.
     * @throws ElementNotFoundException Se a árvore estiver vazia.
     */
    @Override
    public T removeMax() throws ElementNotFoundException {
        return this.removeElement(findMax());
    }

    /**
     * Encontra o elemento mínimo na árvore.
     *
     * @return O elemento mínimo encontrado.
     */
    @Override
    public T findMin() {
        T min = null;
        Comparable<T> comparableElement = (Comparable<T>) this.tree[0];

        for (int i = 0; i < this.maxIndex; i++) {
            if (tree[i] != null && comparableElement.compareTo(tree[i]) > 0) {
                comparableElement = (Comparable<T>) this.tree[i];
                min = this.tree[i];
            }
        }

        return min;
    }

    /**
     * Encontra o elemento máximo na árvore.
     *
     * @return O elemento máximo encontrado.
     */
    @Override
    public T findMax() {
        T max = null;
        Comparable<T> comparableElement = (Comparable<T>) this.tree[0];

        for (int i = 0; i < this.maxIndex; i++) {
            if (tree[i] != null && comparableElement.compareTo(tree[i]) < 0) {
                comparableElement = (Comparable<T>) this.tree[i];
                max = this.tree[i];
            }
        }

        return max;
    }
}
