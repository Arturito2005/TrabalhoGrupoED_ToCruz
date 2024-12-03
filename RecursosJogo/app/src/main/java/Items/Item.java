package Items;

import Mapa.Divisao;

import java.util.Objects;

/*
* Tens os dados abstratos do Item
* */
public abstract class Item {

    private static int ID_ITEM_CONT = 0;

    private int id_item;

    private Divisao divisao;


    public Item(Divisao divisao) {
        this.id_item = ID_ITEM_CONT++;
    }

    public int getId_item() {
        return id_item;
    }

    public Divisao getDivisao() {
        return divisao;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id_item=" + id_item +
                ", divisao=" + divisao +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Item item = (Item) o;

        return id_item == item.id_item || Objects.equals(divisao, item.divisao);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id_item);
    }
}
