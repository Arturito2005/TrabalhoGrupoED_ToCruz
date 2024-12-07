package Interfaces;

import Mapa.Divisao;

import java.util.Iterator;

public interface EdificoInt {

    public int getNumEntradasSaidas();

    public void addDivisao(Divisao divisao);

    public void addLigacao(Divisao vertex1, Divisao vertex2);

    public void addLigacao(Divisao vertex1, Divisao vertex2, double weight);

    public void updateWeight(Divisao vertex1, double weight);

    public Iterator<Divisao> getNextDivisoes(Divisao divisao);

    public Iterator<Divisao> IteratorMapa();
}
