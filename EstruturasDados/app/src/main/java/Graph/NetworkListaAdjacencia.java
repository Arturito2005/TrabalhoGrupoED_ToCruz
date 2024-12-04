package Graph;

import Interfaces.NetworkADT;
import LinkedList.LinearLinkedOrderedList;

import java.util.Iterator;

public class NetworkListaAdjacencia<T> extends GraphListaAdjacencia<T> implements NetworkADT<T> {
    protected LinearLinkedOrderedList<T>[] listaAdj;
    @Override
    public void addEdge(T vertex1, T vertex2, double weight) {
        if (indexIsValid(vertex1) && indexIsValid(vertex2)) {
            this.listaAdj[getIndex(vertex1)].add(vertex2);
            this.listaAdj[getIndex(vertex2)].add(vertex1);
        }
    }

    public void editWeightEdge(T vertex1, T vertex2, double weight) {
        if (indexIsValid(vertex1) && indexIsValid(vertex2)) {
            this.listaAdj[getIndex(vertex1)].add(vertex2);
            this.listaAdj[getIndex(vertex2)].add(vertex1);
        }
    }
    
    //Fazer 1º o caminho mais curto para o alvo e depois fazer ou shortestPathWeight do alvo para uma saida
    @Override
    public double shortestPathWeight(T vertex1, T vertex2) {

        /*
        * ArrayUnorderedList<T> resultList = new ArrayUnorderedList<T>();
        int start_index = getIndex(vertex1);
        int final_index = getIndex(vertex2);

        if (!indexIsValid(start_index) || !indexIsValid(final_index)) {
            //return resultList.iterator();
            return 0;
        }

        LinkedQueue<Integer> traversalQueue = new LinkedQueue<>();
        int index = start_index;
        int[] comprimeto = new int[numVertices];
        int[] antecessor = new int[numVertices];
        boolean[] visited = new boolean[numVertices];

        for (int i = 0; i < numVertices; i++) {
            visited[i] = false;
        }

        traversalQueue.enqueue(start_index);
        visited[start_index] = true;
        comprimeto[start_index] = 0;
        antecessor[start_index] = -1;

        while (!traversalQueue.isEmpty() && (index != final_index)) {
            index = traversalQueue.dequeue();

            Iterator<T> itr = listaAdj[index].iterator();

            while (itr.hasNext()) {
                T element = itr.next();
                int index_ele = getIndex(element);

                if (indexIsValid(index_ele) && !visited[index_ele]) {
                    comprimeto[index_ele] = comprimeto[index_ele] + 1;
                    antecessor[index_ele] = index;
                    traversalQueue.enqueue(index_ele);
                    visited[index_ele] = true;
                }
            }
        }

        //Não existe caminho
        if (index != final_index) {
            return resultList.iterator();
        }

        LinkedStack<Integer> stack = new LinkedStack<>();
        index = final_index;
        stack.push(final_index);

        do {
            index = antecessor[index];
            stack.push(index);
        } while (index != start_index);

        while (!stack.isEmpty()) {
            resultList.addToRear(vertices[stack.pop()]);
        }

        return resultList.iterator();
        * */
        return 0;
    }

    @Override
    public Iterator<T> iterator() {
        return super.iteratorDFS(this.vertices[0]);
    }



    protected int[] getEdgeWithWeightOf(double weight, boolean visited[]) {
        return null;
    }

    /**
     * No jogo esta arvore gerador é que nos vai gerar o caminho mais curto do ToCruz até uma sala, vai ter
     * de ser chamado constantemente para ser atualizada de acordo com a movimentação dos inimigos
     *
     * Returns a minimum spanning tree of the network.
     *
     * @return a minimum spanning tree of the network
     */
   /**
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
