package Modos;

import Cenarios.Divisao.CenariosDivisao;
import Cenarios.Personagens.CenariosInimigos;
import Cenarios.Personagens.CenariosToCruz;
import Interfaces.Modos.ModoJogoInterface;
import Jogo.Simulacao;
import Mapa.Divisao;
import Mapa.Edificio;
import Paths.ShortesPaths;
import Turnos.TurnoInimigo;
import Turnos.TurnoToCruz;

import java.util.Iterator;
import java.util.Scanner;

/**
 * Classe abstrata que define os modos de jogo. Implementa a interface {@link ModoJogoInterface}.
 * Cada modo de jogo terá comportamentos específicos para a interação entre o personagem ToCruz e os inimigos no edifício.
 * Contém métodos que lidam com o movimento do personagem ToCruz, o cálculo do caminho e o turno dos inimigos.
 *
 * @author Artur Pinto
 * Nº mecanográfico: 8230138
 * @author Francisco Oliveira
 * Nº mecanografico: 8230148
 * @version 1.0
 */
public abstract class ModosJogo implements ModoJogoInterface {
    /** Scanner para entrada de dados do utilizador */
    protected Scanner sc;

    /** Cenários para o personagem ToCruz */
    private CenariosToCruz cenariosTo;

    /** Cenários relacionados às divisões do edifício */
    private CenariosDivisao cenariosDivisao;

    /** Turno do personagem ToCruz */
    private TurnoToCruz turnoTo;

    /** Turno dos inimigos*/
    private TurnoInimigo turnoInimigo;

    public ModosJogo (Simulacao simulacao) {
        this.cenariosTo = new CenariosToCruz(simulacao);
        this.cenariosDivisao = new CenariosDivisao(simulacao);
        this.turnoTo = new TurnoToCruz(cenariosTo, cenariosDivisao);
        CenariosInimigos cenariosInimigos = new CenariosInimigos(simulacao);
        this.turnoInimigo = new TurnoInimigo(cenariosInimigos, cenariosDivisao);
        this.sc = new Scanner(System.in);
    }

    /**
     * Retorna o cenário relacionado ao personagem ToCruz.
     *
     * @return O cenário de ToCruz.
     */
    public CenariosToCruz getCenariosTo() {
        return cenariosTo;
    }

    /**
     * Retorna o turno do personagem ToCruz.
     *
     * @return O turno do ToCruz.
     */
    public TurnoToCruz getTurnoTo() {
        return turnoTo;
    }

    /**
     * Retorna o turno dos inimigos.
     *
     * @return O turno dos inimigos.
     */
    public TurnoInimigo getTurnoInimigo() {
        return turnoInimigo;
    }

    /**
     * Retorna os cenários relacionados às divisões do edifício.
     *
     * @return O cenário das divisões do edifício.
     */
    public CenariosDivisao getCenariosDivisao() {
        return cenariosDivisao;
    }

    /**
     * Metodo que sugere o melhor caminho para o To Cruz alcançar o alvo ou sair do edifício
     * automaticamente.
     * Avalia as divisões do edifício para determinar o destino ideal com base na distância
     * e no número de arestas.
     *
     * Se o alvo já tiver sido recolhido, sugere o caminho para a saída mais próxima.
     * Caso contrário, sugere o caminho para a divisão onde o alvo se encontra.
     *
     * @param div_to A divisão de partida do To Cruz.
     * @return O melhor caminho calculado entre dois pontos (partida e destino).
     */
    protected Divisao sugestaoCaminhoToCruzAutomatico(Divisao div_to) {
        Simulacao simulacao = cenariosTo.getSimulacao();
        Edificio edificio = simulacao.getEdificio();
        Iterator<Divisao> itr = edificio.IteratorMapa();
        Divisao best_destino = null;
        double best_distance = Double.MAX_VALUE;
        double num_arestas_com = Double.MAX_VALUE;
        boolean find_alvo = false;

        while (itr.hasNext() && !find_alvo) {
            Divisao div = itr.next();

            if (div.isEntrada_saida() && simulacao.isCollectedAlvo()) {
                double distance = edificio.getShortestPath(div_to, div);

                if (distance == 0 || distance == best_distance) {
                    best_distance = distance;
                    double num_arestas = edificio.getShortestPathNumArestas(div_to, div);

                    if (num_arestas < num_arestas_com) {
                        if (distance == 0) {
                            num_arestas_com = num_arestas;
                        }
                        best_destino = div;
                    }
                } else if (distance < best_distance) {
                    best_distance = distance;
                    best_destino = div;
                }
            } else if (div.getAlvo() != null && !simulacao.isCollectedAlvo()) {
                best_destino = div;
                find_alvo = true;
            }
        }

        String temp;
        if (best_destino.getAlvo() != null) {
            temp = "Sugestao de melhor caminho para o To Cruz chegar ao alvo: " + best_destino.getAlvo().getNome();
        } else {
            temp = "Sugestao de melhor caminho para o ToCruz sair do edificio";
        }

        System.out.println(temp);
        ShortesPaths shortesPaths = new ShortesPaths(edificio);
        return shortesPaths.shortesPathTwoPointsAutomatico(div_to, best_destino);
    }

    /**
     * Método abstrato que define o comportamento do jogo em cada modo.
     * Implementações específicas para os diferentes modos de jogo devem fornecer a lógica do jogo.
     *
     * @return o resultado da simulacao/jogo.
     */
    public abstract Simulacao jogar();
}
