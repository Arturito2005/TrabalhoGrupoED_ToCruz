package Cenarios;

import Interfaces.Cenarios.CenariosInterface;
import Jogo.Simulacao;
import Mapa.Divisao;
import Personagens.Inimigo;
import Personagens.Personagem;

/*
* Ver se não seria melhor antes mandar a simulação ou a versão por enquanto está só o edificio
* */
public abstract class Cenarios implements CenariosInterface {

    private Simulacao simulacao;

    public Cenarios(Simulacao simulacao) {
        this.simulacao = simulacao;
    }

    public Simulacao getSimulacao() {
        return simulacao;
    }

    public void ataque(Personagem atacante, Personagem atacado, Divisao divisaoConfronto) throws IllegalArgumentException {
        if(atacante.getVida() <= 0 || atacado.getVida() <= 0){
            throw new IllegalArgumentException("Um dos personagens já se encontra morto!");
        }

        System.out.println("Turno do" + atacante.getNome() + " atacar!");
        atacado.setVida(atacado.getVida() - atacante.getPoder());

        if (atacado.isDead()) {
            System.out.println("O" + atacante.getNome() + " matou o" + atacado.getNome());
            atacado.setVida(0);

            if(atacado instanceof Inimigo) {
                simulacao.inimigoDead((Inimigo) atacado, divisaoConfronto);
            }
        } else {
            System.out.println("O " + atacado.getNome() + " resitiu ao ataque do " + atacante.getNome() + " e ficou com: " + atacado.getVida() + " HP");
        }
    }
}
