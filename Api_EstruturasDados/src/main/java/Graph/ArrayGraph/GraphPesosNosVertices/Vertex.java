package Graph.ArrayGraph.GraphPesosNosVertices;

import java.util.Objects;

public class Vertex<T> {

    protected T vertex;

    protected double weight;

    public Vertex(T vertex) {
        this.vertex = vertex;
    }

    public Vertex(T vertex, double weight) {
        this.vertex = vertex;
        this.weight = weight;
    }

    public T getVertex() {
        return vertex;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Vertex<?> vertex1 = (Vertex<?>) o;
        return Double.compare(weight, vertex1.weight) == 0 && Objects.equals(vertex, vertex1.vertex);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vertex, weight);
    }

    @Override
    public String toString() {
        return "Vertex{" +
                "vertex=" + vertex +
                ", weight=" + weight +
                '}';
    }
}
