package Personagens;

import java.util.Objects;

/**
 * Classe abstrata que representa cada personagem no jogo
 *
 * @author Artur
 * @version 1.0
 */
public abstract class Personagem {
    private static int ID_PERSONAGEM_CONT = 0; //Variável estática para controlar o ID único para cada personagem.

    private static final int DEFAULT_VIDA = 100; //Variável estasticas para definir um valor default da vida do personagem

    private static final int DEFAULT_PODER = 40; //Variável estasticas para definir um valor default do poder do personagem

    private int id_personagem; //Identificador único de cada personagem.

    private String nome;  //Nome do personagem.

    private double vida; //Quantidade de vida que o personagem possui.

    private double poder; //Poder do personagem.

    /**
     * Construtor principal com todos os atributos.
     */
    public Personagem(String nome, double vida, double poder) {
        this.id_personagem = ID_PERSONAGEM_CONT++;
        this.nome = nome;
        this.vida = vida;
        this.poder = poder;
    }

    /**
     * Construtor com nome e divisão, usa os valores padrão para vida e poder.
     */
    public Personagem(String nome) {
        this.id_personagem = ID_PERSONAGEM_CONT++;
        this.nome = nome;
        this.vida = DEFAULT_VIDA;
        this.poder = DEFAULT_PODER;
    }

    /**
     * Construtor com nome, poder e divisão, usa o valor padrão para vida.
     */
    public Personagem(String nome, double poder) {
        this.id_personagem = ID_PERSONAGEM_CONT++;
        this.nome = nome;
        this.vida = DEFAULT_VIDA;
        this.poder = poder;
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

    @Override
    public int hashCode() {
        return Objects.hashCode(nome);
    }
}
