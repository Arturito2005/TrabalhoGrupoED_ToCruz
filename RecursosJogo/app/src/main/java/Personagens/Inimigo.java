package Personagens;

import Mapa.Divisao;

public class Inimigo extends Personagem {

    public Inimigo(String nome, double poder, Divisao divisao) {
        super(nome, poder, divisao);
    }

    /*Meter o codigo para selecionar uma divisao válida ao inimigo
    * até de distancia 2 de onde ele está e depois chamar o setDivisao
    * para alterar a divisao do mesmo*/
    @Override
    public void mudarDivisao() {

    }

    @Override
    public String toString() {
        return super.toString();
    }
}
