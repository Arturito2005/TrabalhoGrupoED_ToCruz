package Missoes;

import java.util.Objects;

/**
 * Representa uma missão no contexto do jogo, com informações como código, versão,
 * edifício associado e simulações realizadas. Permite executar modos de jogo manual, automático
 * e "jogo automático".
 *
 * @author Artur Pinto
 * Nº mecanográfico: 8230138
 * @author Francisco Oliveira
 * Nº mecanografico: 8230148
 * @version 1.0
 */
public class Missao implements Comparable<Missao> {

    /**
     * Código da missão.
     */
    private String cod_missao;

    private Versao versao;

    /**
     * Construtor que inicializa a missão com os parâmetros fornecidos.
     *
     * @param cod_missao o código da missão
     * @param versao a versão da missão
     */
    public Missao(String cod_missao, Versao versao) {
        this.cod_missao = cod_missao;
        this.versao = versao;
    }

    /**
     * Construtor default que inicializa a missão com valores default.
     */
    public Missao() {
        this.cod_missao = "";
        this.versao = new Versao();
    }

    /**
     * Retorna o código da missão.
     *
     * @return o código da missão
     */
    public String getcod_missao() {
        return cod_missao;
    }

    /**
     * Retorna a versão da missão.
     *
     * @return a versão da missão
     */
    public Versao getVersao() {
        return versao;
    }

    /**
     * Compara esta missão com outra com base no código e na versão.
     *
     * @param o a outra missão a ser comparada
     * @return 1 se esta missão for menor, -1 se for maior, ou 0 se forem iguais
     */
    @Override
    public int compareTo(Missao o) {
        int value;

        if (this.cod_missao.compareTo(o.cod_missao) < 0) {
            value = 1;
        } else if (this.cod_missao.compareTo(o.cod_missao) > 0) {
            value = -1;
        } else {
            if (this.versao.getNum_versao() < o.versao.getNum_versao()) {
                value = 1;
            } else {
                value = -1;
            }
        }

        return value;
    }

    /**
     * Verifica a igualdade entre dois objetos Missao.
     * A comparação é feita pelo código da missão e a versão.
     *
     * @param o o objeto a ser comparado com esta Missao
     * @return true se os objetos forem iguais, false caso contrário
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Missao missao = (Missao) o;

        return this.cod_missao.equals(missao.cod_missao) && this.versao == missao.versao;
    }

    /**
     * Calcula o código de hash para a Missao com base no código e na versão.
     *
     * @return o código de hash calculado
     */
    @Override
    public int hashCode() {
        return Objects.hash(cod_missao, versao);
    }
}
