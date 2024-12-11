package TestArrayHeaps;

import ArrayHeaps.ArrayHeap;
import Exceptions.EmptyCollectionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class TestArrayHeap {

    private ArrayHeap<Integer> heap;

    @BeforeEach
    public void setUp() {
        heap = new ArrayHeap<>();
    }

    @Test
    public void testAddElement() {
        heap.addElement(5);
        heap.addElement(3);
        heap.addElement(8);
        heap.addElement(2);
        heap.addElement(7);

        try {
            assertEquals(2, heap.findMin(), "O elemento mínimo deve ser 2");
        } catch (EmptyCollectionException e) {
            fail("A exceção não deveria ser lançada.");
        }
    }

    @Test
    public void testRemoveMin() {
        heap.addElement(5);
        heap.addElement(3);
        heap.addElement(8);
        heap.addElement(2);
        heap.addElement(7);

        try {
            Integer min = heap.removeMin();
            assertEquals(2, min, "O elemento removido deve ser o menor (2)");
            assertEquals(3, heap.findMin(), "Após remover o menor elemento, o próximo menor deve ser 3");
        } catch (EmptyCollectionException e) {
            fail("A exceção não deveria ser lançada.");
        }
    }

    @Test
    public void testRemoveMinEmptyHeap() {
        try {
            heap.removeMin();
            fail("Deveria lançar uma EmptyCollectionException, pois o heap está vazio.");
        } catch (EmptyCollectionException e) {
            assertEquals("A Heap está vazia", e.getMessage(), "A mensagem de erro deve ser 'A Heap está vazia'");
        }
    }

    @Test
    public void testFindMin() {
        heap.addElement(5);
        heap.addElement(3);
        heap.addElement(8);
        heap.addElement(2);
        heap.addElement(7);

        try {
            assertEquals(2, heap.findMin(), "O menor elemento deve ser 2");
        } catch (EmptyCollectionException e) {
            fail("A exceção não deveria ser lançada.");
        }
    }

    @Test
    public void testFindMinEmptyHeap() {
        try {
            heap.findMin();
            fail("Deveria lançar uma EmptyCollectionException, pois o heap está vazio.");
        } catch (EmptyCollectionException e) {
            assertEquals("Ainda não existem elementos na Heap", e.getMessage(), "A mensagem de erro deve ser 'Ainda não existem elementos na Heap'");
        }
    }

    @Test
    public void testAddAndRemoveMultipleElements() {
        heap.addElement(10);
        heap.addElement(15);
        heap.addElement(3);
        heap.addElement(7);

        try {
            assertEquals(3, heap.removeMin(), "O primeiro elemento removido deve ser 3");
            assertEquals(7, heap.removeMin(), "O segundo elemento removido deve ser 7");
            assertEquals(10, heap.removeMin(), "O terceiro elemento removido deve ser 10");
            assertEquals(15, heap.removeMin(), "O quarto elemento removido deve ser 15");
        } catch (EmptyCollectionException e) {
            fail("A exceção não deveria ser lançada.");
        }
    }
}
