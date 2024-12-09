package Interfaces;

import Mapa.Divisao;
import java.util.Iterator;

/**
 * Interface que define o contrato para um edifício com divisões interconectadas.
 * Permite a manipulação e consulta de divisões, bem como das ligações entre elas.
 *
 * @author Artur Pinto
 * Nº mecanográfico: 8230138
 * @author Francisco Oliveria
 * Nº mecanografico: 8230148
 * @version 1.0
 */
public interface EdificoInt {

    public double getShortestPath(Divisao div_inicial, Divisao div_final);

    public double getShortestPathNumArestas(Divisao div_inicial, Divisao div_final);

    public Iterator<Divisao> shortesPathIt(Divisao div_inicial, Divisao div_final);

    public Divisao nextDivAutomaticToCruz(Divisao div_inicial, Divisao div_final);
    /**
     * Adiciona uma divisão ao edifício.
     *
     * @param divisao a divisão que será adicionada ao edifício.
     */
    public void addDivisao(Divisao divisao);

    /**
     * Cria uma ligação entre duas divisões, especificando um peso associado à ligação.
     *
     * @param vertex1 a primeira divisão.
     * @param vertex2 a segunda divisão.
     * @param weight  o peso da ligação.
     */
    public void addLigacao(Divisao vertex1, Divisao vertex2, double weight);

    /**
     * Atualiza o peso de todas as ligações partindo de uma divisão específica.
     *
     * @param vertex1 a divisão cuja ligação terá seu peso atualizado.
     * @param weight  o novo peso para a ligação.
     */
    public void updateWeight(Divisao vertex1, double weight);

    /**
     * Retorna um iterador com as divisões diretamente conectadas a uma divisão específica.
     *
     * @param divisao a divisão cujas divisões conectadas serão retornadas.
     * @return um iterador com as divisões conectadas.
     */
    public Iterator<Divisao> getNextDivisoes(Divisao divisao);

    /**
     * Retorna um iterador que permite percorrer todas as divisões do mapa do edifício.
     *
     * @return um iterador sobre todas as divisões do edifício.
     */
    public Iterator<Divisao> IteratorMapa();

}
