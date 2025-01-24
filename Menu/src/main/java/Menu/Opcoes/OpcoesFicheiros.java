package Menu.Opcoes;

import Exportar.ExportarDado;
import Importar.ImportarMapa;
import Interfaces.Opcoes.OpFicheirosInterface;
import Interfaces.UnorderedListADT;
import LinkedList.LinearLinkedUnorderedList;
import Missoes.Missoes;
import Missoes.Missao;

import java.io.IOException;
import java.util.InputMismatchException;

public class OpcoesFicheiros extends Opcoes implements OpFicheirosInterface {

    /**
     * Caminho padrão para o diretório de importação de ficheiros.
     */
    private static final String path_name_def = "./Jsons/Import/";

    /**
     * Caminho do diretório para exportar dados.
     */
    private static final String destino = "./Jsons/Export/";

    public OpcoesFicheiros() {
        super();
    }
    /**
     * Método para importar missões de um ficheiro JSON.
     * O jogador é convidado a fornecer o nome do ficheiro e, em seguida, o mapa da missão é importado.
     * Caso o ficheiro seja lido com sucesso, as missões são adicionadas à lista de missões.
     *
     * @return Objeto Missoes que contém todas as missões importadas.
     */
    @Override
    public Missoes importarMissoes() {
        Missoes missoes = new Missoes();
        ImportarMapa importarMapa = new ImportarMapa();
        String name_file;

        int continuar = -1;
        do {
            String path_name = path_name_def;
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
                    System.out.print("Deseja importar mais alguma missao? (Nao: 0 / Sim: 1) -->");
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
     * Método que gere a exportação das missões. Caso existam várias missões, o jogador pode escolher
     * exportar várias missões em sequência ou uma única missão.
     *
     * @param missoes Objeto que contém as missões a serem exportadas.
     */
    @Override
    public void exportarMissoes(Missoes missoes) {
        ExportarDado exportar = new ExportarDado();

        Missao missaoAnterior = missoes.getListaMissao().first();
        UnorderedListADT<Missao> listaVersoes = new LinearLinkedUnorderedList<>();
        for (Missao missao : missoes.getListaMissao()) {
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
}
