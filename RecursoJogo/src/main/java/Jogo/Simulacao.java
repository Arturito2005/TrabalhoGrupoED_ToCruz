package Jogo;

import Interfaces.Simulacao.SimulacaoInterface;
import Interfaces.QueueADT;
import Interfaces.StackADT;
import Interfaces.UnorderedListADT;
import Items.Item;
import Items.ItemCura;
import LinkedList.LinearLinkedUnorderedList;
import Mapa.Divisao;
import Mapa.Edificio;
import Personagens.Inimigo;
import Personagens.ToCruz;
import Queue.LinkedQueue;
import java.util.Objects;

public class Simulacao implements SimulacaoInterface, Comparable<Simulacao> {

    private boolean collectedAlvo;

    private ToCruz toCruz;

    private UnorderedListADT<Inimigo> inimigosDead;

    private UnorderedListADT<Item> collectedItem;

    private QueueADT<Divisao> percursoToCruz;

    private Edificio edificio;

    private double vidaFinalTo;

    private int versao;

    public Simulacao(Edificio edificio, int versao) {
        this.collectedAlvo = false;
        this.toCruz = new ToCruz();
        this.inimigosDead = new LinearLinkedUnorderedList<>();
        this.collectedItem = new LinearLinkedUnorderedList<>();
        this.percursoToCruz = new LinkedQueue<>();
        this.edificio = edificio;
        this.vidaFinalTo = toCruz.getVida();
        this.versao = versao;
    }

    /*Getters*/
    public int getVersao() {
        return versao;
    }

    public UnorderedListADT<Inimigo> getInimigosDead() {
        return inimigosDead;
    }

    public QueueADT<Divisao> getPercursoToCruz() {
        return percursoToCruz;
    }

    public double getVidaFinalTo() {
        return vidaFinalTo;
    }

    public Edificio getEdificio() {
        return edificio;
    }

    public ToCruz getToCruz() {
        return toCruz;
    }

    /*Fim dos getters*/

    /*Metodos do Jogo*/
    @Override
    public void inimigoDead(Inimigo inimigo, Divisao divisao) {
        this.inimigosDead.addToRear(inimigo);
        divisao.removeInimigo(inimigo);
    }

    @Override
    public void updatePercursoToCruz(Divisao divisao) {
        this.percursoToCruz.enqueue(divisao);
    }

    @Override
    public void addCollectedItem(Item item, Divisao divisao) {
        this.collectedItem.addToRear(item);
        divisao.removeItem(item);
    }
    /**
     * Gera o relatório final do jogo, incluindo informações sobre o progresso da personagem ToCruz,
     * itens coletados, inimigos mortos e o status da missão.
     * O relatório também exibe o percurso feito pelo ToCruz durante a simulação.
     */
    @Override
    public void relatorioMissao() {
        this.vidaFinalTo = toCruz.getVida();
        System.out.println(" ");
        System.out.println("!---------------------------------!");
        System.out.println(" ");
        System.out.println("Fim do jogo");
        System.out.println("Relatorio do jogo: ");
        System.out.println("Vida final do ToCruz --> " + vidaFinalTo);

        if (this.vidaFinalTo > 0 && this.toCruz.isColectedAlvo()) {
            System.out.println("Missao realizada com sucesso! ☆*: .｡. o(≧▽≦)o .｡.:*☆");
        } else {
            System.out.println("Missao falhada ಥ_ಥ");
        }


        if (!inimigosDead.isEmpty()) {
            System.out.println("Numero de inimigos mortos: " + inimigosDead.size());
            System.out.println(inimigosDead.toString());
        }

        if (!collectedItem.isEmpty()) {
            System.out.println("Numero de itensColetados: " + collectedItem.size());
            System.out.println("Itens coletados pelo ToCruz:");
            this.collectedItem.toString();
        } else {
            System.out.println("O ToCruz não conseguiu coletar nenhum item.");
        }

        StackADT<ItemCura> mochila = toCruz.getMochila();
        if (!mochila.isEmpty()) {
            System.out.println("Itens na mochila do ToCruz:");
            mochila.toString();
        } else {
            System.out.println("A mochila do To Cruz esta vazia.");
        }

        System.out.println("Percurso feito pelo ToCruz:");
        System.out.println(percursoToCruz.toString());
    }


    @Override
    public boolean getAlvo() {
        this.collectedAlvo = true;
        return this.collectedAlvo;
    }
    /*Fim dos metodos da simulacao*/

    /**
     * Retorna uma representação em string da simulação, incluindo a versão da simulação,
     * o trajeto feito por ToCruz, o número de inimigos mortos, a vida restante de ToCruz
     * e o estado atual do edifício.
     *
     * @return Uma string a representar o estado atual da simulação.
     */
    @Override
    public String toString() {
        return "Jogo{" +
                "collectedAlvo=" + collectedAlvo +
                ", toCruz=" + toCruz +
                ", inimigosDead=" + inimigosDead +
                ", collectedItem=" + collectedItem +
                ", percursoToCruz=" + percursoToCruz +
                ", edificio=" + edificio +
                ", vidaFinalTo=" + vidaFinalTo +
                '}';
    }

    /**
     * Compara este objeto Simulacoes com outro para verificar se são iguais.
     * A comparação leva em consideração a versão da simulação, o trajeto do ToCruz,
     * a vida restante do ToCruz e o estado do edifício.
     *
     * @param o O objeto a ser comparado com o objeto atual.
     * @return True se os objetos forem iguais, false caso contrário.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Simulacao that = (Simulacao) o;
        return versao == that.versao && Double.compare(this.vidaFinalTo, that.vidaFinalTo) == 0 && Objects.equals(this.percursoToCruz, that.percursoToCruz) && Objects.equals(edificio, that.edificio);
    }

    /**
     * Gera um código de hash único para o objeto Simulacoes, baseado na versão da simulação,
     * no trajeto do ToCruz, na vida restante do ToCruz e no estado do edifício.
     *
     * @return Um código de hash gerado para o objeto Simulacoes.
     */
    @Override
    public int hashCode() {
        return Objects.hash(edificio, versao, percursoToCruz, vidaFinalTo);
    }

    /**
     * Compara este objeto Simulacoes com outro para determinar a ordem de classificação.
     * A comparação é feita com base na vida restante do ToCruz e na versão da simulação.
     *
     * @param o O objeto Simulacoes a ser comparado.
     * @return Um número negativo, zero ou positivo dependendo da ordem dos objetos.
     */
    @Override
    public int compareTo(Simulacao o) {
        if (this.vidaFinalTo < o.vidaFinalTo) {
            return 1;
        } else if (this.vidaFinalTo > o.vidaFinalTo) {
            return -1;
        } else {
            if (this.versao < o.versao) {
                return 1;
            } else if (this.versao > o.versao) {
                return -1;
            }
        }
        return 0;
    }
}
