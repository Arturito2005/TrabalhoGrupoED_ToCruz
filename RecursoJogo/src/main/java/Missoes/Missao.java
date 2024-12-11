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

    private String cod_missao;

    private long versao;

    private Edificio edificio;

    private int tot_simulacoes;

    private OrderedListADT<Simulacoes> simulacoes;

    public Missao(String cod_missao, long versao, Edificio edificio) {
        this.cod_missao = cod_missao;
        this.versao = versao;
        this.edificio = edificio;
        this.tot_simulacoes = 0;
        this.simulacoes = new LinearLinkedOrderedList<>();
    }

    public Missao() {
        this.cod_missao = "";
        this.versao = 0;
        this.edificio = new Edificio();
        this.simulacoes = new LinearLinkedOrderedList<>();
    }

    public String getcod_missao() {
        return cod_missao;
    }

    public long getVersao() {
        return versao;
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
    }

    /*Fazer xico com base no outro exportar*/
    private void exportarMissao() {

    }

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
