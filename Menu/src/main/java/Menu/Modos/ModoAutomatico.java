package Menu.Modos;

import Exceptions.InvalidTypeItemException;
import Interfaces.UnorderedListADT;
import Items.ItemCura;
import LinkedList.LinearLinkedUnorderedList;
import Mapa.Divisao;

import java.util.Iterator;

/*
* Por enquanto possui os dois modos automaticos
* */
public class ModoAutomatico {

    /**
     * Realiza a verificação e uso de itens automáticos de cura para o ToCruz.
     *
     * @param divisao a divisão onde o ToCruz se encontra.
     * @throws InvalidTypeItemException se o tipo de item for inválido.
     */
    private void ConditionGetUseItemAutomatico(Divisao divisao) throws InvalidTypeItemException {
        if (!divisao.getItem().isCollected() && divisao.getItem() instanceof ItemCura) {
            ItemCura item = (ItemCura) divisao.getItem();
            ToCruz toCruz = divisao.getToCruz();

            if (item != null) {
                switch (item.getType()) {
                    case COLETE: {
                        toCruz.usarItem(item);
                        System.out.println("O To Cruz apanhou um colete de vida e ficou com " + toCruz.getVida() + " HP");
                        break;
                    }
                    case KIT_VIDA: {
                        if (!toCruz.mochilaIsFull() && (toCruz.getVida() == 100 || toCruz.getVida() + item.getVida_recuperada() >= 100)) {
                            toCruz.guardarKit(item);
                            System.out.println("O To Cruz guardou um kit de vida com " + item.getVida_recuperada() + " HP");
                        } else if (toCruz.getVida() + item.getVida_recuperada() <= 100) {
                            toCruz.usarItem(item);
                            System.out.println("O To Cruz usou um kit de vida com " + item.getVida_recuperada() + " HP");
                        }
                        break;
                    }
                    default: {
                        throw new InvalidTypeItemException("Tipo de item de cura invalido");
                    }
                }
            } else {
                if (toCruz.getVida() <= 30 && toCruz.mochilaTemKit()) {
                    toCruz.usarKit();
                }
            }
        }
    }

    /**
     * Verifica se a personagem ToCruz deve usar um kit de primeiros socorros da mochila,
     * com base na condição de vida da personagem e na disponibilidade do kit na mochila.
     * Se a vida de ToCruz for menor ou igual a 30 e se a mochila contiver um kit, o kit é usado.
     *
     * @param toCruz O objeto da personagem ToCruz, que será analisado para decidir se o
     * kit será utilizado.
     * @return Retorna verdadeiro se o kit foi usado, caso contrário retorna falso.
     */
    private boolean ConditionUseKitMochila(ToCruz toCruz) {
        boolean usedKit = false;

        if (toCruz.getVida() <= 30 && toCruz.mochilaTemKit()) {
            toCruz.usarKit();
            usedKit = true;
        }

        return usedKit;
    }

    /**
     * Determina a melhor divisão inicial para o ToCruz começar a sua trajetória dentro do edifício.
     *
     * @param toCruz a personagem ToCruz que realizará a simulação.
     * @return a divisão onde o ToCruz deve começar o seu percurso.
     */
    private Divisao BestStartToCruz(ToCruz toCruz, UnorderedListADT<Divisao> list_entradas, Divisao div_alvo) {
        Divisao best_div = null;
        double best_distance = Double.MAX_VALUE;
        double num_arestas_com = Double.MAX_VALUE;

        for (Divisao div_entr : list_entradas) {
            double distance = edificio.getShortestPath(div_entr, div_alvo);

            if (distance == 0 || distance == best_distance) {
                best_distance = distance;
                double num_arestas = edificio.getShortestPathNumArestas(div_entr, div_alvo);

                if (num_arestas < num_arestas_com) {
                    if (distance == 0) {
                        num_arestas_com = num_arestas;
                    }
                    best_div = div_entr;

                }
            } else if (distance < best_distance) {
                best_div = div_entr;
                best_distance = distance;
            }
        }

        if (best_distance < toCruz.getVida()) {
            System.out.println("A melhor entrada que o To Cruz deve escolher e esta: " + best_div.getName());
            System.out.println("Melhor caminho que o To Cruz pode fazer ate ao alvo");

            shortesPathTwopoints(best_div, div_alvo);
        }

        return best_div;
    }

    /**
     * Metodo que sugere o melhor caminho para o To Cruz alcançar o alvo ou sair do edifício
     * automaticamente.
     * Avalia as divisões do edifício para determinar o destino ideal com base na distância
     * e no número de arestas.
     *
     * Se o alvo já tiver sido recolhido, sugere o caminho para a saída mais próxima.
     * Caso contrário, sugere o caminho para a divisão onde o alvo se encontra.
     *
     * @param div_to A divisão de partida do To Cruz.
     * @return O melhor caminho calculado entre dois pontos (partida e destino).
     */
    private Divisao sugestaoCaminhoToCruzAutomatico(Divisao div_to) {
        ToCruz toCruz = div_to.getToCruz();
        Iterator<Divisao> itr = edificio.IteratorMapa();
        Divisao best_destino = null;
        double best_distance = Double.MAX_VALUE;
        double num_arestas_com = Double.MAX_VALUE;
        boolean find_alvo = false;

        while (itr.hasNext() && !find_alvo) {
            Divisao div = itr.next();

            if (div.isEntrada_saida() && toCruz.isColectedAlvo()) {
                double distance = edificio.getShortestPath(div_to, div);

                if (distance == 0 || distance == best_distance) {
                    best_distance = distance;
                    double num_arestas = edificio.getShortestPathNumArestas(div_to, div);

                    if (num_arestas < num_arestas_com) {
                        if (distance == 0) {
                            num_arestas_com = num_arestas;
                        }
                        best_destino = div;
                    }
                } else if (distance < best_distance) {
                    best_distance = distance;
                    best_destino = div;
                }
            } else if (div.getAlvo() != null && !toCruz.isColectedAlvo()) {
                best_destino = div;
                find_alvo = true;
            }
        }

        String temp;
        if (best_destino.getAlvo() != null) {
            temp = "Sugestao de melhor caminho para o To Cruz chegar ao alvo: " + best_destino.getAlvo().getNome();
        } else {
            temp = "Sugestao de melhor caminho para o ToCruz sair do edificio";
        }

        System.out.println(temp);
        return shortesPathTwoPointsAutomatico(div_to, best_destino);
    }

    /**
     * Realiza o turno automático do ToCruz, considerando a presença de inimigos
     * e a busca por itens ou alvos.
     *
     * @param divisao_atual a divisão onde o ToCruz se encontra.
     */
    private void turnoAutomaticoToCruz(Divisao divisao_atual) {
        ToCruz toCruz = divisao_atual.getToCruz();
        Divisao divisao = divisao_atual;

        if (divisao_atual.haveConfronto()) {
            if (!ConditionUseKitMochila(toCruz)) {
                divisao_atual.attackToCruz(inimigos_dead);
            }
        } else {
            ConditionUseKitMochila(toCruz);
            divisao = sugestaoCaminhoToCruzAutomatico(divisao_atual);

            divisao.addToCruz(toCruz);
            this.addDivisaoTrajetoToCruz(divisao);
            divisao_atual.removeToCruz();

            if (divisao.haveConfronto()) {
                divisao.attackToCruz(inimigos_dead);
            }

            if (divisao.getItem() != null) {
                ConditionGetUseItemAutomatico(divisao);
            }
        }

        if (!divisao.haveConfronto() && divisao.isToCruzInDivisaoAlvo() && !divisao.getAlvo().isAtinigido()) {
            divisao.ToCruzGetAlvo();
        }
    }

    /**
     * Calcula a melhor entrada para o ToCruz, baseado na distância e nas arestas do
     * caminho até o alvo.
     *
     * @param toCruz a personagem ToCruz.
     * @param div_alvo a divisão alvo.
     * @param list_entradas as divisões de entrada para o edifício.
     * @return a melhor distância para o ToCruz atingir o alvo.
     */
    private double calculateBesteEntradaToCruz(ToCruz toCruz, Divisao div_alvo, UnorderedListADT<Divisao> list_entradas) {
        return edificio.getShortestPath(BestStartToCruz(toCruz, list_entradas, div_alvo), div_alvo);
    }

    /**
     * Calcula o melhor caminho para o ToCruz sair do edifício, considerando as entradas fornecidas.
     *
     * @param div_alvo A divisão alvo que o ToCruz precisa alcançar.
     * @return A distância do melhor caminho encontrado.
     */
    public double calculateBestExitAutomatico(Divisao div_alvo) {
        return edificio.getShortestPath(div_alvo, sugestaoCaminhoToCruzAutomatico(div_alvo));
    }

    /**
     * Realiza a simulação do jogo no modo automático, onde a personagem ToCruz é controlado
     * pelo sistema para tentar alcançar um alvo e, em seguida, encontrar uma saída do edifício
     * sem morrer. O metodo verifica as entradas e saídas do edifício, os inimigos presentes
     * e tenta calcular se é possível concluir a missão com vida.
     * <p>
     * O ToCruz segue automaticamente pelo edifício, enfrenta inimigos, até tentar atingir
     * o alvo e sair do edifício. Se a vida de ToCruz for insuficiente para completar o trajeto
     * ou escapar do edifício, o jogador é notificado.
     */
    @Override
    public void modojogoAutomatico() {
        Iterator<Divisao> itr = edificio.getPlantaEdificio().iterator();
        UnorderedListADT<Divisao> list_entradas = new LinearLinkedUnorderedList<>();
        UnorderedListADT<Divisao> divisao_inimigo = new LinearLinkedUnorderedList<>();
        Divisao div_alvo = null;
        ToCruz to = new ToCruz();

        while (itr.hasNext()) {
            Divisao div = itr.next();

            if (div.isEntrada_saida()) {
                list_entradas.addToRear(div);
            }

            if (div.getAlvo() != null) {
                div_alvo = div;
            }

            if (div.haveInimigo()) {
                divisao_inimigo.addToRear(div);
            }
        }

        if (calculateBesteEntradaToCruz(to, div_alvo, list_entradas) > to.getVida()) {
            System.out.println("O To Cruz nao consegue chegar ao alvo sem morrer!");
        } else {
            for (Divisao div_inimi : divisao_inimigo) {
                turnoInimigo(div_inimi);
            }

            div_alvo.addToCruz(to);
            div_alvo.getAlvo().setAtinigido(true);
            to.setColectedAlvo(true);
            if (calculateBestExitAutomatico(div_alvo) > to.getVida()) {
                System.out.println("E impossivel o To Cruz sair do edificio com vida!");
            }
        }
    }


}
