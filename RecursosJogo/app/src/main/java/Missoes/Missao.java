package Missoes;

import Graph.GraphListaAdjacencia;
import Interfaces.MissaoADT;
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

import java.util.*;

/**
 * Falta o turno do inimigo
 * Falta o modo automatico
 * Falta sugerir o caminho mais curto para o item ou alvo para o To Cruz
 * Trocar o Grafo sem custo para um grafo com custo
 * <p>
 * Ver se realmente vão ser preciso getters, penso que só será necessário os metodos da MissaoADT.
 */
public class Missao implements MissaoADT {

    private String cod_missao;

    protected long versao;

    protected Edificio edificio;

    protected Alvo alvo;

    protected LinearLinkedUnorderedList<ItemCura> item_list;

    protected LinkedStack<ItemCura> item_colected;

    protected LinearLinkedUnorderedList<Inimigo> inimigos;

    protected LinkedStack<Inimigo> inimigos_dead;

    protected LinearLinkedUnorderedList<Inimigo> inimigos_confronto;

    protected LinkedQueue<Divisao> trajeto_to;

    protected ToCruz toCruz;

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

    public Edificio getEdificio() {
        return this.edificio;
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

            if (divisao.isEntrada_saida()) {
                temp = temp + " (esta divisao é uma saida)";
            } else if (alvo.getDivisao().equals(divisao)) {
                temp = temp + " (divisão onde está o alvo)";
            }

            System.out.println(temp);
            listaDiv.push(divisao);
        }

        //Dá erro este do while
        do {
            System.out.println("Selecione a divisao que o ToCruz vai se mover");

            try {
                op = sc.nextInt();
            } catch (InputMismatchException ex) {
                System.out.println("Numero inválido!");
                sc.next();
            }
        } while (op < 0 || op > i);

        int y = listaDiv.size() - 1;
        while (y > op) {
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



    //Testar (especialmente o set Divisão)
    /*
    *
    * private void ToCruzEntrarEdificio() {
        int op = -1;
        int i = 0;
        Scanner sc = new Scanner(System.in);

        //Dou um ponto inicial random
        Iterator<Divisao> itr = this.edificio.getPlantaEdificio().iteratorBFS(alvo.getDivisao());
        StackADT<Divisao> entradas = new LinkedStack<>();
        while (itr.hasNext()) {
            Divisao div = itr.next();

            if (div.isEntrada_saida()) {
                System.out.println(i++ + " - " + div.getName());
                entradas.push(div);
            }
        }

        do {
            System.out.println("Introduza onde o ToCruz vai entrar:");

            try {
                op = sc.nextInt();
            } catch (InputMismatchException ex) {
                System.out.println("Numero inválido!");
                sc.next();
            }
        } while (op < 0 || op >= i);

        //Trocar se calhar todo que seja deste tipo por uma stack
        int y = entradas.size() - 1;
        while (y > op) {
            entradas.pop();
            y--;
        }

        toCruz.setDivisao(entradas.peek());
        addDivisaoTrajetoToCruz(toCruz.getDivisao());

        iniciarConfronto();
        if (existeConfronto()) {
            attackToCruz();
        }

        if (!existeConfronto() && toCruz.getDivisao().equals(alvo.getDivisao())) {
            this.alvo.setAtinigido(true);
        }
    }*/

    /**
     * Só falta meter para sugeir ao ToCruz o caminho mais curto para chegar a Item ou Alvo
     */
    /*
    * @Override
    public void modoManual() {
        ToCruzEntrarEdificio();

        while (!toCruz.isDead() || (!ToCruzinExit() && this.trajeto_to.size() <= 1)) {
            turnoInimigo();
            this.versao++;

            turnoToCruz();
            this.versao++;
        }

        relatoriosMissao();
    }
    * */

    //Tirar To String da Stack


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
