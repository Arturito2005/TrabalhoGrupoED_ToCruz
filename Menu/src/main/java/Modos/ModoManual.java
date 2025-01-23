package Modos;

import ArrayList.ArrayUnordered;
import Cenarios.CenariosInimigos;
import Cenarios.CenariosToCruz;
import Interfaces.ArrayUnorderedADT;
import Interfaces.Modos.ModoJogoManualInterface;
import Interfaces.UnorderedListADT;
import Jogo.Simulacao;
import LinkedList.LinearLinkedUnorderedList;
import Mapa.Divisao;
import Mapa.Edificio;
import Personagens.Inimigo;
import Personagens.ToCruz;
import Turnos.TurnoInimigo;
import Turnos.TurnoToCruz;

import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Scanner;

/*Testar a ver se funciona
* Se der erro é porque a simulacao não está a ser bem atualizada.
* */
public class ModoManual implements ModoJogoManualInterface {
    private Scanner sc;

    private CenariosToCruz cenariosTo;

    private CenariosInimigos cenariosInimigo;

    private TurnoToCruz turnoTo;

    private TurnoInimigo turnoInimigo;

    public ModoManual(CenariosToCruz cenariosTo, CenariosInimigos cenariosInimigo, TurnoToCruz turnoTo, TurnoInimigo turnoInimigo) {
        this.cenariosTo = cenariosTo;
        this.cenariosInimigo = cenariosInimigo;
        this.turnoTo = turnoTo;
        this.turnoInimigo = turnoInimigo;
        this.sc = new Scanner(System.in);
    }

    /*Testar*/
    /**
     * Realiza a simulação do jogo no modo manual. O jogador controla a personagem ToCruz,
     * movendo-se pelo edifício, enfrentando inimigos e coletando itens até completar a missão
     * (sair do edifício com o alvo) ou ser derrotado.
     *
     * @return O objeto Simulacoes atualizado com os resultados da simulação.
     */
    @Override
    public Simulacao modojogoManual() {
        Edificio edificio = cenariosTo.getSimulacao().getEdificio();
        Simulacao simulacao = cenariosTo.getSimulacao();
        ToCruz toCruz = new ToCruz();
        Iterator<Divisao> itrMapa;
        boolean finishgame = false;

        edificio.drawMapa();
        ToCruzEntrarEdificio();
        while (!finishgame) {
            itrMapa = edificio.IteratorMapa();
            UnorderedListADT<Divisao> endTurno = new LinearLinkedUnorderedList<>();
            while (itrMapa.hasNext()) {
                Divisao div = itrMapa.next();

                if (div.haveInimigo()) {
                    endTurno.addToRear(div);
                }
            }

            edificio.drawMapa();
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
                    } else if (div.isEntrada_saida() && !div.haveConfronto() && simulacao.getPercursoToCruz().size() > 1) {
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
                    edificio.drawMapa();
                }
            }
        }

        simulacao.relatorioMissao();

        return cenariosTo.getSimulacao();
    }

    /*Testar o mover que pode não dar*/
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

        Simulacao simulacao = cenariosTo.getSimulacao();
        ToCruz toCruz = simulacao.getToCruz();

        Iterator<Divisao> itr = simulacao.getEdificio().getPlantaEdificio().iterator();
        ArrayUnorderedADT<Divisao> listDiv = new ArrayUnordered<>();

        while (itr.hasNext()) {
            Divisao div = itr.next();

            if (div.isEntrada_saida()) {
                String temp = i + " - " + div.getName();

                if (div.haveInimigo()) {
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
            cenariosTo.moverToCruz(divisao_nova, divisao_nova);

            if (divisao_nova.haveConfronto()) {
                for (Inimigo inimigo: divisao_nova.getInimigos()) {
                    cenariosTo.ataque(toCruz, inimigo, divisao_nova);
                }

            }

            //Se ele matar logo todos os inimigos na sala com um hit
            if (!divisao_nova.haveConfronto()) {
                if (divisao_nova.isToCruzInDivisaoAlvo()) {
                    simulacao.getAlvo();
                } else if (!divisao_nova.getItens().isEmpty()) {
                    //Está no Turno do ToCruz, mete-la nessa classe a public ou arranjar outra forma
                    DivisaoComItem(divisao_nova, toCruz);
                }
            }
        } catch (NullPointerException | ArrayIndexOutOfBoundsException ex) {
            System.out.println(ex.getMessage());
        }

        return divisao_nova;
    }
}
