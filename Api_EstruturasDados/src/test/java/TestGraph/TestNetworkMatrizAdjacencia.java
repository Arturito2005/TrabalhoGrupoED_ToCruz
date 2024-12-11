package TestGraph;

import Graph.NetworkMatrizAdjacencia;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestNetworkMatrizAdjacencia {
    private NetworkMatrizAdjacencia<String> network;

    @BeforeEach
    public void setUp() {
        network = new NetworkMatrizAdjacencia<>();
    }

    @Test
    public void testAddEdge() {
        network.addVertex("A");
        network.addVertex("B");
        network.addEdge("A", "B", 5.0);

        Iterator<String> adjacent = network.iteratorNextVertexs("A");
        assertTrue(adjacent.hasNext());
        assertEquals("B", adjacent.next());
    }

    @Test
    public void testShortestPathWeight() {
        network.addVertex("A");
        network.addVertex("B");
        network.addVertex("C");
        network.addEdge("A", "B", 2.0);
        network.addEdge("B", "C", 3.0);
        network.addEdge("A", "C", 10.0);

        double shortestPath = network.shortestPathWeight("A", "C");
        assertEquals(5.0, shortestPath, 0.01); // A -> B -> C
    }

    @Test
    public void testShortestPathNoPath() {
        network.addVertex("A");
        network.addVertex("B");
        network.addVertex("C");

        double shortestPath = network.shortestPathWeight("A", "C");
        assertEquals(Double.MAX_VALUE, shortestPath);
    }

    @Test
    public void testIteratorNextVertexs() {
        network.addVertex("A");
        network.addVertex("B");
        network.addVertex("C");
        network.addEdge("A", "B", 1.0);
        network.addEdge("A", "C", 2.0);

        Iterator<String> adjacent = network.iteratorNextVertexs("A");
        assertTrue(adjacent.hasNext());
        assertEquals("B", adjacent.next());
        assertEquals("C", adjacent.next());
    }

    @Test
    public void testIteratorDFS() {
        network.addVertex("A");
        network.addVertex("B");
        network.addVertex("C");
        network.addEdge("A", "B", 1.0);
        network.addEdge("B", "C", 2.0);

        Iterator<String> dfs = network.iterator();
        assertTrue(dfs.hasNext());
        assertEquals("A", dfs.next());
        assertEquals("B", dfs.next());
        assertEquals("C", dfs.next());
    }

    @Test
    public void testIsolatedVertex() {
        network.addVertex("A");
        network.addVertex("B");
        network.addEdge("A", "B", 1.0);

        Iterator<String> isolated = network.iteratorNextVertexs("B");
        assertTrue(isolated.hasNext());
        assertEquals("A", isolated.next());
    }
}
