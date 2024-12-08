package Mapa;

import Graph.Network;
import Graph.NetworkMatrizAdjacencia;
import Interfaces.EdificoInt;
import Interfaces.NetworkADT;
import Interfaces.NetworkMatrizADT;
import LinkedList.LinearLinkedUnorderedList;

import java.util.Iterator;
import java.util.Objects;

/**
 * Classe que representa um edifício no jogo.
 *
 * @author Artur
 * @version 1.0
 */
public class Edificio implements EdificoInt {

    /** Contador para atribuir IDs únicos aos edifícios. */
    private static int ID_EDIFICIO_CONT = 0;

    /** Nome padrão para o edifício caso não seja fornecido um. */
    private static final String NAME_DEFAULT = "CIA Headquarters";

    private int id;  /** Identificador único do edifício. */

    private String name; /** Nome do edifício. */

    //Ver se com esta inicialização
    private NetworkMatrizADT<Divisao> planta_edificio; /** Grafo que representa as divisões e suas conexões no edifício. */

    /**
     * Construtor padrão do edifício. Inicializa o nome com o valor padrão e a planta do edifício como um grafo vazio.
     */
    public Edificio() {
        this.id = ID_EDIFICIO_CONT++;
        this.name = NAME_DEFAULT;
        this.planta_edificio = new Network<>();
    }

    /**
     * Retorna o nome do edifício.
     *
     * @return Nome do edifício.
     */
    public String getName() {
        return name;
    }

    /**
     * Retorna a planta do edifício (grafo de divisões).
     *
     * @return Grafo que representa as divisões e conexões do edifício.
     */
    public NetworkMatrizADT<Divisao> getPlantaEdificio() {
        return planta_edificio;
    }

    /**
     * Retorna o número de divisões no edifício.
     *
     * @return Número de divisões.
     */
    public int numDivisoes() {
        return this.planta_edificio.size();
    }

    public double getShortestPath(Divisao div_inicial, Divisao div_final) {
        return this.planta_edificio.shortestPathWeight(div_inicial, div_final);
    }

    public Iterator<Divisao> shortesPathIt(Divisao div_inicial, Divisao div_final) {
        return this.planta_edificio.iteratorShortestPath(div_inicial, div_final);
    }

    //Dá já de forma automatica a divisao para o ToCruz andar
    public Divisao nextDivAutomaticToCruz(Divisao div_inicial, Divisao div_final) {
        Iterator<Divisao> it = this.planta_edificio.iteratorShortestPath(div_inicial, div_final);
        it.next();
        Divisao div = it.next();

        return div;
    }

    /**
     * Retorna o número de entradas/saídas (divisões com a flag de entrada/saída ativada).
     *
     * @return Número de divisões de entrada/saída.
     */
    @Override
    public int getNumEntradasSaidas() {
        int numEntradas = 0;

        Iterator<Divisao> it = this.planta_edificio.iterator();

        while (it.hasNext()) {
            Divisao divisao = it.next();

            if (divisao.isEntrada_saida()) {
                numEntradas++;
            }
        }

        return numEntradas;
    }

//Meter throws
    /**
     * Adiciona uma divisão ao edifício.
     *
     * @param divisao A divisão a ser adicionada ao edifício.
     */
    @Override
    public void addDivisao(Divisao divisao) {
        this.planta_edificio.addVertex(divisao);
    }

    /**
     * Adiciona uma ligação entre duas divisões no edifício.
     *
     * @param vertex1 Primeira divisão.
     * @param vertex2 Segunda divisão.
     */
    @Override
    public void addLigacao(Divisao vertex1, Divisao vertex2) {
        this.planta_edificio.addEdge(vertex1, vertex2);
    }

    /**
     * Adiciona uma ligação entre duas divisões com um peso específico.
     *
     * @param vertex1 Primeira divisão.
     * @param vertex2 Segunda divisão.
     * @param weight Peso da ligação entre as divisões.
     */
    @Override
    public void addLigacao(Divisao vertex1, Divisao vertex2, double weight) {
        this.planta_edificio.addEdge(vertex1, vertex2, weight);
    }

    /**
     * Atualiza o peso de uma ligação no grafo.
     *
     * @param vertex1 A divisão cuja ligação será atualizada.
     * @param weight O novo peso da ligação.
     */
    @Override
    public void updateWeight(Divisao vertex1, double weight) {
        this.planta_edificio.updateWeightEdge(vertex1, weight);
    }

    /**
     * Retorna um iterador para as divisões adjacentes a uma divisão específica, usando BFS.
     *
     * @param divisao A divisão para a qual se deseja obter as divisões adjacentes.
     * @return Um iterador para as divisões adjacentes.
     */
    @Override
    public Iterator<Divisao> getNextDivisoes(Divisao divisao) {
        return this.planta_edificio.iteratorNextVertexs(divisao);
    }


    /**
     * Retorna um iterador para todas as divisões do edifício.
     *
     * @return Um iterador para todas as divisões.
     */
    @Override
    public Iterator<Divisao> IteratorMapa() {
        return this.planta_edificio.iterator();
    }


    /**
     * Desenha uma aresta entre duas divisões no console.
     */
    private void desenharAresta(Divisao divOrigem, Divisao divDestino, double peso) {
        String bordaOrigem = "-".repeat(divOrigem.getName().length() + 2); // Ajusta o comprimento da linha
        String bordaDestino = "-".repeat(divDestino.getName().length() + 2); // Ajusta o comprimento da linha

        // Desenha a ligação no console
        System.out.println(" ".repeat(4) + bordaOrigem + "---- (Peso: " + peso + ") ----" + bordaDestino);
    }

    public void drawMapa() {
        LinearLinkedUnorderedList<Divisao> divJaDesenhada = new LinearLinkedUnorderedList<Divisao>();
        Iterator<Divisao> itrDiv = this.planta_edificio.iterator();

        while (itrDiv.hasNext()) {
            Divisao divOrigem = itrDiv.next();
            divJaDesenhada.addToRear(divOrigem);
            divOrigem.drawnDivisao();

            Iterator<Divisao> adjacentes = this.planta_edificio.iteratorNextVertexs(divOrigem);
            while (adjacentes.hasNext()) {
                Divisao divDestino = adjacentes.next();
                if(!divJaDesenhada.contains(divDestino)) {
                    double peso = this.planta_edificio.getWeightEdge(divOrigem, divDestino);
                    System.out.println("    ^");
                    System.out.println("    |    <---> (Peso: " + peso + ")");
                    System.out.println("    v");
                    divDestino.drawnDivisao();
                }
            }
        }
    }
    /**
     * Retorna uma representação em string do edifício, incluindo seu ID, nome e planta.
     *
     * @return String representando o edifício.
     */
    @Override
    public String toString() {
        return "Edificio{" +
                "id=" + id +
                ", name='" + name +
                ", planta_edificio=" + planta_edificio.toString() +
                '}';
    }


    /**
     * Compara este edifício com outro objeto para verificar se são iguais.
     *
     * @param o O objeto a ser comparado.
     * @return true se os edifícios forem iguais, false caso contrário.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Edificio edificio = (Edificio) o;
        if (id == edificio.id) {
            return true;
        }

        return Objects.equals(name, edificio.name) && Objects.equals(planta_edificio, edificio.planta_edificio);
    }

    /**
     * Gera um código hash para o edifício, utilizando seu nome e planta.
     *
     * @return O código hash do edifício.
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }
}
