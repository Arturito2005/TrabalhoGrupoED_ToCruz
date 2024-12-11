package Interfaces;

import java.util.Iterator;

/**
 * Interface que define as operações para uma rede baseada em matriz.
 * Esta interface estende a {@link NetworkADT} para redes que utilizam uma matriz de adjacência.
 *
 * @param <T> o tipo dos elementos armazenados na rede
 * @author Artur Pinto
 * Nº mecanográfico: 8230138
 * @author Francisco Oliveira
 * Nº mecanográfico: 8230148
 * @version 1.0
 */
public interface NetworkMatrizADT<T> extends NetworkADT<T> {

    /**
     * Retorna o peso da aresta entre dois vértices especificados.
     *
     * @param vertex  o primeiro vértice
     * @param vertex2 o segundo vértice
     * @return o peso da aresta entre os dois vértices
     */
    public double getWeightEdge(T vertex, T vertex2);

    /**
     * Atualiza o peso da aresta entre o vértice especificado e o novo peso.
     *
     * @param vertex o vértice de origem
     * @param weight o novo peso da aresta
     */
    public void updateWeightEdge(T vertex, double weight);

    /**
     * Retorna um iterador que representa o caminho mais curto entre dois vértices.
     * O caminho é calculado a partir do vértice inicial até o vértice final.
     *
     * @param startVertex o vértice de início
     * @param finalVertex o vértice final
     * @return um iterador que contém o caminho mais curto entre os dois vértices
     */
    public Iterator<T> shortestPath(T startVertex, T finalVertex);

    /**
     * Retorna o peso total da aresta entre dois vértices, considerando o caminho mais curto.
     *
     * @param startVertex o vértice de início
     * @param finalVertex o vértice final
     * @return o peso total do caminho mais curto entre os dois vértices
     */
    public double shortestPathArest(T startVertex, T finalVertex);

    /**
     * Calcula o custo total do caminho mais curto entre dois vértices na rede,
     * levando em consideração o peso cumulativo das arestas e o número de arestas percorridas.
     *
     * @param startVertex o vértice inicial do caminho
     * @param finalVertex o vértice final do caminho
     * @return o custo total do caminho mais curto, combinando o número de arestas e os pesos das arestas,
     *         ou {@code Double.MAX_VALUE} se não existir um caminho entre os vértices
     */
    public double shortestPathTotalCustos(T startVertex, T finalVertex);
}
