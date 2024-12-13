package Personagens;

import java.util.Objects;

/**
 * Classe abstrata que representa cada personagem no jogo
 *
 * @author Artur Pinto
 * Nº mecanográfico: 8230138
 * @author Francisco Oliveira
 * Nº mecanografico: 8230148
 * @version 1.0
 */
public abstract class Personagem {
    /**
     * Variável estática para controlar o ID único para cada personagem.
     */
    private static int ID_PERSONAGEM_CONT = 0;

    /**
     * Valor padrão para a quantidade de vida de uma personagem.
     */
    private static final int DEFAULT_VIDA = 100;

    /**
     * Valor padrão para o poder de uma personagem.
     */
    private static final int DEFAULT_PODER = 40;

    /**
     * Identificador único de cada personagem.
     */
    private int id_personagem;

    /**
     * Nome da personagem.
     */
    private String nome;

    /**
     * Quantidade de vida que a personagem possui.
     */
    private long vida;

    /**
     * Poder de ataque da personagem.
     */
    private long poder;

    /**
     * Construtor principal que inicializa todos os atributos da personagem.
     *
     * @param id_personagem Identificador único da personagem.
     * @param nome          Nome da personagem.
     * @param vida          Quantidade inicial de vida da personagem.
     * @param poder         Quantidade inicial de poder de ataque da personagem.
     */
    public Personagem(int id_personagem, String nome, long vida, long poder) {
        this.id_personagem = id_personagem;
        this.nome = nome;
        this.vida = vida;
        this.poder = poder;
    }

    /**
     * Construtor que inicializa a personagem com nome e utiliza valores padrão para vida e poder.
     *
     * @param nome Nome da personagem.
     */
    public Personagem(String nome) {
        this.id_personagem = ID_PERSONAGEM_CONT++;
        this.nome = nome;
        this.vida = DEFAULT_VIDA;
        this.poder = DEFAULT_PODER;
    }

    /**
     * Construtor que inicializa a personagem com nome e poder, e utiliza o valor padrão para a vida.
     *
     * @param nome  Nome da personagem.
     * @param poder Quantidade inicial de poder de ataque da personagem.
     */
    public Personagem(String nome, long poder) {
        this.id_personagem = ID_PERSONAGEM_CONT++;
        this.nome = nome;
        this.vida = DEFAULT_VIDA;
        this.poder = poder;
    }

    /**
     * Retorna o identificador único da personagem.
     *
     * @return O identificador único da personagem.
     */
    public int getId_personagem() {
        return id_personagem;
    }

    /**
     * Retorna o nome da personagem.
     *
     * @return O nome da personagem.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Retorna a quantidade de vida da personagem.
     *
     * @return A quantidade de vida da personagem.
     */
    public long getVida() {
        return vida;
    }

    /**
     * Setter para alterar a quantidade de vida da personagem.
     *
     * @param vida O novo valor de vida a ser atribuído à personagem.
     */
    public void setVida(long vida) {
        this.vida = vida;
    }

    /**
     * Retorna o poder de ataque da personagem.
     *
     * @return O poder de ataque da personagem.
     */
    public long getPoder() {
        return poder;
    }

    /**
     * Retorna se a personagem está morta.
     *
     * @return `true` se a personagem está morta, caso contrário `false`.
     */
    public boolean isDead() {
        boolean dead = false;

        if (this.vida <= 0) {
            dead = true;
        }

        return dead;
    }

    /**
     * Retorna uma String com os dados da Personagem.
     *
     * @return toda a informação da Personagem
     */
    @Override
    public String toString() {
        return "Nome='" + this.nome + '\'' +
                ", vida=" + this.vida +
                ", poder=" + this.poder;
    }

    /**
     * Compara duas personagens com base no ID ou no nome.
     *
     * @param o O objeto a ser comparado.
     * @return {@code true} se as personagens são iguais, caso contrário {@code false}.
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
     * Retorna o código hash da personagem com base no nome.
     *
     * @return O código hash da personagem.
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(nome);
    }
}
