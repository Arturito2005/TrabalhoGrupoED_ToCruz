package Interfaces.ShortestPath;

import Mapa.Divisao;
import Mapa.Edificio;

public interface ShortestPathInterface {

    public Divisao shortesPathTwoPointsAutomatico(Divisao div_start, Divisao div_final) throws NullPointerException;

    public void shortesPathTwopoints(Divisao div_start, Divisao div_final) throws NullPointerException;
}
