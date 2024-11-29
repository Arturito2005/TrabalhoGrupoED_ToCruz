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
        
        System.out.println(graph.isConnected());
        
        //graph.removeVertex("C");
        
        graph.addEdge("A", "B");
        graph.addEdge("A", "C");
        graph.addEdge("B", "D");
        graph.addEdge("B", "C");
        graph.addEdge("D", "E");
        
        //Funciona
        //graph.removeVertex("C");
       
        Iterator<String> itr = graph.iteratorBFS("A");
        Iterator<String> itr2 = graph.iteratorDFS("A");  
        
        System.out.println(graph.isConnected());
    }
    
}
