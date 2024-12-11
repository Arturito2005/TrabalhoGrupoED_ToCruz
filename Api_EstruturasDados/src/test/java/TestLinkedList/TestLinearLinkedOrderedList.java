package TestLinkedList;

import Exceptions.ElementNotFoundException;
import Exceptions.EmptyCollectionException;
import LinkedList.LinearLinkedOrderedList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestLinearLinkedOrderedList {
    private LinearLinkedOrderedList<Integer> orderedList;

    @BeforeEach
    void setUp() {
        orderedList = new LinearLinkedOrderedList<>();
    }

    @Test
    void testAddElementEmptyList() {
        orderedList.add(10);
        assertEquals(1, orderedList.size(), "A lista deve conter 1 elemento");
        assertEquals(10, orderedList.first(), "O primeiro elemento deve ser 10");
        assertEquals(10, orderedList.last(), "O último elemento deve ser 10");
    }

    @Test
    void testAddElementToSortedList() {
        orderedList.add(10);
        orderedList.add(20);
        orderedList.add(15);

        assertEquals(3, orderedList.size(), "A lista deve conter 3 elementos");
        assertEquals(10, orderedList.first(), "O primeiro elemento deve ser 10");
        assertEquals(20, orderedList.last(), "O último elemento deve ser 20");

        String expectedOrder = "10 15 20 ";
        assertEquals(expectedOrder, orderedList.toString(), "A lista deve estar ordenada");
    }

    @Test
    void testAddDuplicateElement() {
        orderedList.add(10);
        orderedList.add(10);
        orderedList.add(5);

        assertEquals(3, orderedList.size(), "A lista deve conter 3 elementos");
        assertEquals(5, orderedList.first(), "O primeiro elemento deve ser 5");
        assertEquals(10, orderedList.last(), "O último elemento deve ser 10");

        String expectedOrder = "5 10 10 ";
        assertEquals(expectedOrder, orderedList.toString(), "A lista deve estar ordenada");
    }

    @Test
    void testRemoveFirstElement() throws EmptyCollectionException {
        orderedList.add(10);
        orderedList.add(20);
        orderedList.add(15);

        Integer removedElement = orderedList.removeFirst();
        assertEquals(10, removedElement, "O elemento removido deve ser 10");
        assertEquals(2, orderedList.size(), "A lista deve conter 2 elementos após a remoção");
        assertEquals(15, orderedList.first(), "O primeiro elemento deve ser 15");
    }

    @Test
    void testRemoveLastElement() throws EmptyCollectionException {
        orderedList.add(10);
        orderedList.add(20);
        orderedList.add(15);

        Integer removedElement = orderedList.removeLast();
        assertEquals(20, removedElement, "O elemento removido deve ser 20");
        assertEquals(2, orderedList.size(), "A lista deve conter 2 elementos após a remoção");
        assertEquals(15, orderedList.last(), "O último elemento deve ser 15");
    }

    @Test
    void testContainsElement() {
        orderedList.add(10);
        orderedList.add(20);

        assertTrue(orderedList.contains(10), "A lista deve conter o elemento 10");
        assertFalse(orderedList.contains(5), "A lista não deve conter o elemento 5");
    }

    @Test
    void testIsEmpty() {
        assertTrue(orderedList.isEmpty(), "A lista deve estar vazia inicialmente");

        orderedList.add(10);
        assertFalse(orderedList.isEmpty(), "A lista não deve estar vazia após a adição de elementos");
    }

    @Test
    void testSize() {
        assertEquals(0, orderedList.size(), "A lista deve ter tamanho 0 inicialmente");

        orderedList.add(10);
        orderedList.add(5);
        orderedList.add(15);

        assertEquals(3, orderedList.size(), "A lista deve ter tamanho 3 após a adição de 3 elementos");
    }

    @Test
    void testRemoveElement() {
        LinearLinkedOrderedList<Integer> list2 = new LinearLinkedOrderedList<>();
        list2.add(10);
        list2.add(20);
        list2.add(30);

        assertThrows(ElementNotFoundException.class, () -> {list2.remove(40);});
    }

    @Test
    void testRemoveElementNotFound() {
        orderedList.add(10);
        orderedList.add(20);
        orderedList.add(30);

        assertThrows(ElementNotFoundException.class, () -> {orderedList.remove(40);});
    }
}
