package Interfaces;

import Exceptions.ElementNotFoundException;
import Exceptions.EmptyCollectionException;
import java.util.Iterator;

/**
 * This interface defines the ADT for a binary tree.
 *
 * @param <T> the type of elements stored in the binary tree
 */
public interface BinaryTreeADT<T> {

    /**
     * Returns a reference to the root element
     *
     * @return a reference to the root
     */
    public T getRoot();

    /**
     * Indica se a lista está vazia ou não
     *
     * @return true if this list contains no elements
     */
    public boolean isEmpty();

    /**
     * Returns the number of elements in this binary tree.
     *
     * @return the integer number of elements in this tree
     */
    public int size();

    /**
     * Returns true if the binary tree contains an element that matches the
     * specified element and false otherwise.
     *
     * @param targetElement the element being sought in the tree
     * @return true if the tree contains the target element
     */
    public boolean contains(T targetElement);

    /**
     * Returns a reference to the specified element if it is found in this
     * binary tree.Throws an exception if the specified element is not found.
     *
     * @param targetElement the element being sought in the tree
     * @return a reference to the specified element
     * @throws EmptyCollectionException,
     * Exceptions.EmptyCollectionException
     */
    public T find(T targetElement) throws ElementNotFoundException, EmptyCollectionException;

    /**
     * Returns the string representation of the binary tree.
     *
     * @return a string representation of the binary tree
     */
    @Override
    public String toString();

    /**
     * Performs an inorder traversal on this binary tree by calling an
     * overloaded, recursive inorder method that starts with the root.
     *
     * @return an iterator over the elements of this binary tree
     */
    public Iterator<T> iteratorInOrder();

    /**
     * Performs a preorder traversal on this binary tree by calling an
     * overloaded, recursive preorder method that starts with the root.
     *
     * @return an iterator over the elements of this binary tree
     */
    public Iterator<T> iteratorPreOrder();

    /**
     * Performs a postorder traversal on this binary tree by calling an
     * overloaded, recursive postorder method that starts with the root.
     *
     * @return an iterator over the elements of this binary tree
     */
    public Iterator<T> iteratorPostOrder();

    /**
     * Performs a levelorder traversal on the binary tree, using a queue.
     *
     * @return an iterator over the elements of this binary tree
     */
    public Iterator<T> iteratorLevelOrder();
}
