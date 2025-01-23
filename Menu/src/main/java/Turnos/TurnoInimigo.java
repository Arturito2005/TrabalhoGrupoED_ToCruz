package Turnos;

import Cenarios.Cenarios;
import Interfaces.UnorderedListADT;
import Jogo.Simulacao;
import LinkedList.LinearLinkedUnorderedList;
import Mapa.Divisao;
import Personagens.Inimigo;

/*
* Feito, falta testar
* */
public class TurnoInimigo extends Turno {

    public TurnoInimigo(Cenarios cenario, Simulacao simulacao) {
        super(cenario, simulacao);
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

        for (Inimigo inimigo : divisao_atual.getInimigos()) {
            if (!divisao_atual.haveConfronto()) {
                System.out.println("O inimigo " + inimigo.getNome() + " esta na sala " + divisao_atual.getName());
                inimigos_move.addToRear(inimigo);
            } else {
                cenario.ataque(inimigo, simulacao.getToCruz(), divisao_atual);
            }
        }

        for (Inimigo inimigo : inimigos_move) {
            Divisao div = cenario.getSimulacao().moverInimigo(divisao_atual, inimigo);
            if (div.haveConfronto()) {
                cenario.ataque(inimigo, simulacao.getToCruz(), divisao_atual);
            }
        }
    }
}
