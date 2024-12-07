package Graph;

import ArrayList.ArrayUnorderedList;
import Exceptions.EmptyCollectionException;
import Interfaces.GraphADT;
import Queue.LinkedQueue;
import Stacks.LinkedStack;
import java.util.Iterator;

/**
 * Graph represents an adjacency matrix implementation of a graph.
 *
 * @param <T>
 */
public class GraphMatrizAdjacencia<T> implements GraphADT<T> {

    protected final int DEFAULT_CAPACITY = 10;

    protected int numVertices; // number of vertices in the graph

    protected boolean[][] adjMatrix; // adjacency matrix

    protected T[] vertices; // values of vertices

    /**
     * Creates an empty graph.
     */
    public GraphMatrizAdjacencia() {
        numVertices = 0;
        this.adjMatrix = new boolean[DEFAULT_CAPACITY][DEFAULT_CAPACITY];
        this.vertices = (T[]) (new Object[DEFAULT_CAPACITY]);
    }

    //Teste (Fazer para o Network)
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
     * Adds a vertex to the graph, expanding the capacity of the graph if
     * necessary. It also associates an object with the vertex.
     *
     * @param vertex the vertex to add to the graph
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
     * Vê se o index é valido recebendo como parametro uma variavel int
     */
    protected boolean indexIsValid(int index) {
        boolean valid = false;

        if (index >= 0 && this.vertices[index] != null) {
            valid = true;
        }

        return valid;
    }

    /**
     * Vê se o index é valido recebendo como parametro uma variavel T
     */
    protected boolean indexIsValid(T vertex) {
        boolean valid = false;

        if (getIndex(vertex) >= 0) {
            valid = true;
        }

        return valid;
    }

    /**
     * Inserts an edge between two vertices of the graph.
     *
     * @param vertex1 the first vertex
     * @param vertex2 the second vertex
     */
    @Override
    public void addEdge(T vertex1, T vertex2) {
        addEdge(getIndex(vertex1), getIndex(vertex2));
    }

    /**
     * Inserts an edge between two vertices of the graph.
     *
     * @param index1 the first index
     * @param index2 the second index
     */
    public void addEdge(int index1, int index2) {
        if (indexIsValid(index1) && indexIsValid(index2)) {
            adjMatrix[index1][index2] = true;
            adjMatrix[index2][index1] = true;
        }
    }

    @Override
    public void removeEdge(T vertex1, T vertex2) {
        removeEdge(getIndex(vertex1), getIndex(vertex1));
    }

    public void removeEdge(int index1, int index2) {
        if (indexIsValid(index1) && indexIsValid(index2)) {
            adjMatrix[index1][index2] = false;
            adjMatrix[index2][index1] = false;
        }
    }

    /**
     * Returns an iterator that performs a breadth first search traversal
     * starting at the given index.
     *
     * @param startVertex the index to begin the search from
     * @return an iterator that performs a breadth first traversal
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
     * Returns an iterator that performs a depth first search traversal starting
     * at the given index.
     *
     * @param startVertex the index to begin the search traversal from
     * @return an iterator that performs a depth first traversal
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

    @Override
    public boolean isEmpty() {
        boolean empty = false;

        if (this.numVertices == 0) {
            empty = true;
        }

        return empty;
    }

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

    @Override
    public int size() {
        return this.numVertices;
    }
}
