package Graph.ArrayGraph.GraphPesosNosVertices;

import ArrayList.ArrayUnorderedList;
import Exceptions.ElementNotFoundException;
import Graph.ArrayGraph.MatrizAdjacencia.GraphMatrizAdjacencia;
import Interfaces.Graph.GraphPesoVertexADT;
import Interfaces.Heap.HeapADT;
import Interfaces.List.UnorderedListADT;
import Interfaces.Stack.StackADT;
import LinkedTree.LinkedHeap;
import Stacks.LinkedStack;

import java.util.Iterator;

/*
 * Precisa de testes (A classe que tem para aqui foi o ChatGpt, que meteu
 * e ajuda a controlar a distancia total e o numero de vertice totais)
 * */
public class GraphMatrizPesoVertex<T extends Vertex> extends GraphMatrizAdjacencia<T> implements GraphPesoVertexADT<T> {

    public void updateWeightVertice(T vertex, double weight) throws ElementNotFoundException {
        int indexVertex = getIndex(vertex);

        if (!indexIsValid(indexVertex)) {
            throw new ElementNotFoundException("O vertice introduzido é inválido!");
        }

        vertex.setWeight(weight);
    }

    private Iterator<T> nextVertexIterator(int vertex, boolean visited[]) {
        UnorderedListADT<T> adjacentes = new ArrayUnorderedList<T>();

        if (!indexIsValid(vertex)) {
            return adjacentes.iterator();
        }

        for (int i = 0; i < numVertices; i++) {
            if (adjMatrix[vertex][i] && !visited[i]) {
                adjacentes.addToRear(vertices[i]);
            }
        }
        return adjacentes.iterator();
    }

    /**
     * Classe auxiliar para armazenar informações de cada vértice durante o algoritmo.
     * Ela encapsula o vértice, a distância acumulada e o número de arestas (hops) utilizados,
     * permitindo que o heap ordene primeiramente pela menor distância e, em caso de empate, pelo menor número de arestas.
     */
    private class Node implements Comparable<Node> {
        T vertex;
        double distance;
        int hops;

        public Node(T vertex, double distance, int hops) {
            this.vertex = vertex;
            this.distance = distance;
            this.hops = hops;
        }

        @Override
        public int compareTo(Node other) {
            int cmp = Double.compare(this.distance, other.distance);
            if (cmp != 0) {
                return cmp;
            }
            return Integer.compare(this.hops, other.hops);
        }
    }

    /**
     * Retorna o caminho mais curto entre dois pontos, tendo em consideração o menor peso possivel e o
     * menor caminho possivel
     *
     * @param startVertex  o vértice inicial da pesquisa
     * @param targetVertex o vértice de destino da pesquisa
     * @return um iterador com o caminho mais curto encontrado
     */
    @Override
    public Iterator<T> iteratorShortestPath(T startVertex, T targetVertex) {
        ArrayUnorderedList<T> resultList = new ArrayUnorderedList<T>();
        int startIndex = getIndex(startVertex);
        int finalIndex = getIndex(targetVertex);

        if (!indexIsValid(startIndex) || !indexIsValid(finalIndex)) {
            return resultList.iterator();
        }

        double[] distances = new double[numVertices];
        int[] arestas = new int[numVertices];
        T[] antecessor = (T[]) new Vertex[numVertices];
        boolean[] visited = new boolean[numVertices];

        for (int i = 0; i < numVertices; i++) {
            distances[i] = Double.MAX_VALUE;
            arestas[i] = Integer.MAX_VALUE;
            visited[i] = false;
            antecessor[i] = null;
        }

        distances[startIndex] = 0;
        arestas[startIndex] = 0;

        HeapADT<Node> minHeap = new LinkedHeap<>();
        minHeap.addElement(new Node(startVertex, 0, 0));

        int currentIndex = -1;

        while (!minHeap.isEmpty() && currentIndex != finalIndex) {
            Node currentNode = minHeap.removeMin();
            T currentVertex = currentNode.vertex;
            currentIndex = getIndex(currentVertex);

            if (!visited[currentIndex]) {
                visited[currentIndex] = true;

                Iterator<T> itr = nextVertexIterator(currentIndex, visited);
                while (itr.hasNext()) {
                    T neighbor = itr.next();
                    int neighborIndex = getIndex(neighbor);

                    if (indexIsValid(neighborIndex) && !visited[neighborIndex]) {
                        double newDistance = distances[currentIndex] + neighbor.getWeight();
                        int newAresta = arestas[currentIndex] + 1;

                        // Comparo, pela menor distancia ou se a distancia for igual e o numero de arestas for menor
                        if (newDistance < distances[neighborIndex] ||
                                (Double.compare(newDistance, distances[neighborIndex]) == 0 && newAresta < arestas[neighborIndex])) {
                            distances[neighborIndex] = newDistance;
                            arestas[neighborIndex] = newAresta;
                            antecessor[neighborIndex] = currentVertex;
                            minHeap.addElement(new Node(neighbor, newDistance, newAresta));
                        }
                    }

                }
            }
        }

        if (distances[finalIndex] == Double.MAX_VALUE) {
            return resultList.iterator();
        }

        StackADT<T> stack = new LinkedStack<>();
        int current = finalIndex;
        boolean invalidPred = false;

        while (current != startIndex && !invalidPred) {
            stack.push(vertices[current]);
            T pred = antecessor[current];

            if (pred == null) {
                invalidPred = true;
            } else {
                current = getIndex(pred);
            }
        }

        if (invalidPred) {
            return resultList.iterator();
        }

        stack.push(vertices[startIndex]);
        while (!stack.isEmpty()) {
            resultList.addToRear(stack.pop());
        }

        return resultList.iterator();
    }


}
