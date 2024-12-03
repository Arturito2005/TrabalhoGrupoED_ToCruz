package Graph;

public class TestNetworkMatrizAdjacencia {

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
