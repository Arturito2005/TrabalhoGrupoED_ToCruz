package Mapa;

import Graph.GraphListaAdjacencia;
import Graph.GraphMatrizAdjacencia;

public class Edificio {

    private static int ID_EDIFICIO_CONT = 0;

    private static final String NAME_DEFAULT = "Edficio";

    private int id;

    private String name;

    private GraphListaAdjacencia<Divisao> planta_edificio;

    public Edificio() {
        this.id = ID_EDIFICIO_CONT++;
        this.name = NAME_DEFAULT;
        this.planta_edificio = new GraphListaAdjacencia<>();
    }

    public Edificio(String name) {
        this.id = ID_EDIFICIO_CONT++;
        this.name = name;
        this.planta_edificio = new GraphListaAdjacencia<>();
    }

    public Edificio(String name, GraphListaAdjacencia<Divisao> planta_edificio) {
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

    public GraphListaAdjacencia<Divisao> getPlantaEdificio() {
        return planta_edificio;
    }

    public void addVertice(Divisao divisao) {
        this.planta_edificio.addVertex(divisao);
    }

    public void addLigacao(Divisao vertex1, Divisao vertex2) {
        this.planta_edificio.addEdge(vertex1, vertex2);
    }

    @Override
    public String toString() {
        return "Edificio{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", planta_edificio=" + planta_edificio.toString() +
                '}';
    }
}
