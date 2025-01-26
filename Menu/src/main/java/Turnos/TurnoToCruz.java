package Turnos;

import Cenarios.Divisao.CenariosDivisao;
import Exceptions.InvalidOptionException;
import Exceptions.InvalidTypeItemException;
import Interfaces.Turno.TurnoToCruzInt;
import Items.Item;
import Items.ItemCura;
import Jogo.Simulacao;
import Mapa.Divisao;
import Mapa.Edificio;
import Paths.ShortesPaths;
import Personagens.ToCruz;
import Cenarios.Personagens.CenariosToCruz;

import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Scanner;

/**
 * Classe responsável pela execução do turno do personagem ToCruz no cenário de jogo.
 * Gerencia as interações do ToCruz, como combate, uso de itens e movimentação dentro de um edifício.
 * Implementa a interface TurnoToCruzInt.
 *
 * @author Artur Pinto
 * Nº mecanográfico: 8230138
 * @author Francisco Oliveira
 * Nº mecanografico: 8230148
 * @version 1.0
 */
public class TurnoToCruz extends Turno implements TurnoToCruzInt {

    /**
     * Scanner
     * */
    private static Scanner sc;

    /**
     * Construtor da classe TurnoToCruz.
     * Inicializa a classe Turno com os cenários e configura o scanner para entrada de dados.
     *
     * @param cenariosToCruz O cenário de personagens do ToCruz.
     * @param cenariosDivisao O cenário de divisões do jogo.
     */
    public TurnoToCruz(CenariosToCruz cenariosToCruz, CenariosDivisao cenariosDivisao) {
        super(cenariosToCruz, cenariosDivisao);
        sc = new Scanner(System.in);
    }

    /**
     * Este metodo executa o turno do ToCruz em uma divisão, lidando com situações de combate
     * e interações com itens. O ToCruz pode atacar inimigos ou usar kits de vida.
     *
     * @param divisao_atual A divisão onde o ToCruz se encontra.
     */
    @Override
    public void turno(Divisao divisao_atual) {
        CenariosToCruz cenariosTo = (CenariosToCruz) getCenarioPersonagens();
        CenariosDivisao cenariosDivisao = getCenariosDivisao();
        Simulacao simulacao = cenariosTo.getSimulacao();
        ToCruz toCruz = simulacao.getToCruz();
        Divisao divisao = divisao_atual;
        int op = -1;

        if (cenariosDivisao.haveConfronto(divisao_atual)) {
            if (toCruz.mochilaTemKit()) {
                do {
                    System.out.println("1 - Atacar");
                    System.out.println("2 - Usar kit");
                    System.out.print("Selecione uma opcao -->");
                    try {
                        op = sc.nextInt();
                    } catch (InputMismatchException ex) {
                        System.out.println("Numero invalido!");
                        sc.next();
                    }
                } while (op < 1 || op > 2);
            } else {
                System.out.println("Nao e possível o To Cruz curar-se porque nao tem kits na mochila. Por isso ToCruz ataca!");
                op = 1;
            }

            switch (op) {
                case 1: {
                    cenariosTo.ataqueToCruz(divisao_atual);
                    break;
                }
                case 2: {
                    toCruz.usarKit();
                    break;
                }
                default: {
                    throw new InvalidOptionException("Opcao invalida");
                }
            }
        } else {
            try {
                if (toCruz.mochilaTemKit() && toCruz.getVida() < 100) {
                    int op_kit = -1;
                    System.out.println("O To Cruz possui kits na sua mochila");
                    System.out.println("O proximo kit da mochila tem os seguintes pontos recuperados: " + toCruz.getMochila().peek().getVida_recuperada() + " HP");
                    do {
                        System.out.print("Deseja utilizar o kit de vida? (Nao: 0/ Sim: 1) -->");

                        try {
                            op_kit = sc.nextInt();
                        } catch (InputMismatchException ex) {
                            System.out.println("Numero invalido!");
                            sc.next();
                        }
                    } while (op_kit < 0 || op_kit > 1);

                    if (op_kit == 1) {
                        toCruz.usarKit();
                    }
                }

                divisao = cenariosTo.andar(divisao_atual);
                cenariosTo.moverToCruz(divisao_atual, divisao);

                if (cenariosDivisao.haveConfronto(divisao)) {
                    cenariosTo.ataqueToCruz(divisao);
                }

                if (!divisao.getItens().isEmpty()) {
                    cenariosDivisao.DivisaoComItem(divisao, toCruz);
                }
            } catch (NullPointerException ne) {
                System.out.println(ne.getMessage());
                divisao = divisao_atual;
            }
        }

        if (cenariosDivisao.isToCruzInDivisaoAlvo(divisao) && !cenariosDivisao.haveConfronto(divisao)) {
            simulacao.isCollectedAlvo();
        }
    }


    /**
     * Verifica se a personagem ToCruz deve usar um kit de primeiros socorros da mochila,
     * com base na condição de vida da personagem e na disponibilidade do kit na mochila.
     * Se a vida de ToCruz for menor ou igual a 30 e se a mochila contiver um kit, o kit é usado.
     *
     * @param toCruz O objeto da personagem ToCruz, que será analisado para decidir se o kit será utilizado.
     * @return Retorna verdadeiro se o kit foi usado, caso contrário retorna falso.
     */
    private boolean ConditionUseKitMochila(ToCruz toCruz) {
        boolean usedKit = false;

        if (toCruz.getVida() <= 30 && toCruz.mochilaTemKit()) {
            toCruz.usarKit();
            usedKit = true;
        }

        return usedKit;
    }

    /**
     * Metodo que sugere o melhor caminho para o To Cruz alcançar o alvo ou sair do edifício
     * automaticamente.
     * Avalia as divisões do edifício para determinar o destino ideal com base na distância
     * e no número de arestas.
     * <p>
     * Se o alvo já tiver sido recolhido, sugere o caminho para a saída mais próxima.
     * Caso contrário, sugere o caminho para a divisão onde o alvo se encontra.
     *
     * @param div_to A divisão de partida do To Cruz.
     * @return O melhor caminho calculado entre dois pontos (partida e destino).
     */
    private Divisao sugestaoCaminhoToCruzAutomatico(Divisao div_to) {
        Simulacao simulacao = getCenarioPersonagens().getSimulacao();
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
        ShortesPaths shortestPath = new ShortesPaths(edificio);
        Divisao returnDiv = shortestPath.shortesPathTwoPointsAutomatico(div_to, best_destino);

        if (returnDiv == null && find_alvo) {
            returnDiv = best_destino;
        }

        return returnDiv;
    }

    /**
     * Realiza a verificação e uso de itens automáticos de cura para o ToCruz.
     *
     * @param divisao a divisão onde o ToCruz se encontra.
     * @throws InvalidTypeItemException se o tipo de item for inválido.
     */
    private void ConditionGetUseItemAutomatico(Divisao divisao) throws InvalidTypeItemException {
        for (Item item : divisao.getItens()) {
            if (item instanceof ItemCura) {
                ItemCura item_cura = (ItemCura) item;
                ToCruz toCruz = divisao.getToCruz();

                if (toCruz.getVida() <= 30 && toCruz.mochilaTemKit()) {
                    toCruz.usarKit();
                }

                switch (item_cura.getType()) {
                    case COLETE: {
                        toCruz.usarItem(item_cura);
                        System.out.println("O To Cruz apanhou um colete de vida e ficou com " + toCruz.getVida() + " HP");
                        break;
                    }
                    case KIT_VIDA: {
                        if (!toCruz.mochilaIsFull() && (toCruz.getVida() == 100 || toCruz.getVida() + item_cura.getVida_recuperada() >= 100)) {
                            toCruz.guardarKit(item_cura);
                            System.out.println("O To Cruz guardou um kit de vida com " + item_cura.getVida_recuperada() + " HP");
                        } else if (toCruz.getVida() + item_cura.getVida_recuperada() <= 100) {
                            toCruz.usarItem(item_cura);
                            System.out.println("O To Cruz usou um kit de vida com " + item_cura.getVida_recuperada() + " HP");
                        }
                        break;
                    }
                    default: {
                        throw new InvalidTypeItemException("Tipo de item de cura invalido");
                    }
                }
            }
        }
    }

    /**
     * Realiza o turno automático do ToCruz, considerando a presença de inimigos
     * e a busca por itens ou alvos.
     *
     * @param divisao_atual a divisão onde o ToCruz se encontra.
     */
    @Override
    public void turnoAutomatico(Divisao divisao_atual) {
        System.out.println();
        System.out.println("Turno do ToCruz");
        ToCruz toCruz = divisao_atual.getToCruz();
        Divisao divisao = divisao_atual;
        CenariosToCruz cenarioTo = (CenariosToCruz) getCenarioPersonagens();
        CenariosDivisao cenariosDivisao = getCenariosDivisao();
        Simulacao simulacao = cenarioTo.getSimulacao();

        ConditionUseKitMochila(toCruz);
        if (cenariosDivisao.haveConfronto(divisao_atual)) {
            cenarioTo.ataqueToCruz(divisao_atual);
        } else {
            divisao = sugestaoCaminhoToCruzAutomatico(divisao_atual);

            cenarioTo.moverToCruz(divisao_atual, divisao);
            if (cenariosDivisao.haveConfronto(divisao)) {
                cenarioTo.ataqueToCruz(divisao_atual);
            }

            if (!divisao.getItens().isEmpty()) {
                ConditionGetUseItemAutomatico(divisao);
            }
        }

        if (cenariosDivisao.isToCruzInDivisaoAlvo(divisao) && !simulacao.isCollectedAlvo() && cenariosDivisao.haveConfronto(divisao)) {
            cenariosDivisao.collectAlvo();
        }
    }
}
