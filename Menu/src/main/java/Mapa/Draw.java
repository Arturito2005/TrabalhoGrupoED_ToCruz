package Mapa;

import Interfaces.Draw.DrawInterface;

public abstract class Draw implements DrawInterface {

    private Edificio edificio;

    public Draw(Edificio edificio) {
        this.edificio = edificio;
    }

    public Edificio getEdificio() {
        return edificio;
    }

    public abstract void draw();
}
