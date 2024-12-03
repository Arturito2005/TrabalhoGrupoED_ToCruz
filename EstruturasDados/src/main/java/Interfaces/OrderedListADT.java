package Interfaces;

/**
 * This interface defines the ADT for a generic ordered list. It is a
 * sub-interface of {@code ListADT<T>}, adding the specific functionality of
 * inserting elements in a defined order.
 *
 * The implementation of this interface must ensure that the elements are kept
 * in the correct order after each insertion.
 *
 * @param <T> the type of elements stored in this ordered list
 */
public interface OrderedListADT<T> extends ListADT<T> {

    /**
     * Adds the specified element to this list at the proper location
     *
     * @param element the element to be added to this list
     */
    public void add(T element);
}
