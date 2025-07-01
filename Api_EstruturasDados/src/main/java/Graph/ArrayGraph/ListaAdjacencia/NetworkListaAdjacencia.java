package Graph.ArrayGraph.ListaAdjacencia;

import Interfaces.Graph.Network.NetworkADT;
import LinkedList.LinearLinkedOrderedList;
import java.util.Iterator;


/**
 * Representa uma rede de grafos implementada utilizando lista de adjacência.
 * Esta classe é responsável por manipular grafos representados por listas de adjacência,
 * incluindo a adição de arestas, a pesquisa de caminhos e a iteração pelos vértices.
 *
 * @param <T> o tipo dos vértices no grafo
 * @author Artur Pinto
 * Nº mecanográfico: 8230138
 * @author Francisco Oliveira
 * Nº mecanográfico: 8230148
 * @version 1.0
 */
public class NetworkListaAdjacencia<T> extends GraphListaAdjacencia<T> implements NetworkADT<T> {

    /**
     * Construtor default para a classe NetworkListaAdjacencia.
     * Inicializa uma nova instância da classe sem configurar nenhuma propriedade específica.
     */
    public NetworkListaAdjacencia() {
        super();
    }

    /**
     * Array de listas ligadas ordenadas que representa a lista de adjacência para cada vértice.
     * Cada índice da lista contém os vértices adjacentes a um vértice específico.
     */
    protected LinearLinkedOrderedList<T>[] listaAdj;

    /**
     * Adiciona uma aresta entre dois vértices no grafo com um peso especificado.
     * A aresta é adicionada em ambas as direções, já que o grafo é não direcionado.
     *
     * @param vertex1 o primeiro vértice da aresta
     * @param vertex2 o segundo vértice da aresta
     * @param weight  o peso da aresta
     */
    @Override
    public void addEdge(T vertex1, T vertex2, double weight) {
        if (indexIsValid(vertex1) && indexIsValid(vertex2)) {
            this.listaAdj[getIndex(vertex1)].add(vertex2);
            this.listaAdj[getIndex(vertex2)].add(vertex1);
        }
    }

    /**
     * Edita o peso de uma aresta entre dois vértices no grafo.
     * Este metodo não altera o peso da aresta, mas sim apenas a lista de adjacências dos vértices.
     *
     * @param vertex1 o primeiro vértice da aresta
     * @param vertex2 o segundo vértice da aresta
     * @param weight  o novo peso da aresta
     */
    public void editWeightEdge(T vertex1, T vertex2, double weight) {
        if (indexIsValid(vertex1) && indexIsValid(vertex2)) {
            this.listaAdj[getIndex(vertex1)].add(vertex2);
            this.listaAdj[getIndex(vertex2)].add(vertex1);
        }
    }

    /**
     * Retorna o peso do caminho mais curto entre dois vértices.
     * Este metodo é um esboço e atualmente retorna 0, sendo necessária a implementação.
     *
     * @param vertex1 o primeiro vértice
     * @param vertex2 o segundo vértice
     * @return o peso do caminho mais curto entre os vértices ou 0 se não implementado
     */
    @Override
    public double shortestPathWeight(T vertex1, T vertex2) {

        return 0;
    }

    /**
     * Retorna um iterador para os vértices adjacentes a um vértice de início.
     * Este metodo está incompleto e retornará nulo por enquanto.
     *
     * @param startVertex o vértice inicial
     * @return um iterador para os vértices adjacentes ao vértice de início
     */
    @Override
    public Iterator<T> iteratorNextVertexs(T startVertex) {
        return null;
    }

    /**
     * Retorna um iterador para os vértices do grafo utilizando uma pesquisa em profundidade (DFS).
     *
     * @return um iterador para os vértices do grafo
     */
    @Override
    public Iterator<T> iterator() {
        return super.iteratorDFS(this.vertices[0]);
    }
}