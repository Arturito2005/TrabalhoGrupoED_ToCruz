package Missoes;

import Exceptions.InvalidOptionException;
import Exceptions.InvalidTypeItemException;
import Interfaces.MissaoInt;
import Items.ItemCura;
import ArrayList.ArrayUnorderedList;
import LinkedList.LinearLinkedUnorderedList;
import Mapa.Divisao;
import Mapa.Edificio;
import Personagens.Inimigo;
import Personagens.ToCruz;
import Queue.LinkedQueue;
import Stacks.LinkedStack;

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

    private LinkedQueue<Divisao> trajeto_to;

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
                    //Ver depois o usarItem
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
        Divisao div_start = null;
        Divisao div_alvo = null;
        ArrayUnorderedList<Divisao> list_item = new ArrayUnorderedList<>();
        Iterator<Divisao> itr;

        //1º Encontrar todos os itens e o alvo
        itr = this.edificio.getPlantaEdificio().iterator();

        while (itr.hasNext()) {
            Divisao div = itr.next();

            if (div.getItemCura() != null) {
                list_item.addToRear(div);
            } else if (div.getAlvo() != null) {
                div_alvo = div;
            }
        }

        //2º Ver o caminho mais curto de cada divisao para o item ou alvo (Deve estar muito mal)
        double distance = 0;
        double min = 0;
        ArrayUnorderedList<Double> min_divi = new ArrayUnorderedList<Double>();
        itr = this.edificio.getPlantaEdificio().iterator();

        while (itr.hasNext()) {
            Divisao div = itr.next();

            if(div.isEntrada_saida()) {
                distance = this.edificio.getShortestPath(div, div_alvo);
                min = distance;
                div_start = div;
                Iterator<Divisao> itr_item = list_item.iterator();

                while (itr_item.hasNext()) {
                    Divisao div_item = itr_item.next();
                    distance = this.edificio.getShortestPath(div, div_item);

                    if(distance < min) {
                        min = distance;
                        div_start = div_item;
                    }

                }
            }
        }

        //3º Encontrar o caminho mais curto para um item
        div_start.addToCruz(toCruz);

        if (div_start.haveConfronto()) {
            div_start.attackToCruz();
        }

        if (div_start.getItemCura() != null) {
            DivisaoComItem(div_start, div_start.getToCruz());
        }

        return div_start;
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
        ToCruz toCruz = new ToCruz();
        Iterator<Divisao> itrMapa;
        boolean finishgame = false;

        BestStartToCruz(toCruz); //Ver se isto mete já o ToCruz na melhor divisao se sim alterar no modo manual se não alterar e meter como o manual
        while (!finishgame) {
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

            this.versao++;
        }

        relatoriosMissao();
    }


    /*
     * Retorna a nova divisao do ToCruz, ver se a parte de seleção da divisao está correta
     *
     * Falta meter agora metodo para sugerir o melhor caminho ao ToCruz para chegar a um item( No final de cada turno, deve ser
     * apresentado ao Tó Cruz o melhor caminho para o alvo e para o kit de recuperação mais próximo.)
     * */
    private Divisao getNewDivisaoTo(Divisao divisao_atual) {
        int op = -1;
        Scanner sc = new Scanner(System.in);
        Iterator<Divisao> itr = this.edificio.getNextDivisoes(divisao_atual);
        ArrayUnorderedList<Divisao> listDiv = new ArrayUnorderedList<>();

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
        /*
        * Iterator<Divisao> itr = this.edificio.getPlantaEdificio().iterator();
        Divisao best_div = null;
        double min = 0;
        double distance;
        int i = 0;

        while (itr.hasNext()) {
            Divisao div = itr.next();

            if (div.getAlvo() != null || div.getItemCura() != null) {
                distance = this.edificio.getShortestPath(divisao_atual, div);

                if (distance < min || i == 0) {
                    min = distance;
                    best_div = div;
                }

                i++;
            }
        }
        * */
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
    private void moverInimigo(Divisao divisao_atual, Inimigo inimigo) {
        Random randomizer = new Random();
        int numMoves = randomizer.nextInt(3);

        for (int i = 0; i < numMoves; i++) {
            this.edificio.updateWeight(divisao_atual, 0);
            ArrayUnorderedList<Divisao> listDiv = new ArrayUnorderedList<>();
            Divisao divisaoEscolhida;
            Iterator<Divisao> itrDiv = this.edificio.getNextDivisoes(divisao_atual);

            while (itrDiv.hasNext()) {
                listDiv.addToRear(itrDiv.next());
            }

            int rand = randomizer.nextInt(listDiv.size());

            divisaoEscolhida = listDiv.find(rand);
            divisao_atual.removeInimigo(inimigo);
            divisaoEscolhida.addInimigo(inimigo);

            this.edificio.updateWeight(divisaoEscolhida, inimigo.getPoder());
            divisao_atual = divisaoEscolhida; //Testar
        }
        System.out.println("O inimigo" + inimigo.getNome() + " moveu-se para a sala" + divisao_atual.getName());
    }

    //Acho que já sei como corrigir é só trocar o iterator pela lista
    private void turnoInimigo(Divisao divisao) {
        Iterator<Inimigo> inimigos = divisao.getInimigos().iterator();
        Inimigo inimigo;

        while (inimigos.hasNext()) {
            inimigo = inimigos.next();

            if (!divisao.haveConfronto()) {
                System.out.println("O inimigo" + inimigo.getNome() + " está na sala" + divisao.getName());
                moverInimigo(divisao, inimigo);

                if (divisao.haveConfronto()) {
                    divisao.attackInimigo(inimigo);
                }
            } else {
                divisao.attackInimigo(inimigo);
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
        ArrayUnorderedList<Divisao> listDiv = new ArrayUnorderedList<>();

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
        } while (op < 0 || op > i);

        Divisao divisao_nova = null;

        try {
            divisao_nova = listDiv.find(op);
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
            //Turno de cada inimigo só falta a movimentação
            //Se a sala estiver com dois inimigos só um é que fica em confronto
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
        LinkedList<Inimigo> inimigos_dead = new LinkedList<>();
        LinkedList<ItemCura> item_colected = new LinkedList<>();
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

        System.out.println("Numero de inimigos mortos: " + inimigos_dead.size());

        if (!inimigos_dead.isEmpty()) {
            System.out.println("Inimigos mortos pelo o To Cruz:");
            System.out.println(inimigos_dead.toString());
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
