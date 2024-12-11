package TestPriorityQueue;

import Exceptions.EmptyCollectionException;
import PriorityQueues.PriorityQueue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestPriorityQueue {

    private PriorityQueue<String> priorityQueue;

    @BeforeEach
    public void setUp() {
        this.priorityQueue = new PriorityQueue<>();
    }

    @Test
    public void testRemoveNextQueue() {
        String exception = "String";
        this.priorityQueue.addElement("String", 2);
        assertEquals(exception, priorityQueue.removeNext(), "Como a priorityQueue não está vazia é possível remover o proximo elemento");
    }

    @Test
    public void testRemoveNextEmptyExpceptionQueue() {
        String except = "Não é possível remover, porque a heap está vazia";
        Exception exception = assertThrows(EmptyCollectionException.class, () -> priorityQueue.removeNext());
        assertEquals(except, exception.getMessage());
    }
}
