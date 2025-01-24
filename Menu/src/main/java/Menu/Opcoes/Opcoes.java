package Menu.Opcoes;

import java.util.Scanner;

public abstract class Opcoes {
    /**
     * Scanner para ler as entradas do jogador.
     */
    protected static Scanner sc;

    public Opcoes() {
        sc = new Scanner(System.in);
    }

}
