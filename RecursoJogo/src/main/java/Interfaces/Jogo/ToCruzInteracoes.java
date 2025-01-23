package Interfaces.Jogo;

import Items.Item;
import Mapa.Divisao;
import Personagens.Inimigo;

public interface ToCruzInteracoes {

    public void moverToCruz(Divisao divisaoAtual, Divisao novaDivisao);

    //Ver se há alguma forma de remover a divisao

    public void usarItemDivisao(Item item, Divisao divisao);

    public void guardarItemMochila(Item item, Divisao divisao);

    public void getAlvo();
}
