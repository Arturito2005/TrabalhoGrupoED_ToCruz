package Menu;

import Menu.Opcoes.OpcoesFicheiros;
import Menu.Opcoes.OpcoesMissoes;

/**
 * Classe responsável pelo menu de missões do jogo.
 * Contém o método principal que importa e realiza as missões disponíveis.
 *
 * @author Artur Pinto
 * Nº mecanográfico: 8230138
 * @author Francisco Oliveira
 * Nº mecanografico: 8230148
 * @version 1.0
 */
public class MenuMissoes {

    /**
     * Construtor padrão da classe MenuMissoes.
     * Inicializa a classe para gestão o menu de missões do jogo.
     */
    public MenuMissoes() {
    }

    /**
     * Método principal que executa o menu de missões.
     * Importa as missões e executa as missões.
     *
     * @param args Argumentos de linha de comando (não utilizados neste caso).
     */
    public static void main(String[] args) {
        OpcoesFicheiros opFile = new OpcoesFicheiros();
        OpcoesMissoes opMiss = new OpcoesMissoes();

        opMiss.RealizarMissoes(opFile.importarMissoes());
    }
}
