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

    private String cod_missao;

    private long versao;

    private Edificio edificio;

    private QueueADT<Divisao> trajeto_to;

    public Missao(String cod_missao, long versao, Edificio edificio) {
        this.cod_missao = cod_missao;
        this.versao = versao;
        this.edificio = edificio;
        this.trajeto_to = new LinkedQueue<>();
    }

    public Missao() {
        this.cod_missao = "";
        this.versao = 0;
        this.edificio = new Edificio();
        this.trajeto_to = new LinkedQueue<>();
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

        if (best_entr.haveConfronto()) {
            best_entr.attackToCruz();
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
                divisao_atual.attackToCruz();
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
            divisao_atual.removeToCruz();

            if (divisao.haveConfronto()) {
                divisao.attackToCruz();
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
                    }

                    findToCruz = true;
                }
            }
            //this.edificio.drawMapa();
            this.versao++;
        }

        //relatoriosMissao();
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

            if(shortestPath.hasNext()) {
                temp = temp + itr.next() + " -->";
            } else {
                temp = temp + itr.next();
            }

        }
        System.out.println(temp);
    }

    private Divisao getNewDivisaoTo(Divisao divisao_atual) {
        int op = -1;
        Scanner sc = new Scanner(System.in);
        Iterator<Divisao> itr = this.edificio.getNextDivisoes(divisao_atual);
        //Trocar apra UnorderedList
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
            } else if (divisao.getItemCura() != null) {
                temp = temp + " (divisao com item de cura item)";
            }

            System.out.println(temp);
            listDiv.addToRear(divisao);
        }

        /*
         * Sugerir melhor caminho To
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
        } while (op < 0 || op > listDiv.size());

        //Meter Try Catch
        return listDiv.find(op);
    }

    /*
     * Falta meter apenas sugerir o caminho mais curto para o ToCruz chegar a um kit de vida ou ao alvo
     *  */
    private void DivisaoComItem(Divisao divisao, ToCruz toCruz) throws InvalidOptionException, InvalidTypeItemException {
        ItemCura item = divisao.getItemCura();
        switch (item.getType()) {
            case COLETE: {
                if (toCruz.mochilaIsFull() && toCruz.getVida() < 100) {
                    toCruz.usarItem(item);
                }
            }
            case KIT_VIDA: {
                Scanner sc = new Scanner(System.in);
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
                } else if (!toCruz.mochilaIsFull() && toCruz.getVida() >= 100) {
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
            }
            default: {
                throw new InvalidTypeItemException("Tipo de item invalido");
            }
        }
    }

    private void turnoToCruz(Divisao divisao_atual) {
        Scanner sc = new Scanner(System.in);
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
                    divisao_atual.attackToCruz();
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
                divisao.attackToCruz();
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

    //Esta bugado
    //Só da erro no remover
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

            //Testar
            int rand = randomizer.nextInt(stDiv.size() - 1);

            while (stDiv.size() - 1 > rand && !stDiv.isEmpty()) {
                stDiv.pop();
            }

            divisaoEscolhida = stDiv.peek();
            divisao_atual.removeInimigo(inimigo);
            divisaoEscolhida.addInimigo(inimigo);

            this.edificio.updateWeight(divisaoEscolhida, inimigo.getPoder());
            divisao_atual = divisaoEscolhida; //Testar
        }
        System.out.println("O inimigo" + inimigo.getNome() + " moveu-se para a sala: " + divisao_atual.getName());
        return divisao_atual;
    }

    private void turnoInimigo(Divisao divisao) {
        LinearLinkedUnorderedList<Inimigo> inimigos_move = new LinearLinkedUnorderedList<>();

        for (Inimigo inimigo: divisao.getInimigos()) {
            if (!divisao.haveConfronto()) {
                System.out.println("O inimigo" + inimigo.getNome() + " está na sala" + divisao.getName());
                inimigos_move.addToRear(inimigo);
            } else {
                divisao.attackInimigo(inimigo);
            }
        }

        for (Inimigo inimigo: inimigos_move) {
            Divisao div = moverInimigo(divisao, inimigo);
            if (div.haveConfronto()) {
                div.attackInimigo(inimigo);
            }
        }
    }


    /**
     * Talvez deve para juntar o final dela com uma parte do turno do ToCruz
     */
    //Também meter para sugerir uma entrada ao ToCruz (Meter a sala que está mais perto do alvo ou item)
    private Divisao ToCruzEntrarEdificio(ToCruz toCruz) {
        int op = -1;
        int i = 0;
        Scanner sc = new Scanner(System.in);

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
                divisao_nova.attackToCruz();
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
     * Não esquecer de fazer o codigo para mostrar aqui também a melhor sugestao de caminho de acordo com a divisao que o ToCruz está!!!!!!!!!!!!!!!!!!!!!!!!!!!!
     * <p>
     * Ver se alterar não dá exceptionModCount
     * Meter para sugerir o caminho mais curto ao ToCRUZ
     * Fazre o teste que comentei linha 603
     */
    @Override
    public void modoManual() {
        //this.edificio.drawMapa();
        ToCruz toCruz = new ToCruz();
        Iterator<Divisao> itrMapa = this.edificio.IteratorMapa();
        Divisao divisaoInicial = ToCruzEntrarEdificio(toCruz);
        boolean finishgame = false;
        boolean addSucesso = false;

        //Testar sem isto e meter o addToCruz no ToCruzEntrarEdificio(toCruz)
        while (itrMapa.hasNext() && !addSucesso) {
            Divisao div = itrMapa.next();
            if (div.equals(divisaoInicial)) {
                div.addToCruz(divisaoInicial.getToCruz());
                addSucesso = true;
                System.out.println("To Cruz entrou na divisao: " + div.getName());
            }
        }

        //Ver porque o ToCruz saiu nas escadas 5 (porque os inimigos movimentação em mais do que uma casa)
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
                    }

                    findToCruz = true;
                }
            }

            this.versao++;
        }

        relatoriosMissao();
    }

    //Ver como depois posso fazer o relatorio da missao
    //Acabar de fazer
    private void relatoriosMissao() {
        Iterator<Divisao> itrMapa = this.edificio.IteratorMapa();
        boolean missaoSucesso = false;
        UnorderedListADT<ItemCura> item_colected = new LinearLinkedUnorderedList<ItemCura>();
        ToCruz toCruz = null;

        while (itrMapa.hasNext()) {
            Divisao div = itrMapa.next();
        }

        if (missaoSucesso) {
            System.out.println("Missão realizada com sucesso! ☆*: .｡. o(≧▽≦)o .｡.:*☆");
            System.out.println("Total de vida do ToCruz --> " + toCruz.getVida());
        } else {
            System.out.println("Missão falhada ಥ_ಥ");
        }

        System.out.println("Numero de itensColetados: " + item_colected.size());
        if (!item_colected.isEmpty()) {
            System.out.println("Itens coletados pelo o ToCruz:");
            System.out.println(item_colected.toString());
        }

        if (!toCruz.getMochila().isEmpty()) {
            System.out.println("Itens na mochilda do ToCruz:");
            System.out.println(toCruz.getMochila().toString());
        } else {
            System.out.println("A mochila do To Cruz está vazia.");
        }

        System.out.println("Percurso feito pelo o ToCruz:");
        System.out.print(trajeto_to.toString());
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
