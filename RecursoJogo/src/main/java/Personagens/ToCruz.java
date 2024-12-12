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
 * @author Artur Pinto
 * Nº mecanográfico: 8230138
 *
 * @author Francisco Oliveria
 * Nº mecanografico: 8230148
 *
 * @version 1.0
 */
public class ToCruz extends Personagem implements ToCruzIt {

    /** Mochila para armazenar itens de cura.*/
    private StackADT<ItemCura> mochila;

    /** Indica se o objetivo principal foi coletado. */
    private boolean colectedAlvo;
    /**
     * Construtor padrão que inicializa o personagem ToCruz sem uma divisão inicial.
     */
    public ToCruz() {
        super("ToCruz");
        this.mochila = new LinkedStack<>();
        this.colectedAlvo = false;
    }

    /**
     * Construtor alternativo para realizar uma deepCopy do ToCruz.
     *
     * @param id_personagem ID do personagem.
     * @param nome Nome do personagem.
     * @param vida Vida inicial do personagem.
     * @param poder Poder inicial do personagem.
     * @param mochila Mochila contendo itens de cura.
     */
    public ToCruz(int id_personagem, String nome, long vida, long poder, StackADT<ItemCura> mochila) {
        super(id_personagem, nome, vida, poder);
        this.mochila = new LinkedStack<>();
        this.colectedAlvo = false;
    }

    /**
     * Verifica se o alvo foi coletado, pelo o To Cruz.
     *
     * @return {@code true} se o alvo foi coletado; caso contrário, {@code false}.
     */
    public boolean isColectedAlvo() {
        return colectedAlvo;
    }

    /**
     * Define o status de coleta do alvo.
     *
     * @param colectedAlvo {@code true} para indicar que o alvo foi coletado, {@code false} caso contrário.
     */
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
     * Verifica se a mochila está cheia.
     *
     * @return {@code true} se a mochila estiver cheia; caso contrário, {@code false}.
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

        long cura = item.getVida_recuperada();
        long nova_vida = 0;

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
        System.out.println("To Cruz curou-se e ficou com: " + this.getVida() + " HP");
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
            System.out.println("O To Cruz guardou o kit de vida com " + item.getVida_recuperada() + " HP com sucesso");
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
                System.out.println("O To Cruz utilizou um kit de vida com " + kit.getVida_recuperada() + " HP");
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
        return "ToCruz{" + "mochila=" + mochila + '}';
    }

    /**
     * Compara este objeto com outro para verificar igualdade.
     *
     * @param o O objeto a ser comparado.
     * @return {@code true} se os objetos forem iguais; caso contrário, {@code false}.
     */
    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    /**
     * Retorna o código hash para este objeto.
     *
     * @return O código hash calculado.
     */
    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
