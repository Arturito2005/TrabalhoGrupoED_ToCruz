package Modos;

import Cenarios.Personagens.CenariosToCruz;
import Interfaces.UnorderedListADT;
import Jogo.Simulacao;
import LinkedList.LinearLinkedUnorderedList;
import Mapa.Divisao;
import Personagens.ToCruz;
import Turnos.TurnoInimigo;

import java.util.Iterator;

/**
 * Representa um modo de jogo automatico onde o utilizador não consegue interagir com o ToCruz, onde
 * este modo indica o caminho mais curto para o ToCruz ir até há divisão do alvo, no menor caminho
 * e a sofrer o menor dano possível e se for possível, o caminho para para sair do edificio, seguindo os mesmos
 * criterios
 *
 * @author Artur Pinto
 * Nº mecanográfico: 8230138
 * @author Francisco Oliveira
 * Nº mecanografico: 8230148
 * @version 1.0
 */
public class ModoCaminhoAutomatico extends ModosAutomaticos {

    /**
     * Construtor para o modo automático de caminho. Inicializa o cenário e as variáveis
     * necessárias para a simulação do jogo.
     *
     * @param simulacao A simulação que contém as informações sobre o edifício, personagens,
     *                  inimigos e o progresso do jogo.
     */
    public ModoCaminhoAutomatico(Simulacao simulacao) {
        super(simulacao);
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
    public Simulacao jogar() {
        CenariosToCruz cenariosTo = getCenariosTo();
        Simulacao simulacao = cenariosTo.getSimulacao();
        TurnoInimigo turnoInimigo = getTurnoInimigo();

        Iterator<Divisao> itr = simulacao.getEdificio().IteratorMapa();
        UnorderedListADT<Divisao> list_entradas = new LinearLinkedUnorderedList<>();
        UnorderedListADT<Divisao> divisao_inimigo = new LinearLinkedUnorderedList<>();
        Divisao div_alvo = null;
        ToCruz toCruz = simulacao.getToCruz();

        while (itr.hasNext()) {
            Divisao div = itr.next();

            if (div.isEntrada_saida()) {
                list_entradas.addToRear(div);
            }

            if (div.getAlvo() != null) {
                div_alvo = div;
            }

            if (!div.getInimigos().isEmpty()) {
                divisao_inimigo.addToRear(div);
            }
        }

        if (calculateBesteEntradaToCruz(toCruz, div_alvo, list_entradas) > toCruz.getVida()) {
            System.out.println("O To Cruz nao consegue chegar ao alvo sem morrer!");
        } else {
            for (Divisao div_inimi : divisao_inimigo) {
                turnoInimigo.turno(div_inimi);
            }

            cenariosTo.getSimulacao().updatePercursoToCruz(div_alvo);
            div_alvo.addToCruz(toCruz);
            if (calculateBestExitAutomatico(div_alvo) > toCruz.getVida()) {
                System.out.println("E impossivel o To Cruz sair do edificio com vida!");
            }
        }

        return simulacao;
    }

    /**
     * Calcula a melhor entrada para o ToCruz, baseado na distância e nas arestas do
     * caminho até o alvo.
     *
     * @param toCruz        a personagem ToCruz.
     * @param div_alvo      a divisão alvo.
     * @param list_entradas as divisões de entrada para o edifício.
     * @return a melhor distância para o ToCruz atingir o alvo.
     */
    private double calculateBesteEntradaToCruz(ToCruz toCruz, Divisao div_alvo, UnorderedListADT<Divisao> list_entradas) {
        return getCenariosTo().getSimulacao().getEdificio().getShortestPath(BestStartToCruz(toCruz, list_entradas, div_alvo), div_alvo);
    }

    /**
     * Calcula o melhor caminho para o ToCruz sair do edifício, considerando as entradas fornecidas.
     *
     * @param div_alvo A divisão alvo que o ToCruz precisa alcançar.
     * @return A distância do melhor caminho encontrado.
     */
    private double calculateBestExitAutomatico(Divisao div_alvo) {
        return getCenariosTo().getSimulacao().getEdificio().getShortestPath(div_alvo, sugestaoCaminhoToCruzAutomatico(div_alvo));
    }
}
