package PriorityQueues;

import ArrayHeaps.ArrayHeap;
import Exceptions.EmptyCollectionException;

/**
 * PriorityQueue demonstrates a priority queue using a Heap.
 *
 * @param <T>
 */
public class PriorityQueue<T> extends ArrayHeap<PriorityQueueNode<T>> {

    /**
     * Creates an empty priority queue.
     */
    public PriorityQueue() {
        super();
    }

    /**
     * Adds the given element to this PriorityQueue.
     *
     * @param object the element to be added to the priority queue
     * @param priority the integer priority of the element to be added
     */
    public void addElement(T object, int priority) {
        PriorityQueueNode<T> node = new PriorityQueueNode<> (object, priority);
        super.addElement(node);
    }

    /**
     * Removes the next highest priority element from this priority queue and
     * returns a reference to it.
     *
     * @return a reference to the next highest priority element in this queue
     * @throws EmptyCollectionException
     */
    public T removeNext() throws EmptyCollectionException {
        if(this.count == 0) {
            throw new EmptyCollectionException("Não é possível remover, porque a heap está vazia");
        }
            
        PriorityQueueNode<T> temp = (PriorityQueueNode<T>) super.removeMin();
        return temp.getElement();
    }
}
