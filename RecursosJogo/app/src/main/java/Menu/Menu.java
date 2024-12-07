package Menu;

import Importar.ImportarMapa;
import Missoes.Missao;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {
    private static final String path = "C:/Users/artur/OneDrive/Desktop/universidade/2_ano/ED/ED_AC_8230138_8230148/RecursosJogo/Jsons/Import/FicheiroMissao.json";

    public static void main(String[] args) throws IOException {
        Missao missao = new Missao();
        //ImportarDados imp = new ImportarDados();
        ImportarMapa importarMapa = new ImportarMapa();
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
        } while (op < 0 || op >= 2);

        if (op == 1) {
            //Inicializa o jogo no modo Manual
            missao.modoManual();
        } else {
            //Inicialzia o jogo no modo Automático
            //missao.modoAutomatico();
        }
    }
}
