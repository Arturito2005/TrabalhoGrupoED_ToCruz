package TestPriorityQueue;

import PriorityQueues.PriorityQueueNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestPriorityQueueNode {


    private PriorityQueueNode<String> highPriorityNode;
    private PriorityQueueNode<String> lowPriorityNode;
    private PriorityQueueNode<String> equalPriorityNode;

    @BeforeEach
    public void setUp() {
        highPriorityNode = new PriorityQueueNode<>("High", 10);
        lowPriorityNode = new PriorityQueueNode<>("Low", 5);
        equalPriorityNode = new PriorityQueueNode<>("Equal", 10);
    }

    @Test
    public void testCompareToHigherPriority() {
        assertEquals(1, highPriorityNode.compareTo(lowPriorityNode), "O nó com maior prioridade deve ser considerado maior.");
    }

    @Test
    public void testCompareToLowerPriority() {
        assertEquals(-1, lowPriorityNode.compareTo(highPriorityNode), "O nó com menor prioridade deve ser considerado menor.");
    }

    @Test
    public void testCompareToEqualPriorityButDifferentOrder() {
        PriorityQueueNode<String> equalPriorityOrderNode = new PriorityQueueNode<>("Equal", 10);
        assertEquals(-1, equalPriorityNode.compareTo(equalPriorityOrderNode), "O nó inserido depois deve ter uma ordem maior.");
    }
}
