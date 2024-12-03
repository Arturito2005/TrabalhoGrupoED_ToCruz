package Queue;

import Exceptions.EmptyCollectionException;
import Interfaces.QueueADT;

public class TesteArrayQueue {

    public static void main(String[] args) {
        QueueADT<String> arrayQueue = new CircularArrayQueue<>(2);

        arrayQueue.enqueue("A");
        arrayQueue.enqueue("B");
        arrayQueue.enqueue("C");
        arrayQueue.enqueue("D");
        arrayQueue.enqueue("E");
        arrayQueue.enqueue("F");

        System.out.println(arrayQueue.toString());

        try {
            System.out.println(arrayQueue.dequeue());
        } catch (EmptyCollectionException ex) {
            System.out.println(ex.getMessage());
        }

        System.out.println(arrayQueue.toString());
    }
}
