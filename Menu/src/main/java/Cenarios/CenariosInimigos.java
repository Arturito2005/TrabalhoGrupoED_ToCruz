package Cenarios;

import Exceptions.InvalidPersonagemException;
import Interfaces.StackADT;
import Mapa.Divisao;
import Mapa.Edificio;
import Personagens.Inimigo;
import Personagens.Personagem;
import Personagens.ToCruz;
import Stacks.LinkedStack;

import java.util.Iterator;
import java.util.Random;

public class CenariosInimigos extends Cenarios {

    /**
     * Metodo que calcula o poder total final de uma divisão, somando o poder inicial
     * fornecido com o poder dos inimigos presentes na divisão.
     * <p>
     * Caso a divisão não tenha inimigos ou contenha apenas um, o poder inicial é retornado.
     * Caso existam vários inimigos, os seus poderes são somados ao poder inicial.
     *
     * @param div           A divisão cuja força total será calculada.
     * @param poder_inimigo O poder inicial base dos inimigos.
     * @return O poder total calculado da divisão.
     */
    private double poderFinalDivisao(Divisao div, long poder_inimigo) {
        long poder_total = poder_inimigo;
        if (div.getInimigos().isEmpty() && div.getInimigos().size() > 1) {
            for (Inimigo inimigo_div : div.getInimigos()) {
                poder_total += inimigo_div.getPoder();
            }
        }

        return poder_total;
    }

    public Divisao mover(Personagem personagem, Divisao divisao_atual) {
        if (!(personagem instanceof Inimigo)) {
            throw new InvalidPersonagemException("O Personagem a receber como paramentro tem de ser um inimigo");
        }

        Inimigo inimigo = (Inimigo) personagem;
        Edificio edificio = this.getSimulacao().getEdificio();
        Random randomizer = new Random();
        int numMoves = randomizer.nextInt(3);
        long poder_inimigo = personagem.getPoder();

        for (int i = 0; i < numMoves; i++) {
            edificio.updateWeight(divisao_atual, poderFinalDivisao(divisao_atual, poder_inimigo) - poder_inimigo);
            StackADT<Divisao> stDiv = new LinkedStack<>();
            Divisao divisaoEscolhida;
            Iterator<Divisao> itrDiv = edificio.getNextDivisoes(divisao_atual);

            while (itrDiv.hasNext()) {
                stDiv.push(itrDiv.next());
            }

            int rand = randomizer.nextInt(stDiv.size());

            while (stDiv.size() - 1 > rand && !stDiv.isEmpty()) {
                stDiv.pop();
            }

            divisaoEscolhida = stDiv.peek();
            divisao_atual.removeInimigo(inimigo);

            divisaoEscolhida.addInimigo(inimigo);
            edificio.updateWeight(divisaoEscolhida, poderFinalDivisao(divisaoEscolhida, poder_inimigo));
            divisao_atual = divisaoEscolhida;
        }

        System.out.println("O inimigo " + inimigo.getNome() + " moveu-se para a sala: " + divisao_atual.getName());

        return divisao_atual;
    }

    @Override
    public void ataque(Personagem personagem, Divisao divisao_atual) {
        if(!(personagem instanceof Inimigo)) {
            throw new InvalidPersonagemException("O Personagem a receber como paramentro tem de ser um inimigo");
        }

        Inimigo inimigo = (Inimigo) personagem;
        ToCruz toCruz = getSimulacao().getToCruz();
        long newvidaTo = toCruz.getVida() - inimigo.getPoder();

        toCruz.setVida(newvidaTo);

        System.out.println("O imimigo " + inimigo.getNome() + " atacou o To Cruz");

        if (toCruz.isDead()) {
            System.out.println("O inimigo " + inimigo.getNome() + " matou o To Cruz");
            toCruz.setVida(0);
        } else {
            System.out.println("To Cruz resiste ao ataque e fica com " + newvidaTo + " HP");
        }
    }
}
