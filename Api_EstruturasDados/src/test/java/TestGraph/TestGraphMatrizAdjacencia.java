package TestGraph;

import Exceptions.EmptyCollectionException;
import Graph.GraphMatrizAdjacencia;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

public class TestGraphMatrizAdjacencia {
    private GraphMatrizAdjacencia<String> graph;

    @BeforeEach
    public void setUp() {
        graph = new GraphMatrizAdjacencia<String>();
    }

    @Test
    public void testAddVertex() {
        graph.addVertex("A");
        graph.addVertex("B");
        assertEquals(2, graph.size(), "Deve haver 2 vértices no grafo.");
    }

    @Test
    public void testRemoveVertex() throws EmptyCollectionException {
        graph.addVertex("A");
        graph.addVertex("B");
        graph.removeVertex("A");
        assertEquals(1, graph.size(), "Deve haver 1 vértice no grafo.");
        assertThrows(EmptyCollectionException.class, () -> graph.removeVertex("C"), "Deve lançar exceção ao remover vértice inexistente.");
    }

    @Test
    public void testAddEdge() {
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addEdge("A", "B");

        Iterator<String> bfsIterator = graph.iteratorBFS("A");
        boolean foundB = false;
        while (bfsIterator.hasNext()) {
            if (bfsIterator.next().equals("B")) {
                foundB = true;
                break;
            }
        }

        assertTrue(foundB, "O vértice B deve ser adjacente ao vértice A.");
    }


    @Test
    public void testIteratorBFS() {
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");
        graph.addEdge("A", "B");
        graph.addEdge("B", "C");

        Iterator<String> iterator = graph.iteratorBFS("A");
        assertTrue(iterator.hasNext(), "A busca em largura deve retornar pelo menos um vértice.");
        assertEquals("A", iterator.next(), "O primeiro vértice da BFS deve ser A.");
        assertEquals("B", iterator.next(), "O segundo vértice da BFS deve ser B.");
        assertEquals("C", iterator.next(), "O terceiro vértice da BFS deve ser C.");
    }

    @Test
    public void testIteratorDFS() {
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");
        graph.addEdge("A", "B");
        graph.addEdge("B", "C");

        Iterator<String> iterator = graph.iteratorDFS("A");
        assertTrue(iterator.hasNext(), "A busca em profundidade deve retornar pelo menos um vértice.");
        assertEquals("A", iterator.next(), "O primeiro vértice da DFS deve ser A.");
        assertEquals("B", iterator.next(), "O segundo vértice da DFS deve ser B.");
        assertEquals("C", iterator.next(), "O terceiro vértice da DFS deve ser C.");
    }

    @Test
    public void testIteratorShortestPath() {
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");
        graph.addVertex("D");
        graph.addEdge("A", "B");
        graph.addEdge("B", "C");
        graph.addEdge("C", "D");

        Iterator<String> iterator = graph.iteratorShortestPath("A", "D");
        assertTrue(iterator.hasNext(), "O caminho mais curto deve retornar pelo menos um vértice.");
        assertEquals("A", iterator.next(), "O primeiro vértice do caminho deve ser A.");
        assertEquals("B", iterator.next(), "O segundo vértice do caminho deve ser B.");
        assertEquals("C", iterator.next(), "O terceiro vértice do caminho deve ser C.");
        assertEquals("D", iterator.next(), "O quarto vértice do caminho deve ser D.");
    }

    @Test
    public void testIsEmpty() {
        assertTrue(graph.isEmpty(), "O grafo deve estar vazio após a criação.");
        graph.addVertex("A");
        assertFalse(graph.isEmpty(), "O grafo não deve estar vazio após adicionar um vértice.");
    }

    @Test
    public void testExpandCapacity() {
        for (int i = 0; i < 15; i++) {
            graph.addVertex("V" + i);
        }
        assertEquals(15, graph.size(), "A capacidade do grafo deve ser expandida automaticamente.");
    }
}
