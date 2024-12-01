package Interfaces;

import Items.Item;
import LinkedList.LinearLinkedUnorderedList;
import Mapa.Divisao;

public interface MissaoADT {

    public void getItem(Item item);

    public void iniciarConfronto();

    public void apanahrAlvo();

    public LinearLinkedUnorderedList<Divisao> getDivisoesVizinhas(Divisao divisao);
}
