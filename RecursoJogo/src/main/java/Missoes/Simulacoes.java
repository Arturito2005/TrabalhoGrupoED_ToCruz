package Missoes;

import ArrayList.ArrayUnordered;
import ArrayList.ArrayUnorderedList;
import Exceptions.InvalidOptionException;
import Exceptions.InvalidTypeItemException;
import Interfaces.*;
import Items.Item;
import Items.ItemCura;
import LinkedList.LinearLinkedUnorderedList;
import Mapa.Alvo;
import Mapa.Divisao;
import Mapa.Edificio;
import Personagens.Inimigo;
import Personagens.ToCruz;
import Queue.LinkedQueue;
import Stacks.LinkedStack;

import java.util.*;

/**
 * Voltar a meter a sugestao do melhor caminho no modo Manual
 * <p>
 * Falta verificar se nos metodos estão a ser utilizadas as melhores estruturas
 * <p>
 * A nivel de testes só falta testar no modo manual a parte de o ToCruz entrar numa divisao com item
 */
public class Simulacoes implements SimulacoesInt, Comparable<Simulacoes> {

    private static Scanner sc = new Scanner(System.in);

    private long versao_simulacao;

    private QueueADT<Divisao> trajeto_to;

    private UnorderedListADT<Inimigo> inimigos_dead;

    private double vida_to;

    private Edificio edificio;

    /*
     * Ver como mando o edificio para não bugar
     * */
    public Simulacoes(long versao_simulacao, Edificio edificio) {
        this.edificio = edificio;
        this.versao_simulacao = versao_simulacao;
        this.vida_to = 0;
        this.trajeto_to = new LinkedQueue<>();
        this.inimigos_dead = new LinearLinkedUnorderedList<Inimigo>();
    }

    public void addDivisaoTrajetoToCruz(Divisao divisao) {
        this.trajeto_to.enqueue(divisao);
    }

    private void ConditionGetUseItemAutomatico(Divisao divisao) throws InvalidTypeItemException {
        if (divisao.getItem() instanceof ItemCura) {
            ItemCura Item = (ItemCura) divisao.getItem();
            ToCruz toCruz = divisao.getToCruz();

            if (Item != null) {
                switch (Item.getType()) {
                    case COLETE: {
                        toCruz.usarItem(Item);
                        System.out.println("To Cruz apanho um colete de vida e ficou com " + toCruz.getVida() + " HP");
                        break;
                    }
                    case KIT_VIDA: {
                        if (!toCruz.mochilaIsFull() && (toCruz.getVida() == 100 || toCruz.getVida() + Item.getVida_recuperada() >= 100)) {
                            toCruz.guardarKit(Item);
                        } else if (toCruz.getVida() + Item.getVida_recuperada() <= 100) {
                            toCruz.usarItem(Item);
                        }
                        break;
                    }
                    default: {
                        throw new InvalidTypeItemException("Tipo de item de cura inválido");
                    }
                }
            } else {
                if (toCruz.getVida() <= 30 && toCruz.mochilaTemKit()) {
                    toCruz.usarKit();
                }
            }
        }
    }

    /**
     * Introduz de forma automatica o ToCruz na melhor divisao que ele pode entrar
     * Testar a ver se funciona
     */
    private Divisao BestStartToCruz(ToCruz toCruz) {
        UnorderedListADT<Divisao> list_entradas = new ArrayUnorderedList<>();
        Iterator<Divisao> itr;

        //1º Encontrar todos os itens e o alvo
        itr = edificio.getPlantaEdificio().iterator();

        while (itr.hasNext()) {
            Divisao div = itr.next();

            if (div.isEntrada_saida()) {
                list_entradas.addToRear(div);
            }
        }

        /*2º Ver o caminho mais curto de cada divisao para o item ou alvo
         * Testar em principio está a dar
         * */
        Divisao best_entr = null;
        double min_dist = Double.MAX_VALUE;
        itr = edificio.getPlantaEdificio().iterator();

        while (itr.hasNext()) {
            Divisao div = itr.next();

            if (div.getItem() != null || div.getAlvo() != null) {
                double min = Double.MAX_VALUE;
                double distance;
                Iterator<Divisao> itrEntr = list_entradas.iterator();
                Divisao div_min = null;
                int i = 0;

                while (itrEntr.hasNext()) {
                    Divisao div_entr = itrEntr.next();
                    distance = this.edificio.getShortestPath(div_entr, div);

                    if (distance < min || i == 0) {
                        min = distance;
                        div_min = div_entr;
                    }

                    i++;
                }

                if (min < min_dist) {
                    min_dist = min;
                    best_entr = div_min;
                }
            }
        }

        //3º Encontrar o caminho mais curto para um item
        best_entr.addToCruz(toCruz);
        addDivisaoTrajetoToCruz(best_entr);
        addDivisaoTrajetoToCruz(best_entr);

        if (best_entr.haveConfronto()) {
            best_entr.attackToCruz(this.inimigos_dead);
        }

        if (best_entr.getItem() != null) {
            DivisaoComItem(best_entr, best_entr.getToCruz());
        }

        return best_entr;
    }

    /**
     * Metodo que faz o turno do ToCruz de forma automatica
     * <p>
     * Só falta realmente ver a parte da movimentação se está mesmo a fazer o menor traj
     * Meter para quando chegar ao Alvo escolher o caminho mais curto para a saida
     * Otimizar o while
     */
    private void turnoAutomaticoToCruz(Divisao divisao_atual) {
        ToCruz toCruz = divisao_atual.getToCruz();
        Divisao divisao = divisao_atual;

        if (divisao_atual.haveConfronto()) {
            if (toCruz.getVida() <= 30 && toCruz.mochilaTemKit()) {
                toCruz.usarKit();
            } else {
                divisao_atual.attackToCruz(this.inimigos_dead);
            }
        } else {
            Iterator<Divisao> itr = this.edificio.getPlantaEdificio().iterator();
            Divisao best_div = null;
            double min = 0;
            double distance;
            int i = 0;

            if (!toCruz.isColectedAlvo()) {
                while (itr.hasNext()) {
                    Divisao div = itr.next();

                    if (div.getAlvo() != null || (div.getItem() != null && !div.getItem().isCollected())) {
                        distance = this.edificio.getShortestPath(divisao_atual, div);

                        if (distance < min || i == 0) {
                            min = distance;
                            best_div = div;
                        }

                        i++;
                    }
                }
            } else {
                while (itr.hasNext()) {
                    Divisao div = itr.next();

                    if (div.isEntrada_saida() || (div.getItem() != null && !div.getItem().isCollected())) {
                        distance = edificio.getShortestPath(divisao_atual, div);

                        if (distance < min || i == 0) {
                            min = distance;
                            best_div = div;
                        }

                        i++;
                    }
                }
            }

            divisao = edificio.nextDivAutomaticToCruz(divisao_atual, best_div);
            divisao.addToCruz(toCruz);
            this.addDivisaoTrajetoToCruz(divisao);
            divisao_atual.removeToCruz();

            if (divisao.haveConfronto()) {
                divisao.attackToCruz(this.inimigos_dead);
            }

            if (divisao.getItem() != null) {
                ConditionGetUseItemAutomatico(divisao);
            }
        }

        if (!divisao.haveConfronto() && divisao.isToCruzInDivisaoAlvo() && !divisao.getAlvo().isAtinigido()) {
            divisao.ToCruzGetAlvo();
            toCruz.setColectedAlvo(true);
        }
    }


    private double calculateBesteEntradaToCruz(ToCruz toCruz, Divisao div_alvo, UnorderedListADT<Divisao> list_entradas) {
        Iterator<Divisao> itr_caminho;
        Divisao best_div = null;

        double best_distance = Double.MAX_VALUE;
        double distance;
        double num_arestas_com = Double.MAX_VALUE;
        double num_arestas;

        for (Divisao div_entr : list_entradas) {
            distance = edificio.getShortestPath(div_entr, div_alvo);

            if (distance == 0) {
                best_distance = distance;
                num_arestas = edificio.getShortestPathNumArestas(div_entr, div_alvo);

                if (num_arestas < num_arestas_com) {
                    best_div = div_entr;
                    num_arestas_com = num_arestas;
                }
            } else if (distance < best_distance) {
                best_div = div_entr;
                best_distance = distance;
            }
        }

        if (best_distance < toCruz.getVida()) {
            itr_caminho = edificio.shortesPathIt(best_div, div_alvo);
            System.out.println("A melhor entrada que o To Cruz deve escolher é esta: " + best_div);
            System.out.println("Melhor caminho que o To Cruz pode fazer até ao alvo");

            String temp = "";
            while (itr_caminho.hasNext()) {
                Divisao div = itr_caminho.next();

                if (itr_caminho.hasNext()) {
                    temp += div.getName() + " --> ";
                } else {
                    temp += div.getName() + " ";
                }
            }
            System.out.println(temp);
        }

        return best_distance;
    }

    public double calculateBestExitAutomatico(ToCruz toCruz, Divisao div_alvo, UnorderedListADT<Divisao> list_entradas) {
        double best_distance = Double.MAX_VALUE;
        Divisao best_div = null;
        double num_arestas_com = Double.MAX_VALUE;
        double num_arestas;
        double distance;
        for (Divisao div_entr : list_entradas) {
            distance = edificio.getShortestPath(div_entr, div_alvo);

            if (distance == 0) {
                best_distance = distance;
                num_arestas = edificio.getShortestPathNumArestas(div_entr, div_alvo);

                if (num_arestas < num_arestas_com) {
                    best_div = div_entr;
                    num_arestas_com = num_arestas;
                }
            } else if (distance < best_distance) {
                best_div = div_entr;
                best_distance = distance;
            }
        }

        if (best_distance < toCruz.getVida()) {
            Iterator<Divisao> itr_caminho = edificio.shortesPathIt(div_alvo, best_div);
            System.out.println("Melhor caminho que o To Cruz deve fazer para sair do edificio");

            String temp = "";
            while (itr_caminho.hasNext()) {
                Divisao div = itr_caminho.next();

                if (itr_caminho.hasNext()) {
                    temp += div.getName() + " --> ";
                } else {
                    temp += div.getName() + " ";
                }
            }
            System.out.println(temp);
        }
        return best_distance;
    }

    /**
     * O caminho de volta do alvo para uma saida não está a dar
     * <p>
     * Testar turno inimigo
     */
    @Override
    public void modojogoAutomatico() {
        Iterator<Divisao> itr = edificio.getPlantaEdificio().iterator();
        UnorderedListADT<Divisao> list_entradas = new LinearLinkedUnorderedList<>();
        UnorderedListADT<Divisao> divisao_inimigo = new LinearLinkedUnorderedList<>();
        Divisao div_alvo = null;
        ToCruz to = new ToCruz();

        while (itr.hasNext()) {
            Divisao div = itr.next();

            if (div.isEntrada_saida()) {
                list_entradas.addToRear(div);
            }

            if (div.getAlvo() != null) {
                div_alvo = div;
            }

            if (div.haveInimigo()) {
                divisao_inimigo.addToRear(div);
            }
        }

        if (calculateBesteEntradaToCruz(to, div_alvo, list_entradas) > to.getVida()) {
            System.out.println("O To Cruz não consegue chegar ao alvo sem morrer!");
        } else {
            for (Divisao div_inimi : divisao_inimigo) {
                turnoInimigo(div_inimi);
            }

            if (calculateBestExitAutomatico(to, div_alvo, list_entradas) > to.getVida()) {
                System.out.println("É impossível o To Cruz sair do edificio com vida!");
            }
        }
    }

    /**
     * Joga o jogo de forma automático
     */
    @Override
    public Simulacoes jogoAutomatico() {
        ToCruz toCruz = new ToCruz();
        Iterator<Divisao> itrMapa;
        boolean finishgame = false;

        BestStartToCruz(toCruz);
        while (!finishgame) {
            itrMapa = this.edificio.IteratorMapa();
            UnorderedListADT<Divisao> endTurno = new LinearLinkedUnorderedList<>();
            while (itrMapa.hasNext()) {
                Divisao div = itrMapa.next();

                if (div.haveInimigo()) {
                    endTurno.addToRear(div);
                }
            }

            while (!endTurno.isEmpty()) {
                turnoInimigo(endTurno.removeFirst());
            }

            itrMapa = this.edificio.IteratorMapa();
            boolean findToCruz = false;
            while (itrMapa.hasNext() && !findToCruz) {
                Divisao div = itrMapa.next();

                if (div.getToCruz() != null) {
                    if (div.getToCruz().isDead() || (div.isToCruzInExit() && trajeto_to.size() > 1)) {
                        finishgame = true;
                    } else {
                        turnoAutomaticoToCruz(div);
                    }

                    findToCruz = true;
                }
            }
        }

        relatoriosMissao();
        double vida = toCruz.getVida();
        if (vida < 0) {
            vida = 0;
        }
        this.vida_to = vida;
        return this;
    }

    private void sugestaoCaminhoToCruz(Divisao div_to) {
        ToCruz toCruz = div_to.getToCruz();
        Iterator<Divisao> itr = edificio.IteratorMapa();

        Divisao best_div = null;
        double best_distance = Double.MAX_VALUE;
        double distance;
        double num_arestas_com = Double.MAX_VALUE;
        double num_arestas = 0;

        while (itr.hasNext()) {
            Divisao div = itr.next();

            if ((div.getAlvo() != null && !toCruz.isColectedAlvo()) || (div.getItem() != null && !div.getItem().isCollected()) || (div.isEntrada_saida() && div.getAlvo() != null && toCruz.isColectedAlvo())) {
                distance = edificio.getShortestPath(div_to, div);

                if (distance == 0) {
                    best_distance = distance;
                    num_arestas = edificio.getShortestPathNumArestas(div_to, div);

                    if (num_arestas < num_arestas_com) {
                        num_arestas_com = num_arestas;
                        best_div = div;
                    }
                } else if (distance < best_distance) {
                    best_distance = distance;
                    best_div = div;
                }
            }
        }

        Iterator<Divisao> shortestPath = edificio.shortesPathIt(div_to, best_div);
        String temp = "";
        System.out.println("Sugestão de caminho mais curto para o To Cruz");

        while (shortestPath.hasNext()) {
            Divisao div = shortestPath.next();

            if (shortestPath.hasNext()) {
                temp = temp + div.getName() + " -->";
            } else {
                temp = temp + div.getName();
            }
        }
        System.out.println(temp);
    }

    /*
     * Susgestao do melhor caminho está bugado
     * */
    private Divisao getNewDivisaoTo(Divisao divisao_atual) {
        int op = -1;
        Iterator<Divisao> itr = edificio.getNextDivisoes(divisao_atual);
        ArrayUnorderedADT<Divisao> listDiv = new ArrayUnordered<Divisao>();

        System.out.print("Selecione a divisao para o ToCruz se mover -->");

        int i = 0;
        String temp = "";
        while (itr.hasNext()) {
            Divisao divisao = itr.next();
            temp = i++ + " - " + divisao.getName();

            if (divisao.isEntrada_saida()) {
                temp = temp + " (esta divisao e uma entrada/saida)";
            } else if (divisao.getAlvo() != null) {
                temp = temp + " (divisao onde esta o alvo)";
            } else if (divisao.getItem() != null) {
                temp = temp + " (divisao com item de cura item)";
            }


            System.out.println(temp);
            listDiv.addToRear(divisao);
        }

        sugestaoCaminhoToCruz(divisao_atual);

        /*
         * Fim da sugestão
         * */
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

    /*
     * Falta meter apenas sugerir o caminho mais curto para o ToCruz chegar a um kit de vida ou ao alvo
     *
     * Verificar se estão todos os cenarios feitos
     *  */
    private void DivisaoComItem(Divisao divisao, ToCruz toCruz) throws InvalidOptionException, InvalidTypeItemException {
        if (divisao.getItem() instanceof ItemCura) {
            ItemCura item = (ItemCura) divisao.getItem();

            switch (item.getType()) {
                case COLETE: {
                    toCruz.usarItem(item);
                    System.out.println("To Cruz apnhou um colete e ficou com " + toCruz.getVida() + " HP");
                    break;
                }
                case KIT_VIDA: {
                    int op = -1;

                    if (toCruz.getVida() < 100 && !toCruz.mochilaIsFull()) {
                        do {
                            System.out.println("Está numa sala com um kit de vida de " + item.getVida_recuperada());
                            System.out.println("0 - Usar Item");
                            System.out.println("1 - Deixa-lo na sala");
                            System.out.println("2 - Guardar");
                            System.out.print("Selecione uma opção -->");
                            try {
                                op = sc.nextInt();
                            } catch (InputMismatchException ex) {
                                System.out.println("Numero inválido!");
                                sc.next();
                            }
                        } while (op < 0 || op > 2);
                    } else if (toCruz.mochilaIsFull() && toCruz.getVida() >= 100) {
                        do {
                            System.out.println("Está numa sala com um kit de vida de " + item.getVida_recuperada());
                            System.out.println("0 - Usar Item");
                            System.out.println("1 - Deixa-lo na sala");
                            System.out.print("Selecione uma opção -->");
                            try {
                                op = sc.nextInt();
                            } catch (InputMismatchException ex) {
                                System.out.println("Numero inválido!");
                                sc.next();
                            }
                        } while (op < 0 || op > 1);
                    } else if (!toCruz.mochilaIsFull() && toCruz.getVida() >= 100) {
                        System.out.println("Está numa sala com um kit de vida de " + item.getVida_recuperada());
                        System.out.println("1 - Deixa-lo na sala");
                        System.out.println("2 - Guardar");
                        System.out.print("Selecione uma opção -->");
                        do {
                            try {
                                op = sc.nextInt();
                            } catch (InputMismatchException ex) {
                                System.out.println("Numero inválido!");
                                sc.next();
                            }
                        } while (op < 1 || op > 2);
                    }

                    switch (op) {
                        case 0: {
                            divisao.usarItemDivisao();
                            break;
                        }
                        case 1: {
                            System.out.println("To Cruz não coleta o item");
                            break;
                        }
                        case 2: {
                            toCruz.guardarKit(item);
                            break;
                        }
                        default: {
                            throw new InvalidOptionException("Introduziu uma opção invalida");
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

    private void turnoToCruz(Divisao divisao_atual) {
        ToCruz toCruz = divisao_atual.getToCruz();
        Divisao divisao = divisao_atual;
        int op = -1;

        if (divisao_atual.haveConfronto()) {
            if (toCruz.mochilaTemKit()) {
                do {
                    System.out.println("1 - Atacar");
                    System.out.println("2 - Usar kit");
                    System.out.print("Selecione uma opção -->");
                    try {
                        op = sc.nextInt();
                    } catch (InputMismatchException ex) {
                        System.out.println("Numero inválido!");
                        sc.next();
                    }
                } while (op < 1 || op > 2);
            } else {
                System.out.println("Não é possível o To Cruz corrar-se porque não tem kits na mochila. Por isso ToCruz ataca!");
                op = 1;
            }

            switch (op) {
                case 1: {
                    divisao_atual.attackToCruz(this.inimigos_dead);
                    break;
                }
                case 2: {
                    toCruz.usarKit();
                    break;
                }
                default: {
                    throw new InvalidOptionException("Opção invalida");
                }
            }
        } else {
            try {
                if(toCruz.mochilaTemKit() && toCruz.getVida() < 100) {
                    String op_kit = null;
                    System.out.println("O to Cruz possui kits na sua mochila");
                    System.out.println("O proximo kit da mochila tem os seguintes pontos" + toCruz.getMochila().peek().getVida_recuperada() + " deseja se curar (Sim: S/ Nao: N)");

                    do {
                        System.out.print("Deseja utilizar o kit de vida? (Sim: S/Nao: n) -->");

                        try {
                            op_kit = sc.nextLine();
                        } catch (InputMismatchException ex) {
                            System.out.println("Numero invalido!");
                            sc.next();
                        }
                    } while(!op_kit.equals("S") && !op_kit.equals("s") && !op_kit.equals("N") && !op_kit.equals("n"));

                    if(op_kit.equals("S") || op_kit.equals("s")) {
                        toCruz.usarKit();
                    }
                }
                divisao = this.getNewDivisaoTo(divisao_atual);
                divisao.addToCruz(divisao_atual.getToCruz());
                divisao_atual.removeToCruz();

                addDivisaoTrajetoToCruz(divisao);

                if (divisao.haveConfronto()) {
                    divisao.attackToCruz(this.inimigos_dead);
                }

                if (divisao.getItem() != null) {
                    DivisaoComItem(divisao, toCruz);
                }
            } catch (NullPointerException ne) {
                System.out.println(ne.getMessage());
                divisao = divisao_atual;
            }
        }

        if (!divisao.haveConfronto() && divisao.isToCruzInDivisaoAlvo()) {
            divisao.ToCruzGetAlvo();
            toCruz.setColectedAlvo(true);
        }
    }

    private Divisao moverInimigo(Divisao divisao_atual, Inimigo inimigo) {
        Random randomizer = new Random();
        int numMoves = randomizer.nextInt(3);

        for (int i = 0; i < numMoves; i++) {
            edificio.updateWeight(divisao_atual, 0);
            StackADT<Divisao> stDiv = new LinkedStack<>();
            Divisao divisaoEscolhida;
            Iterator<Divisao> itrDiv = this.edificio.getNextDivisoes(divisao_atual);

            while (itrDiv.hasNext()) {
                stDiv.push(itrDiv.next());
            }

            int rand = randomizer.nextInt(stDiv.size());

            while (stDiv.size() - 1 > rand && !stDiv.isEmpty()) {
                stDiv.pop();
            }

            divisaoEscolhida = stDiv.peek();
            divisao_atual.removeInimigo(inimigo);
            divisaoEscolhida.addInimigo(inimigo);

            this.edificio.updateWeight(divisaoEscolhida, inimigo.getPoder());
            divisao_atual = divisaoEscolhida;
        }
        System.out.println("O inimigo" + inimigo.getNome() + " moveu-se para a sala: " + divisao_atual.getName());
        return divisao_atual;
    }

    private void turnoInimigo(Divisao divisao) {
        UnorderedListADT<Inimigo> inimigos_move = new LinearLinkedUnorderedList<>();

        for (Inimigo inimigo : divisao.getInimigos()) {
            if (!divisao.haveConfronto()) {
                System.out.println("O inimigo" + inimigo.getNome() + " está na sala" + divisao.getName());
                inimigos_move.addToRear(inimigo);
            } else {
                divisao.attackInimigo(inimigo);
            }
        }

        for (Inimigo inimigo : inimigos_move) {
            Divisao div = moverInimigo(divisao, inimigo);
            if (div.haveConfronto()) {
                div.attackInimigo(inimigo);
            }
        }
    }

    /**
     * Pode faltar sugerir a melhor entrada
     * Testar com a listDiv
     */
    private Divisao ToCruzEntrarEdificio(ToCruz toCruz) {
        int op = -1;
        int i = 0;
        Iterator<Divisao> itr = this.edificio.getPlantaEdificio().iterator();
        ArrayUnorderedADT<Divisao> listDiv = new ArrayUnordered<>();

        while (itr.hasNext()) {
            Divisao div = itr.next();

            if (div.isEntrada_saida()) {
                System.out.println(i + " - " + div.getName());
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
            divisao_nova.addToCruz(toCruz);
            addDivisaoTrajetoToCruz(divisao_nova);

            if (divisao_nova.haveConfronto()) {
                divisao_nova.attackToCruz(this.inimigos_dead);
            }

            if (!divisao_nova.haveConfronto()) {
                if (divisao_nova.isToCruzInDivisaoAlvo()) {
                    divisao_nova.ToCruzGetAlvo();
                    toCruz.setColectedAlvo(true);
                } else if (divisao_nova.getItem() != null) {
                    DivisaoComItem(divisao_nova, divisao_nova.getToCruz());
                }
            }
        } catch (NullPointerException | ArrayIndexOutOfBoundsException ex) {
            System.out.println(ex.getMessage());
        }

        return divisao_nova;
    }

    /**
     * Testar apenas a sugestão do caminho mais curto e o ToCruz sair com o Alvo
     * Não esquecer de fazer o codigo para mostrar aqui também a melhor sugestao de caminho de acordo com a divisao que o ToCruz está!!!!!!!!!!!!!!!!!!!!!!!!!!!!
     * <p>
     * Meter para sugerir o caminho mais curto ao ToCRUZ
     * Fazre o teste que comentei linha 603
     */
    @Override
    public Simulacoes modojogoManual() {
        edificio.drawMapa();
        ToCruz toCruz = new ToCruz();
        Iterator<Divisao> itrMapa;
        boolean finishgame = false;

        ToCruzEntrarEdificio(toCruz);
        while (!finishgame) {
            itrMapa = edificio.IteratorMapa();
            UnorderedListADT<Divisao> endTurno = new LinearLinkedUnorderedList<>();
            while (itrMapa.hasNext()) {
                Divisao div = itrMapa.next();

                if (div.haveInimigo()) {
                    endTurno.addToRear(div);
                }
            }

            int num_turnos = endTurno.size();
            for (int i = 0; i < num_turnos; i++) {
                turnoInimigo(endTurno.removeFirst());
            }

            itrMapa = edificio.IteratorMapa();
            boolean findToCruz = false;

            while (itrMapa.hasNext() && !findToCruz) {
                Divisao div = itrMapa.next();

                if (div.getToCruz() != null) {
                    if (div.getToCruz().isDead()) {
                        finishgame = true;
                    } else if (div.isEntrada_saida() && !div.haveConfronto() && div.isToCruzInExit() && trajeto_to.size() > 1) {
                        String op = "";
                        do {
                            System.out.print("Deseja sair do edifico(Sim: S/ Nao: N)? -->");

                            try {
                                op = sc.nextLine();
                            } catch (InputMismatchException ex) {
                                System.out.println("Numero invalido!");
                                sc.next();
                            }
                        } while (!op.equals("S") && !op.equals("s") && !op.equals("N") && !op.equals("n"));

                        if (op.equals("S") || op.equals("s")) {
                            finishgame = true;
                        }
                    } else {
                        turnoToCruz(div);
                    }

                    findToCruz = true;
                }
            }
        }

        relatoriosMissao();
        double vida = toCruz.getVida();

        if (vida < 0) {
            vida = 0;
        }
        this.vida_to = vida;
        return this;
    }

    /*
     * Se for para guardar os inimigos e os itens coletados no exportar então modificar
     * a estrutura aqui
     * */
    private void relatoriosMissao() {
        Iterator<Divisao> itrMapa = this.edificio.IteratorMapa();
        UnorderedListADT<Item> item_colected = new LinearLinkedUnorderedList<Item>();
        Alvo alvo = new Alvo();
        ToCruz toCruz = null;

        while (itrMapa.hasNext()) {
            Divisao div = itrMapa.next();

            if (div.getItem() != null && div.getItem().isCollected()) {
                item_colected.addToRear(div.getItem());
            }

            if (div.getToCruz() != null) {
                toCruz = div.getToCruz();
            }

            if (div.getAlvo() != null) {
                alvo = div.getAlvo();
            }
        }

        System.out.println(" ");
        System.out.println("!---------------------------------!");
        System.out.println(" ");
        System.out.println("Fim do jogo");
        System.out.println("Relatorio do jogo: ");
        System.out.println("Vida final do ToCruz --> " + this.vida_to);

        if (toCruz.getVida() > 0 && alvo != null) {
            System.out.println("Missão realizada com sucesso! ☆*: .｡. o(≧▽≦)o .｡.:*☆");
            System.out.println("Total de vida do ToCruz --> " + this.vida_to);
        } else {
            System.out.println("Missão falhada ಥ_ಥ");
        }

        System.out.println("Numero de inimigos mortos: " + inimigos_dead.size());
        if (!inimigos_dead.isEmpty()) {
            int i = 1;
            for(Inimigo inimigo: inimigos_dead) {
                System.out.println("Inimigo nº" + i + " : " + inimigo.toString());
                i++;
            }
        }

        System.out.println("Numero de itensColetados: " + item_colected.size());
        if (!item_colected.isEmpty()) {
            System.out.println("Itens coletados pelo o ToCruz:");

            int i = 1;
            while (!item_colected.isEmpty()) {
                System.out.println("Item coletado nº " + i + " : " + item_colected.removeFirst().toString());
                i++;
            }
        }

        StackADT<ItemCura> mochila = toCruz.getMochila();
        if (!mochila.isEmpty()) {
            System.out.println("Itens na mochilda do ToCruz:");

            int i = 1;
            while (!mochila.isEmpty()) {
                System.out.println("Item nº " + i + " : " + toCruz.getMochila().pop().toString());
                i++;
            }
        } else {
            System.out.println("A mochila do To Cruz está vazia.");
        }

        System.out.println("Percurso feito pelo o ToCruz:");
        String percurso = " ";

        QueueADT<Divisao> trajeto_temp = new LinkedQueue<Divisao>();
        while (!trajeto_to.isEmpty()) {
            Divisao trajeto = trajeto_to.dequeue();
            if (trajeto_to.isEmpty()) {
                percurso = percurso + trajeto.getName();
            } else {
                percurso = percurso + trajeto.getName() + " --> ";
            }

            trajeto_temp.enqueue(trajeto);
        }

        System.out.println(percurso);
        this.trajeto_to = trajeto_temp;
    }

    @Override
    public String toString() {
        return "Simulacoes{" +
                "versao_simulacao=" + versao_simulacao +
                ", trajeto_to=" + trajeto_to +
                ", inimigos_dead=" + inimigos_dead +
                ", vida_to=" + vida_to +
                ", edificio=" + edificio +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Simulacoes that = (Simulacoes) o;
        return versao_simulacao == that.versao_simulacao && Double.compare(vida_to, that.vida_to) == 0 && Objects.equals(trajeto_to, that.trajeto_to) && Objects.equals(edificio, that.edificio);
    }

    @Override
    public int hashCode() {
        return Objects.hash(versao_simulacao, trajeto_to, vida_to, edificio);
    }

    @Override
    public int compareTo(Simulacoes o) {
        if (this.vida_to < o.vida_to) {
            return 1;
        } else if (this.vida_to > o.vida_to) {
            return -1;
        } else {
            if (this.versao_simulacao < o.versao_simulacao) {
                return 1;
            } else if (this.versao_simulacao > o.versao_simulacao) {
                return -1;
            }
        }
        return 0;
    }
}