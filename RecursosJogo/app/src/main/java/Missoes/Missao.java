package Missoes;

import Graph.GraphListaAdjacencia;
import Importar.ImportarMapa;
import Interfaces.MissaoADT;
import Items.Item;
import LinkedList.LinearLinkedUnorderedList;
import Mapa.Alvo;
import Mapa.Divisao;
import Mapa.Edificio;
import Personagens.Inimigo;
import Personagens.ToCruz;

import java.io.IOException;
import java.util.Objects;

public class Missao implements MissaoADT {

    private String cod_missao;

    private long versao;

    private Edificio edificio;

    private Alvo alvo;

    private LinearLinkedUnorderedList<Item> item;

    private LinearLinkedUnorderedList<Inimigo> inimigos;

    private LinearLinkedUnorderedList<Divisao> entrada_saida;

    private LinearLinkedUnorderedList<Divisao> trajeto_to;

    private ToCruz toCruz;

    public Missao(String cod_missao, long versao, Edificio edificio, Alvo alvo, LinearLinkedUnorderedList<Item> item, LinearLinkedUnorderedList<Inimigo> inimigos, LinearLinkedUnorderedList<Divisao> entrada_saida) {
        this.cod_missao = cod_missao;
        this.versao = versao;
        this.edificio = edificio;
        this.alvo = alvo;
        this.item = item;
        this.inimigos = inimigos;
        this.entrada_saida = entrada_saida;
        this.toCruz = new ToCruz();
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

    public long getVersao() {
        return versao;
    }

    public void setVersao(long versao) {
        this.versao = versao;
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

    /*
    * Retorna as divisoes vizinhas do Inimigo
    * */
    @Override
    public GraphListaAdjacencia<Divisao> getDivisoesVizinhasInimigo(Divisao divisao) {
        return null;
    }

    /*
    * Retorna as divisoes vizinhas do To
    * */
    @Override
    public LinearLinkedUnorderedList<Divisao> getDivisoesVizinhasTo(Divisao divisao) {
        return null;
    }

    /*
    * Adiciona um nova divisão ao percurso manual do To Cruz.
    * */
    @Override
    public void addDivisaoTrajeto(Divisao divisao) {

    }

    @Override
    public void modoAutomatico() {

    }

    //Ir buscar o menu dele ao projeto do menu
    @Override
    public void modoManual() {

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        if(!(o instanceof Missao)) {
            return false;
        }

        Missao missao = (Missao) o;
        return Objects.equals(cod_missao, missao.cod_missao);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(cod_missao);
    }
}
