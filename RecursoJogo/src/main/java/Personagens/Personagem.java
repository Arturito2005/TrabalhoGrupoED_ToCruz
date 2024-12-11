package Personagens;

import java.util.Objects;

/**
 * Classe abstrata que representa cada personagem no jogo
 *
 * @author Artur Pinto
 * Nº mecanográfico: 8230138
 *
 * @author Francisco Oliveria
 * Nº mecanografico: 8230148
 *
 * @version 1.0
 */
public abstract class Personagem {
    /** Variável estática para controlar o ID único para cada personagem. */
    private static int ID_PERSONAGEM_CONT = 0;

    /** Valor padrão para a quantidade de vida de um personagem. */
    private static final int DEFAULT_VIDA = 100;

    /** Valor padrão para o poder de um personagem. */
    private static final int DEFAULT_PODER = 40;

    /** Identificador único de cada personagem. */
    private int id_personagem;

    /** Nome do personagem. */
    private String nome;

    /** Quantidade de vida que o personagem possui. */
    private double vida;

    /** Poder do personagem. */
    private double poder;

    /**
     * Construtor principal que inicializa todos os atributos do personagem.
     *
     * @param id_personagem Identificador único do personagem.
     * @param nome Nome do personagem.
     * @param vida Quantidade inicial de vida do personagem.
     * @param poder Quantidade inicial de poder do personagem.
     */
    public Personagem(int id_personagem, String nome, double vida, double poder) {
        this.id_personagem = id_personagem;
        this.nome = nome;
        this.vida = vida;
        this.poder = poder;
    }

    /**
     * Construtor que inicializa o personagem com nome e utiliza valores padrão para vida e poder.
     *
     * @param nome Nome do personagem.
     */
    public Personagem(String nome) {
        this.id_personagem = ID_PERSONAGEM_CONT++;
        this.nome = nome;
        this.vida = DEFAULT_VIDA;
        this.poder = DEFAULT_PODER;
    }

    /**
     * Construtor que inicializa o personagem com nome e poder, e utiliza o valor padrão para vida.
     *
     * @param nome Nome do personagem.
     * @param poder Quantidade inicial de poder do personagem.
     */
    public Personagem(String nome, double poder) {
        this.id_personagem = ID_PERSONAGEM_CONT++;
        this.nome = nome;
        this.vida = DEFAULT_VIDA;
        this.poder = poder;
    }

    /**
     * Retorna o identificador único do personagem.
     *
     * @return O identificador único do personagem.
     */
    public int getId_personagem() {
        return id_personagem;
    }

    /**
     * Retorna o nome do personagem.
     *
     * @return O nome do personagem.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Retorna a quantidade de vida do personagem.
     *
     * @return A quantidade de vida do personagem.
     */
    public double getVida() {
        return vida;
    }

    /**
     * Setter para alterar a quantidade de vida do personagem.
     *
     * @param vida O novo valor de vida a ser atribuído ao personagem.
     */
    public void setVida(double vida) {
        this.vida = vida;
    }

    /**
     * Retorna o poder do personagem.
     *
     * @return O poder do personagem.
     */
    public double getPoder() {
        return poder;
    }

    /**
     * Retorna se o personagem está morto.
     *
     * @return `true` se o personagem está morto, caso contrário `false`.
     */
    public boolean isDead() {
        boolean dead = false;

        if (this.vida <= 0) {
            dead = true;
        }

        return dead;
    }

    /**
     * Retorna uma String com os dados do Personagem.
     *
     * @return toda a informação do Personagem
     */
    @Override
    public String toString() {
        return "Nome='" + this.nome + '\'' +
                ", vida=" + this.vida +
                ", poder=" + this.poder;
    }

    /**
     * Compara dois personagens com base no ID ou no nome.
     *
     * @param o O objeto a ser comparado.
     * @return {@code true} se os personagens são iguais, caso contrário {@code false}.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Personagem pers = (Personagem) o;
        if (this.id_personagem == pers.id_personagem) {
            return true;
        }

        return Objects.equals(nome, pers.nome);
    }

    /**
     * Retorna o código hash do personagem com base no nome.
     *
     * @return O código hash do personagem.
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(nome);
    }
}
