package Mapa;

import Graph.NetworkMatrizAdjacencia;

import java.util.Iterator;
import java.util.Objects;

public class Edificio {

    private static int ID_EDIFICIO_CONT = 0;

    private static final String NAME_DEFAULT = "Edficio";

    private int id;

    private String name;

    private NetworkMatrizAdjacencia<Divisao> planta_edificio;

    public Edificio() {
        this.id = ID_EDIFICIO_CONT++;
        this.name = NAME_DEFAULT;
        this.planta_edificio = new NetworkMatrizAdjacencia<>();
    }

    public Edificio(String name) {
        this.id = ID_EDIFICIO_CONT++;
        this.name = name;
        this.planta_edificio = new NetworkMatrizAdjacencia<>();
    }

    public Edificio(String name, NetworkMatrizAdjacencia<Divisao> planta_edificio) {
        this.id = ID_EDIFICIO_CONT++;
        this.name = name;
        this.planta_edificio = planta_edificio;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int numDivisoes() {
        return this.planta_edificio.size();
    }

    public NetworkMatrizAdjacencia<Divisao> getPlantaEdificio() {
        return planta_edificio;
    }

    public void addVertice(Divisao divisao) {
        this.planta_edificio.addVertex(divisao);
    }

    public void addLigacao(Divisao vertex1, Divisao vertex2) {
        this.planta_edificio.addEdge(vertex1, vertex2);
    }

    public void addLigacao(Divisao vertex1, Divisao vertex2, double weight) {
        this.planta_edificio.addEdge(vertex1, vertex2, weight);
    }

    public void updateLigacoa(Divisao vertex1, double weight) {
        this.planta_edificio.updateWeightEdge(vertex1, weight);
    }

    public Iterator<Divisao> getNextDivisoes(Divisao divisao) {
        return this.planta_edificio.iteratorBFSNextDivisoes(divisao);
    }

    @Override
    public String toString() {
        return "Edificio{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", planta_edificio=" + planta_edificio.toString() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Edificio edificio = (Edificio) o;
        if(id == edificio.id) {
            return true;
        }

        return  Objects.equals(name, edificio.name) && Objects.equals(planta_edificio, edificio.planta_edificio);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }
}
