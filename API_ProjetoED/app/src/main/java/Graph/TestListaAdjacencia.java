package Graph;

import java.util.Iterator;

public class TestListaAdjacencia {

    public static void main(String[] args) {
        GraphListaAdjacencia<String> graph = new GraphListaAdjacencia<>();

        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");
        graph.addVertex("D");
        graph.addVertex("E");
        graph.addVertex("F");
        graph.addVertex("G");
        System.out.println(graph.isConnected());

        //Funciona
        //graph.removeVertex("C");

        graph.addEdge("A", "B");
        graph.addEdge("A", "C");
        graph.addEdge("B", "D");
        graph.addEdge("B", "E");
        graph.addEdge("C", "F");
        graph.addEdge("C", "G");
        graph.addEdge("E", "G");
        System.out.println(graph.isConnected());

        Iterator<String> itr2 = graph.iteratorBFSNextDivisoes("A");
        Iterator<String> itr = graph.iteratorShortestPath("A", "G");

        while(itr.hasNext()) {
            System.out.println(itr.next() + " ");
        }
    }
    
}
