package TestQueue;

import Exceptions.EmptyCollectionException;
import Interfaces.QueueADT;
import Queue.LinkedQueue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestLinkedQueue {

    private QueueADT<String> linkedQueue;

    @BeforeEach
    public void setUp() {
        this.linkedQueue = new LinkedQueue<>();
    }

    @Test
    public void testeDequeueDaQueue() {
        String excepted = "Dequeue";
        this.linkedQueue.enqueue("Dequeue");
        assertEquals(excepted, this.linkedQueue.dequeue(), "Como a queue não estava vazia foi feito o dequeue do elemento que ela possuia, nesta caso Dequeue");
    }

    @Test
    public void testDequeueEmptylinkedQueueException() {
        Exception excecao = assertThrows(EmptyCollectionException.class, () -> linkedQueue.dequeue());
    }

    @Test
    public void testeFirstLinkedQueue() {
        String excepted = "Dequeue";
        this.linkedQueue.enqueue("Dequeue");
        assertEquals(excepted, this.linkedQueue.first(), "Como a queue não estava vazia foi feito o first do elemento que ela possuia, nesta caso Dequeue");
    }

    @Test
    public void testFirstEmptylinkedQueueException() {
        Exception excecao = assertThrows(EmptyCollectionException.class, () -> linkedQueue.first());
    }
}
