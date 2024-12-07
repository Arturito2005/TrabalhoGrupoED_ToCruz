package Graph;

import ArrayList.ArrayUnorderedList;
import Interfaces.NetworkADT;
import LinkedList.LinearLinkedUnorderedList;
import LinkedTree.LinkedHeap;
import Queue.LinkedQueue;
import Stacks.LinkedStack;
import PriorityQueues.PriorityQueue;
import java.util.Iterator;

/*
* Só falta implementar o de dijsktra e acabar o gerardor da menor arvore
* */
public class NetworkMatrizAdjacencia<T> extends GraphMatrizAdjacencia<T> implements NetworkADT<T> {

    protected double[][] adjMatrix;

    public NetworkMatrizAdjacencia() {
        super();
        this.adjMatrix = new double[DEFAULT_CAPACITY][DEFAULT_CAPACITY];
    }


    public void addVertex(T vertex) {
        if(this.vertices.length == this.numVertices) {
            expandadweightMatrix();
        }
        super.addVertex(vertex);
    }
    //Testar
    protected void expandadweightMatrix() {
        super.expandCapacity();
       
        double[][] tempMatriz = new double[this.vertices.length * 2][this.vertices.length * 2];

        for (int i = 0; i < this.adjMatrix.length; i++) {
            System.arraycopy(this.adjMatrix[i], 0, tempMatriz[i], 0, this.adjMatrix[i].length);
        }

        this.adjMatrix = tempMatriz;
    }

    @Override
    public void addEdge(T vertex1, T vertex2, double weight) {
        addEdge(getIndex(vertex1), getIndex(vertex2), weight);
    }

    public void addEdge(int index1, int index2, double weight) {
        if (indexIsValid(index1) && indexIsValid(index2)) {
            this.adjMatrix[index1][index2] = weight;
            this.adjMatrix[index2][index1] = weight;
        }
    }

    /*
    * Usado para atualizar o dano que o ToCruz toma se entrar numa sala.
    * O To Cruz só leva dano se não matar o inimigo com instaKill
    * */
    public void updateWeightEdge(T vertex, double weight) {
        updateWeightEdge(getIndex(vertex), weight);
    }

    public void updateWeightEdge(int index1, double weight) {
        if (indexIsValid(index1)) {
            Iterator<T> itr = this.iteratorNextVertexs(index1);

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

    private int getNoMiniumDistance(double[] distances, boolean[] visited) {
        double minDistance = Double.POSITIVE_INFINITY;
        int minIndex = -1;

        for (int i = 0; i < distances.length; i++) {
            if (!visited[i] && distances[i] < minDistance) {
                minDistance = distances[i];
                minIndex = i;
            }
        }

        return minIndex;
    }

    @Override
    public double shortestPathWeight(T vertex1, T vertex2) {
        int start_index = getIndex(vertex1);
        int final_index = getIndex(vertex2);

        if (!indexIsValid(start_index) || !indexIsValid(final_index)) {
            return -1;
        }

        double damage = 0;
        double[] distances = new double[numVertices];
        boolean[] visited = new boolean[numVertices];

        for (int i = 0; i < numVertices; i++) {
            distances[i] = Double.POSITIVE_INFINITY;
            visited[i] = false;
        }

        distances[start_index] = 0;

        for (int count = 0; count < numVertices - 1; count++) {
            int u = getNoMiniumDistance(distances, visited);

            if (u == -1) {
                break;
            }

            visited[u] = true;

            for (int v = 0; v < numVertices; v++) {
                if (!visited[v] && adjMatrix[u][v] >= 0 && distances[u] + adjMatrix[u][v] < distances[v]) {
                    double value_weight = adjMatrix[u][v];
                    if(value_weight == 0) {
                        value_weight = 1;
                    } else {
                        damage = damage + value_weight/ 2;
                    }
                    distances[v] = distances[u] + value_weight;
                }
            }
        }

        if (distances[final_index] == Double.POSITIVE_INFINITY) {
            return -1;
        }

        return distances[final_index];
    }

    /**
     * Testar (Xico copiei a que tu fizeste para o caminho mais curto e meti para no final retornar o caminho em vez do tamanho)
     * */
    public Iterator<T> iteratorShortestPath(T vertex, T vertex2) {
        int start_index = getIndex(vertex);
        int final_index = getIndex(vertex2);
        LinearLinkedUnorderedList<T> resultList = new LinearLinkedUnorderedList<>();

        if (!indexIsValid(start_index) || !indexIsValid(final_index)) {
            return resultList.iterator();
        }

        int index = start_index;
        double[] distances = new double[numVertices];
        boolean[] visited = new boolean[numVertices];
        int[] predecessors = new int[numVertices];

        for (int i = 0; i < numVertices; i++) {
            distances[i] = Double.POSITIVE_INFINITY;
            visited[i] = false;
            predecessors[i] = -1;
        }

        distances[start_index] = 0;

        for (int count = 0; count < numVertices - 1; count++) {
            int u = getNoMiniumDistance(distances, visited);
            if (u == -1) {
                break;
            }

            visited[u] = true;

            for (int v = 0; v < numVertices; v++) {
                if (!visited[v] && adjMatrix[u][v] >= 0 && distances[u] + adjMatrix[u][v] < distances[v]) {
                    distances[v] = distances[u] + adjMatrix[u][v];
                    predecessors[v] = u;
                }
            }
        }

        if (distances[final_index] == Double.POSITIVE_INFINITY) {
            resultList.iterator();
        }

        LinkedStack<Integer> stack = new LinkedStack<>();
        index = final_index;
        stack.push(index);

        do {
            index = predecessors[index];
            stack.push(index);
        } while (index != start_index);

        while (!stack.isEmpty()) {
            resultList.addToRear(vertices[stack.pop()]);
        }

        return resultList.iterator();
    }

    @Override
    public Iterator<T> iterator() {
        return super.iteratorDFS(this.vertices[0]);
    }

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
     *
     * Returns a minimum spanning tree of the network.
     *
     * @return a minimum spanning tree of the network
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
        //Talvez meter aqui 0;
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
