package LinkedList;

import Interfaces.OrderedListADT;
import Nodes.LinearNode;

public class LinearLinkedOrderedList<T> extends LinearLinkedList<T> implements OrderedListADT<T> {

    public LinearLinkedOrderedList() {
        super();
    }

    @Override
    public void add(T element) {
        Comparable<T> temp = (Comparable<T>) element;
        LinearNode<T> new_node = new LinearNode<>(element);

        if (this.head == null) {
            this.tail = new_node;
            this.head = new_node;
        } else {
            LinearNode<T> current_node = this.head;
            LinearNode<T> previous_node = null;

            while (current_node != null && temp.compareTo(current_node.getElement()) > 0) {
                previous_node = current_node;
                current_node = current_node.getNext();
            }

            if (current_node == this.head) {
                new_node.setNext(this.head);
                this.head = new_node;
            }  else if (current_node == null) {
                previous_node.setNext(new_node);
                this.tail = new_node;
            } else {
                previous_node.setNext(new_node);
                new_node.setNext(current_node);
            }
        }

        this.count++;
        this.modCount++;
    }
}
