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
        //ImportarDados imp = new ImportarDados();
        ImportarMapa importarMapa = new ImportarMapa();
        String path_default = "./Jsons/Import/";
        missao = importarMapa.gerarMapa(path);

        Scanner sc = new Scanner(System.in);
        int op = -1;

        do {
            System.out.println("1- Modo Manual");
            System.out.println("2- Modo automárico");
            System.out.println("Introduza o modo do jogo -->");
            try {
                op = sc.nextInt();
            } catch (InputMismatchException ex) {
                System.out.println("Numero inválido!");
                sc.next();
            }
        } while (op < 0 || op > 2);

        switch (op) {
            case 1: {
                missao.modoManual();
                break;
            } case 2: {
                missao.modoAutomatico();
                break;
            } default: {
                throw new InvalidOptionException("Opção selecionada é invalida");
            }
        }
    }
}
