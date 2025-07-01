package Interfaces.Graph;

import Exceptions.ElementNotFoundException;
import Graph.ArrayGraph.GraphPesosNosVertices.Vertex;

import java.util.Iterator;

public interface GraphPesoVertexADT<T extends Vertex> extends GraphADT<T> {

    public void updateWeightVertice(T vertex, double weight) throws ElementNotFoundException;

    public Iterator<T> iteratorShortestPath(T startVertex, T targetVertex);
}
