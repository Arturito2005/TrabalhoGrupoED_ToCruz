package Turnos;

import Cenarios.Divisao.CenariosDivisao;
import Cenarios.Personagens.CenariosInimigos;
import Interfaces.UnorderedListADT;
import Jogo.Simulacao;
import LinkedList.LinearLinkedUnorderedList;
import Mapa.Divisao;
import Personagens.Inimigo;
import Personagens.ToCruz;

import java.util.Iterator;

/*
* Feito, falta testar
* */
public class TurnoInimigo extends Turno {

    public TurnoInimigo(CenariosInimigos cenarioInimigo, CenariosDivisao cenarioDivisao) {
        super(cenarioInimigo, cenarioDivisao);
    }

    /**
     * Realiza o turno de movimento dos inimigos na divisão especificada.
     * Para cada inimigo na divisão, se não houver confronto, o inimigo move-se para uma nova divisão.
     * Caso contrário, o inimigo ataca.
     *
     * @param divisao_atual A divisão onde os inimigos estão localizados e onde o turno será realizado.
     */
    @Override
    public void turno(Divisao divisao_atual) {
        UnorderedListADT<Inimigo> inimigos_move = new LinearLinkedUnorderedList<>();
        CenariosInimigos cenarioInimigo = (CenariosInimigos) this.getCenarioPersonagens();
        CenariosDivisao cenariosDivisao = this.getCenariosDivisao();
        Simulacao simulacao = cenarioInimigo.getSimulacao();
        ToCruz toCruz = simulacao.getToCruz();
        Iterator<Inimigo> itr_inimigos;
        boolean deadToCruz = false;

        itr_inimigos = divisao_atual.getInimigos().iterator();
        while (itr_inimigos.hasNext() && !deadToCruz) {
            Inimigo inimigo = itr_inimigos.next();

            if (!cenariosDivisao.haveConfronto(divisao_atual)) {
                System.out.println("O inimigo " + inimigo.getNome() + " esta na sala " + divisao_atual.getName());
                inimigos_move.addToRear(inimigo);
            } else {
                cenarioInimigo.ataque(inimigo, simulacao.getToCruz(), divisao_atual);
            }
        }

        itr_inimigos = inimigos_move.iterator();
        while (itr_inimigos.hasNext() && !deadToCruz) {
            Inimigo inimigo = itr_inimigos.next();
            Divisao div = cenarioInimigo.andar(inimigo, divisao_atual);
            if (cenariosDivisao.haveConfronto(div)) {
                cenarioInimigo.ataque(inimigo, simulacao.getToCruz(), divisao_atual);
            }
        }
    }
}
