package Turnos;

import Cenarios.Cenarios;
import Interfaces.Turno.TurnoInt;
import Jogo.Simulacao;
import Mapa.Divisao;

public abstract class Turno implements TurnoInt {

    protected Simulacao simulacao;

    protected Cenarios cenario;

    public Turno(Cenarios cenario, Simulacao simulacao) {
        this.cenario = cenario;
        this.simulacao = simulacao;
    }

    public abstract void turno(Divisao divisao);
}
