package Interfaces;

import Exceptions.WrongTypeItem;
import Items.ItemCura;

public interface InteracoesToCruz {
    public String guardarKit(ItemCura item) throws NullPointerException, WrongTypeItem;

    public String usarKit();

    public String usarColete(ItemCura colete) throws NullPointerException, WrongTypeItem;
}
