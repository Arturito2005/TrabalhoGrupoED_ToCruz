package TestLinkedTree;

import Exceptions.ElementNotFoundException;
import LinkedTree.LinkedBinarySearchTree;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestLinkedBinarySearchTree {

    private LinkedBinarySearchTree<Integer> tree;

    @BeforeEach
    void setUp() {
        tree = new LinkedBinarySearchTree<>();
    }

    @Test
    void testAddElement() {
        tree.addElement(10);
        tree.addElement(5);
        tree.addElement(15);

        assertEquals(3, tree.size());
        assertTrue(tree.contains(10));
        assertTrue(tree.contains(5));
        assertTrue(tree.contains(15));
    }

    @Test
    void testRemoveElement() throws ElementNotFoundException {
        tree.addElement(10);
        tree.addElement(5);
        tree.addElement(15);

        Integer removedElement = tree.removeElement(10);

        assertEquals(10, removedElement);
        assertFalse(tree.contains(10));
        assertEquals(2, tree.size());
    }

    @Test
    void testRemoveMin() throws ElementNotFoundException {
        tree.addElement(10);
        tree.addElement(5);
        tree.addElement(15);

        Integer min = tree.removeMin();

        assertEquals(5, min);
        assertFalse(tree.contains(5));
        assertEquals(2, tree.size());
    }

    @Test
    void testRemoveMax() throws ElementNotFoundException {
        tree.addElement(10);
        tree.addElement(5);
        tree.addElement(15);

        Integer max = tree.removeMax();

        assertEquals(15, max);
        assertFalse(tree.contains(15));
        assertEquals(2, tree.size());
    }

    @Test
    void testFindMin() {
        tree.addElement(10);
        tree.addElement(5);
        tree.addElement(15);

        assertEquals(5, tree.findMin());
    }

    @Test
    void testFindMax() {
        tree.addElement(10);
        tree.addElement(5);
        tree.addElement(15);

        assertEquals(15, tree.findMax());
    }

    @Test
    void testFindElement() throws ElementNotFoundException {
        tree.addElement(10);
        tree.addElement(5);
        tree.addElement(15);

        assertEquals(10, tree.find(10));
        assertEquals(5, tree.find(5));
        assertEquals(15, tree.find(15));
    }

    @Test
    void testFindElementNotFound() {
        tree.addElement(10);
        tree.addElement(5);

        assertThrows(ElementNotFoundException.class, () -> tree.find(20));
    }

    @Test
    void testRemoveAllOccurrences() throws ElementNotFoundException {
        tree.addElement(10);
        tree.addElement(5);
        tree.addElement(10);
        tree.addElement(15);

        tree.removeAllOccurences(10);

        assertEquals(2, tree.size());
        assertFalse(tree.contains(10));
    }

    @Test
    void testIsEmpty() {
        assertTrue(tree.isEmpty());

        tree.addElement(10);
        assertFalse(tree.isEmpty());
    }

    @Test
    void testSize() {
        assertEquals(0, tree.size());

        tree.addElement(10);
        tree.addElement(5);
        tree.addElement(15);

        assertEquals(3, tree.size());
    }

}
