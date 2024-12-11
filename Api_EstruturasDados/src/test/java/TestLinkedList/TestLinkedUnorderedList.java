package TestLinkedList;

import Exceptions.ElementNotFoundException;
import LinkedList.LinearLinkedUnorderedList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestLinkedUnorderedList {

    private LinearLinkedUnorderedList<Integer> list;

    @BeforeEach
    public void setUp() {
        list = new LinearLinkedUnorderedList<>();
    }

    @Test
    public void testAddToFront() {
        list.addToFront(10);
        list.addToFront(20);
        assertEquals(2, list.size());
    }

    @Test
    public void testAddToRear() {
        list.addToRear(10);
        list.addToRear(20);
        assertEquals(2, list.size());
    }

    @Test
    public void testAddAfter() throws ElementNotFoundException {
        list.addToRear(10);
        list.addToRear(20);
        list.addToRear(30);
        list.addAfter(25, 20);

        assertEquals(4, list.size());
    }

    @Test
    public void testAddAfterElementNotFound() {
        list.addToRear(10);
        list.addToRear(20);

        assertThrows(ElementNotFoundException.class, () -> {list.addAfter(30, 40);});
    }

    @Test
    public void testSize() {
        assertEquals(0, list.size());

        list.addToFront(10);
        list.addToRear(20);

        assertEquals(2, list.size());
    }

    @Test
    public void testIsEmpty() {
        assertTrue(list.isEmpty());

        list.addToFront(10);

        assertFalse(list.isEmpty());
    }

    @Test
    public void testRemoveElement() throws ElementNotFoundException {
        list.addToFront(10);
        list.addToRear(20);
        list.remove(10);

        assertEquals(1, list.size());
    }
}
