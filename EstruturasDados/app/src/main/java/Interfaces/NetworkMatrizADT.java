package Interfaces;

import java.util.Iterator;

public interface NetworkMatrizADT<T> extends NetworkADT<T> {
    public Iterator<T> iteratorNextVertexs(T startVertex);
}
