package Menu;


import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class MetodosMenuMissao {

    private static Scanner sc = new Scanner(System.in);

    private static final String path = "./Jsons/Import/FicheiroMissao.json";

    private static final String path_name_def = "./Jsons/Import/";

    public Missoes importarMissoes() {
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
                path_name += name_file;
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

        return missoes;
    }

    public void RealizarMissoes(Missoes missoes) {
        if (missoes.getContMissoes() > 1) {
            executarVariasMissoes(missoes);
        } else {
            SelecionarModoJogo(missoes.getListaMissao().first());
        }
    }

    /*
     * Ver a melhor forma de exportar as tantas receber as missoes e só exportar as missoes com
     * o cont de simulações maior que 0
     * */
    private void ExportarDadosMissao(Missoes missoes) {
        /*
         * Chamar metodo para exportar dados
         * */
    }

    private void SelecionarModoJogo(Missao missao) {
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

    private void executarVariasMissoes(Missoes missoes) {
        int op_missao = -1;
        int i = 0;
        Missao[] arrayMissao = new Missao[missoes.getContMissoes()];

        do {
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
                SelecionarModoJogo(arrayMissao[op_missao]);
            }
        } while (op_missao != 0);

    }
}
