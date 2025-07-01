package ArrayHeaps;

import ArrayTree.ArrayBinarySearchTree;
import Exceptions.EmptyCollectionException;
import Interfaces.Heap.HeapADT;

/**
 * ArrayHeap fornece uma implementação de heap mínima, usa um array.
 *
 * @param <T> t
 * @author Artur Pinto
 * Nº mecanográfico: 8230138
 * @author Francisco Oliveira
 * Nº mecanográfico: 8230148
 * @version 1.0
 */
public class ArrayHeap<T> extends ArrayBinarySearchTree<T> implements HeapADT<T> {

    /**
     * Construtor genérico da ArrayHeap
     * */
    public ArrayHeap() {
        super();
    }

    /**
     * Adiciona o elemento especificado a esta heap na posição apropriada
     * de acordo com o seu valor-chave. Note-se que elementos iguais são adicionados à direita.
     *
     * @param obj o elemento a ser adicionado a esta heap
     */
    @Override
    public void addElement(T obj) {
        if (count == tree.length) {
            expandCapacity();
        }

        tree[count] = obj;
        count++;

        if (count > 1) {
            heapifyAdd();
        }
    }

    /**
     * Reordena esta heap para manter a propriedade de ordenação após adicionar um nó.
     */
    private void heapifyAdd() {
        T temp;
        int next = count - 1;
        temp = tree[next];

        while ((next != 0) && (((Comparable) temp).compareTo(tree[(next - 1) / 2]) < 0)) {
            int pos_pai = (next - 1) / 2;
            tree[next] = tree[pos_pai];
            next = (next - 1) / 2;
        }

        tree[next] = temp;
    }

    /**
     * Remove o elemento com o valor mais baixo desta heap e retorna uma
     * referência para ele. Lança uma EmptyCollectionException se a heap estiver vazia.
     *
     * @return uma referência para o elemento com o valor mais baixo desta heap
     * @throws EmptyCollectionException se uma exceção de coleção vazia ocorrer
     */
    @Override
    public T removeMin() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("A Heap está vazia");
        }

        T minElement = tree[0];
        tree[0] = tree[count - 1];
        tree[count - 1] = null;
        heapifyRemove();
        count--;

        return minElement;
    }

    /**
     * Reordena esta heap para manter a propriedade de ordenação.
     */
    private void heapifyRemove() {
        T temp;
        int node = 0;
        int left = 1;
        int right = 2;
        int next;

        if ((tree[left] == null) && (tree[right] == null)) {
            next = count;
        } else if (tree[left] == null) {
            next = right;
        } else if (tree[right] == null) {
            next = left;
        } else if (((Comparable) tree[left]).compareTo(tree[right]) < 0) {
            next = left;
        } else {
            next = right;
        }

        temp = tree[node];

        while ((next < count) && (((Comparable) tree[next]).compareTo(temp) < 0)) {
            tree[node] = tree[next];
            node = next;
            left = (2 * node + 1);
            right = (2 * (node + 1));

            if ((tree[left] == null) && (tree[right] == null)) {
                next = count;
            } else if (tree[left] == null) {
                next = right;
            } else if (tree[right] == null) {
                next = left;
            } else if (((Comparable) tree[left]).compareTo(tree[right]) < 0) {
                next = left;
            } else {
                next = right;
            }
        }

        tree[node] = temp;
    }

    /**
     * Retorna uma referência para o elemento com o valor mais baixo desta heap.
     *
     * @return uma referência para o elemento com o valor mais baixo desta heap
     */
    @Override
    public T findMin() throws EmptyCollectionException {
        if (this.count == 0) {
            throw new EmptyCollectionException("Ainda não existem elementos na Heap");
        }

        return tree[0];
    }
}
