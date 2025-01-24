package Cenarios.Personagens;

import ArrayList.ArrayUnordered;
import Cenarios.Cenarios;
import Exceptions.InvalidOptionException;
import Exceptions.InvalidTypeItemException;
import Interfaces.ArrayUnorderedADT;
import Interfaces.Cenarios.CenariosToCruzInterface;
import Interfaces.UnorderedListADT;
import Items.Item;
import Items.ItemCura;
import Items.TypeItemCura;
import Jogo.Simulacao;
import LinkedList.LinearLinkedUnorderedList;
import Mapa.Divisao;
import Mapa.Edificio;
import Personagens.Inimigo;
import Personagens.ToCruz;

import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Scanner;

/*
 * Criar cenario para quando o ToCruz entra numa divisão e inicia cofronto para evitar estar sempre a criar um for
 * */
public class CenariosToCruz extends CenariosPersonagem implements CenariosToCruzInterface {
    private Scanner sc;

    public CenariosToCruz(Simulacao simulacao) {
        super(simulacao);
        this.sc = new Scanner(System.in);
    }

    //Pode estar aqui o erro
    public void ataqueToCruz(Divisao divisao) {
        ToCruz toCruz = getSimulacao().getToCruz();
        UnorderedListADT<Inimigo> inimigosDead = new LinearLinkedUnorderedList<>();
        for (Inimigo inimigo : divisao.getInimigos()) {
            ataque(toCruz, inimigo, divisao);

            if (inimigo.isDead()) {
                inimigosDead.addToRear(inimigo);
            }
        }

        for (Inimigo inimigo : inimigosDead) {
            Simulacao simulacao = getSimulacao();
            simulacao.inimigoDead(inimigo, divisao);
        }
    }

    public void collectAlvo() {
        getSimulacao().setCollectedAlvo(true);
    }

    @Override
    public void usarItemDivisao(Item item, Divisao divisao) {
        ToCruz toCruz = new ToCruz();

        if (divisao.containItem(item)) {
            if (item instanceof ItemCura) {
                if ((((ItemCura) item).getType().equals(TypeItemCura.KIT_VIDA) && toCruz.getVida() < 100) || item.equals(TypeItemCura.COLETE)) {
                    try {
                        toCruz.usarItem((ItemCura) item);
                        collectAlvo();
                        divisao.removeItem(item);
                    } catch (NullPointerException e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
        } else {
            System.out.println("O item desta divisao ja foi usado anteriormente");
        }
    }

    @Override
    public void guardarKitMochila(Item item, Divisao divisao) {
        ToCruz toCruz = new ToCruz();

        if (divisao.containItem(item) && !toCruz.mochilaIsFull()) {
            try {
                toCruz.guardarKit((ItemCura) item);
                collectAlvo();
                divisao.removeItem(item);
            } catch (NullPointerException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public void moverToCruz(Divisao divisaoAtual, Divisao novaDivisao) throws NullPointerException {
        if (divisaoAtual == null) {
            throw new NullPointerException("A divisao atual está nula!");
        } else if(novaDivisao == null) {
            //É esta que está a vir a nulo
            throw new NullPointerException("A nova divisão está nula!");
        }

        ToCruz toCruz = new ToCruz();
        Simulacao simulacao = getSimulacao();

        simulacao.updatePercursoToCruz(novaDivisao);
        divisaoAtual.removeToCruz();
        novaDivisao.addToCruz(toCruz);
    }

    @Override
    public Divisao andar(Divisao divisao_atual) {
        int op = -1;
        Edificio edificio = getSimulacao().getEdificio();
        Iterator<Divisao> itr = edificio.getNextDivisoes(divisao_atual);
        ArrayUnorderedADT<Divisao> listDiv = new ArrayUnordered<Divisao>();

        int i = 0;
        String temp;

        System.out.println();
        sugestaoCaminhoToCruzKitEAlvo(divisao_atual);
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
                        ItemCura itemCura = (ItemCura) item;
                        if (divisao.getInimigos().isEmpty()) {
                            if (itemCura.getType().equals(TypeItemCura.KIT_VIDA)) {
                                temp += " (divisao com kit)";
                            } else if (itemCura.getType().equals(TypeItemCura.COLETE)) {
                                temp += " (divisao com colete)";
                            }
                        } else {
                            if (itemCura.getType().equals(TypeItemCura.KIT_VIDA)) {
                                temp += " (divisao com kit e com inimigos)";
                            } else if (itemCura.getType().equals(TypeItemCura.COLETE)) {
                                temp += " (divisao com colete e com inimigos)";
                            }
                        }
                    }
                }

            }

            System.out.println(temp);
            listDiv.addToRear(divisao);
        }

        System.out.println();
        do {
            System.out.print("Selecione a divisao que o ToCruz vai se mover -->");

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
     * Este metodo trata de um item encontrado em uma divisão e permite que o ToCruz
     * interaja com ele.
     * Dependendo do tipo de item, ele pode ser usado, deixado na sala ou guardado na mochila.
     *
     * @param divisao A divisão onde o item foi encontrado.
     * @param toCruz  A personagem que interage com o item.
     * @throws InvalidOptionException   Caso a opção fornecida pelo jogador seja inválida.
     * @throws InvalidTypeItemException Caso o tipo de item seja inválido.
     */
    @Override
    public void DivisaoComItem(Divisao divisao, ToCruz toCruz) throws InvalidOptionException, InvalidTypeItemException {
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
                                usarItemDivisao(item, divisao);
                                break;
                            }
                            case 1: {
                                System.out.println("O To Cruz nao coleta o item");
                                break;
                            }
                            case 2: {
                                guardarKitMochila(item, divisao);
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
     * Sugere o melhor caminho para o ToCruz chegar a um item ou a um alvo.
     * O ToCruz deve coletar o item e, em seguida, atingir o alvo.
     *
     * @param div_to A divisão onde o ToCruz está localizado.
     */
    @Override
    public void sugestaoCaminhoToCruzKitEAlvo(Divisao div_to) {
        Simulacao simulacao = getSimulacao();
        ToCruz toCruz = div_to.getToCruz();
        Edificio edificio = simulacao.getEdificio();
        Iterator<Divisao> itr = edificio.IteratorMapa();

        Divisao item_div = null;
        Divisao div_alvo = null;
        double best_distance = Double.MAX_VALUE;
        double distance;
        double num_arestas_com = Double.MAX_VALUE;
        double num_arestas;

        while (itr.hasNext()) {
            Divisao div = itr.next();

            if (!div.getItens().isEmpty() || (div.isEntrada_saida() && div.getAlvo() != null)) {
                distance = edificio.getShortestPath(div_to, div);

                if (distance == 0 || distance == best_distance) {
                    best_distance = distance;
                    num_arestas = edificio.getShortestPathNumArestas(div_to, div);

                    if (num_arestas < num_arestas_com) {
                        if (distance == 0) {
                            num_arestas_com = num_arestas;
                        }

                        item_div = div;

                        if (div.isEntrada_saida()) {
                            div_alvo = div;
                        }
                    }
                } else if (distance < best_distance) {
                    best_distance = distance;

                    item_div = div;

                    if (div.isEntrada_saida()) {
                        div_alvo = div;
                    }
                }
            } else if (div.getAlvo() != null && !simulacao.isCollectedAlvo()) {
                div_alvo = div;
            }
        }

        System.out.println("Sugestao de caminho mais curto para o To Cruz chegar a um item de cura");
        shortesPathTwopoints(div_to, item_div);

        String temp;
        if (div_alvo.getAlvo() != null) {
            temp = "Sugestao de melhor caminho para o To Cruz chegar ao alvo: " + div_alvo.getAlvo().getNome();
        } else {
            temp = "Sugestao de melhor caminho para o ToCruz sair do edificio";
        }

        System.out.println(temp);
        shortesPathTwopoints(div_to, div_alvo);
    }

    /**
     * Imprime o caminho mais curto entre duas divisões no mapa.
     *
     * @param div_start A divisão de início do caminho.
     * @param Div_final A divisão final do caminho.
     */
    public void shortesPathTwopoints(Divisao div_start, Divisao Div_final) {
        Edificio edificio = getSimulacao().getEdificio();
        Iterator<Divisao> shortestPath = edificio.shortesPathIt(div_start, Div_final);
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
