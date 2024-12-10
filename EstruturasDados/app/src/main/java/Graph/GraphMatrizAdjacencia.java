package Graph;

import ArrayList.ArrayUnorderedList;
import Exceptions.EmptyCollectionException;
import Interfaces.GraphADT;
import Queue.LinkedQueue;
import Stacks.LinkedStack;

import java.util.Iterator;

/**
 * A classe {@code GraphMatrizAdjacencia} representa uma implementação de grafo
 * utilizando uma matriz de adjacência. É uma implementação do tipo de dados
 * {@link GraphADT} que permite adicionar e remover vértices e arestas, bem
 * como realizar buscas em largura e profundidade, além de calcular o caminho
 * mais curto entre dois vértices.
 *
 * @param <T> tipo genérico representando os vértices do grafo
 * @author Artur Pinto
 * Nº mecanográfico: 8230138
 * @author Francisco Oliveira
 * Nº mecanográfico: 8230148
 * @version 1.0
 */
public class GraphMatrizAdjacencia<T> implements GraphADT<T> {

    /**
     * A capacidade padrão inicial para o grafo. Define o número inicial de vértices que a rede pode conter.
     */
    protected final int DEFAULT_CAPACITY = 10;

    /**
     * O número de vértices presentes no grafo.
     * Mantém o controle de quantos vértices foram inseridos no grafo até o momento.
     */
    protected int numVertices;

    /**
     * A matriz de adjacência que representa as conexões entre os vértices.
     * Cada posição adjMatrix[i][j] contém o peso da aresta entre o vértice i e o vértice j,
     * ou infinito (Double.POSITIVE_INFINITY) se não houver aresta.
     */
    protected boolean[][] adjMatrix;

    /**
     * Um array contendo os valores dos vértices.
     * Cada posição vertices[i] armazena o valor do vértice de índice i no grafo.
     */
    protected T[] vertices;

    /**
     * Cria um grafo vazio com capacidade inicial padrão.
     */
    public GraphMatrizAdjacencia() {
        numVertices = 0;
        this.adjMatrix = new boolean[DEFAULT_CAPACITY][DEFAULT_CAPACITY];
        this.vertices = (T[]) (new Object[DEFAULT_CAPACITY]);
    }

    /**
     * Expande a capacidade do grafo, dobrando o tamanho da matriz de adjacência
     * e o array de vértices.
     */
    protected void expandCapacity() {
        T[] temp = (T[]) (new Object[(this.vertices.length * 2)]);

        System.arraycopy(this.vertices, 0, temp, 0, this.vertices.length);
        this.vertices = temp;

        boolean[][] tempMatriz = new boolean[this.vertices.length * 2][this.vertices.length * 2];

        for (int i = 0; i < this.adjMatrix.length; i++) {
            System.arraycopy(this.adjMatrix[i], 0, tempMatriz[i], 0, this.adjMatrix[i].length);
        }

        this.adjMatrix = tempMatriz;
    }

    /**
     * Adiciona um vértice ao grafo. Caso a capacidade do grafo seja atingida,
     * a capacidade será expandida automaticamente.
     *
     * @param vertex o vértice a ser adicionado
     */
    @Override
    public void addVertex(T vertex) {
        if (numVertices == vertices.length) {
            expandCapacity();
        }

        vertices[numVertices] = vertex;

        for (int i = 0; i <= numVertices; i++) {
            adjMatrix[numVertices][i] = false;
            adjMatrix[i][numVertices] = false;
        }

        numVertices++;
    }

    /**
     * Remove um vértice do grafo.
     *
     * @param vertex o vértice a ser removido
     * @throws EmptyCollectionException caso o vértice não exista
     */
    @Override
    public void removeVertex(T vertex) throws EmptyCollectionException {
        int indexVertex = getIndex(vertex);

        if (indexVertex == -1) {
            throw new EmptyCollectionException("O vertice que introduziu não existe");
        }

        for (int i = 0; i <= numVertices; i++) {
            removeEdge(indexVertex, i);
        }

        this.vertices[indexVertex] = null;
        numVertices--;

        for (int i = indexVertex; i < numVertices; i++) {
            this.vertices[i] = this.vertices[i + 1];
            this.adjMatrix[i] = this.adjMatrix[i + 1];
        }

        this.vertices[numVertices] = null;
        this.adjMatrix[numVertices] = null;
    }

    /**
     * Obtém o índice de um vértice no array de vértices.
     *
     * @param vertex o vértice a ser procurado
     * @return o índice do vértice ou -1 se não encontrado
     */
    protected int getIndex(T vertex) {
        int i = 0;
        boolean find = false;

        while (i < this.numVertices && !find) {
            if (this.vertices[i].equals(vertex)) {
                find = true;
            } else {
                i++;
            }
        }

        if (!find) {
            i = -1;
        }

        return i;
    }

    /**
     * Verifica se um índice é válido, ou seja, se está dentro dos limites do grafo.
     *
     * @param index o índice a ser verificado
     * @return {@code true} se o índice for válido, {@code false} caso contrário
     */
    protected boolean indexIsValid(int index) {
        boolean valid = false;

        if (index >= 0 && index < this.vertices.length && this.vertices[index] != null) {
            valid = true;
        }

        return valid;
    }

    /**
     * Verifica se o índice de um vértice é válido.
     *
     * @param vertex o vértice a ser verificado
     * @return {@code true} se o índice do vértice for válido, {@code false} caso contrário
     */
    protected boolean indexIsValid(T vertex) {
        boolean valid = false;

        if (getIndex(vertex) >= 0) {
            valid = true;
        }

        return valid;
    }

    /**
     * Adiciona uma aresta entre dois vértices do grafo.
     *
     * @param vertex1 o primeiro vértice
     * @param vertex2 o segundo vértice
     */
    @Override
    public void addEdge(T vertex1, T vertex2) {
        addEdge(getIndex(vertex1), getIndex(vertex2));
    }

    /**
     * Adiciona uma aresta entre dois índices de vértices do grafo.
     *
     * @param index1 o índice do primeiro vértice
     * @param index2 o índice do segundo vértice
     */
    public void addEdge(int index1, int index2) {
        if (indexIsValid(index1) && indexIsValid(index2)) {
            adjMatrix[index1][index2] = true;
            adjMatrix[index2][index1] = true;
        }
    }

    /**
     * Remove uma aresta entre dois vértices do grafo.
     *
     * @param vertex1 o primeiro vértice
     * @param vertex2 o segundo vértice
     */
    @Override
    public void removeEdge(T vertex1, T vertex2) {
        removeEdge(getIndex(vertex1), getIndex(vertex1));
    }

    /**
     * Remove uma aresta entre dois índices de vértices do grafo.
     *
     * @param index1 o índice do primeiro vértice
     * @param index2 o índice do segundo vértice
     */
    public void removeEdge(int index1, int index2) {
        if (indexIsValid(index1) && indexIsValid(index2)) {
            adjMatrix[index1][index2] = false;
            adjMatrix[index2][index1] = false;
        }
    }


    /**
     * Retorna um iterador que realiza uma busca em largura (BFS) começando
     * no vértice especificado.
     *
     * @param startVertex o vértice inicial da busca
     * @return um iterador com o resultado da busca em largura
     */
    @Override
    public Iterator<T> iteratorBFS(T startVertex) {
        int indexVertex = getIndex(startVertex);
        Integer x;
        LinkedQueue<Integer> traversalQueue = new LinkedQueue<>();
        ArrayUnorderedList<T> resultList = new ArrayUnorderedList<T>();

        if (!indexIsValid(startVertex)) {
            return resultList.iterator();
        }

        boolean[] visited = new boolean[numVertices];

        for (int i = 0; i < numVertices; i++) {
            visited[i] = false;
        }

        traversalQueue.enqueue(indexVertex);
        visited[indexVertex] = true;

        while (!traversalQueue.isEmpty()) {
            x = traversalQueue.dequeue();
            resultList.addToRear(vertices[x.intValue()]);
            /**
             * Find all vertices adjacent to x that have not been visited and
             * queue them up
             */
            for (int i = 0; i < numVertices; i++) {
                if (adjMatrix[x.intValue()][i] && !visited[i]) {
                    traversalQueue.enqueue(i);
                    visited[i] = true;
                }
            }
        }

        return resultList.iterator();
    }

    /**
     * Retorna um iterador que realiza uma busca em profundidade (DFS) começando
     * no vértice especificado.
     *
     * @param startVertex o vértice inicial da busca
     * @return um iterador com o resultado da busca em profundidade
     */
    @Override
    public Iterator<T> iteratorDFS(T startVertex) {
        Integer x;
        int startIndex = getIndex(startVertex);
        boolean found;
        LinkedStack<Integer> traversalStack = new LinkedStack<>();
        ArrayUnorderedList<T> resultList = new ArrayUnorderedList<T>();
        boolean[] visited = new boolean[numVertices];

        if (!indexIsValid(startIndex)) {
            return resultList.iterator();
        }

        for (int i = 0; i < numVertices; i++) {
            visited[i] = false;
        }

        traversalStack.push(startIndex);
        resultList.addToRear(vertices[startIndex]);
        visited[startIndex] = true;

        while (!traversalStack.isEmpty()) {
            x = traversalStack.peek();
            found = false;
            /**
             * Find a vertex adjacent to x that has not been visited and push it
             * on the stack
             */
            for (int i = 0; (i < numVertices) && !found; i++) {
                if (adjMatrix[x.intValue()][i] && !visited[i]) {
                    traversalStack.push(i);
                    resultList.addToRear(vertices[i]);
                    visited[i] = true;
                    found = true;
                }
            }
            if (!found && !traversalStack.isEmpty()) {
                traversalStack.pop();
            }
        }

        return resultList.iterator();
    }

    /**
     * Retorna um iterador que realiza a busca do caminho mais curto entre dois
     * vértices usando o algoritmo de busca em largura (BFS).
     *
     * @param startVertex  o vértice inicial da busca
     * @param targetVertex o vértice de destino da busca
     * @return um iterador com o caminho mais curto encontrado
     */
    @Override
    public Iterator<T> iteratorShortestPath(T startVertex, T targetVertex) {
        ArrayUnorderedList<T> resultList = new ArrayUnorderedList<T>();
        int start_index = getIndex(startVertex);
        int final_index = getIndex(targetVertex);

        if (!indexIsValid(start_index) || !indexIsValid(final_index)) {
            return resultList.iterator();
        }

        LinkedQueue<Integer> traversalQueue = new LinkedQueue<>();
        int index = start_index;
        int[] comprimeto = new int[numVertices];
        int[] antecessor = new int[numVertices];
        boolean[] visited = new boolean[numVertices];

        for (int i = 0; i < numVertices; i++) {
            visited[i] = false;
            comprimeto[i] = Integer.MAX_VALUE;
            antecessor[i] = -1;
        }

        traversalQueue.enqueue(start_index);
        visited[start_index] = true;
        comprimeto[start_index] = 0;
        antecessor[start_index] = -1;

        while (!traversalQueue.isEmpty() && (index != final_index)) {
            index = traversalQueue.dequeue();

            for (int i = 0; i < numVertices; i++) {
                if (adjMatrix[index][i] && !visited[i]) {
                    comprimeto[i] = comprimeto[index] + 1;
                    antecessor[i] = index;
                    traversalQueue.enqueue(i);
                    visited[i] = true;
                }
            }
        }

        //Não existe caminho
        if (index != final_index) {
            return resultList.iterator();
        }

        LinkedStack<Integer> stack = new LinkedStack<>();
        index = final_index;
        stack.push(index);

        do {
            index = antecessor[index];
            stack.push(index);
        } while (index != start_index);

        while (!stack.isEmpty()) {
            resultList.addToRear(vertices[stack.pop()]);
        }

        return resultList.iterator();
    }

    /**
     * Verifica se o grafo está vazio.
     *
     * @return {@code true} se o grafo estiver vazio, {@code false} caso contrário
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
     * Verifica se o grafo é conexo, ou seja, se existe um caminho entre todos os
     * vértices.
     *
     * @return {@code true} se o grafo for conexo, {@code false} caso contrário
     */
    @Override
    public boolean isConnected() {
        boolean connected = false;

        if (this.numVertices > 0) {
            Iterator<T> itr = iteratorBFS(vertices[0]);
            int visti_cont = 0;

            while (itr.hasNext()) {
                itr.next();
                visti_cont++;
            }

            if (visti_cont == numVertices) {
                connected = true;
            }
        }

        return connected;
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
