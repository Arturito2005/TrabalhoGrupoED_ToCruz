package Metodos;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ExportarDados {

    //Meter para receber como parametro os dados
    public void exportarMapa() {
        Scanner sc = new Scanner(System.in);
        String nome_file = "";

        //do {
        System.out.println("Exportar Dados!");
        System.out.println("Introduza o nome do ficheiro:");
        nome_file = sc.nextLine();

        try {
            nome_file = sc.nextLine();
        } catch (InputMismatchException ex) {
            System.out.println("Nome de ficheiro Inválido!");
            sc.next();
        }
        //} while(!exportarFicheiro(nome_file));
    }
}
