package Graph;

import Exceptions.ElementNotFoundException;
import Interfaces.Graph.GraphADT;

import java.util.Iterator;

public abstract class Graph<T> implements GraphADT<T> {

    /**
     * Número atual de vértices
     */
    protected int numVertices;

    public Graph() {
        this.numVertices = 0;
    }

    /**
     * Expande a capacidade do grafo, dobrando o tamanho da matriz de adjacência
     * e o array de vértices.
     */
    protected abstract void expandCapacity();

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

    @Override
    public abstract boolean isConnected();
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
