package TestQueue;

import Exceptions.EmptyCollectionException;
import Interfaces.Queue.QueueADT;
import Queue.CircularArrayQueue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestCircularArrayQueue {

    private QueueADT<String> circularQueue;

    @BeforeEach
    public void setUp() {
        this.circularQueue = new CircularArrayQueue<>();
    }

    @Test
    public void testeDequeueDaCircularQueue() {
        String excepted = "Dequeue";
        this.circularQueue.enqueue("Dequeue");
        assertEquals(excepted, this.circularQueue.dequeue(), "Como a queue não estava vazia foi feito o dequeue do elemento que ela possuia, nesta caso Dequeue");
    }

    @Test
    public void testDequeueEmptyCircularQueueException() {
        Exception excecao = assertThrows(EmptyCollectionException.class, () -> circularQueue.dequeue());
    }

    @Test
    public void testeFirstCircularQueue() {
        String excepted = "Dequeue";
        this.circularQueue.enqueue("Dequeue");
        assertEquals(excepted, this.circularQueue.first(), "Como a queue não estava vazia foi feito o first do elemento que ela possuia, nesta caso Dequeue");
    }

    @Test
    public void testFirstEmptyCircularQueueException() {
        Exception excecao = assertThrows(EmptyCollectionException.class, () -> circularQueue.first());
    }
}
