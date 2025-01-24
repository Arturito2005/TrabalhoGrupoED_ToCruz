package Cenarios;

import Jogo.Simulacao;

/*
* Ver se não seria melhor antes mandar a simulação ou a versão por enquanto está só o edificio
* */
public abstract class Cenarios  {

    private Simulacao simulacao;

    public Cenarios(Simulacao simulacao) {
        this.simulacao = simulacao;
    }

    public Simulacao getSimulacao() {
        return simulacao;
    }
}
