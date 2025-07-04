package Mapa;

import java.util.Objects;

/**
 * Classe que representa um alvo no jogo.
 *
 * @author Artur Pinto
 * Nº mecanográfico: 8230138
 * @author Francisco Oliveira
 * Nº mecanografico: 8230148
 * @version 1.0
 */
public class Alvo {

    /**
     * Contador estático para gerar identificadores únicos para os alvos.
     */
    private static int ID_ALVO_CONT = 0;

    /**
     * Identificador único do alvo.
     */
    private int id_alvo;

    /**
     * Nome do alvo.
     */
    private String nome;

    /**
     * Status que indica se o alvo foi atingido ou não.
     */
    private boolean atinigido;

    /**
     * Construtor para criar um alvo com um nome fornecido.
     * O identificador do alvo será gerado automaticamente e o status será
     * definido como não atingido (false).
     *
     * @param nome O nome do alvo.
     */
    public Alvo(String nome) {
        this.id_alvo = ID_ALVO_CONT++;
        this.nome = nome;
        this.atinigido = false;
    }

    /**
     * Construtor para criar um objeto Alvo com os parâmetros fornecidos.
     *
     * @param id_alvo   O ID único do alvo.
     * @param nome      O nome do alvo.
     * @param atinigido Indica se o alvo foi atingido (verdadeiro ou falso).
     */
    public Alvo(int id_alvo, String nome, boolean atinigido) {
        this.id_alvo = id_alvo;
        this.nome = nome;
        this.atinigido = atinigido;
    }

    /**
     * Construtor padrão para criar um alvo sem nome. O identificador será gerado
     * automaticamente, e o nome será uma string vazia.
     */
    public Alvo() {
        this.id_alvo = ID_ALVO_CONT++;
        this.nome = "";
    }

    /**
     * Retorna o ID do alvo.
     *
     * @return O ID do alvo.
     */
    public int getId_alvo() {
        return id_alvo;
    }

    /**
     * Retorna o nome do alvo.
     *
     * @return O nome do alvo.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Retorna o status de atingido do alvo.
     *
     * @return true se o alvo foi atingido, false caso contrário.
     */
    public boolean isAtinigido() {
        return atinigido;
    }

    /**
     * Altera o status de atingido do alvo.
     *
     * @param atinigido O status do alvo (true se atingido, false caso contrário).
     */
    public void setAtinigido(boolean atinigido) {
        this.atinigido = atinigido;
    }

    /**
     * Retorna uma representação em string do alvo, incluindo o seu identificador,
     * nome e status de atingido.
     *
     * @return uma string a representar o alvo.
     */
    @Override
    public String toString() {
        return "Alvo{" +
                "id_alvo=" + id_alvo +
                ", nome='" + nome + '\'' +
                ", atinigido=" + atinigido +
                '}';
    }

    /**
     * Compara este alvo com outro objeto para verificar se são iguais.
     * A comparação é baseada no identificador único e no nome do alvo.
     *
     * @param o o objeto a ser comparado.
     * @return true se os alvos forem iguais (mesmo identificador ou mesmo nome),
     * caso contrário false.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Alvo alvo = (Alvo) o;

        if (id_alvo == alvo.id_alvo) {
            return true;
        }
        return Objects.equals(nome, alvo.nome);
    }

    /**
     * Gera um código de hash para o alvo baseado no nome.
     *
     * @return o código de hash do nome do alvo.
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(nome);
    }
}
