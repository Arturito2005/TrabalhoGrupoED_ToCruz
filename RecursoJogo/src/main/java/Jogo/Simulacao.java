package Jogo;

import Interfaces.Jogo.JogoInterface;
import Interfaces.QueueADT;
import Interfaces.StackADT;
import Interfaces.UnorderedListADT;
import Items.Item;
import Items.ItemCura;
import Items.TypeItemCura;
import LinkedList.LinearLinkedUnorderedList;
import Mapa.Divisao;
import Mapa.Edificio;
import Personagens.Inimigo;
import Personagens.ToCruz;
import Queue.LinkedQueue;
import Stacks.LinkedStack;

import java.util.Iterator;
import java.util.Objects;
import java.util.Random;

public class Simulacao implements JogoInterface, Comparable<Simulacao> {

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
    public void addCollectedItem(Item item) {
        this.collectedItem.addToRear(item);
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
    public void moverToCruz(Divisao divisaoAtual, Divisao novaDivisao) {
        divisaoAtual.removeToCruz();
        percursoToCruz.enqueue(divisaoAtual);
        novaDivisao.addToCruz(toCruz);
    }
    /*Fim dos metodos do Jogo*/

    /// ////////////////////////////////////////////////////
    /// Passar todos estes metodos para o menu
    /// ////////////////////////////////////////////////////
    /*Metodos do ToCruz*/

    //Já está no menu e está geral
    @Override
    public void attackToCruz(Inimigo inimigo, Divisao divisao) {
        System.out.println("Turno do To Cruz atacar!");
        inimigo.setVida(inimigo.getVida() - this.toCruz.getPoder());

        if (inimigo.isDead()) {
            System.out.println("O To Cruz matou o inimigo " + inimigo.getNome());
            inimigosDead.addToRear(inimigo);
            divisao.removeInimigo(inimigo);
        } else {
            System.out.println("O inimigo " + inimigo.getNome() + " resitiu ao ataque do To Cruz e ficou com: " + inimigo.getVida() + " HP");
        }
    }

    @Override
    public void usarItemDivisao(Item item, Divisao divisao) {
        if (divisao.containItem(item)) {
            if (item instanceof ItemCura) {
                if ((((ItemCura) item).getType().equals(TypeItemCura.KIT_VIDA) && toCruz.getVida() < 100) || ((ItemCura) item).equals(TypeItemCura.COLETE)) {
                    try {
                        this.toCruz.usarItem((ItemCura) item);
                        this.collectedItem.addToRear(item);
                        divisao.removeItem(item);
                    } catch (NullPointerException e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
        } else {
            System.out.println("O item desta divisao ja foi usado anteriormente");
        }
    }

    @Override
    public void guardarItemMochila(Item item, Divisao divisao) {
        if (divisao.containItem(item) && !toCruz.mochilaIsFull()) {
            try {
                this.toCruz.guardarKit((ItemCura) item);
                this.collectedItem.addToRear(item);
                divisao.removeItem(item);
            } catch (NullPointerException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public void getAlvo() {
        this.collectedAlvo = true;
    }
    /*Fim dos metodos do ToCruz*/

    /*
     * Outros Metodos
     * */

    /**
     * Sugere o melhor caminho para o ToCruz chegar a um item ou a um alvo.
     * O ToCruz deve coletar o item e, em seguida, atingir o alvo.
     *
     * @param div_to A divisão onde o ToCruz está localizado.
     */
    @Override
    public void sugestaoCaminhoToCruzKitEAlvo(Divisao div_to) {
        ToCruz toCruz = div_to.getToCruz();
        Iterator<Divisao> itr = edificio.IteratorMapa();

        Divisao item_div = null;
        Divisao div_alvo = null;
        double best_distance = Double.MAX_VALUE;
        double distance;
        double num_arestas_com = Double.MAX_VALUE;
        double num_arestas;

        while (itr.hasNext()) {
            Divisao div = itr.next();

            if (!div.getItens().isEmpty() || (div.isEntrada_saida() && div.getAlvo() != null && this.collectedAlvo)) {
                distance = edificio.getShortestPath(div_to, div);

                if (distance == 0 || distance == best_distance) {
                    best_distance = distance;
                    num_arestas = edificio.getShortestPathNumArestas(div_to, div);

                    if (num_arestas < num_arestas_com) {
                        if (distance == 0) {
                            num_arestas_com = num_arestas;
                        }

                        item_div = div;

                        if (div.isEntrada_saida()) {
                            div_alvo = div;
                        }
                    }
                } else if (distance < best_distance) {
                    best_distance = distance;

                    item_div = div;

                    if (div.isEntrada_saida()) {
                        div_alvo = div;
                    }
                }
            } else if (div.getAlvo() != null && !toCruz.isColectedAlvo()) {
                div_alvo = div;
            }
        }

        System.out.println("Sugestao de caminho mais curto para o To Cruz chegar a um item de cura");
        shortesPathTwopoints(div_to, item_div);

        String temp;
        if (div_alvo.getAlvo() != null) {
            temp = "Sugestao de melhor caminho para o To Cruz chegar ao alvo: " + div_alvo.getAlvo().getNome();
        } else {
            temp = "Sugestao de melhor caminho para o ToCruz sair do edificio";
        }

        System.out.println(temp);
        shortesPathTwopoints(div_to, div_alvo);
    }

    /**
     * Imprime o caminho mais curto entre duas divisões no mapa.
     *
     * @param div_start A divisão de início do caminho.
     * @param Div_final A divisão final do caminho.
     */
    private void shortesPathTwopoints(Divisao div_start, Divisao Div_final) {
        Iterator<Divisao> shortestPath = edificio.shortesPathIt(div_start, Div_final);
        String temp = "";

        while (shortestPath.hasNext()) {
            Divisao div = shortestPath.next();

            if (shortestPath.hasNext()) {
                temp += div.getName() + " -->";
            } else {
                temp += div.getName();
            }
        }
        System.out.println(temp);
    }

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
