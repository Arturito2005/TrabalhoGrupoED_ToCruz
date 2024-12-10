package Interfaces;

import Exceptions.ElementNotFoundException;

import java.util.Iterator;

/**
 * GraphADT define a interface para uma estrutura de dados de grafo.
 *
 * @param <T> o tipo dos elementos armazenados no grafo
 * @author Artur Pinto
 * Nº mecanográfico: 8230138
 * @author Francisco Oliveira
 * Nº mecanográfico: 8230148
 * @version 1.0
 */
public interface GraphADT<T> {

    /**
     * Adiciona um vértice a este grafo, associando o objeto ao vértice.
     *
     * @param vertex o vértice a ser adicionado a este grafo
     */
    public void addVertex(T vertex);

    /**
     * Remove um único vértice com o valor fornecido deste grafo.
     *
     * @param vertex o vértice a ser removido deste grafo
     */
    public void removeVertex(T vertex) throws ElementNotFoundException;

    /**
     * Insere uma aresta entre dois vértices deste grafo.
     *
     * @param vertex1 o primeiro vértice
     * @param vertex2 o segundo vértice
     */
    public void addEdge(T vertex1, T vertex2);

    /**
     * Remove uma aresta entre dois vértices deste grafo.
     *
     * @param vertex1 o primeiro vértice
     * @param vertex2 o segundo vértice
     */
    public void removeEdge(T vertex1, T vertex2);

    /**
     * Retorna um iterador de busca em largura (BFS) começando com o vértice fornecido.
     *
     * @param startVertex o vértice inicial
     * @return um iterador de busca em largura começando no vértice fornecido
     */
    public Iterator<T> iteratorBFS(T startVertex);

    /**
     * Retorna um iterador de busca em profundidade (DFS) começando com o vértice fornecido.
     *
     * @param startVertex o vértice inicial
     * @return um iterador de busca em profundidade começando no vértice fornecido
     */
    public Iterator<T> iteratorDFS(T startVertex);

    /**
     * Retorna um iterador que contém o caminho mais curto entre os dois vértices.
     *
     * @param startVertex  o vértice inicial
     * @param targetVertex o vértice final
     * @return um iterador que contém o caminho mais curto entre os dois vértices
     */
    public Iterator<T> iteratorShortestPath(T startVertex, T targetVertex);

    /**
     * Retorna verdadeiro se este grafo estiver vazio, falso caso contrário.
     *
     * @return verdadeiro se este grafo estiver vazio
     */
    public boolean isEmpty();

    /**
     * Retorna verdadeiro se este grafo estiver conectado, falso caso contrário.
     *
     * @return verdadeiro se este grafo estiver conectado
     */
    public boolean isConnected();

    /**
     * Retorna o número de vértices neste grafo.
     *
     * @return o número inteiro de vértices neste grafo
     */
    public int size();

    /**
     * Retorna uma representação em string da matriz de adjacência.
     *
     * @return uma representação em string da matriz de adjacência
     */
    @Override
    public String toString();
}
