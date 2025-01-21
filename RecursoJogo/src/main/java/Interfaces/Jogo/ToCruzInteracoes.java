package Interfaces.Jogo;

import Mapa.Divisao;
import Personagens.Inimigo;

public interface ToCruzInteracoes {

    public void usarItemDivisao();

    public void moverToCruz(Divisao divisaoAtual, Divisao novaDivisao);

    //Ver se há alguma forma de remover a divisao
    public void attackToCruz(Inimigo inimigo, Divisao divisao);

}
