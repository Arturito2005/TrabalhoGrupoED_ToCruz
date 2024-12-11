package Missoes;

import Interfaces.MissaoInt;
import Interfaces.OrderedListADT;
import Interfaces.UnorderedListADT;
import LinkedList.LinearLinkedOrderedList;
import LinkedList.LinearLinkedUnorderedList;
import Mapa.Edificio;

import java.util.InputMismatchException;
import java.util.Objects;
import java.util.Scanner;

/**
 * Representa uma missão no contexto do jogo, contendo informações como código, versão,
 * ediífício associado e simulações realizadas. Permite executar modos de jogo manual e automático.
 *
 * @author Artur Pinto
 * Nº mecanográfico: 8230138
 * @author Francisco Oliveria
 * Nº mecanografico: 8230148
 * @version 1.0
 */
public class Missao implements MissaoInt, Comparable<Missao> {
    /**
     * Scanner para leitura de entradas do usuário.
     */
    private static Scanner sc = new Scanner(System.in);

    /**
     * Código da missão.
     */
    private String cod_missao;

    /**
     * Versão da missão.
     */
    private long versao;

    /**
     * Edifício associado à missão.
     */
    private Edificio edificio;

    /**
     * Total de simulações realizadas nesta missão.
     */
    private int tot_simulacoes;

    /**
     * Lista ordenada de simulações realizadas.
     */
    private OrderedListADT<Simulacoes> simulacoes;

    /**
     * Construtor que inicializa a missão com os parâmetros fornecidos.
     *
     * @param cod_missao o código da missão
     * @param versao     a versão da missão
     * @param edificio   o edifício associado
     */
    public Missao(String cod_missao, long versao, Edificio edificio) {
        this.cod_missao = cod_missao;
        this.versao = versao;
        this.edificio = edificio;
        this.tot_simulacoes = 0;
        this.simulacoes = new LinearLinkedOrderedList<>();
    }

    /**
     * Construtor padrão que inicializa a missão com valores padrão.
     */
    public Missao() {
        this.cod_missao = "";
        this.versao = 0;
        this.edificio = new Edificio();
        this.simulacoes = new LinearLinkedOrderedList<>();
    }

    /**
     * Obtém o código da missão.
     *
     * @return o código da missão
     */
    public String getcod_missao() {
        return cod_missao;
    }

    /**
     * Obtém a versão da missão.
     *
     * @return a versão da missão
     */
    public long getVersao() {
        return versao;
    }

    /**
     * Obtém a lista de simulações realizadas nesta missão.
     *
     * @return a lista ordenada de simulações
     */
    public OrderedListADT<Simulacoes> getSimulacoes() {
        return simulacoes;
    }

    /**
     * Obtém o total de simulações realizadas.
     *
     * @return o total de simulações
     */
    public int getTot_simulacoes() {
        return tot_simulacoes;
    }

    /**
     * Exibe todas as simulações realizadas nesta missão.
     */
    @Override
    public void viewSimulacoes() {
        for (Simulacoes simulacao : simulacoes) {
            System.out.println(simulacao);
        }
    }

    /**
     * Executa o modo manual da missão, permitindo ao usuário realizar simulações interativas.
     */
    @Override
    public void modoManual() {
        int op = 0;
        Simulacoes simula;

        do {
            System.out.println("Começar simulação");
            System.out.println("0 - Voltar");
            System.out.println("1 - Jogar");
            System.out.print("Selecione uma opção -->");

            try {
                op = sc.nextInt();
            } catch (InputMismatchException ex) {
                System.out.println("Numero invalido!");
                sc.next();
            }

            if (op == 1) {
                simula = new Simulacoes(tot_simulacoes, new Edificio(this.edificio.getId(), this.edificio.getName(), this.edificio.getPlantaEdificio()));
                simulacoes.add(simula.modojogoManual());
                tot_simulacoes++;
            }

        } while (op != 0);

        for (Simulacoes simulacao : simulacoes) {
            System.out.println(simulacao.toString());
        }
    }

    /**
     * Executa o modo automático da missão, mostrando o caminho mais curto para o ToCruz entrar no
     * edificio e coletar de volta e o melhor caminho que ele deve fazer para sair do edifico.
     */
    @Override
    public void modoAutomatico() {
        Simulacoes simulacaoAutomatica = new Simulacoes(tot_simulacoes, new Edificio(this.edificio.getId(), this.edificio.getName(), this.edificio.getPlantaEdificio()));
        simulacaoAutomatica.modojogoAutomatico();
    }

    /**
     * Executa um jogo automático nesta missão, ou seja, um jogo em que o ToCruz joga sem iteração do utilizador.
     */
    @Override
    public void jogoAutomatico() {
        Simulacoes simulacao = new Simulacoes(tot_simulacoes, new Edificio(this.edificio.getId(), this.edificio.getName(), this.edificio.getPlantaEdificio()));
        simulacao.jogoAutomatico();
    }

    /**
     * Compara esta missão com outra com base no código e na versão.
     *
     * @param o a outra missão a ser comparada
     * @return 1 se esta missão for menor, -1 se for maior, ou 0 se forem iguais
     */
    @Override
    public int compareTo(Missao o) {
        int value = 0;
        if (this.cod_missao.compareTo(o.cod_missao) < 0) {
            value = 1;
        } else if (this.cod_missao.compareTo(o.cod_missao) > 0) {
            value = -1;
        } else {
            if (this.versao < o.versao) {
                value = 1;
            } else {
                value = -1;
            }
        }

        return value;
    }

    /**
     * Verifica a igualdade entre dois objetos Missao. A comparação é feita pelo código da missão e a versão.
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

        return this.cod_missao.equals(((Missao) o).cod_missao) && this.versao == ((Missao) o).versao;
    }

    /**
     * Calcula o código hash para esta Missao com base no código e na versão.
     *
     * @return o código hash calculado
     */
    @Override
    public int hashCode() {
        return Objects.hash(cod_missao, versao);
    }
}
