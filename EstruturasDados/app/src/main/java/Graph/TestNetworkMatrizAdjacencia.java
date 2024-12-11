package Graph;

/**
 * Classe de teste para a implementação de rede usando Matriz de Adjacência.
 *
 * Este programa cria uma rede de cidades utilizando uma matriz de adjacência,
 * adiciona vértices (cidades) e arestas (distâncias entre as cidades).
 *
 * A classe testa a funcionalidade básica de adição de vértices e arestas, mas
 * ainda está pendente de teste para o cálculo do caminho mais curto entre
 * cidades específicas.
 */
public class TestNetworkMatrizAdjacencia {

    /**
     * Método principal que executa o teste da rede usando Matriz de Adjacência.
     *
     * Neste método, são criados vértices (representando cidades) e adicionadas
     * arestas (distâncias entre as cidades). Também está comentado um teste
     * para o cálculo do caminho mais curto entre as cidades Philadelphia e Roanoke.
     *
     * @param args os argumentos da linha de comando (não são usados neste teste)
     */
    public static void main(String[] args) {
        NetworkMatrizAdjacencia<String> netMatriz = new NetworkMatrizAdjacencia<>();
        
        netMatriz.addVertex("Philadelpia");
        netMatriz.addVertex("Boston");
        netMatriz.addVertex("New York");
        netMatriz.addVertex("Roanoke");
        
        netMatriz.addEdge("Philadelpia", "Boston", 219);
        netMatriz.addEdge("Philadelpia", "New York", 225);
        netMatriz.addEdge("New York", "Boston", 120);
        netMatriz.addEdge("New York", "Roanoke", 320);
        
        //Testar depois o caminho mais curto de Philadelpia até Roanoke
    }
    
}
