package Graph;

import ArrayList.ArrayUnordered;
import ArrayList.ArrayUnorderedList;
import Interfaces.ArrayUnorderedADT;
import Interfaces.NetworkMatrizADT;
import Interfaces.StackADT;
import Interfaces.UnorderedListADT;
import LinkedList.LinearLinkedUnorderedList;
import Stacks.LinkedStack;
import java.util.Iterator;

public class Network<T> extends NetworkMatrizAdjacencia<T> implements NetworkMatrizADT<T> {

    private ArrayUnorderedADT<T>[] listAdj;

    public Network() {
        super();
        this.listAdj = (ArrayUnorderedADT<T>[]) new ArrayUnordered[DEFAULT_CAPACITY];
        for (int i = 0; i < DEFAULT_CAPACITY; i++) {
            listAdj[i] = new ArrayUnordered<T>();
        }
    }

    @Override
    protected void expandadweightMatrix() {
        super.expandadweightMatrix();

        ArrayUnorderedADT<T>[] temp = (ArrayUnorderedADT<T>[]) new ArrayUnordered[listAdj.length * 2];
        System.arraycopy(listAdj, 0, temp, 0, listAdj.length);
        for (int i = listAdj.length; i < temp.length; i++) {
            temp[i] = new ArrayUnordered<T>();
        }

        listAdj = temp;
    }
    @Override
    public void addVertex(T vertex) {
        if(this.vertices.length == this.numVertices) {
            this.expandadweightMatrix();
        }

        super.addVertex(vertex);
    }

    @Override
    public void addEdge(T vertex1, T vertex2, double weight) {
        super.addEdge(vertex1, vertex2, weight);
        int index1 = getIndex(vertex1);
        int index2 = getIndex(vertex2);

        if (indexIsValid(index1) && indexIsValid(index2)) {
            this.listAdj[index1].addToRear(vertex2);
            this.listAdj[index2].addToRear(vertex1);
        }
    }

    @Override
    public double getWeightEdge(T vertex, T vertex2) {
        int ind_vertex = getIndex(vertex);
        int ind_vertex2 = getIndex(vertex2);

        return getWeightEdge(ind_vertex, ind_vertex2);
    }

    private double getWeightEdge(int vertex, int vertex2) {
        double weight = 0;

        if(indexIsValid(vertex) && indexIsValid(vertex2)) {
            weight = this.adjMatrix[vertex][vertex2];
        } else {
            weight = Double.NEGATIVE_INFINITY;
        }

        return weight;
    }

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

    @Override
    public Iterator<T> iteratorNextVertexs(T startVertex) {
        ArrayUnorderedList<T> resultList = new ArrayUnorderedList<T>();
        int startIndex = getIndex(startVertex);

        if (!indexIsValid(startVertex)) {
            return resultList.iterator();
        }

        return listAdj[startIndex].iterator();
    }

    /*
    * Não funciona
    * */
    @Override
    public Iterator<T> shortestPath(T vertex1, T vertex2) {
        int start_index = getIndex(vertex1);
        int final_index = getIndex(vertex2);
        UnorderedListADT<T> listaResult = new LinearLinkedUnorderedList<>();

        if (!indexIsValid(start_index) || !indexIsValid(final_index)) {
            return listaResult.iterator();
        }

        double[] distances = new double[numVertices];
        boolean[] visited = new boolean[numVertices];
        int[] edgeCount = new int[numVertices];
        int[] predecessors = new int[numVertices];

        for (int i = 0; i < numVertices; i++) {
            distances[i] = Double.POSITIVE_INFINITY;
            edgeCount[i] = Integer.MAX_VALUE;
            visited[i] = false;
            predecessors[i] = -1;
        }

        edgeCount[start_index] = 0;
        distances[start_index] = 0;
        for (int count = 0; count < numVertices - 1; count++) {
            int u = getNoMiniumDistance(distances, visited);
            if (u == -1) {
                break;
            }

            visited[u] = true;
            for (int v = 0; v < numVertices; v++) {
                if (!visited[v] && adjMatrix[u][v] >= 0 && distances[u] + adjMatrix[u][v] < distances[v]) {
                    double newDistance = distances[u] + adjMatrix[u][v];

                    if (newDistance < distances[v]) {
                        distances[v] = newDistance;
                        edgeCount[v] = edgeCount[u] + 1;
                        predecessors[v] = u;
                    } else if (newDistance == distances[v] && edgeCount[u] + 1 < edgeCount[v]) {
                        edgeCount[v] = edgeCount[u] + 1;
                        predecessors[v] = u;
                    }
                }
            }
        }

        if (distances[final_index] == Double.POSITIVE_INFINITY) {
            return listaResult.iterator();
        }

        StackADT<Integer> stack = new LinkedStack<>();
        int index = final_index;
        stack.push(index);

        while (index != start_index) {
            index = predecessors[index];
            stack.push(index);
        }

        while (!stack.isEmpty()) {
            listaResult.addToRear(vertices[stack.pop()]);
        }

        return listaResult.iterator();
    }

/*
*     public double shortestPath2(T vertex1, T vertex2) {
        int start_index = getIndex(vertex1);
        int final_index = getIndex(vertex2);

        if (!indexIsValid(start_index) || !indexIsValid(final_index)) {
            return -1;
        }

        double[] distances = new double[numVertices];
        boolean[] visited = new boolean[numVertices];
        int[] predecessors = new int[numVertices];
        ArrayHeap<T> miniheap = new ArrayHeap<>();
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
                if (!visited[v] && adjMatrix[u][v] > 0 && distances[u] + adjMatrix[u][v] < distances[v]) {
                    distances[v] = distances[u] + adjMatrix[u][v];
                    predecessors[v] = u;
                }
            }
        }

        if (distances[final_index] == Double.POSITIVE_INFINITY) {
            return -1;
        }

        return distances[final_index];
    }*/

}
