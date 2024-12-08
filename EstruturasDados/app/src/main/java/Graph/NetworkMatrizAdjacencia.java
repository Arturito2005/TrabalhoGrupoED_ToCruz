package Graph;

import Interfaces.NetworkADT;
import LinkedTree.LinkedHeap;
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

    @Override
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

        for (int i = this.adjMatrix.length; i < tempMatriz.length; i++) {
            for (int j = this.adjMatrix.length; j < tempMatriz.length; j++) {
                tempMatriz[i][j] = Double.NEGATIVE_INFINITY;
            }
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
    protected int getNoMiniumDistance(double[] distances, boolean[] visited) {
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

    /**
     * Não funciona!!!!!!!!!!!!!
     *
     * Calcula o caminho mais curto entre dois vértices, levando em consideração o custo das arestas e o número de arestas
     * no caminho. Em caso de empate no custo, o caminho com menos arestas é priorizado.
     *
     * @param vertex1 O vértice de origem.
     * @param vertex2 O vértice de destino.
     * @return O custo do caminho mais curto entre os dois vértices. Retorna -1 se não houver caminho entre eles.
     */
    @Override
    public double shortestPathWeight(T vertex1, T vertex2) {
        int start_index = getIndex(vertex1);
        int final_index = getIndex(vertex2);

        if (!indexIsValid(start_index) || !indexIsValid(final_index)) {
            return -1;
        }

        double[] custos = new double[numVertices];
        boolean[] visited = new boolean[numVertices];
        int[] comprimento = new int[numVertices];

        for (int i = 0; i < numVertices; i++) {
            custos[i] = Double.POSITIVE_INFINITY;
            comprimento[i] = Integer.MAX_VALUE;
            visited[i] = false;
        }

        comprimento[start_index] = 0;
        custos[start_index] = 0;
        for (int count = 0; count < numVertices - 1; count++) {
            int u = getNoMiniumDistance(custos, visited);
            if (u == -1) {
                break;
            }

            visited[u] = true;
            for (int v = 0; v < numVertices; v++) {
                if (!visited[v] && adjMatrix[u][v] >= 0) {
                    double newCusto = custos[u] + adjMatrix[u][v];

                    if (newCusto < custos[v] || (newCusto == custos[v] && comprimento[u] + 1 < comprimento[v])) {
                        custos[v] = newCusto;
                        comprimento[v] = comprimento[u] + 1;
                    }
                }
            }
        }

        if (custos[final_index] == Double.POSITIVE_INFINITY) {
            return -1;
        }

        double tot_custos = 0;
        double tot_comp = 0;
        for(int i = start_index; i < final_index; i++ ) {
            tot_custos += custos[i];
            tot_comp += comprimento[i];
        }

        return custos[final_index] + comprimento[final_index];
    }

    /*
    * public double shortestPathWeight2(T vertex1, T vertex2) {
        int start_index = getIndex(vertex1);
        int final_index = getIndex(vertex2);

        if (!indexIsValid(start_index) || !indexIsValid(final_index)) {
            return -1;
        }

        double[] custos = new double[numVertices];
        boolean[] visited = new boolean[numVertices];
        int[] comprimento = new int[numVertices];

        for (int i = 0; i < numVertices; i++) {
            custos[i] = Double.POSITIVE_INFINITY;
            comprimento[i] = Integer.MAX_VALUE;
            visited[i] = false;
        }

        comprimento[start_index] = 0;
        custos[start_index] = 0;
        for (int count = 0; count < numVertices - 1; count++) {
            int u = getNoMiniumDistance(custos, visited);
            if (u == -1) {
                break;
            }

            visited[u] = true;
            for (int v = 0; v < numVertices; v++) {
                if (!visited[v] && adjMatrix[u][v] >= 0) {
                    double newCusto = custos[u] + adjMatrix[u][v];

                    if (newCusto < custos[v] || (newCusto == custos[v] && comprimento[u] + 1 < comprimento[v])) {
                        custos[v] = newCusto;
                        comprimento[v] = comprimento[u] + 1;
                    }
                }

            }
        }

        if (custos[final_index] == Double.POSITIVE_INFINITY) {
            return -1;
        }

        double tot_custos = 0;
        double tot_comp = 0;
        for(int i = start_index; i < final_index; i++ ) {
            tot_custos += custos[i];
            tot_comp += comprimento[i];
        }

        return custos[final_index] + comprimento[final_index];
    }
    * */

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
