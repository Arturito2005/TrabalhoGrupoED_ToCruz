package Items;

import Mapa.Divisao;

public class ItemCura extends Item {

    private TypeItemCura type;

    private double vida_recuperada;

    public ItemCura(Divisao divisao, TypeItemCura type, double vida_recuperada) {
        super(divisao);
        this.type = type;
        this.vida_recuperada = vida_recuperada;
    }

    public TypeItemCura getType() {
        return type;
    }

    public double getVida_recuperada() {
        return vida_recuperada;
    }

    @Override
    public String toString() {
        return super.toString() + "ItemCura{" +
                "type=" + type +
                ", vida_recuperada=" + vida_recuperada +
                '}';
    }

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
