package Mapa;

import Items.ItemCura;
import Items.TypeItemCura;
import LinkedList.LinearLinkedUnorderedList;
import Personagens.Inimigo;

public class TestDraw {
    public static void main(String[] args) {
        LinearLinkedUnorderedList<Inimigo> inimigos = new LinearLinkedUnorderedList<>();
        Inimigo inimigo = new Inimigo("Bady1", 20);
        Inimigo inimigo2 = new Inimigo("Bady1", 20);
        inimigos.addToRear(inimigo);
        inimigos.addToRear(inimigo2);
        Divisao divisao = new Divisao("Sala", true, new Alvo("quimicos"), new ItemCura(TypeItemCura.KIT_VIDA, 20),inimigos );
        divisao.drawnDivisao();
    }
}
