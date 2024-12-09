package Interfaces;

import java.util.Iterator;

public interface NetworkMatrizADT<T> extends NetworkADT<T> {
    public double getWeightEdge(T vertex, T vertex2);

    public void updateWeightEdge(T vertex, double weight);

    public Iterator<T> shortestPath(T startVertex, T finalVertex);
}
