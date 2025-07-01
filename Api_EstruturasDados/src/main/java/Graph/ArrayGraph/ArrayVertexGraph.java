package Graph.ArrayGraph;

import Graph.Graph;

public abstract class ArrayVertexGraph<T> extends Graph<T> {

    /**
     * A capacidade default inicial para o grafo.
     * Define o número inicial de vértices que o grafo pode conter.
     */
    protected final int DEFAULT_CAPACITY = 10;

    /**
     * Um array que contém os valores dos vértices.
     * Cada posição vertices[i] armazena o valor do vértice de índice i no grafo.
     */
    protected T[] vertices;

    public ArrayVertexGraph() {
        super();
        this.vertices = (T[]) (new Object[DEFAULT_CAPACITY]);
    }
}
