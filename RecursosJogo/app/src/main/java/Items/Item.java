package Items;

import java.util.Objects;

/**
 * Classe abstrata que representa um item no jogo.
 *
 * @author Artur
 * @version 1.0
 */
public abstract class Item {

    /**
     * Contador estático para gerar um ID único para cada item.
     */
    private static int ID_ITEM_CONT = 0;

    /**
     * Identificador único do item.
     */
    private int id_item;

    /**
     * Indica se o item foi coletado.
     */
    private boolean collected;

    /**
     * Construtor padrão da classe {@link Item}.
     * */
    public Item() {
        this.id_item = ID_ITEM_CONT;
        this.collected = false;
    }

    /**
     * Retorna se o item já foi coletado ou não.
     *
     * @return true se o item foi coletado, caso contrário false.
     */
    public boolean isCollected() {
        return collected;
    }

    /**
     * Altera o status do item.
     *
     * @param collected true se o item for coletado, caso contrário false.
     */
    public void setCollected(boolean collected) {
        this.collected = collected;
    }

    /**
     * Retorna uma String com os dados do Item.
     *
     * @return uma string representando o item.
     */
    @Override
    public String toString() {
        return "Item{" +
                "id_item=" + id_item +
                ", collected=" + collected +
                '}';
    }

    /**
     * Compara este item com outro objeto para verificar se são iguais.
     * A comparação é baseada no identificador único do item.
     *
     * @param o o objeto a ser comparado.
     * @return true se os itens forem iguais (mesmo ID), caso contrário false.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Item item = (Item) o;

        return id_item == item.id_item;
    }

    /**
     * Retorna o código hash do item, baseado no seu identificador único.
     *
     * @return o código hash do item.
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(id_item);
    }
}
