package Missoes;

import Interfaces.MissoesInt;
import Interfaces.OrderedListADT;
import Interfaces.UnorderedListADT;
import LinkedList.LinearLinkedOrderedList;
import LinkedList.LinearLinkedUnorderedList;
import Mapa.Divisao;
import Mapa.Edificio;
import Personagens.ToCruz;
import java.util.Iterator;
import java.util.Scanner;

/**
 * Dar deepCopy das divisoes para não ser diretamente modificado o edificio das divisoes
 * */
public class Missao implements MissoesInt {

    private static int TOT_VERSOES = 0;

    private String cod_versao;

    private long versao;

    private OrderedListADT<Simulacoes> simulacoes;

    private Edificio edificio;

    public Missao(String cod_versao, long versao, Edificio edificio) {
        this.cod_versao = cod_versao;
        this.versao = versao;
        this.simulacoes = new LinearLinkedOrderedList<>();
        this.edificio = edificio;
    }

    public Missao() {
        this.cod_versao = "";
        this.versao = 0;
        this.simulacoes = new LinearLinkedOrderedList<>();
        this.edificio = new Edificio();
    }

    /*
    Esta branch pode ter erros na simulação de eu estar num metodo em vez de mandar o edificio fazer "new Edificio"
    e de não ter o compareTo na Missao
    * */
    @Override
    public void modoManual() {
        Scanner sc = new Scanner(System.in);
        int op = 0;
        Simulacoes simula;

        do {
            simula = new Simulacoes(versao, edificio);
            System.out.println("0 - Sair");
            System.out.println("1 - Jogar");

            if(op == 1) {
                simulacoes.add(simula.modoManual());
            }
            versao++;
        } while(op != 0);

        for(Simulacoes simulacao: simulacoes) {
            System.out.println(simulacao.toString());
        }

        //Exportar Simulacoes
    }

    @Override
    public void modoAutomatico() {
        Iterator<Divisao> itr = edificio.getPlantaEdificio().iterator();
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
            distance = edificio.getShortestPath(div_entr, div_alvo);

            if(distance == 0) {
                best_distance = distance;
                num_arestas = edificio.getShortestPathNumArestas(div_entr, div_alvo);

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
            Iterator<Divisao> itr2 = edificio.shortesPathIt(best_div, div_alvo);
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
                distance = edificio.getShortestPath(div_entr, div_alvo);

                if(distance == 0) {
                    best_distance = distance;
                    num_arestas = edificio.getShortestPathNumArestas(div_entr, div_alvo);

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
                Iterator<Divisao> itr3 = edificio.shortesPathIt(div_alvo, best_div);
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

    @Override
    public void jogoAutomatico() {
        Simulacoes simulacao = new Simulacoes(versao, edificio);

        simulacao.jogoAutomatico();
    }

}
