package Cenarios.Personagens;

import ArrayList.ExtendedArrayUnordered;
import Interfaces.ArrayUnorderedADT;
import Interfaces.Cenarios.CenariosToCruzInterface;
import Interfaces.UnorderedListADT;
import Items.Item;
import Items.ItemCura;
import Items.TypeItemCura;
import Jogo.Simulacao;
import LinkedList.LinearLinkedUnorderedList;
import Mapa.Divisao;
import Mapa.Edificio;
import Paths.ShortesPaths;
import Personagens.Inimigo;
import Personagens.ToCruz;

import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Scanner;

/*
 * Criar cenario para quando o ToCruz entra numa divisão e inicia cofronto para evitar estar sempre a criar um for
 * */
public class CenariosToCruz extends CenariosPersonagem implements CenariosToCruzInterface {
    private Scanner sc;

    public CenariosToCruz(Simulacao simulacao) {
        super(simulacao);
        this.sc = new Scanner(System.in);
    }

    @Override
    public void ataqueToCruz(Divisao divisao) {
        ToCruz toCruz = getSimulacao().getToCruz();
        UnorderedListADT<Inimigo> inimigosDead = new LinearLinkedUnorderedList<>();
        for (Inimigo inimigo : divisao.getInimigos()) {
            ataque(toCruz, inimigo);

            if (inimigo.isDead()) {
                inimigosDead.addToRear(inimigo);
            }
        }

        for (Inimigo inimigo : inimigosDead) {
            Simulacao simulacao = getSimulacao();
            simulacao.inimigoDead(inimigo, divisao);
        }
    }

    @Override
    public void moverToCruz(Divisao divisaoAtual, Divisao novaDivisao) throws NullPointerException {
        if (divisaoAtual == null) {
            throw new NullPointerException("A divisao atual está nula!");
        } else if(novaDivisao == null) {
            throw new NullPointerException("A nova divisão está nula!");
        }

        ToCruz toCruz = new ToCruz();
        Simulacao simulacao = getSimulacao();

        simulacao.updatePercursoToCruz(novaDivisao);
        divisaoAtual.removeToCruz();
        novaDivisao.addToCruz(toCruz);
    }

    @Override
    public Divisao andar(Divisao divisao_atual) {
        int op = -1;
        Edificio edificio = getSimulacao().getEdificio();
        Iterator<Divisao> itr = edificio.getNextDivisoes(divisao_atual);
        ArrayUnorderedADT<Divisao> listDiv = new ExtendedArrayUnordered<>();

        int i = 0;
        String temp;

        System.out.println();
        sugestaoCaminhoToCruzKitEAlvo(divisao_atual);
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

            if (!divisao.getItens().isEmpty()) {
                for (Item item : divisao.getItens()) {
                    if (item instanceof ItemCura) {
                        ItemCura itemCura = (ItemCura) item;
                        if (divisao.getInimigos().isEmpty()) {
                            if (itemCura.getType().equals(TypeItemCura.KIT_VIDA)) {
                                temp += " (divisao com kit)";
                            } else if (itemCura.getType().equals(TypeItemCura.COLETE)) {
                                temp += " (divisao com colete)";
                            }
                        } else {
                            if (itemCura.getType().equals(TypeItemCura.KIT_VIDA)) {
                                temp += " (divisao com kit e com inimigos)";
                            } else if (itemCura.getType().equals(TypeItemCura.COLETE)) {
                                temp += " (divisao com colete e com inimigos)";
                            }
                        }
                    }
                }

            }

            System.out.println(temp);
            listDiv.addToRear(divisao);
        }

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

    /**
     * Sugere o melhor caminho para o ToCruz chegar a um item ou a um alvo.
     * O ToCruz deve coletar o item e, em seguida, atingir o alvo.
     *
     * @param div_to A divisão onde o ToCruz está localizado.
     */
    /*
    * Talvez reformular este metodo, meter um apenas para o kit e outro para o Alvo
    * */
    @Override
    public void sugestaoCaminhoToCruzKitEAlvo(Divisao div_to) {
        Simulacao simulacao = getSimulacao();
        Edificio edificio = simulacao.getEdificio();
        Iterator<Divisao> itr = edificio.IteratorMapa();

        if(itr.hasNext()) {
            Divisao item_div = null;
            Divisao div_alvo = null;
            double best_distance = Double.MAX_VALUE;
            double distance;
            double num_arestas_com = Double.MAX_VALUE;
            double num_arestas;

            while (itr.hasNext()) {
                Divisao div = itr.next();

                if (!div.getItens().isEmpty() || (div.isEntrada_saida() && div.getAlvo() != null)) {
                    distance = edificio.getShortestPath(div_to, div);

                    if (distance == 0 || distance == best_distance) {
                        best_distance = distance;
                        num_arestas = edificio.getShortestPathNumArestas(div_to, div);

                        if (num_arestas < num_arestas_com) {
                            if (distance == 0) {
                                num_arestas_com = num_arestas;
                            }

                            if(!div.getItens().isEmpty()) {
                                item_div = div;
                            }

                            if (div.isEntrada_saida()) {
                                div_alvo = div;
                            }
                        }
                    } else if (distance < best_distance) {
                        best_distance = distance;

                        if(!div.getItens().isEmpty()) {
                            item_div = div;
                        }

                        if (div.isEntrada_saida()) {
                            div_alvo = div;
                        }
                    }
                } else if (div.getAlvo() != null && !simulacao.isCollectedAlvo()) {
                    div_alvo = div;
                }
            }

            ShortesPaths shortesPaths = new ShortesPaths(edificio);

            if (item_div != null) {
                System.out.println("Sugestao de caminho mais curto para o To Cruz chegar a um item de cura");
                shortesPaths.shortesPathTwopoints(div_to, item_div);
            } else {
                System.out.println("Todos os itens já foram coletados!");
            }

            if (div_alvo != null) {
                String temp;
                if (div_alvo.getAlvo() != null) {
                    temp = "Sugestao de melhor caminho para o To Cruz chegar ao alvo: " + div_alvo.getAlvo().getNome();
                } else {
                    temp = "Sugestao de melhor caminho para o ToCruz sair do edificio";
                }

                System.out.println(temp);
                shortesPaths.shortesPathTwopoints(div_to, div_alvo);
            } else {
                System.out.println("Não existe nenhuma suguestão para saida ou alvo");
            }
        } else {
            System.out.println("O edificio está vazio!");
        }
    }
}
