package Network;

import Graph.GraphListaAdjacencia;
import Interfaces.NetworkADT;
import java.util.Iterator;

public class NetworkListaAdjacencia<T> extends GraphListaAdjacencia<T> implements NetworkADT<T> {

    @Override
    public void addEdge(T vertex1, T vertex2, double weight) {
        if (indexIsValid(vertex1) && indexIsValid(vertex2)) {
            this.listaAdj[getIndex(vertex1)].add(vertex2);
            this.listaAdj[getIndex(vertex2)].add(vertex1);
        }
    }

    @Override
    public double shortestPathWeight(T vertex1, T vertex2) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Iterator<T> iterator() {
        return super.iteratorDFS(this.vertices[0]);
    }
    
}
