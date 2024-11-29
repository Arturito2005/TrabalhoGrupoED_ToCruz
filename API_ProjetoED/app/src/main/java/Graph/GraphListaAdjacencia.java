package Graph;

import ArrayList.ArrayUnorderedList;
import Exceptions.ElementNotFoundException;
import Exceptions.EmptyCollectionException;
import Interfaces.GraphADT;
import LinkedList.LinearLinkedOrderedList;
import Queue.LinkedQueue;
import Stacks.LinkedStack;
import java.util.Iterator;

public class GraphListaAdjacencia<T> implements GraphADT<T> {

    protected final int DEFAULT_CAPACITY = 10;

    protected T[] vertices; // Array de vértices

    protected LinearLinkedOrderedList<T>[] listaAdj; // Lista de adjacência como array de ListaLigada

    protected int numVertices; // Número atual de vértices

    public GraphListaAdjacencia() {
        numVertices = 0;
        this.listaAdj = new LinearLinkedOrderedList[DEFAULT_CAPACITY];
        this.vertices = (T[]) (new Object[DEFAULT_CAPACITY]);
    }

    protected void expandCapacity() {
        T[] temp = (T[]) (new Object[(this.vertices.length * 2)]);

        System.arraycopy(this.vertices, 0, temp, 0, this.vertices.length);
        this.vertices = temp;
       
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

        this.listaAdj[numVertices] = new LinearLinkedOrderedList<>();
        vertices[numVertices] = vertex;

        numVertices++;
    }

    //Testar bem (Ver se no while basta apenas ter o != null)
    @Override
    public void removeVertex(T vertex) throws ElementNotFoundException {
        int indexVertex = getIndex(vertex);

        if(indexVertex == -1) {
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
     * @param index
     * @return 
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
     * @param vertex
     * @return 
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
        if (indexIsValid(vertex1) && indexIsValid(vertex2)) {
            this.listaAdj[getIndex(vertex1)].add(vertex2);
            this.listaAdj[getIndex(vertex2)].add(vertex1);
        }
    }

    @Override
    public void removeEdge(T vertex1, T vertex2) {
        if (indexIsValid(vertex1) && indexIsValid(vertex2)) {
            this.listaAdj[getIndex(vertex1)].remove(vertex2);
            this.listaAdj[getIndex(vertex2)].remove(vertex1);
        }
    }

    @Override
    public Iterator iteratorBFS(T startVertex) {
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

    @Override
    public Iterator iteratorShortestPath(T startVertex, T targetVertex) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
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
