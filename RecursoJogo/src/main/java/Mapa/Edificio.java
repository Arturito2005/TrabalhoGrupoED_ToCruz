package Mapa;

import Graph.Network;
import Interfaces.Mapa.EdificoInt;
import Interfaces.NetworkMatrizADT;

import java.util.Iterator;
import java.util.Objects;

/**
 * Classe que representa um edifício no jogo.
 *
 * @author Artur Pinto
 * Nº mecanográfico: 8230138
 * @author Francisco Oliveira
 * Nº mecanografico: 8230148
 * @version 1.0
 */
public class Edificio implements EdificoInt {

    /**
     * Contador para atribuir IDs únicos aos edifícios.
     */
    private static int ID_EDIFICIO_CONT = 0;

    /**
     * Nome padrão para o edifício caso não seja fornecido um.
     */
    private static final String NAME_DEFAULT = "CIA Headquarters";

    /**
     * Identificador único do edifício.
     */
    private int id;

    /**
     * Nome do edifício.
     */
    private String name;

    /**
     * Grafo pesado que representa as divisões e respetivas conexões no edifício.
     */
    private NetworkMatrizADT<Divisao> planta_edificio;

    /**
     * Construtor default do edifício.
     * Inicializa o nome com o valor default e a planta do edifício como um grafo pesado vazio.
     */
    public Edificio() {
        this.id = ID_EDIFICIO_CONT++;
        this.name = NAME_DEFAULT;
        this.planta_edificio = new Network<>();
    }

    /**
     * Construtor de deepCopy do Edificio.
     * Cria um novo edifício copiando as divisões e as ligações do edifício original.
     *
     * @param id_edificio     ID do edifício a ser copiado.
     * @param name            Nome do edifício a ser copiado.
     * @param planta_edificio Grafo que representa as divisões e respetivas conexões.
     */
    public Edificio(int id_edificio, String name, NetworkMatrizADT<Divisao> planta_edificio) {
        this.id = id_edificio;
        this.name = name;
        NetworkMatrizADT<Divisao> networkTemp = new Network<>();

        for (Divisao divisao : planta_edificio) {
            Divisao divisao_temp = new Divisao(divisao.getId_divisao(), divisao.getName(), divisao.isEntrada_saida(), divisao.getAlvo(), divisao.getItem(), divisao.getInimigos(), divisao.getToCruz());
            networkTemp.addVertex(divisao_temp);

            for (Divisao divisaoLig : planta_edificio) {
                Divisao tempDiv_lig = new Divisao(divisaoLig.getId_divisao(), divisaoLig.getName(), divisaoLig.isEntrada_saida(), divisaoLig.getAlvo(), divisaoLig.getItem(), divisaoLig.getInimigos(), divisaoLig.getToCruz());
                double weight = planta_edificio.getWeightEdge(divisao_temp, tempDiv_lig);
                networkTemp.addEdge(divisao_temp, tempDiv_lig, weight);
            }
        }

        this.planta_edificio = networkTemp;
    }

    /**
     * Retorna o identificador único do objeto.
     *
     * @return O identificador único do objeto.
     */
    public int getId() {
        return id;
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
     * Retorna a planta do edifício (grafo pesado de divisões).
     *
     * @return Grafo pesado que representa as divisões e conexões do edifício.
     */
    public NetworkMatrizADT<Divisao> getPlantaEdificio() {
        return planta_edificio;
    }

    /**
     * Retorna o caminho mais curto entre duas divisões, considerando o peso das arestas.
     *
     * @param div_inicial Divisão inicial.
     * @param div_final Divisão final.
     * @return Peso do caminho mais curto entre as divisões.
     */
    @Override
    public double getShortestPath(Divisao div_inicial, Divisao div_final) {
        return this.planta_edificio.shortestPathWeight(div_inicial, div_final);
    }

    /**
     * Retorna o número de arestas no caminho mais curto entre duas divisões.
     *
     * @param div_inicial Divisão inicial.
     * @param div_final Divisão final.
     * @return Número de arestas no caminho mais curto entre as divisões.
     */
    @Override
    public double getShortestPathNumArestas(Divisao div_inicial, Divisao div_final) {
        return this.planta_edificio.shortestPathArest(div_inicial, div_final);
    }

    /**
     * Retorna um iterador para o caminho mais curto entre duas divisões.
     *
     * @param div_inicial Divisão inicial.
     * @param div_final Divisão final.
     * @return Iterador para o caminho mais curto.
     */
    @Override
    public Iterator<Divisao> shortesPathIt(Divisao div_inicial, Divisao div_final) {
        return this.planta_edificio.shortestPath(div_inicial, div_final);
    }

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
     * Desenha o mapa do edifício, exibindo as suas divisões e conexões.
     */
    public void drawMapa() {
        Iterator<Divisao> itrDiv = this.planta_edificio.iterator();

        while (itrDiv.hasNext()) {
            Divisao div = itrDiv.next();
            System.out.println("Lista de Divisoes vizinhas a divisao: " + div.getName());
            System.out.println();

            Iterator<Divisao> itr_adj = this.planta_edificio.iteratorNextVertexs(div);

            while (itr_adj.hasNext()) {
                Divisao div_adj = itr_adj.next();
                System.out.println(div_adj.drawnDivisao());
            }

            System.out.println();
        }
    }

    /**
     * Retorna uma representação em string do edifício, incluindo o seu ID, nome e planta.
     *
     * @return String a representar o edifício.
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
     * Gera um código de hash para o edifício, utilizando o seu nome e planta.
     *
     * @return O código de hash do edifício.
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }
}
