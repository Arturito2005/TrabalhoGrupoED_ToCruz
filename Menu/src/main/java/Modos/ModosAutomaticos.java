package Modos;

import Interfaces.UnorderedListADT;
import Jogo.Simulacao;
import Mapa.Divisao;
import Mapa.Edificio;
import Paths.ShortesPaths;
import Personagens.ToCruz;
import Turnos.TurnoInimigo;
import Turnos.TurnoToCruz;

/*
* Esta classe é a que vai ter os metodos em comum das classes de jogo automatico
* resolvendo o problema dos metodos semelhantes entre ambas
*
* */
public abstract class ModosAutomaticos extends ModosJogo {

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
            //Não devia este metodo de estar nos Cenarios do ToCruz.
            ShortesPaths shortesPaths = new ShortesPaths(edificio);
            shortesPaths.shortesPathTwopoints(best_div, div_alvo);
        }

        return best_div;
    }
}
