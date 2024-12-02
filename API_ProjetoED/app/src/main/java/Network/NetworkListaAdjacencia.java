package Network;

import Graph.GraphListaAdjacencia;
import Interfaces.NetworkADT;
import java.util.Iterator;

public class NetworkListaAdjacencia<T> extends GraphListaAdjacencia<T> implements NetworkADT<T> {
    protected double[][] adjMatrix;
    @Override
    public void addEdge(T vertex1, T vertex2, double weight) {
        if (indexIsValid(vertex1) && indexIsValid(vertex2)) {
            this.listaAdj[getIndex(vertex1)].add(vertex2);
            this.listaAdj[getIndex(vertex2)].add(vertex1);

        }
    }

    public void editWeightEdge(T vertex1, T vertex2, double weight) {

    }
    //Fazer 1º o caminho mais curto para o alvo e depois fazer ou shortestPathWeight do alvo para uma saida
    //
    @Override
    public double shortestPathWeight(T vertex1, T vertex2) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Iterator<T> iterator() {

        return super.iteratorDFS(this.vertices[0]);
    }
    
}
