package Nodes;

public class LinearNode<T> {

    private T element;

    private LinearNode<T> next;

    public LinearNode() {
        this.element = null;
        this.next = null;
    }

    public LinearNode(T element) {
        this.element = element;
        this.next = null;
    }

    public T getElement() {
        return this.element;
    }

    public void setElement(T element) {
        this.element = element;
    }

    public LinearNode<T> getNext() {
        return this.next;
    }

    public void setNext(LinearNode<T> next) {
        this.next = next;
    }

    @Override
    public String toString() {
        return "LinearNode{" + "element=" + this.element + ", linear=" + this.next + '}';
    }
}

