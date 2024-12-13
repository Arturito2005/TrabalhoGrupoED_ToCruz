package Items;

import java.util.Objects;

/**
 * Classe abstrata que representa um item no jogo.
 *
 * @author Artur Pinto
 * Nº mecanográfico: 8230138
 * @author Francisco Oliveira
 * Nº mecanografico: 8230148
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
     * Inicializa o identificador único com o valor do contador estático
     * e define o status de coleta como falso.
     */
    public Item() {
        this.id_item = ID_ITEM_CONT;
        this.collected = false;
    }

    /**
     * Construtor da classe {@link Item} com parâmetros.
     *
     * @param id_item   O identificador único do item.
     * @param collected Indica se o item foi coletado.
     */
    public Item(int id_item, boolean collected) {
        this.id_item = id_item;
        this.collected = collected;
    }

    /**
     * Retorna o identificador único do item.
     *
     * @return O ID do item.
     */
    public int getId_item() {
        return id_item;
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
