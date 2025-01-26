package Modos;

import Interfaces.UnorderedListADT;
import Jogo.Simulacao;
import Mapa.Divisao;
import Mapa.Edificio;
import Paths.ShortesPaths;
import Personagens.ToCruz;

/**
 * Classe abstrata que representa os modos automáticos de jogo e contêm os metodos em comum para cada jogo automatico, estendendo a classe {@link ModosJogo}.
 *
 * @author Artur Pinto
 * Nº mecanográfico: 8230138
 * @author Francisco Oliveira
 * Nº mecanografico: 8230148
 * @version 1.0
 */
public abstract class ModosAutomaticos extends ModosJogo {

    /**
     * Construtor da classe ModosAutomaticos que chama o construtor da classe {@link ModosJogo}.
     * Inicializa os cenários e turnos necessários para o modo de jogo.
     *
     * @param simulacao A simulação que fornece o contexto do jogo, como o edifício e os personagens.
     */
    public ModosAutomaticos(Simulacao simulacao) {
        super(simulacao);
    }

    /**
     * Determina a melhor divisão inicial para o ToCruz começar a sua trajetória dentro do edifício.
     *
     * @param toCruz a personagem ToCruz que realizará a simulação.
     * @return a divisão onde o ToCruz deve começar o seu percurso.
     */
    protected Divisao BestStartToCruz(ToCruz toCruz, UnorderedListADT<Divisao> list_entradas, Divisao div_alvo) {
        Edificio edificio = getCenariosTo().getSimulacao().getEdificio();
        Divisao best_div = null;
        double best_distance = Double.MAX_VALUE;
        double num_arestas_com = Double.MAX_VALUE;

        for (Divisao div_entr : list_entradas) {
            double distance = edificio.getShortestPath(div_entr, div_alvo);

            if (distance == 0 || distance == best_distance) {
                best_distance = distance;
                double num_arestas = edificio.getShortestPathNumArestas(div_entr, div_alvo);

                if (num_arestas < num_arestas_com) {
                    if (distance == 0) {
                        num_arestas_com = num_arestas;
                    }
                    best_div = div_entr;

                }
            } else if (distance < best_distance) {
                best_div = div_entr;
                best_distance = distance;
            }
        }

        if (best_distance < toCruz.getVida()) {
            System.out.println("A melhor entrada que o To Cruz deve escolher e esta: " + best_div.getName());
            System.out.println("Melhor caminho que o To Cruz pode fazer ate ao alvo");

            ShortesPaths shortesPaths = new ShortesPaths(edificio);
            shortesPaths.shortesPathTwopoints(best_div, div_alvo);
        }

        return best_div;
    }
}
