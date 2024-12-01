package Mapa;

import Graph.GraphListaAdjacencia;

public class Edificio {

    private static int ID_EDIFICIO_CONT = 0;

    private int id;

    private String name;

    private GraphListaAdjacencia<Divisao> planta_edificio;

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

    @Override
    public String toString() {
        return "Edificio{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", planta_edificio=" + planta_edificio.toString() +
                '}';
    }
}
