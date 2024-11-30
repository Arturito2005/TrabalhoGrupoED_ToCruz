package Personagens;

import Interfaces.InteracoesToCruz;
import Items.ItemCura;
import Mapa.Divisao;

/**
 *
 * Falta o ToString
 */
public class ToCruz extends Personagem implements InteracoesToCruz {

    //LinkedStack<ItemCura> mochila;

    public ToCruz(Divisao divisao) {
        super("ToCruz", divisao);
        //mochila = new LinkedStack<ItemCura>;
    }

    public ToCruz() {
        super("ToCruz");
    }

    /*Neste caso não sei até que ponto este mudar de divisão não devia de
    * receber como parametro a divisao ou até o metodo do menu para mudar
    * de divisão vinha para aqui
    *
    * Tipo chamava-se o Metodo para mostar a proxima divisao do Ediifio
    * mostrava aqui as opções e o To Cruz selecionava a nova divisao
    * */
    @Override
    public void mudarDivisao() {

    }

    @Override
    public void guardarKit(ItemCura item) {

    }

    @Override
    public void usarKit() {

    }

    @Override
    public void usarColete(double colete) {

    }
}
