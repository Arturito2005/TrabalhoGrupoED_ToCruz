package Turnos;

import ArrayList.ArrayUnordered;
import Cenarios.Cenarios;
import Exceptions.InvalidOptionException;
import Exceptions.InvalidTypeItemException;
import Interfaces.ArrayUnorderedADT;
import Interfaces.Turno.TurnoToCruzInt;
import Items.Item;
import Items.ItemCura;
import Items.TypeItemCura;
import Jogo.Simulacao;
import Mapa.Divisao;
import Mapa.Edificio;
import Personagens.Inimigo;
import Personagens.ToCruz;
import Cenarios.CenariosToCruz;

import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Scanner;

public class TurnoToCruz extends Turno implements TurnoToCruzInt {

    private Scanner sc;

    public TurnoToCruz(Cenarios cenario) {
        super(cenario);
        this.sc = new Scanner(System.in);
    }

    /**
     * Este metodo executa o turno do ToCruz em uma divisão, lidando com situações de combate
     * e interações com itens. O ToCruz pode atacar inimigos ou usar kits de vida.
     *
     * @param divisao_atual A divisão onde o ToCruz se encontra.
     */
    @Override
    public void turno(Divisao divisao_atual) {
        CenariosToCruz cenariosTo = (CenariosToCruz) getCenario();
        Simulacao simulacao = cenariosTo.getSimulacao();
        ToCruz toCruz = simulacao.getToCruz();
        Divisao divisao = divisao_atual;
        int op = -1;

        if (divisao_atual.haveConfronto()) {
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
                    for (Inimigo inimigo : divisao.getInimigos()) {
                        cenariosTo.ataque(toCruz, inimigo, divisao_atual);
                    }
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

                divisao = this.getNewDivisaoTo(divisao_atual);
                divisao.addToCruz(divisao_atual.getToCruz());
                divisao_atual.removeToCruz();

                cenariosTo.moverToCruz(divisao_atual, divisao);

                if (divisao.haveConfronto()) {
                    for(Inimigo inimigo : divisao.getInimigos()) {
                        cenariosTo.ataque(toCruz, inimigo, divisao);
                    }
                }

                if (!divisao.getItens().isEmpty()) {
                    DivisaoComItem(divisao, toCruz);
                }
            } catch (NullPointerException ne) {
                System.out.println(ne.getMessage());
                divisao = divisao_atual;
            }
        }

        if (!divisao.haveConfronto() && divisao.isToCruzInDivisaoAlvo()) {
            simulacao.getAlvo();
        }
    }

    /**
     * Este metodo trata de um item encontrado em uma divisão e permite que o ToCruz
     * interaja com ele.
     * Dependendo do tipo de item, ele pode ser usado, deixado na sala ou guardado na mochila.
     *
     * @param divisao A divisão onde o item foi encontrado.
     * @param toCruz A personagem que interage com o item.
     * @throws InvalidOptionException Caso a opção fornecida pelo jogador seja inválida.
     * @throws InvalidTypeItemException Caso o tipo de item seja inválido.
     */
    private void DivisaoComItem(Divisao divisao, ToCruz toCruz) throws InvalidOptionException, InvalidTypeItemException {
        CenariosToCruz cenarioTo = (CenariosToCruz) getCenario();
        for (Item item : divisao.getItens()) {
            if (item instanceof ItemCura) {
                ItemCura itemCura = (ItemCura) item;

                switch (itemCura.getType()) {
                    case COLETE: {
                        toCruz.usarItem(itemCura);
                        System.out.println("O To Cruz apanhou um colete e ficou com " + toCruz.getVida() + " HP");
                        break;
                    }
                    case KIT_VIDA: {
                        int op = -1;
                        if (toCruz.getVida() < 100 && !toCruz.mochilaIsFull()) {
                            do {
                                System.out.println("Esta numa sala com um kit de vida de " + itemCura.getVida_recuperada());
                                System.out.println("0 - Usar Item");
                                System.out.println("1 - Deixa-lo na sala");
                                System.out.println("2 - Guardar");
                                System.out.print("Selecione uma opcao -->");
                                try {
                                    op = sc.nextInt();
                                } catch (InputMismatchException ex) {
                                    System.out.println("Numero invalido!");
                                    sc.next();
                                }
                            } while (op < 0 || op > 2);
                        } else if (toCruz.mochilaIsFull() && toCruz.getVida() >= 100) {
                            do {
                                System.out.println("Esta numa sala com um kit de vida de " + itemCura.getVida_recuperada());
                                System.out.println("0 - Usar Item");
                                System.out.println("1 - Deixa-lo na sala");
                                System.out.print("Selecione uma opcao -->");
                                try {
                                    op = sc.nextInt();
                                } catch (InputMismatchException ex) {
                                    System.out.println("Numero invalido!");
                                    sc.next();
                                }
                            } while (op < 0 || op > 1);
                        } else if (!toCruz.mochilaIsFull() && toCruz.getVida() >= 100) {
                            System.out.println("Esta numa sala com um kit de vida de " + itemCura.getVida_recuperada());
                            System.out.println("1 - Deixa-lo na sala");
                            System.out.println("2 - Guardar");
                            System.out.print("Selecione uma opcao -->");
                            do {
                                try {
                                    op = sc.nextInt();
                                } catch (InputMismatchException ex) {
                                    System.out.println("Numero invalido!");
                                    sc.next();
                                }
                            } while (op < 1 || op > 2);
                        }

                        switch (op) {
                            case 0: {
                                cenarioTo.usarItemDivisao(item, divisao);
                                break;
                            }
                            case 1: {
                                System.out.println("O To Cruz nao coleta o item");
                                break;
                            }
                            case 2: {
                                cenarioTo.guardarKitMochila(item, divisao);
                                break;
                            }
                            default: {
                                throw new InvalidOptionException("Introduziu uma opcao invalida");
                            }
                        }
                        break;
                    }
                    default: {
                        throw new InvalidTypeItemException("Tipo de item invalido");
                    }
                }
            }
        }

    }

    /**
     * Este metodo solicita ao utilizador que selecione a próxima divisão para o ToCruz se mover,
     * exibindo informações sobre as divisões disponíveis, como se há inimigos, itens ou se é uma
     * entrada/saída. Também sugere o melhor caminho para o ToCruz apanhar itens e atingir o alvo.
     *
     * @param divisao_atual A divisão onde o ToCruz se encontra atualmente.
     * @return A divisão para onde o ToCruz deve se mover.
     */
    private Divisao getNewDivisaoTo(Divisao divisao_atual) {
        int op = -1;
        CenariosToCruz cenariosTo = (CenariosToCruz) getCenario();
        Iterator<Divisao> itr = cenariosTo.getSimulacao().getEdificio().getNextDivisoes(divisao_atual);
        ArrayUnorderedADT<Divisao> listDiv = new ArrayUnordered<Divisao>();

        int i = 0;
        String temp;

        System.out.println();
        System.out.println("Divisoes que o To Cruz pode entrar: ");
        while (itr.hasNext()) {
            Divisao divisao = itr.next();
            temp = i++ + " - " + divisao.getName();

            if (divisao.isEntrada_saida()) {
                if (divisao.getInimigos().isEmpty()) {
                    temp += " (esta divisao e uma entrada/saida)";
                } else {
                    temp += " (esta divisao e uma entrada/saida e tem inimigos)";
                }
            }

            if (divisao.getAlvo() != null) {
                if (divisao.getInimigos().isEmpty()) {
                    temp += " (divisao onde esta o alvo)";
                } else {
                    temp += "(divisao onde esta o alvo, mas tem inimigos)";
                }
            }

            if (!divisao.getItens().isEmpty()) {
                for (Item item : divisao.getItens()) {
                    if (item instanceof ItemCura) {
                        if (divisao.getInimigos().isEmpty()) {
                            if (((ItemCura) item).getType().equals(TypeItemCura.KIT_VIDA)) {
                                temp += " (divisao com kit)";
                            } else if (((ItemCura) item).getType().equals(TypeItemCura.COLETE)) {
                                temp += " (divisao com colete)";
                            }
                        } else {
                            if (((ItemCura) item).getType().equals(TypeItemCura.KIT_VIDA)) {
                                temp += " (divisao com kit e com inimigos)";
                            } else if (((ItemCura) item).getType().equals(TypeItemCura.COLETE)) {
                                temp += " (divisao com colete e com inimigos)";
                            }
                        }
                    }
                }

            }

            System.out.println(temp);
            listDiv.addToRear(divisao);
        }

        cenariosTo.sugestaoCaminhoToCruzKitEAlvo(divisao_atual);
        do {
            System.out.println("Selecione a divisao que o ToCruz vai se mover -->");

            try {
                op = sc.nextInt();
            } catch (InputMismatchException ex) {
                System.out.println("Numero invalido!");
                sc.next();
            }
        } while (op < 0 || op > listDiv.size() - 1);

        Divisao div = null;

        try {
            div = listDiv.find(op);
        } catch (NullPointerException | ArrayIndexOutOfBoundsException ex) {
            System.out.println(ex.getMessage());
        }

        return div;
    }

    /**
     * Verifica se a personagem ToCruz deve usar um kit de primeiros socorros da mochila,
     * com base na condição de vida da personagem e na disponibilidade do kit na mochila.
     * Se a vida de ToCruz for menor ou igual a 30 e se a mochila contiver um kit, o kit é usado.
     *
     * @param toCruz O objeto da personagem ToCruz, que será analisado para decidir se o
     *               kit será utilizado.
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
    private Divisao shortesPathTwoPointsAutomatico(Divisao div_start, Divisao div_final) {
        Iterator<Divisao> shortestPath = getCenario().getSimulacao().getEdificio().shortesPathIt(div_start, div_final);
        String temp = "";
        Divisao div_to = null;

        int i = 0;
        while (shortestPath.hasNext()) {
            Divisao div = shortestPath.next();

            if (i == 1) {
                div_to = div;
            }

            if (shortestPath.hasNext()) {
                temp = temp + div.getName() + " -->";
            } else {
                temp = temp + div.getName();
            }

            i++;
        }

        System.out.println(temp);
        return div_to;
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
        ToCruz toCruz = div_to.getToCruz();
        Edificio edificio = getCenario().getSimulacao().getEdificio();
        Iterator<Divisao> itr = edificio.IteratorMapa();
        Divisao best_destino = null;
        double best_distance = Double.MAX_VALUE;
        double num_arestas_com = Double.MAX_VALUE;
        boolean find_alvo = false;

        while (itr.hasNext() && !find_alvo) {
            Divisao div = itr.next();

            if (div.isEntrada_saida() && toCruz.isColectedAlvo()) {
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
            } else if (div.getAlvo() != null && !toCruz.isColectedAlvo()) {
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
        return shortesPathTwoPointsAutomatico(div_to, best_destino);
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
        ToCruz toCruz = divisao_atual.getToCruz();
        Divisao divisao = divisao_atual;
        CenariosToCruz cenario = (CenariosToCruz) getCenario();
        Simulacao simulacao = getCenario().getSimulacao();

        ConditionUseKitMochila(toCruz);
        if (divisao_atual.haveConfronto()) {
            for (Inimigo inimigo : divisao.getInimigos()) {
                cenario.ataque(toCruz, inimigo, divisao_atual);
            }
        } else {
            divisao = sugestaoCaminhoToCruzAutomatico(divisao_atual);

            //Meter o moverToCruz no cenarioToCruz (se calhar, para tirar responsabilidade à simulação)
            simulacao.moverToCruz(divisao_atual, divisao);
            if (divisao.haveConfronto()) {
                for (Inimigo inimigo : divisao.getInimigos()) {
                    cenario.ataque(toCruz, inimigo, divisao_atual);
                }
            }

            if (!divisao.getItens().isEmpty()) {
                ConditionGetUseItemAutomatico(divisao);
            }
        }

        if (!divisao.haveConfronto() && divisao.isToCruzInDivisaoAlvo() && !divisao.getAlvo().isAtinigido()) {
            simulacao.getAlvo();
        }
    }

}
