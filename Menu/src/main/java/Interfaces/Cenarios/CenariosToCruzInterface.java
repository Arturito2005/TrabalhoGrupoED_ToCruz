package Interfaces.Cenarios;

import Exceptions.InvalidOptionException;
import Exceptions.InvalidTypeItemException;
import Items.Item;
import Mapa.Divisao;
import Personagens.ToCruz;

public interface CenariosToCruzInterface {

    public void ataqueToCruz(Divisao divisao);

    public void collectAlvo();

    public void usarItemDivisao(Item item, Divisao divisao);

    public void guardarKitMochila(Item item, Divisao divisao);

    public void moverToCruz(Divisao divisaoAtual, Divisao novaDivisao);

    public void sugestaoCaminhoToCruzKitEAlvo(Divisao div_to);

    public void DivisaoComItem(Divisao divisao, ToCruz toCruz) throws InvalidOptionException, InvalidTypeItemException;

    public Divisao andar(Divisao divisao_atual);

}
