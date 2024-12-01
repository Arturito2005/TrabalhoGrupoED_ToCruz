package Personagens;

import Mapa.Divisao;

/**
 * Esta classe representa um inimigo no jogo.
 */
public class Inimigo extends Personagem {

    /**
     * Construtor da classe Inimigo.
     *
     * @param nome Nome do inimigo.
     * @param poder Poder do inimigo.
     * @param divisao Divisão onde o inimigo está localizado.
     */
    public Inimigo(String nome, double poder, Divisao divisao) {
        super(nome, poder, divisao);
    }

    /*Meter o codigo para selecionar uma divisao válida ao inimigo
    * até de distancia 2 de onde ele está e depois chamar o setDivisao
    * para alterar a divisao do mesmo
    *     @Override
    public void mudarDivisao() {

    }*/

    /**
     * Retorna a representação uma String com os dados do inimigo.
     *
     * @return String com os detalhes do inimigo.
     */
    @Override
    public String toString() {
        return super.toString();
    }
}
