package Graph;

import Interfaces.*;
import LinkedList.LinearLinkedUnorderedList;
import LinkedTree.LinkedHeap;

import java.util.Iterator;

/**
 * A classe Network estende a classe NetworkMatrizAdjacencia e implementa a interface NetworkMatrizADT.
 * Representa uma rede de grafos com funcionalidades específicas para manipulação de pesos em arestas
 * e cálculo de caminhos mais curtos entre vértices.
 *
 * @param <T> O tipo dos vértices da rede.
 * @author Artur Pinto
 * Nº mecanográfico: 8230138
 * @author Francisco Oliveira
 * Nº mecanográfico: 8230148
 * @version 1.0
 */
public class Network<T> extends NetworkMatrizAdjacencia<T> implements NetworkMatrizADT<T> {

    /**
     * Construtor padrão da classe Network. Inicializa a rede chamando o construtor da classe pai.
     */
    public Network() {
        super();
    }

    /**
     * Retorna o peso da aresta entre dois vértices do grafo.
     *
     * @param vertex O primeiro vértice.
     * @param vertex2 O segundo vértice.
     * @return O peso da aresta entre os dois vértices, ou infinito positivo se não houver aresta.
     */
    @Override
    public double getWeightEdge(T vertex, T vertex2) {
        int ind_vertex = getIndex(vertex);
        int ind_vertex2 = getIndex(vertex2);

        return getWeightEdge(ind_vertex, ind_vertex2);
    }

    /**
     * Retorna o peso da aresta entre dois vértices do grafo, dado seus índices.
     *
     * @param vertex O índice do primeiro vértice.
     * @param vertex2 O índice do segundo vértice.
     * @return O peso da aresta entre os dois vértices, ou infinito positivo se não houver aresta.
     */
    private double getWeightEdge(int vertex, int vertex2) {
        double weight = 0;

        if(indexIsValid(vertex) || indexIsValid(vertex2)) {
            weight = this.adjMatrix[vertex][vertex2];
        } else {
            weight = Double.POSITIVE_INFINITY;
        }

        return weight;
    }

    /**
     * Atualiza o peso das arestas conectadas ao vértice fornecido.
     *
     * @param vertex O vértice cujas arestas serão atualizadas.
     * @param weight O novo peso para as arestas conectadas ao vértice.
     */
    @Override
    public void updateWeightEdge(T vertex, double weight) {
        int index1 = getIndex(vertex);

        if (indexIsValid(index1)) {
            Iterator<T> itr = this.iteratorNextVertexs(vertex);

            while (itr.hasNext()) {
                T element = itr.next();
                int index2 = getIndex(element);

                if(indexIsValid(index2)) {
                    this.adjMatrix[index1][index2] = weight;
                    this.adjMatrix[index2][index1] = weight;
                }
            }
        }
    }

    /**
     * Calcula o caminho mais curto entre dois vértices utilizando o algoritmo de Dijkstra.
     *
     * @param startVertex O vértice de origem.
     * @param finalVertex O vértice de destino.
     * @return Um iterador contendo os vértices do caminho mais curto, ou um iterador vazio se não houver caminho.
     */
    @Override
    public Iterator<T> shortestPath(T startVertex, T finalVertex) {
        UnorderedListADT<T> listResult = new LinearLinkedUnorderedList<>();
        int startIndex = getIndex(startVertex);
        int finalIndex = getIndex(finalVertex);

        if (!indexIsValid(startIndex) || !indexIsValid(finalIndex)) {
            return listResult.iterator();
        }

        double[] distances = new double[numVertices];
        int[] predecessors = new int[numVertices];
        boolean[] visited = new boolean[numVertices];
        HeapADT<T> minHeap = new LinkedHeap<>();

        for (int i = 0; i < numVertices; i++) {
            distances[i] = Double.MAX_VALUE;
            predecessors[i] = -1;
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
                            predecessors[neighborIndex] = currentIndex;
                            minHeap.addElement(element);
                        }
                    }
                }
            }
        }

        if (distances[finalIndex] == Double.MAX_VALUE) {
            return new LinearLinkedUnorderedList<T>().iterator();
        }

        int step = finalIndex;

        while (step != -1) {
            listResult.addToFront(this.vertices[step]);
            step = predecessors[step];
        }

        return listResult.iterator();
    }

    /**
     * Retorna o número mínimo de arestas entre dois vértices utilizando o algoritmo de Dijkstra.
     *
     * @param startVertex O vértice de origem.
     * @param finalVertex O vértice de destino.
     * @return O número mínimo de arestas entre os vértices, ou infinito positivo se não houver caminho.
     */
    @Override
    public double shortestPathArest(T startVertex, T finalVertex) {
        int startIndex = getIndex(startVertex);
        int finalIndex = getIndex(finalVertex);

        if (!indexIsValid(startIndex) || !indexIsValid(finalIndex)) {
            return Double.MAX_VALUE;
        }

        double[] distances = new double[numVertices];
        int[] num_arestas = new int[numVertices];
        boolean[] visited = new boolean[numVertices];
        HeapADT<T> minHeap = new LinkedHeap<>();

        for (int i = 0; i < numVertices; i++) {
            distances[i] = Double.MAX_VALUE;
            num_arestas[i] = -1;
            visited[i] = false;
        }

        distances[startIndex] = 0;
        num_arestas[startIndex] = 0;
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
                            num_arestas[neighborIndex] = num_arestas[currentIndex] + 1;
                            minHeap.addElement(element);
                        }
                    }
                }
            }
        }

        if (distances[finalIndex] == Double.MAX_VALUE) {
            return Double.MAX_VALUE;
        }

        return num_arestas[finalIndex];
    }

    /**
     * Calcula o custo total do caminho mais curto entre dois vértices no grafo,
     * levando em consideração tanto o peso cumulativo das arestas quanto o número de arestas percorridas.
     *
     * @param startVertex o vértice inicial do caminho
     * @param finalVertex o vértice final do caminho
     * @return o custo total do caminho mais curto, combinando o número de arestas e os pesos das arestas,
     *         ou {@code Double.MAX_VALUE} se não existir um caminho entre os vértices
     */
    @Override
    public double shortestPathTotalCustos(T startVertex, T finalVertex) {
        int startIndex = getIndex(startVertex);
        int finalIndex = getIndex(finalVertex);

        if (!indexIsValid(startIndex) || !indexIsValid(finalIndex)) {
            return Double.MAX_VALUE;
        }

        double[] distances = new double[numVertices];
        int[] num_arestas = new int[numVertices];
        boolean[] visited = new boolean[numVertices];
        HeapADT<T> minHeap = new LinkedHeap<>();

        for (int i = 0; i < numVertices; i++) {
            distances[i] = Double.MAX_VALUE;
            num_arestas[i] = -1;
            visited[i] = false;
        }

        distances[startIndex] = 0;
        num_arestas[startIndex] = 0;
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
                            num_arestas[neighborIndex] = num_arestas[currentIndex] + 1;
                            minHeap.addElement(element);
                        }
                    }
                }
            }
        }

        if (distances[finalIndex] == Double.MAX_VALUE) {
            return Double.MAX_VALUE;
        }

        return num_arestas[finalIndex] + distances[finalIndex];
    }
}
