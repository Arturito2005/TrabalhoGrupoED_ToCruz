package Menu;

import Jogo.Simulacao;
import Mapa.Edificio;

import java.util.InputMismatchException;
import java.util.Scanner;

public class GameModes {
    private static Scanner sc;

    public GameModes() {
        sc = new Scanner(System.in);
    }

    /**
     * Criar na mesma a simulação, criar a classe ModoCaminhoAutomatico, que é ela que terá todos os metodos
     * para este modo de jogo
     * */
    @Override
    public void jogoAutomatico() {
        Simulacao simulacao = new Simulacao(tot_simulacoes, new Edificio(this.edificio.getId(), this.edificio.getName(), this.edificio.getPlantaEdificio()));
        simulacao.jogoAutomatico();
    }

    /**
     * Executa o modo manual da missão, permitindo ao utilizador realizar simulações interativas.
     */
    @Override
    public void modoManual() {
        int op = 0;
        Simulacao simula;

        do {
            do {
                System.out.println("Comecar simulacao");
                System.out.println("0 - Voltar");
                System.out.println("1 - Jogar");
                System.out.print("Selecione uma opcao -->");

                try {
                    op = sc.nextInt();
                } catch (InputMismatchException ex) {
                    System.out.println("Numero invalido!");
                    sc.next();
                }
            } while (op < 0 || op > 1);

            if (op == 1) {
                simula = new Simulacao(tot_simulacoes, new Edificio(this.edificio.getId(), this.edificio.getName(), this.edificio.getPlantaEdificio()));
                simulacoes.add(simula.modojogoManual());
                tot_simulacoes++;
            }
        } while (op != 0);

        for (Simulacoes simulacao : simulacoes) {
            System.out.println(simulacao.toString());
            System.out.println();
        }
    }
}
