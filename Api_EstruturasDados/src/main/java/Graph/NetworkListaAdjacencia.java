package Graph;

import Interfaces.NetworkADT;
import LinkedList.LinearLinkedOrderedList;
import java.util.Iterator;


/**
 * Representa uma rede de grafos implementada utilizando listas de adjacência.
 * Esta classe é responsável por manipular grafos representados por listas de adjacência,
 * incluindo a adição de arestas, a busca de caminhos e a iteração pelos vértices.
 *
 * @param <T> o tipo dos vértices no grafo
 * @author Artur Pinto
 * Nº mecanográfico: 8230138
 * @author Francisco Oliveira
 * Nº mecanográfico: 8230148
 * @version 1.0
 */
public class NetworkListaAdjacencia<T> extends GraphListaAdjacencia<T> implements NetworkADT<T> {

    /**
     * Construtor padrão para a classe NetworkListaAdjacencia.
     * Inicializa uma nova instância da classe sem configurar nenhuma propriedade específica.
     */
    public NetworkListaAdjacencia() {
        super();
    }

    /**
     * Array de listas encadeadas ordenadas representando a lista de adjacência para cada vértice.
     * Cada índice da lista contém os vértices adjacentes a um vértice específico.
     */
    protected LinearLinkedOrderedList<T>[] listaAdj;

    /**
     * Adiciona uma aresta entre dois vértices no grafo com um peso especificado.
     * A aresta é adicionada em ambas as direções, já que o grafo é não direcionado.
     *
     * @param vertex1 o primeiro vértice da aresta
     * @param vertex2 o segundo vértice da aresta
     * @param weight o peso da aresta
     */
    @Override
    public void addEdge(T vertex1, T vertex2, double weight) {
        if (indexIsValid(vertex1) && indexIsValid(vertex2)) {
            this.listaAdj[getIndex(vertex1)].add(vertex2);
            this.listaAdj[getIndex(vertex2)].add(vertex1);
        }
    }

    /**
     * Edita o peso de uma aresta entre dois vértices no grafo.
     * Este método não altera o peso da aresta, mas sim apenas a lista de adjacências dos vértices.
     *
     * @param vertex1 o primeiro vértice da aresta
     * @param vertex2 o segundo vértice da aresta
     * @param weight o novo peso da aresta
     */
    public void editWeightEdge(T vertex1, T vertex2, double weight) {
        if (indexIsValid(vertex1) && indexIsValid(vertex2)) {
            this.listaAdj[getIndex(vertex1)].add(vertex2);
            this.listaAdj[getIndex(vertex2)].add(vertex1);
        }
    }

    /**
     * Retorna o peso do caminho mais curto entre dois vértices.
     * Este método é um esboço e atualmente retorna 0, sendo necessário implementação.
     *
     * @param vertex1 o primeiro vértice
     * @param vertex2 o segundo vértice
     * @return o peso do caminho mais curto entre os vértices ou 0 se não implementado
     */
    @Override
    public double shortestPathWeight(T vertex1, T vertex2) {

        return 0;
    }

    /**
     * Retorna um iterador para os vértices adjacentes a um vértice de início.
     * Este método está incompleto e retornará nulo por enquanto.
     *
     * @param startVertex o vértice inicial
     * @return um iterador para os vértices adjacentes ao vértice de início
     */
    @Override
    public Iterator<T> iteratorNextVertexs(T startVertex) {
        return null;
    }

    /**
     * Retorna um iterador para os vértices do grafo utilizando uma busca em profundidade (DFS).
     *
     * @return um iterador para os vértices do grafo
     */
    @Override
    public Iterator<T> iterator() {
        return super.iteratorDFS(this.vertices[0]);
    }

    /**
     * Retorna os vértices adjacentes a um vértice específico com um peso de aresta específico.
     * Este método não está implementado e retorna nulo por enquanto.
     *
     * @param weight o peso da aresta
     * @param visited um array indicando quais vértices já foram visitados
     * @return um array com os vértices adjacentes ao vértice atual
     */
    protected int[] getEdgeWithWeightOf(double weight, boolean visited[]) {
        return null;
    }

    /*
     * No jogo esta arvore gerador é que nos vai gerar o caminho mais curto do ToCruz até uma sala, vai ter
     * de ser chamado constantemente para ser atualizada de acordo com a movimentação dos inimigos
     *
     * Returns a minimum spanning tree of the network.
     *
     * @return a minimum spanning tree of the network
     */
   /*
    public NetworkListaAdjacencia mstNetwork() {
    int x, y;
    int index;
    double weight;
    int[] edge = new int[2];
    LinkedHeap<Double> minHeap = new LinkedHeap<Double>();
    NetworkListaAdjacencia<T> resultGraph = new NetworkListaAdjacencia<T>();

    if (isEmpty() || !isConnected()) {
    return resultGraph;
    }

    resultGraph.adjMatrix = new double[numVertices][numVertices];
    for (int i = 0; i < numVertices; i++) {
    for (int j = 0; j < numVertices; j++) {
    resultGraph.adjMatrix[i][j] = Double.POSITIVE_INFINITY;
    }
    }

    //Inicializa o numero de veritices do grafo resultado
    resultGraph.vertices = (T[])(new Object[numVertices]);
    boolean[] visited = new boolean[numVertices];

    for (int i = 0; i < numVertices; i++) {
    visited[i] = false;
    }

    //Inicializa o vertice de inicio
    edge[0] = 0;
    resultGraph.vertices[0] = this.vertices[0];
    resultGraph.numVertices++;
    visited[0] = true;

        for (int i = 0; i < numVertices; i++){
        minHeap.addElement(adjMatrix[0][i]);
    }

        while ((resultGraph.size() < this.size()) && !minHeap.isEmpty()) {
        do {
            weight = (minHeap.removeMin()).doubleValue();
            edge = getEdgeWithWeightOf(weight, visited);
        } while (!indexIsValid(edge[0]) || !indexIsValid(edge[1]));

        x = edge[0];
        y = edge[1];

        if (!visited[x]) {
            index = x;
        } else {
            index = y;
        }

        resultGraph.vertices[index] = this.vertices[index];
        visited[index] = true;
        resultGraph.numVertices++;
        resultGraph.adjMatrix[x][y] = this.adjMatrix[x][y];
        resultGraph.adjMatrix[y][x] = this.adjMatrix[y][x];

        for (int i = 0; i < numVertices; i++) {
            if (!visited[i] && (this.adjMatrix[i][index] < Double.POSITIVE_INFINITY)) {
                edge[0] = index;
                edge[1] = i;
                minHeap.addElement(adjMatrix[index][i]);
            }
        }
    }
        return resultGraph;
}
    * */
}
