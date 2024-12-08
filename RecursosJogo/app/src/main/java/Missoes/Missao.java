package Missoes;

import Exceptions.InvalidOptionException;
import Exceptions.InvalidTypeItemException;
import Interfaces.*;
import Items.ItemCura;
import ArrayList.ArrayUnorderedList;
import LinkedList.LinearLinkedUnorderedList;
import Mapa.Divisao;
import Mapa.Edificio;
import Personagens.Inimigo;
import Personagens.ToCruz;
import Queue.LinkedQueue;
import Stacks.LinkedStack;
import ArrayList.ArrayUnordered;
import Exportar.ExportarDado;
import java.util.*;

/**
 * Falta o turno do inimigo (corrigir)
 * Falta o modo automatico (e testa-lo)
 * Falta sugerir o caminho mais curto para o item ou alvo para o To Cruz
 * Falta verificar se nos metodos estão a ser utilizadas as melhores estruturas
 * <p>
 * A nivel de testes só falta testar no modo manual a parte de o ToCruz entrar numa divisao com item
 */
public class Missao implements MissaoInt {

    private static Scanner sc;

    private String cod_missao;

    private long versao;

    private Edificio edificio;

    private QueueADT<Divisao> trajeto_to;

    private StackADT<Inimigo> inimigos_dead;

    public Missao(String cod_missao, long versao, Edificio edificio) {
        this.cod_missao = cod_missao;
        this.versao = versao;
        this.edificio = edificio;
        this.trajeto_to = new LinkedQueue<>();
        this.inimigos_dead = new LinkedStack<>();
        this.sc = new Scanner(System.in);
    }

    public Missao() {
        this.cod_missao = "";
        this.versao = 0;
        this.edificio = new Edificio();
        this.trajeto_to = new LinkedQueue<>();
        this.inimigos_dead = new LinkedStack<>();
        this.sc = new Scanner(System.in);
    }

    public QueueADT<Divisao> getTrajeto_to() {
        return trajeto_to;
    }

    /*
     * Adiciona um nova divisão ao percurso manual do To Cruz.
     * */
    private void addDivisaoTrajetoToCruz(Divisao divisao) {
        this.trajeto_to.enqueue(divisao);
    }

    /**
     * Este metodo diz no modoAutomatico quando é que o ToCruz deve coletar o Item de cura da sala, usa-lo ou até mesmo ignora-lo
     * e em que momento no confronto em que ele deve utilizar um Item da mochila
     */

    private void exportarMissao(QueueADT<Divisao> trajeto) {
        QueueADT<QueueADT<Divisao>> trajetoQueue = new LinkedQueue<>();
        trajetoQueue.enqueue(trajeto);
        ExportarDado exportar = new ExportarDado(versao, trajetoQueue);
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
        exportar.exportarDados(path);
    }

    private void ConditionGetUseItemAutomatico(Divisao divisao) throws InvalidTypeItemException {
        ItemCura itemCura = divisao.getItemCura();
        ToCruz toCruz = divisao.getToCruz();

        if (itemCura != null) {
            switch (itemCura.getType()) {
                case COLETE: {
                    toCruz.usarItem(itemCura);
                    break;
                }
                case KIT_VIDA: {
                    if (!toCruz.mochilaIsFull() && (toCruz.getVida() == 100 || toCruz.getVida() + itemCura.getVida_recuperada() >= 100)) {
                        toCruz.guardarKit(itemCura);
                    } else if (toCruz.getVida() + itemCura.getVida_recuperada() <= 100) {
                        toCruz.usarItem(itemCura);
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

            if (div.getItemCura() != null || div.getAlvo() != null) {
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
        trajeto_to.enqueue(best_entr);

        if (best_entr.haveConfronto()) {
            best_entr.attackToCruz(this.inimigos_dead);
        }

        if (best_entr.getItemCura() != null) {
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
            //Posso tamvém chamar o Metodo que fiz
            if (toCruz.getVida() <= 30 && toCruz.mochilaTemKit()) {
                toCruz.usarKit();
            } else {
                divisao_atual.attackToCruz(this.inimigos_dead);
            }
        } else {
            //Ver se está parte está realmente bem
            Iterator<Divisao> itr = this.edificio.getPlantaEdificio().iterator();
            Divisao best_div = null;
            double min = 0;
            double distance;
            int i = 0;

            //Vê se o alvo já foi coletado se sim ele vai procurar por items ou saida (depois otimizar)
            if (!toCruz.isColectedAlvo()) {
                while (itr.hasNext()) {
                    Divisao div = itr.next();

                    if (div.getAlvo() != null || (div.getItemCura() != null && !div.getItemCura().isCollected())) {
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

                    if (div.isEntrada_saida() || (div.getItemCura() != null && !div.getItemCura().isCollected())) {
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
            this.trajeto_to.enqueue(divisao);
            divisao_atual.removeToCruz();

            if (divisao.haveConfronto()) {
                divisao.attackToCruz(this.inimigos_dead);
            }

            if (divisao.getItemCura() != null) {
                ConditionGetUseItemAutomatico(divisao);
            }
        }

        if (!divisao.haveConfronto() && divisao.isToCruzInDivisaoAlvo() && !divisao.getAlvo().isAtinigido()) {
            divisao.ToCruzGetAlvo();
            toCruz.setColectedAlvo(true);
        }
    }

    /**
     * Mete o jogo a funcionar automaticamente.
     * Ver se o turno do Inimigo funciona
     * Mostrar o melhor caminho
     * <p>
     * Falta apenas testar, mas para isso é preciso o Dijkstra
     */
    @Override
    public void modoAutomatico() {
        //ToCruz entra na sala (meter o codigo)
        //this.edificio.drawMapa();
        ToCruz toCruz = new ToCruz();
        Iterator<Divisao> itrMapa;
        boolean finishgame = false;

        BestStartToCruz(toCruz); //Ver se isto mete já o ToCruz na melhor divisao se sim alterar no modo manual se não alterar e meter como o manual
        while (!finishgame) {
            //this.edificio.drawMapa();
            itrMapa = this.edificio.IteratorMapa();
            LinearLinkedUnorderedList<Divisao> endTurno = new LinearLinkedUnorderedList<>();
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
            if (div.getAlvo() != null && !toCruz.isColectedAlvo() || div.getItemCura() != null || div.isEntrada_saida() && toCruz.isColectedAlvo()) {
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
        //Trocar apra UnorderedList
        ArrayUnorderedADT<Divisao> listDiv = new ArrayUnordered<Divisao>();

        System.out.println("Selecione a divisao para o ToCruz se mover!");

        int i = 0;
        String temp = "";
        while (itr.hasNext()) {
            Divisao divisao = itr.next();
            temp = i++ + " - " + divisao.getName();

            if(divisao.isEntrada_saida() || divisao.getAlvo() != null)
            if (divisao.isEntrada_saida()) {
                temp = temp + " (esta divisao e uma entrada/saida)";
            } else if (divisao.getAlvo() != null) {
                temp = temp + " (divisao onde esta o alvo)";
            } else if (divisao.getItemCura() != null) {
                temp = temp + " (divisao com item de cura item)";
            }

            System.out.println(temp);
            listDiv.addToRear(divisao);
        }

        /*
         * Sugerir melhor caminho To DÁ ERRO
         * */
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

        //Meter Try Catch
        return listDiv.find(op);
    }

    /*
     * Falta meter apenas sugerir o caminho mais curto para o ToCruz chegar a um kit de vida ou ao alvo
     *
     * Verificar se estão todos os cenarios feitos
     *  */
    private void DivisaoComItem(Divisao divisao, ToCruz toCruz) throws InvalidOptionException, InvalidTypeItemException {
        ItemCura item = divisao.getItemCura();
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
                } else if(!toCruz.mochilaIsFull() && toCruz.getVida() >= 100) {
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
            divisao = this.getNewDivisaoTo(divisao_atual);
            divisao.addToCruz(divisao_atual.getToCruz());
            divisao_atual.removeToCruz();

            addDivisaoTrajetoToCruz(divisao);

            if (divisao.haveConfronto()) {
                divisao.attackToCruz(this.inimigos_dead);
            }

            if (divisao.getItemCura() != null) {
                DivisaoComItem(divisao, toCruz);
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
        LinearLinkedUnorderedList<Inimigo> inimigos_move = new LinearLinkedUnorderedList<>();

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
     * Falta sugerir a melhor entrada, é reutilizar o codigo da bestEntradaToCruz, do modo automatico
     */
    //Também meter para sugerir uma entrada ao ToCruz (Meter a sala que está mais perto do alvo ou item)
    private Divisao ToCruzEntrarEdificio(ToCruz toCruz) {
        int op = -1;
        int i = 0;

        Iterator<Divisao> itr = this.edificio.getPlantaEdificio().iterator();
        StackADT<Divisao> listDiv = new LinkedStack<>();
        Divisao[] divisaos = new Divisao[this.edificio.getNumEntradasSaidas()]; //Numero de divisoes

        while (itr.hasNext()) {
            Divisao div = itr.next();

            if (div.isEntrada_saida()) {
                System.out.println(i + " - " + div.getName());
                listDiv.push(div);
                i++;
            }
        }

        /**
         * Sugerir melhor entrada
         * */

        do {
            System.out.println("Introduza onde o ToCruz vai entrar -->");

            try {
                op = sc.nextInt();
            } catch (InputMismatchException ex) {
                System.out.println("Numero invalido!");
                sc.next();
            }
        } while (op < 0 || op > i);

        Divisao divisao_nova = null;
        while (listDiv.size() - 1 > op && !listDiv.isEmpty()) {
            listDiv.pop();
        }

        try {
            divisao_nova = divisaos[op];
            divisao_nova = listDiv.peek();
            divisao_nova.addToCruz(toCruz);
            addDivisaoTrajetoToCruz(divisao_nova);

            if (divisao_nova.haveConfronto()) {
                divisao_nova.attackToCruz(this.inimigos_dead);
            }

            if (!divisao_nova.haveConfronto()) {
                if (divisao_nova.isToCruzInDivisaoAlvo()) {
                    divisao_nova.ToCruzGetAlvo();
                    toCruz.setColectedAlvo(true);
                } else if (divisao_nova.getItemCura() != null) {
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
            LinearLinkedUnorderedList<Divisao> endTurno = new LinearLinkedUnorderedList<>();
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
        UnorderedListADT<ItemCura> item_colected = new LinearLinkedUnorderedList<ItemCura>();
        ToCruz toCruz = null;

        while (itrMapa.hasNext()) {
            Divisao div = itrMapa.next();

            if (div.getItemCura() != null && div.getItemCura().isCollected()) {
                item_colected.addToRear(div.getItemCura());
            }

            if (div.getToCruz() != null) {
                toCruz = div.getToCruz();
            }
        }

        System.out.println(" ");
        System.out.println("!---------------------------------!");
        System.out.println(" ");
        System.out.println("Fim do jogo");
        System.out.println("Relatorio do jogo: ");
        if (toCruz.getVida() > 0) {
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
        QueueADT<Divisao> trajeto_temp = new LinkedQueue<Divisao>();
        while (!trajeto_to.isEmpty()) {
            Divisao div = trajeto_to.dequeue();
            if (trajeto_to.isEmpty()) {
                percurso = percurso + div.getName();
            } else {
                percurso = percurso + div.getName() + " --> ";
            }

            trajeto_temp.enqueue(div);
        }

        System.out.println(percurso);
        exportarMissao(trajeto_temp);
    }

    @Override
    public String toString() {
        return "Missao{" +
                "cod_missao='" + cod_missao + '\'' +
                ", versao=" + versao +
                ", edificio=" + edificio.toString() +
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
