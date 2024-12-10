package Missoes;

import Interfaces.MissaoInt;
import Interfaces.OrderedListADT;
import LinkedList.LinearLinkedOrderedList;
import Mapa.Edificio;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Dar deepCopy das divisoes para não ser diretamente modificado o edificio das divisoes
 */
public class Missao implements MissaoInt {
    private static Scanner sc = new Scanner(System.in);

    private static int TOT_VERSOES = 0;

    private String cod_versao;

    private long versao;

    private Edificio edificio;

    private int tot_simulacoes;

    private OrderedListADT<Simulacoes> simulacoes;

    public Missao(String cod_versao, long versao, Edificio edificio) {
        this.cod_versao = cod_versao;
        this.versao = versao;
        this.edificio = edificio;
        this.tot_simulacoes = 0;
        this.simulacoes = new LinearLinkedOrderedList<>();
    }

    public Missao() {
        this.cod_versao = "";
        this.versao = 0;
        this.edificio = new Edificio();
        this.simulacoes = new LinearLinkedOrderedList<>();
    }


/*
*     public Missao(String cod_versao, long versao, Edificio edificio, OrderedListADT<Simulacoes> simulacoes) {
        this.cod_versao = cod_versao;
        this.versao = versao;
        OrderedListADT<Simulacoes> tempSimulacoes = new LinearLinkedOrderedList<>();
        Edificio tempEdifico = new Edificio(edificio.getId(), edificio.getName(), edificio.getPlantaEdificio());

        for(Simulacoes simulacao: simulacoes) {
            Simulacoes tempSimulacao = new Simulacoes();
            simulacoes.add(tempSimulacao);
        }

    }
* */

    public String getCod_versao() {
        return cod_versao;
    }

    public OrderedListADT<Simulacoes> getSimulacoes() {
        return simulacoes;
    }

    public Edificio getEdificio() {
        return edificio;
    }

    @Override
    public void viewSimulacoes() {
        for(Simulacoes simulacao: simulacoes) {
            System.out.println(simulacao);
        }
    }

    /*
    * Testar, em principio funciona
    * */
    @Override
    public void modoManual() {
        int op = 0;
        Simulacoes simula;

        do {

            System.out.println("Começar simulação");
            System.out.println("0 - Sair");
            System.out.println("1 - Jogar");
            System.out.print("Selecione uma opção -->");

            try {
                op = sc.nextInt();
            } catch (InputMismatchException ex) {
                System.out.println("Numero invalido!");
                sc.next();
            }

            if (op == 1) {
                simula = new Simulacoes(tot_simulacoes, new Edificio(this.edificio.getId(), this.edificio.getName(), this.edificio.getPlantaEdificio()));
                simulacoes.add(simula.modojogoManual());
                tot_simulacoes++;
            }

        } while (op != 0);

        for (Simulacoes simulacao : simulacoes) {
            System.out.println(simulacao.toString());
        }

        //Exportar Simulacoes

    }

    /*Fazer xico com base no outro exportar*/
    private void exportarMissao() {

    }

    /*
    *  private void exportarMissao(QueueADT<Divisao> trajeto) {
        QueueADT<QueueADT<Divisao>> trajetoQueue = new LinkedQueue<>();
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
   * */

    @Override
    public void modoAutomatico() {
        Simulacoes simulacaoAutomatica = new Simulacoes(tot_simulacoes, new Edificio(this.edificio.getId(), this.edificio.getName(), this.edificio.getPlantaEdificio()));
        simulacaoAutomatica.modojogoAutomatico();
    }

    @Override
    public void jogoAutomatico() {
        Simulacoes simulacao = new Simulacoes(tot_simulacoes, new Edificio(this.edificio.getId(), this.edificio.getName(), this.edificio.getPlantaEdificio()));
        simulacao.jogoAutomatico();
    }

}
