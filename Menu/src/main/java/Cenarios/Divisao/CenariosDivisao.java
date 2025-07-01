package Cenarios.Divisao;

import Cenarios.Cenarios;
import Exceptions.ElementNotFoundException;
import Exceptions.InvalidOptionException;
import Exceptions.InvalidTypeItemException;
import Interfaces.Cenarios.CenariosDivisaoInterface;
import Items.Item;
import Items.ItemCura;
import Items.TypeItemCura;
import Jogo.Simulacao;
import Mapa.Divisao;
import Personagens.ToCruz;

import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Scanner;

public class CenariosDivisao extends Cenarios implements CenariosDivisaoInterface {

    public CenariosDivisao(Simulacao simulacao) {
        super(simulacao);
    }

    /**
     * Verifica se o To Cruz está numa saída.
     *
     * @return {@code true} se o To Cruz estiver na saída, {@code false} caso contrário.
     */
    @Override
    public boolean isToCruzInExit(Divisao divisao) {
        boolean is = false;

        if (divisao.getToCruz() != null && divisao.isEntrada_saida()) {
            is = true;
        }

        return is;
    }

    /**
     * Verifica se o To Cruz está na divisão com o alvo.
     *
     * @return {@code true} se o To Cruz estiver com o alvo, {@code false} caso contrário.
     */
    @Override
    public boolean isToCruzInDivisaoAlvo(Divisao divisao) {
        boolean is = false;

        if (divisao.getToCruz() != null && divisao.getAlvo() != null) {
            is = true;
        }

        return is;
    }

    /**
     * Verifica se há um confronto entre o To Cruz e os inimigos.
     *
     * @return {@code true} se houver confronto, {@code false} caso contrário.
     */
    @Override
    public boolean haveConfronto(Divisao divisao) {
        boolean confronto = false;

        if (divisao.getToCruz() != null && !divisao.getInimigos().isEmpty()) {
            confronto = true;
        }

        return confronto;
    }

    /**
     * Este metodo trata de um item encontrado em uma divisão e permite que o ToCruz
     * interaja com ele.
     * Dependendo do tipo de item, ele pode ser usado, deixado na sala ou guardado na mochila.
     *
     * @param divisao A divisão onde o item foi encontrado.
     * @param toCruz  A personagem que interage com o item.
     * @throws InvalidOptionException   Caso a opção fornecida pelo jogador seja inválida.
     * @throws InvalidTypeItemException Caso o tipo de item seja inválido.
     */
    @Override
    public void DivisaoComItem(Divisao divisao, ToCruz toCruz) throws InvalidOptionException, InvalidTypeItemException {
        Scanner sc = new Scanner(System.in);
        for (Item item : divisao.getItens()) {
            if (item instanceof ItemCura) {
                ItemCura itemCura = (ItemCura) item;

                switch (itemCura.getType()) {
                    case COLETE: {
                        toCruz.usarItem(itemCura);
                        System.out.println("O To Cruz apanhou um colete e ficou com " + toCruz.getVida() + " HP");
                        break;
                    }
                    case KIT_VIDA: {
                        int op = -1;
                        if (toCruz.getVida() < 100 && !toCruz.mochilaIsFull()) {
                            do {
                                System.out.println("Esta numa sala com um kit de vida de " + itemCura.getVida_recuperada());
                                System.out.println("0 - Usar Item");
                                System.out.println("1 - Deixa-lo na sala");
                                System.out.println("2 - Guardar");
                                System.out.print("Selecione uma opcao -->");
                                try {
                                    op = sc.nextInt();
                                } catch (InputMismatchException ex) {
                                    System.out.println("Numero invalido!");
                                    sc.next();
                                }
                            } while (op < 0 || op > 2);
                        } else if (toCruz.mochilaIsFull() && toCruz.getVida() >= 100) {
                            do {
                                System.out.println("Esta numa sala com um kit de vida de " + itemCura.getVida_recuperada());
                                System.out.println("0 - Usar Item");
                                System.out.println("1 - Deixa-lo na sala");
                                System.out.print("Selecione uma opcao -->");
                                try {
                                    op = sc.nextInt();
                                } catch (InputMismatchException ex) {
                                    System.out.println("Numero invalido!");
                                    sc.next();
                                }
                            } while (op < 0 || op > 1);
                        } else if (!toCruz.mochilaIsFull() && toCruz.getVida() >= 100) {
                            System.out.println("Esta numa sala com um kit de vida de " + itemCura.getVida_recuperada());
                            System.out.println("1 - Deixa-lo na sala");
                            System.out.println("2 - Guardar");
                            System.out.print("Selecione uma opcao -->");
                            do {
                                try {
                                    op = sc.nextInt();
                                } catch (InputMismatchException ex) {
                                    System.out.println("Numero invalido!");
                                    sc.next();
                                }
                            } while (op < 1 || op > 2);
                        }

                        switch (op) {
                            case 0: {
                                usarItemDivisao(item, divisao);
                                break;
                            }
                            case 1: {
                                System.out.println("O To Cruz nao coleta o item");
                                break;
                            }
                            case 2: {
                                guardarKitMochila(item, divisao);
                                break;
                            }
                            default: {
                                throw new InvalidOptionException("Introduziu uma opcao invalida");
                            }
                        }
                        break;
                    }
                    default: {
                        throw new InvalidTypeItemException("Tipo de item invalido");
                    }
                }
            }
        }
    }

    public void collectAlvo() {
        getSimulacao().setCollectedAlvo(true);
    }

    private void usarItemDivisao(Item item, Divisao divisao) {
        ToCruz toCruz = new ToCruz();

        if (divisao.containItem(item)) {
            if (item instanceof ItemCura) {
                if ((((ItemCura) item).getType().equals(TypeItemCura.KIT_VIDA) && toCruz.getVida() < 100) || item.equals(TypeItemCura.COLETE)) {
                    try {
                        toCruz.usarItem((ItemCura) item);
                        collectAlvo();
                        divisao.removeItem(item);
                    } catch (NullPointerException e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
        } else {
            System.out.println("O item desta divisao ja foi usado anteriormente");
        }
    }

    private void guardarKitMochila(Item item, Divisao divisao) {
        ToCruz toCruz = new ToCruz();

        if (divisao.containItem(item) && !toCruz.mochilaIsFull()) {
            try {
                toCruz.guardarKit((ItemCura) item);
                collectAlvo();
                divisao.removeItem(item);
            } catch (NullPointerException e) {
                System.out.println(e.getMessage());
            }
        }
    }

}
