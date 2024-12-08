package Interfaces;

import java.util.Iterator;

public interface NetworkMatrizADT<T> extends NetworkADT<T> {
    public double getWeightEdge(T vertex, T vertex2);

    public void updateWeightEdge(T vertex, double weight);

    public Iterator<T> iteratorNextVertexs(int startVertex);

    public Iterator<T> iteratorNextVertexs(T startVertex);

    public Iterator<T> shortestPath(T vertex1, T vertex2);
}
