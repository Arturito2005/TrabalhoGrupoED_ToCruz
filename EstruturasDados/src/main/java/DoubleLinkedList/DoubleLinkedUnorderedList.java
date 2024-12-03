package DoubleLinkedList;

import Exceptions.ElementNotFoundException;
import Interfaces.UnorderedListADT;

public class DoubleLinkedUnorderedList<T> extends DoublyLinkedList<T> implements UnorderedListADT<T> {

    public DoubleLinkedUnorderedList() {
        super();
    }

    @Override
    public void addToFront(T element) {
        DoubleNode<T> new_node = new DoubleNode<>(element);

        if (this.head == null) {
            this.tail = new_node;
        } else {
            new_node.setNext_ele(this.head);
            this.head.setPrevious_ele(new_node);
        }

        this.head = new_node;
        this.count++;
        this.modCount++;
    }

    @Override
    public void addToRear(T element) {
        DoubleNode<T> new_node = new DoubleNode<>(element);

        if (this.tail == null) {
            this.head = new_node;
        } else {
            new_node.setPrevious_ele(this.tail);
            this.tail.setNext_ele(new_node);
        }

        this.tail = new_node;
        this.count++;
        this.modCount++;
    }

    @Override
    public void addAfter(T element, T target) throws ElementNotFoundException {
        DoubleNode<T> current = this.head;
        boolean find = false;

        while (current != null && find == false) {
            if (current.getElement().equals(target)) {
                find = true;
            } else {
                current = current.getNext_ele();
            }
        }

        if (!find) {
            throw new ElementNotFoundException("O target introduzido (" + target + ") não pertence há lista!");
        }

        DoubleNode<T> new_node = new DoubleNode<>(element);

        new_node.setNext_ele(current.getNext_ele());
        new_node.setPrevious_ele(current);

        current.setNext_ele(new_node);

        this.count++;
        this.modCount++;
    }
}
