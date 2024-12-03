package Personagens;

import Exceptions.WrongTypeItem;
import Interfaces.InteracoesToCruz;
import Items.ItemCura;
import Items.TypeItemCura;
import Mapa.Divisao;
import Stacks.LinkedStack;

import java.util.Objects;

/**
 * Classe que representa o personagem principal do jogo, ToCruz.
 *
 * @author Artur
 * @version 1.0
 */
public class ToCruz extends Personagem implements InteracoesToCruz {

    private LinkedStack<ItemCura> mochila; /** Mochila para armazenar itens de cura.*/

    /**
     * Construtor que inicializa o personagem ToCruz em uma divisão específica.
     *
     * @param divisao A divisão inicial onde o personagem estará localizado.
     */
    public ToCruz(Divisao divisao) {
        super("ToCruz", divisao);
        mochila = new LinkedStack<ItemCura>();
    }

    /**
     * Construtor padrão que inicializa o personagem ToCruz sem uma divisão inicial.
     */
    public ToCruz() {
        super("ToCruz");
        mochila = new LinkedStack<ItemCura>();
    }

    /**
     * Retorna a mochila do To Cruz.
     *
     * @return A Stack de itens de cura armazenados na mochila.
     */
    public LinkedStack<ItemCura> getMochila() {
        return mochila;
    }

    /**
     * Método publico que verifica se a mochila do To Cruz já está cheia
     */
    public boolean mochilaIsFull() {
        boolean full = false;

        if(mochila.size() == 3) {
            full = true;
        }

        return full;
    }

    /**
     * Método privado que aplica a cura ao personagem utilizando um item de cura.
     * Atualiza a vida do personagem com base no tipo e na quantidade de cura fornecida pelo item.
     *
     * @param item O item de cura a ser utilizado.
     */
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
    }

    /**
     * Método para guardar um kit médico na mochila.
     * Caso a mochila esteja cheia, o personagem poderá usá-lo imediatamente se necessário.
     *
     * @param item O item de cura a ser guardado.
     * @return Uma mensagem indicando o estado da operação (guardado, usado ou descartado).
     * @throws NullPointerException Se o item for nulo.
     * @throws WrongTypeItem Se o tipo do item não for um kit de vida.
     */
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

    /**
     * Método para usar um kit médico da mochila.
     * Apenas possível se houver kits na mochila e se a vida não estiver cheia.
     *
     * @return Uma mensagem indicando o resultado da operação.
     */
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

    /**
     * Metodo para verificar se a mochila contém pelo menos um kit.
     *
     *
     * @return true se a mochila contiver pelo menos um item, false se estiver vazia.
     */
    public boolean mochilaTemKit() {
        boolean contem = true;

        if(mochila.isEmpty()) {
            contem = false;
        }

        return contem;
    }

    /**
     * Método para usar um item.
     *
     * @param item O item do tipo colete a ser usado.
     * @return Uma mensagem indicando que o colete foi usado.
     * @throws NullPointerException Se o colete for nulo.
     * @throws WrongTypeItem Se o item não for do tipo colete.
     */
    @Override
    public String usarItem(ItemCura item) throws NullPointerException{
        if(item == null) {
            throw new NullPointerException("O item não pode ser nulo");
        }

        String temp = "Colete Usado";
        curar(item);
        return temp;
    }

    /**
     * Retorna uma string com todos os dados do ToCruz.
     *
     * @return Uma string com detalhes do personagem e sua mochila.
     */
    @Override
    public String toString() {
        return "ToCruz{" +
                "mochila=" + mochila.toString() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
