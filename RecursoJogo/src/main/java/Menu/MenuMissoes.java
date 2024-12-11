package Menu;

import Missoes.Missoes;

import java.util.Scanner;

/**
 * Classe responsável pelo menu de missões do jogo.
 * Contém o método principal que importa e realiza as missões disponíveis.
 */
public class MenuMissoes {

    /**
     * Método principal que executa o menu de missões.
     * Importa as missões e executa as missões.
     *
     * @param args Argumentos de linha de comando (não utilizados neste caso).
     */
    public static void main(String[] args) {
        MetodosMenuMissao metodos = new MetodosMenuMissao();

        Missoes missoes = metodos.importarMissoes();
        metodos.RealizarMissoes(missoes);
    }
}
