package Mapa;

public class Edificio {

    private static int ID_EDIFICIO_CONT = 0;

    private int id;

    private String name;

    private int contDivisoes;

    //private GraphListaAdjacencia<Divisao> planta_edificio;

    public Edificio(String name) {
        this.id = ID_EDIFICIO_CONT++;
        this.name = name;
        this.contDivisoes = 0;
        //this.plantaEdificio = new GraphListaAdjacencia<Divisao>;
    }

    /*
    *public Edificio(String name, GraphListaAdjacencia<Divisao> planta_edificio) {
        this.id = ID_EDIFICIO_CONT++;
        this.name = name;
        this.contDivisoes = planta_edificio.size;
        this.plantaEdificio = planta_edificio;
    }
    * */

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getContDivisoes() {
        return contDivisoes;
    }

    /*
    *public GraphListaAdjacencia<Divisao> getPlantaEdificio() {
        return planta_edificio;
    }
    * */

    /*
    * A planta do edificio neste toString
    * */
    @Override
    public String toString() {
        return "Edificio{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", contDivisoes=" + contDivisoes +
                '}';
    }
}
