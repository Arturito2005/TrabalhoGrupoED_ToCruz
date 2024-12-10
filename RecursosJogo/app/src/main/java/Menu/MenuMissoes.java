package Menu;

import Importar.ImportarMapa;
import Missoes.Missao;
import Missoes.Missoes;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * */
public class MenuMissoes {
    private static final String path = "./Jsons/Import/FicheiroMissao.json";

    private static final String path_name_def= "./Jsons/Import/";
    public static void main(String[] args) {
        Missoes missoes = new Missoes();
        Missao missao;
        ImportarMapa importarMapa = new ImportarMapa();
        String path_name;
        String name_file;
        Scanner sc = new Scanner(System.in);

        String continuar = "";
        do {
            missao = new Missao();
            path_name = path_name_def;
            name_file = "";
            System.out.print("Introduza o nome do ficheiro a importar -->");

            try {
                name_file = sc.nextLine();
            } catch (InputMismatchException ex) {
                System.out.println("Numero invalido!");
                sc.next();
            }

            if (!name_file.equals("")) {
                path_name+= name_file;
            }

            try {
                missoes.addMissao(importarMapa.gerarMapa(path));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            System.out.print("Desenha importar mais alguma missao? (Sim:S / Não:N) -->");
            try {
                continuar = sc.nextLine();
            } catch (InputMismatchException ex) {
                System.out.println("Numero invalido!");
                sc.next();
            }

        } while (!continuar.equals("N") && !continuar.equals("n"));

        int i = 0;
        Missao[] arrayMissao = new Missao[missoes.getContMissoes()];
        for (Missao mis : missoes.getListaMissao()) {
            System.out.println("Missao nº " + i + " de nome " + mis.getCod_versao());
            arrayMissao[i++] = mis;
        }

        int op_missao = -1;
        do {
            System.out.print("Selecione a opção da missão que quer jogar -->");

            try {
                op_missao = sc.nextInt();
            } catch (InputMismatchException ex) {
                System.out.println("Numero invalido!");
                sc.next();
            }
        } while (op_missao < 0 && op_missao > i - 1);


        System.out.println("0 - Cancelar");
        System.out.println("1 - Modo Manual");
        System.out.println("2 - Modo Automatico");
        System.out.println("3 - Jogo Automatico");
        int op_modo = -1;

        do {
            System.out.print("Selecione o modo de jogo que pretende jogar -->");
            try {
                op_modo = sc.nextInt();
            } catch (InputMismatchException ex) {
                System.out.println("Numero invalido!");
                sc.next();
            }

        } while (op_modo < 0 && op_modo > i - 1);

        Missao missao_sel = arrayMissao[op_missao];
        switch (op_modo) {
            case 1: {
                missao_sel.modoManual();
                break;
            } case 2: {
                missao_sel.modoAutomatico();
                break;
            } case 3: {
                missao_sel.jogoAutomatico();
                break;
            } default: {
                break;
            }
        }
    }
}
