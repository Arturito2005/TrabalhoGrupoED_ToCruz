package Graph;

import ArrayList.ArrayUnorderedList;
import Exceptions.ElementNotFoundException;
import Exceptions.EmptyCollectionException;
import Interfaces.GraphADT;
import LinkedList.LinearLinkedOrderedList;
import Queue.LinkedQueue;
import Stacks.LinkedStack;

import java.util.Iterator;

/**
 * Classe que implementa um grafo, usa uma lista de adjacência.
 *
 * @param <T> tipo genérico que representa o tipo de dados dos vértices do grafo.
 * @author Artur Pinto
 * Nº mecanográfico: 8230138
 * @author Francisco Oliveira
 * Nº mecanográfico: 8230148
 * @version 1.0
 */
public class GraphListaAdjacencia<T> extends Graph<T> {

    /**
     * Capacidade default do grafo
     */
    protected final int DEFAULT_CAPACITY = 30;

    /**
     * Array de vértices
     */
    protected T[] vertices;

    /**
     * Lista de adjacência como array de ListaLigada
     */
    protected LinearLinkedOrderedList<T>[] listaAdj;

    /**
     * Número atual de vértices
     */
    protected int numVertices;

    /**
     * Construtor do grafo, inicializa a lista de adjacência e o array de vértices.
     */
    public GraphListaAdjacencia() {
        numVertices = 0;
        this.listaAdj = new LinearLinkedOrderedList[DEFAULT_CAPACITY];
        this.vertices = (T[]) (new Object[DEFAULT_CAPACITY]);
    }

    /**
     * Expande a capacidade do grafo, duplicando o tamanho do array de vértices
     * e da lista de adjacência.
     */
    protected void expandCapacity() {
        T[] temp = (T[]) (new Object[(this.vertices.length * 2)]);
        System.arraycopy(this.vertices, 0, temp, 0, this.vertices.length);
        this.vertices = temp;

        LinearLinkedOrderedList<T>[] novoArray = new LinearLinkedOrderedList[listaAdj.length * 2];
        System.arraycopy(listaAdj, 0, novoArray, 0, listaAdj.length);
        listaAdj = novoArray;
    }

    /**
     * Adiciona um vértice ao grafo, expandindo a capacidade do grafo, se necessário.
     * Também associa um objeto ao vértice.
     *
     * @param vertex o vértice a ser adicionado ao grafo
     */
    @Override
    public void addVertex(T vertex) {
        if (numVertices == vertices.length) {
            expandCapacity();
        }

        this.listaAdj[numVertices] = new LinearLinkedOrderedList<>();
        vertices[numVertices] = vertex;

        numVertices++;
    }

    /**
     * Remove um vértice do grafo, excluindo todas as suas arestas.
     * Se o vértice não for encontrado, lança uma exceção.
     *
     * @param vertex o vértice a ser removido
     * @throws ElementNotFoundException se o vértice não for encontrado
     */
    @Override
    public void removeVertex(T vertex) throws ElementNotFoundException {
        int indexVertex = getIndex(vertex);

        if (indexVertex == -1) {
            throw new EmptyCollectionException("O vertice que introduziu não existe");
        }

        while (!this.listaAdj[indexVertex].isEmpty()) {
            T element = this.listaAdj[indexVertex].removeFirst();
            int index = getIndex(element);

            if (indexIsValid(index)) {
                this.listaAdj[index].remove(vertex);
            }
        }

        this.vertices[indexVertex] = null;
        numVertices--;

        for (int i = indexVertex; i < numVertices; i++) {
            this.vertices[i] = this.vertices[i + 1];
            this.listaAdj[i] = this.listaAdj[i + 1];
        }

        this.vertices[numVertices] = null;
        this.listaAdj[numVertices] = null;
    }

    /**
     * Retorna o índice de um vértice no array de vértices. Se o vértice não for encontrado, retorna -1.
     *
     * @param vertex o vértice a ser procurado
     * @return o índice do vértice ou -1 se não for encontrado
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
     * Verifica se o índice é válido.
     *
     * @param index o índice a ser verificado
     * @return true se o índice for válido, false caso contrário
     */
    protected boolean indexIsValid(int index) {
        boolean valid = false;

        if (index >= 0 && this.vertices[index] != null) {
            valid = true;
        }

        return valid;
    }

    /**
     * Verifica se o índice de um vértice é válido.
     *
     * @param vertex o vértice a ser verificado
     * @return true se o índice for válido, false caso contrário
     */
    protected boolean indexIsValid(T vertex) {
        boolean valid = false;

        if (getIndex(vertex) >= 0) {
            valid = true;
        }

        return valid;
    }

    /**
     * Insere uma aresta entre dois vértices do grafo.
     *
     * @param vertex1 o primeiro vértice
     * @param vertex2 o segundo vértice
     */
    @Override
    public void addEdge(T vertex1, T vertex2) {
        int indexVertex1 = getIndex(vertex1);
        int indexVertex2 = getIndex(vertex2);

        if (indexIsValid(indexVertex1) && indexIsValid(indexVertex1)) {
            this.listaAdj[indexVertex1].add(vertex2);
            this.listaAdj[indexVertex2].add(vertex1);
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
        if (indexIsValid(vertex1) && indexIsValid(vertex2)) {
            this.listaAdj[getIndex(vertex1)].remove(vertex2);
            this.listaAdj[getIndex(vertex2)].remove(vertex1);
        }
    }

    /**
     * Retorna um iterador para realizar a pesquisa em largura (BFS) a partir de um vértice de início
     * para suas divisões vizinhas.
     *
     * @param startVertex o vértice de início para a pesquisq
     * @return um iterador para os resultados da pesquisa
     */
    public Iterator<T> iteratorBFSNextDivisoes(T startVertex) {
        int indexVertex = getIndex(startVertex);
        ArrayUnorderedList<T> resultList = new ArrayUnorderedList<T>();

        if (!indexIsValid(indexVertex)) {
            return resultList.iterator();
        }

        boolean[] visited = new boolean[numVertices];

        for (int i = 0; i < numVertices; i++) {
            visited[i] = false;
        }

        visited[indexVertex] = true;
        Iterator<T> itr = listaAdj[indexVertex].iterator();

        while (itr.hasNext()) {
            T element = itr.next();
            int index = getIndex(element);

            if (indexIsValid(index) && !visited[index]) {
                visited[index] = true;
                resultList.addToRear(element);
            }
        }

        return resultList.iterator();
    }

    /**
     * Retorna um iterador para realizar a pesquisa em largura (BFS) a partir de um vértice de início.
     *
     * @param startVertex o vértice de início para a pesquisa
     * @return um iterador para os resultados da pesquisa
     */
    @Override
    public Iterator iteratorBFS(T startVertex) {
        int indexVertex = getIndex(startVertex);
        Integer x;
        LinkedQueue<Integer> traversalQueue = new LinkedQueue<Integer>();
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

            if (listaAdj[x.intValue()] != null) {
                Iterator<T> itr = listaAdj[x.intValue()].iterator();

                while (itr.hasNext()) {
                    T element = itr.next();
                    int index = getIndex(element);

                    if (indexIsValid(index) && !visited[index]) {
                        traversalQueue.enqueue(index);
                        visited[index] = true;
                    }
                }
            }
        }

        return resultList.iterator();
    }

    /**
     * Retorna um iterador para realizar a pesquisa em profundidade (DFS)
     * a partir de um vértice de início.
     *
     * @param startVertex o vértice de início para a pesquisa
     * @return um iterador para os resultados da pesquisa
     */
    @Override
    public Iterator iteratorDFS(T startVertex) {
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

            if (listaAdj[x.intValue()] != null) {
                Iterator<T> itr = listaAdj[x.intValue()].iterator();

                while (itr.hasNext() && !found) {
                    T element = itr.next();
                    int index = getIndex(element);

                    if (indexIsValid(index) && !visited[index]) {
                        traversalStack.push(index);
                        resultList.addToRear(vertices[index]);
                        visited[index] = true;
                        found = true;
                    }
                }
            }

            if (!found && !traversalStack.isEmpty()) {
                traversalStack.pop();
            }
        }

        return resultList.iterator();
    }

    /**
     * Retorna um iterador para o caminho mais curto entre dois vértices usando a pesquisa
     * em largura (BFS).
     *
     * @param startVertex  o vértice de início para a pesquisa
     * @param targetVertex o vértice de destino para a pesquisa
     * @return um iterador para os vértices do caminho mais curto
     */
    @Override
    public Iterator<T> iteratorShortestPath(T startVertex, T targetVertex) {
        ArrayUnorderedList<T> resultList = new ArrayUnorderedList<T>();
        int start_index = getIndex(startVertex);
        int final_index = getIndex(targetVertex);

        if (!indexIsValid(start_index) || !indexIsValid(final_index)) {
            return resultList.iterator();
        }

        LinkedQueue<Integer> traversalQueue = new LinkedQueue<Integer>();
        int index = start_index;
        int[] comprimeto = new int[numVertices];
        int[] antecessor = new int[numVertices];
        boolean[] visited = new boolean[numVertices];

        for (int i = 0; i < numVertices; i++) {
            visited[i] = false;
        }

        traversalQueue.enqueue(start_index);
        visited[start_index] = true;
        comprimeto[start_index] = 0;
        antecessor[start_index] = -1;

        while (!traversalQueue.isEmpty() && (index != final_index)) {
            index = traversalQueue.dequeue();

            Iterator<T> itr = listaAdj[index].iterator();

            while (itr.hasNext()) {
                T element = itr.next();
                int index_ele = getIndex(element);

                if (indexIsValid(index_ele) && !visited[index_ele]) {
                    comprimeto[index_ele] = comprimeto[index_ele] + 1;
                    antecessor[index_ele] = index;
                    traversalQueue.enqueue(index_ele);
                    visited[index_ele] = true;
                }
            }
        }

        //Não existe caminho
        if (index != final_index) {
            return resultList.iterator();
        }

        LinkedStack<Integer> stack = new LinkedStack<>();
        index = final_index;
        stack.push(final_index);

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
     * Verifica se o grafo é conexo.
     *
     * @return true se o grafo for conexo, false caso contrário
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
     * @return o número de vértices
     */
    @Override
    public int size() {
        return this.numVertices;
    }

}
