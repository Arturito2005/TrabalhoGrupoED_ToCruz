package menu;

import java.util.Scanner;

public class ModoJogo {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int op = -1;

        //Inicializa ciclo do while
        System.out.println("Introduza o modo do jogo");
        System.out.println("1- Modo Manual");
        System.out.println("2- Modo automárico");

        op = sc.nextInt();
        //Só termina o ciclo quando selecionar opção válida

        if (op == 1) {
            //Inicializa o jogo no modo Manual
        } else if (op == 2) {
            //Inicialzia o jogo no modo Automático
        }
    }

}
