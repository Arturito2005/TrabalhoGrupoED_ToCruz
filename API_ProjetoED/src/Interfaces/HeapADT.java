package Interfaces;

import Exceptions.EmptyCollectionException;

/**
 * The HeapADT interface represents the Heap data structure, which extends a binary tree.
 *
 * @param <T> the type of elements stored in this heap, which must be comparable to determine the ordering.
 */
public interface HeapADT<T> extends BinaryTreeADT<T> {

    /**
     * Adds the specified object to this heap.
     *
     * @param obj the element to added to this head
     */
    public void addElement(T obj);

    /**
     * Removes element with the lowest value from this heap.
     *
     * @return the element with the lowest value from this heap
     * @throws Exceptions.EmptyCollectionException
     */
    public T removeMin() throws EmptyCollectionException;

    /**
     * Returns a reference to the element with the lowest value in this heap.
     *
     * @return a reference to the element with the lowest value in this heap
     */
    public T findMin();
}
