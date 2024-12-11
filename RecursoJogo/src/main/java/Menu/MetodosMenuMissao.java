package Menu;

import Exportar.ExportarDado;
import Importar.ImportarMapa;
import Missoes.Missao;
import Missoes.Missoes;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Scanner;

public abstract class MetodosMenuMissao {

    private static Scanner sc = new Scanner(System.in);

    private static final String path = "./Jsons/Import/FicheiroMissao.json";

    private static final String path_name_def = "./Jsons/Import/";

    private static final String destino = "./Jsons/Export/";

    public static Missoes importarMissoes() {
        Missoes missoes = new Missoes();
        Missao missao;
        ImportarMapa importarMapa = new ImportarMapa();
        String path_name;
        String name_file;
        Scanner sc = new Scanner(System.in);

        int continuar = -1;
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
                path_name += name_file;
            }

            try {
                missoes.addMissao(importarMapa.gerarMapa(path));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            do {
                System.out.print("Desenha importar mais alguma missao? (Nao:0 / Sim:1) -->");
                try {
                    continuar = sc.nextInt();
                } catch (InputMismatchException ex) {
                    System.out.println("Numero invalido!");
                    sc.next();
                }
            } while (continuar < 0 && continuar > 1);
        } while (continuar != 0);

        return missoes;
    }

    public static void RealizarMissoes(Missoes missoes) {
        if (missoes.getContMissoes() > 1) {
            int recomecar = -1;
            do {
                executarVariasMissoes(missoes);

                do {
                    System.out.println("Deseja jogar outra missao? (Nao: 0/ Sim: 1)");

                    try {
                        recomecar = sc.nextInt();
                    } catch (InputMismatchException ex) {
                        System.out.println("Numero invalido!");
                        sc.next();
                    }
                } while (recomecar < 0 && recomecar > 1);
            } while (recomecar != 0);
        } else {
            SelecionarModoJogo(missoes.getListaMissao().first());
        }

        exportarMissoes(missoes);
    }

    private static void exportarMissoes(Missoes missoes) {
        Iterator<Missao> iterator = missoes.getListaMissao().iterator();
        while (iterator.hasNext()) {
            Missao missao = iterator.next();
            ExportarDado exportar = new ExportarDado(missao);
            exportar.exportarTrajetos(destino);
        }
    }
    private static void SelecionarModoJogo(Missao missao) {
        int op_modo;
        do {
            op_modo = -1;
            do {
                System.out.println("0 - Sair");
                System.out.println("1 - Modo Manual");
                System.out.println("2 - Modo Automatico");
                System.out.println("3 - Jogo Automatico");
                System.out.print("Selecione o modo de jogo que pretende jogar -->");
                try {
                    op_modo = sc.nextInt();
                } catch (InputMismatchException ex) {
                    System.out.println("Numero invalido!");
                    sc.next();
                }

            } while (op_modo < 0 && op_modo > 3);

            switch (op_modo) {
                case 1: {
                    missao.modoManual();
                    break;
                }
                case 2: {
                    missao.modoAutomatico();
                    break;
                }
                case 3: {
                    missao.jogoAutomatico();
                    break;
                }
                default: {
                    break;
                }
            }
        } while (op_modo != 0);
    }

    private static void executarVariasMissoes(Missoes missoes) {
        int op_missao = -1;
        Missao[] arrayMissao = new Missao[missoes.getContMissoes()];

        do {
            int i = 0;
            System.out.println("0 - Sair");
            for (Missao mis : missoes.getListaMissao()) {
                arrayMissao[i++] = mis;
                System.out.println(i + " - Missao" + mis.getcod_missao() + " versão nº " + mis.getVersao());
            }

            op_missao = -1;
            do {
                System.out.print("Selecione a opção da missão que quer jogar -->");

                try {
                    op_missao = sc.nextInt();
                } catch (InputMismatchException ex) {
                    System.out.println("Numero invalido!");
                    sc.next();
                }
            } while (op_missao < 0 && op_missao > i - 1);

            if (op_missao > 0) {
                SelecionarModoJogo(arrayMissao[op_missao - 1]);
            }
        } while (op_missao != 0);
    }
}
