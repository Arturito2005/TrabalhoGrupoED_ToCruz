package Paths;

import Interfaces.ShortestPath.ShortestPathInterface;
import Mapa.Divisao;
import Mapa.Edificio;

import java.util.Iterator;

/**
 * Classe com os metodos para mostar o caminho mais curto entre dois vertice/divisoes do edificio
 *
 * @author Artur Pinto
 * Nº mecanográfico: 8230138
 * @author Francisco Oliveira
 * Nº mecanografico: 8230148
 * @version 1.0
 */
public class ShortesPaths implements ShortestPathInterface {

    /** O edifício sobre o qual os caminhos mais curtos serão calculados*/
    private Edificio edificio;

    /**
     * Construtor que inicializa a classe com o edifício a ser usado para calcular os caminhos mais curtos.
     *
     * @param edificio O edifício onde os cálculos de caminho mais curto serão realizados.
     */
    public ShortesPaths(Edificio edificio) {
        this.edificio = edificio;
    }

    /**
     * Retorna o edifício associado a esta instância de ShortesPaths.
     *
     * @return O edifício sobre o qual os caminhos mais curtos são calculados.
     */
    public Edificio getEdificio() {
        return edificio;
    }

    /**
     * Define um novo edifício para a instância de ShortesPaths.
     * Este método permite alterar o edifício atual para outro, permitindo recalcular os caminhos
     * mais curtos com base no novo edifício.
     *
     * @param edificio O novo edifício a ser definido.
     */
    public void setEdificio(Edificio edificio) {
        this.edificio = edificio;
    }

    /**
     * Calcula e imprime o menor caminho entre duas divisões,
     * retornando a próxima divisão no caminho.
     * <p>
     * Este metodo utiliza o iterador do menor caminho entre duas divisões fornecido pelo edifício.
     * Ele constrói uma string que representa o caminho completo e identifica a próxima divisão
     * a ser visitada após a divisão inicial.
     *
     * @param div_start a divisão inicial no caminho.
     * @param div_final a divisão final no caminho.
     * @return a próxima divisão no menor caminho a partir da divisão inicial.
     */
    @Override
    public Divisao shortesPathTwoPointsAutomatico(Divisao div_start, Divisao div_final) throws NullPointerException {
        if (div_start == null || div_final == null) {
            throw new NullPointerException("Algum dos parametros está a null");
        }

        Iterator<Divisao> shortestPath = edificio.shortesPathIt(div_start, div_final);
        String temp = "";
        Divisao div_to;

        if (!shortestPath.hasNext()) {
            div_to = div_final;
        } else {
            div_to = shortestPath.next();

            if (shortestPath.hasNext()) {
                temp += div_to.getName() + " -->";
            } else {
                temp += div_to.getName();
            }

            while (shortestPath.hasNext()) {
                Divisao div = shortestPath.next();

                if (shortestPath.hasNext()) {
                    temp += div.getName() + " -->";
                } else {
                    temp += div.getName();
                }
            }

            System.out.println(temp);
        }


        return div_to;
    }

    /**
     * Imprime o caminho mais curto entre duas divisões no mapa.
     *
     * @param div_start A divisão de início do caminho.
     * @param div_final A divisão final do caminho.
     */
    @Override
    public void shortesPathTwopoints(Divisao div_start, Divisao div_final) throws NullPointerException {
        if (div_start == null || div_final == null) {
            throw new NullPointerException("Algum dos parametros está a null");
        }

        Iterator<Divisao> shortestPath = edificio.shortesPathIt(div_start, div_final);
        String temp = "";

        while (shortestPath.hasNext()) {
            Divisao div = shortestPath.next();

            if (shortestPath.hasNext()) {
                temp += div.getName() + " -->";
            } else {
                temp += div.getName();
            }
        }
        System.out.println(temp);
    }
}
