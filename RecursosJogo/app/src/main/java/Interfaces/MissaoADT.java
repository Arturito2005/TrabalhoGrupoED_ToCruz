package Interfaces;

import Graph.GraphListaAdjacencia;
import Mapa.Divisao;
import java.util.Iterator;

public interface MissaoADT {

    public GraphListaAdjacencia<Divisao> getDivisoesVizinhasInimigo(Divisao divisao);

    public Iterator<Divisao> getCaminhoMaisCurtoToItemOrAlvo();

    public Divisao getDivisoesVizinhasTo();

    public void addDivisaoTrajetoToCruz(Divisao divisao);

    public void modoAutomatico();

    public void modoManual();

    public void relatoriosMissao();
}
