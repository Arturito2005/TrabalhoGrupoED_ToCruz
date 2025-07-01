package Interfaces.Graph.Network;

import Interfaces.Graph.GraphADT;

import java.util.Iterator;

/**
 * NetworkADT define a interface para uma rede.
 *
 * @param <T> o tipo dos elementos armazenados nesta rede
 * @author Artur Pinto
 * Nº mecanográfico: 8230138
 * @author Francisco Oliveira
 * Nº mecanográfico: 8230148
 * @version 1.0
 */
public interface NetworkADT<T> extends Iterable<T>, GraphADT<T> {
    /**
     * Insere uma aresta entre dois vértices deste grafo.
     *
     * @param vertex1 o primeiro vértice
     * @param vertex2 o segundo vértice
     * @param weight  o peso da aresta
     */
    public void addEdge(T vertex1, T vertex2, double weight);

    /**
     * Retorna o peso do caminho mais curto nesta rede.
     *
     * @param vertex1 o primeiro vértice
     * @param vertex2 o segundo vértice
     * @return o peso do caminho mais curto nesta rede
     */
    public double shortestPathWeight(T vertex1, T vertex2);

    /**
     * Retorna um iterador para os próximos vértices a partir do vértice inicial.
     *
     * @param startVertex o vértice de início
     * @return um iterador para os próximos vértices a partir do vértice inicial
     */
    public Iterator<T> iteratorNextVertexs(T startVertex);
}
