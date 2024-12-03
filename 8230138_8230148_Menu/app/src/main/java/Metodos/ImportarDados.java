package Metodos;

import java.util.InputMismatchException;
import java.util.Scanner;

/*
* Este classe armazena o metodo para importar o Mapa do Jogo
* */
public class ImportarDados {

    public void importarDadod() {
        Scanner sc = new Scanner(System.in);

        String nome_file = "";

        //meter if para ver se tem ficheiros a importar
        //do {
            System.out.println("Importar Dados!");
            System.out.println("Introduza o nome do ficheiro:");


            try {
                nome_file = sc.nextLine();
            } catch (InputMismatchException ex) {
                System.out.println("Nome de ficheiro Inválido!");
                sc.next();
            }
        //} while(!lerficherio(nome_file));
    }
}
