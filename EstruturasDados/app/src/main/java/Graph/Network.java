package Graph;

import ArrayList.ArrayUnorderedList;
import Interfaces.NetworkMatrizADT;

import java.util.Iterator;

public class Network<T> extends NetworkMatrizAdjacencia<T> implements NetworkMatrizADT<T> {

    //Ver se está bem (Inicializar o que não tem ligações a 0)
    public Iterator<T> iteratorNextVertexs(int startVertex) {
        ArrayUnorderedList<T> resultList = new ArrayUnorderedList<T>();

        if (!indexIsValid(startVertex)) {
            return resultList.iterator();
        }

        boolean[] visited = new boolean[numVertices];

        for (int i = 0; i < numVertices; i++) {
            visited[i] = false;
        }

        visited[startVertex] = true;
        for (int i = 0; i < numVertices; i++) {
            if (adjMatrix[startVertex][i] >= 0 && !visited[i]) {
                visited[i] = true;
                resultList.addToRear(vertices[i]);
            }
        }

        return resultList.iterator();
    }

    @Override
    public Iterator<T> iteratorNextVertexs(T startVertex) {
        return iteratorNextVertexs(getIndex(startVertex));
    }
}
