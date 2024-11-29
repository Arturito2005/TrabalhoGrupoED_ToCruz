package menu;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ModoJogo {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int op = -1;

        //Inicializa ciclo do while
        do {
            System.out.println("Introduza o modo do jogo");
            System.out.println("1- Modo Manual");
            System.out.println("2- Modo automárico");

            try {
                op = sc.nextInt();
            } catch (InputMismatchException ex) {
                System.out.println("Numero inválido!");
                sc.next();
            }
        } while(op < 0 || op > 2);

        if (op == 1) {
            //Inicializa o jogo no modo Manual
        } else {
            //Inicialzia o jogo no modo Automático
        }
    }

}
