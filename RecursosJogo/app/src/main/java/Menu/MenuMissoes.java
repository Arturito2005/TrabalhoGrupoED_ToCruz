package Menu;

import Missoes.Missoes;
import java.util.Scanner;

/**
 *
 * */
public class MenuMissoes {
    private static Scanner sc = new Scanner(System.in);

    private static final String path = "./Jsons/Import/FicheiroMissao.json";

    private static final String path_name_def= "./Jsons/Import/";

    public static void main(String[] args) {
        MetodosMenuMissao metodos = new MetodosMenuMissao();

        Missoes missoes = metodos.importarMissoes();
        metodos.RealizarMissoes(missoes);
        //missoes.exportarMissoes();
    }
}
