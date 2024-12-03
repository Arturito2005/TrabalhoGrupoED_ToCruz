package LinkedList;

import Exceptions.ElementNotFoundException;
import Interfaces.UnorderedListADT;
import Nodes.LinearNode;

public class LinearLinkedUnorderedList<T> extends LinearLinkedList<T> implements UnorderedListADT<T> {

    public LinearLinkedUnorderedList() {
        super();
    }

    @Override
    public void addToFront(T element) {
        LinearNode<T> new_node = new LinearNode<>(element);

        if (this.head == null) {
            this.tail = new_node;
        } else {
            new_node.setNext(this.head);
        }

        this.head = new_node;

        this.count++;
        this.modCount++;
    }

    @Override
    public void addToRear(T element) {
        LinearNode<T> new_node = new LinearNode<>(element);

        if (this.tail == null) {
            this.head = new_node;
        } else {
            this.tail.setNext(new_node);
        }

        this.tail = new_node;
        this.count++;
        this.modCount++;
    }

    @Override
    public void addAfter(T element, T target) throws ElementNotFoundException {
        LinearNode<T> current = this.head;
        boolean find = false;
        int pos_find = this.count;

        while (pos_find > 0 && find == false) {
            if (current.getElement().equals(target)) {
                find = true;
            } else {
                current = current.getNext();
                pos_find--;
            }
        }

        if (!find) {
            throw new ElementNotFoundException("O target introduzido (" + target + ") não pertence há lista!");
        }

        LinearNode<T> new_node = new LinearNode<>(element);

        if (this.head == null) {
            this.head = new_node;
            this.tail = new_node;
        } else if (target.equals(this.head.getElement()) && pos_find == this.count) {
            new_node.setNext(this.head);
            this.head = new_node;
        } else if (target.equals(this.tail.getElement()) && pos_find == 1) {
            this.tail.setNext(new_node);
            this.tail = new_node;
        } else {
            new_node.setNext(current.getNext());
            current.setNext(new_node);
        }

        this.count++;
        this.modCount++;
    }
}
