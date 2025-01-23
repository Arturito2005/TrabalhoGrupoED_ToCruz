package Turnos;

import Cenarios.Cenarios;
import Exceptions.InvalidTypeItemException;
import Interfaces.Turno.TurnoToCruzInt;
import Items.Item;
import Items.ItemCura;
import Jogo.Simulacao;
import Mapa.Divisao;
import Mapa.Edificio;
import Personagens.Inimigo;
import Personagens.ToCruz;

import java.util.Iterator;

public class TurnoToCruz extends Turno implements TurnoToCruzInt {

    public TurnoToCruz(Cenarios cenario, Simulacao simulacao) {
        super(cenario, simulacao);
    }

    @Override
    public void turno(Divisao divisao) {

    }

    /**
     * Verifica se a personagem ToCruz deve usar um kit de primeiros socorros da mochila,
     * com base na condição de vida da personagem e na disponibilidade do kit na mochila.
     * Se a vida de ToCruz for menor ou igual a 30 e se a mochila contiver um kit, o kit é usado.
     *
     * @param toCruz O objeto da personagem ToCruz, que será analisado para decidir se o
     *               kit será utilizado.
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
     * Calcula e imprime o menor caminho entre duas divisões,
     * retornando a próxima divisão no caminho.
     * <p>
     * Este metodo utiliza o iterador do menor caminho entre duas divisões fornecido pelo edifício.
     * Ele constrói uma string que representa o caminho completo e identifica a próxima divisão
     * a ser visitada após a divisão inicial.
     *
     * @param div_start a divisão inicial no caminho.
     * @param div_final a divisão final no caminho.
     * @return a próxima divisão no menor caminho a partir da divisão inicial.
     */
    private Divisao shortesPathTwoPointsAutomatico(Divisao div_start, Divisao div_final) {
        Iterator<Divisao> shortestPath = simulacao.getEdificio().shortesPathIt(div_start, div_final);
        String temp = "";
        Divisao div_to = null;

        int i = 0;
        while (shortestPath.hasNext()) {
            Divisao div = shortestPath.next();

            if (i == 1) {
                div_to = div;
            }

            if (shortestPath.hasNext()) {
                temp = temp + div.getName() + " -->";
            } else {
                temp = temp + div.getName();
            }

            i++;
        }

        System.out.println(temp);
        return div_to;
    }

    /**
     * Metodo que sugere o melhor caminho para o To Cruz alcançar o alvo ou sair do edifício
     * automaticamente.
     * Avalia as divisões do edifício para determinar o destino ideal com base na distância
     * e no número de arestas.
     * <p>
     * Se o alvo já tiver sido recolhido, sugere o caminho para a saída mais próxima.
     * Caso contrário, sugere o caminho para a divisão onde o alvo se encontra.
     *
     * @param div_to A divisão de partida do To Cruz.
     * @return O melhor caminho calculado entre dois pontos (partida e destino).
     */
    private Divisao sugestaoCaminhoToCruzAutomatico(Divisao div_to) {
        ToCruz toCruz = div_to.getToCruz();
        Edificio edificio = simulacao.getEdificio();
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
     * Realiza a verificação e uso de itens automáticos de cura para o ToCruz.
     *
     * @param divisao a divisão onde o ToCruz se encontra.
     * @throws InvalidTypeItemException se o tipo de item for inválido.
     */
    private void ConditionGetUseItemAutomatico(Divisao divisao) throws InvalidTypeItemException {
        for (Item item : divisao.getItens()) {
            if (item instanceof ItemCura) {
                ItemCura item_cura = (ItemCura) item;
                ToCruz toCruz = divisao.getToCruz();

                if (toCruz.getVida() <= 30 && toCruz.mochilaTemKit()) {
                    toCruz.usarKit();
                }

                switch (item_cura.getType()) {
                    case COLETE: {
                        toCruz.usarItem(item_cura);
                        System.out.println("O To Cruz apanhou um colete de vida e ficou com " + toCruz.getVida() + " HP");
                        break;
                    } case KIT_VIDA: {
                        if (!toCruz.mochilaIsFull() && (toCruz.getVida() == 100 || toCruz.getVida() + item_cura.getVida_recuperada() >= 100)) {
                            toCruz.guardarKit(item_cura);
                            System.out.println("O To Cruz guardou um kit de vida com " + item_cura.getVida_recuperada() + " HP");
                        } else if (toCruz.getVida() + item_cura.getVida_recuperada() <= 100) {
                            toCruz.usarItem(item_cura);
                            System.out.println("O To Cruz usou um kit de vida com " + item_cura.getVida_recuperada() + " HP");
                        }
                        break;
                    } default: {
                        throw new InvalidTypeItemException("Tipo de item de cura invalido");
                    }
                }
            }
        }
    }

    //Talvez meter o moverToCruz, como um cenario dele.

    /**
     * Realiza o turno automático do ToCruz, considerando a presença de inimigos
     * e a busca por itens ou alvos.
     *
     * @param divisao_atual a divisão onde o ToCruz se encontra.
     */
    @Override
    public void turnoAutomatico(Divisao divisao_atual) {
        ToCruz toCruz = divisao_atual.getToCruz();
        Divisao divisao = divisao_atual;

        ConditionUseKitMochila(toCruz);
        if (divisao_atual.haveConfronto()) {
            for (Inimigo inimigo : divisao.getInimigos()) {
                cenario.ataque(toCruz, inimigo, divisao_atual);
            }
        } else {
            divisao = sugestaoCaminhoToCruzAutomatico(divisao_atual);

            //divisao.addToCruz(toCruz);
            //simulacao.updatePercursoToCruz(divisao);
            //divisao_atual.removeToCruz();

            //Meter o moverToCruz no cenarioToCruz
            simulacao.moverToCruz(divisao_atual, divisao);
            if (divisao.haveConfronto()) {
                for (Inimigo inimigo : divisao.getInimigos()) {
                    cenario.ataque(toCruz, inimigo, divisao_atual);
                }
            }

            if (!divisao.getItens().isEmpty()) {
                ConditionGetUseItemAutomatico(divisao);
            }
        }

        if (!divisao.haveConfronto() && divisao.isToCruzInDivisaoAlvo() && !divisao.getAlvo().isAtinigido()) {
            simulacao.getAlvo();
        }
    }

}
