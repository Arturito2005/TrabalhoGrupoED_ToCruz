package Interfaces;

import Exceptions.WrongTypeItem;
import Items.ItemCura;

public interface InteracoesToCruz {
    public String guardarKit(ItemCura item) throws NullPointerException, WrongTypeItem;

    public String usarKit();

    public String usarItem(ItemCura colete) throws NullPointerException;
}
