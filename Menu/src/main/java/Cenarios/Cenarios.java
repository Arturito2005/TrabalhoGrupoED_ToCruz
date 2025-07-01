package Cenarios;

import Jogo.Simulacao;

public abstract class Cenarios  {

    private Simulacao simulacao;

    public Cenarios(Simulacao simulacao) {
        this.simulacao = simulacao;
    }

    public Simulacao getSimulacao() {
        return simulacao;
    }
}
