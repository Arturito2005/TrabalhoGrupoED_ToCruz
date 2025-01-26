package Interfaces.Cenarios;

import Items.Item;
import Mapa.Divisao;

public interface CenariosToCruzInterface {

    public void ataqueToCruz(Divisao divisao);

    public void moverToCruz(Divisao divisaoAtual, Divisao novaDivisao) throws NullPointerException;

    public void sugestaoCaminhoToCruzKitEAlvo(Divisao div_to);

    public Divisao andar(Divisao divisao_atual);

}
