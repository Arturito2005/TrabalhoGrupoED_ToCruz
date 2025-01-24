package Cenarios.Divisao;

import Cenarios.Cenarios;
import Exceptions.ElementNotFoundException;
import Interfaces.Cenarios.CenariosDivisaoInterface;
import Jogo.Simulacao;
import Mapa.Divisao;

import java.util.Iterator;

public class CenariosDivisao extends Cenarios implements CenariosDivisaoInterface {

    public CenariosDivisao(Simulacao simulacao) {
        super(simulacao);
    }

    private boolean validDivisiao(Divisao divisao) {
        boolean valid = false;
        Simulacao simulacao = getSimulacao();

        Iterator<Divisao> divItr = simulacao.getEdificio().IteratorMapa();

        while (divItr.hasNext() && !valid) {
            Divisao div = divItr.next();

            if (div.equals(divisao)) {
                valid = true;
            }
        }

        return valid;
    }

    /**
     * Verifica se o To Cruz está numa saída.
     *
     * @return {@code true} se o To Cruz estiver na saída, {@code false} caso contrário.
     */
    @Override
    public boolean isToCruzInExit(Divisao divisao) throws ElementNotFoundException {
        if (!validDivisiao(divisao)) {
            throw new ElementNotFoundException("A divisao introduzida não existe!");
        }

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
    public boolean isToCruzInDivisaoAlvo(Divisao divisao) throws ElementNotFoundException {
        if (!validDivisiao(divisao)) {
            throw new ElementNotFoundException("A divisao introduzida não existe!");
        }

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
    public boolean haveConfronto(Divisao divisao) throws ElementNotFoundException {
        if (!validDivisiao(divisao)) {
            throw new ElementNotFoundException("A divisao introduzida não existe!");
        }

        boolean confronto = false;

        if (divisao.getToCruz() != null && !divisao.getInimigos().isEmpty()) {
            confronto = true;
        }

        return confronto;
    }


}
