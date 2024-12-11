package Menu;

import Exportar.ExportarDado;
import Importar.ImportarMapa;
import Interfaces.UnorderedListADT;
import LinkedList.LinearLinkedUnorderedList;
import Missoes.Missao;
import Missoes.Missoes;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Classe responsável por gestão o menu de missões do jogo.
 * Contém métodos para importar, realizar e exportar missões, além de permitir a seleção do modo de jogo.
 *
 * @author Artur Pinto
 * Nº mecanográfico: 8230138
 * @author Francisco Oliveria
 * Nº mecanografico: 8230148
 * @version 1.0
 */
public abstract class MetodosMenuMissao {

    /**
     * Construtor padrão da classe MetodosMenuMissao.
     * Inicializa a classe para gestão o menu de missões, sem parâmetros adicionais.
     */
    public MetodosMenuMissao() {
    }
    /**
     * Scanner para ler entradas do usuário.
     */
    private static Scanner sc = new Scanner(System.in);

    /**
     * Caminho padrão para o diretório de importação de arquivos.
     */
    private static final String path_name_def = "./Jsons/Import/";

    /**
     * Caminho do diretório para exportar dados.
     */
    private static final String destino = "./Jsons/Export/";

    /**
     * Método para importar missões de um arquivo JSON.
     * O usuário é solicitado a fornecer o nome do arquivo e, em seguida, o mapa da missão é importado.
     * Caso o arquivo seja lido com sucesso, as missões são adicionadas à lista de missões.
     *
     * @return Objeto Missoes contendo todas as missões importadas.
     */
    public static Missoes importarMissoes() {
        Missoes missoes = new Missoes();
        ImportarMapa importarMapa = new ImportarMapa();
        String name_file;
        Scanner sc = new Scanner(System.in);

        int continuar = -1;
        do {
            String path_name = path_name_def;
            sc = new Scanner(System.in);
            name_file = "";
            System.out.print("Introduza o nome do ficheiro a importar -->");

            try {
                name_file = sc.nextLine();
            } catch (InputMismatchException ex) {
                System.out.println("Numero invalido!");
                sc.next();
            }

            if (!name_file.equals("")) {
                path_name += name_file + ".json";
            }

                try {
                    missoes.addMissao(importarMapa.gerarMapa(path_name));
                    do {
                        System.out.print("Desenha importar mais alguma missao? (Nao:0 / Sim:1) -->");
                        try {
                            continuar = sc.nextInt();
                        } catch (InputMismatchException ex) {
                            System.out.println("Numero invalido!");
                            sc.next();
                        }
                    } while (continuar < 0 || continuar > 1);
                } catch (NullPointerException ex) {
                    System.out.println(ex.getMessage());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
        } while (continuar != 0);

        return missoes;
    }

    /**
     * Método que gerencia a execução das missões. Caso existam várias missões, o usuário pode escolher
     * jogar várias missões em sequência ou uma única missão.
     *
     * @param missoes Objeto contendo as missões a serem realizadas.
     */
    private static void exportarMissoes(Missoes missoes) {
        ExportarDado exportar = new ExportarDado();

        Missao missaoAnterior = missoes.getListaMissao().first();
        UnorderedListADT<Missao> listaVersoes = new LinearLinkedUnorderedList<>();
        for (Missao missao: missoes.getListaMissao()) {
            if (missao.getcod_missao().equals(missaoAnterior.getcod_missao())) {
                listaVersoes.addToRear(missao);
            } else {
                exportar.exportarVersoes(destino + missaoAnterior.getcod_missao().replaceAll(" ", "") + ".json", listaVersoes);
                listaVersoes = new LinearLinkedUnorderedList<>();
                missaoAnterior = missao;
                listaVersoes.addToRear(missaoAnterior);
            }
        }

        exportar.exportarVersoes(destino + missaoAnterior.getcod_missao().replaceAll(" ", "") + ".json", listaVersoes);
    }

    /**
     * Método que gerencia a execução das missões. Caso existam várias missões, o usuário pode escolher
     * jogar várias missões em sequência ou uma única missão.
     *
     * @param missoes Objeto contendo as missões a serem realizadas.
     */
    public static void RealizarMissoes(Missoes missoes) {
        if (missoes.getContMissoes() > 1) {
            int recomecar = -1;
            do {
                executarVariasMissoes(missoes);

                do {
                    System.out.print("Deseja jogar outra missao? (Nao: 0/ Sim: 1) -->");

                    try {
                        recomecar = sc.nextInt();
                    } catch (InputMismatchException ex) {
                        System.out.println("Numero invalido!");
                        sc.next();
                    }
                } while (recomecar < 0 || recomecar > 1);
            } while (recomecar != 0);
        } else {
            SelecionarModoJogo(missoes.getListaMissao().first());
        }

        exportarMissoes(missoes);
    }

    /**
     * Método que permite ao jogador selecionar o modo de jogo para uma missão específica.
     * O jogador pode escolher entre modo manual, automático ou automático completo.
     *
     * @param missao A missão que será jogada no modo selecionado.
     */
    private static void SelecionarModoJogo(Missao missao) {
        int op_modo;
        do {
            op_modo = -1;
            do {
                System.out.println("0 - Voltar");
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

            } while (op_modo < 0 || op_modo > 3);

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

    /**
     * Método responsável por gestão a execução de várias missões. O jogador pode escolher
     * qual missão deseja jogar a partir de uma lista de missões disponíveis.
     *
     * @param missoes Objeto contendo todas as missões disponíveis.
     */
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
            } while (op_missao < 0 || op_missao > i);

            if (op_missao > 0) {
                SelecionarModoJogo(arrayMissao[op_missao - 1]);
            }
        } while (op_missao != 0);
    }
}
