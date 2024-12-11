package Graph;

import java.util.Iterator;

/**
 * Classe de teste para a implementação de uma rede usando Matriz de Adjacência.
 *
 * Este programa testa funcionalidades de adição de vértices e arestas em uma rede
 * representada por uma matriz de adjacência, além de calcular e imprimir o caminho
 * mais curto entre pares de vértices.
 *
 * O código cria uma rede com vários vértices (representando diferentes locais)
 * e arestas (representando conexões entre os locais com pesos), e em seguida calcula
 * os caminhos mais curtos entre alguns pontos específicos da rede.
 */
public class TestNetwork {

    /**
     * Método principal que executa o teste da rede de matriz de adjacência.
     *
     * Neste método, são adicionados vértices à rede e arestas que conectam esses
     * vértices. Depois, são realizados cálculos para encontrar os caminhos mais
     * curtos entre diferentes pares de vértices e imprimir os resultados.
     *
     * @param args os argumentos da linha de comando (não são usados neste teste)
     */
    public static void main(String[] args) {
        Network<String> netMatriz = new Network<>();

        //netMatriz.addVertex("Escada de Emergência");
        //netMatriz.addVertex("Garagem");
        //netMatriz.addVertex("Heliporto");
        netMatriz.addVertex("Camaratas");
        //netMatriz.addVertex("Porteiro");
        //netMatriz.addVertex("Corredor 1");
        netMatriz.addVertex("Corredor 2");
        netMatriz.addVertex("Segurança");
        netMatriz.addVertex("WC");
        //netMatriz.addVertex("Escada 1");
        //netMatriz.addVertex("Escada 2");
        //netMatriz.addVertex("Escada 3");
        netMatriz.addVertex("Escada 4");
        netMatriz.addVertex("Escada 5");
       //netMatriz.addVertex("Escada 6");
        //netMatriz.addVertex("Escritório 1");
        //netMatriz.addVertex("Escritório 2");
        netMatriz.addVertex("Escritório 3");
        netMatriz.addVertex("Laboratório");
        //netMatriz.addVertex("Armazém");
        //netMatriz.addVertex("Hall");

/*
*         netMatriz.addEdge("Garagem", "Escada 1", 0);
        netMatriz.addEdge("Garagem", "Escada de Emergência", 0);
        netMatriz.addEdge("Escritório 1", "Escada de Emergência", 0);
        netMatriz.addEdge("Porteiro", "Escada 1", 0);
        netMatriz.addEdge("Porteiro", "Escada 2", 0);
        netMatriz.addEdge("Corredor 1", "Escada 2", 15);
        netMatriz.addEdge("Corredor 1", "Escritório 1", 15);
        netMatriz.addEdge("Corredor 1", "Escritório 2", 15);
        netMatriz.addEdge("Corredor 1", "Escada 3", 15);
        netMatriz.addEdge("Hall", "Escada 3", 0);
        netMatriz.addEdge("Hall", "Segurança", 15);
        netMatriz.addEdge("Corredor 2", "Segurança", 15);
        //netMatriz.addEdge("Corredor 2", "WC", 0);
        netMatriz.addEdge("Corredor 2", "Escada 4", 0);
        netMatriz.addEdge("Escritório 3", "Escada 4", 0);
        netMatriz.addEdge("Escritório 3", "Escada 5", 0);
        netMatriz.addEdge("Laboratório", "Escada 5", 0);
        netMatriz.addEdge("Laboratório", "WC", 0);
        netMatriz.addEdge("Segurança", "WC", 0);
        netMatriz.addEdge("Armazém", "Escada 5", 0);
        netMatriz.addEdge("Camaratas", "Escada 5", 25);
        netMatriz.addEdge("Camaratas", "Escada 6", 25);
        netMatriz.addEdge("Heliporto", "Escada 6", 20);*/

        netMatriz.addEdge("Camaratas", "Escada 5", 25);
        netMatriz.addEdge("Laboratório", "Escada 5", 0);
        netMatriz.addEdge("Laboratório", "WC", 0);
        netMatriz.addEdge("Segurança", "WC", 0);
        netMatriz.addEdge("Segurança", "Corredor 2", 13);
        netMatriz.addEdge("Escritório 3", "Escada 5", 0);
        netMatriz.addEdge("Escritório 3", "Escada 4", 0);
        netMatriz.addEdge("Corredor 2", "Escada 4", 0);
        //netMatriz.addEdge("Corredor 2", "Segurança", 0);
        Double custo = netMatriz.shortestPathWeight("Camaratas", "Corredor 2");
        Iterator<String> iterator1 = netMatriz.shortestPath("Camaratas", "Corredor 2");
        while (iterator1.hasNext()) {
            System.out.println(iterator1.next());
        }
        System.out.println(netMatriz.shortestPathWeight("Corredor 2", "Hall"));

        Iterator<String> iterator2 = netMatriz.shortestPath("Heliporto", "Camaratas");
        while (iterator2.hasNext()) {
            System.out.println(iterator2.next());
        }
        System.out.println(netMatriz.shortestPathWeight("Heliporto", "Camaratas"));

        Iterator<String> iterator3 = netMatriz.iteratorShortestPath("Escritório 1", "Hall");
        while (iterator3.hasNext()) {
            System.out.println(iterator3.next());
        }
        System.out.println(netMatriz.shortestPathWeight("Escritório 1", "Hall"));
    }

}