package Jogo;

import Interfaces.Jogo.JogoInterface;
import Personagens.Inimigo;

public class Jogo implements JogoInterface {

    public boolean collectedAlvo;

    public ToCruz toCruz;

    public Jogo() {
        this.collectedAlvo = false;
        this.toCruz = new ToCruz();
    }


    @Override
    public void getAlvo() {

    }

    @Override
    public void relatorioMissao() {

    }

    @Override
    public void attackInimigo() {

    }

    @Override
    public void moverInimigo() {

    }

    @Override
    public void usarItemDivisao() {

    }

    @Override
    public void moverToCruz() {

    }

    @Override
    public void attackToCruz(Inimigo inimigo) {

    }
}
