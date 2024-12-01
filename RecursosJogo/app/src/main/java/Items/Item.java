package Items;

import Mapa.Divisao;

/*
* Tens os dados abstratos do Item
* */
public abstract class Item {

    private static int ID_ITEM_CONT = 0;

    private int id_item;

    private Divisao divisao;

    private boolean colected;

    public Item(Divisao divisao) {
        this.id_item = ID_ITEM_CONT++;
        this.divisao = divisao;
        this.colected = false;
    }

    public int getId_item() {
        return id_item;
    }

    public Divisao getDivisao() {
        return divisao;
    }

    public boolean isColected() {
        return colected;
    }

    public void setColected(boolean colected) {
        this.colected = colected;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id_item=" + id_item +
                ", divisao=" + divisao +
                '}';
    }
}
