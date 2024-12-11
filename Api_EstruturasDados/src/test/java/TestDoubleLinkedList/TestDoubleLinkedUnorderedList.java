package TestDoubleLinkedList;

import DoubleLinkedList.DoubleLinkedUnorderedList;
import Exceptions.ElementNotFoundException;
import Exceptions.EmptyCollectionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class TestDoubleLinkedUnorderedList {

    private DoubleLinkedUnorderedList<Integer> list;

    @BeforeEach
    public void setUp() {
        list = new DoubleLinkedUnorderedList<>();
    }

    @Test
    public void testSize() {
        assertEquals(0, list.size(), "A lista deve estar vazia inicialmente.");
        list.addToFront(10);
        assertEquals(1, list.size(), "O tamanho da lista deve ser 1 após adicionar um elemento.");
        list.addToRear(20);
        assertEquals(2, list.size(), "O tamanho da lista deve ser 2 após adicionar outro elemento.");
    }

    @Test
    public void testAddToFront() {
        list.addToFront(10);
        list.addToFront(20);
        list.addToFront(30);

        assertEquals(3, list.size(), "A lista deve conter 3 elementos.");
        assertEquals("30 20 10 ", list.toString(), "Os elementos não estão na ordem correta.");
    }

    @Test
    public void testAddToRear() {
        list.addToRear(10);
        list.addToRear(20);
        list.addToRear(30);

        assertEquals(3, list.size(), "A lista deve conter 3 elementos.");
        assertEquals("10 20 30 ", list.toString(), "Os elementos não estão na ordem correta.");
    }

    @Test
    public void testAddAfter() throws ElementNotFoundException {
        list.addToFront(10);
        list.addToFront(20);
        list.addToRear(30);

        list.addAfter(25, 20);

        assertEquals(4, list.size(), "A lista deve conter 4 elementos.");
        assertEquals("20 25 10 30 ", list.toString(), "O elemento não foi inserido corretamente.");
    }

    @Test
    public void testAddAfterElementNotFound() {
        list.addToFront(10);
        list.addToRear(20);

        assertThrows(ElementNotFoundException.class, () -> list.addAfter(25, 30),
                "Deveria lançar ElementNotFoundException quando o elemento alvo não for encontrado.");
    }

    @Test
    public void testRemoveFirst() throws EmptyCollectionException {
        list.addToFront(10);
        list.addToRear(20);
        list.addToRear(30);

        int removed = list.removeFirst();

        assertEquals(10, removed, "O primeiro elemento deve ser removido.");
        assertEquals(2, list.size(), "O tamanho da lista deve ser 2 após a remoção.");
        assertEquals("20 30 ", list.toString(), "A lista não está na ordem correta.");   }

    @Test
    public void testRemoveLast() throws EmptyCollectionException {
        list.addToFront(10);
        list.addToRear(20);
        list.addToRear(30);
        int removed = list.removeLast();

        assertEquals(30, removed, "O último elemento deve ser removido.");
        assertEquals(2, list.size(), "O tamanho da lista deve ser 2 após a remoção.");
        assertEquals("10 20 ", list.toString(), "A lista não está na ordem correta.");   }

    @Test
    public void testRemoveElement() throws EmptyCollectionException, ElementNotFoundException {
        list.addToFront(10);
        list.addToRear(20);
        list.addToRear(30);

        assertEquals(20, list.remove(20), "O elemento 20 deve ser removido.");
        assertEquals(2, list.size(), "O tamanho da lista deve ser 2 após a remoção.");
        assertEquals("10 30 ", list.toString(), "A lista não está na ordem correta.");
    }

    @Test
    public void testRemoveElementNotFound() {
        list.addToFront(10);
        list.addToRear(20);

        assertThrows(ElementNotFoundException.class, () -> list.remove(30),
                "Deveria lançar ElementNotFoundException quando o elemento não for encontrado.");
    }

    @Test
    public void testIsEmpty() {
        assertTrue(list.isEmpty(), "A lista deve estar vazia inicialmente.");
        list.addToFront(10);
        assertFalse(list.isEmpty(), "A lista não deve estar vazia após adicionar um elemento.");
    }

    @Test
    public void testIterator() {
        list.addToFront(10);
        list.addToRear(20);
        list.addToRear(30);

        Iterator<Integer> iterator = list.iterator();

        assertTrue(iterator.hasNext(), "O iterador deveria ter um próximo elemento.");
        assertEquals(10, iterator.next(), "O primeiro elemento retornado pelo iterador deveria ser 10.");
        assertEquals(20, iterator.next(), "O segundo elemento retornado pelo iterador deveria ser 20.");
        assertEquals(30, iterator.next(), "O terceiro elemento retornado pelo iterador deveria ser 30.");
    }

    @Test
    public void testRemoveIterator() throws EmptyCollectionException {
        list.addToFront(10);
        list.addToRear(20);
        list.addToRear(30);

        Iterator<Integer> iterator = list.iterator();
        iterator.next();
        iterator.remove();

        assertEquals(2, list.size(), "A lista deve ter 2 elementos após a remoção.");
        assertEquals("20 30 ", list.toString(), "A lista não está na ordem correta após a remoção.");
    }

    @Test
    public void testConcurrentModificationException() {
        list.addToFront(10);
        Iterator<Integer> iterator = list.iterator();

        list.addToRear(20);

        assertThrows(ConcurrentModificationException.class, iterator::next,
                "Deveria lançar ConcurrentModificationException quando a lista é modificada durante a iteração.");
    }

    @Test
    public void testNoSuchElementException() {
        list.addToFront(10);
        Iterator<Integer> iterator = list.iterator();
        iterator.next();

        assertThrows(NoSuchElementException.class, iterator::next,
                "Deveria lançar NoSuchElementException quando não houver mais elementos.");
    }
}
