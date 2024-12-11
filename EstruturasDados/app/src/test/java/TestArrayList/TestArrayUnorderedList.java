package TestArrayList;

import ArrayList.ArrayUnorderedList;
import Exceptions.ElementNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestArrayUnorderedList {

    private ArrayUnorderedList<Integer> list;

    @BeforeEach
    public void setUp() {
        list = new ArrayUnorderedList<Integer>();
    }

    @Test
    public void testAddToFront() {
        list.addToFront(10);
        list.addToFront(20);
        list.addToFront(30);

        assertEquals(3, list.size());
        assertEquals("30 20 10 ", list.toString());
    }

    @Test
    public void testAddToRear() {
        list.addToRear(10);
        list.addToRear(20);
        list.addToRear(30);

        assertEquals(3, list.size());
        assertEquals("10 20 30 ", list.toString());
    }

    @Test
    public void testAddAfterElementFound() throws ElementNotFoundException {
        list.addToRear(10);
        list.addToRear(20);
        list.addToRear(30);

        list.addAfter(25, 20);

        assertEquals(4, list.size());
        assertEquals("10 20 25 30 ", list.toString());
    }

    @Test
    public void testAddAfterElementNotFound() {
        list.addToRear(10);
        list.addToRear(20);
        list.addToRear(30);

        assertThrows(ElementNotFoundException.class, () -> {list.addAfter(25, 40);});
    }

    @Test
    public void testSize() {
        assertEquals(0, list.size());

        list.addToRear(10);
        list.addToRear(20);

        assertEquals(2, list.size());
    }

    @Test
    public void testIsEmpty() {
        assertTrue(list.isEmpty());

        list.addToRear(10);

        assertFalse(list.isEmpty());
    }

    @Test
    public void testContains() {
        list.addToRear(10);
        list.addToRear(20);

        assertTrue(list.contains(10));
        assertFalse(list.contains(30));
    }

    @Test
    public void testRemoveElement() throws ElementNotFoundException {
        list.addToRear(10);
        list.addToRear(20);
        list.addToRear(30);
        list.remove((Integer) 20);

        assertEquals(2, list.size());
        assertEquals("10 30 ", list.toString());
    }

    @Test
    public void testExpandArray() {
        for (int i = 0; i < 100; i++) {
            list.addToRear(i);
        }

        assertEquals(100, list.size());
    }
}
