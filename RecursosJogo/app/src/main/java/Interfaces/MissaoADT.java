package Interfaces;

import Graph.GraphListaAdjacencia;
import LinkedList.LinearLinkedUnorderedList;
import Mapa.Divisao;

public interface MissaoADT {

    public GraphListaAdjacencia<Divisao> getDivisoesVizinhasInimigo(Divisao divisao);

    public LinearLinkedUnorderedList<Divisao> getDivisoesVizinhasTo(Divisao divisao);

    public void addDivisaoTrajeto(Divisao divisao);

    public void modoAutomatico();

    public void modoManual();
}
