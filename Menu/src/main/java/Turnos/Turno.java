package Turnos;

import Cenarios.Cenarios;
import Interfaces.Turno.TurnoInt;
import Mapa.Divisao;

public abstract class Turno implements TurnoInt {

    private Cenarios cenario;

    public Cenarios getCenario() {
        return cenario;
    }

    public Turno(Cenarios cenario) {
        this.cenario = cenario;
    }

    public abstract void turno(Divisao divisao);
}
