package TestDoubleLinkedList;

import DoubleLinkedList.DoublyLinkedOrderedList;
import Exceptions.ElementNotFoundException;
import Exceptions.EmptyCollectionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestDoubleOrderedList {
    private DoublyLinkedOrderedList<Integer> orderedList;

    @BeforeEach
    public void setUp() {
        orderedList = new DoublyLinkedOrderedList<>();
    }

    @Test
    public void testAddElementToOrderedList() {
        orderedList.add(10);
        orderedList.add(20);
        orderedList.add(15);

        // Verificar a ordem correta
        assertEquals(3, orderedList.size(), "A lista deve ter 3 elementos.");
        assertEquals("10 15 20 ", orderedList.toString(), "A lista não está na ordem correta.");
    }

    @Test
    public void testAddElementToEmptyList() {
        orderedList.add(10);

        // Verificar que o único elemento é o 10
        assertEquals(1, orderedList.size(), "A lista deve ter 1 elemento.");
        assertEquals("10 ", orderedList.toString(), "A lista não está correta.");
    }

    @Test
    public void testAddMultipleElementsInOrder() {
        orderedList.add(30);
        orderedList.add(10);
        orderedList.add(20);

        // A lista deve ficar ordenada
        assertEquals(3, orderedList.size(), "A lista deve ter 3 elementos.");
        assertEquals("10 20 30 ", orderedList.toString(), "A lista não está na ordem correta.");
    }

/*
*     @Test
    public void testInverseList() throws EmptyCollectionException {
        orderedList.add(10);
        orderedList.add(20);
        orderedList.add(30);

        DoublyLinkedOrderedList<Integer> invertedList = orderedList.inverse();

        // Verificar a ordem invertida
        assertEquals(3, invertedList.size(), "A lista invertida deve ter 3 elementos.");
        assertEquals("30 20 10 ", invertedList.toString(), "A lista invertida não está correta.");
    }
* */

    @Test
    public void testInverseEmptyList() {
        assertThrows(EmptyCollectionException.class, () -> {
            orderedList.inverse();
        }, "Deve lançar exceção quando tentar inverter uma lista vazia.");
    }

    @Test
    public void testAddElementWhenListIsFull() {
        // Teste em uma lista que já contém elementos
        orderedList.add(50);
        orderedList.add(40);
        orderedList.add(60);

        assertEquals(3, orderedList.size(), "A lista deve ter 3 elementos.");
        assertEquals("40 50 60 ", orderedList.toString(), "A lista não está na ordem correta.");
    }

    @Test
    public void testRemoveFirstElement() throws EmptyCollectionException {
        orderedList.add(10);
        orderedList.add(20);
        orderedList.add(15);

        assertEquals(10, orderedList.removeFirst(), "O primeiro elemento deve ser removido.");
        assertEquals(2, orderedList.size(), "A lista deve ter 2 elementos após a remoção.");
        assertEquals("15 20 ", orderedList.toString(), "A lista não está na ordem correta após a remoção.");
    }

    @Test
    public void testRemoveLastElement() throws EmptyCollectionException {
        orderedList.add(10);
        orderedList.add(20);
        orderedList.add(15);

        assertEquals(20, orderedList.removeLast(), "O último elemento deve ser removido.");
        assertEquals(2, orderedList.size(), "A lista deve ter 2 elementos após a remoção.");
        assertEquals("10 15 ", orderedList.toString(), "A lista não está na ordem correta após a remoção.");
    }

    @Test
    public void testRemoveElement() throws EmptyCollectionException {
        orderedList.add(10);
        orderedList.add(20);
        orderedList.add(15);

        assertEquals(15, orderedList.remove(15), "O elemento 15 deve ser removido.");
        assertEquals(2, orderedList.size(), "A lista deve ter 2 elementos após a remoção.");
        assertEquals("10 20 ", orderedList.toString(), "A lista não está na ordem correta após a remoção.");
    }

    @Test
    public void testRemoveElementNotFound() {
        orderedList.add(10);
        orderedList.add(20);

        assertThrows(ElementNotFoundException.class, () -> {
            orderedList.remove(30);
        }, "Deve lançar uma exceção quando tentar remover um elemento não encontrado.");
    }

    @Test
    public void testIsEmpty() {
        assertTrue(orderedList.isEmpty(), "A lista deve estar vazia.");
        orderedList.add(10);
        assertFalse(orderedList.isEmpty(), "A lista não deve estar vazia.");
    }
}
