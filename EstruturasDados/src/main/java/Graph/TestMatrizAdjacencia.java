package Graph;

import java.util.Iterator;

public class TestMatrizAdjacencia {

    public static void main(String[] args) {
        GraphMatrizAdjacencia<String> graph = new GraphMatrizAdjacencia<>();
        
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");
        graph.addVertex("D");
        graph.addVertex("E");
        System.out.println(graph.isConnected());
        
        //Funciona
        //graph.removeVertex("C");
        
        graph.addEdge("A", "B");
        graph.addEdge("A", "C");
        graph.addEdge("B", "D");
        graph.addEdge("B", "C");
        graph.addEdge("D", "E");
        
        System.out.println(graph.isConnected());
        
        Iterator<String> itr2 = graph.iteratorDFS("A");
        Iterator<String> itr = graph.iteratorShortestPath("A", "E");
        
        while(itr.hasNext()) {
            System.out.println(itr.next() + " ");
        }
    }
    
}
