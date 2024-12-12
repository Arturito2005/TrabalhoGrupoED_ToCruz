package Items;

/**
 * Classe que representa um item de cura no jogo.
 *
 * @author Artur Pinto
 * Nº mecanográfico: 8230138
 *
 * @author Francisco Oliveria
 * Nº mecanografico: 8230148
 *
 * @version 1.0
 */
public class ItemCura extends Item {

    /**
     * O tipo do item de cura (kit de vida ou colete).
     */
    private TypeItemCura type;

    /**
     * A quantidade de vida que o item de cura recupera.
     */
    private long vida_recuperada;

    /**
     * Construtor para criar um {@link ItemCura} com o tipo de item e a
     * quantidade de vida a ser recuperada.
     *
     * @param type O tipo do item de cura.
     * @param vida_recuperada A quantidade de vida a ser recuperada ao usar o item.
     */
    public ItemCura(TypeItemCura type, long vida_recuperada) {
        super();
        this.type = type;
        this.vida_recuperada = vida_recuperada;
    }

    /**
     * Construtor para criar um {@link ItemCura} com ID, status de coleta,
     * tipo de item e quantidade de vida recuperada.
     *
     * @param id_item         O identificador único do item.
     * @param colleceted      Indica se o item foi coletado.
     * @param type            O tipo do item de cura.
     * @param vida_recuperada A quantidade de vida a ser recuperada ao usar o item.
     */
    public ItemCura(int id_item, boolean colleceted, TypeItemCura type, long vida_recuperada) {
        super(id_item, colleceted);
        this.type = type;
        this.vida_recuperada = vida_recuperada;
    }

    /**
     * Retorna o tipo de item de cura.
     *
     * @return o tipo do item de cura.
     */
    public TypeItemCura getType() {
        return type;
    }

    /**
     * Retorna a quantidade de vida que o item de cura pode recuperar.
     *
     * @return a quantidade de vida recuperada.
     */
    public long getVida_recuperada() {
        return vida_recuperada;
    }

    /**
     * Retorna uma string com os dados do {@link ItemCura}, incluindo o tipo do item de cura
     * e a quantidade de vida recuperada.
     *
     * @return uma string representando o item de cura.
     */
    @Override
    public String toString() {
        return super.toString() + "ItemCura{" +
                "type=" + type +
                ", vida_recuperada=" + vida_recuperada +
                '}';
    }

    /**
     * Compara este item de cura com outro objeto para verificar se são iguais.
     * A comparação é baseada no tipo do item de cura e na quantidade de vida recuperada.
     *
     * @param o o objeto a ser comparado.
     * @return true se os itens de cura forem iguais (mesmo tipo e mesma quantidade de vida recuperada),
     *         caso contrário false.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()){
            return false;
        }

        if (!super.equals(o)) {
            return false;
        }

        ItemCura itemCura = (ItemCura) o;
        return Double.compare(vida_recuperada, itemCura.vida_recuperada) == 0 && type == itemCura.type;
    }
}
