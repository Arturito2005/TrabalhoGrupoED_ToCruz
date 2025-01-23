package Cenarios;

import ArrayList.ArrayUnordered;
import Interfaces.ArrayUnorderedADT;
import Items.ItemCura;
import Items.TypeItemCura;
import Mapa.Divisao;
import Personagens.Personagem;
import Personagens.ToCruz;

import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Scanner;

public class CenariosToCruz extends Cenarios{
    private Scanner sc = new Scanner(System.in);

    private ToCruz toCruz;

    public CenariosToCruz() {
        this.toCruz = new ToCruz();
    }

    public CenariosToCruz(ToCruz toCruz) {
        this.toCruz = toCruz;
    }

    //No codigo onde chamo este metodo meter chamar o moverToCruz da Simulação para realmente o mexer e atualizar o percurso.
    public Divisao mover(Divisao divisao_atual) {
        int op = -1;
        Iterator<Divisao> itr = this.getEdificio().getNextDivisoes(divisao_atual);
        ArrayUnorderedADT<Divisao> listDiv = new ArrayUnordered<Divisao>();

        int i = 0;
        String temp;

        System.out.println();
        System.out.println("Divisoes que o To Cruz pode entrar: ");
        while (itr.hasNext()) {
            Divisao divisao = itr.next();
            temp = i++ + " - " + divisao.getName();

            if (divisao.isEntrada_saida()) {
                if (divisao.getInimigos().isEmpty()) {
                    temp += " (esta divisao e uma entrada/saida)";
                } else {
                    temp += " (esta divisao e uma entrada/saida e tem inimigos)";
                }
            }

            if (divisao.getAlvo() != null) {
                if (divisao.getInimigos().isEmpty()) {
                    temp += " (divisao onde esta o alvo)";
                } else {
                    temp += "(divisao onde esta o alvo, mas tem inimigos)";
                }
            }

            if (divisao.getItem() != null) {
                if (divisao.getItem() instanceof ItemCura && !divisao.getItem().isCollected()) {
                    if (divisao.getInimigos().isEmpty()) {
                        if (((ItemCura) divisao.getItem()).getType().equals(TypeItemCura.KIT_VIDA)) {
                            temp += " (divisao com kit)";
                        } else if (((ItemCura) divisao.getItem()).getType().equals(TypeItemCura.COLETE)) {
                            temp += " (divisao com colete)";
                        }
                    } else {
                        if (((ItemCura) divisao.getItem()).getType().equals(TypeItemCura.KIT_VIDA)) {
                            temp += " (divisao com kit e com inimigos)";
                        } else if (((ItemCura) divisao.getItem()).getType().equals(TypeItemCura.COLETE)) {
                            temp += " (divisao com colete e com inimigos)";
                        }
                    }
                }
            }

            System.out.println(temp);
            listDiv.addToRear(divisao);
        }

        //Ver onde colocar o metodo deste, atualmente está algures na pasta do turno manula ou menu
        sugestaoCaminhoToCruzKitEAlvo(divisao_atual);

        System.out.println();
        do {
            System.out.print("Selecione a divisao que o ToCruz vai se mover -->");

            try {
                op = sc.nextInt();
            } catch (InputMismatchException ex) {
                System.out.println("Numero invalido!");
                sc.next();
            }
        } while (op < 0 || op > listDiv.size() - 1);

        Divisao div = null;
        try {
            div = listDiv.find(op);
        } catch (NullPointerException | ArrayIndexOutOfBoundsException ex) {
            System.out.println(ex.getMessage());
        }

        return div;
    }

    @Override
    public void ataque(Personagem atacante) {
        System.out.println("Turno do To Cruz atacar!");
        personagem.setVida(personagem.getVida() - this.toCruz.getPoder());

        if (personagem.isDead()) {
            System.out.println("O To Cruz matou o inimigo " + inimigo.getNome());
            getinimigosDead.addToRear(inimigo);
            divisao.removeInimigo(inimigo);
        } else {
            System.out.println("O inimigo " + inimigo.getNome() + " resitiu ao ataque do To Cruz e ficou com: " + inimigo.getVida() + " HP");
        }
    }

}
