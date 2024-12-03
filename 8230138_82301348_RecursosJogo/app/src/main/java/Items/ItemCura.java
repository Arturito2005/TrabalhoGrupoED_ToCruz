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
}
