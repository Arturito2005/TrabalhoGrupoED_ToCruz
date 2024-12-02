package menu;

import Importar.ImportarMapa;
import Metodos.ImportarDados;
import Missoes.Missao;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;


/*
* Não esquecer de meter metodos para gerar o mapa e para atualizar o mapa,
* o atualizar mapa ele é chamado após cada turno (provavlemente vai ficar na missão)
* e ele vai mudar de divisao do toCruz e dos inimigos e é onde caso o ToCruz esteja na divisao do item ele vai
* coletá-lo
* */
public class ModoJogo {

    //Depois eliminar
    private static final String path = "C:/Users/artur/OneDrive/Desktop/universidade/2_ano/ED/TrabalhoGrupoED_ToCruz/JSON/FicheiroMissao.json";

    public static void main(String[] args) throws IOException {
        Missao missao = new Missao();
        //ImportarDados imp = new ImportarDados();
        ImportarMapa importarMapa = new ImportarMapa();
        missao = importarMapa.gerarMapa(path);
        Scanner sc = new Scanner(System.in);
        int op = -1;

        //Inicializa ciclo do while
        do {
            System.out.println("1- Modo Manual");
            System.out.println("2- Modo automárico");
            System.out.println("Introduza o modo do jogo -->");
            try {
                op = sc.nextInt();
            } catch (InputMismatchException ex) {
                System.out.println("Numero inválido!");
                sc.next();
            }
        } while(op < 0 || op >= 2);

        if (op == 1) {
            //Inicializa o jogo no modo Manual
            missao.modoManual();
        } else {
            //Inicialzia o jogo no modo Automático
            //missao.modoAutomatico();
        }
    }

}
