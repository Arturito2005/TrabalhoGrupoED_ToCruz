package TestArrayList;

import ArrayList.ArrayOrderedList;
import Exceptions.EmptyCollectionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestArrayorderedList {

    private ArrayOrderedList<Integer> list;

    @BeforeEach
    public void setUp() {
        list = new ArrayOrderedList<Integer>();
    }

    @Test
    public void testAdd() {
        list.add(10);
        list.add(5);
        list.add(20);

        assertEquals(3, list.size());
        assertEquals("5 10 20 ", list.toString());
    }

    @Test
    public void testAddMultipleElements() {
        list.add(30);
        list.add(10);
        list.add(20);
        list.add(5);

        assertEquals(4, list.size());
        assertEquals("5 10 20 30 ", list.toString());
    }

    @Test
    public void testAddElementAtBeginning() {
        list.add(10);
        list.add(5);

        assertEquals(2, list.size());
        assertEquals("5 10 ", list.toString());
    }

    @Test
    public void testAddElementAtEnd() {
        list.add(10);
        list.add(20);

        assertEquals(2, list.size());
        assertEquals("10 20 ", list.toString());
    }

    @Test
    public void testAddElementInMiddle() {
        list.add(10);
        list.add(30);
        list.add(20);

        assertEquals(3, list.size());
        assertEquals("10 20 30 ", list.toString());
    }

    @Test
    public void testSize() {
        assertEquals(0, list.size());

        list.add(10);
        list.add(20);

        assertEquals(2, list.size());
    }

    @Test
    public void testIsEmpty() {
        assertTrue(list.isEmpty());
        list.add(10);

        assertFalse(list.isEmpty());
    }

    @Test
    public void testRemoveFirst() throws EmptyCollectionException {
        list.add(10);
        list.add(20);
        list.add(30);
        list.removeFirst();

        assertEquals(2, list.size());
        assertEquals("20 30 ", list.toString());
    }

    @Test
    public void testRemoveLast() throws EmptyCollectionException {
        list.add(10);
        list.add(20);
        list.add(30);

        list.removeLast();

        assertEquals(2, list.size());
        assertEquals("10 20 ", list.toString());
    }

    @Test
    public void testRemoveElement() throws EmptyCollectionException {
        // Adiciona elementos à lista
        list.add(10);
        list.add(20);
        list.add(30);

        list.remove((Integer) 20);

        assertEquals(2, list.size());
        assertEquals("10 30 ", list.toString());
    }
}
