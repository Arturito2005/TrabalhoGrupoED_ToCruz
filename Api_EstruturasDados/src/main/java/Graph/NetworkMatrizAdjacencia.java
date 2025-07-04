package Graph;

import ArrayList.ArrayUnorderedList;
import Interfaces.HeapADT;
import Interfaces.NetworkADT;
import Interfaces.UnorderedListADT;
import LinkedTree.LinkedHeap;

import java.util.Iterator;

/**
 * Representa uma rede de grafos utilizando uma matriz de adjacência para armazenar os pesos das arestas.
 * Esta classe implementa a interface {@link NetworkADT} e oferece funcionalidades para adicionar vértices e arestas,
 * calcular caminhos mais curtos, iterar pelos vértices adjacentes e calcular a árvore geradora mínima.
 *
 * @param <T> o tipo de dado que representa os vértices no grafo
 * @author Artur Pinto
 * Nº mecanográfico: 8230138
 * @author Francisco Oliveira
 * Nº mecanográfico: 8230148
 * @version 1.0
 */
public class NetworkMatrizAdjacencia<T> extends GraphMatrizAdjacencia<T> implements NetworkADT<T> {

    /**
     * A matriz de adjacência que armazena os pesos das arestas entre os vértices.
     */
    protected double[][] adjMatrix;

    /**
     * Construtor que inicializa a matriz de adjacência com a capacidade default.
     * Define todos os valores da matriz como infinito positivo, indicando a ausência de arestas.
     */
    public NetworkMatrizAdjacencia() {
        super();
        this.adjMatrix = new double[DEFAULT_CAPACITY][DEFAULT_CAPACITY];
        for(int i = 0; i < DEFAULT_CAPACITY; i++) {
            for(int j = 0; j < DEFAULT_CAPACITY; j++) {
                adjMatrix[i][j] = Double.POSITIVE_INFINITY;
            }
        }
    }

    /**
     * Expande a matriz de adjacência para dobrar a sua capacidade quando não houver mais espaço.
     * Essa operação copia os dados da matriz atual para uma nova matriz maior
     * e redefine os valores para infinito positivo.
     */
    protected void expandadweightMatrix() {
        super.expandCapacity();
        double[][] tempMatriz = new double[this.vertices.length * 2][this.vertices.length * 2];

        for (int i = 0; i < tempMatriz.length; i++) {
            for (int j = 0; j < tempMatriz.length; j++) {
                tempMatriz[i][j] = Double.POSITIVE_INFINITY;
            }
        }

        for (int i = 0; i < this.adjMatrix.length; i++) {
            System.arraycopy(this.adjMatrix[i], 0, tempMatriz[i], 0, this.adjMatrix[i].length);
        }

        this.adjMatrix = tempMatriz;
    }

    /**
     * Adiciona um vértice ao grafo.
     * Se o número de vértices alcançar a capacidade máxima, a matriz de adjacência será expandida.
     *
     * @param vertex o vértice a ser adicionado
     */
    @Override
    public void addVertex(T vertex) {
        if(this.vertices.length == this.numVertices) {
            expandadweightMatrix();
        }

        super.addVertex(vertex);
    }

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
        int index1 = getIndex(vertex1);
        int index2 = getIndex(vertex2);

        if (indexIsValid(index1) && indexIsValid(index2)) {
            this.adjMatrix[index1][index2] = weight;
            this.adjMatrix[index2][index1] = weight;
            super.addEdge(index1, index2);
        }
    }

    /**
     * Retorna um iterador para os vértices adjacentes a um vértice de início.
     * A iteração é feita pelos vértices que possuem uma aresta com o vértice de início,
     * excluindo os vértices com peso infinito.
     *
     * @param startVertex o vértice inicial
     * @return um iterador para os vértices adjacentes ao vértice de início
     */
    @Override
    public Iterator<T> iteratorNextVertexs(T startVertex) {
        int index = getIndex(startVertex);

        if (!indexIsValid(index)) {
            return new ArrayUnorderedList<T>().iterator();
        }

        UnorderedListADT<T> adjacentVertices = new ArrayUnorderedList<T>();

        for (int i = 0; i < numVertices; i++) {
            if (adjMatrix[index][i] != Double.POSITIVE_INFINITY) {
                adjacentVertices.addToRear(vertices[i]);
            }
        }

        return adjacentVertices.iterator();
    }

    /**
     * Calcula o caminho mais curto entre dois vértices, levando em consideração o
     * custo das arestas e o número de arestas.
     * Em caso de empate no custo, o caminho com menos arestas é priorizado.
     *
     * @param startVertex o vértice de origem
     * @param finalVertex o vértice de destino
     * @return o custo do caminho mais curto entre os dois vértices ou
     * Double.MAX_VALUE se não houver caminho
     */
    @Override
    public double shortestPathWeight(T startVertex, T finalVertex) {
        int startIndex = getIndex(startVertex);
        int finalIndex = getIndex(finalVertex);

        if (!indexIsValid(startIndex) || !indexIsValid(finalIndex)) {
            return Double.MAX_VALUE;
        }

        double[] distances = new double[numVertices];
        boolean[] visited = new boolean[numVertices];
        HeapADT<T> minHeap = new LinkedHeap<>();

        for (int i = 0; i < numVertices; i++) {
            distances[i] = Double.MAX_VALUE;
            visited[i] = false;
        }

        distances[startIndex] = 0;
        minHeap.addElement(startVertex);

        while (!minHeap.isEmpty()) {
            T currentVertex = minHeap.removeMin();
            int currentIndex = getIndex(currentVertex);

            if (indexIsValid(currentIndex) && !visited[currentIndex]) {
                visited[currentIndex] = true;

                Iterator<T> itr = this.iteratorNextVertexs(currentVertex);
                while (itr.hasNext()) {
                    T element = itr.next();
                    int neighborIndex = getIndex(element);

                    if (indexIsValid(neighborIndex) && !visited[neighborIndex]) {
                        double newDistance = distances[currentIndex] + this.adjMatrix[currentIndex][neighborIndex];

                        if (newDistance < distances[neighborIndex]) {
                            distances[neighborIndex] = newDistance;
                            minHeap.addElement(element);
                        }
                    }
                }
            }
        }

        if (distances[finalIndex] == Double.MAX_VALUE) {
            return Double.MAX_VALUE;
        }

        return distances[finalIndex];
    }

    /**
     * Retorna um iterador para os vértices do grafo utilizando uma pesquisa em profundidade (DFS).
     *
     * @return um iterador para os vértices do grafo
     */
    @Override
    public Iterator<T> iterator() {
        return super.iteratorDFS(this.vertices[0]);
    }

    /**
     * Retorna os vértices adjacentes a um vértice específico com um peso de aresta específico.
     *
     * @param weight o peso da aresta
     * @param visited um array indicando quais vértices já foram visitados
     * @return um array com os vértices adjacentes ao vértice atual
     */
    protected int[] getEdgeWithWeightOf(double weight, boolean visited[]) {
        int[] edges = new int[2];
        for (int i = 0; i < this.adjMatrix.length; i++) {
            for (int y = 0; y < this.adjMatrix[i].length; y++) {
                if (this.adjMatrix[i][y] == weight && (visited[i] && !visited[y]) || (!visited[i] && visited[y])) {
                    edges[0] = i;
                    edges[1] = y;
                    return edges;
                }
            }
        }

        edges[0] = -1;
        edges[1] = -1;
        return edges;
    }

    /**
     * Retorna a árvore geradora de custo mínimo do grafo, utilizando o algoritmo de Prim.
     *
     * @return uma árvore geradora de custo mínimo do grafo
     */
    public NetworkMatrizAdjacencia<T> mstNetwork() {
        int x, y;
        int index;
        double weight;
        int[] edge = new int[2];
        LinkedHeap<Double> minHeap = new LinkedHeap<Double>();
        NetworkMatrizAdjacencia<T> resultGraph = new NetworkMatrizAdjacencia<T>();

        if (isEmpty() || !isConnected()) {
            return resultGraph;
        }

        resultGraph.adjMatrix = new double[numVertices][numVertices];
        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numVertices; j++) {
                resultGraph.adjMatrix[i][j] = Double.POSITIVE_INFINITY;
            }
        }

        //Inicializa o numero de vertices do grafo resultado
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
        /** Add all edges, which are adjacent to the starting vertex,
         to the heap

         Adicione todas as arestas adjacentes ao vértice inicial,
         para a heap*/
        for (int i = 0; i < numVertices; i++){
            minHeap.addElement(adjMatrix[0][i]);
        }

        while ((resultGraph.size() < this.size()) && !minHeap.isEmpty()) {
            /** Get the edge with the smallest weight that has exactly
             one vertex already in the resultGraph

             Obtem a borda com o menor peso que tenha exatamente
             um vértice já no resultGraph*/
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

            /** Add the new edge and vertex to the resultGraph */
            resultGraph.vertices[index] = this.vertices[index];
            visited[index] = true;
            resultGraph.numVertices++;
            resultGraph.adjMatrix[x][y] = this.adjMatrix[x][y];
            resultGraph.adjMatrix[y][x] = this.adjMatrix[y][x];

            /** Adicione todas as arestas adjacentes ao vértice recém-adicionado,
             para a pilha */
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
}
