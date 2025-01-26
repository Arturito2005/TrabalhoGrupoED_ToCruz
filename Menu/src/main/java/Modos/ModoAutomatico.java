package Modos;

import Cenarios.Divisao.CenariosDivisao;
import Cenarios.Personagens.CenariosToCruz;
import Interfaces.UnorderedListADT;
import Jogo.Simulacao;
import LinkedList.LinearLinkedUnorderedList;
import Mapa.Divisao;
import Mapa.Edificio;
import Personagens.ToCruz;
import Turnos.TurnoInimigo;
import Turnos.TurnoToCruz;

import java.util.Iterator;

/**
 * Representa o modo automático do jogo, onde o personagem ToCruz é controlado automaticamente
 * pelo sistema para enfrentar inimigos, antigir o alvo e sair do edificio.
 *
 * O modo automático simula o movimento do personagem e a interação com inimigos,
 * com o alvo de maneira autônoma.
 *
 * @author Artur Pinto
 * Nº mecanográfico: 8230138
 * @author Francisco Oliveira
 * Nº mecanografico: 8230148
 * @version 1.0
 */
public class ModoAutomatico extends ModosAutomaticos {

    /**
     * Construtor para o modo automático. Inicializa a simulação do jogo.
     *
     * @param simulacao A instância da simulação que contém o estado atual do jogo.
     */
    public ModoAutomatico(Simulacao simulacao) {
        super(simulacao);
    }

    /**
     * Simula o jogo automaticamente, fazendo o ToCruz se , enfrentar inimigos e
     * atingir objetivos.
     *
     * @return A instância atual da simulação após a execução do jogo automático.
     */
    @Override
    public Simulacao jogar() {
        CenariosToCruz cenariosTo = getCenariosTo();
        CenariosDivisao cenariosDivisao = getCenariosDivisao();
        Simulacao simulacao = cenariosTo.getSimulacao();
        TurnoInimigo turnoInimigo = getTurnoInimigo();
        TurnoToCruz turnoTo = getTurnoTo();
        Edificio edificio = simulacao.getEdificio();
        ToCruz toCruz = simulacao.getToCruz();
        Iterator<Divisao> itrMapa = edificio.IteratorMapa();
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
        simulacao.updatePercursoToCruz(div_start);
        div_start.addToCruz(toCruz);

        while (!finishgame) {
            itrMapa = edificio.IteratorMapa();
            UnorderedListADT<Divisao> endTurno = new LinearLinkedUnorderedList<>();
            while (itrMapa.hasNext()) {
                Divisao div = itrMapa.next();

                if (!div.getInimigos().isEmpty()) {
                    endTurno.addToRear(div);
                }
            }

            while (!endTurno.isEmpty() && !toCruz.isDead()) {
                turnoInimigo.turno(endTurno.removeFirst());
            }

            if(toCruz.isDead()) {
                finishgame = true;
                System.out.println("O To Cruz foi morto!");
            } else {
                itrMapa = edificio.IteratorMapa();
                boolean findToCruz = false;
                while (itrMapa.hasNext() && !findToCruz) {
                    Divisao div = itrMapa.next();

                    if (div.getToCruz() != null) {
                        if (cenariosDivisao.isToCruzInDivisaoAlvo(div) && simulacao.isCollectedAlvo()) {
                            finishgame = true;
                        } else {
                            turnoTo.turnoAutomatico(div);
                        }

                        findToCruz = true;
                    }
                }
            }
        }

        simulacao.relatorioSimulacao();
        return simulacao;
    }
}
