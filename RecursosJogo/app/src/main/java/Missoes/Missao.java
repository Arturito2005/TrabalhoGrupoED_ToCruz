package Missoes;

import Items.Item;
import LinkedList.LinearLinkedUnorderedList;
import Mapa.Alvo;
import Mapa.Divisao;
import Mapa.Edificio;
import Personagens.Inimigo;
import Personagens.ToCruz;

public class Missao {

    private String cod_missao;

    private int versao;

    private Edificio edificio;

    private Alvo alvo;

    private LinearLinkedUnorderedList<Item> item;

    private LinearLinkedUnorderedList<Inimigo> inimigos;

    private LinearLinkedUnorderedList<Divisao> entrada_saida;

    private ToCruz toCruz;

    public Missao(String cod_missao, int versao, Edificio edificio, Alvo alvo, LinearLinkedUnorderedList<Item> item, LinearLinkedUnorderedList<Inimigo> inimigos, LinearLinkedUnorderedList<Divisao> entrada_saida, ToCruz toCruz) {
        this.cod_missao = cod_missao;
        this.versao = versao;
        this.edificio = edificio;
        this.alvo = alvo;
        this.item = item;
        this.inimigos = inimigos;
        this.entrada_saida = entrada_saida;
        this.toCruz = toCruz;
    }

    public Missao() {
        this.cod_missao = null;
        this.versao = 0;
        this.edificio = new Edificio();
        this.alvo = new Alvo();
        this.item = new LinearLinkedUnorderedList<>();
        this.inimigos = new LinearLinkedUnorderedList<>();
        this.entrada_saida = new LinearLinkedUnorderedList<>();
        this.toCruz = new ToCruz();
    }

    public String getCod_missao() {
        return cod_missao;
    }

    public int getVersao() {
        return versao;
    }

    public Edificio getEdificio() {
        return edificio;
    }

    public Alvo getAlvo() {
        return alvo;
    }

    public LinearLinkedUnorderedList<Item> getItem() {
        return item;
    }

    public LinearLinkedUnorderedList<Inimigo> getInimigos() {
        return inimigos;
    }

    public LinearLinkedUnorderedList<Divisao> getEntrada_saida() {
        return entrada_saida;
    }

    public ToCruz getToCruz() {
        return toCruz;
    }

    @Override
    public String toString() {
        return "Missao{" +
                "cod_missao='" + cod_missao + '\'' +
                ", versao=" + versao +
                ", edificio=" + edificio.toString() +
                ", alvo=" + alvo.toString() +
                ", item=" + item.toString() +
                ", inimigos=" + inimigos.toString() +
                ", entrada_saida=" + entrada_saida.toString() +
                ", toCruz=" + toCruz.toString() +
                '}';
    }
}
