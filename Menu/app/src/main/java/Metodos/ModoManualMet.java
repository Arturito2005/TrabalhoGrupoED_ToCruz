/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Metodos;

import java.util.Scanner;

/**
 *
 * @author artur
 */
public class ModoManualMet {

    private Scanner sc = new Scanner(System.in);

    /*Turno ToCruz*/
    public void menuManual() {
        /*
        * Meter if para remover recursividade
        * if(ToCruz.getDivisao.getSaidaEntrada == true && Alvo.getAtingido) {
        *   Terminar recursividade
        * } else {
        *   Meter aqui o codigo abaixo
        * }
        *
        * */


        //if(!ToCruz.getConfronto()) {
        int op = -1;

        /*
        * Antes de selecionar alguma coisa é mostrado o melhor caminho ao utilizador
        * */
        //int kits = Mochila.contains()
        do {
            System.out.println("1- Andar");

            //if(kits > 0) {
                System.out.println("2- Usar Mochila");
            //} else { System.out.println("ToCruz não tem como se curar)}
        } while(op < 1 || op > 2 /*|| (op ==2 && kits == 0*/);

        //Meter também que nesta op == 1 que o toCruz não está em batalha (Recursos)
        if(op == 1) {
            selecionarDivisao();
            //Menu para selecionar Divisao
        } else {

            //Opção para curar(Do rescurso dos jogo)
        }

        menuManual();
        //} else {
            /*Neste void ainda por criar ele vai ter a opção de atacar ou curar*/
            //void combate
        //}
    }

    private void selecionarDivisao() {
        int op = -1;
        int i = 0;

        /*
        *         do {
            System.out.println(i + " " + Divisao.getName);
            i++;
        } while(i < contDivisao);
        * */

        do {
            System.out.println("Selecione uma opção -->");
            //Meter T
            op = sc.nextInt();
        } while(op < 0 || op > i);
    }

    private void apanharItem() {
        //if(inimigo.divisao == ToCruz.divisao) {
            //combate()
        //}

        //ToCruz.apnharItem();
    }

    private void combate() {
        /*
        * Queue turno = new Queue
        * */

    }
}