package Mapa;

import Exceptions.EmptyCollectionException;
import Exceptions.WrongTypeItem;
import Interfaces.DivisaoCondition;
import Interfaces.IteracoesInimigo;
import Interfaces.IteracoesToCruz;
import Interfaces.UnorderedListADT;
import Items.ItemCura;
import LinkedList.LinearLinkedUnorderedList;
import Personagens.Inimigo;
import Personagens.ToCruz;

import java.util.Iterator;
import java.util.Objects;


public class Divisao implements Comparable, IteracoesInimigo, IteracoesToCruz, DivisaoCondition {

    private static int ID_DIVISAO_CONT = 0;

    private int id_divisao;

    private String name;

    private boolean entrada_saida;

    private ToCruz toCruz;

    private LinearLinkedUnorderedList<Inimigo> inimigos;

    // private LinearLinkedUnorderedList<Inimigo> inimigos_dead;

    private ItemCura itemCura;

    private Alvo alvo;

    public Divisao(String name, boolean entradas_saidas, Alvo alvo, ItemCura itemCura, LinearLinkedUnorderedList<Inimigo> inimigos) {
        this.id_divisao = ID_DIVISAO_CONT++;
        this.name = name;
        this.entrada_saida = entradas_saidas;
        this.alvo = alvo;
        this.itemCura = itemCura;
        this.inimigos = inimigos;
        //this.inimigos_dead = new LinearLinkedUnorderedList<>();
        this.toCruz = new ToCruz();
    }

    public Divisao(String name, boolean entradas_saidas) {
        this.id_divisao = ID_DIVISAO_CONT++;
        this.name = name;
        this.entrada_saida = entradas_saidas;
        this.alvo = null;
        this.itemCura = null;
        this.inimigos = new LinearLinkedUnorderedList<>();
        //this.inimigos_dead = new LinearLinkedUnorderedList<>();
        this.toCruz = null;
    }

    public Divisao(String name) {
        this.id_divisao = ID_DIVISAO_CONT++;
        this.name = name;
        this.entrada_saida = false;
        this.alvo = null;
        this.itemCura = null;
        this.inimigos = new LinearLinkedUnorderedList<>();
        this.toCruz = null;
    }

    public Divisao() {
        this.id_divisao = ID_DIVISAO_CONT++;
        this.name = "";
        this.entrada_saida = false;
        this.alvo = null;
        this.itemCura = null;
        this.inimigos = new LinearLinkedUnorderedList<>();
        //this.inimigos_dead = new LinearLinkedUnorderedList<>();
        this.toCruz = null;
    }

    public String getName() {
        return name;
    }

    public boolean isEntrada_saida() {
        return entrada_saida;
    }

    public void setEntrada_saida(boolean entrada_saida) {
        this.entrada_saida = entrada_saida;
    }

    public ToCruz getToCruz() {
        return toCruz;
    }

    public void removeToCruz() {
        this.toCruz = null;
    }

    public void addToCruz(ToCruz toCruz) {
        this.toCruz = toCruz;
    }

    public LinearLinkedUnorderedList<Inimigo> getInimigos() {
        return inimigos;
    }

    public void addInimigo(Inimigo inimigo) {
        this.inimigos.addToRear(inimigo);
    }

    public Inimigo removeFirstInimigo() throws EmptyCollectionException {
        if (this.inimigos.isEmpty()) {
            throw new EmptyCollectionException("A divisao não tem inimigos!");
        }

        return this.inimigos.removeFirst();
    }

    public Inimigo removeInimigo(Inimigo inimigos) {
        return this.inimigos.remove(inimigos);
    }

    public ItemCura getItemCura() {
        return itemCura;
    }

    public void setItemCura(ItemCura itemCura) {
        this.itemCura = itemCura;
    }

    public Alvo getAlvo() {
        return alvo;
    }

    public void setAlvo(Alvo alvo) {
        this.alvo = alvo;
    }

    @Override
    public boolean isToCruzInExit() {
        boolean is = false;

        if (toCruz != null && entrada_saida) {
            is = true;
        }

        return is;
    }

    @Override
    public boolean isToCruzInDivisaoAlvo() {
        boolean is = false;

        if (toCruz != null && alvo != null) {
            is = true;
        }

        return is;
    }

    @Override
    public boolean haveInimigo() {
        boolean have = false;

        if (!inimigos.isEmpty()) {
            have = true;
        }

        return have;
    }

    @Override
    public boolean haveConfronto() {
        boolean confronto = false;

        if (toCruz != null && !this.inimigos.isEmpty()) {
            confronto = true;
        }

        return confronto;
    }

    @Override
    public void attackInimigo(Inimigo inimigo) {
        double vidaTo = toCruz.getVida() - inimigo.getPoder();
        toCruz.setVida(vidaTo);

        System.out.println("O imimigo" + inimigo.getNome() + " atacou o To Cruz");

        if (toCruz.isDead()) {
            System.out.println("O inimigo " + inimigo.getNome() + "matou o To Cruz");
        } else {
            System.out.println("To Cruz resiste ao ataque e fica com " + vidaTo + " HP");
        }
    }

    @Override
    public void ToCruzGetAlvo() {
        this.alvo.setAtinigido(true);
        System.out.println("To Cruz está com o alvo, agora só falta sair do edificio com vida");
    }

    @Override
    public void attackToCruz() {
        Iterator<Inimigo> iterator = this.inimigos.iterator();
        UnorderedListADT<Inimigo> inimigosDead = new LinearLinkedUnorderedList<>();

        while (iterator.hasNext()) {
            Inimigo inimigo = iterator.next();
            inimigo.setVida(inimigo.getVida() - this.toCruz.getPoder());

            if (inimigo.isDead()) {
                System.out.println("O inimigo " + inimigo.getNome());
                inimigosDead.addToRear(inimigo);
            } else {
                System.out.println("Vida do inimigo " + inimigo.getNome() + ": " + inimigo.getVida());
            }
        }

        while (!inimigosDead.isEmpty()) {
            removeInimigo(inimigosDead.removeFirst());
        }
    }

    @Override
    public void usarItemDivisao() {
        if (!this.itemCura.isCollected()) {
            try {
                this.toCruz.usarItem(this.itemCura);
                this.itemCura.setCollected(true);
            } catch (NullPointerException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("O item desta divisao já foi usado anteriormente");
        }
    }

    @Override
    public int compareTo(Object o) {
        int compare = 0;
        if (this.id_divisao > ((Divisao) o).id_divisao) {
            compare = 1;
        } else if (this.id_divisao < ((Divisao) o).id_divisao) {
            compare = -1;
        }
        return compare;
    }

    @Override
    public String toString() {
        return "Divisao{" +
                "id_divisao=" + id_divisao +
                ", name='" + name + '\'' +
                ", entrada_saida=" + entrada_saida +
                ", toCruz=" + toCruz +
                ", inimigos=" + inimigos +
                ", itemCura=" + itemCura +
                ", alvo=" + alvo +
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

        if (this.id_divisao == ((Divisao) o).id_divisao) {
            return true;
        }

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}