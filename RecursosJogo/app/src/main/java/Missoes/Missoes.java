package Missoes;

import Interfaces.MissoesInt;
import Interfaces.UnorderedListADT;
import LinkedList.LinearLinkedUnorderedList;
import java.util.Scanner;

public class Missoes implements MissoesInt {

    private static Scanner sc = new Scanner(System.in);

    private int contMissoes;

    private UnorderedListADT<Missao> listaMissao;

    public Missoes() {
        this.contMissoes = 0;
        listaMissao = new LinearLinkedUnorderedList<Missao>();
    }

    public int getContMissoes() {
        return this.contMissoes;
    }

    public UnorderedListADT<Missao> getListaMissao() {
        return this.listaMissao;
    }

    @Override
    public void addMissao(Missao missao) throws NullPointerException {
        if(missao == null) {
            throw new NullPointerException("Não é possível introduzir missões nulas");
        }
        listaMissao.addToRear(missao);
        this.contMissoes++;
    }

    /*
    * Isto já é menu.
    public void SelecionaMissao() {
        Missao[] missoes = new Missao[listaMissao.size()];
        int i = 0;

        for (Missao missao : listaMissao) {
            System.out.println("Missao nº " + i + " de nome " + missao.getCod_versao());
            missoes[i++] = missao;
        }

        int op_missao = -1;
        do {
            System.out.print("Selecione a opção da missão que quer jogar -->");

            try {
                op_missao = sc.nextInt();
            } catch (InputMismatchException ex) {
                System.out.println("Numero invalido!");
                sc.next();
            }
        } while (op_missao < 0 && op_missao > i - 1);


        System.out.println("0 - Cancelar");
        System.out.println("1 - Modo Manual");
        System.out.println("2 - Modo Automatico");
        System.out.println("3 - Jogo Automatico");
        int op_modo = -1;

        do {
            System.out.print("Selecione o modo de jogo que pretende jogar -->");
            try {
                op_modo = sc.nextInt();
            } catch (InputMismatchException ex) {
                System.out.println("Numero invalido!");
                sc.next();
            }

        } while (op_modo < 0 && op_modo > i - 1);
    }
    * */


}
