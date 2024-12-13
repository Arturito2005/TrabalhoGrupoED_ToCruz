package Missoes;

import Interfaces.MissoesInt;
import Interfaces.OrderedListADT;
import LinkedList.LinearLinkedOrderedList;

import java.util.Objects;

/**
 * Representa uma coleção de missões com capacidade de adicionar novas
 * missões e fazer a contagem total de missões.
 *
 * @author Artur Pinto
 * Nº mecanográfico: 8230138
 * @author Francisco Oliveira
 * Nº mecanografico: 8230148
 * @version 1.0
 */
public class Missoes implements MissoesInt {

    /**
     * Contador para o número total de missões adicionadas.
     */
    private int contMissoes;

    /**
     * Lista ordenada que armazena objetos do tipo Missao.
     */
    private OrderedListADT<Missao> listaMissao;

    /**
     * Construtor padrão que inicializa o contador de missões e a lista de missões.
     */
    public Missoes() {
        this.contMissoes = 0;
        listaMissao = new LinearLinkedOrderedList<Missao>();
    }

    /**
     * Retorna o número total de missões adicionadas.
     *
     * @return o número total de missões.
     */
    public int getContMissoes() {
        return this.contMissoes;
    }

    /**
     * Retorna a lista ordenada de missões.
     *
     * @return a lista de missões.
     */
    public OrderedListADT<Missao> getListaMissao() {
        return this.listaMissao;
    }

    /**
     * Adiciona uma nova missão à lista de missões.
     *
     * @param missao a missão a ser adicionada.
     * @throws NullPointerException se a missão fornecida for nula.
     */
    @Override
    public void addMissao(Missao missao) throws NullPointerException {
        if (missao == null) {
            throw new NullPointerException("Nao e possivel introduzir missoes nulas");
        }
        listaMissao.add(missao);
        this.contMissoes++;
    }

    /**
     * Compara a instância atual de Missoes com outro objeto para verificar se são iguais.
     * A comparação é feita com base na lista de missões armazenada.
     *
     * @param o o objeto a ser comparado com a instância atual.
     * @return {@code true} se os objetos forem iguais, {@code false} caso contrário.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Missoes missoes = (Missoes) o;
        return Objects.equals(listaMissao, missoes.listaMissao);
    }

    /**
     * Retorna o código de hash para a instância atual de Missoes.
     * O código de hash é calculado com base na lista de missões.
     *
     * @return o código de hash para a instância de Missoes.
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(listaMissao);
    }
}