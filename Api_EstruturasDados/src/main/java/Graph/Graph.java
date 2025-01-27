package Graph;

import Exceptions.ElementNotFoundException;
import Interfaces.GraphADT;

import java.util.Iterator;

public abstract class Graph<T> implements GraphADT<T> {

    protected int numVertices;

    public Graph() {
        this.numVertices = 0;
    }

    @Override
    public abstract void addVertex(T vertex);

    @Override
    public abstract void removeVertex(T vertex) throws ElementNotFoundException;

    @Override
    public abstract void addEdge(T vertex1, T vertex2);

    @Override
    public abstract void removeEdge(T vertex1, T vertex2);

    @Override
    public abstract Iterator<T> iteratorBFS(T startVertex);

    @Override
    public abstract Iterator<T> iteratorDFS(T startVertex);

    @Override
    public abstract Iterator<T> iteratorShortestPath(T startVertex, T targetVertex);

    /**
     * Verifica se o grafo está vazio.
     *
     * @return true se o grafo estiver vazio, false caso contrário
     */
    @Override
    public boolean isEmpty() {
        boolean empty = false;

        if (this.numVertices == 0) {
            empty = true;
        }

        return empty;
    }

    @Override
    public abstract boolean isConnected();

    /**
     * Retorna o número de vértices no grafo.
     *
     * @return o número de vértices no grafo
     */
    @Override
    public int size() {
        return this.numVertices;
    }
}
