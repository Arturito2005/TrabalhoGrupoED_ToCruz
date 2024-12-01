package Personagens;

import Exceptions.WrongTypeItem;
import Interfaces.InteracoesToCruz;
import Items.ItemCura;
import Items.TypeItemCura;
import Mapa.Divisao;
import Stacks.LinkedStack;
/**
 *
 * Testar metodos
 */
public class ToCruz extends Personagem implements InteracoesToCruz {

    private LinkedStack<ItemCura> mochila;

    public ToCruz(Divisao divisao) {
        super("ToCruz", divisao);
        mochila = new LinkedStack<ItemCura>();
    }

    public ToCruz() {
        super("ToCruz");
        mochila = new LinkedStack<ItemCura>();
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

    private void curar(ItemCura item) {
        double cura = item.getVida_recuperada();
        double nova_vida = 0;

        if (item.getType().equals(TypeItemCura.KIT_VIDA)) {
            if (this.getVida() + cura > 100) {
                nova_vida = 100;
            } else {
                nova_vida = this.getVida() + cura;
            }
        } else if (item.getType().equals(TypeItemCura.COLETE)) {
            nova_vida = this.getVida() + cura;
        }

        this.setVida(nova_vida);
        item.setColected(true);
    }

    //Testar
    @Override
    public String guardarKit(ItemCura item) throws NullPointerException, WrongTypeItem {
        if(item == null) {
            throw new NullPointerException("O kit medico a guardar na mochila não pode ser null");
        }

        if(item.getType() != TypeItemCura.KIT_VIDA) {
            throw new WrongTypeItem("O tipo de item de cura a guardar a mochila é do tipo Kit de vida");
        }

        String temp = "";
        if (this.mochila.size() == 3) {
            System.out.println("A mochila está cheia");

            if(this.getVida() < 100) {
               curar(item);
               temp = "Curado";
            } else {
                System.out.println("A sua vida está cheia, logo o item continuará na sala");
                temp = "Mochila cheia e vida cheia";
            }
        } else {
            mochila.push(item);
            temp = "Item Guardado";
        }

        return temp;
    }

    @Override
    public String usarKit() {
        String temp = "";
        if(!this.mochila.isEmpty()) {
            if(this.getVida() == 100) {
                System.out.println("Não é possível usar o kit médico, porque a vida está cheia");
                temp = "Não é possível usar o kit médico, porque a vida está cheia";
            } else {
                temp = "Curar";
                ItemCura kit = mochila.pop();
                curar(kit);
            }
        } else {
            System.out.println("Não existem kit medicos na mochila!");
            temp = "Sem kit medicos na mochila";
        }

        return temp;
    }

    @Override
    public String usarColete(ItemCura colete) throws NullPointerException, WrongTypeItem{
        if(colete == null) {
            throw new NullPointerException("O colete não pode ser nulo");
        }

        if(colete.getType() != TypeItemCura.COLETE) {
            throw new WrongTypeItem("O tipo do item tem de ser colete");
        }

        String temp = "Colete Usado";
        curar(colete);
        return temp;
    }

    @Override
    public String toString() {
        return "ToCruz{" +
                "mochila=" + mochila.toString() +
                '}';
    }
}
