package TestGraph;

import Graph.ArrayGraph.ListaAdjacencia.MatrizAdjacencia.ExtendedNetwork;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Iterator;
import static org.junit.jupiter.api.Assertions.*;

public class TestNetwork {

    private ExtendedNetwork<String> graph;

    @BeforeEach
    public void setUp() {
        graph = new ExtendedNetwork<>();
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");
    }

    @Test
    public void testGetWeightEdge() {
        graph.addEdge("A", "B", 5.0);

        double weight = graph.getWeightEdge("A", "B");
        assertEquals(5.0, weight, "O peso da aresta deve ser 5.0.");

        weight = graph.getWeightEdge("A", "C");
        assertEquals(Double.POSITIVE_INFINITY, weight, "O peso da aresta inexistente deve ser infinito.");
    }

    @Test
    public void testUpdateWeightEdge() {
        graph.addEdge("A", "B", 5.0);
        graph.updateWeightEdge("A", 10.0);

        double weight = graph.getWeightEdge("A", "B");
        assertEquals(10.0, weight, "O peso da aresta entre A e B deve ser 10.0.");
    }

    @Test
    public void testShortestPath() {
        graph.addEdge("A", "B", 5.0);
        graph.addEdge("B", "C", 2.0);

        Iterator<String> path = graph.shortestPath("A", "C");
        assertTrue(path.hasNext(), "Deve haver um caminho mais curto entre A e C.");
        assertEquals("A", path.next(), "O primeiro vértice do caminho deve ser A.");
        assertEquals("B", path.next(), "O segundo vértice do caminho deve ser B.");
        assertEquals("C", path.next(), "O terceiro vértice do caminho deve ser C.");
    }

    @Test
    public void testShortestPathNoPath() {
        Iterator<String> path = graph.shortestPath("A", "B");
        assertFalse(path.hasNext(), "Não deve haver caminho entre A e B.");
    }

    @Test
    public void testShortestPathArest() {
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");

        graph.addEdge("A", "B", 5.0);
        graph.addEdge("B", "C", 2.0);
        double numEdges = graph.shortestPathArest("A", "C");
        assertEquals(2.0, numEdges, "O número mínimo de arestas entre A e C deve ser 2.");
        assertEquals(2.0, numEdges, "O número mínimo de arestas entre A e C deve ser 2.");
    }

    @Test
    public void testShortestPathArestNoPath() {
        double numEdges = graph.shortestPathArest("A", "B");
        assertEquals(Double.MAX_VALUE, numEdges, "Se não houver caminho, o número de arestas deve ser infinito.");
    }
}
