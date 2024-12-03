package ArrayHeaps;

import ArrayTree.ArrayBinarySearchTree;
import Exceptions.EmptyCollectionException;
import Interfaces.HeapADT;

/**
 * ArrayHeap provides an array implementation of a minheap.
 *
 * @param <T>
 */
public class ArrayHeap<T> extends ArrayBinarySearchTree<T> implements HeapADT<T> {

    public ArrayHeap() {
        super();
    }

    /**
     * Adds the specified element to this heap in the appropriate position
     * according to its key value. Note that equal elements are added to the
     * right.
     *
     * @param obj the element to be added to this heap
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
     * Reorders this heap to maintain the ordering property after adding a node.
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
     * Remove the element with the lowest value in this heap and returns a
     * reference to it. Throws an EmptyCollectionException if the heap is empty.
     *
     * @return a reference to the element with the lowest value in this head
     * @throws EmptyCollectionException if an empty collection exception occurs
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
     * Reorders this heap to maintain the ordering property.
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
     * Returns a reference to the element with the lowest value in this heap.
     *
     * @return a reference to the element with the lowest value in this heap
     */
    @Override
    public T findMin() throws EmptyCollectionException {
        if (this.count == 0) {
            throw new EmptyCollectionException("Ainda não existem elementos na Heap");
        }

        return tree[0];
    }
}
