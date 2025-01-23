package Modos;

import Exceptions.InvalidTypeItemException;
import Interfaces.UnorderedListADT;
import Items.ItemCura;
import LinkedList.LinearLinkedUnorderedList;
import Mapa.Divisao;
import Mapa.Edificio;
import Missoes.Simulacoes;

import java.util.Iterator;

/*
* Por enquanto possui os dois modos automaticos
* */
public class ModoAutomatico {

    /**
     * Executa um jogo automático nesta missão, ou seja, o jogador não interage para fazer
     * uma determinada ação.
     */



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

    /*Apenas sugestão do caminho automatico*/
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

    /*Principal Automaitco*/
    /**
     * Simula o jogo automaticamente, fazendo o ToCruz se , enfrentar inimigos e
     * atingir objetivos.
     *
     * @return A instância atual da simulação após a execução do jogo automático.
     */
    @Override
    public Simulacoes jogoAutomatico() {
        ToCruz toCruz = new ToCruz();
        Iterator<Divisao> itrMapa = this.edificio.IteratorMapa();
        UnorderedListADT<Divisao> list_entradas = new LinearLinkedUnorderedList<>();
        Divisao div_alvo = null;
        boolean finishgame = false;
        while (itrMapa.hasNext()) {
            Divisao div = itrMapa.next();

            if (div.isEntrada_saida()) {
                list_entradas.addToRear(div);
            }

            if (div.getAlvo() != null) {
                div_alvo = div;
            }
        }

        Divisao div_start = BestStartToCruz(toCruz, list_entradas, div_alvo);
        addDivisaoTrajetoToCruz(div_start);
        div_start.addToCruz(toCruz);
        while (!finishgame) {
            itrMapa = this.edificio.IteratorMapa();
            UnorderedListADT<Divisao> endTurno = new LinearLinkedUnorderedList<>();
            while (itrMapa.hasNext()) {
                Divisao div = itrMapa.next();

                if (div.haveInimigo()) {
                    endTurno.addToRear(div);
                }
            }

            while (!endTurno.isEmpty()) {
                turnoInimigo(endTurno.removeFirst());
            }

            itrMapa = this.edificio.IteratorMapa();
            boolean findToCruz = false;
            while (itrMapa.hasNext() && !findToCruz) {
                Divisao div = itrMapa.next();

                if (div.getToCruz() != null) {
                    if (div.getToCruz().isDead() || (div.isToCruzInExit() && div.getToCruz().isColectedAlvo())) {
                        finishgame = true;
                    } else {
                        turnoAutomaticoToCruz(div);
                    }

                    findToCruz = true;
                }
            }
        }

        long vida = toCruz.getVida();
        if (vida < 0) {
            vida = 0;
        }
        this.vida_to = vida;
        relatoriosMissao(toCruz);
        return this;
    }


}
