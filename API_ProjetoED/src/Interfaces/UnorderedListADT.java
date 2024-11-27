package Interfaces;

import Exceptions.ElementNotFoundException;

/**
 * This interface defines the ADT (Abstract Data Type) for a generic unordered
 * list. It is a sub-interface of {@code ListADT<T>} and provides methods to add
 * elements to the front, rear, or after a specific target element in the list.
 *
 * @param <T> the type of elements stored in this unordered list
 */
public interface UnorderedListADT<T> extends ListADT<T> {

    /**
     * Adds the specified element to the front of this list.
     *
     * @param element the element to be added to the front of this list
     */
    public void addToFront(T element);

    /**
     * Adds the specified element to the rear of this list.
     *
     * @param element the element to be added to the rear of this list
     */
    public void addToRear(T element);

    /**
     * Adds the specified element after the specified target.
     *
     * @param element the element to be added after the target
     * @param target the target is the item that the element will be added after
     * @throws Exceptions.ElementNotFoundException
     */
    public void addAfter(T element, T target) throws ElementNotFoundException;
}
