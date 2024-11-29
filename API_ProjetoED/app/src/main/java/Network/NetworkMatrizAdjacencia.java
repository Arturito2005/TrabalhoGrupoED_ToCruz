package Network;

import Graph.GraphMatrizAdjacencia;
import Interfaces.NetworkADT;
import java.util.Iterator;

public class NetworkMatrizAdjacencia<T> extends GraphMatrizAdjacencia<T> implements NetworkADT<T> {

    protected double[][] weightMatrix;

    public NetworkMatrizAdjacencia() {
        super();
        this.weightMatrix = new double[DEFAULT_CAPACITY][DEFAULT_CAPACITY];

        for (int i = 0; i < DEFAULT_CAPACITY; i++) {
            for (int y = 0; y < DEFAULT_CAPACITY; y++) {
                weightMatrix[i][y] = 0;
            }
        }
    }

    //Testar
    protected void expandadweightMatrix() {
        super.expandCapacity();
       
        double[][] tempMatriz = new double[this.vertices.length * 2][this.vertices.length * 2];

        for (int i = 0; i < this.adjMatrix.length; i++) {
            System.arraycopy(this.adjMatrix[i], 0, tempMatriz[i], 0, this.adjMatrix[i].length);
        }
        
        weightMatrix = tempMatriz;
    }

    @Override
    public void addEdge(T vertex1, T vertex2, double weight) {
        addEdge(getIndex(vertex1), getIndex(vertex2), weight);
    }

    public void addEdge(int index1, int index2, double weight) {
        if (indexIsValid(index1) && indexIsValid(index2)) {
            weightMatrix[index1][index2] = weight;
            weightMatrix[index2][index1] = weight;
        }
    }

    /*
    Depois fazer
     */
    @Override
    public double shortestPathWeight(T vertex1, T vertex2) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    //Por enquanto fica isto
    @Override
    public Iterator<T> iterator() {
        return super.iteratorDFS(this.vertices[0]);
    }

}
