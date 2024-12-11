package Graph;

import java.util.Iterator;

/**
 * Classe de teste para a implementação de um grafo usando Matriz de Adjacência.
 *
 * Este programa cria um grafo com vértices e arestas, testa se o grafo está
 * conectado e calcula o caminho mais curto entre dois vértices utilizando uma
 * matriz de adjacência. Ele também utiliza a busca em profundidade (DFS) para
 * iterar sobre os vértices.
 */
public class TestMatrizAdjacencia {

    /**
     * Método principal que executa os testes no grafo representado por uma matriz
     * de adjacência.
     *
     * Neste método, são adicionados vértices ao grafo, arestas entre eles são
     * criadas, e em seguida são feitos testes para verificar se o grafo está
     * conectado. O código também testa a iteração sobre os vértices utilizando
     * busca em profundidade (DFS) e a obtenção do caminho mais curto entre dois
     * vértices.
     *
     * @param args os argumentos da linha de comando (não são usados neste teste)
     */
    public static void main(String[] args) {
        GraphMatrizAdjacencia<String> graph = new GraphMatrizAdjacencia<>();
        
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
        
        Iterator<String> itr2 = graph.iteratorDFS("A");
        Iterator<String> itr = graph.iteratorShortestPath("A", "G");
        
        while(itr.hasNext()) {
            System.out.println(itr.next() + " ");
        }
    }
    
}
