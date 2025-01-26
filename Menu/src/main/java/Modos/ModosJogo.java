package Modos;

import Cenarios.Divisao.CenariosDivisao;
import Cenarios.Personagens.CenariosInimigos;
import Cenarios.Personagens.CenariosToCruz;
import Interfaces.Modos.ModoJogoInterface;
import Jogo.Simulacao;
import Mapa.Divisao;
import Mapa.Edificio;
import Paths.ShortesPaths;
import Personagens.ToCruz;
import Turnos.TurnoInimigo;
import Turnos.TurnoToCruz;

import java.util.Iterator;
import java.util.Scanner;

public abstract class ModosJogo implements ModoJogoInterface {
    protected Scanner sc;

    //Posso talvez remover o cenarios do To Cruz uma vez que eles estão incluidos no turno
    private CenariosToCruz cenariosTo;

    private CenariosDivisao cenariosDivisao;

    private TurnoToCruz turnoTo;

    private TurnoInimigo turnoInimigo;

    public ModosJogo (Simulacao simulacao) {
        this.cenariosTo = new CenariosToCruz(simulacao);
        this.cenariosDivisao = new CenariosDivisao(simulacao);
        this.turnoTo = new TurnoToCruz(cenariosTo, cenariosDivisao);
        CenariosInimigos cenariosInimigos = new CenariosInimigos(simulacao);
        this.turnoInimigo = new TurnoInimigo(cenariosInimigos, cenariosDivisao);
        this.sc = new Scanner(System.in);
    }

    public CenariosToCruz getCenariosTo() {
        return cenariosTo;
    }

    public TurnoToCruz getTurnoTo() {
        return turnoTo;
    }

    public TurnoInimigo getTurnoInimigo() {
        return turnoInimigo;
    }

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
        ToCruz toCruz = simulacao.getToCruz();
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

    public abstract Simulacao jogar();
}
