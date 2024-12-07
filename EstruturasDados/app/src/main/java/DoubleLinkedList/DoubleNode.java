package DoubleLinkedList;

public class DoubleNode<T> {

    private T element;

    private DoubleNode<T> next_ele;

    private DoubleNode<T> previous_ele;

    public DoubleNode() {
        this.element = null;
        this.next_ele = null;
        this.previous_ele = null;
    }

    public DoubleNode(T element) {
        this.element = element;
        this.next_ele = null;
        this.previous_ele = null;
    }

    public T getElement() {
        return element;
    }

    public void setElement(T element) {
        this.element = element;
    }

    public DoubleNode<T> getNext_ele() {
        return next_ele;
    }

    public void setNext_ele(DoubleNode<T> next_ele) {
        this.next_ele = next_ele;
    }

    public DoubleNode<T> getPrevious_ele() {
        return previous_ele;
    }

    public void setPrevious_ele(DoubleNode<T> previous_ele) {
        this.previous_ele = previous_ele;
    }
}
