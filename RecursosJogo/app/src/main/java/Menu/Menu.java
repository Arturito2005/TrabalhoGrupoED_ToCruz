package Menu;

import Exceptions.InvalidOptionException;
import Importar.ImportarMapa;
import Missoes.Missao;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {
    private static final String path = "./Jsons/Import/FicheiroMissao.json";

    public static void main(String[] args) throws IOException {
        Missao missao = new Missao();
        ImportarMapa importarMapa = new ImportarMapa();
        String path_name = "./Jsons/Import/";
        String name_file = "";
        Scanner sc = new Scanner(System.in);

        do {
            System.out.print("Introduza o nome do ficheiro a importar -->");

            try {
                name_file = sc.nextLine();
            } catch (InputMismatchException ex) {
                System.out.println("Numero invalido!");
                sc.next();
            }
        } while (path_name.equals(""));

        path_name += name_file;
        missao = importarMapa.gerarMapa(path);
        int op = -1;

        /*
        * Pode ser necessario trocar a ordem, do Modo com o Jogo, porque o Jogo é o antigo modo automatico
        * */
        do {
            System.out.println("1 - Modo Manual");
            System.out.println("2 - Modo automático");
            System.out.println("3 - Jogo automático");
            System.out.println("Introduza o modo do jogo -->");
            try {
                op = sc.nextInt();
            } catch (InputMismatchException ex) {
                System.out.println("Numero inválido!");
                sc.next();
            }
        } while (op < 0 || op > 3);

        switch (op) {
            case 1: {
                missao.modoManual();
                break;
            } case 2: {
                missao.modoAutomatico();
                break;
            } case 3: {
                missao.jogoAutomatico();
                break;
            } default: {
                throw new InvalidOptionException("Opção selecionada é invalida");
            }
        }
    }
}
