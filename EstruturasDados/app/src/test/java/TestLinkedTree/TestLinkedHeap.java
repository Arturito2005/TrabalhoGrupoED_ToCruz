package TestLinkedTree;

import static org.junit.jupiter.api.Assertions.*;

import LinkedTree.LinkedHeap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import Exceptions.EmptyCollectionException;

public class TestLinkedHeap {

    private LinkedHeap<Integer> heap;

    @BeforeEach
    public void setUp() {
        heap = new LinkedHeap<>();
    }

    @Test
    public void testAddElement() {
        heap.addElement(5);
        heap.addElement(3);
        heap.addElement(7);

        assertEquals(Integer.valueOf(3), heap.findMin(), "O menor valor deve ser 3.");
    }

    @Test
    public void testRemoveMin() throws EmptyCollectionException {
        heap.addElement(5);
        heap.addElement(3);
        heap.addElement(7);

        Integer removed = heap.removeMin();

        assertEquals(Integer.valueOf(3), removed, "O valor removido deve ser 3.");
        assertEquals(Integer.valueOf(5), heap.findMin(), "O novo menor valor deve ser 5.");
    }

    @Test
    public void testRemoveMinWhenEmpty() {
        assertThrows(EmptyCollectionException.class, () -> {
            heap.removeMin();
        }, "Deve lançar uma exceção quando tentar remover de uma heap vazia.");
    }

    @Test
    public void testFindMin() throws EmptyCollectionException {
        heap.addElement(10);
        heap.addElement(20);
        heap.addElement(5);

        assertEquals(Integer.valueOf(5), heap.findMin(), "O menor valor deve ser 5.");
    }

    @Test
    public void testFindMinWhenEmpty() {
        assertThrows(EmptyCollectionException.class, () -> {
            heap.findMin();
        }, "Deve lançar uma exceção quando tentar acessar o mínimo em uma heap vazia.");
    }

    @Test
    public void testHeapifyAdd() {
        heap.addElement(10);
        heap.addElement(5);
        heap.addElement(3);
        heap.addElement(8);
        heap.addElement(15);

        assertEquals(Integer.valueOf(3), heap.findMin(), "O menor valor deve ser 3 após várias inserções.");
    }

    @Test
    public void testHeapifyRemove() throws EmptyCollectionException {
        heap.addElement(10);
        heap.addElement(5);
        heap.addElement(3);
        heap.addElement(8);
        heap.addElement(15);

        heap.removeMin();  // Remove o 3
        assertEquals(Integer.valueOf(5), heap.findMin(), "O novo menor valor após a remoção deve ser 5.");
    }

    @Test
    public void testRemoveMinMultipleElements() throws EmptyCollectionException {
        heap.addElement(10);
        heap.addElement(20);
        heap.addElement(5);
        heap.addElement(3);
        heap.addElement(15);

        heap.removeMin();  // Remove o 3
        heap.removeMin();  // Remove o 5

        assertEquals(Integer.valueOf(10), heap.findMin(), "O novo menor valor após a remoção deve ser 10.");
    }

    @Test
    public void testAddElementWhenEmpty() {
        heap.addElement(5);
        assertEquals(Integer.valueOf(5), heap.findMin(), "O valor único adicionado deve ser o mínimo.");
    }
}
