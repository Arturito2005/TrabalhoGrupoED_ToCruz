package Mapa;

import Interfaces.UnorderedListADT;
import Items.Item;
import Items.ItemCura;
import Personagens.Inimigo;
import Personagens.ToCruz;

import java.util.Iterator;

public class DrawMap extends Draw {

    public DrawMap(Edificio edificio) {
        super(edificio);
    }

    /**
     * Desenha o mapa do edifício, exibindo as suas divisões e conexões.
     */
    @Override
    public void draw() {
        Edificio edificio = getEdificio();
        Iterator<Divisao> itrDiv = edificio.IteratorMapa();

        while (itrDiv.hasNext()) {
            Divisao div = itrDiv.next();
            System.out.println("Lista de Divisoes vizinhas a divisao: " + div.getName());
            System.out.println();

            Iterator<Divisao> itr_adj = edificio.IteratorMapa();

            while (itr_adj.hasNext()) {
                Divisao div_adj = itr_adj.next();
                System.out.println(drawnDivisao(div_adj));
            }

            System.out.println();
        }
    }

    /**
     * Desenha a representação visual da divisão na consola.
     *
     * @return Uma string com o desenho da divisão
     */
    private String drawnDivisao(Divisao div) {

        String dados_sala = "";

        ToCruz toCruz = div.getToCruz();
        if (toCruz != null) {
            dados_sala += toCruz.getNome() + " ";
        }

        UnorderedListADT<Item> itemList = div.getItens();
        if (!itemList.isEmpty()) {
            for (Item item : itemList) {
                if (item instanceof ItemCura) {
                    dados_sala += ((ItemCura) item).getType() + " " + ((ItemCura) item).getVida_recuperada() + " HP ";
                }
            }
        }

        Alvo alvo = div.getAlvo();
        if (alvo != null) {
            dados_sala += alvo.getNome() + " ";
        }

        if (div.isEntrada_saida()) {
            dados_sala += "Saida" + " ";
        }

        long damage_total = 0;
        UnorderedListADT<Inimigo> inimigoList = div.getInimigos();
        if (!inimigoList.isEmpty()) {
            for (Inimigo inimigo : inimigoList) {
                dados_sala += inimigo.getNome() + " ";
                damage_total += inimigo.getPoder();
            }
        }

        int num_hifens;
        String sala = div.getName();
        String nome_sala = sala;
        if (damage_total > 0) {
            nome_sala += "  Custo: " + damage_total;
        }

        if (dados_sala.length() > sala.length()) {
            num_hifens = dados_sala.length();
        } else {
            num_hifens = nome_sala.length();
        }

        num_hifens = num_hifens + 7;
        String bordas = "";
        for (int i = 0; i < num_hifens; i++) {
            bordas = bordas + "-";
        }


        String nome_sala_central = "|" + centralizarTexto(nome_sala.trim(), bordas.length() - 2) + "|";
        String dados_sala_central = "|" + centralizarTexto(dados_sala.trim(), bordas.length() - 2) + "|";

        return bordas + "\n" +
                nome_sala_central + "\n" +
                dados_sala_central + "\n" +
                bordas;
    }

    /**
     * Centraliza um texto para exibição.
     *
     * @param texto   Texto a ser centralizado.
     * @param largura Largura total disponível.
     * @return Texto centralizado.
     */
    private String centralizarTexto(String texto, int largura) {
        int espacosTotal = largura - texto.length();
        int espacosEsquerda = espacosTotal / 2;
        int espacosDireita = espacosTotal - espacosEsquerda;
        String temp = "";

        for (int i = 0; i < espacosEsquerda; i++) {
            temp += " ";
        }

        temp += texto;

        for (int i = 0; i < espacosDireita; i++) {
            temp += " ";
        }
        return temp;
    }
}
