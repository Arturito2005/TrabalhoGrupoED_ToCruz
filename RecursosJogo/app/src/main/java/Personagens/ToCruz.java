package Personagens;

import Exceptions.AllLifeException;
import Exceptions.EmptyCollectionException;
import Exceptions.UsedColectedItemException;
import Exceptions.WrongTypeItemException;
import Interfaces.StackADT;
import Interfaces.ToCruzIt;
import Items.ItemCura;
import Items.TypeItemCura;
import Stacks.LinkedStack;

/**
 * Classe que representa o personagem principal do jogo, ToCruz.
 *
 * @author Artur
 * @version 1.0
 */
public class ToCruz extends Personagem implements ToCruzIt {

    private StackADT<ItemCura> mochila; /** Mochila para armazenar itens de cura.*/

    private boolean colectedAlvo;
    /**
     * Construtor padrão que inicializa o personagem ToCruz sem uma divisão inicial.
     */
    public ToCruz() {
        super("ToCruz");
        mochila = new LinkedStack<>();
        colectedAlvo = false;
    }

    public boolean isColectedAlvo() {
        return colectedAlvo;
    }

    public void setColectedAlvo(boolean colectedAlvo) {
        this.colectedAlvo = colectedAlvo;
    }

    /**
     * Retorna a mochila do To Cruz.
     *
     * @return A Stack de itens de cura armazenados na mochila.
     */
    public StackADT<ItemCura> getMochila() {
        return mochila;
    }


    /**
     * Método publico que verifica se a mochila do To Cruz já está cheia
     */
    @Override
    public boolean mochilaIsFull() {
        boolean full = false;

        if (mochila.size() == 3) {
            full = true;
        }

        return full;
    }

    /**
     * Metodo para verificar se a mochila contém pelo menos um kit.
     *
     * @return true se a mochila contiver pelo menos um item, false se estiver vazia.
     */
    @Override
    public boolean mochilaTemKit() {
        boolean contem = true;

        if (mochila.isEmpty()) {
            contem = false;
        }

        return contem;
    }

    /**
     * Método privado que aplica a cura ao personagem utilizando um item de cura.
     * Atualiza a vida do personagem com base no tipo e na quantidade de cura fornecida pelo item.
     *
     * @param item O item de cura a ser utilizado.
     */
    private void curar(ItemCura item) throws AllLifeException {
        if (this.getVida() >= 100 && !item.getType().equals(TypeItemCura.COLETE)) {
            throw new AllLifeException("Não é possível usar o kit médico, porque a vida está cheia");
        }

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
        item.setCollected(true);
    }

    /**
     * Método para guardar um kit médico na mochila.
     * Caso a mochila esteja cheia, o personagem poderá usá-lo imediatamente se necessário.
     *
     * @param item O item de cura a ser guardado.
     * @return Uma mensagem indicando o estado da operação (guardado, usado ou descartado).
     * @throws NullPointerException Se o item for nulo.
     * @throws WrongTypeItemException        Se o tipo do item não for um kit de vida.
     */
    @Override
    public ItemCura guardarKit(ItemCura item) throws NullPointerException, WrongTypeItemException, AllLifeException, UsedColectedItemException {
        if (item == null) {
            throw new NullPointerException("O kit medico a guardar na mochila não pode ser null");
        }

        if (item.getType() != TypeItemCura.KIT_VIDA) {
            throw new WrongTypeItemException("O tipo de item de cura a guardar a mochila é do tipo Kit de vida");
        }

        if(item.isCollected()) {
            throw new UsedColectedItemException("O item a usar já foi utilizado anteriormente pelo o To Cruz");
        }

        if (this.mochila.size() == 3) {
            System.out.println("A mochila está cheia");

            try {
                curar(item);
            } catch (AllLifeException e) {
                System.out.println(e.getMessage());
            }
        } else {
            mochila.push(item);
            item.setCollected(true);
        }

        return item;
    }

    /**
     * Método para usar um kit médico da mochila.
     * Apenas possível se houver kits na mochila e se a vida não estiver cheia.
     *
     * @return Uma mensagem indicando o resultado da operação.
     */
    @Override
    public ItemCura usarKit() throws EmptyCollectionException {
        String temp = "";
        ItemCura kit;

        if (!this.mochila.isEmpty()) {
            if (this.getVida() == 100) {
                throw new AllLifeException("Não é possível usar o kit médico, porque a vida está cheia");
            } else {
                kit = mochila.pop();
                curar(kit);
            }
        } else {
            throw new EmptyCollectionException("O To Cruz está sem kit de vidas na mochila");
        }

        return kit;
    }

    /**
     * Método para usar um item.
     *
     * @param item O item do tipo colete a ser usado.
     * @return Uma mensagem indicando que o colete foi usado.
     * @throws NullPointerException Se o colete for nulo.
     * @throws WrongTypeItemException        Se o item não for do tipo colete.
     */
    @Override
    public ItemCura usarItem(ItemCura item) throws NullPointerException, UsedColectedItemException {
        if (item == null) {
            throw new NullPointerException("O item não pode ser nulo");
        }

        if(item.isCollected()) {
            throw new UsedColectedItemException("O item a usar já foi utilizado anteriormente pelo o To Cruz");
        }

        try {
            curar(item);
        } catch (AllLifeException e) {
            System.out.println(e.getMessage());
        }

        return item;
    }

    /**
     * Retorna uma string com todos os dados do ToCruz.
     *
     * @return Uma string com detalhes do personagem e sua mochila.
     */
    @Override
    public String toString() {
        return "ToCruz{" + "mochila=" + mochila.toString() + '}';
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
