package Missoes;

import Interfaces.MissoesInt;
import Interfaces.OrderedListADT;
import LinkedList.LinearLinkedOrderedList;
import Mapa.Edificio;

import java.util.Scanner;

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
        this.edificio = new Edificio();
    }

    @Override
    public void modoManual() {
        Scanner sc = new Scanner(System.in);
        int op = 0;
        Simulacoes simula = new Simulacoes();

        do {
            System.out.println("0 - Sair");
            System.out.println("1 - Jogar");

            if(op == 1) {
                simulacoes.add(simula.modoManual(edificio));
            }
        } while(op != 0);

        for(Simulacoes simulacao: simulacoes) {
            System.out.println(simulacao.toString());
        }

        //Exportar Simulacoes
    }

    @Override
    public void modoAutomatico() {
        //Chamar o metodo Automatico da simulacao
    }

    @Override
    public void jogoAutomatico() {
        //Vai ser igual ao manual
    }

}
