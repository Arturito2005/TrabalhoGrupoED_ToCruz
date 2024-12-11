package TestArrayTree;

import static org.junit.jupiter.api.Assertions.*;

import ArrayTree.ArrayBinarySearchTree;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import Exceptions.ElementNotFoundException;

public class TestArrayBinarySearchTree {
    private ArrayBinarySearchTree<Integer> tree;

    @BeforeEach
    void setUp() {
        tree = new ArrayBinarySearchTree<>();
    }

    @Test
    void testAddElement() {
        tree.addElement(10);
        tree.addElement(5);
        tree.addElement(15);
        assertEquals(3, tree.size(), "Tamanho da árvore deve ser 3 após adicionar 3 elementos.");
        assertTrue(tree.contains(10), "A árvore deve conter o elemento 10.");
        assertTrue(tree.contains(5), "A árvore deve conter o elemento 5.");
        assertTrue(tree.contains(15), "A árvore deve conter o elemento 15.");
    }

    @Test
    void testRemoveElement() throws ElementNotFoundException {
        tree.addElement(10);
        tree.addElement(5);
        tree.addElement(15);
        Integer removed = tree.removeElement(5);
        assertEquals(5, removed, "O elemento removido deve ser 5.");
        assertFalse(tree.contains(5), "A árvore não deve conter o elemento 5 após remoção.");
    }

    @Test
    void testRemoveElementNotFound() {
        tree.addElement(10);
        tree.addElement(5);
        tree.addElement(15);
        assertThrows(ElementNotFoundException.class, () -> tree.removeElement(20), "Deve lançar exceção quando o elemento não for encontrado.");
    }

    @Test
    void testRemoveMin() throws ElementNotFoundException {
        tree.addElement(10);
        tree.addElement(5);
        tree.addElement(15);
        Integer min = tree.removeMin();
        assertEquals(5, min, "O menor elemento removido deve ser 5.");
        assertFalse(tree.contains(5), "A árvore não deve conter o elemento 5 após remoção.");
    }

 /*
 *    @Test
    void testRemoveMax() {
        tree.addElement(10);
        tree.addElement(5);
        tree.addElement(15);

        Integer max = tree.removeMax();

        assertEquals(15, max, "O maior elemento removido deve ser 15.");
    }
 * */

    @Test
    void testFindMin() {
        tree.addElement(10);
        tree.addElement(5);
        tree.addElement(15);
        Integer min = tree.findMin();
        assertEquals(5, min, "O menor elemento na árvore deve ser 5.");
    }

    @Test
    public void testFindMax() {
        ArrayBinarySearchTree<Integer> tree = new ArrayBinarySearchTree<>();
        tree.addElement(10);
        tree.addElement(20);
        tree.addElement(5);
        tree.addElement(15);

        assertEquals(20, tree.findMax());
    }

    @Test
    void testFindElement() throws ElementNotFoundException {
        tree.addElement(10);
        tree.addElement(5);
        tree.addElement(15);
        Integer element = tree.find(10);
        assertEquals(10, element, "O elemento encontrado deve ser 10.");
    }

    @Test
    void testFindElementNotFound() {
        tree.addElement(10);
        tree.addElement(5);
        tree.addElement(15);
        assertThrows(ElementNotFoundException.class, () -> tree.find(20), "Deve lançar exceção quando o elemento não for encontrado.");
    }

    @Test
    void testIsEmpty() {
        assertTrue(tree.isEmpty(), "A árvore deve estar vazia inicialmente.");
        tree.addElement(10);
        assertFalse(tree.isEmpty(), "A árvore não deve estar vazia após adicionar um elemento.");
    }

    @Test
    void testRemoveAllOccurrences() throws ElementNotFoundException {
        tree.addElement(10);
        tree.addElement(5);
        tree.addElement(15);
        tree.addElement(5);
        tree.removeAllOccurences(5);
        assertFalse(tree.contains(5), "A árvore não deve conter o elemento 5 após remover todas as ocorrências.");
    }
}
