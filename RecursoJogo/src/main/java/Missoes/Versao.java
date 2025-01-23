package Missoes;

import Interfaces.MissoesInt.VersaoInterface;
import Interfaces.OrderedListADT;
import Jogo.Simulacao;
import LinkedList.LinearLinkedOrderedList;
import Mapa.Edificio;

/*
* Talvez seja preciso meter deepCopy
* */
public class Versao implements VersaoInterface {

    /**
     * Versão da missão. Criar uma classe para ela e meter lá o edificio e as simulaçoes
     */
    private long num_versao;

    /**
     * Edifício associado à missão (Vai passar para a classe versao).
     */
    private Edificio edificio;

    /**
     * Total de simulações realizadas nesta missão. (Vai passar para a classe versao)
     */
    private int tot_simulacoes;

    /**
     * Lista ordenada de simulações realizadas (Vai passar para a classe versao).
     */
    private OrderedListADT<Simulacao> simulacoes;

    public Versao() {
        this.num_versao = 0;
        this.edificio = new Edificio();
        this.tot_simulacoes = 0;
        this.simulacoes = new LinearLinkedOrderedList<>();
    }

    public Versao(long num_versao, Edificio edificio) {
        this.num_versao = num_versao;
        this.edificio = edificio;
        this.tot_simulacoes = 0;
        this.simulacoes = new LinearLinkedOrderedList<>();
    }

    public long getNum_versao() {
        return num_versao;
    }

    public Edificio getEdificio() {
        return edificio;
    }

    public int getTot_simulacoes() {
        return tot_simulacoes;
    }

    public OrderedListADT<Simulacao> getSimulacoes() {
        return simulacoes;
    }

    @Override
    public String toString() {
        return "Versao{" +
                "num_versao=" + num_versao +
                ", edificio=" + edificio +
                ", tot_simulacoes=" + tot_simulacoes +
                ", simulacoes=" + simulacoes +
                '}';
    }

    @Override
    public void addSimulacao(Simulacao jogo) {
        this.simulacoes.add(jogo);
        this.num_versao++;
    }
}
