package Missoes;

import Graph.GraphListaAdjacencia;
import Interfaces.MissaoADT;
import Interfaces.QueueADT;
import Interfaces.StackADT;
import Items.ItemCura;
import Items.TypeItemCura;
import LinkedList.LinearLinkedUnorderedList;
import Mapa.Alvo;
import Mapa.Divisao;
import Mapa.Edificio;
import Personagens.Inimigo;
import Personagens.ToCruz;
import Queue.LinkedQueue;
import Stacks.LinkedStack;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Objects;
import java.util.Scanner;

/**
 * Falta o turno do inimigo
 * Falta o modo automatico
 * Falta sugerir o caminho mais curto para o item ou alvo para o To Cruz
 * Trocar o Grafo sem custo para um grafo com custo
 *
 * Ver se realmente vão ser preciso getters, penso que só será necessário os metodos da MissaoADT.
 * */
public class Missao implements MissaoADT {

    private String cod_missao;

    private long versao;

    private Edificio edificio;

    private Alvo alvo;

    private LinearLinkedUnorderedList<ItemCura> item_list;

    private LinkedStack<ItemCura> item_colected;

    private LinearLinkedUnorderedList<Inimigo> inimigos;

    private LinkedStack<Inimigo> inimigos_dead;

    private LinearLinkedUnorderedList<Inimigo> inimigos_confronto;

    private LinkedQueue<Divisao> trajeto_to;

    private ToCruz toCruz;

    public Missao(String cod_missao, long versao, Edificio edificio, Alvo alvo, LinearLinkedUnorderedList<ItemCura> item_list, LinearLinkedUnorderedList<Inimigo> inimigos) {
        this.cod_missao = cod_missao;
        this.versao = versao;
        this.edificio = edificio;
        this.alvo = alvo;
        this.item_list = item_list;
        this.item_colected = new LinkedStack<>();
        this.inimigos = inimigos;
        this.inimigos_confronto = new LinearLinkedUnorderedList<>();
        this.inimigos_dead = new LinkedStack<>();
        this.trajeto_to = new LinkedQueue<>();
        this.toCruz = new ToCruz();
    }

    public Missao() {
        this.cod_missao = "";
        this.versao = 0;
        this.edificio = new Edificio();
        this.alvo = new Alvo();
        this.item_list = new LinearLinkedUnorderedList<>();
        this.item_colected = new LinkedStack<>();
        this.inimigos = new LinearLinkedUnorderedList<>();
        this.inimigos_confronto = new LinearLinkedUnorderedList<>();
        this.inimigos_dead = new LinkedStack<>();
        this.trajeto_to = new LinkedQueue<>();
        this.toCruz = new ToCruz();
    }

    /* Retorna as divisoes vizinhas do Inimigo */
    @Override
    public GraphListaAdjacencia<Divisao> getDivisoesVizinhasInimigo(Divisao divisao) {
        return null;
    }

    /* Fazer com base no sem custos e depois adaptar */
    @Override
    public Iterator<Divisao> getCaminhoMaisCurtoToItemOrAlvo() {
        return null;
    }

    /*
    * Retorna a nova divisao do ToCruz, ver se a parte de seleção da divisao está correta
    *
    * Falta meter agora metodo para sugerir o melhor caminho ao ToCruz para chegar a um item( No final de cada turno, deve ser
    * apresentado ao Tó Cruz o melhor caminho para o alvo e para o kit de recuperação mais próximo.)
    * */
    @Override
    public Divisao getDivisoesVizinhasTo() {
        int op = -1;
        Scanner sc = new Scanner(System.in);
        Iterator<Divisao> itr = this.edificio.getNextDivisoes(toCruz.getDivisao());
        LinkedStack<Divisao> listaDiv = new LinkedStack<>();

        System.out.println("Selecione a divisão para o ToCruz se mover!");

        int i = 0;
        while (itr.hasNext()) {
            String temp = "";
            Divisao divisao = itr.next();
            temp = i++ + " - " + divisao.toString();

            if(divisao.isEntrada_saida()) {
                temp = temp + " (esta divisao é uma saida)";
            } else if(alvo.getDivisao().equals(divisao)) {
                temp = temp + " (divisão onde está o alvo)";
            }

            System.out.println(temp);
            listaDiv.push(divisao);
        }

        do {
            System.out.println("Selecione a divisao que o ToCruz vai se mover");

            try {
                op = sc.nextInt();
            } catch (InputMismatchException ex) {
                System.out.println("Numero inválido!");
                sc.next();
            }
        } while (0 < op || op >= listaDiv.size());

        int y = listaDiv.size() - 1;
        while(y > op) {
            listaDiv.pop();
            y--;
        }

        return listaDiv.peek();
    }

    /*
    * Adiciona um nova divisão ao percurso manual do To Cruz.
    * */
    @Override
    public void addDivisaoTrajetoToCruz(Divisao divisao) {
        this.trajeto_to.enqueue(divisao);
    }

    @Override
    public void modoAutomatico() {

    }

    private boolean ToCruzinExit() {
        boolean exit = false;

        if(toCruz.getDivisao().isEntrada_saida()) {
            exit = true;
        }

        return exit;
    }

    private boolean existeConfronto() {
        boolean existe = false;

        if(!this.inimigos_confronto.isEmpty()) {
            existe = true;
        }

        return existe;
    }

    private void iniciarConfronto() {
        for(Inimigo inimigo: inimigos) {
            if(inimigo.getDivisao().equals(toCruz.getDivisao())) {
                this.inimigos_confronto.addToRear(inimigos.remove(inimigo));
            }
        }
    }

    private void attackToCruz() {
        for(Inimigo inimigo: inimigos_confronto) {
            inimigo.setVida(inimigo.getVida() - toCruz.getPoder());

            if(inimigo.isDead()) {
                this.inimigos_dead.push(this.inimigos_confronto.remove(inimigo));
            }
        }
    }

    private void attackInimigo() {
        for(Inimigo inimigo: inimigos_confronto) {
            inimigo.setVida(inimigo.getVida() - toCruz.getPoder());

            if(inimigo.isDead()) {
                this.inimigos_dead.push(this.inimigos_confronto.remove(inimigo));
            }
        }
    }

    //Testar
    private ItemCura colectItem() {
        ItemCura item_div = null;
        if(!item_list.isEmpty()) {
            boolean find = false;
            Iterator<ItemCura> itr = this.item_list.iterator();

            while(itr.hasNext() && !find) {
                ItemCura item = itr.next();
                if (toCruz.getDivisao().equals(item.getDivisao())) {
                    this.item_colected.push(this.item_list.remove(item));
                    item_div = item;
                    find = true;
                }
            }
        }

        return item_div;
    }

    private void usarItem(ItemCura item) {
        toCruz.usarItem(item);
        this.item_colected.push(this.item_list.remove(item));
    }

    private void guardarItem(ItemCura item) {
        toCruz.guardarKit(item);
        this.item_colected.push(this.item_list.remove(item));
    }

    /*
    * Meter quando ele entra na sala com kit de vida para ele apanhar o kit ou curar com permição do utilizador
    * Falta meter apenas sugerir o caminho mais curto para o ToCruz chegar a um kit de vida ou ao alvo
    *  */
    private void turnoToCruz() {
        Scanner sc = new Scanner(System.in);
        int op = -1;

        if (existeConfronto()) {
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
                } while(op < 1 || op >= 2);
            } else {
                System.out.println("Não é possível o To Cruz corrar-se porque não tem kits na mochila. Por isso ToCruz ataca!");
                op = 1;
            }

            if(op == 1) {
                attackToCruz();
            } else if(op == 2) {
                toCruz.usarKit();
            }
        } else {
            //Mete o ToCruz na no
            //Este getDivisoesVizinhasTo funciona
            this.toCruz.setDivisao(this.getDivisoesVizinhasTo());
            addDivisaoTrajetoToCruz(toCruz.getDivisao());

            if(toCruz.getDivisao().equals(alvo.getDivisao())) {
                System.out.println("To Cruz entrou na sala do alvo");
            }

            iniciarConfronto();

            if(existeConfronto()) {
                attackToCruz();
            }

            //To Cruz entra numa sala com Item de cura
            ItemCura item = colectItem();
            if(item != null) {
                if(item.getType().equals(TypeItemCura.COLETE) || toCruz.mochilaIsFull() && toCruz.getVida() < 100) {
                    usarItem(item);
                }

                if(item.getType().equals(TypeItemCura.KIT_VIDA)) {
                    if(toCruz.getVida() < 100 && !toCruz.mochilaIsFull()) {
                        op = -1;

                        do {
                            System.out.println("Está numa sala com um kit de vida de " + item.getVida_recuperada());
                            System.out.println("0 - Usar Item");
                            System.out.println("1 - Guardar");
                            System.out.println("2 - Deixa-lo na sala");
                            try {
                                op = sc.nextInt();
                            } catch (InputMismatchException ex) {
                                System.out.println("Numero inválido!");
                                sc.next();
                            }
                        } while(op < 0 || op > 2);

                        if (op == 0) {
                            usarItem(item);
                        } else if (op == 1){
                            guardarItem(item);
                        }
                    } else if(!toCruz.mochilaIsFull() && toCruz.getVida() >= 100) {
                        guardarItem(item);
                    }
                }
            }
        }

        //Qunado o TOCruz apava o confilto ou entrar na divisao do alvo e pode coleta-lo
        if(!existeConfronto() && toCruz.getDivisao().equals(alvo.getDivisao())) {
            this.alvo.setAtinigido(true);
            System.out.println("To Cruz está com o alvo, agora só falta sair do edificio com vida");
        }
    }

    private void turnoInimigo() {
        //Meter todo o codigo dos inimigos
        for(Inimigo inimigo: inimigos) {
            //Meter o metodo para o inimigo
        }

        if(existeConfronto()) {
            for(Inimigo inimigo: inimigos_confronto) {
                attackInimigo();
            }
        }

    }

    //Também meter para sugerir uma entrada ao ToCruz (Meter a sala que está mais perto do alvo ou item)
    //Testar (especialmente o set Divisão)
    private void ToCruzEntrarEdificio() {
        int op = -1;
        int i = 0;
        Scanner sc = new Scanner(System.in);

        //Dou um ponto inicial random
        Iterator<Divisao> itr = this.edificio.getPlantaEdificio().iteratorBFS(alvo.getDivisao());
        StackADT<Divisao> entradas = new LinkedStack<>();
        while(itr.hasNext()) {
            Divisao div = itr.next();

            if(div.isEntrada_saida()) {
                System.out.println(i++ + " - " + div.getName());
                entradas.push(div);
            }
        }

        do {
            System.out.println("Introduza onde o ToCruz vai entrar");

            try {
                op = sc.nextInt();
            } catch (InputMismatchException ex) {
                System.out.println("Numero inválido!");
                sc.next();
            }
        } while (op < 0 || op >= i);

        //Trocar se calhar todo que seja deste tipo por uma stack
        int y = entradas.size() - 1;
        while(y > op) {
            entradas.pop();
            y--;
        }

        toCruz.setDivisao(entradas.peek());
        addDivisaoTrajetoToCruz(toCruz.getDivisao());

        iniciarConfronto();
        if(existeConfronto()) {
            attackToCruz();
        }

        if(!existeConfronto() && toCruz.getDivisao().equals(alvo.getDivisao())) {
            this.alvo.setAtinigido(true);
        }
    }

    /**
     * Não esquecer de fazer o codigo para mostrar aqui também a melhor sugestao de caminho de acordo com a divisao que o ToCruz está!!!!!!!!!!!!!!!!!!!!!!!!!!!!
     *
     * Falta apenas turno do inimigo
     * */
    @Override
    public void modoManual() {
        ToCruzEntrarEdificio();

        //Já testei o while funciona
        while (!toCruz.isDead() && (ToCruzinExit() || this.trajeto_to.size() < 1)) {
            turnoInimigo();
            this.versao++;

            turnoToCruz();
            this.versao++;
        }

        relatoriosMissao();
    }

    private void relatoriosMissao() {
        if(!toCruz.isDead() && (alvo.isAtinigido())) {
            System.out.println("Missão realizada com sucesso! ☆*: .｡. o(≧▽≦)o .｡.:*☆");
            System.out.println("Total de vida do ToCruz --> " + toCruz.getVida());
        } else {
            System.out.println("Missão falhada ಥ_ಥ");
        }

        System.out.println("Numero de inimigos mortos: " + inimigos_dead.size());

        if(!inimigos_dead.isEmpty()) {
            System.out.println("Inimigos mortos pelo o To Cruz:");
            System.out.println(inimigos_dead.toString());
        }

        System.out.println("Numero de itensColetados: " + item_colected.size());
        if(!item_colected.isEmpty()) {
            System.out.println("Itens coletados pelo o ToCruz:");
            System.out.println(item_colected.toString());
        }

        if(!toCruz.getMochila().isEmpty()) {
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
                ", alvo=" + alvo.toString() +
                ", item_list=" + item_list.toString() +
                ", item_colected=" + item_colected.toString() +
                ", inimigos=" + inimigos.toString() +
                ", inimigos_dead=" + inimigos_dead.toString() +
                ", inimigos_confronto=" + inimigos_confronto.toString() +
                ", trajeto_to=" + trajeto_to.toString() +
                ", toCruz=" + toCruz.toString() +
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
