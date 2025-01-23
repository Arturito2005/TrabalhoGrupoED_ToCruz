package Interfaces.Cenarios;

import Items.Item;
import Mapa.Divisao;

public interface CenariosToCruzInterface {

    public void usarItemDivisao(Item item, Divisao divisao);

    public void guardarKitMochila(Item item, Divisao divisao);

    public void moverToCruz(Divisao divisaoAtual, Divisao novaDivisao);

    public void sugestaoCaminhoToCruzKitEAlvo(Divisao div_to);

    public Divisao andar(Divisao divisao_atual);

}
