package Modos;

import ArrayList.ArrayUnordered;
import Cenarios.Divisao.CenariosDivisao;
import Cenarios.Personagens.CenariosToCruz;
import Interfaces.ArrayUnorderedADT;
import Interfaces.UnorderedListADT;
import Jogo.Simulacao;
import LinkedList.LinearLinkedUnorderedList;
import Mapa.Divisao;
import Mapa.Draw;
import Mapa.DrawMap;
import Mapa.Edificio;
import Personagens.ToCruz;
import Turnos.TurnoInimigo;
import Turnos.TurnoToCruz;

import java.util.InputMismatchException;
import java.util.Iterator;

/**
 * Representa o modo manual de jogo, onde o jogador controla a personagem ToCruz
 * através de divisões do edifício, tomando decisões sobre como se mover, combater inimigos
 * e interagir com itens até alcançar a missão ou ser derrotado.
 *
 * O jogador escolhe as ações do ToCruz e interage com o mapa manualmente.
 *
 * @author Artur Pinto
 * Nº mecanográfico: 8230138
 * @author Francisco Oliveira
 * Nº mecanografico: 8230148
 * @version 1.0
 */
public class ModoManual extends ModosJogo {

    /**
     * Construtor para o modo manual. Inicializa os cenários, turnos e o cenário atual do jogo.
     *
     * @param simulacao A simulação que contém as informações sobre o edifício, personagens, e o progresso do jogo.
     */
    public ModoManual(Simulacao simulacao) {
        super(simulacao);
    }

    /**
     * Realiza a simulação do jogo no modo manual. O jogador controla a personagem ToCruz,
     * movendo-se pelo edifício, enfrentando inimigos e coletando itens até completar a missão
     * (sair do edifício com o alvo) ou ser derrotado.
     *
     * @return O objeto Simulacoes atualizado com os resultados da simulação.
     */
    @Override
    public Simulacao jogar() {
        CenariosToCruz cenariosTo = getCenariosTo();
        CenariosDivisao cenariosDivisao = getCenariosDivisao();
        TurnoToCruz turnoTo = getTurnoTo();
        TurnoInimigo turnoInimigo = getTurnoInimigo();
        Edificio edificio = cenariosTo.getSimulacao().getEdificio();
        Draw drawMapa = new DrawMap(edificio);
        Simulacao simulacao = cenariosTo.getSimulacao();
        Iterator<Divisao> itrMapa;
        boolean finishgame = false;

        drawMapa.draw();
        ToCruzEntrarEdificio();
        while (!finishgame) {
            itrMapa = edificio.IteratorMapa();
            UnorderedListADT<Divisao> endTurno = new LinearLinkedUnorderedList<>();
            while (itrMapa.hasNext()) {
                Divisao div = itrMapa.next();

                if (!div.getInimigos().isEmpty()) {
                    endTurno.addToRear(div);
                }
            }

            drawMapa.draw();
            int num_turnos = endTurno.size();
            for (int i = 0; i < num_turnos; i++) {
                turnoInimigo.turno(endTurno.removeFirst());
            }

            itrMapa = edificio.IteratorMapa();
            boolean findToCruz = false;

            while (itrMapa.hasNext() && !findToCruz) {
                Divisao div = itrMapa.next();

                if (div.getToCruz() != null) {
                    if (div.getToCruz().isDead()) {
                        finishgame = true;
                    } else if (div.isEntrada_saida() && !cenariosDivisao.haveConfronto(div) && simulacao.getPercursoToCruz().size() > 1) {
                        int op = -1;

                        do {
                            System.out.print("Deseja sair do edificio (Nao:0 / Sim: 1)? -->");

                            try {
                                op = sc.nextInt();
                            } catch (InputMismatchException ex) {
                                System.out.println("Numero invalido!");
                                sc.next();
                            }
                        } while (op < 0 || op > 1);

                        if (op == 1) {
                            finishgame = true;
                        } else {
                            turnoTo.turno(div);
                        }
                    } else {
                        turnoTo.turno(div);
                    }

                    findToCruz = true;
                    drawMapa.draw();
                }
            }
        }

        simulacao.relatorioSimulacao();

        return cenariosTo.getSimulacao();
    }

    /**
     * Permite que a personagem ToCruz entre num edifício, selecionando uma divisão de entrada/saída.
     * O jogador escolhe uma sala de entrada onde o ToCruz começa o jogo, e a divisão escolhida
     * é registada. Caso o ToCruz entre numa divisão com confronto,
     * ele pode lutar contra os inimigos ou interagir com itens do chão.
     *
     * @return A divisão em que o ToCruz entrou após a seleção.
     * @throws NullPointerException Caso ocorra um erro de null pointer.
     * @throws ArrayIndexOutOfBoundsException Caso o índice da sala selecionada este fora dos limites do array.
     */
    private Divisao ToCruzEntrarEdificio() {
        int op = -1;
        int i = 0;

        CenariosToCruz cenariosTo = getCenariosTo();
        CenariosDivisao cenariosDivisao = getCenariosDivisao();
        Simulacao simulacao = cenariosTo.getSimulacao();
        ToCruz toCruz = simulacao.getToCruz();

        Iterator<Divisao> itr = simulacao.getEdificio().IteratorMapa();
        ArrayUnorderedADT<Divisao> listDiv = new ArrayUnordered<>();

        while (itr.hasNext()) {
            Divisao div = itr.next();

            if (div.isEntrada_saida()) {
                String temp = i + " - " + div.getName();

                if (!div.getInimigos().isEmpty()) {
                    temp += " (divisao com inimigos)";
                }

                if (!div.getItens().isEmpty()) {
                    temp += " (divisao com item)";
                }

                System.out.println(temp);
                listDiv.addToRear(div);
                i++;
            }
        }

        do {
            System.out.print("Introduza onde o ToCruz vai entrar -->");

            try {
                op = sc.nextInt();
            } catch (InputMismatchException ex) {
                System.out.println("Numero invalido!");
                sc.next();
            }
        } while (op < 0 || op > listDiv.size() - 1);

        Divisao divisao_nova = null;

        try {
            divisao_nova = listDiv.find(op);
            simulacao.updatePercursoToCruz(divisao_nova);
            divisao_nova.addToCruz(toCruz);

            if (cenariosDivisao.haveConfronto(divisao_nova)) {
                cenariosTo.ataqueToCruz(divisao_nova);
            }

            //Se ele matar logo todos os inimigos na sala com um hit
            if (!cenariosDivisao.haveConfronto(divisao_nova)) {
                if (cenariosDivisao.isToCruzInDivisaoAlvo(divisao_nova)) {
                    simulacao.setCollectedAlvo(true);
                } else if (!divisao_nova.getItens().isEmpty()) {
                    cenariosDivisao.DivisaoComItem(divisao_nova, toCruz);
                }
            }
        } catch (NullPointerException | ArrayIndexOutOfBoundsException ex) {
            System.out.println(ex.getMessage());
        }

        return divisao_nova;
    }
}
