package Missoes;

import Exceptions.InvalidOptionException;
import Exceptions.InvalidTypeItemException;
import Interfaces.*;
import Items.Item;
import ArrayList.ArrayUnorderedList;
import Items.ItemCura;
import LinkedList.LinearLinkedUnorderedList;
import Mapa.Alvo;
import Mapa.Divisao;
import Mapa.Edificio;
import Mapa.Trajeto;
import Personagens.Inimigo;
import Personagens.ToCruz;
import Queue.LinkedQueue;
import Stacks.LinkedStack;
import ArrayList.ArrayUnordered;
import Exportar.ExportarDado;
import java.util.*;

/**
 * Falta no updateWeight o weight total ser o dano que o ToCruz toma no total dos turnos todos (ver se realmente é para fazer isso)
 * Falta verificar se nos metodos estão a ser utilizadas as melhores estruturas
 * <p>
 * A nivel de testes só falta testar no modo manual a parte de o ToCruz entrar numa divisao com item
 */
public class Missao implements MissaoInt {

    private static Scanner sc = new Scanner(System.in);

    private String cod_missao;

    private long versao;

    private Edificio edificio;

    private QueueADT<Trajeto> trajeto_to;

    private StackADT<Inimigo> inimigos_dead;

    public Missao(String cod_missao, long versao, Edificio edificio) {
        this.cod_missao = cod_missao;
        this.versao = versao;
        this.edificio = edificio;
        this.trajeto_to = new LinkedQueue<>();
        this.inimigos_dead = new LinkedStack<>();
    }

    public Missao() {
        this.cod_missao = "";
        this.versao = 0;
        this.edificio = new Edificio();
        this.trajeto_to = new LinkedQueue<>();
        this.inimigos_dead = new LinkedStack<>();
    }

    private void addDivisaoTrajetoToCruz(long versao, Divisao divisao, double pontos_vida) {
        this.trajeto_to.enqueue(new Trajeto(versao, divisao, pontos_vida));
    }

    private void ConditionGetUseItemAutomatico(Divisao divisao) throws InvalidTypeItemException {
        if(divisao.getItem() instanceof ItemCura) {
            ItemCura Item = (ItemCura) divisao.getItem();
            ToCruz toCruz = divisao.getToCruz();

            if (Item != null) {
                switch (Item.getType()) {
                    case COLETE: {
                        toCruz.usarItem(Item);
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

    //Sugere a melhor divisão para o ToCruz entrar.

    /**
     * Introduz de forma automatica o ToCruz na melhor divisao que ele pode entrar
     * Testar a ver se funciona
     */
    private Divisao BestStartToCruz(ToCruz toCruz) {
        UnorderedListADT<Divisao> list_entradas = new ArrayUnorderedList<>();
        Iterator<Divisao> itr;

        //1º Encontrar todos os itens e o alvo
        itr = this.edificio.getPlantaEdificio().iterator();

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
        itr = this.edificio.getPlantaEdificio().iterator();

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
        addDivisaoTrajetoToCruz(versao, best_entr, toCruz.getVida());

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
                        distance = this.edificio.getShortestPath(divisao_atual, div);

                        if (distance < min || i == 0) {
                            min = distance;
                            best_div = div;
                        }

                        i++;
                    }
                }
            }

            divisao = this.edificio.nextDivAutomaticToCruz(divisao_atual, best_div);
            divisao.addToCruz(toCruz);
            this.addDivisaoTrajetoToCruz(versao, divisao, toCruz.getVida());
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

    /**
     * Ver a melhor forma do getShortestPath, porque ele retornar o num_arestas também, talvez fazer
     * outro metodo que apenas retona o numero de arestas, porque o ShortestPath tem logica apenas retorar o dano
     * e ter outro que apenas retorna o num_arestas
     *
     * Fazer o codigo para arestas na network
     */
    @Override
    public void modoAutomatico() {
        Iterator<Divisao> itr = this.edificio.getPlantaEdificio().iterator();
        UnorderedListADT<Divisao> list_entradas = new LinearLinkedUnorderedList<Divisao>();
        Divisao div_alvo = null;
        Divisao best_div = null;
        ToCruz to = new ToCruz();

        while (itr.hasNext()) {
            Divisao div = itr.next();

            if (div.isEntrada_saida()) {
                list_entradas.addToRear(div);
            }

            if (div.getAlvo() != null) {
                div_alvo = div;
            }
        }

        double best_distance = Double.MAX_VALUE;
        double distance;
        double num_arestas_com = Double.MAX_VALUE;
        double num_arestas = 0;

        for (Divisao div_entr : list_entradas) {
            distance = this.edificio.getShortestPath(div_entr, div_alvo);

            if(distance == 0) {
                best_distance = distance;
                num_arestas = this.edificio.getShortestPathNumArestas(div_entr, div_alvo);

                if(num_arestas < num_arestas_com) {
                    best_div = div_entr;
                    num_arestas_com = num_arestas;
                }
            } else if (distance < best_distance) {
                best_div = div_entr;
                best_distance = distance;
            }
        }

        //Esta comparacao não está assim tão bem pois ele considera que o andar dá dano ao toCruz
        if (best_distance < to.getVida()) {
            Iterator<Divisao> itr2 = this.edificio.shortesPathIt(best_div, div_alvo);
            System.out.println("A melhor entrada que o To Cruz deve escolher é esta: " + best_div);
            System.out.println("Melhor caminho que o To Cruz pode fazer até ao alvo");

            String temp = "";
            while (itr2.hasNext()) {
                Divisao div = itr2.next();

                if(itr.hasNext()) {
                    temp += div.getName() + " --> ";
                } else {
                    temp += div.getName() + " ";
                }
            }
            System.out.println(temp);


            best_distance = Double.MAX_VALUE;
            best_div = null;
            distance = 0;
            num_arestas_com = Double.MAX_VALUE;
            num_arestas = 0;
            for (Divisao div_entr : list_entradas) {
                distance = this.edificio.getShortestPath(div_entr, div_alvo);

                if(distance == 0) {
                    best_distance = distance;
                    num_arestas = this.edificio.getShortestPathNumArestas(div_entr, div_alvo);

                    if(num_arestas < num_arestas_com) {
                        best_div = div_entr;
                        num_arestas_com = num_arestas;
                    }
                } else if (distance < best_distance) {
                    best_div = div_entr;
                    best_distance = distance;
                }
            }

            //Ver aqui porque se a distancia for igual a 0 ele vai meter o numero de arestas.
            if(best_distance < to.getVida()) {
                Iterator<Divisao> itr3 = this.edificio.shortesPathIt(div_alvo, best_div);
                System.out.println("Melhor caminho que o To Cruz deve fazer para sair do edificio");

                temp = "";
                while (itr2.hasNext()) {
                    Divisao div = itr2.next();

                    if(itr.hasNext()) {
                        temp += div.getName() + " --> ";
                    } else {
                        temp += div.getName() + " ";
                    }
                }
                System.out.println(temp);
            } else {
                System.out.println("É impossível o To Cruz sair do edificio com vida!");
            }
        } else {
            System.out.println("O To Cruz não consegue chegar ao alvo sem morrer!");
        }
    }

    /**
     * Joga o jogo de forma automático
     */
    public void jogoAutomatico() {
        //ToCruz entra na sala (meter o codigo)
        //this.edificio.drawMapa();
        ToCruz toCruz = new ToCruz();
        Iterator<Divisao> itrMapa;
        boolean finishgame = false;

        BestStartToCruz(toCruz); //Ver se isto mete já o ToCruz na melhor divisao se sim alterar no modo manual se não alterar e meter como o manual
        this.versao++;
        while (!finishgame) {
            //this.edificio.drawMapa();
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
            //this.edificio.drawMapa();
            while (itrMapa.hasNext() && !findToCruz) {
                Divisao div = itrMapa.next();

                if (div.getToCruz() != null) {
                    if (div.getToCruz().isDead() || (div.isToCruzInExit() && trajeto_to.size() > 1)) {
                        finishgame = true;
                    } else {
                        turnoAutomaticoToCruz(div);
                        this.versao++;
                    }

                    findToCruz = true;
                }
            }
            //this.edificio.drawMapa();
        }

        relatoriosMissao();
    }

    /*
     * Dá a suguestão do melhor caminho ao To Cruz
     *
     * */
    private void sugestaoCaminhoToCruz(Divisao div_to, ArrayUnorderedADT<Divisao> listDiv) {
        ToCruz toCruz = div_to.getToCruz();
        Divisao min_division = null;
        Iterator<Divisao> itr = listDiv.iterator();

        double min = Double.MAX_VALUE;
        while (itr.hasNext()) {
            Divisao div = itr.next();

            double distance = Double.MAX_VALUE;
            if ((div.getAlvo() != null && !toCruz.isColectedAlvo()) || (div.getItem() != null) || (div.isEntrada_saida() && toCruz.isColectedAlvo())) {
                distance = this.edificio.getShortestPath(div_to, div);
            }

            if (distance < min) {
                min = distance;
                min_division = div;
            }
        }

        Iterator<Divisao> shortestPath = this.edificio.shortesPathIt(div_to, min_division);
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

    private Divisao getNewDivisaoTo(Divisao divisao_atual) {
        int op = -1;
        Iterator<Divisao> itr = this.edificio.getNextDivisoes(divisao_atual);
        ArrayUnorderedADT<Divisao> listDiv = new ArrayUnordered<Divisao>();

        System.out.println("Selecione a divisao para o ToCruz se mover!");

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

        sugestaoCaminhoToCruz(divisao_atual, listDiv);

        /*
         * Fim da sugestão
         * */
        do {
            System.out.println("Selecione a divisao que o ToCruz vai se mover");

            try {
                op = sc.nextInt();
            } catch (InputMismatchException ex) {
                System.out.println("Numero invalido!");
                sc.next();
            }
        } while (op < 0 || op > listDiv.size() - 1);

        Divisao div = null;
        try {
            div= listDiv.find(op);
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
        if(divisao.getItem() instanceof ItemCura) {
            ItemCura item = (ItemCura ) divisao.getItem();

            switch (item.getType()) {
                case COLETE: {
                    if (toCruz.mochilaIsFull() && toCruz.getVida() < 100) {
                        toCruz.usarItem(item);
                    }
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
                divisao = this.getNewDivisaoTo(divisao_atual);
                divisao.addToCruz(divisao_atual.getToCruz());
                divisao_atual.removeToCruz();

                addDivisaoTrajetoToCruz(versao, divisao, toCruz.getVida());

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
            this.edificio.updateWeight(divisao_atual, 0);
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
            System.out.println("Introduza onde o ToCruz vai entrar -->");

            try {
                op = sc.nextInt();
            } catch (InputMismatchException ex) {
                System.out.println("Numero invalido!");
                sc.next();
            }
        } while (op < 0 || op > listDiv.size());

        Divisao divisao_nova = null;

        try {
            divisao_nova = listDiv.find(op);
            divisao_nova.addToCruz(toCruz);
            addDivisaoTrajetoToCruz(versao, divisao_nova, toCruz.getVida());

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
    public void modoManual() {
        this.edificio.drawMapa();
        ToCruz toCruz = new ToCruz();
        Iterator<Divisao> itrMapa;
        boolean finishgame = false;

        ToCruzEntrarEdificio(toCruz);
        while (!finishgame) {
            itrMapa = this.edificio.IteratorMapa();
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

            itrMapa = this.edificio.IteratorMapa();
            boolean findToCruz = false;

            while (itrMapa.hasNext() && !findToCruz) {
                Divisao div = itrMapa.next();

                if (div.getToCruz() != null) {
                    if (div.getToCruz().isDead() || (div.isToCruzInExit() && trajeto_to.size() > 1)) {
                        finishgame = true;
                    } else {
                        turnoToCruz(div);
                        this.versao++;
                    }

                    findToCruz = true;
                }
            }
        }

        relatoriosMissao();
    }

    //Ver como depois posso fazer o relatorio da missao
    //Acabar de fazer
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
        if (toCruz.getVida() > 0 && alvo != null) {
            System.out.println("Missão realizada com sucesso! ☆*: .｡. o(≧▽≦)o .｡.:*☆");
            System.out.println("Total de vida do ToCruz --> " + toCruz.getVida());
        } else {
            System.out.println("Missão falhada ಥ_ಥ");
        }


        System.out.println("Numero de inimigos mortos: " + inimigos_dead.size());
        if (!inimigos_dead.isEmpty()) {
            int i = 1;
            while (!inimigos_dead.isEmpty()) {
                System.out.println("Inimigo nº" + i + " : " + inimigos_dead.pop().toString());
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
        //Talvez mandar uma nova queue para o exportar
        QueueADT<Trajeto> trajeto_temp = new LinkedQueue<Trajeto>();
        while (!trajeto_to.isEmpty()) {
            Trajeto trajeto = trajeto_to.dequeue();
            if (trajeto_to.isEmpty()) {
                percurso = percurso + trajeto.toString();
            } else {
                percurso = percurso + trajeto.toString() + " --> ";
            }

            trajeto_temp.enqueue(trajeto);
        }

        System.out.println(percurso);
        exportarMissao(trajeto_temp);
    }

    private void exportarMissao(QueueADT<Trajeto> trajeto) {
        QueueADT<QueueADT<Trajeto>> trajetoQueue = new LinkedQueue<>();
        trajetoQueue.enqueue(trajeto);
        //ExportarDado exportar = new ExportarDado(cod_missao, trajetoQueue);
        String path = "./Jsons/Export/";
        String name_file = "";

        do {
            System.out.println("Introduza o nome do fichiro que vai conter o trajeto do To Cruz -->");
            try {
                name_file = sc.nextLine();
            } catch (InputMismatchException ex) {
                System.out.println("Numero invalido!");
                sc.next();
            }
        } while (name_file.equals(""));

        path += name_file;
        //exportar.exportarDados(path);
    }

    @Override
    public String toString() {
        return "Missao{" +
                "cod_missao='" + cod_missao + '\'' +
                ", versao=" + versao +
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

        Missao missao = (Missao) o;
        return Objects.equals(cod_missao, missao.cod_missao);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(cod_missao);
    }
}
