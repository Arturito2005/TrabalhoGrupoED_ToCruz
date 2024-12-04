package Missoes;

import Interfaces.ModoManualADT;
import Interfaces.StackADT;
import Items.ItemCura;
import Items.TypeItemCura;
import Mapa.Divisao;
import Stacks.LinkedStack;

import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Scanner;

public class ModoManual extends ModosJogo implements ModoManualADT {

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
                } while (op < 1 || op >= 2);
            } else {
                System.out.println("Não é possível o To Cruz corrar-se porque não tem kits na mochila. Por isso ToCruz ataca!");
                op = 1;
            }

            if (op == 1) {
                attackToCruz();
            } else if (op == 2) {
                toCruz.usarKit();
            }
        } else {
            //Mete o ToCruz na no
            //Este getDivisoesVizinhasTo funciona
            this.toCruz.setDivisao(this.getDivisoesVizinhasTo());
            addDivisaoTrajetoToCruz(toCruz.getDivisao());

            if (toCruz.getDivisao().equals(alvo.getDivisao())) {
                System.out.println("To Cruz entrou na sala do alvo");
            }

            iniciarConfronto();

            if (existeConfronto()) {
                attackToCruz();
            }

            //To Cruz entra numa sala com Item de cura
            ItemCura item = colectItem();
            if (item != null) {
                if (item.getType().equals(TypeItemCura.COLETE) || toCruz.mochilaIsFull() && toCruz.getVida() < 100) {
                    usarItem(item);
                }

                if (item.getType().equals(TypeItemCura.KIT_VIDA)) {
                    if (toCruz.getVida() < 100 && !toCruz.mochilaIsFull()) {
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
                        } while (op < 0 || op > 2);

                        if (op == 0) {
                            usarItem(item);
                        } else if (op == 1) {
                            guardarItem(item);
                        }
                    } else if (!toCruz.mochilaIsFull() && toCruz.getVida() >= 100) {
                        guardarItem(item);
                    }
                }
            }
        }

        //Qunado o TOCruz apava o confilto ou entrar na divisao do alvo e pode coleta-lo
        if (!existeConfronto() && toCruz.getDivisao().equals(alvo.getDivisao())) {
            this.alvo.setAtinigido(true);
            System.out.println("To Cruz está com o alvo, agora só falta sair do edificio com vida");
        }
    }

    //Também meter para sugerir uma entrada ao ToCruz (Meter a sala que está mais perto do alvo ou item)
    private void ToCruzEntrarEdificio() {
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
    }

    @Override
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
}
