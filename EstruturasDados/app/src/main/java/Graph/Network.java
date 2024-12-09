package Graph;

import Interfaces.*;
import LinkedList.LinearLinkedUnorderedList;
import LinkedTree.LinkedHeap;
import java.util.Iterator;

public class Network<T> extends NetworkMatrizAdjacencia<T> implements NetworkMatrizADT<T> {

    public Network() {
        super();
    }

    @Override
    public double getWeightEdge(T vertex, T vertex2) {
        int ind_vertex = getIndex(vertex);
        int ind_vertex2 = getIndex(vertex2);

        return getWeightEdge(ind_vertex, ind_vertex2);
    }

    private double getWeightEdge(int vertex, int vertex2) {
        double weight = 0;

        if(indexIsValid(vertex) || indexIsValid(vertex2)) {
            weight = this.adjMatrix[vertex][vertex2];
        } else {
            weight = Double.POSITIVE_INFINITY;
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

    /*
    * Funciona e retorna o caminho mais proximo.
    * */
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

    @Override
    public double shortestPathArest(T startVertex, T finalVertex) {
        int startIndex = getIndex(startVertex);
        int finalIndex = getIndex(finalVertex);

        if (!indexIsValid(startIndex) || !indexIsValid(finalIndex)) {
            return Double.MAX_VALUE;
        }

        double[] distances = new double[numVertices];
        int[] num_arestas = new int[numVertices];
        int[] predecessors = new int[numVertices];
        boolean[] visited = new boolean[numVertices];
        HeapADT<T> minHeap = new LinkedHeap<>();

        for (int i = 0; i < numVertices; i++) {
            distances[i] = Double.MAX_VALUE;
            predecessors[i] = -1;
            num_arestas[i] = -1;
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
                            predecessors[neighborIndex] = currentIndex;
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
}
