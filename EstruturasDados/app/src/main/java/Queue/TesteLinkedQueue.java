package Queue;

import Exceptions.EmptyCollectionException;
import Interfaces.QueueADT;

public class TesteLinkedQueue {

    public static void main(String[] args) {
        QueueADT<String> linkedqueue = new LinkedQueue<>();

        linkedqueue.enqueue("a");
        linkedqueue.enqueue("b");
        linkedqueue.enqueue("c");
        linkedqueue.enqueue("d");
        linkedqueue.enqueue("e");
        linkedqueue.enqueue("f");

        System.out.println(linkedqueue.toString());

        try {
            System.out.println(linkedqueue.dequeue());
        } catch (EmptyCollectionException ex) {
            System.out.println(ex.getMessage());
        }

        try {
            System.out.println(linkedqueue.first());
        } catch (EmptyCollectionException ex) {
            System.out.println(ex.getMessage());
        }

        try {
            System.out.println(linkedqueue.dequeue());
        } catch (EmptyCollectionException ex) {
            System.out.println(ex.getMessage());
        }

        System.out.println(linkedqueue.toString());
        System.out.println(linkedqueue.size());
        System.out.println(linkedqueue.isEmpty());
    }
}
