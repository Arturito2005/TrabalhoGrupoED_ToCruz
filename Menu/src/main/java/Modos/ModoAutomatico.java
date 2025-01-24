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

/*
* Parece funcionar, tirando os problemas no ataque e da primeria divisao ser nula, que não percebo o porque de acontecer ambos
* */
public class ModoAutomatico extends ModosAutomaticos {

    public ModoAutomatico(TurnoToCruz turnoTo, TurnoInimigo turnoInimigo) {
        super(turnoTo, turnoInimigo);
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
